package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.AssetMasterData;

public interface AssetMastersRepository extends JpaRepository<AssetMasterData, Long>{
	List<AssetMasterData> findAll();
	List<AssetMasterData> findByAssetIdAndFacilityId(String assetId,String facilityId);
	List<AssetMasterData> findByAssetTypeAndFacilityId(String assetType,String facilityId);
	List<AssetMasterData> findByFacilityId(String facilityId);
	//@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM EnergyMeter tet WHERE tet.feederId = :feederId and CAST(tet.startDate AS date ) = :startDate")
	
	@Query(value="select * from asset_master_data " +
			" where facility_id=:facilityId and asset_type=:assetType " +
			" and kilometer BETWEEN :fromKm and :toKm ",nativeQuery = true)
	
	List<AssetMasterData> findByAssetTypeAndFacilityIdAndKM(String assetType, String facilityId, Double fromKm, Double toKm);

	@Query(value = "select * from asset_master_data amd,product_category_member pcm where product_category_id ='CIRCUIT_BREAKER' and amd.asset_type=pcm.product_id",
            nativeQuery=true )
	List<AssetMasterData> findByAssetId(String productId);
	
	/*@Query(value = "select * from asset_master_data amd,product_category_member pcm where amd.facilityName=:substation and product_category_id ='CIRCUIT_BREAKER' and amd.asset_type=pcm.product_id",
            nativeQuery=true )
	List<AssetMasterData> findByAssetIdBasedOnFacilityName(@Param("substation")String substation);
	*/
	@Query(value="select * from asset_master_data " +
			" where facility_id=:facilityId " +
			" and kilometer BETWEEN :fromKm and :toKm ",nativeQuery = true)
	List<AssetMasterData> findAssetIdsByFacilityId(String facilityId, Double fromKm, Double toKm);
	
	@Query(value="select * from asset_master_data " +
			" where facility_id=:facilityId and asset_type=:assetType " +
			" and asset_id=:assetId ",nativeQuery = true)
	List<AssetMasterData> findMakeModel(String assetId, String assetType, String facilityId);
}

