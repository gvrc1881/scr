package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.Facility;
import com.scr.model.TpcBoardReportingFacility;

public interface TPCBoardDepotAssocRepository extends JpaRepository<TpcBoardReportingFacility, Long>{
	
	List<TpcBoardReportingFacility> findAll();
	 //Query Annotation for exist record
	@Query(value = "SELECT case when count(tbrf)> 0 then true else false  end  FROM TpcBoardReportingFacility tbrf WHERE tbrf.tpcBoard = :tpcBoard and tbrf.unitName  = :unitName")
	Boolean existsByTpcBoardAndUnitName(@Param("tpcBoard") String tpcBoard,@Param("unitName") String unitName);
	
    Optional<TpcBoardReportingFacility>findByTpcBoardAndUnitName(String tpcBoard,String unitName);
    
	List<TpcBoardReportingFacility> findByTpcBoard(String tpcBoard);
	
	List<TpcBoardReportingFacility> findByTpcBoardAndUnitType(String tpcBoard, String unitType);


	  
}
