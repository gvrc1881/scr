package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Model;

@Repository

public interface ModelRepository extends JpaRepository<Model, Long>{
	
	List<Model> findAll();
	

	Boolean existsByModelCode(String modelCode);

}