package com.scr.services;


import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.SidingDetails;
import com.scr.model.StationsSection;
import com.scr.repository.SidingsRepository;
import com.scr.repository.StationsSectionsRepository;

@Service
public class SidingsService {
	
	private static Logger log = LogManager.getLogger(GuidenceItemService.class);
	
	
	@Autowired
	private SidingsRepository sidingsRepository;
	
	
	public List<SidingDetails> findAll() {
		return sidingsRepository.findAll();
	}
	
	public void save(SidingDetails sidingDetails) {
		sidingsRepository.save(sidingDetails);
	}
	public Optional<SidingDetails> findSidingsItemById(Long id) {
		return sidingsRepository.findById(id);
	}
	public void deleteSidingsItemById(Long id) {
		sidingsRepository.deleteById(id);
	}
	public boolean existsBySidingCode(String sidingCode) {
		return sidingsRepository.existsBySidingCode(sidingCode);
	}
	
}


