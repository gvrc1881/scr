package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Compliance;

@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, Long>{

}
