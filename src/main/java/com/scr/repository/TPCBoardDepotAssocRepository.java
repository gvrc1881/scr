package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.TpcBoardReportingFacility;

public interface TPCBoardDepotAssocRepository extends JpaRepository<TpcBoardReportingFacility, Long>{
	
	List<TpcBoardReportingFacility> findAll();

}
