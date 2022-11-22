package com.becommerce.exception;

public enum ErrorEnum {
    REGISTER_USER_ERROR("0x01");

    private final String code;

    ErrorEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
