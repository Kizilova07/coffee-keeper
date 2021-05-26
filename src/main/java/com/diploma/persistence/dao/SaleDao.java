package com.diploma.persistence.dao;

import com.diploma.persistence.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface SaleDao extends JpaRepository<SaleEntity, Long> {

    @Transactional
    @Query("SELECT MAX(t.id) FROM SaleEntity t")
    Long findLastId();

    SaleEntity findOneById(Long id);

    @Transactional
    @Query("select d from SaleEntity d " +
            "where d.date between :startDate and :endDate ")
    List<SaleEntity> findAllInPeriod(@Param("startDate") String startDate,
                                     @Param("endDate") String endDate);

    List<SaleEntity> findAllByDate(String date);


}
