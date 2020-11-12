package com.scr.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.scr.mapper.FpInspectionMapper;
import com.scr.message.request.InspectionRequest;
import com.scr.message.response.ObservationResponse;
import com.scr.model.Compliance;
import com.scr.model.ContentManagement;
import com.scr.model.FootPatrollingInspection;
import com.scr.mapper.CommonMapper;
import com.scr.model.Observation;
import com.scr.repository.ComplianceRepository;
import com.scr.repository.ContentManagementRepository;
import com.scr.repository.FootPatrollingInspectionRepository;
import com.scr.repository.ObservationUtilRepository;
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
	private ContentManagementRepository contentManagementRepository;
	@Autowired
	private FpInspectionMapper fpInspectionMapper;
	@Autowired
	private ObservationUtilRepository utilRepository;
	
	@Autowired
	private CommonMapper commonMapper;
	
	static Logger log = LogManager.getLogger(FootPatrollingInspectionService.class);

	@Value("${observation.path}")
	private String observationPath;
	
	@Value("${compliance.path}")
	private String compliancePath;
	
	public List<FootPatrollingInspection> findAll() {
		// TODO Auto-generated method stub
		return footPatrollingInspectionRepository.findAll();
	}
	public FootPatrollingInspection save(FootPatrollingInspection footPatrollingInspection) {
		// TODO Auto-generated method stub
		return footPatrollingInspectionRepository.save(footPatrollingInspection);
	}
	public Optional<FootPatrollingInspection> findFPInspectionItemById(Long id) {
		// TODO Auto-generated method stub
		return footPatrollingInspectionRepository.findById(id);
	}

	public void deleteFPInspectionItemById(Long id) {
		footPatrollingInspectionRepository.deleteById(id);
	}
	
	
	//Observation Service
	public List<Observation> findAllObservationItem() {
		// TODO Auto-generated method stub
		return observationsRepository.findAll();
	}
	public List<Observation> findByInspectionSeqId(String inspectionSeqId) {
		// TODO Auto-generated method stub
		return observationsRepository.findByInspectionSeqId(inspectionSeqId);
	}
	public @Valid boolean saveObservation(@Valid InspectionRequest observationsRequest, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		
		liContentManagements = commonMapper.prepareContentManagementList(file, observationPath, Constants.OBSERVATIONS,
				"","","","Observations","","","","","", Integer.parseInt(observationsRequest.getCreatedBy()));
		
		if(liContentManagements != null && !liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
			Observation observation = fpInspectionMapper.prepareObservationsModel(observationsRequest, file, liContentManagements.get(0).getCommonFileId());
			observation = observationsRepository.save(observation);
		}else {
			Observation observation = fpInspectionMapper.prepareObservationsModel(observationsRequest, file, new Long(0));
			observation = observationsRepository.save(observation);
		}
		
		return true;
	}
	
	public String updateObservationsData(@Valid InspectionRequest observationsRequest, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();
		liContentManagements = commonMapper.prepareForUpdateContentManagementList(file, observationPath, Constants.OBSERVATIONS,
				"","","","Observations","","","","","", Integer.parseInt(observationsRequest.getUpdatedBy()), observationsRequest.getAttachment());
		
		Long commonFileId = (long) 0.0;
		if(liContentManagements!=null && !liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
			commonFileId = liContentManagements.get(0).getCommonFileId();
		}
		else {
			commonFileId = observationsRequest.getAttachment() != null ? Long.parseLong(observationsRequest.getAttachment()) : (long) 0.0;
			log.info("find file = "+commonFileId);
		}
		
		log.info("find the existing observation by id : "+commonFileId);
		Optional<Observation> observation = observationsRepository.findById(observationsRequest.getId());
		if(observation.isPresent()) {
			Observation observationUpdate = fpInspectionMapper.prepareObservationsUpdataData(observation.get(), observationsRequest, file, commonFileId);
			observationUpdate = observationsRepository.save(observationUpdate);
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
	public List<Compliance> findByObeservationSeqId(String obeservationSeqId) {
		// TODO Auto-generated method stub
		return complianceRepository.findByObeservationSeqId(obeservationSeqId);
	}
		public @Valid boolean saveCompliances(@Valid InspectionRequest complianceRequest, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		
		liContentManagements = commonMapper.prepareContentManagementList(file, compliancePath, Constants.COMPLIANCES,
				"","","","Compliance","","","","","", Integer.parseInt(complianceRequest.getCreatedBy()));
		
		if(liContentManagements != null && !liContentManagements.isEmpty()) {
			contentManagementRepository.saveAll(liContentManagements);
			log.info("Files Details saved in to Database Successfully.");
			Compliance compliance = fpInspectionMapper.prepareComplianceModel(complianceRequest, file, liContentManagements.get(0).getCommonFileId());
			compliance = complianceRepository.save(compliance);
		}else {
			Compliance compliance = fpInspectionMapper.prepareComplianceModel(complianceRequest, file, new Long(0));
			compliance = complianceRepository.save(compliance);
		}
		
		return true;
	}
		public String updateCompliancesData(@Valid InspectionRequest complianceRequest, List<MultipartFile> file) {
			
			List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();
			liContentManagements = commonMapper.prepareForUpdateContentManagementList(file, compliancePath, Constants.COMPLIANCES,
					"","","","Compliance","","","","","", Integer.parseInt(complianceRequest.getUpdatedBy()), complianceRequest.getAttachment());
			
			Long commonFileId = (long) 0.0;
			
			if(liContentManagements != null && !liContentManagements.isEmpty()) {
				contentManagementRepository.saveAll(liContentManagements);
				log.info("Files Details saved in to Database Successfully.");
				commonFileId = liContentManagements.get(0).getCommonFileId();
			}
			else {
				commonFileId = complianceRequest.getAttachment() != null ? Long.parseLong(complianceRequest.getAttachment()) : (long) 0.0;
				log.info("find file = "+commonFileId);
			}
			
			log.info("find the existing compliance by id : "+commonFileId);
			Optional<Compliance> compliance = complianceRepository.findById(complianceRequest.getId());
			if(compliance.isPresent()) {
				Compliance complianceUpdate = fpInspectionMapper.prepareCompliancesUpdataData(compliance.get(), complianceRequest, file, commonFileId);
				complianceUpdate = complianceRepository.save(complianceUpdate);
				return Constants.JOB_SUCCESS_MESSAGE;
			}else {
				return "Invalid Compliance Id";
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
	
	public List<ObservationResponse> findObservation(String section, String facilityId,String nameOfStaff,String fromDateTime) {
		List<ObservationResponse> inspectionList = utilRepository.findObservation(section,facilityId,nameOfStaff,fromDateTime);
		log.info("inspectionListService"+inspectionList);
		log.info("inspectionListService"+inspectionList.size());
		return inspectionList;
	}
	public Optional<ContentManagement> findInspectionsContentByIdAndCommon(Long commonFileId, Long Id) {
		return contentManagementRepository.findByIdAndCommonFileId(Id, commonFileId);
	}
	public void updatefileStatus(ContentManagement contentUpdate) {
		contentManagementRepository.save(contentUpdate);
	}
}
