package com.scr.mapper;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.model.Facility;
import com.scr.model.PrecautionaryMeasure;
import com.scr.repository.FacilityRepository;

@Component
public class PrecautionaryMeasureMapper {
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	public PrecautionaryMeasure preparePreacutionaryMeasureData(
			PrecautionaryMeasure precautionaryMeasure) {
		if (precautionaryMeasure.getFacilityId() != null ) {
			Optional<Facility> facility  = facilityRepository.findByFacilityId(precautionaryMeasure.getFacilityId());
			if (facility.isPresent()) {
				precautionaryMeasure.setFacilityId(facility.get().getFacilityName());
			}
		}
		
		return precautionaryMeasure;
	}


}
