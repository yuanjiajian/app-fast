package com.app.system.mapper;

import com.app.system.entity.Resource;
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
public interface ResourceMapper extends BaseMapper<Resource> {
    @Select("SELECT id, parent_id, `name`, url, `type` , icon, `status`, sort, create_time, update_time FROM resource WHERE resource.id IN ( SELECT role_resource.resource_id FROM role_resource WHERE role_resource.role_id = #{id} )")
    List<Resource> listByRoleId(Integer id);
}
