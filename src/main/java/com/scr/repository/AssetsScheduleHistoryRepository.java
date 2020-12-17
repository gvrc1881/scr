package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetsScheduleHistory;



@Repository
public interface AssetsScheduleHistoryRepository extends JpaRepository<AssetsScheduleHistory, Long>{
	
	//schedule code in asscending order
	List<AssetsScheduleHistory> findDistinctScheduleCodeByAssetTypeAndScheduleCodeOrderByScheduleCodeAsc(String assetType,String scheduleCode);

	Optional<AssetsScheduleHistory> findById(Long id);

	



	//<AssetsScheduleHistory> findByScheduleDate(String assetType, String assetId, String facilityId);

	Optional<AssetsScheduleHistory> findByAssetTypeAndAssetIdAndFacilityId(String assetType, String assetId,String facilityId);
			

}