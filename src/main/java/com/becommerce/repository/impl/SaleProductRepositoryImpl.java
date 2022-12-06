package com.becommerce.repository.impl;

import com.becommerce.model.SaleProductModel;
import com.becommerce.repository.SaleProductRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
@Repository
public class SaleProductRepositoryImpl implements SaleProductRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    @TransactionalAdvice
    public SaleProductModel save(SaleProductModel saleProductModel) {
        entityManager.persist(saleProductModel);
        return saleProductModel;
    }
}
