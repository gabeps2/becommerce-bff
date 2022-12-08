package com.becommerce.controller;

import com.becommerce.api.ImageApi;
import com.becommerce.model.PreSignUrlSchema;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.ImageService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;

@Controller
public class ImageController implements ImageApi {

    @Inject
    private ImageService imageService;

    @Inject
    private AuthenticateUserService authenticateUserService;

    @Override
    public HttpResponse<PreSignUrlSchema> getPreSignUrl(String xApiToken) {
        authenticateUserService.validateToken(xApiToken);
        return HttpResponse.ok(imageService.getPreSignUrl(xApiToken));
    }
}
