package com.becommerce.repository.impl;

import com.becommerce.model.ImageModel;
import com.becommerce.repository.ImageRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    @TransactionalAdvice
    public Optional<ImageModel> findById(String id) {
        String qlString = "SELECT im FROM ImageModel as im WHERE id = :id";
        return entityManager
                .createQuery(qlString, ImageModel.class)
                .setParameter("id", UUID.fromString(id))
                .getResultList().stream().findFirst();
    }

    @Override
    @TransactionalAdvice
    public List<ImageModel> findByProduct(String productId) {
        String qlString = "SELECT im FROM ImageModel as im WHERE product_id = :productId";
        return new ArrayList<>(entityManager
                .createQuery(qlString, ImageModel.class)
                .setParameter("productId", UUID.fromString(productId))
                .getResultList());
    }

    @Override
    @TransactionalAdvice
    public ImageModel save(ImageModel imageModel) {
        imageModel.setCreatedAt(LocalDateTime.now());
        imageModel.setUpdatedAt(LocalDateTime.now());
        entityManager.persist(imageModel);
        return imageModel;
    }

    @Override
    @TransactionalAdvice
    public void saveMany(List<ImageModel> imageModelList) {
        imageModelList.forEach(im -> {
            im.setCreatedAt(LocalDateTime.now());
            im.setUpdatedAt(LocalDateTime.now());
            entityManager.persist(im);
        });
    }
}
