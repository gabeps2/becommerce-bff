package com.becommerce.repository;

import com.becommerce.model.ProductModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<ProductModel> findById(UUID id);
    List<ProductModel> getByPartner(String id);
    List<ProductModel> findMany(List<UUID> id);
    List<ProductModel> findByFilter(String filter);
    List<ProductModel> findAll();
    ProductModel save(ProductModel productModel);
}
