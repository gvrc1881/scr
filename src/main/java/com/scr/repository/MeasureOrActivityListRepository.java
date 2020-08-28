package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.MeasureOrActivityList;

public interface MeasureOrActivityListRepository extends JpaRepository<MeasureOrActivityList, Long>{

	Optional<MeasureOrActivityList> findByActivityId(String activityId);
	List<MeasureOrActivityList> findByActivityType(String activityType);

	Boolean existsByActivityId(String activityId);
	
	Boolean existsByActivityName(String activityName);
	
	
	@Query(value = "SELECT case when count(mral)> 0 then true else false  end  FROM MeasureOrActivityList mral WHERE mral.activityName = :activityName and mral.unitOfMeasure = :unitOfMeasure")
	Boolean existsByActivityNameAndUnitOfMeasure(@Param("activityName")String activityName, @Param("unitOfMeasure") String unitOfMeasure);
	
	

}
