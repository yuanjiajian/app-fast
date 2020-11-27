package com.app.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.app.system.entity.Admin;
import com.app.system.entity.Menu;
import com.app.system.entity.Resource;
import com.app.system.entity.RoleResource;
import com.app.system.mapper.ResourceMapper;
import com.app.system.service.ResourceService;
import com.app.system.service.RoleResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    private RoleResourceService roleResourceService;

    @Override
    public List<Resource> update_status(List<Integer> ids, Integer status) {
        List<Resource> resourceList = this.listByIds(ids);
        for (Resource resource : resourceList) {
            resource.setStatus(status);
            resource.setUpdateTime(LocalDateTime.now());
            this.updateById(resource);
        }
        return resourceList;
    }

    @Override
    @Transactional
    public void delete(List<Integer> ids) {
        this.removeByIds(ids);
        roleResourceService.remove(new QueryWrapper<RoleResource>().in("resource_id", ids));
        List<Resource> resourceList = this.list(new QueryWrapper<Resource>().in("parent_id", ids));
        if (CollUtil.isNotEmpty(resourceList)) {
            List<Integer> idList = resourceList.stream().map(Resource::getId).collect(Collectors.toList());
            this.delete(idList);
        }
    }

    @Override
    public List<Resource> listByRoleId(Integer id) {
        return this.baseMapper.listByRoleId(id);
    }

    /**
     * 获取当前用户对应资源集合
     *
     * @param admin
     * @return
     */
    @Override
    public List<Resource> getResourceList(Admin admin) {
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

    @Override
    public List<Menu> getMenuList(List<Resource> resourceList, Integer parentId) {
        List<Menu> menuList = new ArrayList<>();
        for (Resource resource : resourceList) {
            Integer id = resource.getId();
            Integer pid = resource.getParentId();
            if (parentId == pid) {
                List<Menu> list = this.getMenuList(resourceList, id);
                Menu menu = new Menu();
                BeanUtil.copyProperties(resource, menu);
                menu.setChildrenMenu(list);
                menuList.add(menu);
            }
        }
        return menuList;
    }
}
