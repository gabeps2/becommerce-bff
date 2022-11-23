package com.becommerce.model.enums;

public enum ErrorEnum {
    AUTHENTICATE_USER_ERROR("Invalid credentials.", "Invalid email/password combination.", "BeBFF_01"),
    REGISTER_CUSTOMER_ERROR("Error when register new customer", "Error when register new customer", "BeBFF_02");

    private final String message;
    private final String detailMessage;
    private final String code;

    ErrorEnum(String message, String detailMessage, String code) {
        this.message = message;
        this.detailMessage = detailMessage;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    public String getCode() {
        return this.code;
    }
}
