package com.scr.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.scr.mapper.ContentManagementMapper;
import com.scr.mapper.FpInspectionMapper;
import com.scr.message.request.InspectionRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Compliance;
import com.scr.model.ContentManagement;
import com.scr.model.FootPatrollingInspection;
import com.scr.model.Inspection;
import com.scr.model.Observation;
import com.scr.repository.ComplianceRepository;
import com.scr.repository.ContentManagementRepository;
import com.scr.repository.FootPatrollingInspectionRepository;
import com.scr.repository.ObservationsRepository;
import com.scr.util.Constants;
import com.scr.util.Helper;

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
	@Autowired
	private FpInspectionMapper fpInspectionMapper;
	static Logger log = LogManager.getLogger(FootPatrollingInspectionService.class);

	@Value("${observation.path}")
	private String observationPath;
	
	@Value("${compliance.path}")
	private String compliancePath;
	
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
	
	public @Valid boolean save(@Valid InspectionRequest request, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		ContentManagement fileId = contentManagementRepository.findTopByOrderByCommonFileIdDesc();
		Long commonFileId = (long) 0.0; 
		if(fileId == null || fileId.getCommonFileId() == null) {
			commonFileId = (long) 1;
		}else {
			commonFileId = fileId.getCommonFileId()+1;
		}
		
		ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(observationPath, Constants.OBSERVATIONS );
		
		for(MultipartFile mf: file)
		{	
			log.info("filename: "+mf.getOriginalFilename());
			String changedFileName = Helper.prepareChangeFileName(mf, Constants.OBSERVATIONS, request.getCreatedBy());
			Path rootLocation = null;
			try {
				String folderPath = folderResponse.getMessage();
				rootLocation = Paths.get(folderPath);
				Files.copy(mf.getInputStream(), rootLocation.resolve(changedFileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ContentManagement contentManagement = new ContentManagement();			
			log.info("File Saved Successfully with name "+changedFileName);
			contentManagement = new ContentManagement();
			contentManagement.setCommonFileId(commonFileId);
			contentManagement.setDivision("");
			contentManagement.setFunUnit("");
			contentManagement.setGenOps("");
			contentManagement.setTopic("Observations");
			contentManagement.setDescription("");
			contentManagement.setOriginalFileName(mf.getOriginalFilename());				
			contentManagement.setChangeFileName(rootLocation+"\\"+changedFileName);
			double bytes = mf.getSize();
			log.info("bytes = "+bytes);
			double kilobytes = Math.round((bytes / 1024) * 100.0) / 100.0;
			log.info("KB = "+kilobytes);
			double megabytes = Math.round((kilobytes / 1024) * 100.0) / 100.0;
			log.info("mega bytes = "+megabytes);
			contentManagement.setFileSize(kilobytes+" KB");
			contentManagement.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			contentManagement.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			contentManagement.setCreatedBy(Integer.parseInt(request.getCreatedBy()));
			contentManagement.setAssetTypeRlyId("");
			contentManagement.setMake("");
			contentManagement.setModel("");
			contentManagement.setDocCategory("");
			contentManagement.setStatusId(Constants.ACTIVE_STATUS_ID);
			
			liContentManagements.add(contentManagement);
		}
		if(!liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
		}
		Observation observations = fpInspectionMapper.prepareObservationsModel(request, file, commonFileId);
		observations = observationsRepository.save(observations);
		return true;
	}
	public String updateObservationsData(@Valid InspectionRequest request, List<MultipartFile> file) {
		Long commonFileId = (long) 0.0; 
		if(file != null && file.size() > 0) {
			log.info("file ");
		ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(observationPath, Constants.OBSERVATIONS );
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		log.info("commonfifle "+request.getAttachment());
		if(request.getAttachment() == null || Long.parseLong(request.getAttachment()) ==0.0 ) {
			
			ContentManagement fileId = contentManagementRepository.findTopByOrderByCommonFileIdDesc();		
			if(fileId == null || fileId.getCommonFileId() == null) {
				commonFileId = (long) 1;
			}else {
				commonFileId = fileId.getCommonFileId()+1;
			}
		}else {
			commonFileId = request.getAttachment() != null ? Long.parseLong(request.getAttachment()) : (long) 0.0;
		}
		log.info("find file = "+commonFileId);
		
		for(MultipartFile mf: file)
		{	
			ContentManagement contentManagement = new ContentManagement();
			String changedFileName = Helper.prepareChangeFileName(mf, Constants.OBSERVATIONS, request.getUpdatedBy());
			Path rootLocation = null;
			try {
				String folderPath = folderResponse.getMessage();
				rootLocation = Paths.get(folderPath);
				Files.copy(mf.getInputStream(), rootLocation.resolve(changedFileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			log.info("filename: "+mf.getOriginalFilename());
			
			log.info("File Saved Successfully with name "+changedFileName);
			contentManagement = new ContentManagement();
			contentManagement.setCommonFileId(commonFileId);
			contentManagement.setDivision("");
			contentManagement.setFunUnit("");
			contentManagement.setGenOps("");
			contentManagement.setTopic("Observations");
			contentManagement.setDescription("");
			contentManagement.setOriginalFileName(mf.getOriginalFilename());				
			contentManagement.setChangeFileName(rootLocation+"\\"+changedFileName);
			double bytes = mf.getSize();
			log.info("bytes = "+bytes);
			double kilobytes = Math.round((bytes / 1024) * 100.0) / 100.0;
			log.info("KB = "+kilobytes);
			double megabytes = Math.round((kilobytes / 1024) * 100.0) / 100.0;
			log.info("mega bytes = "+megabytes);
			contentManagement.setFileSize(kilobytes+" KB");
			contentManagement.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			contentManagement.setModifiedBy(Integer.parseInt(request.getUpdatedBy()));
			contentManagement.setAssetTypeRlyId("");
			contentManagement.setMake("");
			contentManagement.setModel("");
			contentManagement.setDocCategory("");
			contentManagement.setStatusId(Constants.ACTIVE_STATUS_ID);
			
			liContentManagements.add(contentManagement);
		}
		if(!liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
		}
		}else {
			commonFileId = request.getAttachment() != null ? Long.parseLong(request.getAttachment()) : (long) 0.0;
			log.info("find file = "+commonFileId);
		}
		
		log.info("find the existing observation by id : "+commonFileId);
		Optional<Observation> observations = observationsRepository.findById(request.getId());
		if(observations.isPresent()) {
			Observation observationsUpdate = fpInspectionMapper.prepareObservationsUpdataData(observations.get(), request, file, commonFileId);
			observationsUpdate = observationsRepository.save(observationsUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Observations Id";
		}
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
	
	public List<ContentManagement> findObservationsContentById(Long commonFileId) {
		return contentManagementRepository.findByCommonFileIdAndStatusId(commonFileId, Constants.ACTIVE_STATUS_ID);
	}
	//Compliance Service
	public List<Compliance> findAllCompliances() {
		return complianceRepository.findAll();
	}
	
	public @Valid boolean saveCompliances(@Valid InspectionRequest request, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		ContentManagement fileId = contentManagementRepository.findTopByOrderByCommonFileIdDesc();
		Long commonFileId = (long) 0.0; 
		if(fileId == null || fileId.getCommonFileId() == null) {
			commonFileId = (long) 1;
		}else {
			commonFileId = fileId.getCommonFileId()+1;
		}
		
		ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(compliancePath, Constants.COMPLIANCES );
		
		for(MultipartFile mf: file)
		{	
			log.info("filename: "+mf.getOriginalFilename());
			String changedFileName = Helper.prepareChangeFileName(mf, Constants.COMPLIANCES, request.getCreatedBy());
			Path rootLocation = null;
			try {
				String folderPath = folderResponse.getMessage();
				rootLocation = Paths.get(folderPath);
				Files.copy(mf.getInputStream(), rootLocation.resolve(changedFileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ContentManagement contentManagement = new ContentManagement();			
			log.info("File Saved Successfully with name "+changedFileName);
			contentManagement = new ContentManagement();
			contentManagement.setCommonFileId(commonFileId);
			contentManagement.setDivision("");
			contentManagement.setFunUnit("");
			contentManagement.setGenOps("");
			contentManagement.setTopic("Compliances");
			contentManagement.setDescription("");
			contentManagement.setOriginalFileName(mf.getOriginalFilename());				
			contentManagement.setChangeFileName(rootLocation+"\\"+changedFileName);
			double bytes = mf.getSize();
			log.info("bytes = "+bytes);
			double kilobytes = Math.round((bytes / 1024) * 100.0) / 100.0;
			log.info("KB = "+kilobytes);
			double megabytes = Math.round((kilobytes / 1024) * 100.0) / 100.0;
			log.info("mega bytes = "+megabytes);
			contentManagement.setFileSize(kilobytes+" KB");
			contentManagement.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			contentManagement.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			contentManagement.setCreatedBy(Integer.parseInt(request.getCreatedBy()));
			contentManagement.setAssetTypeRlyId("");
			contentManagement.setMake("");
			contentManagement.setModel("");
			contentManagement.setDocCategory("");
			contentManagement.setStatusId(Constants.ACTIVE_STATUS_ID);
			
			liContentManagements.add(contentManagement);
		}
		if(!liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
		}
		Compliance compliance = fpInspectionMapper.prepareComplianceModel(request, file, commonFileId);
		compliance = complianceRepository.save(compliance);
		return true;
	}
	public String updateCompliancesData(@Valid InspectionRequest request, List<MultipartFile> file) {
		Long commonFileId = (long) 0.0; 
		if(file != null && file.size() > 0) {
			log.info("file ");
		ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(compliancePath, Constants.COMPLIANCES );
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		log.info("commonfifle "+request.getAttachment());
		if(request.getAttachment() == null || Long.parseLong(request.getAttachment()) ==0.0 ) {
			
			ContentManagement fileId = contentManagementRepository.findTopByOrderByCommonFileIdDesc();		
			if(fileId == null || fileId.getCommonFileId() == null) {
				commonFileId = (long) 1;
			}else {
				commonFileId = fileId.getCommonFileId()+1;
			}
		}else {
			commonFileId = request.getAttachment() != null ? Long.parseLong(request.getAttachment()) : (long) 0.0;
		}
		log.info("find file = "+commonFileId);
		
		for(MultipartFile mf: file)
		{	
			ContentManagement contentManagement = new ContentManagement();
			String changedFileName = Helper.prepareChangeFileName(mf, Constants.COMPLIANCES, request.getUpdatedBy());
			Path rootLocation = null;
			try {
				String folderPath = folderResponse.getMessage();
				rootLocation = Paths.get(folderPath);
				Files.copy(mf.getInputStream(), rootLocation.resolve(changedFileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			log.info("filename: "+mf.getOriginalFilename());
			
			log.info("File Saved Successfully with name "+changedFileName);
			contentManagement = new ContentManagement();
			contentManagement.setCommonFileId(commonFileId);
			contentManagement.setDivision("");
			contentManagement.setFunUnit("");
			contentManagement.setGenOps("");
			contentManagement.setTopic("Compliances");
			contentManagement.setDescription("");
			contentManagement.setOriginalFileName(mf.getOriginalFilename());				
			contentManagement.setChangeFileName(rootLocation+"\\"+changedFileName);
			double bytes = mf.getSize();
			log.info("bytes = "+bytes);
			double kilobytes = Math.round((bytes / 1024) * 100.0) / 100.0;
			log.info("KB = "+kilobytes);
			double megabytes = Math.round((kilobytes / 1024) * 100.0) / 100.0;
			log.info("mega bytes = "+megabytes);
			contentManagement.setFileSize(kilobytes+" KB");
			contentManagement.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			contentManagement.setModifiedBy(Integer.parseInt(request.getUpdatedBy()));
			contentManagement.setAssetTypeRlyId("");
			contentManagement.setMake("");
			contentManagement.setModel("");
			contentManagement.setDocCategory("");
			contentManagement.setStatusId(Constants.ACTIVE_STATUS_ID);
			
			liContentManagements.add(contentManagement);
		}
		if(!liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
		}
		}else {
			commonFileId = request.getAttachment() != null ? Long.parseLong(request.getAttachment()) : (long) 0.0;
			log.info("find file = "+commonFileId);
		}
		
		log.info("find the existing Compliance by id : "+commonFileId);
		Optional<Compliance> compliances = complianceRepository.findById(request.getId());
		if(compliances.isPresent()) {
			Compliance compliancesUpdate = fpInspectionMapper.prepareCompliancesUpdataData(compliances.get(), request, file, commonFileId);
			compliancesUpdate = complianceRepository.save(compliancesUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Observations Id";
		}
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
	
	public List<Inspection> findObservation(String section, String facilityId,String nameOfStaff,Timestamp fromDateTime) {
		List<Inspection> inspectionList = observationsRepository.findObservation(section,facilityId,nameOfStaff,fromDateTime);
		log.info("inspectionListService"+inspectionList);
		log.info("inspectionListService"+inspectionList.size());
		return inspectionList;
	}
	
}
