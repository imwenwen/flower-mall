
package com.flower.mall.controller.admin;

import com.flower.mall.service.UserService;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.Result;
import com.flower.mall.util.MallResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public String usersPage(HttpServletRequest request) {
        request.setAttribute("path", "users");
        return "admin/user";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return MallResult.createFailRes("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return MallResult.createSuccessRes(userService.getNewBeeMallUsersPage(pageUtil));
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @RequestMapping(value = "/users/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable int lockStatus) {
        if (ids.length < 1) {
            return MallResult.createFailRes("参数异常！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return MallResult.createFailRes("操作非法！");
        }
        if (userService.lockUsers(ids, lockStatus)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes("禁用失败");
        }
    }
}