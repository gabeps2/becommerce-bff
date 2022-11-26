package com.becommerce.repository.impl;

import com.becommerce.model.CategoryModel;
import com.becommerce.repository.CategoryRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public Optional<CategoryModel> findById(String id) {
        return Optional.empty();
    }

    @Override
    @TransactionalAdvice
    public Optional<CategoryModel> findByName(String name) {
        String qlString = "SELECT c FROM CategoryModel as c WHERE name = :name";
        return entityManager
                .createQuery(qlString, CategoryModel.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }

    @Override
    @TransactionalAdvice
    public CategoryModel save(CategoryModel category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        entityManager.persist(category);
        return category;
    }

    @Override
    public CategoryModel saveWithException(String name) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    @TransactionalAdvice
    public List<CategoryModel> findAll() {
        String qlString = "SELECT c FROM CategoryModel as c";
        return new ArrayList<>(entityManager
                .createQuery(qlString, CategoryModel.class)
                .getResultList());
    }

    @Override
    public int update(long id, String name) {
        return 0;
    }
}
