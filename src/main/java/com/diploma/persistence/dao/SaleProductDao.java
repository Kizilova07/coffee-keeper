package com.diploma.persistence.dao;

import com.diploma.persistence.entity.SaleProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface SaleProductDao extends JpaRepository<SaleProductEntity, Long> {

    @Transactional
    @Query("select t from SaleProductEntity t " +
            "where t.saleEntity.id = :sale_id")
    List<SaleProductEntity> findAllBySaleId(@Param("sale_id") Long sale_id);

    @Modifying
    @Transactional
    @Query("delete from SaleProductEntity t " +
            "where (t.saleRetailProductEntity.id = :product_id and " +
            "t.saleEntity.id = :sale_id)")
    void deleteOneByProductId(@Param("product_id") Long product_id,
                              @Param("sale_id") Long sale_id);

    @Transactional
    @Query("select t from SaleProductEntity t " +
            "where (t.saleRetailProductEntity.name = :productName and " +
            "t.saleEntity.id = :sale_id)")
    SaleProductEntity findOneByProduct(@Param("productName") String productName,
                                       @Param("sale_id") Long sale_id);

    @Modifying
    @Transactional
    @Query("update SaleProductEntity t " +
            "set t.amount = :amount " +
            "where t.id = :id")
    void update(@Param("id") Long id,
                @Param("amount") Double amount);

    @Transactional
    @Query("select d from SaleProductEntity d " +
            "order by d.amount desc")
    List<SaleProductEntity> findAllSortedByAmount();
}
