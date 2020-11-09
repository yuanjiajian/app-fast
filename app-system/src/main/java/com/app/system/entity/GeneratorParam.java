package com.app.system.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class GeneratorParam implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "项目路径不能为空")
    private String projectPath;
    private String packageName;
    @NotBlank(message = "数据库地址不能为空")
    private String dbUrl;
    @NotBlank(message = "数据库账户不能为空")
    private String dbUsername;
    @NotBlank(message = "数据库密码不能为空")
    private String dbPassword;
    @NotEmpty(message = "数据库表不能为空")
    private List<String> tables;
}
