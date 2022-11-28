package com.becommerce.service;

import com.becommerce.model.SessionSchema;

import java.util.UUID;

public interface AuthenticateUserService {
    SessionSchema authenticate(String email, String password);
    UUID getSubject(String token);

    void validateToken(String token);
}
