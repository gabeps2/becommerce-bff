package com.becommerce.service;

import com.becommerce.model.RegisterCustomerRequest;
import com.becommerce.model.RegisterProductRequest;

public interface RegisterService {
    void registerCustomer(RegisterCustomerRequest request);
    void registerProduct(RegisterProductRequest request, String token);

}
