package com.scr.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveDailyProgress;
import com.scr.model.Drives;

@Repository
public interface DriveProgressRecordRepository extends JpaRepository<DriveDailyProgress, Long>{

	Optional<DriveDailyProgress> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveDailyProgress> findByStatusId(Integer statusId);

	Optional<DriveDailyProgress> findByDriveId(Drives driveId);

	Optional<DriveDailyProgress> findByDriveIdAndPerformedDate(Drives drives, Date fromDate);

	List<DriveDailyProgress> findByDriveIdAndPerformedDateLessThanEqual(Drives drives, Date fromDate);

	List<DriveDailyProgress> getByDriveIdAndStatusId(Drives driveId, int activeStatusId);

}
