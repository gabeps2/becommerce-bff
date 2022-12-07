package com.becommerce.controller;

import com.becommerce.api.PartnerApi;
import com.becommerce.model.PartnersListSchema;
import com.becommerce.model.ProductSchema;
import com.becommerce.model.RegisterPartnerSchema;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.PartnerService;
import com.becommerce.service.ProductService;
import com.becommerce.service.RegisterService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;
import java.util.List;

@Controller
public class PartnerController implements PartnerApi {
    @Inject
    PartnerService partnerService;
    @Inject
    ProductService productService;

    @Inject
    RegisterService registerService;

    @Inject
    AuthenticateUserService authenticateUserService;

    @Override
    public HttpResponse<List<ProductSchema>> getByPartner(String xApiToken, String id) {
        authenticateUserService.validateToken(xApiToken);
        return HttpResponse.ok(productService.getByPartner(id));
    }

    @Override
    public HttpResponse<PartnersListSchema> listPartners(String xApiToken) {
        authenticateUserService.validateToken(xApiToken);
        return HttpResponse.ok(partnerService.findAll());
    }

    @Override
    public HttpResponse<Void> registerPartner(String xApiToken, RegisterPartnerSchema registerPartnerSchema) {
        authenticateUserService.validateToken(xApiToken);
        registerService.registerPartner(registerPartnerSchema, xApiToken);
        return HttpResponse.ok();
    }

    @Override
    public HttpResponse<Void> removePartner(String xApiToken) {
        authenticateUserService.validateToken(xApiToken);
        partnerService.removePartner(authenticateUserService.getSubject(xApiToken));
        return HttpResponse.ok();
    }
}
