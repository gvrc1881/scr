package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.MilestoneTargets;
import com.scr.model.WorkPhases;
import com.scr.model.Works;


public interface MilestoneTargetsRepository extends JpaRepository<MilestoneTargets, Integer> {

	List<MilestoneTargets> getByWorkId(MilestoneTargets milestoneTargets);

	List<MilestoneTargets> getByWorkId(Works works);

	

}
