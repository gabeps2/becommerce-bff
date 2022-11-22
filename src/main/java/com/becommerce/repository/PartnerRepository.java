package com.becommerce.repository;

import com.becommerce.model.PartnerModel;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface PartnerRepository {

    Optional<PartnerModel> findById(int id);

    Optional<PartnerModel> findByName(String name);

    PartnerModel save(@NotBlank PartnerModel partner);

    PartnerModel saveWithException(@NotBlank String name);

    void deleteById(int id);

    List<PartnerModel> findAll();

    int update(long id, @NotBlank String name);
}
