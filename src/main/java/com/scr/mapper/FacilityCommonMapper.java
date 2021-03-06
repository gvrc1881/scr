package com.scr.mapper;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.model.Facility;
import com.scr.model.FootPatrollingSection;
import com.scr.model.GantryMasterData;
import com.scr.model.OheLocation;
import com.scr.model.Sector;
import com.scr.model.SubSector;
import com.scr.repository.FacilityRepository;

@Component
public class FacilityCommonMapper {
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	public GantryMasterData prepareGantryData(
			GantryMasterData gantryMasterData) {
		if (gantryMasterData.getFacilityId() != null ) {
			Optional<Facility> facility  = facilityRepository.findByFacilityId(gantryMasterData.getFacilityId());
			if (facility.isPresent()) {
				gantryMasterData.setFacilityId(facility.get().getFacilityName());
			}
		}
		
		return gantryMasterData;
	}
	public Sector prepareSectorData(
			Sector sector) {
		if (sector.getFacilityId() != null ) {
			Optional<Facility> facility  = facilityRepository.findByFacilityId(sector.getFacilityId());
			if (facility.isPresent()) {
				sector.setFacilityId(facility.get().getFacilityName());
			}
		}
		
		return sector;
	}
	public SubSector prepareSubSectorData(
			SubSector subSector) {
		if (subSector.getFacilityId() != null ) {
			Optional<Facility> facility  = facilityRepository.findByFacilityId(subSector.getFacilityId());
			if (facility.isPresent()) {
				subSector.setFacilityId(facility.get().getFacilityName());
			}
		}
		
		return subSector;
	}
	
	public OheLocation prepareOheLocationData(
			OheLocation oheLocation) {
		if (oheLocation.getFacilityId() != null ) {
			Optional<Facility> facility  = facilityRepository.findByFacilityId(oheLocation.getFacilityId());
			if (facility.isPresent()) {
				oheLocation.setFacilityId(facility.get().getFacilityName());
			}
		}
		
		return oheLocation;
	}
	public FootPatrollingSection prepareFpSectionsData(
			FootPatrollingSection footPatrollingSection) {
		if (footPatrollingSection.getFacilityDepot() != null ) {
			Optional<Facility> facility  = facilityRepository.findByFacilityId(footPatrollingSection.getFacilityDepot());
			if (facility.isPresent()) {
				footPatrollingSection.setFacilityDepot(facility.get().getFacilityName());
			}
		}
		
		return footPatrollingSection;
	}
}
