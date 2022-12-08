package com.becommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tb_partner")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerModel implements Serializable {
    @Id
    @Type(type = "pg-uuid")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name = "";

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

    @Column(name = "background_image")
    private String backgroundImage = "";

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne
    private UserModel user;

    @ManyToMany(mappedBy = "partners")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CategoryModel> categories = new ArrayList<>();

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private SaleModel sale;

    @JsonIgnore
    @OneToMany(mappedBy = "partner")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductModel> products = new ArrayList<>();
}
