package com.app.system.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    USERNAME_EXIST(2, "用户名存在"),
    ROLE_NAME_EXIST(3,"角色名存在"),
    CAPTCHA_ERROR(4,"图形验证码不正确"),
    USERNAME_NOT_EXIST(5,"用户名不存在"),
    USERNAME_DISABLE(6,"用户已禁用"),
    PASSWORD_ERROR(7,"密码不正确"),
    UNAUTHORIZED(8,"未授权"),
    NO_PERMISSION(9,"没有权限");
    private final int code;
    private final String desc;

    ResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
