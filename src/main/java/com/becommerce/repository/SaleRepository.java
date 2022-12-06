package com.becommerce.repository;

import com.becommerce.model.SaleModel;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleRepository {

    Optional<SaleModel> findById(String id);
    Optional<SaleModel> findByNumber(Integer number);
    List<SaleModel> findByUser(UUID userId);

    List<SaleModel> findByProduct(String productId);

    SaleModel save(@NotBlank SaleModel saleModel);
}
