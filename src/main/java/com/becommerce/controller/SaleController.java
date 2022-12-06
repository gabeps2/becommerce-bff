package com.becommerce.controller;

import com.becommerce.api.SaleApi;
import com.becommerce.model.RegisterSaleSchema;
import com.becommerce.model.SaleComponentSchema;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.SaleService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;
import java.util.List;

@Controller
public class SaleController implements SaleApi {

    @Inject
    private SaleService saleService;
    @Inject
    AuthenticateUserService authenticateUserService;

    @Override
    public HttpResponse<List<SaleComponentSchema>> getSales(String xApiToken) {
        authenticateUserService.validateToken(xApiToken);
        return HttpResponse.ok(saleService.getSales(xApiToken));
    }

    @Override
    public HttpResponse<Void> registerSale(String xApiToken, RegisterSaleSchema registerSaleSchema) {
        saleService.registerSale(xApiToken, registerSaleSchema);
        return HttpResponse.ok();
    }
}
