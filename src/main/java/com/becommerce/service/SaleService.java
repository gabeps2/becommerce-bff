package com.becommerce.service;

import com.becommerce.model.RegisterSaleSchema;
import com.becommerce.model.SaleComponentSchema;
import com.becommerce.model.SaleSchema;

import java.util.List;

public interface SaleService {

    List<SaleComponentSchema> getSales(String token);

    SaleSchema findSale(String token, Integer number);

    void registerSale(String token, RegisterSaleSchema registerSaleSchema);

}
