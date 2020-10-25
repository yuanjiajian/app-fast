package com.app.system.entity;

import com.app.system.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code = ResultEnum.SUCCESS.getCode();
    private String message = ResultEnum.SUCCESS.getDesc();
    private Object data = null;

    public static Result success() {
        return new Result();
    }

    public static Result success(String message) {
        return new Result(ResultEnum.SUCCESS.getCode(), message, null);
    }

    public static Result success(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), data);
    }

    public static Result success(String message, Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static Result fail() {
        return new Result(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDesc(), null);
    }

    public static Result fail(String message) {
        return new Result(ResultEnum.FAIL.getCode(), message, null);
    }

    public static Result fail(Integer code, String message) {
        return new Result(code, message, null);
    }
}
