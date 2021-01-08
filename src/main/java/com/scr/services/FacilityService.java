package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Facility;
import com.scr.model.Make;
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
		return facilityRepository.findByParentDepotOrderByFacilityNameAsc(facilityName);
	}

	public List<Facility> findBySubDivision(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findBySubDivisionOrderByFacilityNameAsc(facilityName);
	}

	public List<Facility> findByDivision(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findByDivisionOrderByFacilityNameAsc(facilityName);
	}

	public List<Facility> findByZone(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findByZoneOrderByFacilityNameAsc(facilityName);
	}

	public List<Facility> findByParentFacilityId(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findByParentFacilityIdOrderByFacilityNameAsc(facilityName);
	}
	
	public void save(Facility facility) {
		// TODO Auto-generated method stub
		facilityRepository.save(facility);
	}
	
	public void deleteFacilityById(Long id) {
		// TODO Auto-generated method stub
		facilityRepository.deleteById(id);
	}

	public List<Facility> findAll() {
		// TODO Auto-generated method stub
		return facilityRepository.findAll();
	}
	
	public Optional<Facility> findFacilityById(Long id) {
		// TODO Auto-generated method stub
		return facilityRepository.findById(id);
	}
	
	public Boolean existsByFacilityName(String facilityName)
	{
		return facilityRepository.existsByFacilityName(facilityName);
	}
	
	public Boolean existsByFacilityId(String facilityId)
	{
		return facilityRepository.existsByFacilityId(facilityId);
	}
	public Optional<Facility> findByFacilityName(String facilityName) {
		// TODO Auto-generated method stub
		return facilityRepository.findByFacilityName(facilityName);
	}

}
