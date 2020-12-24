
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
	
	
	Optional<AssetStatusUpdate> findByAssetTypeAndAssetIdAndFacilityId(String assetType, String assetId,
			String facilityId);
	
	@Query(value = "SELECT case when count(asu)> 0 then true else false  end  FROM AssetStatusUpdate asu WHERE  asu.assetId = :assetId ")

	Boolean existsByAssetId(@Param("assetId") String assetId);


	

	
}
