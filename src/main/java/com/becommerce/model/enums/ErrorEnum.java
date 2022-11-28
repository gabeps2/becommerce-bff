package com.becommerce.model.enums;

public enum ErrorEnum {
    AUTHENTICATE_USER_ERROR("Invalid credentials.", "Invalid email/password combination.", "BeBFF_01"),
    REGISTER_CUSTOMER_ERROR("Register error", "Error when register new customer", "BeBFF_02"),
    REGISTER_PRODUCT_ERROR("Register error", "Error when register new product", "BeBFF_03"),
    GET_PRODUCT_ERROR("Register error", "Error when get product", "BeBFF_04"),
    INVALID_TOKEN_ERROR("Invalid credentials.", "Invalid JWT token.", "BeBFF_05"),
    REGISTER_PARTNER_ERROR("Register error", "Error when register new partner", "BeBFF_06");

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
