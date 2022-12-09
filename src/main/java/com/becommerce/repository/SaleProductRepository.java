package com.becommerce.repository;

import com.becommerce.model.SaleProductModel;

import java.util.Optional;
import java.util.UUID;

public interface SaleProductRepository {

    SaleProductModel save(SaleProductModel saleProductModel);

    Optional<SaleProductModel> findSale(UUID saleId, UUID productId);

}
