package com.app.system.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.app.system.entity.Admin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Admin admin = (Admin)session.getAttribute("admin");
        if(ObjectUtil.isNull(admin)){

        }
        return false;
    }
}
