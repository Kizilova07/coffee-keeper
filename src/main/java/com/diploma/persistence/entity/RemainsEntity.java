package com.diploma.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "remains")
public class RemainsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "retail_product_id", referencedColumnName = "id")
    private RetailProductEntity retailProductEntity;
}
