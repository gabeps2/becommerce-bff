package com.becommerce.repository;

import com.becommerce.model.CategoryModel;
import com.becommerce.model.ImageModel;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface ImageRepository {

    Optional<ImageModel> findById(String id);

    List<ImageModel> findByProduct(String productId);

    ImageModel save(@NotBlank ImageModel imageModel);

    void saveMany(List<ImageModel> imageModelList);
}
