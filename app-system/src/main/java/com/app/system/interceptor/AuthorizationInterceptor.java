package com.app.system.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.app.system.entity.Admin;
import com.app.system.entity.Resource;
import com.app.system.entity.Result;
import com.app.system.enums.ResultEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否为ajax请求，默认不是
        boolean isAjaxRequest = false;
        if (!StrUtil.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
            isAjaxRequest = true;
        }
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        List<Resource> resourceList = this.getResourceList(admin);
        String path = request.getServletPath();
        PrintWriter writer = response.getWriter();
        if (isAjaxRequest) {
            if (this.isAuth(admin)) {
                writer.write(JSONUtil.toJsonStr(Result.fail(ResultEnum.UNAUTHORIZED.getCode(), ResultEnum.UNAUTHORIZED.getDesc())));
                writer.close();
                return false;
            }
            if (this.isPermission(path, resourceList)) {
                writer.write(JSONUtil.toJsonStr(Result.fail(ResultEnum.NO_PERMISSION.getCode(), ResultEnum.NO_PERMISSION.getDesc())));
                writer.close();
                return false;
            }
        } else {
            if (this.isAuth(admin)) {
                response.sendRedirect("login.html");
                return false;
            }
            if (this.isPermission(path, resourceList)) {
                response.sendRedirect("login.html");
                return false;
            }
        }
        return true;


    }

    /**
     * 是否授权
     * @param admin
     * @return 如果返回true则未授权，如果返回false则已授权。
     */
    private boolean isAuth(Admin admin) {
        return ObjectUtil.isNull(admin);
    }

    /**
     * 是否有权限
     * @param path
     * @param resourceList
     * @return 如果返回true则没有权限，如果返回false则有权限。
     */
    private boolean isPermission(String path, List<Resource> resourceList) {
        if(CollUtil.isEmpty(resourceList)){
            return true;
        }
        for (Resource resource : resourceList) {
            if(path.matches(resource.getUrl())){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取当前用户对应资源集合
     * @param admin
     * @return
     */
    private List<Resource> getResourceList(Admin admin) {
        List<Resource> resourceList = new ArrayList<>();
        if (ObjectUtil.isNull(admin)) {
            return resourceList;
        }
        admin.getRoleList().stream().filter(role -> role.getStatus() == 0).collect(Collectors.toList()).forEach(role -> {
            role.getResourceList().stream().filter(resource -> resource.getStatus() == 0).collect(Collectors.toList()).forEach(resource -> {
                if (ArrayUtil.isEmpty(resourceList)) {
                    resourceList.add(resource);
                    return;
                }
                resourceList.forEach(r -> {
                    if (r.getUrl().equals(resource.getUrl())) {
                        return;
                    }
                });
                resourceList.add(resource);
            });
        });
        return resourceList;
    }
}
