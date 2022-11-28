package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.mapper.Mapper;
import com.becommerce.model.CategoryModel;
import com.becommerce.model.PartnerModel;
import com.becommerce.model.ProductModel;
import com.becommerce.model.ProductSchema;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.repository.CategoryRepository;
import com.becommerce.repository.ImageRepository;
import com.becommerce.repository.ProductRepository;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.PartnerService;
import com.becommerce.service.ProductService;
import com.becommerce.utils.TokenUtils;
import io.micronaut.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.becommerce.model.enums.ErrorEnum.GET_PRODUCT_ERROR;
import static com.becommerce.model.enums.ErrorEnum.REGISTER_PRODUCT_ERROR;

@Singleton
@Named("ProductService")
public class ProductServiceImpl implements ProductService {

    @Inject
    private AuthenticateUserService authenticateUserService;

    @Inject
    private PartnerService partnerService;
    @Inject
    private ProductRepository productRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private ImageRepository imageRepository;
    @Inject
    private Mapper mapper;


    @Override
    public Optional<ProductSchema> getById(String id) {
        Optional<ProductModel> productModel = productRepository.getById(id);
        if (productModel.isEmpty()) {
            throwsException(GET_PRODUCT_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return Optional.ofNullable(mapper.toProductSchema(productModel.get()));
    }

    @Override
    public List<ProductSchema> getByPartner(String partnerId) {
        List<ProductModel> productsModel = productRepository.getByPartner(partnerId);
        return mapper.toProductsSchema(productsModel);
    }

    @Override
    public void save(ProductSchema productSchema) {
        Optional<CategoryModel> categoryModel = categoryRepository.findByName(productSchema.getCategory());
        ProductModel productModel;
        if (categoryModel.isEmpty()) {
            CategoryModel categoryResponse = categoryRepository.save(CategoryModel.builder().name(productSchema.getCategory()).build());
            productModel = productRepository.save(mapper.toProductModel(productSchema, categoryResponse));
        }
        productModel = productRepository.save(mapper.toProductModel(productSchema, categoryModel.get()));
        imageRepository.saveMany(mapper.toImagesModel(productSchema.getImages(), productModel));
    }

    @Override
    public List<ProductSchema> findByFilter(String token, String filter) {
        return mapper.toProductsSchema(productRepository.findByFilter(filter));
    }

    @Override
    public void registerProduct(ProductSchema productSchema, String token) {
        Optional<PartnerModel> partner = partnerService.getByUserId(authenticateUserService.getSubject(token));
        CategoryModel category;

        if (partner.isEmpty()) throw throwsException(REGISTER_PRODUCT_ERROR, HttpStatus.PRECONDITION_FAILED);

        Optional<CategoryModel> categoryModel = categoryRepository.findByName(productSchema.getCategory());

        if (categoryModel.isEmpty())
            category = categoryRepository.save(CategoryModel.builder().name(productSchema.getCategory()).build());
        else category = categoryModel.get();

        ProductModel productModel = ProductModel.builder()
                .category(category)
                .partner(partner.get())
                .name(productSchema.getName())
                .price(productSchema.getPrice())
                .icon(productSchema.getIcon())
                .quantity(productSchema.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        productModel = productRepository.save(productModel);
        imageRepository.saveMany(mapper.toImagesModel(productSchema.getImages(), productModel));
    }

    AuthenticateUserException throwsException(ErrorEnum errorEnum, HttpStatus httpStatus) {
        return new AuthenticateUserException(
                errorEnum.getMessage(),
                errorEnum.getDetailMessage(),
                errorEnum.getCode(),
                httpStatus
        );
    }
}
