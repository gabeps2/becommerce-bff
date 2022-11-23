package com.becommerce.repository;

import com.becommerce.model.ProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<ProductModel> getById(String id);
    List<ProductModel> getByPartner(String id);
    List<ProductModel> findAll();

    ProductModel save(ProductModel productModel);
}
