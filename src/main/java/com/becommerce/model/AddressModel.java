package com.becommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel implements Serializable {
    @Id
    @Type(type = "pg-uuid")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotNull
    @Column(name = "street", nullable = false)
    private String street;
    @NotNull
    @Column(name = "street_type", nullable = false)
    private String streetType;
    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;
    @NotNull
    @Column(name = "city", nullable = false)
    private String city;
    @NotNull
    @Column(name = "state", nullable = false)
    private String state;
    @NotNull
    @Column(name = "country", nullable = false)
    private String country;
    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;
    @NotNull
    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @OneToOne
    private PartnerModel partner;
}
