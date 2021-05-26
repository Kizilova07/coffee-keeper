package com.diploma.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "supplies")
public class SupplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "cost")
    private Double cost;
    @Column(name = "date")
    private String date;

    @ManyToOne
    @JoinColumn(name = "retail_product_id", referencedColumnName = "id")
    private RetailProductEntity retailProductEntity;
}
