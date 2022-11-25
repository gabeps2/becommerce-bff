package com.becommerce.service;

import com.becommerce.model.PartnerModel;
import com.becommerce.model.PartnerSchema;

import java.util.Optional;

public interface PartnerService {
    Optional<PartnerModel> getById(String id);
    Optional<PartnerModel> getByName(String name);
    PartnerModel savePartner(PartnerModel partner);
    PartnerSchema getPartnerDto(String id, String name);
}
