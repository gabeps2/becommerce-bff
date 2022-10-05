package com.becommerce.exception;

import com.becommerce.exception.base.BaseException;
import io.micronaut.http.HttpStatus;

public class AuthenticateUserException extends BaseException {
    public AuthenticateUserException(String message, String detailMessage, String code, HttpStatus httpStatus) {
        super(message, detailMessage, code, httpStatus);
    }
}
