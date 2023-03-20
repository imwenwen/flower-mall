
package com.flower.mall.controller.mall;

import cn.hutool.captcha.ShearCaptcha;
import com.flower.mall.common.Constants;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallUserVO;
import com.flower.mall.entity.MallUser;
import com.flower.mall.service.UserService;
import com.flower.mall.util.MD5Util;
import com.flower.mall.util.Result;
import com.flower.mall.util.MallResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PersonalController {

    @Resource
    private UserService userService;

    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request,
                               HttpSession httpSession) {
        request.setAttribute("path", "personal");
        return "mall/personal";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        return "mall/login";
    }

    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "mall/login";
    }

    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }

    @GetMapping("/personal/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("verifyCode") String verifyCode,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        //判断用户名是否为空
        if (StringUtils.isBlank(loginName)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        //判断密码是否为空
        if (StringUtils.isBlank(password)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        //判断验证码是否为空
        if (StringUtils.isBlank(verifyCode)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        //生成验证码会存入session，通过Constants.MALL_VERIFY_CODE_KEY获取到验证码和输入的验证码进行校验
        ShearCaptcha shearCaptcha = (ShearCaptcha) httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY);

        //验证码和输入的验证码进行校验
        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String loginResult = userService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"), httpSession);
        //登录成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            //删除session中的verifyCode 删除验证码
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return MallResult.createSuccessRes();
        }
        //登录失败
        return MallResult.createFailRes(loginResult);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           HttpSession httpSession) {
        //判断用户名是否为空
        if (StringUtils.isBlank(loginName)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        //判断密码是否为空
        if (StringUtils.isBlank(password)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        //判断验证码是否为空
        if (StringUtils.isBlank(verifyCode)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        //生成验证码会存入session，通过Constants.MALL_VERIFY_CODE_KEY获取到验证码和输入的验证码进行校验
        ShearCaptcha shearCaptcha = (ShearCaptcha) httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY);

        //验证码和输入的验证码进行校验
        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String registerResult = userService.register(loginName, password);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return MallResult.createSuccessRes();
        }
        //注册失败
        return MallResult.createFailRes(registerResult);
    }

    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        FlowerMallUserVO mallUserTemp = userService.updateUserInfo(mallUser, httpSession);
        if (mallUserTemp == null) {
            return MallResult.createFailRes("修改失败");
        } else {
            //返回成功
            return  MallResult.createSuccessRes();
        }
    }
}
