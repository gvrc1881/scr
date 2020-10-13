package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.DriveDailyProgress;
import com.scr.model.DriveProgressId;

public interface DriveProgressIdRepository extends JpaRepository<DriveProgressId, Long>{

	List<DriveProgressId> findByDriveDailyProgressId(DriveDailyProgress driveDailyProgress);

}
