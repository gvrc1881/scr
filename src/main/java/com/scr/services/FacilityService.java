package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Facility;
import com.scr.repository.FacilityRepository;

@Service
public class FacilityService {
	
	@Autowired
	private FacilityRepository facilityRepository;

	public Optional<Facility> findByFacilityId(String facilityId) {
		// TODO Auto-generated method stub
		return facilityRepository.findByFacilityId(facilityId);
	}

	public List<Facility> findByParentDepot(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findByParentDepot(facilityName);
	}

	public List<Facility> findBySubDivision(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findBySubDivision(facilityName);
	}

	public List<Facility> findByDivision(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findByDivision(facilityName);
	}

	public List<Facility> findByZone(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findByZone(facilityName);
	}

}
