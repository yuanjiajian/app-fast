package com.app.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 地址
     */
    private String url;

    /**
     * 类型(0目录，1菜单，2按钮)
     */
    private Integer type;

    /**
     * 图标
     */
    private String icon;

    /**
     * 子菜单
     */
    private List<Menu> childrenMenu = new ArrayList<>();
}
