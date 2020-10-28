package com.app.system.service;

import com.app.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-10-28
 */
public interface RoleService extends IService<Role> {

    List<Role> update_status(List<Integer> ids, Integer status);
}
