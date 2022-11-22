package com.becommerce.service;

import com.becommerce.model.Partner;
import com.becommerce.model.PartnerModel;

import java.util.Optional;

public interface PartnerService {
    Optional<PartnerModel> getById(int id);
    Optional<PartnerModel> getByName(String name);
    PartnerModel savePartner(PartnerModel partner);
    Partner getPartnerDto(int id, String name);
}
