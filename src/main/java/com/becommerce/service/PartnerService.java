package com.becommerce.service;

import com.becommerce.model.PartnerModel;

public interface PartnerService {
    PartnerModel getPartner(int id);
    PartnerModel savePartner(PartnerModel partner);
}
