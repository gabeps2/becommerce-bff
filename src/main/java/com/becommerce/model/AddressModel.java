package com.becommerce.model;

import com.becommerce.model.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Table(name = "tb_address", schema = "becommerce")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "becommerce.tb_address_id_seq")
    @SequenceGenerator(name = "becommerce.tb_address_id_seq", sequenceName = "becommerce.tb_address_id_seq", schema = "becommerce", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    private String street;
    private String streetType;
    private Integer number;
    private String city;
    private String country;
    private String zipCode;
    private String neighborhood;
    private Date createdAt;
    private Date updatedAt;
}
