package com.becommerce.exception.base;

import io.micronaut.http.HttpStatus;

import java.io.Serializable;

public class BaseException extends RuntimeException implements Serializable {

    private final String message;
    private final String detailMessage;
    private final String code;

    private final HttpStatus httpStatus;

    public BaseException(String message, String detailMessage, String code, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.detailMessage = detailMessage;
        this.code = code;
        this.httpStatus = httpStatus;
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

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
