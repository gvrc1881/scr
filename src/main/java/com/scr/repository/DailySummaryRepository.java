package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.scr.model.DailyProgressSummery;

public interface DailySummaryRepository extends JpaRepository<DailyProgressSummery,Long>{
	
    List<DailyProgressSummery> findAll();
  //Query Annotation for exist record
	@Query(value = "SELECT case when count(dps)> 0 then true else false  end  FROM DailyProgressSummery dps WHERE dps.facilityId = :facilityId and CAST(dps.createdDate AS date ) = :createdDate")
	Boolean existsByFacilityIdAndCreatedDate(@Param("facilityId") String facilityId,@Param("createdDate") Timestamp createdDate);

}
