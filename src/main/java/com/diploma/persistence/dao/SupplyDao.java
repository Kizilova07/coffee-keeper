package com.diploma.persistence.dao;

import com.diploma.persistence.entity.SupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface SupplyDao extends JpaRepository<SupplyEntity, Long> {

    @Transactional
    @Query("select t from SupplyEntity t " +
            "where (t.date = :date and " +
            "t.description = :description and " +
            "t.cost = :cost and " +
            "t.retailProductEntity.name = :productName)")
    SupplyEntity findOne(@Param("date") String date,
                         @Param("description") String description,
                         @Param("cost") Double cost,
                         @Param("productName") String productName);

    @Transactional
    @Modifying
    @Query("update SupplyEntity t " +
            "set t.description = :description, " +
            "t.amount = :amount, " +
            "t.cost = :cost, " +
            "t.date = :date, " +
            "t.retailProductEntity.id = :product_id " +
            "where t.id = :id")
    void update(@Param("id") Long id,
                @Param("description") String description,
                @Param("amount") Double amount,
                @Param("cost") Double cost,
                @Param("date") String date,
                @Param("product_id") Long product_id);

    @Transactional
    @Query("select d from SupplyEntity d " +
            "where d.date between :startDate and :endDate ")
    List<SupplyEntity> findAllInPeriod(@Param("startDate") String startDate,
                                       @Param("endDate") String endDate);

}