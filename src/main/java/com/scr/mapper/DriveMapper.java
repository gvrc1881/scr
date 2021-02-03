package com.scr.mapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.scr.message.request.DriveRequest;
import com.scr.model.CrsEigInspections;
import com.scr.model.DriveCategory;
import com.scr.model.DriveCategoryAsso;
import com.scr.model.DriveCheckList;
import com.scr.model.DriveDailyProgress;
import com.scr.model.DriveProgressId;
import com.scr.model.DriveTarget;
import com.scr.model.Drives;
import com.scr.model.ElectrificationTargets;
import com.scr.model.Facility;
import com.scr.model.FailureAnalysis;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Stipulations;
import com.scr.model.FunctionalLocationTypes;
import com.scr.repository.DriveCategoryRepository;
import com.scr.repository.DrivesRepository;
import com.scr.repository.FacilityRepository;
import com.scr.repository.MeasureOrActivityListRepository;
import com.scr.repository.FunctionLocationTypesRepository;
import com.scr.util.Constants;
import com.scr.util.Helper;


@Component
public class DriveMapper {
	static Logger logger = LogManager.getLogger(DriveMapper.class);
	
	@Value("${stipulation.path}")
	private String stipulationPath;
	
	@Value("${inspection.path}")
	private String inspectionPath;
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	@Autowired
	private MeasureOrActivityListRepository measureOrActivityListRepository;
	
	@Autowired
	private DrivesRepository drivesRepository;
	
	@Autowired
	private DriveCategoryRepository driveCategoryRepository;
	
	@Autowired
	private FunctionLocationTypesRepository functionLocationTypesRepository;
	
	public Drives prepareDriveModel(@Valid DriveRequest driveRequest) throws Exception {
		Drives drive = null;
		logger.info("Preparing the drive model object");
		try {
		if (driveRequest != null) {
			drive = new Drives();

			drive.setName(driveRequest.getName());
			drive.setDescription(driveRequest.getDescription());
			drive.setFromDate(driveRequest.getFromDate());
			drive.setToDate(driveRequest.getToDate());
			
			if (driveRequest.getDepotType() != null && !driveRequest.getDepotType().isEmpty()) {
				//Optional<Facility> facility = facilityRepository.findByFacilityName(driveRequest.getFunctionalUnit());
				Optional<FunctionalLocationTypes> functional = functionLocationTypesRepository.findByCode(driveRequest.getDepotType());
				drive.setDepotType(functional.get());
			}

			drive.setAssetType(driveRequest.getAssetType());
			drive.setFrequency(driveRequest.getFrequency());
			drive.setAssetDescription(driveRequest.getAssetDescription());
			drive.setCriteria(driveRequest.getCriteria());
			drive.setTarget_qty(driveRequest.getTarget_qty());
			drive.setIsIdRequired(driveRequest.getIsIdRequired());
			drive.setFunctionalUnit(driveRequest.getFunctionalUnit());
			drive.setChecklist(driveRequest.getChecklist());
			drive.setActive(driveRequest.getActive());

			drive.setCreatedBy(driveRequest.getCreatedBy());
			drive.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
			drive.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		logger.info("Prepared model object = "+drive);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return drive;
	}

	public Drives prepareDriveUpdataData(Drives drive, @Valid DriveRequest driveRequest) {

		if (driveRequest != null) {

			logger.info(driveRequest);

			drive.setName(driveRequest.getName());
			drive.setDescription(driveRequest.getDescription());
			drive.setFromDate(driveRequest.getFromDate());
			drive.setToDate(driveRequest.getToDate());
logger.info("depot=="+driveRequest.getDepotType());
			if (driveRequest.getDepotType() != null && !driveRequest.getDepotType().isEmpty()) {
				//Optional<Facility> facility = facilityRepository.findByFacilityName(driveRequest.getFunctionalUnit());
				Optional<FunctionalLocationTypes> functional = functionLocationTypesRepository.findByCode(driveRequest.getDepotType());
				drive.setDepotType(functional.get());
			}

			drive.setAssetType(driveRequest.getAssetType());
			drive.setFrequency(driveRequest.getFrequency());
			drive.setAssetDescription(driveRequest.getAssetDescription());
			drive.setCriteria(driveRequest.getCriteria());
			drive.setTarget_qty(driveRequest.getTarget_qty());
			drive.setIsIdRequired(driveRequest.getIsIdRequired());
			drive.setFunctionalUnit(driveRequest.getFunctionalUnit());
			drive.setChecklist(driveRequest.getChecklist());
			drive.setActive(driveRequest.getActive());
			drive.setCreatedBy(driveRequest.getCreatedBy());
			drive.setUpdatedBy(driveRequest.getUpdatedBy());
			drive.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		}
		return drive;
	}
	
	// DRIVE CATEGORY
	public DriveCategory prepareDriveCategoryModel(@Valid DriveRequest driveRequest) {
		DriveCategory driveCategory = null;
		if(driveRequest != null) {
			driveCategory = new DriveCategory();
			
			driveCategory.setDriveCategoryName(driveRequest.getName());
			driveCategory.setDescription(driveRequest.getDescription());
			driveCategory.setFromDate(driveRequest.getFromDate());
			driveCategory.setToDate(driveRequest.getToDate());
			driveCategory.setAuthority(driveRequest.getAuthority());
			
			driveCategory.setCreatedBy(driveRequest.getCreatedBy());
			driveCategory.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			driveCategory.setStatusId(Constants.ACTIVE_STATUS_ID);
			
		}
		return driveCategory;
	}

	public DriveCategory prepareDriveCategoryUpdataData(DriveCategory driveCategory, @Valid DriveRequest driveRequest) {
		
		if (driveRequest != null) {
			driveCategory.setDriveCategoryName(driveRequest.getName());
			driveCategory.setDescription(driveRequest.getDescription());
			driveCategory.setFromDate(driveRequest.getFromDate());
			driveCategory.setToDate(driveRequest.getToDate());
			driveCategory.setAuthority(driveRequest.getAuthority());
			driveCategory.setCreatedBy(driveRequest.getCreatedBy());
			driveCategory.setUpdatedBy(driveRequest.getUpdatedBy());
			driveCategory.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
			driveCategory.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveCategory;
	}	
	// DRIVE CATEGORY
	
	//DRIVE CATEGORY ASS
	public DriveCategoryAsso prepareDriveCategoryAssoModel(@Valid DriveRequest driveRequest) {
		DriveCategoryAsso driveCategory = null;
		if(driveRequest != null) {
			driveCategory = new DriveCategoryAsso();
			
			driveCategory.setActive(driveRequest.getActive());
			Optional<DriveCategory> drOptional = driveCategoryRepository.findByIdAndStatusId(Long.parseLong(driveRequest.getDriveCategoryId()), Constants.ACTIVE_STATUS_ID);
			if(drOptional.isPresent()) {
				driveCategory.setDriveCategoryId(drOptional.get());
			}
			
			Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(driveRequest.getDriveId()), Constants.ACTIVE_STATUS_ID);
			if(drive.isPresent()) {
				driveCategory.setDriveId(drive.get());
			}
			
			driveCategory.setCreatedBy(driveRequest.getCreatedBy());
			driveCategory.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			driveCategory.setStatusId(Constants.ACTIVE_STATUS_ID);
			
		}
		return driveCategory;
	}

	public DriveCategoryAsso prepareDriveCategoryAssoUpdataData(DriveCategoryAsso driveCategory, @Valid DriveRequest driveRequest) {
		
		if (driveRequest != null) {
			driveCategory.setActive(driveRequest.getActive());
			Optional<DriveCategory> drOptional = driveCategoryRepository.findByIdAndStatusId(Long.parseLong(driveRequest.getDriveCategoryId()), Constants.ACTIVE_STATUS_ID);
			if(drOptional.isPresent()) {
				driveCategory.setDriveCategoryId(drOptional.get());
			}
			
			Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(driveRequest.getDriveId()), Constants.ACTIVE_STATUS_ID);
			if(drive.isPresent()) {
				driveCategory.setDriveId(drive.get());
			}
			driveCategory.setCreatedBy(driveRequest.getCreatedBy());
			driveCategory.setUpdatedBy(driveRequest.getUpdatedBy());
			driveCategory.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			driveCategory.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveCategory;
	}	
	// DRIVE CATEGORY ASS
	
	public DriveCheckList prepareCheckListModel(@Valid DriveRequest request) {
		DriveCheckList driveCheckList = null;
		if(request != null) {
			driveCheckList = new DriveCheckList();
			driveCheckList.setActivityPositionId(request.getActivityPositionId());
			driveCheckList.setDisplayOrder(request.getDisplayOrder());
			driveCheckList.setActive(request.getActive());
			driveCheckList.setLowerLimit(request.getLowerLimit());
			driveCheckList.setUpperLimit(request.getUpperLimit());
			if (request.getActivityId() != null && !request.getActivityId().isEmpty()) {
				Optional<MeasureOrActivityList> measure = measureOrActivityListRepository
						.findByActivityId(request.getActivityId());
				if (measure.isPresent()) {
					driveCheckList.setActivityId(measure.get());
				}
			}
			
			if (request.getDriveId() != null && !request.getDriveId().isEmpty()) {
				Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(request.getDriveId()),
						Constants.ACTIVE_STATUS_ID);
				if (drive.isPresent()) {
					driveCheckList.setDriveId(drive.get());
				}
			}
			driveCheckList.setCreatedBy(request.getCreatedBy());
			 driveCheckList.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			 driveCheckList.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveCheckList;
	}

	public DriveCheckList prepareCheckListUpdataData(DriveCheckList driveCheckList, @Valid DriveRequest request) {
		if(request != null) {
			driveCheckList.setActivityPositionId(request.getActivityPositionId());
			driveCheckList.setDisplayOrder(request.getDisplayOrder());
			driveCheckList.setActive(request.getActive());
			driveCheckList.setLowerLimit(request.getLowerLimit());
			driveCheckList.setUpperLimit(request.getUpperLimit());
			driveCheckList.setReportColumnHeader(request.getReportColumnHeader());
			
			if (request.getActivityId() != null && !request.getActivityId().isEmpty()) {
				Optional<MeasureOrActivityList> measure = measureOrActivityListRepository
						.findByActivityId(request.getActivityId());
				if (measure.isPresent()) {
					driveCheckList.setActivityId(measure.get());
				}
			}
			
			if (request.getDriveId() != null && !request.getDriveId().isEmpty()) {
				Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(request.getDriveId()),
						Constants.ACTIVE_STATUS_ID);
				if (drive.isPresent()) {
					driveCheckList.setDriveId(drive.get());
				}
			}
			
			 driveCheckList.setUpdatedBy(request.getUpdatedBy());
			 driveCheckList.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			 driveCheckList.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveCheckList;
	}
	
	
	public DriveTarget prepareDriveTargetModel(@Valid DriveRequest request) {
		DriveTarget driveTarget = null;
		if(request != null) {
			driveTarget = new DriveTarget();
			
			driveTarget.setUnitType(request.getUnitType());
			driveTarget.setUnitName(request.getUnitName());
			driveTarget.setTarget(request.getTarget());
			driveTarget.setPoulation(request.getPoulation());
			if (request.getDriveId() != null && !request.getDriveId().isEmpty()) {
				Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(request.getDriveId()),
						Constants.ACTIVE_STATUS_ID);
				if (drive.isPresent()) {
					driveTarget.setDriveId(drive.get());
				}
			}
			driveTarget.setCreatedBy(request.getCreatedBy());
			driveTarget.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			driveTarget.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveTarget;
	}

	public DriveTarget prepareDriveUpdataData(DriveTarget driveTarget, @Valid DriveRequest request) {
		if(request != null) {
			
			driveTarget.setUnitType(request.getUnitType());
			driveTarget.setUnitName(request.getUnitName());
			driveTarget.setTarget(request.getTarget());
			driveTarget.setPoulation(request.getPoulation());
			if (request.getDriveId() != null && !request.getDriveId().isEmpty()) {
				Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(request.getDriveId()),
						Constants.ACTIVE_STATUS_ID);
				if (drive.isPresent()) {
					driveTarget.setDriveId(drive.get());
				}
			}
			
			driveTarget.setUpdatedBy(request.getUpdatedBy());
			driveTarget.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			driveTarget.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveTarget;
	}
	
	public DriveDailyProgress prepareDriveDailyProgressModel(@Valid DriveRequest request) {
		DriveDailyProgress driveDailyProgress = null;
		if(request != null) {
			driveDailyProgress = new DriveDailyProgress();
			if(request.getActivityId() != null) {
				driveDailyProgress.setActivityId(Long.parseLong(request.getActivityId()));
			}
			driveDailyProgress.setPerformedDate(request.getPerformedDate());
			driveDailyProgress.setDivision(request.getDivision());
			driveDailyProgress.setDepot(request.getDepot());
			driveDailyProgress.setSection(request.getSection());
			driveDailyProgress.setPerformedCount(request.getPerformedCount());
			driveDailyProgress.setSupervisor(request.getSupervisor());
			if (request.getDriveId() != null && !request.getDriveId().isEmpty()) {
				Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(request.getDriveId()),
						Constants.ACTIVE_STATUS_ID);
				if (drive.isPresent()) {
					driveDailyProgress.setDriveId(drive.get());
				}
			}
			
			driveDailyProgress.setCreatedBy(request.getCreatedBy());
			driveDailyProgress.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			driveDailyProgress.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveDailyProgress;
	}

	public DriveDailyProgress prepareDriveUpdataData(DriveDailyProgress driveDailyProgress,
			@Valid DriveRequest request) {
		if(request != null) {
			driveDailyProgress.setActivityId(Long.parseLong(request.getActivityId()));
			driveDailyProgress.setPerformedDate(request.getPerformedDate());
			driveDailyProgress.setDivision(request.getDivision());
			driveDailyProgress.setDepot(request.getDepot());
			driveDailyProgress.setSection(request.getSection());
			driveDailyProgress.setPerformedCount(request.getPerformedCount());
			driveDailyProgress.setSupervisor(request.getSupervisor());
			if (request.getDriveId() != null && !request.getDriveId().isEmpty()) {
				Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(request.getDriveId()),
						Constants.ACTIVE_STATUS_ID);
				if (drive.isPresent()) {
					driveDailyProgress.setDriveId(drive.get());
				}
			}
			
			driveDailyProgress.setUpdatedBy(request.getUpdatedBy());
			driveDailyProgress.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			driveDailyProgress.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveDailyProgress;
	}
	
	public FailureAnalysis prepareFailureAnalysissModel(@Valid DriveRequest request) {
		FailureAnalysis failureAnalysis = null;
		if(request != null) {
			failureAnalysis = new FailureAnalysis();
			
			failureAnalysis.setReported(request.getReported());
			failureAnalysis.setReportDescription(request.getReportDescription());
			failureAnalysis.setRepurcussion(request.getRepurcussion());
			failureAnalysis.setDate(request.getDate());
			failureAnalysis.setSection(request.getSection());
			failureAnalysis.setDiv(request.getDiv());
			failureAnalysis.setAssetType(request.getAssetType());
			failureAnalysis.setAssetId(request.getAssetId());
			failureAnalysis.setSubAssetId(request.getSubAssetId());
			failureAnalysis.setSubAssetType(request.getSubAssetType());
			failureAnalysis.setMake(request.getMake());
			failureAnalysis.setModel(request.getModel());
			failureAnalysis.setRootCause(request.getRootCause());
			failureAnalysis.setActionPlan(request.getActionPlan());
			failureAnalysis.setActionStatus(request.getActionStatus());
			failureAnalysis.setApprovedBy(request.getApprovedBy());
			failureAnalysis.setActionTargetDate(request.getActionTargetDate());
			failureAnalysis.setActionCompletedDate(request.getActionCompletedDate());
			failureAnalysis.setActionDescription(request.getActionDescription());
			failureAnalysis.setAvoidable(request.getAvoidable());
			failureAnalysis.setRemarkBrief(request.getRemarkBrief());
			failureAnalysis.setRemarkDetails(request.getRemarkDetails());
			
			failureAnalysis.setCreatedBy(request.getCreatedBy());
			failureAnalysis.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			failureAnalysis.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return failureAnalysis;
	}

	public FailureAnalysis prepareFailureAnalysisUpdataData(FailureAnalysis failureAnalysis,
			@Valid DriveRequest request) {
		if(request != null) {
			failureAnalysis.setReported(request.getReported());
			failureAnalysis.setReportDescription(request.getReportDescription());
			failureAnalysis.setRepurcussion(request.getRepurcussion());
			failureAnalysis.setDate(request.getDate());
			failureAnalysis.setSection(request.getSection());
			failureAnalysis.setDiv(request.getDiv());
			failureAnalysis.setAssetType(request.getAssetType());
			failureAnalysis.setAssetId(request.getAssetId());
			failureAnalysis.setSubAssetId(request.getSubAssetId());
			failureAnalysis.setSubAssetType(request.getSubAssetType());
			failureAnalysis.setMake(request.getMake());
			failureAnalysis.setModel(request.getModel());
			failureAnalysis.setRootCause(request.getRootCause());
			failureAnalysis.setActionPlan(request.getActionPlan());
			failureAnalysis.setActionStatus(request.getActionStatus());
			failureAnalysis.setApprovedBy(request.getApprovedBy());
			failureAnalysis.setActionTargetDate(request.getActionTargetDate());
			failureAnalysis.setActionCompletedDate(request.getActionCompletedDate());
			failureAnalysis.setActionDescription(request.getActionDescription());
			failureAnalysis.setAvoidable(request.getAvoidable());
			failureAnalysis.setRemarkBrief(request.getRemarkBrief());
			failureAnalysis.setRemarkDetails(request.getRemarkDetails());
			
			failureAnalysis.setUpdatedBy(request.getUpdatedBy());
			failureAnalysis.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			failureAnalysis.setStatusId(Constants.ACTIVE_STATUS_ID);	
		}
		return failureAnalysis;
	}
	
	
	public ElectrificationTargets prepareElectrificationTargetsModel(
			@Valid DriveRequest request) {
		ElectrificationTargets electrificationTargets = null;
		if(request != null) {
			electrificationTargets = new ElectrificationTargets();
			
			logger.info("ElectrificationTargets Request = "+request.toString());
			
			electrificationTargets.setSection(request.getSection());
			electrificationTargets.setGuage(request.getGuage());
			electrificationTargets.setTargetDate(request.getTargetDate());
			electrificationTargets.setStatus(request.getStatus());
		    electrificationTargets.setDivision(request.getDivision());
		    electrificationTargets.setExecutionAgency(request.getExecutionAgency());
		    electrificationTargets.setTKM(request.getTKM());
		    electrificationTargets.setRKM(request.getRKM());
		    electrificationTargets.setCrsInspection(request.getCrsInspection());
		    electrificationTargets.setCrsAuthorisation(request.getCrsAuthorisation());
		    electrificationTargets.setTargetSetBy(request.getTargetSetBy());
		    electrificationTargets.setDoublingTrippling(request.getDoublingTrippling());
		    electrificationTargets.setState(request.getState());
		    electrificationTargets.setPhase(request.getPhase());
		    electrificationTargets.setProposalScheme(request.getProposalScheme());
		    electrificationTargets.setSanctionByBoard(request.getSanctionByBoard());
		    electrificationTargets.setYearOfSanction(request.getYearOfSanction());
		    electrificationTargets.setDateOfCompletion(request.getDateOfCompletion());
		    
		    electrificationTargets.setCreatedBy(request.getCreatedBy());
		    electrificationTargets.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    
		    electrificationTargets.setStatusId(Constants.ACTIVE_STATUS_ID);
		    
		}
		return electrificationTargets;
	}

	public ElectrificationTargets prepareElectrificationTargetsUpdataData(ElectrificationTargets electrificationTargets,
			@Valid DriveRequest request) {
		if(request != null) {
			electrificationTargets.setSection(request.getSection());
			electrificationTargets.setGuage(request.getGuage());
			electrificationTargets.setTargetDate(request.getTargetDate());
			electrificationTargets.setStatus(request.getStatus());
		    electrificationTargets.setDivision(request.getDivision());
		    electrificationTargets.setExecutionAgency(request.getExecutionAgency());
		    electrificationTargets.setTKM(request.getTKM());
		    electrificationTargets.setRKM(request.getRKM());
		    electrificationTargets.setCrsInspection(request.getCrsInspection());
		    electrificationTargets.setCrsAuthorisation(request.getCrsAuthorisation());
		    electrificationTargets.setTargetSetBy(request.getTargetSetBy());
		    electrificationTargets.setDoublingTrippling(request.getDoublingTrippling());
		    electrificationTargets.setState(request.getState());
		    electrificationTargets.setPhase(request.getPhase());
		    electrificationTargets.setProposalScheme(request.getProposalScheme());
		    electrificationTargets.setSanctionByBoard(request.getSanctionByBoard());
		    electrificationTargets.setYearOfSanction(request.getYearOfSanction());
		    electrificationTargets.setDateOfCompletion(request.getDateOfCompletion());
		    
		    electrificationTargets.setUpdatedBy(request.getUpdatedBy());
		    electrificationTargets.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
		    electrificationTargets.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return electrificationTargets;
	}

	public Stipulations prepareStipulationsModel(@Valid DriveRequest request, List<MultipartFile> file, Long commonFileId) {
		Stipulations stipulations = null;
		if(request != null) {
			stipulations = new Stipulations();
		//	String fileList = storeDriveFiles(file, stipulationPath, Constants.STIPULATION);
			stipulations.setStipulation(request.getStipulation());
			stipulations.setInspectionId(request.getInspectionId());
		    stipulations.setDateOfStipulation(request.getDateOfStipulation());
		    stipulations.setDateComplied(request.getDateComplied());
		    stipulations.setCompliance(request.getCompliance());
		    stipulations.setAttachment(String.valueOf(commonFileId));
		    stipulations.setCompliedBy(request.getCompliedBy());
		    
		    stipulations.setCredatedBy(request.getCreatedBy());
		    stipulations.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    
		    stipulations.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return stipulations;
	}

	public Stipulations prepareStipulationsUpdataData(Stipulations stipulations, @Valid DriveRequest request, List<MultipartFile> file, Long commonFileId) {
		if(request != null) {
			//String fileList = storeDriveFiles(file, stipulationPath, Constants.STIPULATION);
			stipulations.setStipulation(request.getStipulation());
			stipulations.setInspectionId(request.getInspectionId());
		    stipulations.setDateOfStipulation(request.getDateOfStipulation());
		    stipulations.setDateComplied(request.getDateComplied());
		    stipulations.setCompliance(request.getCompliance());
		    stipulations.setAttachment(String.valueOf(commonFileId));
		    stipulations.setCompliedBy(request.getCompliedBy());
		    
		    stipulations.setUpdatedBy(request.getUpdatedBy());
		    stipulations.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
		    stipulations.setStatusId(Constants.ACTIVE_STATUS_ID);	
		}
		return stipulations;
	}

	public CrsEigInspections prepareInspectionsModel(@Valid DriveRequest request, List<MultipartFile> file, Long commonFileId) {
		CrsEigInspections inspections = null;
		if(request != null) {
			
			//String fileList = storeDriveFiles(file, inspectionPath, Constants.INSPECTION);
			
			inspections = new CrsEigInspections();
			
			inspections.setInspectionType(request.getInspectionType());
			inspections.setSection(request.getSection());
		    inspections.setSectionStartLocation(request.getSectionStartLocation());
		    inspections.setSectionEndLocation(request.getSectionEndLocation());
		    inspections.setDateOfInspection(request.getDateOfInspection());
		    inspections.setTKM(request.getTKM());
		    inspections.setRKM(request.getRKM());
		    inspections.setRemarks(request.getRemarks());
		    inspections.setAuthorisationDate(request.getAuthorisationDate());
		    inspections.setChargingDate(request.getChargingDate());
		    inspections.setAttachment(String.valueOf(commonFileId));
		    inspections.setStation(request.getStation());
		    
		    inspections.setCredatedBy(request.getCreatedBy());
		    inspections.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    
		    inspections.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return inspections;
	}

	private String storeDriveFiles(List<MultipartFile> file, String path, String folder) {
		File saveFiles = new File(path + folder);
		StringBuffer sb = new StringBuffer();
		if (file != null) {
			boolean flag = true;
			if (saveFiles.exists()) {
				flag = true;
				logger.info("Folder structure already created.");
			} else {
				flag = true;
				saveFiles.mkdirs();
				logger.info("Folder created successfully.");
			}
			if (flag) {
				for (MultipartFile mf : file) {
					logger.info("original filename: " + mf.getOriginalFilename());
					String fileName = mf.getOriginalFilename().split(Constants.SPLIT_DOT)[0] + Constants.UNDER_SCORE
							+ Helper.currentTimeStampWithString().split(" ")[0] + Constants.UNDER_SCORE
							+ Helper.currentTimeStampWithString().split(" ")[1].replace(":", "-") + Constants.DOT
							+ mf.getOriginalFilename().split(Constants.SPLIT_DOT)[1];
					logger.info("changed filename = " + fileName);
					try {
						Path rootLocation = Paths.get(saveFiles.getAbsolutePath());
						Files.copy(mf.getInputStream(), rootLocation.resolve(fileName));
					} catch (Exception e) {
						logger.error("ERROR >>> while storing " + folder + " file > " + fileName + " in "
								+ saveFiles.getAbsolutePath());
					}
					sb.append(fileName + ",");
				}
			}
		}
		return sb.toString();
	}

	public CrsEigInspections prepareInspectionsUpdataData(CrsEigInspections inspections,@Valid DriveRequest request, List<MultipartFile> file, Long commonFileId) {
		if(request != null) {
			inspections.setInspectionType(request.getInspectionType());
			inspections.setSection(request.getSection());
		    inspections.setSectionStartLocation(request.getSectionStartLocation());
		    inspections.setSectionEndLocation(request.getSectionEndLocation());
		    inspections.setDateOfInspection(request.getDateOfInspection());
		    inspections.setTKM(request.getTKM());
		    inspections.setRKM(request.getRKM());
		    inspections.setRemarks(request.getRemarks());
		    inspections.setAuthorisationDate(request.getAuthorisationDate());
		    inspections.setChargingDate(request.getChargingDate());
		    inspections.setAttachment(String.valueOf(commonFileId));
		    inspections.setStation(request.getStation());

		    inspections.setUpdatedBy(request.getUpdatedBy());
		    inspections.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
		    inspections.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return inspections;
	}

	public DriveProgressId prepareDriveProgressIdModel(String assetId, DriveDailyProgress driveDailyProgress,
			String createdBy, Timestamp createdOn) {
		DriveProgressId driveProgressId = new DriveProgressId();
		driveProgressId.setAssetId(assetId);
		driveProgressId.setDriveDailyProgressId(driveDailyProgress);
		driveProgressId.setCreatedBy(createdBy);
		driveProgressId.setCreatedOn(createdOn);
		return driveProgressId;
	}
	
}
