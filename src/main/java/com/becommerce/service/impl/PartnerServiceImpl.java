package com.becommerce.service.impl;

import com.becommerce.model.PartnerModel;
import com.becommerce.mapper.Mapper;
import com.becommerce.model.PartnerSchema;
import com.becommerce.model.PartnersListSchema;
import com.becommerce.repository.PartnerRepository;
import com.becommerce.service.PartnerService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Named("PartnerService")
@Singleton
public class PartnerServiceImpl implements PartnerService {
    @Inject
    private PartnerRepository partnerRepository;
    @Inject
    private Mapper mapper;

    @Override
    public Optional<PartnerModel> getById(String id) {
        return partnerRepository.findById(id);
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
    public PartnerSchema getPartnerDto(String id, String name) {
        Optional<PartnerModel> partnerModel;
        if (!name.isEmpty()) {
            partnerModel = getByName(name);
        } else {
            partnerModel = getById(id);
        }
        return partnerModel.map(model -> mapper.toPartner(model)).orElse(null);
    }
}
