package com.becommerce.utils;

import com.becommerce.exception.IdentifierException;
import com.becommerce.model.enums.ErrorEnum;
import io.micronaut.http.HttpStatus;

import java.util.UUID;

import static com.becommerce.model.enums.ErrorEnum.IDENTIFIER_ERROR;

public class UUIDUtils {
    public static UUID getFromString(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new IdentifierException(
                    IDENTIFIER_ERROR.getMessage(),
                    IDENTIFIER_ERROR.getDetailMessage(),
                    IDENTIFIER_ERROR.getCode(),
                    HttpStatus.PRECONDITION_FAILED
            );
        }
    }
}
