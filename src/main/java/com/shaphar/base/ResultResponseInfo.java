package com.shaphar.base;

import java.io.Serializable;

/**
 * Created by v054 on 2019/2/27.
 */
public class ResultResponseInfo<T> extends AbstractResult implements Serializable {
    private T data;
    private Long count;

    protected ResultResponseInfo(ResultStatus status, String message) {
        super(status, message);
    }
    protected ResultResponseInfo(ResultStatus status) {
        super(status);
    }
    public static <T> ResultResponseInfo<T> build() {
        return new ResultResponseInfo(ResultStatus.SUCCESS, (String)null);
    }

    public static <T> ResultResponseInfo<T> build(String message) {
        return new ResultResponseInfo(ResultStatus.SUCCESS, message);
    }

    public static <T> ResultResponseInfo<T> error(ResultStatus status) {
        return new ResultResponseInfo<T>(status);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCount() {
        return this.count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void success(T value) {
        this.success();
        this.data = value;
        this.count = 0L;
    }
}
