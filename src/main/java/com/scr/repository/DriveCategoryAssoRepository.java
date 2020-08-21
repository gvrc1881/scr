package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveCategoryAsso;

@Repository
public interface DriveCategoryAssoRepository extends JpaRepository<DriveCategoryAsso, Long>{

	Optional<DriveCategoryAsso> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveCategoryAsso> findByStatusId(Integer statusId);
	
	Boolean existsByDriveIdAndDriveCategoryId(Integer driveId, Integer driveCategoryId);

}
