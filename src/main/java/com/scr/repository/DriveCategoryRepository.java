package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveCategory;

@Repository
public interface DriveCategoryRepository extends JpaRepository<DriveCategory, Long>{

	Optional<DriveCategory> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveCategory> findByStatusId(Integer statusId);

}
