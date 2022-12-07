package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.model.PartnerModel;
import com.becommerce.mapper.Mapper;
import com.becommerce.model.PartnerSchema;
import com.becommerce.model.PartnersListSchema;
import com.becommerce.model.UserModel;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.repository.PartnerRepository;
import com.becommerce.repository.UserRepository;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.PartnerService;
import io.micronaut.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.becommerce.model.enums.ErrorEnum.OPERATION_ERROR;

@Named("PartnerService")
@Singleton
public class PartnerServiceImpl implements PartnerService {
    @Inject
    private PartnerRepository partnerRepository;

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
        if (userModel.isEmpty() || userModel.get().getPartner() == null) throw throwsException(OPERATION_ERROR, HttpStatus.PRECONDITION_FAILED);
        partnerRepository.deleteById(userModel.get().getPartner().getId());
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
