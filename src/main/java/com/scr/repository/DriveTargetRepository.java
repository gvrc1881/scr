package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.DriveTarget;

public interface DriveTargetRepository extends JpaRepository<DriveTarget, Long>{

	Optional<DriveTarget> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveTarget> findByStatusId(Integer statusId);

}
