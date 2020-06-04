package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.TpcBoardReportingFacility;
import com.scr.repository.TPCBoardDepotAssocRepository;

@Service
public class TPCBoardDepotAssocService {
	
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
	public boolean existsByTpcBoard(String tpcBoard) {
		return tpcBoardDepotAssocRepository.existsByTpcBoard(tpcBoard);
	}
	public boolean existsByUnitName(String unitName) {
		return tpcBoardDepotAssocRepository.existsByUnitName(unitName);
	}
}
