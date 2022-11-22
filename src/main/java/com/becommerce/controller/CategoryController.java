package com.becommerce.controller;

import com.becommerce.api.CategoryApi;
import com.becommerce.model.Category;
import io.micronaut.http.HttpResponse;

import java.util.List;

public class CategoryController implements CategoryApi {
    @Override
    public HttpResponse<List<Category>> getByCategory(String type) {
        return null;
    }
}
