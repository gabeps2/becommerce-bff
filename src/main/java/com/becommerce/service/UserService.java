package com.becommerce.service;

import com.becommerce.model.UserModel;
import com.becommerce.model.UserSchema;

public interface UserService {
    UserModel updateUser(UserModel userModel);
    UserModel updateUser(UserSchema userSchema);
}
