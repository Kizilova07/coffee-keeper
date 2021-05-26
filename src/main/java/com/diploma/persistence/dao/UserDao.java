package com.diploma.persistence.dao;

import com.diploma.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findOneById(Long id);

    UserEntity findOneByPassword(String password);

}
