package com.app.system.controller;


import cn.hutool.core.util.StrUtil;
import com.app.system.entity.Admin;
import com.app.system.entity.PageParam;
import com.app.system.entity.Result;
import com.app.system.enums.SortOrderEnum;
import com.app.system.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-10-23
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/list.html")
    public String list() {
        return "admin/list";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result list(String name, PageParam pageParam) {
        Page<Admin> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
        if (pageParam.getSortOrder().equalsIgnoreCase(SortOrderEnum.ASC.getDesc())) {
            page.addOrder(OrderItem.asc(pageParam.getSort()));
        } else {
            page.addOrder(OrderItem.desc(pageParam.getSort()));
        }
        Page<Admin> adminPage = adminService.page(page, new QueryWrapper<Admin>().like(StrUtil.isNotBlank(name), "name", name));
        return Result.success(adminPage);
    }

    @PostMapping("/update_status")
    @ResponseBody
    public Result update_status(Integer id, Integer status) {
        Admin admin = adminService.getById(id);
        admin.setStatus(status);
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
        return Result.success(admin);
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(@Validated Admin admin) {
        adminService.updateById(admin);
        return Result.success();
    }
}

