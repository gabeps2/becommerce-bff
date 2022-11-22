package com.becommerce.controller;

import com.becommerce.api.PartnerApi;
import com.becommerce.model.Category;
import com.becommerce.model.Partner;
import com.becommerce.model.PartnerPage;
import com.becommerce.model.Product;
import com.becommerce.service.PartnerService;
import com.becommerce.service.ProductService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PartnerController implements PartnerApi {
    @Inject
    PartnerService partnerService;
    @Inject
    ProductService productService;

    @Override
    public HttpResponse<PartnerPage> getByPartner(Integer id) {
        Partner partner = partnerService.getPartnerDto(id,"");
        List<Product> products = productService.getByPartner(id);

        List<String> list = new ArrayList<>();
        list.add("Espetinhos");
        list.add("Salgadinhos");
        list.add("Bebidas");

        PartnerPage partnerPage = new PartnerPage();
        partnerPage.setPartner(partner);
        partnerPage.setProducts(products);
        partnerPage.setProductsLabel(list);

        return HttpResponse.ok(partnerPage);
    }
}
