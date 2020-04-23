package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.StationsSection;
import com.scr.repository.StationsSectionsRepository;

@Service
public class StationsSectionsService {
	
	@Autowired
	private StationsSectionsRepository stationsSectionsRepository;
	
	public List<StationsSection> findAll() {
		// TODO Auto-generated method stub
		return stationsSectionsRepository.findAll();
	}

	public void save(StationsSection stationsSection) {
		// TODO Auto-generated method stub
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

}
