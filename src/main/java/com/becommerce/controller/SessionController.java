package com.becommerce.controller;

import com.becommerce.api.SessionApi;
import com.becommerce.model.AuthenticationRequest;
import com.becommerce.model.SessionResponse;
import com.becommerce.service.AuthenticateUserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;

@Controller
public class SessionController implements SessionApi {

    @Inject
    private AuthenticateUserService authenticateUserService;

    @Override
    public HttpResponse<SessionResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return HttpResponse.ok(authenticateUserService
                .authenticate(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
    }
}
