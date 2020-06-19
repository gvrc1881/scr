package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.StationsSection;
import com.scr.repository.StationsSectionsRepository;

@Service
public class StationsSectionsService {
	static Logger logger = LogManager.getLogger(DrivesService.class);

	@Autowired
	private StationsSectionsRepository stationsSectionsRepository;
	
	public List<StationsSection> findAll() {
		return stationsSectionsRepository.findAll();
	}
	public void save(StationsSection stationsSection) {
		stationsSectionsRepository.save(stationsSection);
	}

	public Optional<StationsSection> findStationSectionsById(Long id) {
		// TODO Auto-generated method stub
		return stationsSectionsRepository.findById(id);

	}

	public void deleteStationSectionsById(Long id) {
		// TODO Auto-generated method stub
		stationsSectionsRepository.deleteById(id);
	}
	public Boolean existsByStationCode(String stationCode) {
		return stationsSectionsRepository.existsByStationCode(stationCode);
	}
	//exist condition
	public Boolean existsByStationName(String stationName) {
		return stationsSectionsRepository.existsByStationName(stationName);
	}
}
