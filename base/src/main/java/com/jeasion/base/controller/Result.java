package com.jeasion.base.controller;

import com.jeasion.exception.ErrorCodeEnum;
import lombok.Data;

/**
 * 结果表.
 *
 * @author liushanping
 */
@Data
public class Result<T> {

    private T data;

    private Boolean success;

    private String errorMsg;

    private String errorCode;

    private String errorDetail;

    public Result(T data, Boolean success, String errorMsg, String errorCode, String errorDetail) {
        this.data = data;
        this.success = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
    }

    public Result(Boolean success, String errorCode, String errorMsg, String errorDetail) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorDetail = errorDetail;
    }


    public Result(T data) {
        this.data = data;
        this.success = Boolean.TRUE;
    }

    public Result() {
        this.success = Boolean.TRUE;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static Result<Void> success() {
        return new Result<>();
    }

    public static <T> Result<T> failure() {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> failure(T data) {
        Result<T> result = failure();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failure(String errorCode, String errorMsg, String errorDetail) {
        return new Result<>(Boolean.FALSE, errorCode, errorMsg, errorDetail);
    }

    public static <T> Result<T> failure(ErrorCodeEnum errorCodeEnum, Exception e) {
        return new Result<>(Boolean.FALSE, errorCodeEnum.getCode(), errorCodeEnum.getErrorMsg(), e.getLocalizedMessage());
    }

    public static <T> Result<T> failure(T data, String errorCode, String errorMsg, String errorDetail) {
        return new Result<>(data, Boolean.FALSE, errorCode, errorMsg, errorDetail);
    }

}
