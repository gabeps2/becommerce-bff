package com.becommerce.repository.impl;

import com.becommerce.model.PartnerModel;
import com.becommerce.model.UserModel;
import com.becommerce.repository.UserRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    @TransactionalAdvice
    public Optional<UserModel> findById(UUID id) {
        String qlString = "SELECT p FROM UserModel as p WHERE id = :id";
        return entityManager
                .createQuery(qlString, UserModel.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserModel> findByName(String name) {
        return Optional.ofNullable(entityManager.find(UserModel.class, name));
    }

    @Override
    @TransactionalAdvice
    public Optional<UserModel> findByEmail(String email) {
        String qlString = "SELECT p FROM UserModel as p WHERE email = :email";
        return entityManager
                .createQuery(qlString, UserModel.class)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }

    @Override
    @TransactionalAdvice
    public UserModel save(UserModel partner) {
        entityManager.persist(partner);
        return partner;
    }

    @Override
    @ReadOnly
    @TransactionalAdvice
    public List<UserModel> findAll() {
        String qlString = "SELECT u FROM UserModel as u";
        TypedQuery<UserModel> query = entityManager.createQuery(qlString, UserModel.class);
        return query.getResultList();
    }

    @Override
    @TransactionalAdvice
    public UserModel update(UserModel user) {
        entityManager.merge(user);
        return user;
    }
}
