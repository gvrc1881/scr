package com.scr.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.DriveMapper;
import com.scr.message.request.DriveRequest;
import com.scr.model.CrsEigInspections;
import com.scr.model.DriveCheckList;
import com.scr.model.DriveTarget;
import com.scr.model.Drives;
import com.scr.model.ElectrificationTargets;
import com.scr.model.Stipulations;
import com.scr.repository.ChecklistRepository;
import com.scr.repository.DriveElectrificationTargetsRepository;
import com.scr.repository.DriveInspectionRepository;
import com.scr.repository.DriveStipulationRepository;
import com.scr.repository.DriveTargetRepository;
import com.scr.repository.DrivesRepository;
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
	
	public @Valid boolean saveStipulationsData(@Valid DriveRequest stipulationsRequest) {
		Stipulations stipulations = driveMapper.prepareStipulationsModel(stipulationsRequest);
		stipulations = driveStipulationRepository.save(stipulations);
		return true;
	}	

	public String updateStipulationsData(@Valid DriveRequest request) {
		Optional<Stipulations> stipulations = driveStipulationRepository.findById(request.getId());
		if(stipulations.isPresent()) {
			Stipulations stipulationsUpdate = driveMapper.prepareStipulationsUpdataData(stipulations.get(), request);
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

	public @Valid boolean saveInspectionsData(@Valid DriveRequest request) {
		CrsEigInspections inspections = driveMapper.prepareInspectionsModel(request);
		inspections = driveInspectionRepository.save(inspections);
		return true;
	}	

	public String updateInspectionsData(@Valid DriveRequest request) {
		Optional<CrsEigInspections> inspections = driveInspectionRepository.findById(request.getId());
		if(inspections.isPresent()) {
			CrsEigInspections inspectionsUpdate = driveMapper.prepareInspectionsUpdataData(inspections.get(), request);
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
