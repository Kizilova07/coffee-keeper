package com.diploma.persistence.entity;

import com.diploma.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_roles")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @OneToMany(mappedBy = "userRoleEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserEntity> userEntities = new ArrayList<>();

}