package com.becommerce.service;

import com.becommerce.model.SessionResponse;

public interface AuthenticateUserService {
    SessionResponse authenticate(String email, String password);
}
