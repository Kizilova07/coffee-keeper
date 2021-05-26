package com.diploma.persistence.dao;

import com.diploma.persistence.entity.RetailProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
public interface RetailProductDao extends JpaRepository<RetailProductEntity, Long> {

    @Transactional
    @Modifying
    @Query("update RetailProductEntity t " +
            "set t.name = :name, " +
            "t.description = :description, " +
            "t.cost = :cost, " +
            "t.rawCost = :rawCost, " +
            "t.retailSupplier.id = :supplier_id " +
            "where t.id = :id")
    void update(@Param("id") Long id,
                @Param("name") String name,
                @Param("description") String description,
                @Param("cost") Double cost,
                @Param("rawCost") Double rawCost,
                @Param("supplier_id") Long supplier_id);

    RetailProductEntity findOneByName(String name);
}
