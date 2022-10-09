package com.becommerce.model;

import com.becommerce.model.enums.CustomerType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Table(name = "tb_user", schema = "becommerce")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "becommerce.tb_user_id_seq")
    @SequenceGenerator(name = "becommerce.tb_user_id_seq", sequenceName = "becommerce.tb_user_id_seq", schema = "becommerce", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String cnpj = "";

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    private AddressModel address;
    private Date createdAt;
    private Date updatedAt;
}
