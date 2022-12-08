package com.becommerce.service;

import com.becommerce.model.PreSignUrlSchema;
import com.becommerce.model.ProductModel;

public interface ImageService {
    PreSignUrlSchema getPreSignUrl(String token);
    String generatePutPreSignUrl(String fileName);
}
