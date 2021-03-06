package com.scr.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveCategoryAsso;
import com.scr.model.Drives;
import com.scr.model.Make;
import com.scr.model.DriveCategory;

@Repository
public interface DriveCategoryAssoRepository extends JpaRepository<DriveCategoryAsso, Long>{

	Optional<DriveCategoryAsso> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveCategoryAsso> findByStatusId(Integer statusId);
	
	
	@Query(value = "SELECT case when count(dca)> 0 then true else false  end  FROM DriveCategoryAsso dca WHERE dca.driveId = :driveId and dca.driveCategoryId = :driveCategoryId")
	Boolean existsByDriveIdAndDriveCategoryId(@Param("driveId")Drives driveId, @Param("driveCategoryId") DriveCategory driveCategoryId);
	
	Optional<DriveCategoryAsso> findByDriveIdAndDriveCategoryId(Drives driveId, DriveCategory driveCategoryId);

	

	List<DriveCategoryAsso> getByDriveIdAndStatusId(Drives drives, int activeStatusId);
	
	List<DriveCategoryAsso> findByDriveCategoryId(DriveCategory driveCategory);

	

	List<DriveCategoryAsso> findByDriveCategoryIdAndStatusId(DriveCategory driveCategory, int activeStatusId);

	Optional<DriveCategoryAsso> findByDriveCategoryId(Optional<DriveCategory> driveCategory);
	

	Optional<DriveCategoryAsso> findByDriveCategoryId(Long categoryId);



	List<DriveCategoryAsso> getByDriveCategoryIdAndStatusId(Optional<DriveCategory> driveCategory, int activeStatusId);

	Optional<DriveCategoryAsso> getByDriveCategoryIdAndDriveIdAndStatusId(DriveCategory driveCategoryId, Drives driveId,
			int activeStatusId);

	Optional<DriveCategoryAsso> getByDriveCategoryIdAndDriveIdAndStatusId(Optional<DriveCategory> driveCategory,
			Optional<Drives> drivId, int activeStatusId);

	

	

	Optional<DriveCategoryAsso> getByDriveIdAndDriveCategoryId(Drives drives, DriveCategory driveCategory);

	Optional<DriveCategoryAsso> getByDriveIdAndDriveCategoryIdAndStatusId(Drives drives, DriveCategory driveCategory,
			int activeStatusId);

	Optional<DriveCategoryAsso> findByDriveIdAndDriveCategoryIdAndStatusId(Drives drive, DriveCategory driveCategory,
			int activeStatusId);

	

	

	

	

	

	

	

	

	

	
}
