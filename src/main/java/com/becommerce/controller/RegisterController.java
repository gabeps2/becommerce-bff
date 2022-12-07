package com.becommerce.controller;

import com.becommerce.api.RegisterApi;
import com.becommerce.model.CategorySchema;
import com.becommerce.model.ProductSchema;
import com.becommerce.model.RegisterPartnerSchema;
import com.becommerce.model.RegisterUserSchema;
import com.becommerce.service.RegisterService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;

@Controller
public class RegisterController implements RegisterApi {

    @Inject
    RegisterService registerService;

    @Override
    public HttpResponse<Void> registerCategory(String xApiToken, CategorySchema categorySchema) {
        registerService.registerCategory(categorySchema, xApiToken);
        return HttpResponse.ok();
    }

    @Override
    public HttpResponse<Void> registerUser(RegisterUserSchema registerUserSchema) {
        registerService.registerUser(registerUserSchema);
        return HttpResponse.ok();
    }
}
