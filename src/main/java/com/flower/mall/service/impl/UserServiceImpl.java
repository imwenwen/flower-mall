
package com.flower.mall.service.impl;

import com.flower.mall.common.Constants;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallUserVO;
import com.flower.mall.dao.MallUserMapper;
import com.flower.mall.entity.MallUser;
import com.flower.mall.service.UserService;
import com.flower.mall.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MallUserMapper mallUserMapper;

    @Override
    public PageResult getUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(pageUtil);
        int total = mallUserMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String register(String loginName, String password) {
        //根据手机号检验是否唯一，唯一才可以注册
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        //默认别称为手机号
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        //密码加密
        registerUser.setPasswordMd5(passwordMD5);
        if (mallUserMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        MallUser user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            FlowerMallUserVO flowerMallUserVO = new FlowerMallUserVO();
            BeanUtil.copyProperties(user, flowerMallUserVO);
            //设置购物车中的数据，购物车上面显示的数量会在CartNumberInterceptor拦截器set数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, flowerMallUserVO);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public FlowerMallUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        FlowerMallUserVO userTemp = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallUser userFromDB = mallUserMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            if (StringUtils.isNotBlank(mallUser.getNickName())) {
                userFromDB.setNickName(MyUtils.cleanString(mallUser.getNickName()));
            }
            if (StringUtils.isNotBlank(mallUser.getAddress())) {
                userFromDB.setAddress(MyUtils.cleanString(mallUser.getAddress()));
            }
            if (StringUtils.isNotBlank(mallUser.getIntroduceSign())) {
                userFromDB.setIntroduceSign(MyUtils.cleanString(mallUser.getIntroduceSign()));
            }
            //因为用户信息登陆的时候一开始就存在session里面了，这里更新完用户信息之后还要对session里面的个人信息进行更新
            if (mallUserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                FlowerMallUserVO flowerMallUserVO = new FlowerMallUserVO();
                userFromDB = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(userFromDB, flowerMallUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, flowerMallUserVO);
                return flowerMallUserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}
