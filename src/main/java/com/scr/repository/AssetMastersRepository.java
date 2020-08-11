package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scr.model.AssetMasterData;

public interface AssetMastersRepository extends JpaRepository<AssetMasterData, Long>{
	List<AssetMasterData> findAll();
	List<AssetMasterData> findByAssetIdAndFacilityId(String assetId,String facilityId);
	List<AssetMasterData> findByAssetTypeAndFacilityId(String assetType,String facilityId);
	List<AssetMasterData> findByFacilityId(String facilityId);


}

