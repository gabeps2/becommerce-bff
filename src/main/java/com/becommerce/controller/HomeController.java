package com.becommerce.controller;

import com.becommerce.api.HomeApi;
import com.becommerce.model.Home;
import com.becommerce.service.HomeService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;

@Controller
public class HomeController implements HomeApi {

    @Inject
    private HomeService homeService;

    @Override
    public HttpResponse<Home> getHomepageData(String type) {
        return HttpResponse.ok(homeService.getHomepageData(type));
    }
}