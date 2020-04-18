package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.FailureAnalysis;

public interface FailureAnalysisRepository extends JpaRepository<FailureAnalysis, Long>{
List<FailureAnalysis> findAll();
}
