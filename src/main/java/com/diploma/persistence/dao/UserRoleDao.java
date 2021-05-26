package com.diploma.persistence.dao;

import com.diploma.persistence.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface UserRoleDao extends JpaRepository<UserRoleEntity, Long> {

}
