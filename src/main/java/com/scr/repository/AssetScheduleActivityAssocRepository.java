package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetScheduleActivityAssoc;

@Repository

public interface AssetScheduleActivityAssocRepository extends JpaRepository<AssetScheduleActivityAssoc, Long>{
	
	List<AssetScheduleActivityAssoc> findAll();
	
	@Query(value = "SELECT case when count(asaa)> 0 then true else false  end  FROM AssetScheduleActivityAssoc asaa WHERE asaa.asaSeqId = :asaSeqId and asaa.activityPositionId = :activityPositionId and asaa.makeCode = :makeCode and asaa.modelCode = :modelCode")
	Boolean existsByAsaSeqIdAndActivityPositionId(@Param("asaSeqId")String asaSeqId, @Param("activityPositionId") String activityPositionId,
			@Param("makeCode") String makeCode,@Param("modelCode") String modelCode);
	
	@Query(value = "SELECT case when count(asaa)> 0 then true else false  end  FROM AssetScheduleActivityAssoc asaa WHERE asaa.asaSeqId = :asaSeqId and asaa.activityId = :activityId and asaa.makeCode = :makeCode and asaa.modelCode = :modelCode")
	Boolean existsByAsaSeqIdAndactivityId(@Param("asaSeqId")String asaSeqId, @Param("activityId") String activityId,
			@Param("makeCode") String makeCode,@Param("modelCode") String modelCode);
	
	@Query(value = "SELECT case when count(asaa)> 0 then true else false  end  FROM AssetScheduleActivityAssoc asaa WHERE asaa.asaSeqId = :asaSeqId and asaa.activityId = :activityId and asaa.displayOrder = :displayOrder and asaa.makeCode = :makeCode and asaa.modelCode = :modelCode")
	Boolean existsByAsaSeqIdAndactivityDisplayOrder(@Param("asaSeqId")String asaSeqId, @Param("activityId") String activityId,@Param("displayOrder") String displayOrder,
			@Param("makeCode") String makeCode,@Param("modelCode") String modelCode);
	
}