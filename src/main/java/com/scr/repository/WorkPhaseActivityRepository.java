package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;

public interface WorkPhaseActivityRepository extends JpaRepository<WorkPhaseActivity, Integer>{

	List<WorkPhaseActivity> findByWorkPhaseId(WorkPhases workPhases);

}
