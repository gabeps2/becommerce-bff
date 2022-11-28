package com.becommerce.repository;

import com.becommerce.model.PartnerModel;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartnerRepository {

    Optional<PartnerModel> findById(UUID id);
    Optional<PartnerModel> findByUserId(UUID id);

    Optional<PartnerModel> findByName(String name);

    Optional<PartnerModel> findByEmail(String email);

    PartnerModel save(@NotBlank PartnerModel partner);

    PartnerModel saveWithException(@NotBlank String name);

    void deleteById(UUID id);

    List<PartnerModel> findAll();
    List<PartnerModel> findWithLimit(Integer limit);

    int update(long id, @NotBlank String name);
}
