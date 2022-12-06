package com.becommerce.repository;

import com.becommerce.model.PartnerModel;
import com.becommerce.model.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<UserModel> findById(UUID id);

    Optional<UserModel> findByName(String name);

    Optional<UserModel> findByEmail(String email);

    UserModel save(UserModel user);

    List<UserModel> findAll();

    UserModel update(UserModel user);
}
