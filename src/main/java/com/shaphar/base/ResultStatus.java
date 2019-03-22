package com.shaphar.base;


public enum ResultStatus {

    SUCCESS(0, "成功"),
    FAILD(-2, "请求信息不存在"),
    EXCEPTION(-1, "系统异常"),
    DEL_SUCCESS(1, "deleted"),
    NOT_FOUND(-3, "not_found"),
    SYSTEM_ERROR(10001, "系统错误");


    private int code;
    private String message;

    private ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return this.name();
    }

    public String getOutputName() {
        return this.name();
    }

    public String toString() {
        return this.getName();
    }

    private ResultStatus(Object... args) {
        this.message = String.format(this.message, args);
    }
}