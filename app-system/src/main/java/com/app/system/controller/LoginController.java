package com.app.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

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

}
