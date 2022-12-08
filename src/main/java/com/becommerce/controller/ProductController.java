package com.becommerce.controller;

import com.becommerce.api.ProductApi;
import com.becommerce.model.ProductSchema;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.ProductService;
import com.becommerce.service.RegisterService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;
import java.util.List;

@Controller
public class ProductController implements ProductApi {
    @Inject
    ProductService productService;
    @Inject
    AuthenticateUserService authenticateUserService;

    @Override
    public HttpResponse<ProductSchema> findProduct(String xApiToken, String id) {
        authenticateUserService.validateToken(xApiToken);
        return HttpResponse.ok(productService.getById(id));
    }

    @Override
    public HttpResponse<List<ProductSchema>> findProducts(String xApiToken, String filter) {
        authenticateUserService.validateToken(xApiToken);
        return HttpResponse.ok(productService.findByFilter(xApiToken, filter));
    }

    @Override
    public HttpResponse<Void> registerProduct(String X_API_TOKEN, ProductSchema registerProductRequest) {
        productService.registerProduct(registerProductRequest, X_API_TOKEN);
        return HttpResponse.noContent();
    }


}
