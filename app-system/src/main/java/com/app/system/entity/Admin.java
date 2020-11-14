package com.app.system.entity;

import com.app.system.validation.LoginGroup;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空",groups = {LoginGroup.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",groups = {LoginGroup.class})
    private String password;
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空")
    private String avatar;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态(0启用，1禁用)
     */
    private Integer status;

    /**
     * 逻辑删除（0未删除，1已删除）
     */
    @TableLogic(value = "0",delval = "1")
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 角色集合
     */
    @TableField(exist = false)
    private List<Role> roleList = new ArrayList<>();
}
