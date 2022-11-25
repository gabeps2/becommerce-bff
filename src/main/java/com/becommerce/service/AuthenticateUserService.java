package com.becommerce.service;

import com.becommerce.model.SessionSchema;

public interface AuthenticateUserService {
    SessionSchema authenticate(String email, String password);
}
