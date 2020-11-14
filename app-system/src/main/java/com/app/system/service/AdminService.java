package com.app.system.service;

import com.app.system.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-10-23
 */
public interface AdminService extends IService<Admin> {

    List<Admin> update_status(List<Integer> ids, Integer status);

    void add(Admin admin);

    void edit(Admin admin);

    void delete(List<Integer> ids);

    Admin getRoleWithResourceById(Integer id);
}
