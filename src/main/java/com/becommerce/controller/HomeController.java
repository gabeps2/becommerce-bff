package com.becommerce.controller;

import com.becommerce.api.HomeApi;
import com.becommerce.model.HomeScreenSchema;
import com.becommerce.service.ScreenService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;

@Controller
public class HomeController implements HomeApi {

    @Inject
    ScreenService screenService;

    @Override
    public HttpResponse<HomeScreenSchema> getData(String type, String xApiToken) {
        return HttpResponse.ok(screenService.getHomeData(xApiToken));
    }
}