package com.becommerce.controller;

import io.micronaut.http.annotation.*;

@Controller("/becommerceBff")
public class BecommerceBffController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}