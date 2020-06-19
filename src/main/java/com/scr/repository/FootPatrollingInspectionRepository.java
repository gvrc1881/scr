package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.FootPatrollingInspection;

@Repository
public interface FootPatrollingInspectionRepository extends JpaRepository<FootPatrollingInspection, Long>{
	//fetching from repository
	List<FootPatrollingInspection> findAll();

}
