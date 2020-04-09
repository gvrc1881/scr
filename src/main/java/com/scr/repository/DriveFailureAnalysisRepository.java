package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Failure;


public interface DriveFailureAnalysisRepository extends JpaRepository<Failure, Long>{

}
