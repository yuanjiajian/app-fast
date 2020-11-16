package com.app.system.mapper;

import com.app.system.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-10-23
 */
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("SELECT id, username, `password`, `name`, avatar, sort, `status`, create_time, update_time FROM admin WHERE admin.id = #{id} and  admin.deleted=0")
    @Results(id = "adminMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "name", property = "name"),
            @Result(column = "avatar", property = "avatar"),
            @Result(column = "sort", property = "sort"),
            @Result(column = "status", property = "status"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(property = "roleList", column = "id", many = @Many(select = "com.app.system.mapper.RoleMapper.listByAdminId"))
    })
    Admin getRoleWithResourceById(Integer id);
}
