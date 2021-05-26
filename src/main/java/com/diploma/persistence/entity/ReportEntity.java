package com.diploma.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    private SaleEntity saleEntity;
    @ManyToOne
    @JoinColumn(name = "cancellation_id", referencedColumnName = "id")
    private CancellationEntity cancellationEntity;
}
