package com.becommerce.model;

import com.becommerce.exception.base.BaseException;
import com.becommerce.model.enums.ErrorEnum;
import io.micronaut.http.HttpStatus;

public abstract class Service {
    protected BaseException throwsException(ErrorEnum errorEnum, HttpStatus httpStatus) {
        return new BaseException(errorEnum.getMessage(), errorEnum.getDetailMessage(), errorEnum.getCode(), httpStatus);
    }
}
