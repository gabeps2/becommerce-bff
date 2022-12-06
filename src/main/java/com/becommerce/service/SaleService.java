package com.becommerce.service;

import com.becommerce.model.RegisterSaleSchema;
import com.becommerce.model.SaleComponentSchema;

import java.util.List;

public interface SaleService {

    List<SaleComponentSchema> getSales(String token);

    void registerSale(String token, RegisterSaleSchema registerSaleSchema);

}
