package com.scr.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scr.mapper.DriveMapper;
import com.scr.message.request.DriveRequest;
import com.scr.model.CrsEigInspections;
import com.scr.model.DriveCheckList;
import com.scr.model.DriveTarget;
import com.scr.model.Drives;
import com.scr.model.ElectrificationTargets;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Product;
import com.scr.model.Stipulations;
import com.scr.repository.ChecklistRepository;
import com.scr.repository.DriveElectrificationTargetsRepository;
import com.scr.repository.DriveInspectionRepository;
import com.scr.repository.DriveStipulationRepository;
import com.scr.repository.DriveTargetRepository;
import com.scr.repository.DrivesRepository;
import com.scr.repository.MeasureOrActivityListRepository;
import com.scr.repository.ProductRepository;
import com.scr.util.Constants;

@Service
public class DrivesService {

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
	
	public List<Drives> findAllDrives() {
		return driveRepository.findByStatusId(Constants.ACTIVE_STATUS_ID);
	}	

	public @Valid boolean saveDriveData(@Valid DriveRequest driveRequest) {
		Drives drive = driveMapper.prepareDriveModel(driveRequest);
		drive = driveRepository.save(drive);
		return true;
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
	
	
	public List<DriveTarget> findAllDriveTargets() {
		return driveTargetRepository.findAll();
	}

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
		Stipulations stipulations = driveMapper.prepareStipulationsModel(stipulationsRequest, file);
		stipulations = driveStipulationRepository.save(stipulations);
		return true;
	}	

	public String updateStipulationsData(@Valid DriveRequest request, List<MultipartFile> file) {
		Optional<Stipulations> stipulations = driveStipulationRepository.findById(request.getId());
		if(stipulations.isPresent()) {
			Stipulations stipulationsUpdate = driveMapper.prepareStipulationsUpdataData(stipulations.get(), request, file);
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
		CrsEigInspections inspections = driveMapper.prepareInspectionsModel(request, file);
		inspections = driveInspectionRepository.save(inspections);
		return true;
	}	

	public String updateInspectionsData(@Valid DriveRequest request, List<MultipartFile> file) {
		Optional<CrsEigInspections> inspections = driveInspectionRepository.findById(request.getId());
		if(inspections.isPresent()) {
			CrsEigInspections inspectionsUpdate = driveMapper.prepareInspectionsUpdataData(inspections.get(), request, file);
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

}
