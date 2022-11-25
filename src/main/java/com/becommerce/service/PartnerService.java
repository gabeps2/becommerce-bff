package com.becommerce.service;

import com.becommerce.model.PartnerModel;
import com.becommerce.model.PartnerSchema;
import com.becommerce.model.PartnersListSchema;

import java.util.Optional;

public interface PartnerService {
    Optional<PartnerModel> getById(String id);
    Optional<PartnerModel> getByName(String name);
    PartnersListSchema findAll();
    PartnerModel savePartner(PartnerModel partner);
    PartnerSchema getPartnerDto(String id, String name);
}
