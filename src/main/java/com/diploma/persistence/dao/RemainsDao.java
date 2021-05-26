package com.diploma.persistence.dao;

import com.diploma.persistence.entity.RemainsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface RemainsDao extends JpaRepository<RemainsEntity, Long> {

    @Transactional
    @Query("select t from RemainsEntity t " +
            "where t.retailProductEntity.name = :productName")
    RemainsEntity findOneByProduct(@Param("productName") String productName);

    @Transactional
    @Modifying
    @Query("update RemainsEntity t " +
            "set t.amount = :amount, " +
            "t.retailProductEntity.id = :product_id " +
            "where t.id = :id")
    void update(@Param("id") Long id,
                @Param("amount") Double amount,
                @Param("product_id") Long product_id);

}