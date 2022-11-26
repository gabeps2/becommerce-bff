package com.becommerce.repository;

import com.becommerce.model.CategoryModel;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<CategoryModel> findById(String id);

    Optional<CategoryModel> findByName(String name);

    CategoryModel save(@NotBlank CategoryModel category);

    CategoryModel saveWithException(@NotBlank String name);

    void deleteById(String id);

    List<CategoryModel> findAll();

    int update(long id, @NotBlank String name);
}
