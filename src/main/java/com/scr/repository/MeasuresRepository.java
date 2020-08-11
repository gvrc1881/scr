package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Measures;

@Repository

public interface MeasuresRepository extends JpaRepository<Measures, Long>{
	
	List<Measures> findAll();
	
	

}