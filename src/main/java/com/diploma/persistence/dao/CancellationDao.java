package com.diploma.persistence.dao;

import com.diploma.persistence.entity.CancellationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface CancellationDao extends JpaRepository<CancellationEntity, Long> {

    @Transactional
    @Query("select d from CancellationEntity d " +
            "where d.date between :startDate and :endDate ")
    List<CancellationEntity> findAllInPeriod(@Param("startDate") String startDate,
                                             @Param("endDate") String endDate);

    @Transactional
    @Query("select t from CancellationEntity t " +
            "where (t.date = :date and " +
            "t.comment = :comment and " +
            "t.amount = :amount and " +
            "t.userEntity.name = :userName and " +
            "t.retailProductEntity.name = :productName)")
    CancellationEntity findOne(@Param("date") String date,
                               @Param("comment") String comment,
                               @Param("amount") Double amount,
                               @Param("userName") String userName,
                               @Param("productName") String productName);

    @Transactional
    @Modifying
    @Query("update CancellationEntity t " +
            "set t.date = :date, " +
            "t.comment = :comment, " +
            "t.amount = :amount, " +
            "t.userEntity.id = :user_id, " +
            "t.retailProductEntity.id = :product_id " +
            "where t.id = :id")
    void update(@Param("id") Long id,
                @Param("date") String date,
                @Param("comment") String comment,
                @Param("amount") Double amount,
                @Param("user_id") Long user_id,
                @Param("product_id") Long product_id);
}
