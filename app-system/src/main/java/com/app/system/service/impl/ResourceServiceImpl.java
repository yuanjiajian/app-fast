package com.app.system.service.impl;

import com.app.system.entity.Admin;
import com.app.system.entity.Resource;
import com.app.system.mapper.ResourceMapper;
import com.app.system.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}
