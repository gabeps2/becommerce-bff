package com.becommerce.repository.impl;

import com.becommerce.model.PartnerModel;
import com.becommerce.model.SaleProductModel;
import com.becommerce.repository.SaleProductRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<SaleProductModel> findSale(UUID saleId, UUID productId) {
        String qlString = "SELECT spm FROM SaleProductModel as spm WHERE product_id = :productId AND sale_id = :saleId";
        return entityManager
                .createQuery(qlString, SaleProductModel.class)
                .setParameter("productId", productId)
                .setParameter("saleId", saleId)
                .getResultList().stream().findFirst();
    }
}
