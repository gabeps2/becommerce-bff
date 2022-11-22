package com.becommerce.repository.impl;

import com.becommerce.model.PartnerModel;
import com.becommerce.model.Product;
import com.becommerce.model.ProductModel;
import com.becommerce.repository.ProductRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.ReadOnly;

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
    public List<ProductModel> getByPartner(int id) {
        String qlString = "SELECT tp FROM ProductModel as tp WHERE id = :id";

        return entityManager
                .createQuery(qlString, ProductModel.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    @ReadOnly
    public List<ProductModel> findAll() {
        String qlString = "SELECT tp FROM ProductModel as tp";
        TypedQuery<ProductModel> query = entityManager.createQuery(qlString, ProductModel.class);
        return query.getResultList();
    }


}
