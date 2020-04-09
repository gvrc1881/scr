package com.scr.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.scr.model.Failure;

@Repository
public interface FailuresRepository extends JpaRepository<Failure, Long> {

	List<Failure> findAll();
		
	}
	

