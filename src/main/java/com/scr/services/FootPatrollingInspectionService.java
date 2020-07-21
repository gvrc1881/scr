package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.scr.mapper.ContentManagementMapper;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Compliance;
import com.scr.model.ContentManagement;
import com.scr.model.FootPatrollingInspection;
import com.scr.model.Observation;
import com.scr.repository.ComplianceRepository;
import com.scr.repository.ContentManagementRepository;
import com.scr.repository.FootPatrollingInspectionRepository;
import com.scr.repository.ObservationsRepository;
import com.scr.util.Constants;

@Service
public class FootPatrollingInspectionService {
	
	
	@Autowired
	private FootPatrollingInspectionRepository footPatrollingInspectionRepository;
	@Autowired
	private ObservationsRepository observationsRepository;
	@Autowired
	private ComplianceRepository complianceRepository;
	@Autowired
	private ContentManagementMapper contentManagementMapper;
	@Autowired
	private ContentManagementRepository contentManagementRepository;
	
	static Logger log = LogManager.getLogger(FootPatrollingInspectionService.class);

	@Value("${observation.path}")
	private String observationPath;
	
	public List<FootPatrollingInspection> findAll() {
		// TODO Auto-generated method stub
		return footPatrollingInspectionRepository.findAll();
	}
	
	public void save(FootPatrollingInspection footPatrollingInspection) {
		// TODO Auto-generated method stub
		footPatrollingInspectionRepository.save(footPatrollingInspection);
	}
	public Optional<FootPatrollingInspection> findFPInspectionItemById(Long id) {
		// TODO Auto-generated method stub
		return footPatrollingInspectionRepository.findById(id);
	}

	public void deleteFPInspectionItemById(Long id) {
		footPatrollingInspectionRepository.deleteById(id);
	}
	
	
	//Observation Service
	public List<Observation> findAllObservations() {
		// TODO Auto-generated method stub
		return observationsRepository.findAll();
	}
	
	public void save(Observation observation) {
		// TODO Auto-generated method stub
		observationsRepository.save(observation);
	}
	public Optional<Observation> findObservationItemById(Long id) {
		// TODO Auto-generated method stub
		return observationsRepository.findById(id);
	}

	public void deleteObservationItemById(Long id) {
		observationsRepository.deleteById(id);
	}
	public Optional<Observation> findById(Long id) {
		// TODO Auto-generated method stub
		return observationsRepository.findById(id);
	}
	public ResponseStatus storeUploadedFiles(List<MultipartFile> multipartFile, String contentCategory, String description,
			String divisionCode, String createdBy, String zonal, String fU, String contentTopic,Long observationId) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(observationPath, contentCategory );
			if(folderResponse.getCode() == Constants.SUCCESS_CODE) {				
				List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
				ContentManagement fileId = contentManagementRepository.findTopByOrderByCommonFileIdDesc();
				Long commonFileId = (long) 0.0; 
				if(fileId == null || fileId.getCommonFileId() == null) {
					commonFileId = (long) 1;
				}else {
					commonFileId = fileId.getCommonFileId()+1;
				}
				Optional<Observation> observation =observationsRepository.findById(observationId);
				if (observation.isPresent()) {
					Observation obs = observation.get();
					if (obs.getAttachment() != null) {
						commonFileId = Long.parseLong(obs.getAttachment());
					} else {
						obs.setAttachment(String.valueOf(commonFileId));
					}
					observationsRepository.save(obs);
				}
				
				for(MultipartFile mf: multipartFile)
				{
					String folderPath = folderResponse.getMessage();
					liContentManagements.add(contentManagementMapper.saveAndStoreDetails(mf, divisionCode, createdBy, zonal, fU, contentTopic, description, contentCategory, folderPath, commonFileId));									
				}
				if(!liContentManagements.isEmpty()) {
					liContentManagements = contentManagementRepository.saveAll(liContentManagements);
										log.info("Files Details saved in to Database Successfully.");
				}
			}					
			responseStatus.setCode(Constants.SUCCESS_CODE);
			responseStatus.setMessage(Constants.JOB_SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while saving files "+e.getMessage());
			responseStatus.setCode(Constants.FAILURE_CODE);
			responseStatus.setMessage("ERROR >>> "+e.getMessage());
		}
		return responseStatus;
	}
	//Compliance Service
	public List<Compliance> findAllCompliances() {
		return complianceRepository.findAll();
	}
	
	public void save(Compliance compliance) {
		complianceRepository.save(compliance);
	}
	public Optional<Compliance> findComplianceItemById(Long id) {
		return complianceRepository.findById(id);
	}

	public void deleteComplianceItemById(Long id) {
		complianceRepository.deleteById(id);
	}
	public List<Compliance> findByStatus(String status) {	
		return complianceRepository.findByStatus(status);
	}
}
