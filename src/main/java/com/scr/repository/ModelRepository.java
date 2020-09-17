package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.scr.model.Model;

@Repository

public interface ModelRepository extends JpaRepository<Model, Long>{
	@Query("FROM Model ORDER BY modelCode ASC")
	List<Model> findAllOrderBymodelCodeAsc();
	

	Boolean existsByModelCode(String modelCode);
	
	Optional<Model> findByModelCode(String modelCode);
}