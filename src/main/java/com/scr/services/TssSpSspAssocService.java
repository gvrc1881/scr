package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Facility;
import com.scr.model.TssSpSspAssoc;
import com.scr.repository.TssSpSspAssocRepository;



@Service

public class TssSpSspAssocService {
	
	private static Logger logger = LogManager.getLogger(TssSpSspAssocService.class);
	
	@Autowired
	private TssSpSspAssocRepository tssSpSSpRepository;	



	/*public List<TssSpSspAssoc> findByTssFacilityId(Long id) {
		
		logger.info("in service ===="+id);
		return tssSpSSpRepository.findByTssFacilityId(id);
	}*/



	public List<TssSpSspAssoc> findByTssFacilityId(Facility facility) {
		
		logger.info("in service ===="+facility);
		return tssSpSSpRepository.findByTssFacilityId(facility);
	}


	public Optional<TssSpSspAssoc> findBySspSpFacilityId(String string) {
		// TODO Auto-generated method stub
		return tssSpSSpRepository.findBySspSpFacilityId(string);
	}

}
