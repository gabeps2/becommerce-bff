package com.becommerce.service.impl;

import com.becommerce.exception.ErrorEnum;
import com.becommerce.exception.RegisterUserException;
import com.becommerce.model.PartnerModel;
import com.becommerce.model.RegisterCustomerRequest;
import com.becommerce.service.PartnerService;
import com.becommerce.service.RegisterService;
import com.becommerce.utils.PasswordStorage;
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

    @Override
    public void registerCustomer(RegisterCustomerRequest request) {
        assert request.getPassword() != null;
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
            throw new RegisterUserException(e.getMessage(), e.getLocalizedMessage(), ErrorEnum.REGISTER_USER_ERROR.getCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
