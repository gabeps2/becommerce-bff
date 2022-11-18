package com.becommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Table(name = "tb_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "status", nullable = false, unique = true)
    private String status;

    @NotNull
    @Column(name = "note", unique = true)
    private String note;

    @NotNull
    @Column(name = "created_at", nullable = false, unique = true)
    private Date createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false, unique = true)
    private Date updatedAt;

    @ManyToOne
    private PartnerModel partner;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductModel> products = new HashSet<>();

}
