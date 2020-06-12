package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Make;

@Repository

public interface MakeRepository extends JpaRepository<Make, Long>{
	
	List<Make> findAll();
	
	//public boolean existMake(String makeCode);
	Boolean existsByMakeName(String makeName);
	Boolean existsByMakeCode(String makeCode);

}