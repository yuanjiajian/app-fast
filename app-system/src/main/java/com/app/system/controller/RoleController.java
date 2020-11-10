package com.app.system.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.app.system.entity.PageParam;
import com.app.system.entity.Resource;
import com.app.system.entity.Result;
import com.app.system.entity.Role;
import com.app.system.enums.ResultEnum;
import com.app.system.enums.SortOrderEnum;
import com.app.system.service.ResourceService;
import com.app.system.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/list.html")
    public String list() {
        return "role/list";
    }

    @GetMapping("/edit.html")
    public String edit(Integer id, Model model) {
        Role role = new Role();
        if (ObjectUtil.isNull(id)) {
            role.setStatus(0);
        } else {
            role = roleService.getById(id);
            List<Resource> resourceList = resourceService.listByRoleId(id);
            role.setResourceList(resourceList);
        }
        model.addAttribute("role", role);
        return "role/edit";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result list(String name, @Validated PageParam pageParam) {
        Page<Role> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
        if (pageParam.getSortOrder().equalsIgnoreCase(SortOrderEnum.ASC.getDesc())) {
            page.addOrder(OrderItem.asc(pageParam.getSort()));
        } else {
            page.addOrder(OrderItem.desc(pageParam.getSort()));
        }
        Page<Role> rolePage = roleService.page(page, new QueryWrapper<Role>().like(StrUtil.isNotBlank(name), "name", name));
        return Result.success(rolePage);
    }


    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated Role role) {
        Role roleByName = roleService.getOne(new QueryWrapper<Role>().eq("name", role.getName()));
        if (ObjectUtil.isNotNull(roleByName)) {
            return Result.fail(ResultEnum.ROLE_NAME_EXIST.getCode(), ResultEnum.ROLE_NAME_EXIST.getDesc());
        }
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleService.add(role);
        return Result.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        roleService.delete(ids);
        return Result.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody @Validated Role role) {
        Role roleByName = roleService.getOne(new QueryWrapper<Role>().eq("name", role.getName()));
        if (ObjectUtil.isNotNull(roleByName) && roleByName.getId() != role.getId()) {
            return Result.fail(ResultEnum.ROLE_NAME_EXIST.getCode(), ResultEnum.ROLE_NAME_EXIST.getDesc());
        }
        role.setUpdateTime(LocalDateTime.now());
        roleService.edit(role);
        return Result.success();
    }

    @PostMapping("/update_status")
    @ResponseBody
    public Result update_status(@RequestParam("ids") List<Integer> ids, Integer status) {
        List<Role> roleList = roleService.update_status(ids, status);
        return Result.success(roleList);
    }
}

