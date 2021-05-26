package com.diploma.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cancellations")
public class CancellationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "comment")
    private String comment;
    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "retail_product_id", referencedColumnName = "id")
    private RetailProductEntity retailProductEntity;

    @OneToMany(mappedBy = "cancellationEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ReportEntity> reportEntities = new ArrayList<>();

}
