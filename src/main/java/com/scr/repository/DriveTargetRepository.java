package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.DriveTarget;
import com.scr.model.Drives;
import com.scr.model.Facility;
import com.scr.model.Make;

public interface DriveTargetRepository extends JpaRepository<DriveTarget, Long>{

	Optional<DriveTarget> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveTarget> findByStatusId(Integer statusId);
	
	
	/*@Query(value = "SELECT case when count(dt)> 0 then true else false  end  FROM DriveTarget dt WHERE dt.unitType = :unitType and dt.unitName = :unitName")
	Boolean findByUnitNameAndUnitType(@Param("unitType")String unitType, @Param("unitName") String unitName);
	*/
	Optional<DriveTarget> findByUnitTypeAndUnitName(String unitType,String unitName);

	List<DriveTarget> getByDriveIdAndStatusId(Drives driveId, int activeStatusId);

	@Query(value = "select distinct driveId from DriveTarget") 
	List<Drives> findDistinctByDriveId();

	List<DriveTarget> findByDriveId(Drives drives);	
	
/*	@Query(value = "select Division,subDivision from Facility") 
	List<Facility> findByUnitName();*/

	@Query(value = "select distinct unitName from DriveTarget")
	List<Facility> findDistinctByUnitName();	

	Optional<DriveTarget> findByDriveIdAndUnitName(Drives driveId, String unitName);

	

	DriveTarget getByDriveIdAndUnitName(Drives drives2, String zone);

	Optional<DriveTarget> findByDriveIdAndUnitName(Optional<Drives> drive, String zone);

	

	

	

	

	

	

	
	

	
}
