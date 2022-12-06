package com.becommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sale_product")
@AssociationOverrides({
        @AssociationOverride(name = "saleProductId.sale",
                joinColumns = @JoinColumn(name = "saleId")),
        @AssociationOverride(name = "saleProductId.product",
                joinColumns = @JoinColumn(name = "productId")) })
public class SaleProductModel {

    @EmbeddedId
    private SaleProductId saleProductId = new SaleProductId();
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
