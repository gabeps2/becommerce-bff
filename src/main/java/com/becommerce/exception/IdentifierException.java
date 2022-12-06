package com.becommerce.exception;

import com.becommerce.exception.base.BaseException;
import io.micronaut.http.HttpStatus;

public class IdentifierException extends BaseException {
    public IdentifierException(String message, String detailMessage, String code, HttpStatus httpStatus) {
        super(message, detailMessage, code, httpStatus);
    }
}
