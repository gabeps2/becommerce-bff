package com.becommerce.service.impl;

import com.becommerce.exception.RegisterUserException;
import com.becommerce.model.*;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.service.PartnerService;
import com.becommerce.service.ProductService;
import com.becommerce.service.RegisterService;
import io.micronaut.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;

import static com.becommerce.utils.PasswordStorage.createHash;

@Singleton
@Named("RegisterService")
public class RegisterServiceImpl implements RegisterService {

    @Inject
    PartnerService partnerService;

    @Inject
    ProductService productService;

    @Override
    public void registerCustomer(RegisterCustomerRequest request) {
        try {
            String hashedPassword = createHash(request.getPassword());
            PartnerModel partnerModel = PartnerModel.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(hashedPassword)
                    .updatedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .build();
            partnerService.savePartner(partnerModel);
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
    public void registerProduct(RegisterProductRequest request, String token) {
    }
}
