package com.becommerce.repository;

import com.becommerce.model.PartnerModel;
import com.becommerce.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserModel> findById(String id);

    Optional<UserModel> findByName(String name);

    Optional<UserModel> findByEmail(String email);

    UserModel save(UserModel user);

    List<UserModel> findAll();

    UserModel update(UserModel user);
}
