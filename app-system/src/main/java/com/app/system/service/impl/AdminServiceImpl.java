package com.app.system.service.impl;

import com.app.system.entity.Admin;
import com.app.system.entity.AdminRole;
import com.app.system.entity.Role;
import com.app.system.mapper.AdminMapper;
import com.app.system.service.AdminRoleService;
import com.app.system.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-10-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    @Transactional
    public List<Admin> update_status(List<Integer> ids, Integer status) {
        List<Admin> adminList = this.listByIds(ids);
        for (Admin admin : adminList) {
            admin.setStatus(status);
            admin.setUpdateTime(LocalDateTime.now());
            this.updateById(admin);
        }
        return adminList;
    }

    @Override
    @Transactional
    public void add(Admin admin) {
        this.save(admin);
        List<AdminRole> adminRoleList = getAdminRoles(admin);
        adminRoleService.saveBatch(adminRoleList);
    }

    /**
     * 根据管理员对应角色集合生成管理员角色的关联关系集合
     *
     * @param admin
     * @return
     */
    private List<AdminRole> getAdminRoles(Admin admin) {
        List<Role> roleList = admin.getRoleList();
        return roleList.stream().map(role -> {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(admin.getId());
            adminRole.setRoleId(role.getId());
            return adminRole;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void edit(Admin admin) {
        this.updateById(admin);
        adminRoleService.remove(new QueryWrapper<AdminRole>().in("admin_id", admin.getId()));
        List<AdminRole> adminRoleList = getAdminRoles(admin);
        adminRoleService.saveBatch(adminRoleList);
    }

    @Override
    public void delete(List<Integer> ids) {
        this.removeByIds(ids);
        adminRoleService.remove(new QueryWrapper<AdminRole>().in("admin_id", ids));
    }

    @Override
    public Admin getRoleWithResourceById(Integer id) {
        return this.baseMapper.getRoleWithResourceById(id);
    }


}
