package com.scr.mapper;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.scr.message.request.InspectionRequest;
import com.scr.model.Compliance;
import com.scr.model.Facility;
import com.scr.model.FootPatrollingInspection;
import com.scr.model.Observation;
import com.scr.model.OheLocation;
import com.scr.repository.FacilityRepository;

@Component
public class FpInspectionMapper {
	static Logger logger = LogManager.getLogger(FpInspectionMapper.class);
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	public Observation prepareObservationsModel(@Valid InspectionRequest request, List<MultipartFile> file, Long commonFileId) {
		Observation observations = null;
		if(request != null) {
						
			observations = new Observation();
			observations.setInspectionSeqId(request.getInspectionSeqId());
			observations.setLocation(request.getLocation());
			observations.setObservationCategory(request.getObservationCategory());
			observations.setObservationItem(request.getObservationItem());
			observations.setDescription(request.getDescription());
			observations.setActionRequired(request.getActionRequired());
			observations.setCreatedBy(request.getCreatedBy());
			observations.setAttachment(String.valueOf(commonFileId));
		}
		return observations;
	}
	public Observation prepareObservationsUpdataData(Observation observations,
			@Valid InspectionRequest request, List<MultipartFile> file, Long commonFileId) {
		if(request != null) {
			observations.setInspectionSeqId(request.getInspectionSeqId());
			observations.setLocation(request.getLocation());
			observations.setObservationCategory(request.getObservationCategory());
			observations.setObservationItem(request.getObservationItem());
			observations.setDescription(request.getDescription());
			observations.setActionRequired(request.getActionRequired());
			observations.setAttachment(String.valueOf(commonFileId));
			observations.setUpdatedBy(request.getUpdatedBy());			
		}
		return observations;
	}
	public Compliance prepareComplianceModel(@Valid InspectionRequest request, List<MultipartFile> file, Long commonFileId) {
		Compliance compliance = null;
		if(request != null) {			
			compliance = new Compliance();
			compliance.setObeservationSeqId(request.getObeservationSeqId());
			compliance.setStatus(request.getStatus());
			compliance.setAction(request.getAction());
			compliance.setComplianceBy(request.getComplianceBy());
			compliance.setCompliedDateTime(request.getCompliedDateTime());
			compliance.setCreatedBy(request.getCreatedBy());
			compliance.setDocument(String.valueOf(commonFileId));
		}
		return compliance;
	}
	public Compliance prepareCompliancesUpdataData(Compliance compliance,
			@Valid InspectionRequest request, List<MultipartFile> file, Long commonFileId) {
		if(request != null) {
			compliance.setObeservationSeqId(request.getObeservationSeqId());
			compliance.setStatus(request.getStatus());
			compliance.setAction(request.getAction());
			compliance.setComplianceBy(request.getComplianceBy());
			compliance.setCompliedDateTime(request.getCompliedDateTime());
			compliance.setDocument(String.valueOf(commonFileId));
			compliance.setUpdatedBy(request.getUpdatedBy());			
		}
		return compliance;
	}
	
	public FootPatrollingInspection prepareFootPatrollingInspectionData(
			FootPatrollingInspection footPatrollingInspection) {
		if (footPatrollingInspection.getFacilityId() != null ) {
			Optional<Facility> facility  = facilityRepository.findByFacilityId(footPatrollingInspection.getFacilityId());
			if (facility.isPresent()) {
				footPatrollingInspection.setFacilityId(facility.get().getFacilityName());
			}
		}
		
		return footPatrollingInspection;
	}
}
