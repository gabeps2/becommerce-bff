package com.becommerce.controller;

import com.becommerce.api.RegisterApi;
import com.becommerce.model.RegisterCustomerRequest;
import com.becommerce.model.RegisterPartnerRequest;
import com.becommerce.service.RegisterService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;

@Controller
public class RegisterController implements RegisterApi {
    
    @Inject
    RegisterService registerService;
    
    @Override
    public HttpResponse<Void> registerPartner(RegisterPartnerRequest registerPartnerRequest) {
        return null;
    }

    @Override
    public HttpResponse<Void> registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        registerService.registerCustomer(registerCustomerRequest);
        return HttpResponse.ok();
    }
}
