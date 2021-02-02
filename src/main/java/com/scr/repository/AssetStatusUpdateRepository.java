
package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetStatusUpdate;

@Repository
public interface AssetStatusUpdateRepository extends JpaRepository<AssetStatusUpdate,Long>{
	
	List<AssetStatusUpdate> findAll();
	
	
	/*Optional<AssetStatusUpdate> findByAssetTypeAndAssetIdAndFacilityId(String assetType, String assetId,
			String facilityId);*/



	Optional<AssetStatusUpdate> getByAssetId(String assetId);

	@Query(value = "SELECT max(dateOfStatus)  FROM AssetStatusUpdate asu where assetType =:assetType and assetId =:assetId and facilityId =:facilityId ")
	Timestamp findByAssetTypeAndAssetIdAndFacilityId(String assetType, String assetId, String facilityId);


	Optional<AssetStatusUpdate> findByAssetTypeAndAssetIdAndFacilityIdAndDateOfStatus(String assetType, String assetId,
			String facilityId, Timestamp astu);

	
	List<AssetStatusUpdate> findByAssetIdOrderByDateOfStatusDesc(String assetId);


	List<AssetStatusUpdate> findByFacilityIdIn(List<String> fac);

	
	


	

	
}
