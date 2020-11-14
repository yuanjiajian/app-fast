package com.app.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.util.ObjectUtil;
import com.app.system.entity.Admin;
import com.app.system.entity.Result;
import com.app.system.entity.Role;
import com.app.system.enums.ResultEnum;
import com.app.system.service.AdminService;
import com.app.system.service.RoleService;
import com.app.system.validation.LoginGroup;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@Controller
@Validated
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    @GetMapping("/login.html")
    public String login() {
        return "login";
    }

    @GetMapping("/captcha")
    public void captcha() throws IOException {
        ICaptcha captcha = CaptchaUtil.createCircleCaptcha(120, 38, 4, 20);
        String code = captcha.getCode();
        session.setAttribute("code", code);
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        outputStream.close();
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@Validated(value = {LoginGroup.class}) Admin loginAdmin, @NotBlank(message = "图形验证不能为空") String picCode) {
        String code = (String) session.getAttribute("code");
        if (!picCode.equalsIgnoreCase(code)) {
            return Result.fail(ResultEnum.CAPTCHA_ERROR.getCode(), ResultEnum.CAPTCHA_ERROR.getDesc());
        }
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("username", loginAdmin.getUsername()));
        if (ObjectUtil.isNull(admin)) {
            return Result.fail(ResultEnum.USERNAME_NOT_EXIST.getCode(), ResultEnum.USERNAME_NOT_EXIST.getDesc());
        }
        if (admin.getStatus() == 1) {
            return Result.fail(ResultEnum.USERNAME_DISABLE.getCode(), ResultEnum.USERNAME_DISABLE.getDesc());
        }
        if (!admin.getPassword().equals(DigestUtils.md5DigestAsHex(loginAdmin.getPassword().getBytes()))) {
            return Result.fail(ResultEnum.PASSWORD_ERROR.getCode(), ResultEnum.PASSWORD_ERROR.getDesc());
        }
        Admin adminRoleWithResource = adminService.getRoleWithResourceById(admin.getId());
        session.setAttribute("admin", adminRoleWithResource);
        return Result.success();
    }
}
