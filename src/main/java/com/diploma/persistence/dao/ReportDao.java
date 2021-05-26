package com.diploma.persistence.dao;

import com.diploma.persistence.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface ReportDao extends JpaRepository<ReportEntity, Long> {
}
