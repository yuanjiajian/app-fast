package com.app.system.controller;

import com.app.system.entity.Admin;
import com.app.system.entity.Menu;
import com.app.system.entity.Resource;
import com.app.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private HttpSession session;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/index.html")
    public String index(Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        List<Resource> resourceList = resourceService.getResourceList(admin);
        List<Menu> menuList = resourceService.getMenuList(resourceList,0);
        model.addAttribute("menuList",menuList);
        return "index";
    }

    @GetMapping("/main.html")
    public String main() {
        return "main";
    }
}
