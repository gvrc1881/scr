package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scr.model.AssetScheduleAssoc;


@Repository
public interface AssetSchAssoRepository extends JpaRepository<AssetScheduleAssoc, Long>{
	
	List<AssetScheduleAssoc> findByAssetType(String assetType);
	
	@Query(value = "SELECT case when count(asa)> 0 then true else false  end  FROM AssetScheduleAssoc asa WHERE asa.assetType = :assetType and asa.scheduleCode = :scheduleCode")
	Boolean existsByAssetTypeAndScheduleCode(@Param("assetType")String assetType, @Param("scheduleCode") String scheduleCode);
	

    public List<AssetScheduleAssoc> findAll();
    
    Optional<AssetScheduleAssoc>findByAssetTypeAndScheduleCode(String assetType,String scheduleCode);
    
    Optional<AssetScheduleAssoc> findByAsaSeqId(String asaSeqId);

	Optional<AssetScheduleAssoc> findByTargetPlanMonths(String seqId);
	
	List<AssetScheduleAssoc> findByIsDpr(String isDpr);
	
	@Query(value ="select * from asset_schedule_assoc asa,product_category_member pcm where pcm.product_category_id ='OHE_FIXED_ASSET' and asa.asset_type = pcm.product_id and asa.is_dpr='Y'",nativeQuery=true)
	List<AssetScheduleAssoc> getAssetTypesAndSchedules();




	
}
