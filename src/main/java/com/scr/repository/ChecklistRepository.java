package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveCheckList;
import com.scr.model.Drives;
import com.scr.model.MeasureOrActivityList;

@Repository
public interface ChecklistRepository extends JpaRepository<DriveCheckList, Long> {

	List<DriveCheckList> findByStatusId(Integer statusId);

	Optional<DriveCheckList> findByIdAndStatusId(Long id, Integer statusId);
	
	@Query(value = "SELECT case when count(dcl)> 0 then true else false  end  FROM DriveCheckList dcl WHERE dcl.driveId = :driveId and dcl.activityId = :activityId")
	Boolean existsByDriveIdAndActivityId(@Param("driveId")Drives driveId, @Param("activityId") MeasureOrActivityList measureOrActivityList);
	
	
	
	@Query(value = "SELECT case when count(dcl)> 0 then true else false  end  FROM DriveCheckList dcl WHERE dcl.driveId = :driveId and dcl.activityPositionId = :activityPositionId")
	Boolean existsByDriveIdAndActivityPositionId(@Param("driveId")Drives driveId, @Param("activityPositionId") String activityPositionId );


}
