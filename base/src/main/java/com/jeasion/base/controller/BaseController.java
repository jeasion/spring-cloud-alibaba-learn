package com.jeasion.base.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Controller 基础表
 *
 * @author liushanping
 */
public abstract class BaseController implements Serializable {

    private static final String PAGE_NO = "pageNo";
    private static final String PAGE_SIZE = "pageSize";

    private static final String DESC_COLUMNS = "descColumns";

    private static final String ASC_COLUMNS = "ascColumns";

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;


    protected <T> Page<T> getPage() {
        long pageNo = Long.parseLong(this.request.getParameter(PAGE_NO));
        long pageSize = Long.parseLong(this.request.getParameter(PAGE_SIZE));

        return new Page<T>(pageNo, pageSize);
    }

    protected <T> Page<T> getPageBySort() {
        Page<T> page = getPage();

        String[] descColumns = this.request.getParameterValues(DESC_COLUMNS);
        String[] ascColumns = this.request.getParameterValues(ASC_COLUMNS);
        page
                .addOrder(OrderItem.descs(descColumns))
                .addOrder(OrderItem.ascs(ascColumns));

        return page;
    }


    public Result<Void> success() {
        return Result.success();
    }

    public <T> Result<T> success(T data) {
        return Result.success(data);
    }

    public <T> Result<T> failure() {
        return Result.failure();
    }

    public <T> Result<T> failure(T data) {
        return Result.failure(data);
    }


    public <T> Result<T> failure(T data, String errorMsg, String errorCode, String errorDetail) {
        return Result.failure(data, errorMsg, errorCode, errorDetail);
    }


    public <T> Result<Page<T>> pageSuccess(Page<T> pageResult) {
        return Result.success(pageResult);
    }

    public <T> Result<Page<T>> pageFailure(Page<T> pageResult) {
        return Result.failure(pageResult);
    }


}
