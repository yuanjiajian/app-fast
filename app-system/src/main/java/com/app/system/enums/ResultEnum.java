package com.app.system.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    USERNAME_EXIST(2, "用户名存在"),
    ROLE_NAME_EXIST(3,"角色名存在");
    private final int code;
    private final String desc;

    ResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
