package com.app.system.mapper;

import com.app.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-10-28
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT id, `name`, `status`, sort, create_time, update_time FROM role WHERE role.id IN ( SELECT admin_role.role_id FROM admin_role WHERE admin_role.admin_id = #{id} ) and  role.deleted=0")
    @Results(id = "roleMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "sort", property = "sort"),
            @Result(column = "status", property = "status"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(property = "resourceList", column = "id", many = @Many(select = "com.app.system.mapper.ResourceMapper.listByRoleId"))
    })
    List<Role> listByAdminId(Integer id);
}
