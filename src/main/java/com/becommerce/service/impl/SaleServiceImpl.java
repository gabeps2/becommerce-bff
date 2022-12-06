package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.exception.base.BaseException;
import com.becommerce.mapper.Mapper;
import com.becommerce.model.*;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.model.enums.SaleStatusEnum;
import com.becommerce.repository.*;
import com.becommerce.service.AuthenticateUserService;
import com.becommerce.service.SaleService;
import com.becommerce.utils.UUIDUtils;
import io.micronaut.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Named
public class SaleServiceImpl implements SaleService {

    @Inject
    private SaleRepository saleRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PartnerRepository partnerRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private SaleProductRepository saleProductRepository;

    @Inject
    private AuthenticateUserService authenticateUserService;

    @Inject
    private Mapper mapper;

    @Override
    public List<SaleComponentSchema> getSales(String token) {
        List<SaleModel> sales = saleRepository.findByUser(authenticateUserService.getSubject(token));
        return mapper.toSaleComponentSchemaList(sales);
    }

    @Override
    public void registerSale(String token, RegisterSaleSchema registerSaleSchema) {
        Optional<UserModel> user = userRepository.findById(authenticateUserService.getSubject(token));
        if (user.isEmpty()) throw throwsException(ErrorEnum.REGISTER_SALE_ERROR, HttpStatus.PRECONDITION_FAILED);

        Optional<PartnerModel> partner = partnerRepository.findById(UUIDUtils.getFromString(registerSaleSchema.getPartnerId()));
        if (partner.isEmpty()) throw throwsException(ErrorEnum.REGISTER_SALE_ERROR, HttpStatus.PRECONDITION_FAILED);

        List<UUID> productIdList = registerSaleSchema
                .getProducts()
                .stream()
                .map(p -> UUID.fromString(p.getProductId()))
                .collect(Collectors.toList());

        SaleModel sale = SaleModel.builder()
                .user(user.get())
                .partner(partner.get())
                .address(user.get().getAddress())
                .note(registerSaleSchema.getNote())
                .status(SaleStatusEnum.ENVIADO_AO_FORNECEDOR.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        saleRepository.save(sale);

        List<SaleProductModel> saleProductModels = registerSaleSchema.getProducts()
                .stream()
                .map(p -> SaleProductModel.builder()
                        .saleProductId(
                                SaleProductId.builder()
                                        .product(productRepository.findById(UUIDUtils.getFromString(p.getProductId())).get())
                                        .sale(sale)
                                        .build())
                        .quantity(p.getQuantity())
                        .build())
                .collect(Collectors.toList());

        saleProductModels.forEach(spm -> saleProductRepository.save(spm));
    }

    BaseException throwsException(ErrorEnum errorEnum, HttpStatus httpStatus) {
        return new AuthenticateUserException(
                errorEnum.getMessage(),
                errorEnum.getDetailMessage(),
                errorEnum.getCode(),
                httpStatus
        );
    }
}
