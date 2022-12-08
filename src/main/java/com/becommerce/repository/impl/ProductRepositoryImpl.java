package com.becommerce.repository.impl;

import com.becommerce.model.ProductModel;
import com.becommerce.repository.ProductRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    @TransactionalAdvice
    public Optional<ProductModel> findById(UUID id) {
        String qlString = "SELECT pm FROM ProductModel as pm WHERE id = :id";
        return entityManager
                .createQuery(qlString, ProductModel.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    @ReadOnly
    @TransactionalAdvice
    public List<ProductModel> findByPartner(UUID partnerId) {
        String qlString = "SELECT pm FROM ProductModel as pm WHERE partner_id = :id";
        return entityManager
                .createQuery(qlString, ProductModel.class)
                .setParameter("id", partnerId)
                .getResultList();
    }

    @Override
    @ReadOnly
    @TransactionalAdvice
    public List<ProductModel> findMany(List<UUID> ids) {
        String qlString = "SELECT pm FROM ProductModel as pm WHERE id IN :ids";
        return entityManager
                .createQuery(qlString, ProductModel.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    @TransactionalAdvice
    @ReadOnly
    public List<ProductModel> findByFilter(String filter) {
        String qlString = "SELECT pm FROM ProductModel as pm WHERE name LIKE :filter";
        return entityManager
                .createQuery(qlString, ProductModel.class)
                .setParameter("filter", "%" + filter + "%")
                .getResultList();
    }

    @Override
    @ReadOnly
    @TransactionalAdvice
    public List<ProductModel> findAll() {
        String qlString = "SELECT pm FROM ProductModel as pm";
        TypedQuery<ProductModel> query = entityManager.createQuery(qlString, ProductModel.class);
        return query.getResultList();
    }

    @Override
    @TransactionalAdvice
    public ProductModel save(ProductModel productModel) {
        entityManager.persist(productModel);
        return productModel;
    }
}
