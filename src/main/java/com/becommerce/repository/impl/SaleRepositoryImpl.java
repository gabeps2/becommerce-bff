package com.becommerce.repository.impl;

import com.becommerce.model.SaleModel;
import com.becommerce.repository.SaleRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Repository
public class SaleRepositoryImpl implements SaleRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public Optional<SaleModel> findById(String id) {
        return Optional.ofNullable(entityManager.find(SaleModel.class, id));
    }

    @Override
    @TransactionalAdvice
    public Optional<SaleModel> findByNumber(Integer number) {
        String qlString = "SELECT pm FROM ProductModel as pm WHERE number = :number";
        return entityManager
                .createQuery(qlString, SaleModel.class)
                .setParameter("number", number)
                .getResultList().stream().findFirst();
    }

    @Override
    @TransactionalAdvice
    public List<SaleModel> findByUser(UUID userId) {
        String qlString = "SELECT pm FROM SaleModel as pm WHERE user_id = :userId";
        return entityManager
                .createQuery(qlString, SaleModel.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    @TransactionalAdvice
    public List<SaleModel> findByProduct(String productId) {
        String qlString = "SELECT pm FROM ProductModel as pm WHERE productId = :productId";
        return entityManager
                .createQuery(qlString, SaleModel.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    @TransactionalAdvice
    public SaleModel save(SaleModel saleModel) {
        entityManager.persist(saleModel);
        return saleModel;
    }
}
