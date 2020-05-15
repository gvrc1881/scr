package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetsScheduleHistory;



@Repository
public interface AssetsScheduleHistoryRepository extends JpaRepository<AssetsScheduleHistory, Long>{
	
	//schedule code in asscending order
	List<AssetsScheduleHistory> findByAssetTypeAndScheduleCodeOrderByScheduleCodeAsc(String assetType,String scheduleCode);

		

}
