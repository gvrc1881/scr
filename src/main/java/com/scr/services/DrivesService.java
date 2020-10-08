package com.scr.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scr.mapper.CommonMapper;
import com.scr.mapper.DriveMapper;
import com.scr.message.request.DriveRequest;
import com.scr.model.ContentManagement;
import com.scr.model.CrsEigInspections;
import com.scr.model.Division;
import com.scr.model.DriveCategory;
import com.scr.model.DriveCategoryAsso;
import com.scr.model.DriveCheckList;
import com.scr.model.DriveDailyProgress;
import com.scr.model.DriveTarget;
import com.scr.model.Drives;
import com.scr.model.ElectrificationTargets;
import com.scr.model.FailureAnalysis;
import com.scr.model.InspectionType;
import com.scr.model.Make;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Product;
import com.scr.model.Stipulations;
import com.scr.repository.ChecklistRepository;
import com.scr.repository.ContentManagementRepository;
import com.scr.repository.DivisionRepository;
import com.scr.repository.DriveCategoryAssoRepository;
import com.scr.repository.DriveCategoryRepository;
import com.scr.repository.DriveElectrificationTargetsRepository;
import com.scr.repository.DriveFailureAnalysisRepository;
import com.scr.repository.DriveInspectionRepository;
import com.scr.repository.DriveProgressRecordRepository;
import com.scr.repository.DriveStipulationRepository;
import com.scr.repository.DriveTargetRepository;
import com.scr.repository.DrivesRepository;
import com.scr.repository.InspectionTypeRepository;
import com.scr.repository.MeasureOrActivityListRepository;
import com.scr.repository.ProductRepository;
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
	
	public List<Drives> findAllDrives() {
		logger.info("Fetcing drives data where active is 1.");
		return driveRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
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
		return driveCategoryRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
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
				"","","","Stipulations","","","","","", Integer.parseInt(stipulationsRequest.getCreatedBy()));
		
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
				"","","","Inspections","","","","","", Integer.parseInt(request.getCreatedBy()));
				
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

	public List<Drives> getDrivesBasedOnFromDateGreaterThanEqualAndToDateGreaterThanEqualOrToDateIsNull(
			Date fromDate,Date toDate ) {
		// TODO Auto-generated method stub
		return driveRepository.findByFromDateGreaterThanEqualAndToDateGreaterThanEqualOrToDateIsNull(fromDate,toDate);
	}

	public void saveDriveDailyProgressRecord(@Valid DriveRequest driveDailyProgressRequest) {
		DriveDailyProgress driveDailyProgress = driveMapper.prepareDriveDailyProgressModel(driveDailyProgressRequest);
		Optional<DriveDailyProgress> existsDriveDailyProgress = driveProgressRecordRepository.findByDriveId(driveDailyProgress.getDriveId());
		if (existsDriveDailyProgress.isPresent()) {
			DriveDailyProgress ddProgress = existsDriveDailyProgress.get();
			ddProgress.setPerformedCount(driveDailyProgress.getPerformedCount());
			driveProgressRecordRepository.save(ddProgress);
		} else {
			driveProgressRecordRepository.save(driveDailyProgress);
		}
		
	}

	

	

}
