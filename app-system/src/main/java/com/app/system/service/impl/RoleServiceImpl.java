package com.app.system.service.impl;

import com.app.system.entity.Role;
import com.app.system.mapper.RoleMapper;
import com.app.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

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
}
