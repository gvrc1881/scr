package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetScheduleActivityRecord;

@Repository

public interface AssetScheduleActivityRecordsRepository extends JpaRepository<AssetScheduleActivityRecord, Long>{
	
	List<AssetScheduleActivityRecord> findAll();
	
}