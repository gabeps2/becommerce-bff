package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.exception.RegisterUserException;
import com.becommerce.mapper.Mapper;
import com.becommerce.model.*;
import com.becommerce.model.enums.CustomerType;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.repository.AddressRepository;
import com.becommerce.repository.CategoryRepository;
import com.becommerce.repository.UserRepository;
import com.becommerce.service.PartnerService;
import com.becommerce.service.ProductService;
import com.becommerce.service.RegisterService;
import com.becommerce.utils.TokenUtils;
import io.micronaut.http.HttpStatus;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.becommerce.utils.PasswordStorage.createHash;

@Singleton
@Named("RegisterService")
public class RegisterServiceImpl implements RegisterService {

    @Inject
    UserRepository userRepository;

    @Inject
    ProductService productService;

    @Inject
    PartnerService partnerService;

    @Inject
    AddressRepository addressRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    Mapper mapper;

    @Override
    public void registerUser(RegisterUserSchema userSchema) {
        try {
            String hashedPassword = createHash(userSchema.getPassword());
            UserModel userModel = UserModel.builder()
                    .name(userSchema.getName())
                    .email(userSchema.getEmail())
                    .password(hashedPassword)
                    .type(userSchema.getUserType())
                    .updatedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(userModel);
        } catch (Exception e) {
            throw new RegisterUserException(
                    ErrorEnum.REGISTER_CUSTOMER_ERROR.getMessage(),
                    ErrorEnum.REGISTER_CUSTOMER_ERROR.getDetailMessage(),
                    ErrorEnum.REGISTER_CUSTOMER_ERROR.getCode(),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }

    @Override
    public void registerProduct(ProductSchema productSchema, String token) {
        Optional<PartnerModel> partner = partnerService.getById(TokenUtils.getUserId(token));
        if (!partner.isPresent()) throwsException(HttpStatus.PRECONDITION_FAILED);

        try {
            ProductModel productModel = ProductModel.builder()
                    .name(productSchema.getName())
                    .price(productSchema.getPrice())
                    .category(CategoryModel.builder().build())
                    .icon(productSchema.getIcon())
                    .partner(partner.get())
                    .quantity(productSchema.getQuantity())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    @Override
    @TransactionalAdvice
    public void registerPartner(RegisterPartnerSchema request, String token) {
        String userId = (request.getUserId().isEmpty()) ? TokenUtils.getUserId(token) :
                request.getUserId();
        Optional<UserModel> response = userRepository.findById(userId);

        if (!response.isPresent()) {
            throwsException(HttpStatus.PRECONDITION_FAILED);
        }

        UserModel user = response.get();

        PartnerModel partnerModel = PartnerModel.builder()
                .name(request.getPartnerSchema().getName())
                .user(user)
                .cnpj(request.getPartnerSchema().getCnpj())
                .description(request.getPartnerSchema().getDescription())
                .location(request.getPartnerSchema().getLocation())
                .avaliation(5.0)
                .icon(request.getPartnerSchema().getIcon())
                .backgroundImage(request.getPartnerSchema().getBackgroundImage())
                .categories(mapper.toCategoryModel(request.getPartnerSchema().getCategories()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        AddressModel addressModel = mapper.toAddressModel(request.getAddressSchema());

        user.setAddress(addressModel);
        user.setType(CustomerType.PARTNER.getName());
        user.setUpdatedAt(LocalDateTime.now());


        userRepository.save(user);
        partnerService.savePartner(partnerModel);
    }

    @Override
    public void registerCategory(CategorySchema categorySchema, String token) {
        categoryRepository.save(mapper.toCategoryModel(categorySchema));
    }

    void throwsException(HttpStatus httpStatus) {
        throw new AuthenticateUserException(
                ErrorEnum.AUTHENTICATE_USER_ERROR.getMessage(),
                ErrorEnum.AUTHENTICATE_USER_ERROR.getDetailMessage(),
                ErrorEnum.AUTHENTICATE_USER_ERROR.getCode(),
                httpStatus
        );
    }
}
