package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.WorkPhases;
import com.scr.model.Works;

public interface WorkPhaseRepository extends JpaRepository<WorkPhases, Integer> {

	List<WorkPhases> findByWorkId(Works works);

	void save(List<WorkPhases> workPhases);
	

	Optional<WorkPhases> findByWorkIdAndPhaseName(Works workId, String phaseName);
	
	@Query(value = "SELECT case when count(wp)> 0 then true else false  end  FROM WorkPhases wp WHERE wp.workId = :work and wp.phaseName = :phaseName")
	Boolean existsByWorkIdAndPhaseName(@Param("work") Works work, @Param("phaseName") String phaseName);
	
	
	@Query(value = "SELECT case when count(wp)> 0 then true else false  end  FROM WorkPhases wp WHERE wp.workId = :work and wp.sequence = :sequence")
	Boolean existByWorkIdAndSequence(@Param("work") Works work, @Param("sequence") Integer sequence);

	List<WorkPhases> getByWorkId(Works works);

	



}
