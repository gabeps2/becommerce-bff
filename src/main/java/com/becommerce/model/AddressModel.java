package com.becommerce.model;

import com.becommerce.model.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Column(name = "street", nullable = false, unique = true)
    private String street;
    @NotNull
    @Column(name = "street_type", nullable = false, unique = true)
    private String streetType;
    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Integer number;
    @NotNull
    @Column(name = "city", nullable = false, unique = true)
    private String city;
    @NotNull
    @Column(name = "state", nullable = false, unique = true)
    private String state;
    @NotNull
    @Column(name = "country", nullable = false, unique = true)
    private String country;
    @NotNull
    @Column(name = "zip_code", nullable = false, unique = true)
    private String zipCode;
    @NotNull
    @Column(name = "neighborhood", nullable = false, unique = true)
    private String neighborhood;
    @NotNull
    @Column(name = "created_at", nullable = false, unique = true)
    private Date createdAt;
    @NotNull
    @Column(name = "updated_at", nullable = false, unique = true)
    private Date updatedAt;
}
