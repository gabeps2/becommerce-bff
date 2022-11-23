package com.becommerce.service.impl;

import com.becommerce.mapper.Mapper;
import com.becommerce.model.Product;
import com.becommerce.model.ProductModel;
import com.becommerce.repository.ProductRepository;
import com.becommerce.service.ProductService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named("ProductService")
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductRepository productRepository;
    @Inject
    private Mapper mapper;

    @Override
    public Optional<ProductModel> getById(String id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getByPartner(String id) {
        return mapper.toProducts(productRepository.getByPartner(id));
    }

    @Override
    public void save(ProductModel productModel) {
        productRepository.save(productModel);
    }
}
