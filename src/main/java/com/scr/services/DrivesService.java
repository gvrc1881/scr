package com.scr.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scr.mapper.CommonMapper;
import com.scr.mapper.ContentManagementMapper;
import com.scr.mapper.DriveMapper;
import com.scr.message.request.CopyDrivesRequest;
import com.scr.message.request.DriveRequest;
import com.scr.message.response.DriveTargetResponse;
import com.scr.message.response.DrivesResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.AssetScheduleActivityAssoc;
import com.scr.model.ContentManagement;
import com.scr.model.CrsEigInspections;
import com.scr.model.Division;
import com.scr.model.DriveCategory;
import com.scr.model.DriveCategoryAsso;
import com.scr.model.DriveCheckList;
import com.scr.model.DriveDailyProgress;
import com.scr.model.DriveProgressId;
import com.scr.model.DriveTarget;
import com.scr.model.Drives;
import com.scr.model.ElectrificationTargets;
import com.scr.model.Facility;
import com.scr.model.Failure;
import com.scr.model.FailureAnalysis;
import com.scr.model.FunctionalLocationTypes;
import com.scr.model.GuidenceItem;
import com.scr.model.InspectionType;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Product;
import com.scr.model.Stipulations;
import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
import com.scr.repository.ChecklistRepository;
import com.scr.repository.ContentManagementRepository;
import com.scr.repository.DivisionRepository;
import com.scr.repository.DriveCategoryAssoRepository;
import com.scr.repository.DriveCategoryRepository;
import com.scr.repository.DriveElectrificationTargetsRepository;
import com.scr.repository.DriveFailureAnalysisRepository;
import com.scr.repository.DriveInspectionRepository;
import com.scr.repository.DriveProgressIdRepository;
import com.scr.repository.DriveProgressRecordRepository;
import com.scr.repository.DriveStipulationRepository;
import com.scr.repository.DriveTargetRepository;
import com.scr.repository.DrivesRepository;
import com.scr.repository.FunctionalLocationTypesRepository;
import com.scr.repository.InspectionTypeRepository;
import com.scr.repository.MeasureOrActivityListRepository;
import com.scr.repository.ProductRepository;
import com.scr.repository.FacilityRepository;
import com.scr.util.Constants;

@Service
public class DrivesService {
	
	static Logger logger = LogManager.getLogger(DrivesService.class);
	
	@Value("${stipulation.path}")
	private String stipulationPath;
	
	@Value("${inspection.path}")
	private String inspectionPath;
	
	@Value("${content.management.path}")
	private String contentManagementPath;
		
	@Autowired
	private DriveMapper driveMapper;
	
	@Autowired
	private DrivesRepository driveRepository;
	
	@Autowired
	private ChecklistRepository checklistRepository;
	
	@Autowired
	private DriveTargetRepository driveTargetRepository;
	
	@Autowired
	private DriveStipulationRepository driveStipulationRepository;
	
	@Autowired
	private DriveElectrificationTargetsRepository driveElectrificationTargetsRepository;
	
	@Autowired
	private DriveInspectionRepository driveInspectionRepository;
	
	@Autowired
	private MeasureOrActivityListRepository measureOrActivityListRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private DriveProgressRecordRepository driveProgressRecordRepository;
	
	@Autowired
	private DriveFailureAnalysisRepository driveFailureAnalysisRepository;
	
	@Autowired
	private DriveCategoryRepository driveCategoryRepository;
	
	@Autowired
	private DriveCategoryAssoRepository driveCategoryAssoRepository;
	
	@Autowired
	private DivisionRepository divisionRepository;
	
	@Autowired
	private InspectionTypeRepository inspectionTypeRepository;
	
	@Autowired
	private ContentManagementRepository repository;
	
	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private DriveProgressIdRepository driveProgressIdRepository;
	
	@Autowired
	private FunctionalLocationTypesRepository functionalLocationTypesRepository;
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	@Autowired
	private ContentManagementMapper contentManagementMapper;
	
	@Value("${drive.path}")
	private String drivePath;
	
	@Value("${failureAnalysis.path}")
	private String failureAnalysisPath;
	
	public List<Drives> findAllDrives() {
		logger.info("Fetcing drives data where active is 1.");
		return driveRepository.findByStatusIdAndOrderByCreatedOnDesc(Constants.ACTIVE_STATUS_ID);
	}	

	public @Valid boolean saveDriveData(@Valid DriveRequest driveRequest) throws Exception {
		logger.info("Calling mapper for preparing the drive model object");
		Drives drive = driveMapper.prepareDriveModel(driveRequest);
		if (drive != null) {
			logger.info("After prepared model object, saving to drive table");
			drive = driveRepository.save(drive);
			logger.info("Drive data saved successfully.");
			return true;
		} else {
			logger.info("Preparing drive model object failed");
			return false;
		}
	}

	public String updateDriveData(@Valid DriveRequest request) {
		Optional<Drives> drives = driveRepository.findById(request.getId());
		if(drives.isPresent()) {
			Drives driveUpdate = driveMapper.prepareDriveUpdataData(drives.get(), request);
			driveUpdate = driveRepository.save(driveUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Id";
		}
	}
	public String deleteDrive(Long id) {
		Optional<Drives> driveOptional = driveRepository.findById(id);
		if (driveOptional.isPresent()) {
			Drives driveToUpdate = driveOptional.get();
			driveToUpdate.setId(id);
			driveToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveRepository.save(driveToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Id";
		}
	}
	
	public Optional<Drives> findDriveById(Long id) {
		return driveRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}
	public Boolean existsByDriveNameAndStatusId(String name, Integer statusId) {
		return driveRepository.existsByNameAndStatusId(name, statusId);
	}
	public Boolean existsByDriveDescriptionAndStatusId(String description, Integer statusId) {
		return driveRepository.existsByDescriptionAndStatusId(description, statusId);
	}
	

	// DRIVE CATEGORY
	public List<DriveCategory> findAllDriveCategory() {
		return driveCategoryRepository.findByStatusIdAndOrderByCeatedOnDesc(Constants.ACTIVE_STATUS_ID);
	}	

	public @Valid boolean saveDriveCategoryData(@Valid DriveRequest driveRequest) {
		DriveCategory driveCategory = driveMapper.prepareDriveCategoryModel(driveRequest);
		driveCategory = driveCategoryRepository.save(driveCategory);
		return true;
	}	

	public String updateDriveCategoryData(@Valid DriveRequest request) {
		Optional<DriveCategory> driveCategory = driveCategoryRepository.findById(request.getId());
		if(driveCategory.isPresent()) {
			DriveCategory driveCategoryUpdate = driveMapper.prepareDriveCategoryUpdataData(driveCategory.get(), request);
			driveCategoryUpdate = driveCategoryRepository.save(driveCategoryUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Category Id";
		}
	}
	public String deleteDriveCategory(Long id) {
		Optional<DriveCategory> driveCategoryOptional = driveCategoryRepository.findById(id);
		if (driveCategoryOptional.isPresent()) {
			DriveCategory driveCategoryToUpdate = driveCategoryOptional.get();
			driveCategoryToUpdate.setId(id);
			driveCategoryToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveCategoryRepository.save(driveCategoryToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Category Id";
		}
	}
	
	public Optional<DriveCategory> findDriveCategoryById(Long id) {
		return driveCategoryRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}
	public Boolean existsByDriveCategoryNameAndStatusId(String name, Integer statusId) {
		return driveCategoryRepository.existsByDriveCategoryNameAndStatusId(name, statusId);
	}
	public Boolean existsByDriveCategoryDescriptionAndStatusId(String description, Integer statusId) {
		return driveCategoryRepository.existsByDescriptionAndStatusId(description, statusId);
	}
	// DRIVE CATEGORY
	
	// DRIVE CATEGORY ASS
	public List<DriveCategoryAsso> findAllDriveCategoryAsso() {
		return driveCategoryAssoRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}	

	public @Valid boolean saveDriveCategoryAssoData(@Valid DriveRequest driveRequest) {
		DriveCategoryAsso driveCategory = driveMapper.prepareDriveCategoryAssoModel(driveRequest);
		driveCategory = driveCategoryAssoRepository.save(driveCategory);
		return true;
	}	

	public String updateDriveCategoryAssoData(@Valid DriveRequest request) {
		Optional<DriveCategoryAsso> driveCategory = driveCategoryAssoRepository.findById(request.getId());
		if(driveCategory.isPresent()) {
			DriveCategoryAsso driveCategoryUpdate = driveMapper.prepareDriveCategoryAssoUpdataData(driveCategory.get(), request);
			driveCategoryUpdate = driveCategoryAssoRepository.save(driveCategoryUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Category Id";
		}
	}
	public String deleteDriveCategoryAsso(Long id) {
		Optional<DriveCategoryAsso> driveCategoryOptional = driveCategoryAssoRepository.findById(id);
		if (driveCategoryOptional.isPresent()) {
			DriveCategoryAsso driveCategoryToUpdate = driveCategoryOptional.get();
			driveCategoryToUpdate.setId(id);
			driveCategoryToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveCategoryAssoRepository.save(driveCategoryToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Category Id";
		}
	}
	
	public Optional<DriveCategoryAsso> findDriveCategoryAssoById(Long id) {
		return driveCategoryAssoRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}
	public Boolean existsByDriveIdAndDriveCategoryId(Drives driveId, DriveCategory driveCategoryId) {
		return driveCategoryAssoRepository.existsByDriveIdAndDriveCategoryId(driveId, driveCategoryId);
	}
	// DRIVE CATEGORY ASS
	public List<DriveCheckList> findAllCheckLists() {
		return checklistRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}
	
	public List<MeasureOrActivityList> findAllMeasureOrActivityList() {
		return measureOrActivityListRepository.findAll();
	}
	
	public @Valid boolean saveCheckListData(@Valid DriveRequest driveRequest) {
		DriveCheckList driveCheckList = driveMapper.prepareCheckListModel(driveRequest);
		driveCheckList = checklistRepository.save(driveCheckList);
		return true;
	}	

	public String updateCheckListData(@Valid DriveRequest request) {
		Optional<DriveCheckList> driveCheckList = checklistRepository.findById(request.getId());
		if(driveCheckList.isPresent()) {
			DriveCheckList driveCheckListUpdate = driveMapper.prepareCheckListUpdataData(driveCheckList.get(), request);
			driveCheckListUpdate = checklistRepository.save(driveCheckListUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid CheckList Id";
		}
	}
	public String deleteCheckList(Long id) {
		Optional<DriveCheckList> driveCheckListOptional = checklistRepository.findById(id);
		if (driveCheckListOptional.isPresent()) {
			DriveCheckList driveCheckListToUpdate = driveCheckListOptional.get();
			driveCheckListToUpdate.setId(id);
			driveCheckListToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			checklistRepository.save(driveCheckListToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid CheckList Id";
		}
	}
	
	public Optional<DriveCheckList> findCheckListById(Long id) {
		return checklistRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}
	
	public Boolean existsByDriveIdAndActivityId(Drives driveId, MeasureOrActivityList measureOrActivityList) {
		return checklistRepository.existsByDriveIdAndActivityId(driveId, measureOrActivityList);
	}
	public Boolean existsByDriveIdAndActivityPositionId(Drives driveId, String activityPositionId) {
		return checklistRepository.existsByDriveIdAndActivityPositionId(driveId, activityPositionId);
	}
	
	public List<DriveTarget> findAllDriveTargets() {
		
		
		return driveTargetRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}
	
	public void saveDriveTargetData(@Valid DriveRequest driveTargetRequest) {
		DriveTarget driveTarget = driveMapper.prepareDriveTargetModel(driveTargetRequest);
		driveTarget = driveTargetRepository.save(driveTarget);
	}

	public String updateDriveTargetData(@Valid DriveRequest request) {
		Optional<DriveTarget> driveTarget = driveTargetRepository.findById(request.getId());
		if(driveTarget.isPresent()) {
			DriveTarget driveTargetUpdate = driveMapper.prepareDriveUpdataData(driveTarget.get(), request);
			driveTargetUpdate = driveTargetRepository.save(driveTargetUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Target Id";
		}
	}

	public String deleteDriveTarget(Long id) {
		Optional<DriveTarget> driveTargetOptional = driveTargetRepository.findById(id);
		if (driveTargetOptional.isPresent()) {
			DriveTarget driveToUpdate = driveTargetOptional.get();
			driveToUpdate.setId(id);
			driveToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveTargetRepository.save(driveToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Target Id";
		}
	}

	public Optional<DriveTarget> findDriveTargetById(Long id) {
		return driveTargetRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}
	/*public Boolean findByUnitNameAndUnitType(String unitType, String unitName) {
		return driveTargetRepository.findByUnitNameAndUnitType(unitType, unitName);
	}*/
	public Optional<DriveTarget> findByUnitTypeAndUnitName(String unitType, String unitName) {
		// TODO Auto-generated method stub
		return driveTargetRepository.findByUnitTypeAndUnitName(unitType, unitName);
	}
	// DRIVE DAILY PROGRESS
	public List<DriveDailyProgress> findAllDriveDailyProgress() {
		return driveProgressRecordRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}
	
	public void saveDriveDailyProgressData(@Valid DriveRequest request) {
		DriveDailyProgress driveDailyProgress = driveMapper.prepareDriveDailyProgressModel(request);
		driveDailyProgress = driveProgressRecordRepository.save(driveDailyProgress);
	}

	public String updateDriveDailyProgressData(@Valid DriveRequest request) {
		Optional<DriveDailyProgress> driveDailyProgress = driveProgressRecordRepository.findById(request.getId());
		if(driveDailyProgress.isPresent()) {
			DriveDailyProgress driveDailyProgressUpdate = driveMapper.prepareDriveUpdataData(driveDailyProgress.get(), request);
			driveDailyProgressUpdate = driveProgressRecordRepository.save(driveDailyProgressUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Daily Progress Id";
		}
	}

	public String deleteDriveDailyProgress(Long id) {
		Optional<DriveDailyProgress> driveDailyProgressOptional = driveProgressRecordRepository.findById(id);
		if (driveDailyProgressOptional.isPresent()) {
			DriveDailyProgress driveDailyProgressToUpdate = driveDailyProgressOptional.get();
			driveDailyProgressToUpdate.setId(id);
			driveDailyProgressToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveProgressRecordRepository.save(driveDailyProgressToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Daily Progress Id";
		}
	}

	public Optional<DriveDailyProgress> findDriveDailyProgressById(Long id) {
		return driveProgressRecordRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}

	// DRIVE DAILY PROGRESS
	
	 
	// DRIVE FAILURE ANALYSIS
	public List<FailureAnalysis> findAllFailureAnalysis() {
		return driveFailureAnalysisRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}

	public void saveFailureAnalysisData(@Valid DriveRequest failureAnalysisRequest) { 
		FailureAnalysis failureAnalysis = driveMapper.prepareFailureAnalysissModel(failureAnalysisRequest);
		failureAnalysis = driveFailureAnalysisRepository.save(failureAnalysis);
	}

	public String updateFailureAnalysisData(@Valid DriveRequest request) {
		Optional<FailureAnalysis> failureAnalysis = driveFailureAnalysisRepository.findById(request.getId());
		if(failureAnalysis.isPresent()) {
			FailureAnalysis failureAnalysisUpdate = driveMapper.prepareFailureAnalysisUpdataData(failureAnalysis.get(), request);
			failureAnalysisUpdate = driveFailureAnalysisRepository.save(failureAnalysisUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid ElectrificationTargets Id";
		}
	}

	public String deleteFailureAnalysis(Long id) {
		Optional<FailureAnalysis> failureAnalysisOptional = driveFailureAnalysisRepository.findById(id);
		if (failureAnalysisOptional.isPresent()) {
			FailureAnalysis failureAnalysisToUpdate = failureAnalysisOptional.get();
			failureAnalysisToUpdate.setId(id);
			failureAnalysisToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveFailureAnalysisRepository.save(failureAnalysisToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Failure Analysis Id";
		}
	}

	public Optional<FailureAnalysis> findFailureAnalysisById(Long id) {
		return driveFailureAnalysisRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}
	// DRIVE FAILURE ANALYSIS
	

	public List<ElectrificationTargets> findAllElectrificationTargets() {
		return driveElectrificationTargetsRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}
	
	public @Valid boolean saveElectrificationTargetsData(@Valid DriveRequest electrificationTargetsRequest) {
		ElectrificationTargets electrificationTargets = driveMapper.prepareElectrificationTargetsModel(electrificationTargetsRequest);
		electrificationTargets = driveElectrificationTargetsRepository.save(electrificationTargets);
		return true;
	}	

	public String updateElectrificationTargetsData(@Valid DriveRequest request) {
		Optional<ElectrificationTargets> electrificationTargets = driveElectrificationTargetsRepository.findById(request.getId());
		if(electrificationTargets.isPresent()) {
			ElectrificationTargets electrificationTargetsUpdate = driveMapper.prepareElectrificationTargetsUpdataData(electrificationTargets.get(), request);
			electrificationTargetsUpdate = driveElectrificationTargetsRepository.save(electrificationTargetsUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid ElectrificationTargets Id";
		}
	}
	public String deleteElectrificationTargets(Long id) {
		Optional<ElectrificationTargets> electrificationTargetsOptional = driveElectrificationTargetsRepository.findById(id);
		if (electrificationTargetsOptional.isPresent()) {
			ElectrificationTargets driveToUpdate = electrificationTargetsOptional.get();
			driveToUpdate.setId(id);
			driveToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveElectrificationTargetsRepository.save(driveToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid ElectrificationTargets Id";
		}
	}
	
	public Optional<ElectrificationTargets> findElectrificationTargetsById(Long id) {
		return driveElectrificationTargetsRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}

	public List<Stipulations> findAllStipulations() {
		return driveStipulationRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}
	

	public List<Product> findAllProduct() {		
		return productRepository.findAll();
	}
	
	public @Valid boolean saveStipulationsData(@Valid DriveRequest stipulationsRequest, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		
		liContentManagements = commonMapper.prepareContentManagementList(file, stipulationPath, Constants.STIPULATION,
				"","","stipulations","Stipulations","","","","","", Integer.parseInt(stipulationsRequest.getCreatedBy()));
		
		if(liContentManagements != null && !liContentManagements.isEmpty()) {
			repository.saveAll(liContentManagements);
			logger.info("Files Details saved in to Database Successfully.");
			Stipulations stipulations = driveMapper.prepareStipulationsModel(stipulationsRequest, file, liContentManagements.get(0).getCommonFileId());
			stipulations = driveStipulationRepository.save(stipulations);
		}else {
			Stipulations stipulations = driveMapper.prepareStipulationsModel(stipulationsRequest, file, new Long(0));
			stipulations = driveStipulationRepository.save(stipulations);
		}
		
		return true;
	}	

	public String updateStipulationsData(@Valid DriveRequest request, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();
		liContentManagements = commonMapper.prepareForUpdateContentManagementList(file, stipulationPath, Constants.STIPULATION,
				"","","","Stipulations","","","","","", Integer.parseInt(request.getUpdatedBy()), request.getAttachment());
		
		Long commonFileId = (long) 0.0;
		if(liContentManagements!=null && !liContentManagements.isEmpty()) {
			repository.saveAll(liContentManagements);
			logger.info("Files Details saved in to Database Successfully.");
			commonFileId = liContentManagements.get(0).getCommonFileId();
		}
		else {
			commonFileId = request.getAttachment() != null ? Long.parseLong(request.getAttachment()) : (long) 0.0;
			logger.info("find file = "+commonFileId);
		}
		
		logger.info("find the existing inspection by id : "+commonFileId);
		Optional<Stipulations> stipulations = driveStipulationRepository.findById(request.getId());
		if(stipulations.isPresent()) {
			Stipulations stipulationsUpdate = driveMapper.prepareStipulationsUpdataData(stipulations.get(), request, file, commonFileId);
			stipulationsUpdate = driveStipulationRepository.save(stipulationsUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Stipulations Id";
		}
	}
	public String deleteStipulations(Long id) {
		Optional<Stipulations> stipulationsOptional = driveStipulationRepository.findById(id);
		if (stipulationsOptional.isPresent()) {
			Stipulations stipulationsUpdate = stipulationsOptional.get();
			stipulationsUpdate.setId(id);
			stipulationsUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveStipulationRepository.save(stipulationsUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Stipulations Id";
		}
	}
	
	public Optional<Stipulations> findStipulationsById(Long id) {
		return driveStipulationRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}

	public List<CrsEigInspections> findAllInspections() {
		return driveInspectionRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}

	public @Valid boolean saveInspectionsData(@Valid DriveRequest request, List<MultipartFile> file) {
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
		
		liContentManagements = commonMapper.prepareContentManagementList(file, inspectionPath, Constants.INSPECTION,
				"","","inspections","Inspections","","","","","", Integer.parseInt(request.getCreatedBy()));
				
		if(liContentManagements != null && !liContentManagements.isEmpty()) {
			repository.saveAll(liContentManagements);
			logger.info("Files Details saved in to Database Successfully.");
			CrsEigInspections inspections = driveMapper.prepareInspectionsModel(request, file, liContentManagements.get(0).getCommonFileId());
			inspections = driveInspectionRepository.save(inspections);
		}else {
			CrsEigInspections inspections = driveMapper.prepareInspectionsModel(request, file, new Long(0));
			inspections = driveInspectionRepository.save(inspections);
		}
		
		return true;
	}	

	public String updateInspectionsData(@Valid DriveRequest request, List<MultipartFile> file) {
		
		List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();
		liContentManagements = commonMapper.prepareForUpdateContentManagementList(file, inspectionPath, Constants.INSPECTION,
				"","","","Inspections","","","","","", Integer.parseInt(request.getUpdatedBy()), request.getAttachment());
		
		Long commonFileId = (long) 0.0;
		
		if(liContentManagements != null && !liContentManagements.isEmpty()) {
			repository.saveAll(liContentManagements);
			logger.info("Files Details saved in to Database Successfully.");
			commonFileId = liContentManagements.get(0).getCommonFileId();
		}
		else {
			commonFileId = request.getAttachment() != null ? Long.parseLong(request.getAttachment()) : (long) 0.0;
			logger.info("find file = "+commonFileId);
		}
		
		logger.info("find the existing inspection by id : "+commonFileId);
		Optional<CrsEigInspections> inspections = driveInspectionRepository.findById(request.getId());
		if(inspections.isPresent()) {
			CrsEigInspections inspectionsUpdate = driveMapper.prepareInspectionsUpdataData(inspections.get(), request, file, commonFileId);
			inspectionsUpdate = driveInspectionRepository.save(inspectionsUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Inspections Id";
		}
		
	}
	public String deleteInspections(Long id) {
		Optional<CrsEigInspections> inspectionsOptional = driveInspectionRepository.findById(id);
		if (inspectionsOptional.isPresent()) {
			CrsEigInspections inspectionsUpdate = inspectionsOptional.get();
			inspectionsUpdate.setId(id);
			inspectionsUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
			driveInspectionRepository.save(inspectionsUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Inspections Id";
		}
	}

	public Optional<CrsEigInspections> findInspectionsById(Long id) {
		return driveInspectionRepository.findByIdAndStatusId(id, Constants.ACTIVE_STATUS_ID);
	}

	public void saveInspectionWithDoc(CrsEigInspections inspectionsUpdate) {
		driveInspectionRepository.save(inspectionsUpdate);
	}

	public void saveStipulationWithDoc(Stipulations stipulationsUpdate) {
		driveStipulationRepository.save(stipulationsUpdate);
	}

	public List<Division> findAllOrderByCodeAsc() {
		return divisionRepository.findAllOrderByCodeAsc();
	}
	

	public List<InspectionType> findAllOrderByInspectionTypeAsc() {
		return inspectionTypeRepository.findAllOrderByInspectionTypeAsc();
	}

	public List<ContentManagement> findInspectionsContentById(Long commonFileId) {
		return repository.findByCommonFileIdAndStatusId(commonFileId, Constants.ACTIVE_STATUS_ID);
	}

	public Optional<ContentManagement> findInspectionsContentByIdAndCommon(Long commonFileId, Long Id) {
		return repository.findByIdAndCommonFileId(Id, commonFileId);
	}

	public void updatefileStatus(ContentManagement contentUpdate) {
		repository.save(contentUpdate);
	}

	public Optional<Drives> findDrivesById(Long driveId) {
		// TODO Auto-generated method stub
		return driveRepository.findById(driveId);
	}

	

	public Optional<DriveCategory> findDrivesCategoryById(Long driveCategoryId) {
		// TODO Auto-generated method stub
		return driveCategoryRepository.findById(driveCategoryId);
	}

	public List<Drives> getDrivesBasedOnFromDateAndDepotType(Date fromDate, String depotType) {
		// TODO Auto-generated method stub
		return driveRepository.findByFromDateAndDepotType(fromDate,depotType);
	}

	public List<DrivesResponse> getDrivesBasedOnFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNull(
			Date fromDate,Date toDate, String depotType, String requestType, String driveCategoryName ) {
		logger.info("** depot type***"+depotType);
		List<Drives> drivesList = null;
		List<DrivesResponse> drivesResponseList = new ArrayList<>();
		Optional<FunctionalLocationTypes> functionalLocationType = functionalLocationTypesRepository.findByCode(depotType);
		Optional<DriveCategory> driveCategory = driveCategoryRepository.findByDriveCategoryName(driveCategoryName);
		if ("Schedule Progress".equals(requestType)) {
			if (driveCategory.isPresent()) {
				List<DriveCategoryAsso> driveCategoryAssos = driveCategoryAssoRepository.findByDriveCategoryId(driveCategory.get());
				if (driveCategoryAssos.size() > 0 ) {
					List<Long> drives =  new ArrayList<>();
					for (DriveCategoryAsso driveCategoryAsso : driveCategoryAssos) {
						drives.add(driveCategoryAsso.getDriveId().getId());
					}
					drivesList = driveRepository.findByDepotTypeAndIdInAndFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNull(functionalLocationType.get(),drives,fromDate,toDate);
					logger.info("*** drives list size***"+drivesList.size());
				}
			}
		}else {
			if (driveCategory.isPresent()) {
				List<DriveCategoryAsso> driveCategoryAssos = driveCategoryAssoRepository.findByDriveCategoryId(driveCategory.get());
				List<Long> drives =  new ArrayList<>();
				for (DriveCategoryAsso driveCategoryAsso : driveCategoryAssos) {
					drives.add(driveCategoryAsso.getDriveId().getId());
				}
				drivesList = driveRepository.findByDepotTypeAndFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNullAndIdNotIn(functionalLocationType.get(),fromDate,toDate,drives);
			} else 
			drivesList = driveRepository.findByDepotTypeAndFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNull(functionalLocationType.get(),fromDate,toDate);
			logger.info("** in else condition size ***"+drivesList.size());
		}
		if (drivesList != null) {
			for (Drives drives : drivesList) {
				DrivesResponse driveResponse = new DrivesResponse();
				Optional<DriveDailyProgress> DDProgress = this.findByDriveIdAndPerformedDate(drives,fromDate);
				if (DDProgress.isPresent()) {
					driveResponse.setPerformedCount(DDProgress.get().getPerformedCount());
					driveResponse.setFacilityId(DDProgress.get().getDepot());
				}else
					driveResponse.setPerformedCount(0);
				List<DriveDailyProgress> alreadyDDProgressList = this.findByDriveIdAndPerformedDateLessThan(drives,fromDate);
				double alreadyDoneCount = 0;
				for (DriveDailyProgress driveDailyProgress : alreadyDDProgressList) {
					alreadyDoneCount = alreadyDoneCount+driveDailyProgress.getPerformedCount();
				}
				driveResponse.setAlreadyDone(alreadyDoneCount);
				driveResponse.setName(drives.getName());
				driveResponse.setId(drives.getId());
				driveResponse.setActive(drives.getActive());
				driveResponse.setAssetDescription(drives.getAssetDescription());
				driveResponse.setAssetType(drives.getAssetType());
				driveResponse.setChecklist(drives.getChecklist());
				driveResponse.setCreatedBy(drives.getCreatedBy());
				driveResponse.setCreatedOn(drives.getCreatedOn());
				driveResponse.setCriteria(drives.getCriteria());
				driveResponse.setDepotType(drives.getDepotType());
				driveResponse.setDescription(drives.getDescription());
				driveResponse.setFromDate(drives.getFromDate());
				driveResponse.setToDate(drives.getToDate());
				driveResponse.setFunctionalUnit(drives.getFunctionalUnit());
				driveResponse.setIsIdRequired(drives.getIsIdRequired());
				driveResponse.setStatusId(drives.getStatusId());
				driveResponse.setTarget_qty(drives.getTarget_qty());
				driveResponse.setUpdatedBy(drives.getUpdatedBy());
				driveResponse.setUpdatedOn(drives.getUpdatedOn());
				driveResponse.setFrequency(drives.getFrequency());
				drivesResponseList.add(driveResponse);
			}
		}
		
		return drivesResponseList;
	}

	public DriveDailyProgress saveDriveDailyProgressRecord(@Valid DriveRequest driveDailyProgressRequest) {
		DriveDailyProgress DDProgressRecord = null;
		DriveDailyProgress driveDailyProgress = driveMapper.prepareDriveDailyProgressModel(driveDailyProgressRequest);
		Optional<DriveDailyProgress> existsDriveDailyProgress = driveProgressRecordRepository.findByDriveIdAndPerformedDate(driveDailyProgress.getDriveId(),driveDailyProgress.getPerformedDate());
		if (existsDriveDailyProgress.isPresent()) {
			DriveDailyProgress ddProgress = existsDriveDailyProgress.get();
			ddProgress.setPerformedCount(driveDailyProgress.getPerformedCount());
			DDProgressRecord = driveProgressRecordRepository.save(ddProgress);
		} else {
			DDProgressRecord = driveProgressRecordRepository.save(driveDailyProgress);
		}
		return DDProgressRecord;
	}

	public Optional<DriveDailyProgress> findByDriveIdAndPerformedDate(Drives drives, Date fromDate) {
		// TODO Auto-generated method stub
		return driveProgressRecordRepository.findByDriveIdAndPerformedDate(drives,fromDate);
	}

	public void saveDriveProgressId(List<String> assetIds, Long driveProgressId, String createdBy, Timestamp createdOn) {
		// TODO Auto-generated method stub
		Optional<DriveDailyProgress> existsDriveDailyProgress = driveProgressRecordRepository.findById(driveProgressId);
		
		for (String assetId : assetIds) {
			DriveProgressId drProgressId = driveMapper.prepareDriveProgressIdModel(assetId,existsDriveDailyProgress.get(),createdBy,createdOn);
			driveProgressIdRepository.save(drProgressId);
		}
		
	}

	public List<DriveProgressId> findByDriveDailyProgressId(Long driveDailyProgressId) {
		Optional<DriveDailyProgress> existsDriveDailyProgress = driveProgressRecordRepository.findById(driveDailyProgressId);
		return driveProgressIdRepository.findByDriveDailyProgressId(existsDriveDailyProgress.get());
	}

	public void deleteDriveProgressId(Long id) {
		driveProgressIdRepository.deleteById(id);
	}

	public List<DriveDailyProgress> findByDriveIdAndPerformedDateLessThan(Drives drives, Date fromDate) {
		return driveProgressRecordRepository.findByDriveIdAndPerformedDateLessThan(drives,fromDate);
	}

	public Optional<DriveCategoryAsso> findByDriveIdAndDriveCategoryId(Drives driveId, DriveCategory driveCategoryId) {
		// TODO Auto-generated method stub
		return driveCategoryAssoRepository.findByDriveIdAndDriveCategoryId(driveId,driveCategoryId);
	}
	
	 public List<Drives> getDrives() {
		    List<Drives> driveList= driveRepository.getDrives();	
		       return driveList;
		    
		  }
	 
	public List<DriveCategoryAsso> findByDriveCategoryId(DriveCategory driveCategory) {
		return driveCategoryAssoRepository.findByDriveCategoryId(driveCategory);
	}

	public void saveDrives(CopyDrivesRequest copyDrivesRequest) {
		List<Drives> drives = copyDrivesRequest.getDrives();
		DriveCategory driveCategory = driveCategoryRepository.save(copyDrivesRequest.getDriveCategory());
		for (Drives drive : drives) {
			drive = driveRepository.save(drive);
			DriveCategoryAsso DCAsso = new DriveCategoryAsso();
			DCAsso.setDriveCategoryId(driveCategory);
			DCAsso.setDriveId(drive);
			DCAsso.setCreatedBy(drive.getCreatedBy());
			DCAsso.setActive("Yes");
			DCAsso.setStatusId(Constants.ACTIVE_STATUS_ID);
			driveCategoryAssoRepository.save(DCAsso);
		}

	}

	public Optional<DriveCheckList> findByDriveIdAndActivityId(Drives drives,
			MeasureOrActivityList measureOrActivityList) {
		// TODO Auto-generated method stub
		return checklistRepository.findByDriveIdAndActivityId(drives,measureOrActivityList);
	}

	public Optional<DriveCheckList> findByDriveIdAndActivityPositionId(Drives drives, String activityPositionId) {
		// TODO Auto-generated method stub
		return checklistRepository.findByDriveIdAndActivityPositionId(drives,activityPositionId);
	}
	
	public List<DriveDailyProgress> findByDriveId(Drives drive) {
		return driveProgressRecordRepository.findByDriveId(drive);
	}

	public Optional<DriveProgressId> findByDriveProgressId(Long id) {
		return driveProgressIdRepository.findById(id);
	}

	public Optional<DriveDailyProgress> findById(Long id) {
		return driveProgressRecordRepository.findById(id);
	}

	public void saveDriveDailyProgress(DriveDailyProgress driveDailyProgress) {
		driveProgressRecordRepository.save(driveDailyProgress);
	}

/*	public List<DriveTarget> findDriveTargets() {
		
		List<DriveTargetResponse> driveTargetResponse = new ArrayList<>();		
		List<Drives> distinctDrives  =driveTargetRepository.findDistinctByDriveId();
		//List<Facility> unitName =driveTargetRepository.findByUnitName(distinctDrives);
		//List<Facility> division =facilityRepository.findByDivision(unitName);
		
		for (Drives drive : distinctDrives) {
			DriveTargetResponse DTargetResponse = new DriveTargetResponse();
		
					List<DriveTarget> driveTargets = driveTargetRepository.findByDriveId(drive);					
					Double driveTargetSum = 0D;
					for (DriveTarget driveTarget : driveTargets) {
						driveTargetSum = driveTargetSum+driveTarget.getTarget();
					}
					
					DTargetResponse.setCreatedBy(drive);
					DTarget.setTarget(driveTargetSum);
					DTarget.setDriveId(drive);
					driveTargetResponse.add(DTarget);
		}
		for (Facility facility : division) {
			
			DriveTarget DTargets = new DriveTarget();
		
					List<DriveTarget> driveTargt = driveTargetRepository.findByUnitName(facility);					
					Double driveTargetSum = 0D;
					for (DriveTarget driveTarget : driveTargt) {
						driveTargetSum = driveTargetSum+driveTarget.getTarget();
						}					
					DTargets.setTarget(driveTargetSum);
					//DTargets.setUnitName(facility);
					target.add(DTargets);
		}
		
		
	
		return target;
	}*/


	
public void saveTargets(List<DriveTarget> driveTarget) {	
	

	for (DriveTarget driveTarget2 : driveTarget) {
		
		Optional<DriveTarget> dtData = driveTargetRepository.findByDriveIdAndUnitName(driveTarget2.getDriveId(), driveTarget2.getUnitName());
		
		if (dtData.isPresent()) {
			
			DriveTarget saveDriveTarget = dtData.get();
			
			saveDriveTarget.setDriveId(driveTarget2.getDriveId());
			saveDriveTarget.setUnitName(driveTarget2.getUnitName());
			saveDriveTarget.setUnitType(driveTarget2.getUnitType());
			saveDriveTarget.setPoulation(driveTarget2.getPoulation());
			saveDriveTarget.setTarget(driveTarget2.getTarget());
			saveDriveTarget.setUpdatedBy(driveTarget2.getCreatedBy());
			saveDriveTarget.setUpdatedOn(driveTarget2.getCreatedOn());
			
			driveTargetRepository.save(saveDriveTarget);
		} else {
			driveTargetRepository.save(driveTarget2);
		}
		
		
	}
			
	 
	
}
public List<DriveCategoryAsso> findByDriveCategoryIdAndStatusId(DriveCategory driveCategory, int activeStatusId) {
	return driveCategoryAssoRepository.findByDriveCategoryIdAndStatusId(driveCategory,activeStatusId);
}

public Optional<DriveCategory> findByDriveCategoryName(String driveCategoryName) {
	return driveCategoryRepository.findByDriveCategoryName(driveCategoryName);
}

public List<Drives> getByCategoryId(DriveCategory driveCategory) {
	// TODO Auto-generated method stub
	logger.info("service calling");
	List<DriveCategoryAsso> driveCatAssocList= driveCategoryAssoRepository.findByDriveCategoryIdAndStatusId(driveCategory,Constants.ACTIVE_STATUS_ID);
	List<Drives> drivesList = new ArrayList<>();	
	Date date= new Date();
	logger.info("drives from assoc==="+driveCatAssocList.size());
	for (DriveCategoryAsso driveCategoryAsso : driveCatAssocList) {		
		
		Optional<Drives> drive = driveRepository.findByIdAndToDateGreaterThanEqualOrToDateIsNull(driveCategoryAsso.getDriveId().getId(),date);
		logger.info("drives from validations==="+drive);
		/*	for (Drives drives : drive) {
		logger.info("drives=="+drives.toString());
		Optional<DriveCategoryAsso> validDrives = driveCategoryAssoRepository.getByDriveIdAndDriveCategoryIdAndStatusId(drives,driveCategory,Constants.ACTIVE_STATUS_ID);
		
		if(validDrives.isPresent())
		{
		drivesList.add(drives);
		}
	}*/
		
		if (drive.isPresent()) {
			drivesList.add(drive.get());	
		}
	} 
       return drivesList;
}

public List<DriveTargetResponse> getByDriveCategoryIdAndDriveId(DriveCategory driveCategoryId, Drives driveId,String zone) {
	logger.info("in service before drive cat assoc");
	//List<DriveCategoryAsso> driveCatAssocList= driveCategoryAssoRepository.findByDriveCategoryId(driveCategoryId);
	Optional<DriveCategoryAsso> driveCatAssoc = driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategoryId,driveId,Constants.ACTIVE_STATUS_ID);
	
	Optional<Facility> fac = facilityRepository.findByFacilityName(zone);
	logger.info("before response");
	
	List<DriveTargetResponse> driveTargetResponse = new ArrayList<>();	
	if(driveCatAssoc.isPresent()) {
		DriveTargetResponse dtr = new DriveTargetResponse();		
		Optional<Drives> drive =driveRepository.findByIdAndStatusId(driveCatAssoc.get().getDriveId().getId(),Constants.ACTIVE_STATUS_ID);
		logger.info("driveid=="+drive);
		logger.info("fac=="+zone);
		DriveTarget driveTarget = driveTargetRepository.findByDriveIdAndUnitName(drive,fac.get().getFacilityName());
		logger.info("drive reponsee=="+driveTarget);
		double target = 0;
		String population = "0"; 
		logger.info("before objects=");
		if (driveTarget != null) {
			logger.info("before agg if");
			if(fac.get().getDepotType().equals("zone")) {
				logger.info("in if agg");
			Double driveDivAggregation =	driveTargetRepository.getDivAggregation(drive,zone);
			Double driveSubAggregation =	driveTargetRepository.getSubDivAggregation(drive,zone);
			Double driveDepotAggregation =	driveTargetRepository.getDepotDivAggregation(drive,zone);
			
			dtr.setDivisionAggregation(driveDivAggregation);
			dtr.setSubDivisionAggregation(driveSubAggregation);
			dtr.setDepotAggregation(driveDepotAggregation);
			
			}
			else if(fac.get().getDepotType().equals("DIV")) {
				
				logger.info("in else if agg");
				Double subDivAggregationAtDivlevel = driveTargetRepository.getSubDivsnAggregation(drive,fac.get().getFacilityName());
				Double depotAggregationAtDivlevel = driveTargetRepository.getDepAggregation(drive,fac.get().getFacilityName());
				if(subDivAggregationAtDivlevel != null) {
				dtr.setSubDivisionAggregation(subDivAggregationAtDivlevel);
				}if(depotAggregationAtDivlevel != null) {
				dtr.setDepotAggregation(depotAggregationAtDivlevel);
				}
				
			}
			else if (fac.get().getDepotType().equals("SUB_DIV")) {
				
				logger.info("in 2nd else if agg");
				Double depotAggregationAtSubDivlevel = driveTargetRepository.getDepotsAggregation(drive,fac.get().getFacilityName());
				if(depotAggregationAtSubDivlevel != null) {
				dtr.setDepotAggregation(depotAggregationAtSubDivlevel);
				}
			}
			logger.info("close agg");
			dtr.setTarget(driveTarget.getTarget());
			dtr.setPoulation(driveTarget.getPoulation());
			
			
		}
	
		else {
			logger.info("in else condition");
			
			dtr.setPoulation(population);
			dtr.setTarget(target);
			dtr.setDivisionAggregation(0);
			dtr.setSubDivisionAggregation(0);
			dtr.setDepotAggregation(0);
		}
		
		dtr.setDriveId(driveId);
		dtr.setAssetType(drive.get().getAssetType());		
		dtr.setUnitType(fac.get().getDepotType());
		dtr.setUnitName(fac.get().getFacilityName());
		logger.info("after objects=="+dtr.toString());
	
	driveTargetResponse.add(dtr);
	}
	
       return driveTargetResponse;
}

public List<DriveTargetResponse> getByDriveId(List<Drives> drives, String zone) {	
	
	Optional<Facility> fac = facilityRepository.findByFacilityName(zone);
	List<DriveTargetResponse> driveTargetResponse = new ArrayList<>();	
	
	for (Drives drives2 : drives) {		
	
		logger.info("in for loop");	
		logger.info("drives in for loop"+drives.size());
		DriveTargetResponse dtr = new DriveTargetResponse();			
			
			double target = 0;
			String population = "0"; 
			
			logger.info("in drive if condition");
			DriveTarget driveTarget = driveTargetRepository.getByDriveIdAndUnitName(drives2,zone);
			logger.info("In drive target driveId"+driveTarget);
			
			logger.info("before objects=");
			if (driveTarget != null) {
				logger.info("in if drive present=="+drives2);
				
				Double driveDivAggregation =	driveTargetRepository.getDivAggregation(drives2,zone);
				Double driveSubAggregation =	driveTargetRepository.getSubDivAggregation(drives2,zone);
				Double driveDepotAggregation =	driveTargetRepository.getDepotDivAggregation(drives2,zone);
				
				dtr.setDriveId(driveTarget.getDriveId());
				
				dtr.setTarget(driveTarget.getTarget());
				dtr.setPoulation(driveTarget.getPoulation());
				dtr.setDivisionAggregation(driveDivAggregation);
				dtr.setSubDivisionAggregation(driveSubAggregation);
				dtr.setDepotAggregation(driveDepotAggregation);
				logger.info("population in targets=="+driveTarget.getPoulation());
				
			}		
			else {
				dtr.setDriveId(drives2);
				
				dtr.setPoulation(population);
				dtr.setTarget(target);
				dtr.setDivisionAggregation(0);
				dtr.setSubDivisionAggregation(0);
				dtr.setDepotAggregation(0);
			}
			
			dtr.setAssetType(drives2.getAssetType());
			dtr.setUnitType(fac.get().getDepotType());
			dtr.setUnitName(zone);
			logger.info("after objects=="+dtr.toString());
			driveTargetResponse.add(dtr);
			}
		
		
	
		
	return driveTargetResponse;



}

public List<DriveTargetResponse> getByDriveId(List<Drives> drives, Optional<Facility> fac) {
	
	
	logger.info("facility in service=="+fac.get().getFacilityName());
List<DriveTargetResponse> driveTargetResponse = new ArrayList<>();	
	
	for (Drives drives2 : drives) {		
	
		logger.info("in for loop");	
		logger.info("drives in for loop"+drives.size());
		DriveTargetResponse dtr = new DriveTargetResponse();			
			
			Double target = 0D;
			String population = "0"; 
			double targetAggregation = 0;
			logger.info("in drive if condition");
			DriveTarget driveTarget = driveTargetRepository.getByDriveIdAndUnitName(drives2,fac.get().getFacilityName());
			logger.info("In drive target driveId"+driveTarget);
			/*List<Facility> facilityList = facilityRepository.findBySubDivision(fac.get().getSubDivision());
			logger.info("*** facility list ***"+facilityList.size());
			for (Facility facility : facilityList) {
				logger.info("*** in loop ****");
				logger.info("** facility object***"+facility.toString());
				DriveTarget Dtarget = driveTargetRepository.getByDriveIdAndUnitName(drives2,facility.getFacilityName());
				logger.info("** target object***"+Dtarget.toString());
				targetAggregation = targetAggregation + Dtarget.getTarget();
				logger.info("*** target aggregation***"+targetAggregation);
			}*/
			
			logger.info("before objects=");
			if (driveTarget != null) {
				logger.info("in if drive present=="+drives2);
				if(fac.get().getDepotType().equals("DIV")) {
					logger.info("for aggregation in if condition");
					Double subDivAggregationAtDivlevel = driveTargetRepository.getSubDiviAggregation(drives2,fac.get().getFacilityName());
					Double depotAggregationAtDivlevel = driveTargetRepository.getDepotAggregation(drives2,fac.get().getFacilityName());
					if(subDivAggregationAtDivlevel != null ) {
					dtr.setSubDivisionAggregation(subDivAggregationAtDivlevel);
					}
					if(depotAggregationAtDivlevel != null) {
					dtr.setDepotAggregation(depotAggregationAtDivlevel);
					}
				}else if(fac.get().getDepotType().equals("SUB_DIV")) {
					
					Double depotAggregationAtSubDivlevel = driveTargetRepository.getDeptAggregation(drives2,fac.get().getFacilityName());
					if(depotAggregationAtSubDivlevel != null) {
						
						dtr.setDepotAggregation(depotAggregationAtSubDivlevel);
					}
					
				}
				
				dtr.setDriveId(driveTarget.getDriveId());						
				dtr.setTarget(driveTarget.getTarget());
				dtr.setPoulation(driveTarget.getPoulation());
				
				logger.info("population in targtes=="+driveTarget.getPoulation());
				
				
			}		
			else {
				dtr.setDriveId(drives2);				
				dtr.setPoulation(population);
				dtr.setTarget(target);
			}
			dtr.setAssetType(drives2.getAssetType());
			dtr.setUnitType(fac.get().getDepotType());
			dtr.setUnitName(fac.get().getFacilityName());	
			logger.info("after objects=="+dtr.toString());
			driveTargetResponse.add(dtr);
			}
		
		
	
		
	return driveTargetResponse;

	
}

public ResponseStatus storeUploadedFiles(List<MultipartFile> multipartFile, String contentCategory, String description,
		String divisionCode, String createdBy, String zonal, String fU, String contentTopic, Long driveId) {
	ResponseStatus responseStatus = new ResponseStatus();
	try {
		ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(drivePath, contentCategory );
		if(folderResponse.getCode() == Constants.SUCCESS_CODE) {				
			List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
			ContentManagement fileId = repository.findTopByOrderByCommonFileIdDesc();
			Long commonFileId = (long) 0.0; 
			if(fileId == null || fileId.getCommonFileId() == null) {
				commonFileId = (long) 1;
			}else {
				commonFileId = fileId.getCommonFileId()+1;
			}
			Optional<Drives> drive =driveRepository.findById(driveId);
			if (drive.isPresent()) {
				Drives driveDetails = drive.get();
				if (driveDetails.getContentLink() != null) {
					commonFileId = Long.parseLong(driveDetails.getContentLink());
				} else {
					driveDetails.setContentLink(String.valueOf(commonFileId));
				}
				
				driveRepository.save(driveDetails);
			}
			
			for(MultipartFile mf: multipartFile)
			{
				String folderPath = folderResponse.getMessage();
				liContentManagements.add(contentManagementMapper.saveAndStoreDetails(mf, divisionCode, createdBy, zonal, fU, contentTopic, description, contentCategory, folderPath, commonFileId));									
			}
			if(!liContentManagements.isEmpty()) {
				liContentManagements = repository.saveAll(liContentManagements);
									logger.info("Files Details saved in to Database Successfully.");
			}
		}					
		responseStatus.setCode(Constants.SUCCESS_CODE);
		responseStatus.setMessage(Constants.JOB_SUCCESS_MESSAGE);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Error while saving files "+e.getMessage());
		responseStatus.setCode(Constants.FAILURE_CODE);
		responseStatus.setMessage("ERROR >>> "+e.getMessage());
	}
	return responseStatus;
}

public List<FailureAnalysis> findByDiv(List<String> fac) {
	
	return driveFailureAnalysisRepository.findByDivInAndStatusId(fac,Constants.ACTIVE_STATUS_ID);
}

public ResponseStatus storeUploadedFile(List<MultipartFile> file, String contentCategory, String description,
		String divisionCode, String createdBy, String zonal, String fU, String contentTopic, Long failureAnalysisId) {
	ResponseStatus responseStatus = new ResponseStatus();
	try {
		ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(failureAnalysisPath, contentCategory );
		if(folderResponse.getCode() == Constants.SUCCESS_CODE) {				
			List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
			ContentManagement fileId = repository.findTopByOrderByCommonFileIdDesc();
			Long commonFileId = (long) 0.0; 
			if(fileId == null || fileId.getCommonFileId() == null) {
				commonFileId = (long) 1;
			}else {
				commonFileId = fileId.getCommonFileId()+1;
			}
			Optional<FailureAnalysis> failureId =driveFailureAnalysisRepository.findById(failureAnalysisId);
			if (failureId.isPresent()) {
				FailureAnalysis failureDetails = failureId.get();
				if (failureDetails.getContentLink() != null) {
					commonFileId = Long.parseLong(failureDetails.getContentLink());
				} else {
					failureDetails.setContentLink(String.valueOf(commonFileId));
				}
				
				driveFailureAnalysisRepository.save(failureDetails);
			}
			
			for(MultipartFile mf: file)
			{
				String folderPath = folderResponse.getMessage();
				liContentManagements.add(contentManagementMapper.saveAndStoreDetails(mf, divisionCode, createdBy, zonal, fU, contentTopic, description, contentCategory, folderPath, commonFileId));									
			}
			if(!liContentManagements.isEmpty()) {
				liContentManagements = repository.saveAll(liContentManagements);
									logger.info("Files Details saved in to Database Successfully.");
			}
		}					
		responseStatus.setCode(Constants.SUCCESS_CODE);
		responseStatus.setMessage(Constants.JOB_SUCCESS_MESSAGE);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Error while saving files "+e.getMessage());
		responseStatus.setCode(Constants.FAILURE_CODE);
		responseStatus.setMessage("ERROR >>> "+e.getMessage());
	}
	return responseStatus;
}

public List<Drives> findByFunctionalUnit(List<String> fac) {
	
	return driveRepository.findByFunctionalUnitInOrFunctionalUnitIsNullAndStatusId(fac,Constants.ACTIVE_STATUS_ID);
}







}




	 
		
		
		
	

