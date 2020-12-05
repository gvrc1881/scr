package com.scr.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;

public interface WorkPhaseActivityRepository extends JpaRepository<WorkPhaseActivity, Integer>{

	List<WorkPhaseActivity> findByWorkPhaseId(WorkPhases workPhases);

	Optional<WorkPhaseActivity> findByWorkPhaseIdAndName(WorkPhases workPhaseId, String name);


	
	@Query(value = "SELECT case when count(wp)> 0 then true else false  end  FROM WorkPhaseActivity wp WHERE wp.workPhaseId = :workPhase and wp.name = :name")
	Boolean existsByWorkPhaseIdAndName(@Param("workPhase") WorkPhases workPhase, @Param("name") String name);
	
	
	@Query(value = "SELECT case when count(wp)> 0 then true else false  end  FROM WorkPhaseActivity wp WHERE wp.workPhaseId = :workPhase and wp.sequence = :sequence")
	Boolean existsByWorkPhaseIdAndSequence(@Param("workPhase") WorkPhases workPhase, @Param("sequence") BigDecimal sequence);

	

	//@Query("FROM WorkPhaseActivity ORDER BY workPhaseId,sequence ASC")
	List<WorkPhaseActivity> findByWorkPhaseIdIn(List<WorkPhases> workPhaseList);
	
	
	


}
