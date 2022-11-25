package com.becommerce.controller;

import com.becommerce.api.HomeApi;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

@Controller
public class HomeController implements HomeApi {

    @Override
    public HttpResponse getHomepageData(String type) {
        return HttpResponse.ok();
    }
}