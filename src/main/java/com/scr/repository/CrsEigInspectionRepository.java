package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.CrsEigInspections;

public interface CrsEigInspectionRepository extends JpaRepository<CrsEigInspections, Long>{
	List<CrsEigInspections> findAll();

}
