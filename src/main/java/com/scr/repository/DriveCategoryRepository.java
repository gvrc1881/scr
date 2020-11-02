package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveCategory;
import com.scr.model.Drives;

@Repository
public interface DriveCategoryRepository extends JpaRepository<DriveCategory, Long>{

	Optional<DriveCategory> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveCategory> findByStatusId(Integer statusId);

	Boolean existsByDriveCategoryNameAndStatusId(String name, Integer statusId);

	Boolean existsByDescriptionAndStatusId(String description, Integer statusId);
	
	Optional<DriveCategory> findById(Long driveCategoryId);	

	//List<DriveCategory> getByDriveIdAndStatusId(Drives drives, int activeStatusId);

}
