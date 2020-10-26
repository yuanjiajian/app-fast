package com.app.system.controller;


import cn.hutool.core.util.ObjectUtil;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/edit.html")
    public String edit(Integer id, Model model) {
        Admin admin = new Admin();
        if (ObjectUtil.isNull(id)) {
            admin.setStatus(0);
            model.addAttribute("admin", admin);
        } else {
            admin = adminService.getById(id);
            model.addAttribute("admin", admin);
        }
        return "admin/edit";
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
    public Result update_status(@RequestParam("ids") List<Integer> ids, Integer status) {
        List<Admin> adminList = adminService.update_status(ids, status);
        return Result.success(adminList);
    }

}

