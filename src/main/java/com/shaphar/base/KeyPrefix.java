package com.shaphar.base;


public enum KeyPrefix {

    SIT_PREFIX(0, "ERP_SIT"),
    MOBILE_PREFIX(-1, "MOBILE_ERP");



    private int code;
    private String message;

    private KeyPrefix(int code, String message) {
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

    private KeyPrefix(Object... args) {
        this.message = String.format(this.message, args);
    }
}