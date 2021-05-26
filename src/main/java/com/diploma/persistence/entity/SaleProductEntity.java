package com.diploma.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sale_products")
public class SaleProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "retail_product_id", referencedColumnName = "id")
    private RetailProductEntity saleRetailProductEntity;

    @ManyToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    private SaleEntity saleEntity;



}
