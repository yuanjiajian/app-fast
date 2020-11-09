package com.app.system.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.app.system.entity.Admin;
import com.app.system.entity.PageParam;
import com.app.system.entity.Result;
import com.app.system.entity.Role;
import com.app.system.enums.ResultEnum;
import com.app.system.enums.SortOrderEnum;
import com.app.system.service.AdminService;
import com.app.system.service.RoleService;
import com.app.system.service.UploadService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private UploadService uploadService;

    @GetMapping("/list.html")
    public String list() {
        return "admin/list";
    }

    @GetMapping("/edit.html")
    public String edit(Integer id, Model model) {
        Admin admin = new Admin();
        if (ObjectUtil.isNull(id)) {
            admin.setStatus(0);
        } else {
            admin = adminService.getById(id);
            List<Role> roleList = roleService.listByAdminId(id);
            admin.setRoleList(roleList);
            admin.setPassword(null);
        }
        model.addAttribute("admin", admin);
        List<Role> roleList = roleService.list();
        model.addAttribute("roleList", roleList);
        return "admin/edit";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result list(String name, @Validated PageParam pageParam) {
        Page<Admin> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
        if (pageParam.getSortOrder().equalsIgnoreCase(SortOrderEnum.ASC.getDesc())) {
            page.addOrder(OrderItem.asc(pageParam.getSort()));
        } else {
            page.addOrder(OrderItem.desc(pageParam.getSort()));
        }
        Page<Admin> adminPage = adminService.page(page, new QueryWrapper<Admin>().like(StrUtil.isNotBlank(name), "name", name));
        return Result.success(adminPage);
    }

    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Validated Admin admin) {
        Admin adminByUsername = adminService.getOne(new QueryWrapper<Admin>().eq("username", admin.getUsername()));
        if (ObjectUtil.isNotNull(adminByUsername)) {
            return Result.fail(ResultEnum.USERNAME_EXIST.getCode(), ResultEnum.USERNAME_EXIST.getDesc());
        }
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminService.save(admin);
        return Result.success();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        adminService.removeByIds(ids);
        return Result.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody @Validated Admin admin) {
        Admin adminByUsername = adminService.getOne(new QueryWrapper<Admin>().eq("username", admin.getUsername()));
        if (ObjectUtil.isNotNull(adminByUsername) && adminByUsername.getId() != admin.getId()) {
            return Result.fail(ResultEnum.USERNAME_EXIST.getCode(), ResultEnum.USERNAME_EXIST.getDesc());
        }
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
        return Result.success();
    }

    @PostMapping("/update_status")
    @ResponseBody
    public Result update_status(@RequestParam("ids") List<Integer> ids, Integer status) {
        List<Admin> adminList = adminService.update_status(ids, status);
        return Result.success(adminList);
    }

    @PostMapping("/upload_avatar")
    @ResponseBody
    public Result upload_avatar(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Result.fail();
        }
        String url = uploadService.uploadFile(file.getBytes(), file.getOriginalFilename());
        return Result.success(url);
    }

}

