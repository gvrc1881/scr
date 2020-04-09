package com.scr.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.request.DriveRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.CrsEigInspections;
import com.scr.model.Department;
import com.scr.model.DriveCheckList;
import com.scr.model.DriveTarget;
import com.scr.model.Drives;
import com.scr.model.ElectrificationTargets;
import com.scr.model.Stipulations;
import com.scr.services.DrivesService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class DrivesController {
	static Logger logger = LogManager.getLogger(DrivesController.class);
	
	@Autowired
	private DrivesService service;
	
	@RequestMapping(value = "/drives", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<Drives>> findAllDrives() throws JSONException {
		List<Drives> usersList = null;
		try {			
			usersList = service.findAllDrives();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((usersList));
	}
	
	@RequestMapping(value = "/saveDrive", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveDriveData(@Valid @RequestBody DriveRequest driveRequest) throws JSONException {		
		try {			
			service.saveDriveData(driveRequest);
			return Helper.findResponseStatus("Drive Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding drive data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateDrive", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateDriveData(@Valid @RequestBody DriveRequest driveRequest) throws JSONException {		
		try {			
			String status = service.updateDriveData(driveRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating drive data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteDrive/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteDrive(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteDrive(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/driveById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Drives> findDriveDataById(@PathVariable("id") Long id){
		Optional<Drives> depOptional= null;
		try {
			logger.info("Selected Drive Id = "+id);
			depOptional = service.findDriveById(id);
			if(depOptional.isPresent()) {
				logger.info("Drive Data = "+depOptional.get());
				return new ResponseEntity<Drives>(depOptional.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<Drives>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Drives Details by id, "+e.getMessage());
			return new ResponseEntity<Drives>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/checklist", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<DriveCheckList>> findAllChecklist() throws JSONException {
		List<DriveCheckList> checkList = null;
		try {			
			checkList = service.findAllCheckLists();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((checkList));
	}
	
	@RequestMapping(value = "/driveTarget", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<DriveTarget>> findAllDriveTargets() throws JSONException {
		List<DriveTarget> driveTargetList = null;
		try {			
			driveTargetList = service.findAllDriveTargets();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((driveTargetList));
	}
	
	
	
	@RequestMapping(value = "/electrificationTargets", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<ElectrificationTargets>> findAllElectrificationTargets() throws JSONException {
		List<ElectrificationTargets> electrificationTargetsList = null;
		try {			
			electrificationTargetsList = service.findAllElectrificationTargets();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((electrificationTargetsList));
	}
	
	@RequestMapping(value = "/saveElectrificationTargets", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveElectrificationTargetsData(@Valid @RequestBody DriveRequest electrificationTargetsRequest) throws JSONException {		
		try {			
			service.saveElectrificationTargetsData(electrificationTargetsRequest);
			return Helper.findResponseStatus("Electrification Targets Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Electrification Targets data. "+e.getMessage());
			return Helper.findResponseStatus("Electrification Targets Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateElectrificationTargets", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateElectrificationTargetsData(@Valid @RequestBody DriveRequest electrificationTargetsRequest) throws JSONException {		
		try {			
			String status = service.updateElectrificationTargetsData(electrificationTargetsRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Electrification Targets Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating Electrification Targets data. "+e.getMessage());
			return Helper.findResponseStatus("Electrification Targets Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteElectrificationTargets/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteElectrificationTargets(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteElectrificationTargets(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Electrification Targets Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Electrification Targets Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Electrification Targets Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/electrificationTargetsById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ElectrificationTargets> findElectrificationTargetsDataById(@PathVariable("id") Long id){
		Optional<ElectrificationTargets> depOptional= null;
		try {
			depOptional = service.findElectrificationTargetsById(id);
			if(depOptional.isPresent())
				return new ResponseEntity<ElectrificationTargets>(depOptional.get(), HttpStatus.OK);
			else
				return new ResponseEntity<ElectrificationTargets>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find ElectrificationTargets Details by id");
			return new ResponseEntity<ElectrificationTargets>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/stipulations", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<Stipulations>> findAllStipulations() throws JSONException {
		List<Stipulations> stipulationsList = null;
		try {			
			stipulationsList = service.findAllStipulations();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((stipulationsList));
	}
	
	@RequestMapping(value = "/saveStipulations", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveStipulationsData(@Valid @RequestBody DriveRequest stipulationsRequest) throws JSONException {		
		try {			
			service.saveStipulationsData(stipulationsRequest);
			return Helper.findResponseStatus("Stipulations Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Stipulations data. "+e.getMessage());
			return Helper.findResponseStatus("Stipulations Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateStipulations", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateStipulationsData(@Valid @RequestBody DriveRequest stipulationsRequest) throws JSONException {		
		try {			
			String status = service.updateStipulationsData(stipulationsRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Stipulations Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating Stipulations data. "+e.getMessage());
			return Helper.findResponseStatus("Stipulations Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteStipulations/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteStipulations(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteStipulations(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Stipulations Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Stipulations Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Stipulations Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/stipulationsById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Stipulations> findStipulationsDataById(@PathVariable("id") Long id){
		Optional<Stipulations> depOptional= null;
		try {
			depOptional = service.findStipulationsById(id);
			if(depOptional.isPresent())
				return new ResponseEntity<Stipulations>(depOptional.get(), HttpStatus.OK);
			else
				return new ResponseEntity<Stipulations>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Stipulations Details by id");
			return new ResponseEntity<Stipulations>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/inspections", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<CrsEigInspections>> findAllInspections() throws JSONException {
		List<CrsEigInspections> inspectionsList = null;
		try {			
			inspectionsList = service.findAllInspections();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((inspectionsList));
	}
	
	@RequestMapping(value = "/saveInspections", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveInspectionsData(@Valid @RequestBody DriveRequest inspectionsRequest) throws JSONException {		
		try {			
			service.saveInspectionsData(inspectionsRequest);
			return Helper.findResponseStatus("Inspections Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Inspections data. "+e.getMessage());
			return Helper.findResponseStatus("Inspections Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateInspections", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateInspectionsData(@Valid @RequestBody DriveRequest inspectionsRequest) throws JSONException {		
		try {			
			String status = service.updateInspectionsData(inspectionsRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Inspections Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating Inspections data. "+e.getMessage());
			return Helper.findResponseStatus("Inspections Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteInspections/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteInspections(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteInspections(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Inspections Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Inspections Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Inspections Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/inspectionsById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<CrsEigInspections> findInspectionsDataById(@PathVariable("id") Long id){
		Optional<CrsEigInspections> depOptional= null;
		try {
			depOptional = service.findInspectionsById(id);
			if(depOptional.isPresent())
				return new ResponseEntity<CrsEigInspections>(depOptional.get(), HttpStatus.OK);
			else
				return new ResponseEntity<CrsEigInspections>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Inspections Details by id");
			return new ResponseEntity<CrsEigInspections>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
