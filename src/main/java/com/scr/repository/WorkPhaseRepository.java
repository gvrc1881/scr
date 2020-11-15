package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.WorkPhases;
import com.scr.model.Works;

public interface WorkPhaseRepository extends JpaRepository<WorkPhases, Integer> {

	List<WorkPhases> findByWorkId(Works works);

}
