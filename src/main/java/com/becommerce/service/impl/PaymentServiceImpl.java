package com.becommerce.service.impl;

import com.becommerce.model.*;
import com.becommerce.service.PaymentService;
import com.becommerce.service.UserService;
import com.becommerce.utils.LoggerUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.Product;
import com.stripe.param.*;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.becommerce.model.enums.ErrorEnum.EXTERNAL_ERROR;

@Singleton
@Named
public class PaymentServiceImpl extends Service implements PaymentService {
    @Value("${STRIPE_API_KEY}")
    private String STRIPE_API_KEY;

    @Inject
    private UserService userService;

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
            Product.create(productCreateParams);
        } catch (Exception e) {
            throw throwsException(EXTERNAL_ERROR, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @Override
    public String createCustomer(UserModel userModel) {
        Stripe.apiKey = STRIPE_API_KEY;
        if (userModel.getPaymentApiId() != null) return userModel.getPaymentApiId();
        try {
            Customer stripeCustomer = Customer.create(
                    CustomerCreateParams
                            .builder()
                            .setEmail(userModel.getEmail())
                            .setName(userModel.getName())
                            .build()
            );
            userModel.setPaymentApiId(stripeCustomer.getId());
            userService.updateUser(userModel);
            return stripeCustomer.getId();
        } catch (Exception e) {
            throw throwsException(EXTERNAL_ERROR, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @Override
    public void sendInvoicing(UserModel userModel, List<SaleProductModel> saleProductModelList) {
        Stripe.apiKey = STRIPE_API_KEY;
        String userPaymentID = createCustomer(userModel);
        try {
            InvoiceCreateParams invoiceParams =
                    InvoiceCreateParams
                            .builder()
                            .setCustomer(userPaymentID)
                            .setCollectionMethod(InvoiceCreateParams.CollectionMethod.SEND_INVOICE)
                            .setDaysUntilDue(15L)
                            .build();

            Invoice invoice = Invoice.create(invoiceParams);

            InvoiceItemCreateParams invoiceItemParams =
                    InvoiceItemCreateParams.builder()
                            .setCustomer(userPaymentID)
                            .setInvoice(invoice.getId())
                            .setAmount(calcPrice(saleProductModelList))
                            .build();

            InvoiceItem.create(invoiceItemParams);

            Invoice resource = Invoice.retrieve(invoice.getId());

            InvoiceFinalizeInvoiceParams finalizeInvoiceParams = InvoiceFinalizeInvoiceParams.builder().build();
            resource.finalizeInvoice(finalizeInvoiceParams);

            InvoiceSendInvoiceParams sendInvoiceParams = InvoiceSendInvoiceParams.builder().build();
            resource.sendInvoice(sendInvoiceParams);
        } catch (StripeException e) {
            LoggerUtils.log().error(e.getMessage(), e);
            throw throwsException(EXTERNAL_ERROR, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    private String createPrice(Long price) {
        Stripe.apiKey = STRIPE_API_KEY;
        return "";
    }

    private Long calcPrice(List<SaleProductModel> saleProductModelList) {
        AtomicLong price = new AtomicLong();
        saleProductModelList.forEach(slm ->
                price.addAndGet(slm.getSaleProductId().getProduct().getPrice() * slm.getQuantity() * 100)
        );
        return price.longValue();
    }
}