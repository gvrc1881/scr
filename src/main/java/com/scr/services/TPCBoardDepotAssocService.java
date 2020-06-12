package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.TpcBoardReportingFacility;
import com.scr.repository.TPCBoardDepotAssocRepository;

@Service
public class TPCBoardDepotAssocService {
	static Logger log = LogManager.getLogger(TPCBoardDepotAssocService.class);

	@Autowired
	private TPCBoardDepotAssocRepository tpcBoardDepotAssocRepository;
	
	public List<TpcBoardReportingFacility> findAll() {
		// TODO Auto-generated method stub
		return tpcBoardDepotAssocRepository.findAll();

	}

	public void save(TpcBoardReportingFacility tpcBoardReportingFacility) {
		tpcBoardDepotAssocRepository.save(tpcBoardReportingFacility);
	}

	public Optional<TpcBoardReportingFacility> findTPCBoardDepotAssocById(Long id) {
		// TODO Auto-generated method stub
		return tpcBoardDepotAssocRepository.findById(id);

	}

	public void deleteTPCBoardDepotAssocById(Long id) {
		// TODO Auto-generated method stub
		tpcBoardDepotAssocRepository.deleteById(id);
	}
	public Boolean existsByTpcBoardAndUnitName(String tpcBoard, String unitName) {
		return tpcBoardDepotAssocRepository.existsByTpcBoardAndUnitName(tpcBoard,unitName);
	}
}
