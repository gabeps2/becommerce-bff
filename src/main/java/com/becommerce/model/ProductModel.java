package com.becommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Table(name = "tb_product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "description", unique = true)
    private String description;

    @NotNull
    @Column(name = "price", nullable = false, unique = true)
    private String price;

    @NotNull
    @Column(name = "quantity", nullable = false, unique = true)
    private Integer quantity;

    @NotNull
    @Column(name = "icon", nullable = false, unique = true)
    private String icon;

    @NotNull
    @Column(name = "created_at", nullable = false, unique = true)
    private Date createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false, unique = true)
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<ImageModel> images = new HashSet<>();

    @ManyToOne
    private CategoryModel category;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private Set<OrderModel> orders = new HashSet<>();

    @ManyToOne
    private PartnerModel partner;
}
