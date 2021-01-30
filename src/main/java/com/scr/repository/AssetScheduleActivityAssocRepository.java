package com.scr.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetScheduleActivityAssoc;
import com.scr.model.Make;

@Repository

public interface AssetScheduleActivityAssocRepository extends JpaRepository<AssetScheduleActivityAssoc, Long>{
	
	@Query("FROM AssetScheduleActivityAssoc ORDER BY createdOn DESC")
	List<AssetScheduleActivityAssoc> findAllOrderByCreatedOnDesc();
	
	@Query(value = "SELECT case when count(asaa)> 0 then true else false  end  FROM AssetScheduleActivityAssoc asaa WHERE asaa.asaSeqId = :asaSeqId and asaa.activityPositionId = :activityPositionId and asaa.makeCode = :makeCode and asaa.modelCode = :modelCode")
	Boolean existsByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(@Param("asaSeqId")String asaSeqId, @Param("activityPositionId") String activityPositionId,
			@Param("makeCode") String makeCode,@Param("modelCode") String modelCode);
	
	@Query(value = "SELECT case when count(asaa)> 0 then true else false  end  FROM AssetScheduleActivityAssoc asaa WHERE asaa.asaSeqId = :asaSeqId and asaa.activityId = :activityId and asaa.makeCode = :makeCode and asaa.modelCode = :modelCode")
	Boolean existsByAsaSeqIdAndactivityIdAndMakeCodeAndModelCode(@Param("asaSeqId")String asaSeqId, @Param("activityId") String activityId,
			@Param("makeCode") String makeCode,@Param("modelCode") String modelCode);
	
	@Query(value = "SELECT case when count(asaa)> 0 then true else false  end  FROM AssetScheduleActivityAssoc asaa WHERE asaa.asaSeqId = :asaSeqId and asaa.activityId = :activityId and asaa.displayOrder = :displayOrder and asaa.makeCode = :makeCode and asaa.modelCode = :modelCode")
	Boolean existsByAsaSeqIdAndactivityIdAndDisplayOrderAndMakeCodeAndModelCode(@Param("asaSeqId")String asaSeqId, @Param("activityId") String activityId,@Param("displayOrder") BigDecimal displayOrder,
			@Param("makeCode") String makeCode,@Param("modelCode") String modelCode);
	
	Optional<AssetScheduleActivityAssoc>findByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(String asaSeqId,String activityPositionId,String makeCode,String modelCode);
	
	Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityIdAndMakeCodeAndModelCode(String asaSeqId,String activityId,String makeCode,String modelCode);
	

	Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityIdAndDisplayOrderAndMakeCodeAndModelCode(
			String asaSeqId, String activityId, BigDecimal displayOrder, String makeCode, String modelCode);
}