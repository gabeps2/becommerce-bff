package com.becommerce.repository.impl;

import com.becommerce.model.AddressModel;
import com.becommerce.model.PartnerModel;
import com.becommerce.repository.AddressRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import java.util.Optional;

@Singleton
@Repository
public class AddressRepositoryImpl implements AddressRepository {
    @Inject
    private EntityManager entityManager;


    @Override
    @TransactionalAdvice
    public AddressModel save(AddressModel address) {
        entityManager.persist(address);
        return address;
    }

    @Override
    public Optional<AddressModel> findById(String id) {
        return Optional.ofNullable(entityManager.find(AddressModel.class, id));
    }
}
