package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.StandardPhaseActivity;
import com.scr.model.StandardPhases;


public interface StandardPhaseActivityRepository extends JpaRepository<StandardPhaseActivity, Integer> {

	List<StandardPhaseActivity> findByStandardPhaseIdIn(List<StandardPhases> standardPhases);
	

}
