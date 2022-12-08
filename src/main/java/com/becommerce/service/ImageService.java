package com.becommerce.service;

import com.becommerce.model.PreSignUrlSchema;

public interface ImageService {
    PreSignUrlSchema getPreSignUrl(String token);
    String generatePutPreSignUrl(String fileName);
}
