package com.becommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class SaleProductId implements Serializable {

    @ManyToOne
    private SaleModel sale;
    @ManyToOne
    private ProductModel product;
}
