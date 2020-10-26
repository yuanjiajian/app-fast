package com.app.system.service.impl;

import com.app.system.entity.Admin;
import com.app.system.mapper.AdminMapper;
import com.app.system.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-10-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    @Transactional
    public List<Admin> update_status(List<Integer> ids, Integer status) {
        List<Admin> adminList = this.listByIds(ids);
        for (Admin admin:adminList) {
            admin.setStatus(status);
            admin.setUpdateTime(LocalDateTime.now());
            this.updateById(admin);
        }
        return adminList;
    }
}
