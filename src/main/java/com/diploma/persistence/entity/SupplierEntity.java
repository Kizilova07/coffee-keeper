package com.diploma.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "suppliers")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "retailSupplier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RetailProductEntity> retailProductEntities = new ArrayList<>();


}
