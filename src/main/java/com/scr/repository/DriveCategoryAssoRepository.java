package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveCategoryAsso;
import com.scr.model.Drives;
import com.scr.model.DriveCategory;

@Repository
public interface DriveCategoryAssoRepository extends JpaRepository<DriveCategoryAsso, Long>{

	Optional<DriveCategoryAsso> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveCategoryAsso> findByStatusId(Integer statusId);
	
	
	@Query(value = "SELECT case when count(dca)> 0 then true else false  end  FROM DriveCategoryAsso dca WHERE dca.driveId = :driveId and dca.driveCategoryId = :driveCategoryId")
	Boolean existsByDriveIdAndDriveCategoryId(@Param("driveId")Drives driveId, @Param("driveCategoryId") DriveCategory driveCategoryId);

}
