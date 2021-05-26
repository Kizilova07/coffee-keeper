package com.diploma.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "retail_products")
public class RetailProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "cost")
    private Double cost;
    @Column(name = "raw_cost")
    private Double rawCost;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private SupplierEntity retailSupplier;

    @OneToMany(mappedBy = "saleRetailProductEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SaleProductEntity> saleProductEntities = new ArrayList<>();

    @OneToMany(mappedBy = "retailProductEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CancellationEntity> cancellationEntities = new ArrayList<>();

    @OneToMany(mappedBy = "retailProductEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SupplyEntity> supplyEntities = new ArrayList<>();

    @OneToMany(mappedBy = "retailProductEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RemainsEntity> remainsEntities = new ArrayList<>();

}
