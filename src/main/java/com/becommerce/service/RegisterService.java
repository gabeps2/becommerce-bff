package com.becommerce.service;

import com.becommerce.model.RegisterCustomerRequest;
import com.becommerce.utils.PasswordStorage;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public interface RegisterService {

    void registerCustomer(RegisterCustomerRequest request);

}
