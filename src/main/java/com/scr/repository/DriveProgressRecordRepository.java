package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveDailyProgress;

@Repository
public interface DriveProgressRecordRepository extends JpaRepository<DriveDailyProgress, Long>{

	Optional<DriveDailyProgress> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveDailyProgress> findByStatusId(Integer statusId);

}
