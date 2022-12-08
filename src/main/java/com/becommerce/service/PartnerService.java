package com.becommerce.service;

import com.becommerce.model.PartnerModel;
import com.becommerce.model.PartnerPageSchema;
import com.becommerce.model.PartnerSchema;
import com.becommerce.model.PartnersListSchema;

import java.util.Optional;
import java.util.UUID;

public interface PartnerService {
    Optional<PartnerModel> getById(UUID id);
    Optional<PartnerModel> getByUserId(UUID id);
    Optional<PartnerModel> getByName(String name);

    PartnerPageSchema getPartnerData(String id);

    PartnersListSchema findAll();
    PartnerModel savePartner(PartnerModel partner);
    PartnerSchema getPartnerDto(UUID id, String name);
    void removePartner(UUID id);
}
