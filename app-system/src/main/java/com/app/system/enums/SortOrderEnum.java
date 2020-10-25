package com.app.system.enums;

import lombok.Getter;

@Getter
public enum SortOrderEnum {
    ASC("asc"),DESC("desc");

    private final String desc;

    SortOrderEnum(String desc) {
        this.desc=desc;
    }
}
