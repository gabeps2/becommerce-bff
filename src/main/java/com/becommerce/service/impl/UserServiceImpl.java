package com.becommerce.service.impl;

import com.becommerce.model.UserModel;
import com.becommerce.model.UserSchema;
import com.becommerce.repository.UserRepository;
import com.becommerce.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    public UserModel updateUser(UserModel userModel) {
        return userRepository.update(userModel);
    }

    @Override
    public UserModel updateUser(UserSchema userSchema) {
        return null;
    }
}
