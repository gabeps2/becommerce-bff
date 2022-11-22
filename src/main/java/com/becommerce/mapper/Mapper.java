package com.becommerce.mapper;

import com.becommerce.model.Partner;
import com.becommerce.model.PartnerModel;
import com.becommerce.model.Product;
import com.becommerce.model.ProductModel;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named("Mapper")
public class Mapper {
    public Partner toPartner(PartnerModel partnerModel) {
        Partner partner = new Partner();

        partner.setName(partnerModel.getName());
        partner.setAvaliation(partnerModel.getAvaliation());
        partner.setLocation(partnerModel.getLocation());
        partner.setIcon(partnerModel.getIcon());
        partner.setBackgroundImage(partnerModel.getIcon());
        partner.setCategory(partnerModel.getName());

        return partner;
    }

    public Product toProduct(ProductModel productModel) {
        Product product = new Product();

        product.setDescription(productModel.getDescription());
        product.setName(productModel.getName());
        product.setPrice(0.0);
        product.setQuantity(productModel.getQuantity());
        product.setImage("");

        return product;
    }

    public List<Product> toProducts(List<ProductModel> productModels) {
        Stream<Product> products;
        products = productModels.stream().map(this::toProduct);
        return products.collect(Collectors.toList());
    }
}
