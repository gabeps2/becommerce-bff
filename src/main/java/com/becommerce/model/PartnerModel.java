package com.becommerce.model;

import com.becommerce.model.enums.CustomerType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_partner")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotNull
    @Column(name = "cnpj", unique = true)
    private String cnpj = "";
    @NotNull
    @Column(name = "description")
    private String description = "";
    @NotNull
    @Column(name = "location")
    private String location = "";
    @NotNull
    @Column(name = "avaliation")
    private Double avaliation;
    @NotNull
    @Column(name = "icon")
    private String icon = "";
    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "partner")
    private Set<TokenModel> tokens = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "partner")
    private Set<ProductModel> products = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "partner")
    private Set<OrderModel> orders = new HashSet<>();

    @OneToOne
    private AddressModel address;
}
