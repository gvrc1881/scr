package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Division;
import com.scr.model.SubDivision;

public interface SubDivisionRepository extends JpaRepository<SubDivision, Long>{
	List<SubDivision> findByDivisionId(Division divisionId);
	List<SubDivision> findByCode(String code);

}
