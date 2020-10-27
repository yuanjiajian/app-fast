package com.app.system.entity;

import com.app.system.enums.SortOrderEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PageParam {
    @NotNull(message = "limit不能为空")
    private Integer limit = 10;
    @NotNull(message = "offset不能为空")
    private Integer offset = 0;
    @NotNull(message = "page不能为空")
    private Integer page = 1;
    @NotBlank(message = "sort不能为空")
    private String sort = "sort";
    @NotBlank(message = "sortOrder不能为空")
    private String sortOrder = SortOrderEnum.ASC.getDesc();
}
