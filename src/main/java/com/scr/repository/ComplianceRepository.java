package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Compliance;

@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, Long>{
	List<Compliance> findAll();

}
