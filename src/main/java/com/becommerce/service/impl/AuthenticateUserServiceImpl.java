package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.model.SessionResponse;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.repository.SessionRepository;
import com.becommerce.service.AuthenticateUserService;
import io.micronaut.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AuthenticateUserServiceImpl implements AuthenticateUserService {

    @Inject
    private SessionRepository sessionRepository;

    @Override
    public SessionResponse authenticate(String email, String password) {
        String user = sessionRepository.findOne();

        if (user == null) {
            throw new AuthenticateUserException(
                    ErrorEnum.AUTHENTICATE_USER_ERROR.getMessage(),
                    ErrorEnum.AUTHENTICATE_USER_ERROR.getDetailMessage(),
                    ErrorEnum.AUTHENTICATE_USER_ERROR.getCode(),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        SessionResponse sessionResponse = new SessionResponse();
        sessionResponse.setAccessToken("access_token_jwt");
        sessionResponse.setExpiresIn("3299");

        return sessionResponse;
    }
}
