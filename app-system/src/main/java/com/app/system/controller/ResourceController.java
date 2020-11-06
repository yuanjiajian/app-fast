package com.app.system.controller;


import cn.hutool.core.util.ObjectUtil;
import com.app.system.entity.Resource;
import com.app.system.entity.Result;
import com.app.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
            resource.setType(0);
            resource.setStatus(0);
        } else {
            resource = resourceService.getById(id);
        }
        model.addAttribute("resource", resource);
        return "resource/edit";
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Resource> resourceList = resourceService.list();
        return Result.success(resourceList);
    }

    @PostMapping("/add")
    @ResponseBody
    public Result add(@Validated Resource resource) {
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
        resourceService.save(resource);
        return Result.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        resourceService.delete(ids);
        return Result.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(@Validated Resource resource) {
        resource.setUpdateTime(LocalDateTime.now());
        resourceService.updateById(resource);
        return Result.success();
    }

    @PostMapping("/update_status")
    @ResponseBody
    public Result update_status(@RequestParam("ids") List<Integer> ids, Integer status) {
        List<Resource> resourceList = resourceService.update_status(ids, status);
        return Result.success(resourceList);
    }
}

