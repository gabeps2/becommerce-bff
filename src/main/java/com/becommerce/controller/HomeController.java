package com.becommerce.controller;

import com.becommerce.api.HomeApi;
import com.becommerce.model.Home;
import com.becommerce.model.HomeCategories;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

@Controller
public class HomeController implements HomeApi {

    @Override
    public HttpResponse<Home> getCategories(String type) {
        System.out.println(type);
        Home home = new Home();
        HomeCategories homeCategories = new HomeCategories();
        homeCategories.setName("Alimentação");
        homeCategories.setIcon("icon.png");
        homeCategories.deeplink("https://www.google.com.br");
        home.setCategories(homeCategories);
        return HttpResponse.ok(home);
    }
}