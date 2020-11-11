package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.WorkPhases;

public interface WorkPhaseRepository extends JpaRepository<WorkPhases, Integer> {

}
