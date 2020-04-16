package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.MeasureOrActivityList;

public interface MeasureOrActivityListRepository extends JpaRepository<MeasureOrActivityList, Long>{

	Optional<MeasureOrActivityList> findByActivityId(String activityId);

}
