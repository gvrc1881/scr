package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.EnergyMeter;

@Repository
public interface EnergyMeterRepository extends JpaRepository<EnergyMeter, Long>{
	
	@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM EnergyMeter tet WHERE tet.feederId = :feederId and CAST(tet.startDate AS date ) = :startDate")
	Boolean existsByFeederAndStartDate(@Param("feederId")String feeder, @Param("startDate") Timestamp startDae);
	
	@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM EnergyMeter tet WHERE tet.feederId = :feederId and tet.endDate is  null")
	Boolean existsByFeederAndEndDateIsNull(@Param("feederId")String feeder);

	List<EnergyMeter> findByDataDivIn(List<String> divisions);

}
