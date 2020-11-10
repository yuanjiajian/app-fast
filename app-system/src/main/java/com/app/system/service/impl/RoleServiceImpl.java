package com.app.system.service.impl;

import com.app.system.entity.AdminRole;
import com.app.system.entity.Resource;
import com.app.system.entity.Role;
import com.app.system.entity.RoleResource;
import com.app.system.mapper.RoleMapper;
import com.app.system.service.AdminRoleService;
import com.app.system.service.RoleResourceService;
import com.app.system.service.RoleService;
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
 * @since 2020-10-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private RoleResourceService roleResourceService;


    @Override
    @Transactional
    public List<Role> update_status(List<Integer> ids, Integer status) {
        List<Role> roleList = this.listByIds(ids);
        for (Role role : roleList) {
            role.setStatus(status);
            role.setUpdateTime(LocalDateTime.now());
            this.updateById(role);
        }
        return roleList;
    }

    @Override
    public List<Role> listByAdminId(Integer id) {
        return this.baseMapper.listByAdminId(id);
    }

    @Override
    @Transactional
    public void add(Role role) {
        this.save(role);
        List<RoleResource> roleResourceList = getRoleResources(role);
        roleResourceService.saveBatch(roleResourceList);
    }

    /**
     * 根据角色对应资源集合生成角色资源的关联关系集合
     * @param role
     * @return
     */
    private List<RoleResource> getRoleResources(Role role) {
        List<Resource> resourceList = role.getResourceList();
        return resourceList.stream().map(resource -> {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(role.getId());
            roleResource.setResourceId(resource.getId());
            return roleResource;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void edit(Role role) {
        this.updateById(role);
        roleResourceService.remove(new QueryWrapper<RoleResource>().in("role_id",role.getId()));
        List<RoleResource> roleResourceList = getRoleResources(role);
        roleResourceService.saveBatch(roleResourceList);
    }

    @Override
    @Transactional
    public void delete(List<Integer> ids) {
        this.removeByIds(ids);
        adminRoleService.remove(new QueryWrapper<AdminRole>().in("role_id",ids));
        roleResourceService.remove(new QueryWrapper<RoleResource>().in("role_id",ids));
    }
}
