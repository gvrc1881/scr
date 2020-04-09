package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.scr.model.AssetsScheduleHistory;


public interface AssetsScheduleHistoryRepository extends JpaRepository<AssetsScheduleHistory, Long>{
	

		List<AssetsScheduleHistory > findByAssetTypeAndScheduleCode(String assetType,String scheduleCode);

}
