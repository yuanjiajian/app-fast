package com.app.system.mapper;

import com.app.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-10-28
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT id, `name`, `status`, sort, create_time, update_time FROM role WHERE role.id IN ( SELECT admin_role.role_id FROM admin_role WHERE admin_role.admin_id = #{id} )")
    List<Role> listByAdminId(Integer id);
}
