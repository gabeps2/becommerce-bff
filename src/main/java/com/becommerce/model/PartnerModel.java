package com.becommerce.model;

import com.becommerce.model.enums.CustomerType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotNull
    @Column(name = "cnpj", unique = true)
    private String cnpj = "";
    @NotNull
    @Column(name = "description", unique = true)
    private String description = "";
    @NotNull
    @Column(name = "location", unique = true)
    private String location = "";
    @NotNull
    @Column(name = "avaliation", unique = true)
    private String avaliation = "";
    @NotNull
    @Column(name = "icon", unique = true)
    private String icon = "";
    @NotNull
    @Column(name = "created_at", nullable = false, unique = true)
    private Date createdAt;
    @NotNull
    @Column(name = "updated_at", nullable = false, unique = true)
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "partner")
    private Set<TokenModel> tokens = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "partner")
    private Set<ProductModel> products = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "partner")
    private Set<OrderModel> orders = new HashSet<>();
}
