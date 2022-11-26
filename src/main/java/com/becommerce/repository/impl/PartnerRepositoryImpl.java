package com.becommerce.repository.impl;

import com.becommerce.model.PartnerModel;
import com.becommerce.repository.PartnerRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Singleton
@Repository
public class PartnerRepositoryImpl implements PartnerRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    @ReadOnly
    public Optional<PartnerModel> findById(String id) {
        return Optional.ofNullable(entityManager.find(PartnerModel.class, id));
    }

    @Override
    public Optional<PartnerModel> findByName(String name) {
        return Optional.ofNullable(entityManager.find(PartnerModel.class, name));
    }

    @Override
    @TransactionalAdvice
    public Optional<PartnerModel> findByEmail(String email) {
        String qlString = "SELECT p FROM PartnerModel as p WHERE email = :email";
        return entityManager
                .createQuery(qlString, PartnerModel.class)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }

    @Override
    @TransactionalAdvice
    public PartnerModel save(PartnerModel partner) {
        entityManager.persist(partner);
        return partner;
    }

    @Override
    @TransactionalAdvice
    public PartnerModel saveWithException(String name) {
        return null;
    }

    @Override
    @TransactionalAdvice
    public void deleteById(String id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    @ReadOnly
    public List<PartnerModel> findAll() {
        String qlString = "SELECT p FROM PartnerModel as p";
        TypedQuery<PartnerModel> query = entityManager.createQuery(qlString, PartnerModel.class);
        return query.getResultList();
    }

    @Override
    @TransactionalAdvice
    public List<PartnerModel> findWithLimit(Integer limit) {
        String qlString = "SELECT p FROM PartnerModel as p ORDER BY createdAt DESC";
        TypedQuery<PartnerModel> query = entityManager
                .createQuery(qlString, PartnerModel.class)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    @TransactionalAdvice
    public int update(long id, String name) {
        return 0;
    }
}
