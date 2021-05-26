package com.diploma.persistence.dao;

import com.diploma.persistence.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
public interface SupplierDao extends JpaRepository<SupplierEntity, Long> {

    @Transactional
    @Modifying
    @Query("update SupplierEntity t " +
            "set t.name = :name, " +
            "t.address = :address, " +
            "t.phone = :phone " +
            "where t.id = :id")
    void update(@Param("id") Long id,
                 @Param("name") String name,
                 @Param("address") String address,
                 @Param("phone") String phone);

    SupplierEntity findOneByName(String name);
}