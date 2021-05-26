package com.diploma.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    private UserRoleEntity userRoleEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CancellationEntity> cancellationEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SaleEntity> saleEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ReportEntity> reportEntities = new ArrayList<>();

}
