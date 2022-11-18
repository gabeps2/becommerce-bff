package com.becommerce.service.impl;

import com.becommerce.model.PartnerModel;
import com.becommerce.repository.PartnerRepository;
import com.becommerce.service.PartnerService;

import javax.inject.Inject;
import javax.inject.Named;

@Named("PartnerService")
public class PartnerServiceImpl implements PartnerService {

    @Inject
    private PartnerRepository partnerRepository;

    @Override
    public PartnerModel getPartner(int id) {
        return null;
    }

    @Override
    public PartnerModel savePartner(PartnerModel partner) {
        return null;
    }
}
