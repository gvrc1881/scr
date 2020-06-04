package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scr.model.TpcBoardReportingFacility;

public interface TPCBoardDepotAssocRepository extends JpaRepository<TpcBoardReportingFacility, Long>{
	
	List<TpcBoardReportingFacility> findAll();
	 
	    public boolean existsByTpcBoard(String tpcBoard);
	    public boolean existsByUnitName(String unitName);
}
