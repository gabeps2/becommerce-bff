package com.becommerce.service;

import com.becommerce.model.PartnerPageSchema;
import com.becommerce.model.ProductSchema;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductSchema getById(String id);

    List<ProductSchema> getByPartner(String partnerId);

    void save(ProductSchema productSchema);

    List<ProductSchema> findByFilter(String token, String filter);

    void registerProduct(ProductSchema productSchema, String token);
}
