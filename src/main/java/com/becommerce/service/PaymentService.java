package com.becommerce.service;

import com.becommerce.model.ProductModel;
import com.becommerce.model.SaleProductModel;
import com.becommerce.model.UserModel;

import java.util.List;

public interface PaymentService {
    void registerProduct(ProductModel productModel);

    void sendInvoicing(UserModel userModel, List<SaleProductModel> saleProductModelList);

    String createCustomer(UserModel userModel);

}