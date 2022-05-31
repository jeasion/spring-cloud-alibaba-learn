package com.jeasion.exception;

import com.jeasion.base.controller.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author liushanping
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> exceptionHandler(Exception e) {
        return Result.failure(ErrorCodeEnum.EXCEPTION, e);
    }

    @ExceptionHandler(NumberFormatException.class)
    public Result<Void> numberFormatException(NumberFormatException e) {
        return Result.failure(ErrorCodeEnum.PARAMETER_EXCEPTION, e);
    }

}
