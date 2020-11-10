package com.app.system.service.impl;

import cn.hutool.core.collection.CollUtil;
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
        roleResourceService.remove(new QueryWrapper<RoleResource>().in("resource_id",ids));
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
}
