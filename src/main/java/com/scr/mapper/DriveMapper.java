package com.scr.mapper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.controller.DrivesController;
import com.scr.message.request.DriveRequest;
import com.scr.model.CrsEigInspections;
import com.scr.model.DriveCheckList;
import com.scr.model.Drives;
import com.scr.model.ElectrificationTargets;
import com.scr.model.Facility;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Product;
import com.scr.model.Stipulations;
import com.scr.repository.DriveStipulationRepository;
import com.scr.repository.DrivesRepository;
import com.scr.repository.FacilityRepository;
import com.scr.repository.MeasureOrActivityListRepository;
import com.scr.repository.ProductRepository;
import com.scr.util.Constants;


@Component
public class DriveMapper {
	static Logger logger = LogManager.getLogger(DrivesController.class);

	@Autowired
	private FacilityRepository facilityRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private DriveStipulationRepository stipulationRepository;

	@Autowired
	private MeasureOrActivityListRepository measureOrActivityListRepository;
	
	@Autowired
	private DrivesRepository drivesRepository;
	
	public Drives prepareDriveModel(@Valid DriveRequest driveRequest) {
		Drives drive = null;
		if (driveRequest != null) {

			logger.info(driveRequest);

			drive = new Drives();

			drive.setName(driveRequest.getName());
			drive.setDescription(driveRequest.getDescription());
			drive.setFromDate(driveRequest.getFromDate());
			drive.setToDate(driveRequest.getToDate());

			Optional<Facility> facility = facilityRepository.findById(Long.parseLong(driveRequest.getDepotType()));
			drive.setDepotType(facility.get());

			drive.setAssetType(driveRequest.getAssetType());
			drive.setAssetDescription(driveRequest.getAssetDescription());
			drive.setCriteria(driveRequest.getCriteria());
			drive.setTarget_qty(driveRequest.getTarget_qty());
			drive.setIsIdRequired(driveRequest.getIsIdRequired());
			drive.setFunctionalUnit(driveRequest.getFunctionalUnit());
			drive.setChecklist(driveRequest.getChecklist());
			drive.setActive(driveRequest.getActive());

			drive.setCreatedBy(driveRequest.getCreatedBy());
			drive.setUpdatedBy(driveRequest.getUpdatedBy());
			drive.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			drive.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
			drive.setStatusId(Constants.ACTIVE_STATUS_ID);
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

			Optional<Facility> facility = facilityRepository.findById(Long.parseLong(driveRequest.getDepotType()));
			drive.setDepotType(facility.get());

			drive.setAssetType(driveRequest.getAssetType());
			drive.setAssetDescription(driveRequest.getAssetDescription());
			drive.setCriteria(driveRequest.getCriteria());
			drive.setTarget_qty(driveRequest.getTarget_qty());
			drive.setIsIdRequired(driveRequest.getIsIdRequired());
			drive.setFunctionalUnit(driveRequest.getFunctionalUnit());
			drive.setChecklist(driveRequest.getChecklist());
			drive.setActive(driveRequest.getActive());

			drive.setCreatedBy(driveRequest.getCreatedBy());
			drive.setUpdatedBy(driveRequest.getUpdatedBy());
			drive.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			drive.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		}
		return drive;
	}
	public DriveCheckList prepareCheckListModel(@Valid DriveRequest request) {
		DriveCheckList driveCheckList = null;
		if(request != null) {
			driveCheckList = new DriveCheckList();
			
			driveCheckList.setActivityPositionId(request.getActivityPositionId());
			driveCheckList.setDisplayOrder(request.getDisplayOrder());
			driveCheckList.setActive(request.getActive());
			driveCheckList.setLowerLimit(request.getLowerLimit());
			driveCheckList.setUpperLimit(request.getUpperLimit());
			driveCheckList.setReportColumnHeader(request.getReportColumnHeader());
			
			Optional<MeasureOrActivityList> measure = measureOrActivityListRepository.findById(Long.parseLong(request.getActivityId()));
			if(measure.isPresent()) {
				driveCheckList.setActivityId(measure.get());
			}
			
			Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(request.getDriveId()), Constants.ACTIVE_STATUS_ID);
			if(drive.isPresent()) {
				driveCheckList.setDriveId(drive.get());
			}
			
			driveCheckList.setCreatedBy(request.getCreatedBy());
			 driveCheckList.setUpdatedBy(request.getUpdatedBy());
			 driveCheckList.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			 driveCheckList.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
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
			
			Optional<MeasureOrActivityList> measure = measureOrActivityListRepository.findById(Long.parseLong(request.getActivityId()));
			if(measure.isPresent()) {
				driveCheckList.setActivityId(measure.get());
			}
			
			Optional<Drives> drive = drivesRepository.findByIdAndStatusId(Long.parseLong(request.getDriveId()), Constants.ACTIVE_STATUS_ID);
			if(drive.isPresent()) {
				driveCheckList.setDriveId(drive.get());
			}
			
			driveCheckList.setCreatedBy(request.getCreatedBy());
			 driveCheckList.setUpdatedBy(request.getUpdatedBy());
			 driveCheckList.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			 driveCheckList.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				
			 driveCheckList.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return driveCheckList;
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
		    electrificationTargets.setUpdatedBy(request.getUpdatedBy());
		    electrificationTargets.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    electrificationTargets.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
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
		    
		    electrificationTargets.setCreatedBy(request.getCreatedBy());
		    electrificationTargets.setUpdatedBy(request.getUpdatedBy());
		    electrificationTargets.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    electrificationTargets.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
		    electrificationTargets.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return electrificationTargets;
	}

	public Stipulations prepareStipulationsModel(@Valid DriveRequest request) {
		Stipulations stipulations = null;
		if(request != null) {
			stipulations = new Stipulations();
			
			stipulations.setStipulation(request.getStipulation());
			stipulations.setStipulationTo(request.getStipulationTo());
		    stipulations.setDateOfStipulation(request.getDateOfStipulatio());
		    stipulations.setDateComplied(request.getDateComplied());
		    stipulations.setCompliance(request.getCompliance());
		    stipulations.setAttachment(request.getAttachment());
		    stipulations.setCompliedBy(request.getCompliedBy());
		    
		    Optional<Product> product = productRepository.findById(Long.parseLong(request.getAssetType()));
		    if(product.isPresent()) {
		    	stipulations.setAssetType(product.get());
		    }
		    
		    stipulations.setCredatedBy(request.getCreatedBy());
		    stipulations.setUpdatedBy(request.getUpdatedBy());
		    stipulations.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    stipulations.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
		    stipulations.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return stipulations;
	}

	public Stipulations prepareStipulationsUpdataData(Stipulations stipulations, @Valid DriveRequest request) {
		if(request != null) {
			stipulations.setStipulation(request.getStipulation());
			stipulations.setStipulationTo(request.getStipulationTo());
		    stipulations.setDateOfStipulation(request.getDateOfStipulatio());
		    stipulations.setDateComplied(request.getDateComplied());
		    stipulations.setCompliance(request.getCompliance());
		    stipulations.setAttachment(request.getAttachment());
		    stipulations.setCompliedBy(request.getCompliedBy());
		    
		    Optional<Product> product = productRepository.findById(Long.parseLong(request.getAssetType()));
		    if(product.isPresent()) {
		    	stipulations.setAssetType(product.get());
		    }
		    
		    stipulations.setCredatedBy(request.getCreatedBy());
		    stipulations.setUpdatedBy(request.getUpdatedBy());
		    stipulations.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    stipulations.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
		    stipulations.setStatusId(Constants.ACTIVE_STATUS_ID);	
		}
		return stipulations;
	}

	public CrsEigInspections prepareInspectionsModel(@Valid DriveRequest request) {
		CrsEigInspections inspections = null;
		if(request != null) {
			
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
		    inspections.setAttachment(request.getAttachment());
		    inspections.setStation(request.getStation());

		    Optional<Stipulations> stipulations = stipulationRepository.findById(Long.parseLong(request.getStipulationsId()));
		    if(stipulations.isPresent()) {
		    	inspections.setStipulationsId(stipulations.get());
		    }
		    inspections.setCredatedBy(request.getCreatedBy());
		    inspections.setUpdatedBy(request.getUpdatedBy());
		    inspections.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    inspections.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
		    inspections.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return inspections;
	}

	public CrsEigInspections prepareInspectionsUpdataData(CrsEigInspections inspections,
			@Valid DriveRequest request) {
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
		    inspections.setAttachment(request.getAttachment());
		    inspections.setStation(request.getStation());

		    Optional<Stipulations> stipulations = stipulationRepository.findById(Long.parseLong(request.getStipulationsId()));
		    if(stipulations.isPresent()) {
		    	inspections.setStipulationsId(stipulations.get());
		    }
		    inspections.setCredatedBy(request.getCreatedBy());
		    inspections.setUpdatedBy(request.getUpdatedBy());
		    inspections.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		    inspections.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			
		    inspections.setStatusId(Constants.ACTIVE_STATUS_ID);
		}
		return inspections;
	}

	
}
