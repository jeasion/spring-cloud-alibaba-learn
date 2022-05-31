package com.jeasion.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liushanping
 */
@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    EXCEPTION("000","系统繁忙，请稍候再试"),
    TIME_OUT_EXCEPTION("001","请求超时，请稍候再试"),
    PARAMETER_EXCEPTION("100","参数类型转换异常");


    private String code;
    private String errorMsg;






}
