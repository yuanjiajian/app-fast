package com.app.system.service;

import com.app.system.entity.Admin;
import com.app.system.entity.Menu;
import com.app.system.entity.Resource;
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
public interface ResourceService extends IService<Resource> {

    List<Resource> update_status(List<Integer> ids, Integer status);

    void delete(List<Integer> ids);

    List<Resource> listByRoleId(Integer id);

    List<Resource> getResourceList(Admin admin);

    List<Menu> getMenuList(List<Resource> resourceList);
}
