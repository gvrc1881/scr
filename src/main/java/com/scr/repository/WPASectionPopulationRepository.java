package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.WPASectionPopulation;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhaseActivity;

public interface WPASectionPopulationRepository extends JpaRepository<WPASectionPopulation, Integer>{

	Optional<WPASectionPopulation> findByWorkGroupIdAndWorkPhaseActivityId(WorkGroup workGroup,
			WorkPhaseActivity workPhaseActivity);

	List<WPASectionPopulation> getByWorkPhaseActivityId(WorkPhaseActivity workPhaseActivity);

}
