package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.WPASectionTargets;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhaseActivity;

public interface WPASectionTargetsRepository extends JpaRepository<WPASectionTargets, Integer>{

	Optional<WPASectionTargets> findByYearAndWorkGroupIdAndWorkPhaseActivityId(int nextYear, WorkGroup workGroup,
			WorkPhaseActivity wPA);

	Optional<WPASectionTargets> findByWorkGroupIdAndWorkPhaseActivityIdAndYear(WorkGroup workGroup,
			WorkPhaseActivity workPhaseActivity, Integer year);

}
