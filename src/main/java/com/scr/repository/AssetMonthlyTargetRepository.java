package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetMonthlyTarget;

@Repository
public interface AssetMonthlyTargetRepository extends JpaRepository<AssetMonthlyTarget, Long> {

	List<AssetMonthlyTarget> findAll();

	List<AssetMonthlyTarget> findByFacilityIdAndYear(String facilityId, String year);
	
	Optional<AssetMonthlyTarget> findByFacilityId(String facilityId);


	@Query(nativeQuery = true, value = "select total_target_year from v_asset_monthly_targets where asset_type = :assetType AND schedule_type = :schCode   AND year = :financialYear AND facility_id = :facilityId ")
	String cumTargetBasedOnAssetTypeScheduleCodeFacilityIdYear(@Param("assetType") String assetType,
			@Param("schCode") String schCode, @Param("facilityId") String facilityId,
			@Param("financialYear") String financialYear);

	@Query(nativeQuery = true, value = "select target_jan,target_feb,target_mar,target_apr,target_may,target_jun,target_jul,target_aug,target_sep,target_oct,target_nov,target_dec from v_asset_monthly_targets where asset_type = :assetType AND schedule_type = :schCode   AND year = :financialYear AND facility_id = :facilityId ")
	String cumMonthTargetBasedOnAssetTypeScheduleCodeFacilityIdYear(@Param("assetType") String assetType,
			@Param("schCode") String schCode, @Param("facilityId") String facilityId,
			@Param("financialYear") String financialYear);

	Optional<AssetMonthlyTarget> findByFacilityIdAndYearAndAssetTypeAndScheduleType(String facilityId, String year,
			String assetType, String scheduleCode);

	

}
