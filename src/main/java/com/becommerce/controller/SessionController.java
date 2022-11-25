package com.becommerce.controller;

import com.becommerce.api.SessionApi;
import com.becommerce.model.AuthenticationSchema;
import com.becommerce.model.SessionSchema;
import com.becommerce.service.AuthenticateUserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;

@Controller
public class SessionController implements SessionApi {
    @Inject
    private AuthenticateUserService authenticateUserService;

    @Override
    public HttpResponse<SessionSchema> authenticate(AuthenticationSchema authenticationSchema) {
        return HttpResponse.ok(authenticateUserService
                .authenticate(
                        authenticationSchema.getEmail(),
                        authenticationSchema.getPassword()
                )
        );
    }
}
