package com.app.system.controller;


import cn.hutool.core.util.ObjectUtil;
import com.app.system.entity.Resource;
import com.app.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-10-28
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/list.html")
    public String list() {
        return "resource/list";
    }

    @GetMapping("/edit.html")
    public String edit(Integer id, Integer parentId, Model model) {
        Resource resource = new Resource();
        if (ObjectUtil.isNull(id)) {
            resource.setParentId(parentId);
            resource.setStatus(0);
        } else {
            resource = resourceService.getById(id);
        }
        model.addAttribute("resource", resource);
        return "resource/edit";
    }

}

