package com.becommerce.mapper;

import com.becommerce.model.*;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named("Mapper")
public class Mapper {

    public PartnerSchema toPartner(PartnerModel partnerModel) {
        PartnerSchema partner = new PartnerSchema();

        partner.setAvaliation(partnerModel.getAvaliation());
        partner.setLocation(partnerModel.getLocation());
        partner.setIcon(partnerModel.getIcon());
        partner.setBackgroundImage(partnerModel.getIcon());
        partner.setCategories(toCategory(new ArrayList<>(partnerModel.getCategories())));
        partner.setCnpj(partnerModel.getCnpj());
        partner.setDescription(partnerModel.getDescription());

        return partner;
    }

    public AddressModel toAddressModel(AddressSchema addressSchema) {
        return AddressModel.builder()
                .city(addressSchema.getCity())
                .state(addressSchema.getState())
                .country(addressSchema.getCountry())
                .number(addressSchema.getNumber())
                .street(addressSchema.getStreet())
                .streetType(addressSchema.getStreetType())
                .neighborhood(addressSchema.getNeighborhood())
                .zipCode(addressSchema.getZipCode())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public List<CategorySchema> toCategory(List<CategoryModel> categories) {
        return categories.stream().map(c -> CategorySchema
                .builder()
                .id(c.getId().toString())
                .name(c.getName())
                .build()
        ).collect(Collectors.toList());
    }

    public List<CategoryModel> toCategoryModel(List<CategorySchema> categories) {
        return categories.stream().map(c -> CategoryModel.builder()
                .name(c.getName())
                .build()
        ).collect(Collectors.toList());
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
