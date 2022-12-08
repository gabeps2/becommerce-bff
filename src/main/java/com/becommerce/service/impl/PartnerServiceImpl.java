package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.model.*;
import com.becommerce.mapper.Mapper;
import com.becommerce.model.enums.CustomerType;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.repository.PartnerRepository;
import com.becommerce.repository.ProductRepository;
import com.becommerce.repository.UserRepository;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.PartnerService;
import com.becommerce.utils.UUIDUtils;
import io.micronaut.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.becommerce.model.enums.ErrorEnum.FIND_PARTNER_ERROR;
import static com.becommerce.model.enums.ErrorEnum.OPERATION_ERROR;

@Named("PartnerService")
@Singleton
public class PartnerServiceImpl implements PartnerService {
    @Inject
    private PartnerRepository partnerRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private UserRepository userRepository;
    @Inject
    private Mapper mapper;

    @Override
    public Optional<PartnerModel> getById(UUID id) {
        return partnerRepository.findById(id);
    }

    @Override
    public Optional<PartnerModel> getByUserId(UUID id) {
        return partnerRepository.findByUserId(id);
    }

    @Override
    public Optional<PartnerModel> getByName(String name) {
        return partnerRepository.findByName(name);
    }

    @Override
    public PartnerPageSchema getPartnerData(String id) {
        UUID partnerId = UUIDUtils.getFromString(id);

        Optional<PartnerModel> partnerModel = partnerRepository.findById(partnerId);
        if (partnerModel.isEmpty()) throw throwsException(FIND_PARTNER_ERROR, HttpStatus.PRECONDITION_FAILED);

        UserModel userModel = partnerModel.get().getUser();
        AddressModel addressModel = userModel.getAddress();
        List<ProductModel> productModelList = productRepository.findByPartner(partnerId);

        return PartnerPageSchema.builder()
                .partnerSchema(mapper.toPartnerSchema(partnerModel.get()))
                .userSchema(mapper.toUserSchema(userModel))
                .addressSchema(mapper.toAddressSchema(addressModel))
                .products(mapper.toProductsSchema(productModelList))
                .build();
    }

    @Override
    public PartnersListSchema findAll() {
        List<PartnerModel> partnerModelList = partnerRepository.findAll();
        return PartnersListSchema.builder()
                .partners(mapper.toPartnersSchema(partnerModelList))
                .build();
    }

    @Override
    public PartnerModel savePartner(PartnerModel partner) {
        partner.setUpdatedAt(LocalDateTime.now());
        partner.setCreatedAt(LocalDateTime.now());
        return partnerRepository.save(partner);
    }

    @Override
    public PartnerSchema getPartnerDto(UUID id, String name) {
        Optional<PartnerModel> partnerModel;
        if (!name.isEmpty()) {
            partnerModel = getByName(name);
        } else {
            partnerModel = getById(id);
        }
        return partnerModel.map(model -> mapper.toPartner(model)).orElse(null);
    }

    @Override
    public void removePartner(UUID id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isEmpty() || userModel.get().getPartner() == null)
            throw throwsException(OPERATION_ERROR, HttpStatus.PRECONDITION_FAILED);
        UserModel user = userModel.get();
        partnerRepository.deleteById(user.getPartner().getId());
        user.setType(CustomerType.CUSTOMER.getName());
        userRepository.update(user);
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
