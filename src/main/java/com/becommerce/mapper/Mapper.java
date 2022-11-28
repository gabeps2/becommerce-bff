package com.becommerce.mapper;

import com.becommerce.model.*;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public AddressSchema toAddressSchema(AddressModel addressModel) {
        return AddressSchema.builder()
                .city(addressModel.getCity())
                .state(addressModel.getState())
                .country(addressModel.getCountry())
                .number(addressModel.getNumber())
                .street(addressModel.getStreet())
                .streetType(addressModel.getStreetType())
                .neighborhood(addressModel.getNeighborhood())
                .zipCode(addressModel.getZipCode())
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

    public ProductSchema toProductSchema(ProductModel productModel) {
        ProductSchema productSchema = new ProductSchema();

        productSchema.setCategory(productModel.getCategory().getName());
        productSchema.setDescription(productModel.getDescription());
        productSchema.setIcon(productModel.getIcon());
        productSchema.setName(productModel.getName());
        productSchema.setQuantity(productModel.getQuantity());
        productSchema.setImages(productSchema.getImages());

        return productSchema;
    }

    public List<ProductSchema> toProductsSchema(List<ProductModel> productModels) {
        Stream<ProductSchema> products;
        products = productModels.stream().map(this::toProductSchema);
        return products.collect(Collectors.toList());
    }

    public ProductModel toProductModel(ProductSchema productSchema, CategoryModel categoryModel) {
        return ProductModel.builder()
                .name(productSchema.getName())
                .price(productSchema.getPrice())
                .category(categoryModel)
                .icon(productSchema.getIcon())
                .quantity(productSchema.getQuantity())
                .build();

    }

    public PartnerSchema toPartnerSchema(PartnerModel partnerModel) {
        AddressSchema addressSchema = toAddressSchema(partnerModel.getUser().getAddress());
        List<CategorySchema> categorySchema = toCategory(partnerModel.getCategories());
        String partnerName = partnerModel.getName().isEmpty() ? partnerModel.getUser().getName() : partnerModel.getName();

        return PartnerSchema.builder()
                .id(partnerModel.getId().toString())
                .name(partnerName)
                .cnpj(partnerModel.getCnpj())
                .icon(partnerModel.getIcon())
                .avaliation(partnerModel.getAvaliation())
                .location(partnerModel.getLocation())
                .backgroundImage(partnerModel.getBackgroundImage())
                .description(partnerModel.getDescription())
                .categories(categorySchema)
                .address(addressSchema)
                .build();
    }

    public List<PartnerSchema> toPartnersSchema(List<PartnerModel> partnerModels) {
        Stream<PartnerSchema> partners = partnerModels.stream().map(this::toPartnerSchema);
        return partners.collect(Collectors.toList());
    }

    public CategoryModel toCategoryModel(CategorySchema categorySchema) {
        return CategoryModel.builder()
                .name(categorySchema.getName())
                .build();
    }

    public CategorySchema toCategorySchema(CategoryModel categoryModel) {
        return CategorySchema.builder()
                .id(categoryModel.getId().toString())
                .name(categoryModel.getName())
                .build();
    }

    public List<CategorySchema> toCategoriesSchema(List<CategoryModel> categoriesModel) {
        Stream<CategorySchema> categories = categoriesModel.stream().map(this::toCategorySchema);
        return categories.collect(Collectors.toList());
    }

    public NewsSchema toNewSchema(PartnerModel partnerModel) {
        return NewsSchema.builder()
                .partnerId(partnerModel.getId().toString())
                .partnerName(partnerModel.getName())
                .partnerDescription(partnerModel.getDescription())
                .backgroundImg(partnerModel.getBackgroundImage())
                .build();
    }

    public List<NewsSchema> toNewsSchema(List<PartnerModel> partnersModel) {
        Stream<NewsSchema> news = partnersModel.stream().map(this::toNewSchema);
        return news.collect(Collectors.toList());
    }

    public ImageModel toImageModel(String url, ProductModel productModel) {
        return ImageModel.builder().url(url).product(productModel).build();
    }

    public List<ImageModel> toImagesModel(List<String> urls, ProductModel productModel) {
        return urls.stream().map(url -> toImageModel(url, productModel)).collect(Collectors.toList());
    }
}
