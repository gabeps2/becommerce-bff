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

@Singleton
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public Optional<ProductModel> getById(String id) {
        return Optional.ofNullable(entityManager.find(ProductModel.class, id));
    }

    @Override
    @ReadOnly
    @TransactionalAdvice
    public List<ProductModel> getByPartner(String partnerId) {
        String qlString = "SELECT pm FROM ProductModel as pm WHERE partner_id = :id";
        return entityManager
                .createQuery(qlString, ProductModel.class)
                .setParameter("id", partnerId)
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
