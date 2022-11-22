package com.becommerce.service;

import com.becommerce.model.Product;
import com.becommerce.model.ProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductModel> getById(String id);
    List<Product> getByPartner(int id);
}
