
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
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
        if (StringUtils.isEmpty(loginName)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        ShearCaptcha shearCaptcha = (ShearCaptcha) httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY);

        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String loginResult = userService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"), httpSession);
        //????????????
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            //??????session??????verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return MallResult.createSuccessRes();
        }
        //????????????
        return MallResult.createFailRes(loginResult);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        ShearCaptcha shearCaptcha = (ShearCaptcha) httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY);
        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            return MallResult.createFailRes(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String registerResult = userService.register(loginName, password);
        //????????????
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            //??????session??????verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return MallResult.createSuccessRes();
        }
        //????????????
        return MallResult.createFailRes(registerResult);
    }

    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        FlowerMallUserVO mallUserTemp = userService.updateUserInfo(mallUser, httpSession);
        if (mallUserTemp == null) {
            Result result = MallResult.createFailRes("????????????");
            return result;
        } else {
            //????????????
            Result result = MallResult.createSuccessRes();
            return result;
        }
    }
}
