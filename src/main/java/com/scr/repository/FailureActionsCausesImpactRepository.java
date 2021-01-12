
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.FailureActionsCausesImpact;

public interface FailureActionsCausesImpactRepository extends JpaRepository<FailureActionsCausesImpact, Long>{

	List<FailureActionsCausesImpact> findAll();

	List<FailureActionsCausesImpact> findByFailureSeqId(Long failureSeqId);
}