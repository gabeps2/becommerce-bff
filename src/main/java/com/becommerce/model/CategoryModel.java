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
import java.util.Set;

@Entity
@Builder
@Table(name = "tb_category")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "created_at", nullable = false, unique = true)
    private Date createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false, unique = true)
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<ProductModel> products = new HashSet<>();
}
