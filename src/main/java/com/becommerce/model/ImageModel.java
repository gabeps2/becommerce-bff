package com.becommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Builder
@Table(name = "tb_image")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "url", nullable = false, unique = true)
    private Integer url;

    @NotNull
    @Column(name = "created_at", nullable = false, unique = true)
    private Date createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false, unique = true)
    private Date updatedAt;

    @ManyToOne
    private ProductModel product;
}
