package com.becommerce.repository;

import com.becommerce.model.AddressModel;
import com.becommerce.model.PartnerModel;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface AddressRepository {
    AddressModel save(@NotBlank AddressModel partner);
    Optional<AddressModel> findById(String id);
}
