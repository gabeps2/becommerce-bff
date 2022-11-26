package com.becommerce.service;

import com.becommerce.model.CategorySchema;
import com.becommerce.model.RegisterUserSchema;
import com.becommerce.model.RegisterPartnerSchema;
import com.becommerce.model.ProductSchema;

public interface RegisterService {
    void registerUser(RegisterUserSchema userSchema);
    void registerProduct(ProductSchema request, String token);
    void registerPartner(RegisterPartnerSchema registerPartnerSchema, String token);

    void registerCategory(CategorySchema categorySchema, String token);
}
