package com.becommerce.service.impl;

import com.becommerce.model.ProductModel;
import com.becommerce.model.Service;
import com.becommerce.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpStatus;

import javax.inject.Named;
import javax.inject.Singleton;

import java.math.BigDecimal;

import static com.becommerce.model.enums.ErrorEnum.EXTERNAL_ERROR;

@Singleton
@Named
public class PaymentServiceImpl extends Service implements PaymentService {
    @Value("${STRIPE_API_KEY}")
    private String STRIPE_API_KEY;

    @Override
    public void registerProduct(ProductModel productModel) {
        Stripe.apiKey = STRIPE_API_KEY;
        ProductCreateParams productCreateParams = ProductCreateParams.builder()
                .setId(productModel.getId().toString())
                .setName(productModel.getName())
                .setDescription(productModel.getDescription())
                .addImage(productModel.getIcon())
                .setDefaultPriceData(
                        ProductCreateParams.DefaultPriceData.builder()
                                .setUnitAmount(productModel.getPrice())
                                .setCurrency("brl")
                                .build()
                )
                .addExpand("default_price")
                .build();


        try {
            Product product = Product.create(productCreateParams);
        } catch (Exception e) {
            throw throwsException(EXTERNAL_ERROR, HttpStatus.FAILED_DEPENDENCY);
        }
    }
}