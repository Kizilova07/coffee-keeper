package com.diploma.persistence.entity;

import com.diploma.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sales")
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "profit")
    private Double profit;
    @Column(name = "income")
    private Double income;
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "saleEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SaleProductEntity> saleProductEntities = new ArrayList<>();

    @OneToMany(mappedBy = "saleEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ReportEntity> reportEntities = new ArrayList<>();

}
