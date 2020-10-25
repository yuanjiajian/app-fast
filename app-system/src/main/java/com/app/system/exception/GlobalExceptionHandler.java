package com.app.system.exception;

import com.app.system.entity.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.fail(message);
    }

    @ExceptionHandler(value = BindException.class)
    public Result BindException(BindException ex) {
        String message = ex.getAllErrors().get(0).getDefaultMessage();
        return Result.fail(message);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result ConstraintViolationException(ConstraintViolationException ex) {
        return Result.fail(ex.getMessage());
    }
}
