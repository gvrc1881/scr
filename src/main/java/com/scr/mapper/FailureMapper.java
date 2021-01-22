package com.scr.mapper;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.model.Facility;
import com.scr.model.Failure;
import com.scr.repository.FacilityRepository;

@Component
public class FailureMapper {
	
	static Logger logger = LogManager.getLogger(FailureMapper.class);
	@Autowired
	private FacilityRepository facilityRepository;

	public Failure prepareFailureData(Failure failure2) {
		
		logger.info("failure mapper");
		
		if(failure2!= null) {
			
			Optional<Facility> facility  = facilityRepository.findByFacilityId(failure2.getFeedOf());
			Optional<Facility> fac  = facilityRepository.findByFacilityId(failure2.getExtendedOf());
			
			failure2.setFeedOf(facility.get().getFacilityName());
			failure2.setExtendedOf(fac.get().getFacilityName());
			
		}
		
		return failure2;
	}

	public Failure prepareFailureStationData(Failure failure2) {
		if(failure2!= null) {
					
					Optional<Facility> facility  = facilityRepository.findByFacilityId(failure2.getSubStation());
					
					
					failure2.setSubStation(facility.get().getFacilityName());					
					
				}
				
				return failure2;
	}

}
