package com.app.system.entity;

import com.app.system.enums.SortOrderEnum;
import lombok.Data;

@Data
public class PageParam {
    private Integer limit = 10;
    private Integer offset = 0;
    private Integer page = 1;
    private String sort = "sort";
    private String sortOrder = SortOrderEnum.ASC.getDesc();
}
