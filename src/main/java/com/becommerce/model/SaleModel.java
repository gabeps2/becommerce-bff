package com.becommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@Table(name = "tb_sale")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleModel {
    @Id
    @Type(type = "pg-uuid")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Column(name = "number", columnDefinition = "serial", insertable = false, updatable = false)
    @Generated(value = GenerationTime.INSERT)
    private Integer number;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne
    private AddressModel address;

    @ManyToOne
    private UserModel user;

    @ManyToOne
    private PartnerModel partner;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "saleProductId.sale", cascade = CascadeType.ALL)
    private List<SaleProductModel> saleProductModelList = new ArrayList<>();


}
