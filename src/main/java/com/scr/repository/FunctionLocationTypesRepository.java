package com.scr.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.FunctionalLocationTypes;

@Repository
public interface FunctionLocationTypesRepository extends JpaRepository<FunctionalLocationTypes, Long >{
	List<FunctionalLocationTypes> findAll();
	
}
