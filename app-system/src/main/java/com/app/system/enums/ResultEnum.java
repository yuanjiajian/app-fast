package com.app.system.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"), FAIL(1, "失败");

    private final int code;
    private final String desc;

    ResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
