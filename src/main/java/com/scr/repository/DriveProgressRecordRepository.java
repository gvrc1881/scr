package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveDailyProgress;

@Repository
public interface DriveProgressRecordRepository extends JpaRepository<DriveDailyProgress, Long>{

}
