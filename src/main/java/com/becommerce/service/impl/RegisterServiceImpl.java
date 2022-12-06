package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.exception.RegisterException;
import com.becommerce.exception.RegisterUserException;
import com.becommerce.mapper.Mapper;
import com.becommerce.model.*;
import com.becommerce.model.enums.CustomerType;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.repository.*;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.PartnerService;
import com.becommerce.service.RegisterService;
import com.becommerce.utils.UUIDUtils;
import io.micronaut.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.becommerce.model.enums.ErrorEnum.REGISTER_PARTNER_ERROR;
import static com.becommerce.utils.PasswordStorage.createHash;

@Singleton
@Named("RegisterService")
public class RegisterServiceImpl implements RegisterService {

    @Inject
    UserRepository userRepository;

    @Inject
    PartnerService partnerService;

    @Inject
    ProductRepository productRepository;

    @Inject
    ImageRepository imageRepository;

    @Inject
    AuthenticateUserService authenticateUserService;

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
    public void registerPartner(RegisterPartnerSchema request, String token) {
        UUID userId = UUIDUtils.getFromString(authenticateUserService.getSubject(token).toString());
        Optional<UserModel> response = userRepository.findById(userId);

        if (response.isEmpty()) throw throwsException(REGISTER_PARTNER_ERROR, HttpStatus.PRECONDITION_FAILED);
        UserModel user = response.get();
        try {
            PartnerModel partnerModel = PartnerModel.builder()
                    .user(user)
                    .name(request.getPartnerSchema().getName())
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

            userRepository.update(user);
            partnerService.savePartner(partnerModel);
        } catch (Exception e) {
            throw throwsException(REGISTER_PARTNER_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public void registerCategory(CategorySchema categorySchema, String token) {
        categoryRepository.save(mapper.toCategoryModel(categorySchema));
    }

    private RegisterException throwsException(ErrorEnum errorEnum, HttpStatus httpStatus) {
        return new RegisterException(
                errorEnum.getMessage(),
                errorEnum.getDetailMessage(),
                errorEnum.getCode(),
                httpStatus
        );
    }
}
