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
@Table(name = "tb_token")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @NotNull
    @Column(name = "created_at", nullable = false, unique = true)
    private Date createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false, unique = true)
    private Date updatedAt;

    @ManyToOne
    private PartnerModel partner;
}
