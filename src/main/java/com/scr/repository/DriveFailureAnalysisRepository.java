package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.FailureAnalysis;


public interface DriveFailureAnalysisRepository extends JpaRepository<FailureAnalysis, Long>{

	List<FailureAnalysis> findByStatusId(Integer statusId);

	Optional<FailureAnalysis> findByIdAndStatusId(Long id, Integer statusId);

	List<FailureAnalysis> findByStatusIdAndDiv(int activeStatusId, String division);

}
