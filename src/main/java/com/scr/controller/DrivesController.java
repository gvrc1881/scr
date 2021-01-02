package com.scr.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scr.message.request.CopyDrivesRequest;
import com.scr.message.request.DriveFileDeleteRequest;
import com.scr.message.request.DriveRequest;
import com.scr.message.response.AssetStatusUpdateResponse;
import com.scr.message.response.DriveTargetResponse;
import com.scr.message.response.DrivesResponse;
import com.scr.message.response.ResponseStatus;
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
import com.scr.model.FailureAnalysis;
import com.scr.model.InspectionType;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Product;
import com.scr.model.Stipulations;
import com.scr.repository.ChecklistRepository;
import com.scr.repository.DriveCategoryAssoRepository;
import com.scr.repository.DriveProgressRecordRepository;
import com.scr.repository.DriveTargetRepository;
import com.scr.repository.DrivesRepository;
import com.scr.repository.FacilityRepository;
import com.scr.services.DrivesService;
import com.scr.services.FacilityService;
import com.scr.services.MeasureOrActivityListService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class DrivesController {
	static Logger logger = LogManager.getLogger(DrivesController.class);
	
	@Autowired
	private DrivesService service;
	
	@Autowired
	private MeasureOrActivityListService measureService;
	
	@Autowired
	private FacilityService facilityService;
	

	@Autowired
	private DriveCategoryAssoRepository driveCategoryAssoRepository; 

	@Autowired
	private DrivesRepository driveRepository;
	
	@Autowired
	private ChecklistRepository checklistRepository;
	
	@Autowired
	private DriveTargetRepository driveTargetRepository;
	
	@Autowired
	private DriveProgressRecordRepository driveProgressRecordRepository;
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	
	@RequestMapping(value = "/drives", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<Drives>> findAllDrives() throws JSONException {
		logger.info("Enter into findAllDrives function");
		List<Drives> usersList = null;
		try {			
			logger.info("Calling service for dirves data");
			usersList = service.findAllDrives();	
			logger.info("Fetched drives data = "+usersList);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the drives data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the drives data = "+e.getMessage());
		}
		logger.info("Exit from findAllDrives function");
		return ResponseEntity.ok((usersList));
	}
	
	@RequestMapping(value = "/saveDrive", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveDriveData(@Valid @RequestBody DriveRequest driveRequest) throws JSONException {	
		logger.info("Enter into saveDriveData function with below request parameters ");
		logger.info("Request Parameters = "+driveRequest.toString());
		try {			
			logger.info("Calling service with request parameters.");
			service.saveDriveData(driveRequest);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Drive Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding drive data. "+npe.getMessage());
			return Helper.findResponseStatus("Drive Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
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
		
		List <DriveCategoryAsso> drivecatassocList = driveCategoryAssoRepository.getByDriveIdAndStatusId(driveRepository.findById(id).get(), Constants.ACTIVE_STATUS_ID);
		List <DriveCheckList> drivecheckList = checklistRepository.getByDriveIdAndStatusId(driveRepository.findById(id).get(), Constants.ACTIVE_STATUS_ID);
		List <DriveTarget> drivetargetList = driveTargetRepository.getByDriveIdAndStatusId(driveRepository.findById(id).get(), Constants.ACTIVE_STATUS_ID);
		List <DriveDailyProgress> driveprogressList = driveProgressRecordRepository.getByDriveIdAndStatusId(driveRepository.findById(id).get(), Constants.ACTIVE_STATUS_ID);
		String result="";
		logger.info("delete function==");
		logger.info("drivetargetList=="+drivetargetList.size()+"drivecheckList=="+drivecheckList.size()+"driveprogressList=="+driveprogressList.size());
		if( drivecatassocList.size() == 0 && drivecheckList.size()== 0 && drivetargetList.size() == 0 && driveprogressList.size() == 0 )
		{
			logger.info("drivecatassocList=="+drivecatassocList);
			
		try {
			String status = service.deleteDrive(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Deleted Successfully", Constants.SUCCESS_CODE);
		
			/*else
				return Helper.findResponseStatus("Drive have dependency", Constants.FAILURE_CODE);*/
		}
		catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
		}
		if(drivecatassocList.size() > 0 )
			 result="This Drive is Associated with Drive Category Assoc";
		else if(drivecheckList.size() > 0 )
			 result="This Drive is Associated with Drive Check list";
		else if(drivetargetList.size() > 0 )
			 result="This Drive is Associated with Drive target list";
		else if(driveprogressList.size() > 0 )
			 result="This Drive is Associated with Drive Daily Progress list";
		return Helper.findResponseStatus( result , Constants.FAILURE_CODE);	
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
	
	@RequestMapping(value = "/existsDriveName/{driveName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDriveName(@PathVariable("driveName") String driveName){		
		try {
			return service.existsByDriveNameAndStatusId(driveName, Constants.ACTIVE_STATUS_ID);
		} catch (Exception e) {
			logger.error("Error while checking exists drive name.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsDriveDescription/{driveDescription}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDriveDescription(@PathVariable("driveDescription") String driveDescription){		
		try {
			return service.existsByDriveDescriptionAndStatusId(driveDescription, Constants.ACTIVE_STATUS_ID);
		} catch (Exception e) {
			logger.error("Error while checking exists drive name.");
			return false;
		}
	}
	
	// DRIVE CATEGORY
	@RequestMapping(value = "/driveCategory", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<DriveCategory>> findAllDriveCategory() throws JSONException {
		List<DriveCategory> driveCategoryList = null;
		try {			
			driveCategoryList = service.findAllDriveCategory();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((driveCategoryList));
	}
	
	@RequestMapping(value = "/saveDriveCategory", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveDriveCategoryData(@Valid @RequestBody DriveRequest request) throws JSONException {		
		try {			
			service.saveDriveCategoryData(request);
			return Helper.findResponseStatus("Drive Category Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Drive Category data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Category Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateDriveCategory", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateDriveCategoryData(@Valid @RequestBody DriveRequest request) throws JSONException {		
		try {			
			String status = service.updateDriveCategoryData(request);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Category Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating Drive Category data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Category Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteDriveCategory/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteDriveCategory(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteDriveCategory(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Category Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Category Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Category Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/driveCategoryById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<DriveCategory> findDriveCategoryDataById(@PathVariable("id") Long id){
		Optional<DriveCategory> depOptional= null;
		try {
			logger.info("Selected Drive Category Id = "+id);
			depOptional = service.findDriveCategoryById(id);
			if(depOptional.isPresent()) {
				logger.info("Drive Category Data = "+depOptional.get());
				return new ResponseEntity<DriveCategory>(depOptional.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<DriveCategory>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Drive Category Details by id, "+e.getMessage());
			return new ResponseEntity<DriveCategory>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/existsDriveCategoryName/{driveCategoryName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDriveCategoryName(@PathVariable("driveCategoryName") String driveCategoryName){		
		try {
			return service.existsByDriveCategoryNameAndStatusId(driveCategoryName, Constants.ACTIVE_STATUS_ID);
		} catch (Exception e) {
			logger.error("Error while checking exists driveCategoryName.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsDriveCategoryDescription/{driveCategoryDescription}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDriveCategoryDescription(@PathVariable("driveCategoryDescription") String driveCategoryDescription){		
		try {
			return service.existsByDriveCategoryDescriptionAndStatusId(driveCategoryDescription, Constants.ACTIVE_STATUS_ID);
		} catch (Exception e) {
			logger.error("Error while checking exists drive Category description.");
			return false;
		}
	}
	// DRIVE CATEGORY
	
	// DRIVE CATEGORY ASS
		@RequestMapping(value = "/driveCategoryAsso", method = RequestMethod.GET , headers = "Accept=application/json")
		public ResponseEntity<List<DriveCategoryAsso>> findAllDriveCategoryAsso() throws JSONException {
			List<DriveCategoryAsso> driveCategoryAssoList = null;
			try {			
				driveCategoryAssoList = service.findAllDriveCategoryAsso();			
			} catch (NullPointerException e) {			
				logger.error(e);
			} catch (Exception e) {			
				logger.error(e);
			}
			return ResponseEntity.ok((driveCategoryAssoList));
		}
		
		@RequestMapping(value = "/saveDriveCategoryAsso", method = RequestMethod.POST, headers = "Accept=application/json")
		public ResponseStatus saveDriveCategoryAssoData(@Valid @RequestBody DriveRequest request) throws JSONException {		
			try {			
				service.saveDriveCategoryAssoData(request);
				return Helper.findResponseStatus("Drive Category Asso Data Added Successfully", Constants.SUCCESS_CODE);
			}catch (Exception e) {
				logger.error("ERROR >> While adding Drive Category Asso data. "+e.getMessage());
				return Helper.findResponseStatus("Drive Category Asso Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
		}
		
		@RequestMapping(value = "/updateDriveCategoryAsso", method = RequestMethod.PUT, headers = "Accept=application/json")
		public ResponseStatus updateDriveCategoryAssoData(@Valid @RequestBody DriveRequest request) throws JSONException {		
			try {			
				String status = service.updateDriveCategoryAssoData(request);
				if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
					return Helper.findResponseStatus("Drive Category Asso Data Updated Successfully", Constants.SUCCESS_CODE);
				else
					return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
			}catch (Exception e) {
				logger.error("ERROR >> While updating Drive Category Asso data. "+e.getMessage());
				return Helper.findResponseStatus("Drive Category Asso Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
		}
		
		@RequestMapping(value = "/deleteDriveCategoryAsso/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
		public ResponseStatus deleteDriveCategoryAsso(@PathVariable("id") Long id) throws JSONException {
			try {
				String status = service.deleteDriveCategoryAsso(id);
				if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
					return Helper.findResponseStatus("Drive Category Asso Deleted Successfully", Constants.SUCCESS_CODE);
				else
					return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
			} catch (NullPointerException e) {
				logger.error(e);
				return Helper.findResponseStatus("Drive Category Asso Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
			} catch (Exception e) {
				logger.error(e);
				return Helper.findResponseStatus("Drive Category Asso Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
			}
		}
		
		@RequestMapping(value = "/driveCategoryAssoById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
		public ResponseEntity<DriveCategoryAsso> findDriveCategoryAssoDataById(@PathVariable("id") Long id){
			Optional<DriveCategoryAsso> depOptional= null;
			try {
				logger.info("Selected Drive Category Asso Id = "+id);
				depOptional = service.findDriveCategoryAssoById(id);
				if(depOptional.isPresent()) {
					logger.info("Drive Category Asso Data = "+depOptional.get());
					return new ResponseEntity<DriveCategoryAsso>(depOptional.get(), HttpStatus.OK);
				}
				else
					return new ResponseEntity<DriveCategoryAsso>(depOptional.get(), HttpStatus.CONFLICT);
					
			} catch (Exception e) {
				logger.error("Error while find Drive Category Asso Details by id, "+e.getMessage());
				return new ResponseEntity<DriveCategoryAsso>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/existsDriveCategoryAssoc/{driveId}/{driveCategoryId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
		public Boolean existsByDriveIdAndDriveCategoryId(@PathVariable("driveId") Long driveId,@PathVariable("driveCategoryId") Long driveCategoryId){		
			logger.info("driveId"+driveId+"drivcatId=="+driveCategoryId);
			try {
				return service.existsByDriveIdAndDriveCategoryId(service.findDrivesById(driveId).get(),service.findDrivesCategoryById(driveCategoryId).get());
			} catch (Exception e) {
				logger.error("Error while checking exists drive and Drive cat assoc."+e.getMessage());
				return false;
			}
		}
		// DRIVE CATEGORY ASS
			
	@RequestMapping(value = "/checklist", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<DriveCheckList>> findAllChecklist() throws JSONException {
		logger.info("Enter into findAllDrives function");
		List<DriveCheckList> checkList = null;
		try {		
			logger.info("Calling service for dirves data");
			checkList = service.findAllCheckLists();	
			logger.info("Fetched driveschecklist data = "+checkList);
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((checkList));
	}
	
	@RequestMapping(value = "/measureActivityList", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<MeasureOrActivityList>> findAllMeasureActivityList() throws JSONException {
		List<MeasureOrActivityList> measureOrActivityList = null;
		try {			
			measureOrActivityList = service.findAllMeasureOrActivityList();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((measureOrActivityList));
	}
	
	@RequestMapping(value = "/saveCheckList", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveCheckListData(@Valid @RequestBody DriveRequest driveRequest) throws JSONException {		
		try {			
			service.saveCheckListData(driveRequest);
			return Helper.findResponseStatus("CheckList Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding CheckList data. "+e.getMessage());
			return Helper.findResponseStatus("CheckList Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateCheckList", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateCheckListData(@Valid @RequestBody DriveRequest driveRequest) throws JSONException {		
		try {			
			String status = service.updateCheckListData(driveRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("CheckList Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating CheckList data. "+e.getMessage());
			return Helper.findResponseStatus("CheckList Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteCheckList/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteCheckList(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteCheckList(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("CheckList Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("CheckList Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("CheckList Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/checkListById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<DriveCheckList> findCheckListDataById(@PathVariable("id") Long id){
		Optional<DriveCheckList> depOptional= null;
		try {
			logger.info("Selected Drive Id = "+id);
			depOptional = service.findCheckListById(id);
			if(depOptional.isPresent()) {
				logger.info("CheckList Data = "+depOptional.get());
				return new ResponseEntity<DriveCheckList>(depOptional.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<DriveCheckList>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Drives Details by id, "+e.getMessage());
			return new ResponseEntity<DriveCheckList>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/existsByDriveIdActivityId/{driveId}/{activityId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsByDriveIdAndActivityId(@PathVariable("driveId") Long driveId,@PathVariable("activityId") String activityId){		
		logger.info("driveId"+driveId+"activityId=="+activityId);
		try {
			return service.existsByDriveIdAndActivityId(service.findDrivesById(driveId).get(),measureService.findByActivityId(activityId).get());
		} catch (Exception e) {
			logger.error("Error while checking exists drive and activity id."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existByDriveIdPositionId/{driveId}/{activityPositionId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existByDriveIdPositionId(@PathVariable("driveId") Long driveId,@PathVariable("activityPositionId") String activityPositionId){		
		logger.info("driveId"+driveId+"activityPositionId=="+activityPositionId);
		try {
			return service.existsByDriveIdAndActivityPositionId(service.findDrivesById(driveId).get(),activityPositionId);
		} catch (Exception e) {
			logger.error("Error while checking exists drive and activityPositionid."+e.getMessage());
			return false;
		}
	}
	
/*	@RequestMapping(value = "/driveTargetAggregation", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<DriveTarget>> findDriveTargets() throws JSONException {
		List<DriveTarget> driveTargetList = null;
		try {			
			driveTargetList = service.findDriveTargets();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((driveTargetList));
	}*/
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
	@RequestMapping(value = "/saveDriveTarget", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveDriveTargetData(@Valid @RequestBody DriveRequest driveTargetRequest) throws JSONException {		
		try {			
			service.saveDriveTargetData(driveTargetRequest);
			return Helper.findResponseStatus("Drive Target Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Drive Target data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Target Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateDriveTarget", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateDriveTargetData(@Valid @RequestBody DriveRequest driveTargetRequest) throws JSONException {		
		try {			
			String status = service.updateDriveTargetData(driveTargetRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Target Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating Drive Target data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Target Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteDriveTarget/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteDriveTarget(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteDriveTarget(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Target Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Target Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Target Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/driveTargetById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<DriveTarget> findDriveTargetDataById(@PathVariable("id") Long id){
		Optional<DriveTarget> depOptional= null;
		try {
			depOptional = service.findDriveTargetById(id);
			if(depOptional.isPresent())
				return new ResponseEntity<DriveTarget>(depOptional.get(), HttpStatus.OK);
			else
				return new ResponseEntity<DriveTarget>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Drive Target Details by id");
			return new ResponseEntity<DriveTarget>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/existByUnitNameAndUnitType/{id}/{unitType}/{unitName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existByUnitNameAndUnitType(@PathVariable("id") Long id,@PathVariable("unitType") String unitType,@PathVariable("unitName") String unitName){
		
		logger.info("id=="+id+"unitType=="+unitType+"unitName=="+unitName);
		Boolean result;
		try {
			Optional<DriveTarget> targetData = service.findByUnitTypeAndUnitName(unitType,unitName);
			
			if(targetData.isPresent()) {
				DriveTarget target = targetData.get();
				logger.info("***id ***"+target.getId());
				if (id.equals(target.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and UnitNameAndUnitType..."+e.getMessage());
			return false;
		}
	}
	// DRIVE PROGRESS RECORD
	@RequestMapping(value = "/driveDailyProgress", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<DriveDailyProgress>> findAllDriveDailyProgress() throws JSONException {
		List<DriveDailyProgress> driveDailyProgress = null;
		try {			
			driveDailyProgress = service.findAllDriveDailyProgress();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((driveDailyProgress));
	}
	
	@RequestMapping(value = "/saveDriveDailyProgress", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveDriveDailyProgressData(@Valid @RequestBody DriveRequest driveDailyProgressRequest) throws JSONException {		
		try {			
			service.saveDriveDailyProgressData(driveDailyProgressRequest);
			return Helper.findResponseStatus("Drive Daily Progress Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Drive Daily Progress data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Daily Progress Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateDriveDailyProgress", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateDriveDailyProgressData(@Valid @RequestBody DriveRequest driveDailyProgressRequest) throws JSONException {		
		try {			
			String status = service.updateDriveDailyProgressData(driveDailyProgressRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Daily Progress Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating Drive Daily Progress data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Daily Progress Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteDriveDailyProgress/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteDriveDailyProgress(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteDriveDailyProgress(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Drive Daily Progress Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Target Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Target Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/driveDailyProgressById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<DriveDailyProgress> findDriveDailyProgressDataById(@PathVariable("id") Long id){
		Optional<DriveDailyProgress> depOptional= null;
		try {
			depOptional = service.findDriveDailyProgressById(id);
			if(depOptional.isPresent())
				return new ResponseEntity<DriveDailyProgress>(depOptional.get(), HttpStatus.OK);
			else
				return new ResponseEntity<DriveDailyProgress>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Drive Daily Progress Details by id");
			return new ResponseEntity<DriveDailyProgress>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//DRIVE PROGRESS RECORD
	
	// DRIVE FAILURE ANALYSIS

	@RequestMapping(value = "/failureAnalysis", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<FailureAnalysis>> findAllFailureAnalysis() throws JSONException {
		List<FailureAnalysis> failureAnalysis = null;
		try {			
			failureAnalysis = service.findAllFailureAnalysis();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((failureAnalysis));
	}
	
	@RequestMapping(value = "/saveFailureAnalysis", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveFailureAnalysisData(@Valid @RequestBody DriveRequest failureAnalysisRequest) throws JSONException {		
		try {			
			service.saveFailureAnalysisData(failureAnalysisRequest);
			return Helper.findResponseStatus("Failure Analysis Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Failure Analysis data. "+e.getMessage());
			return Helper.findResponseStatus("Failure Analysis Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateFailureAnalysis", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateFailureAnalysisData(@Valid @RequestBody DriveRequest failureAnalysisRequest) throws JSONException {		
		try {			
			String status = service.updateFailureAnalysisData(failureAnalysisRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Failure Analysis Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating Failure Analysis data. "+e.getMessage());
			return Helper.findResponseStatus("Failure Analysis Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteFailureAnalysis/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteFailureAnalysis(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteFailureAnalysis(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Failure Analysis Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Failure Analysis Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Failure Analysis Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/failureAnalysisById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<FailureAnalysis> findFailureAnalysisById(@PathVariable("id") Long id){
		Optional<FailureAnalysis> depOptional= null;
		try {
			depOptional = service.findFailureAnalysisById(id);
			if(depOptional.isPresent())
				return new ResponseEntity<FailureAnalysis>(depOptional.get(), HttpStatus.OK);
			else
				return new ResponseEntity<FailureAnalysis>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Failure Analysis Details by id");
			return new ResponseEntity<FailureAnalysis>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// DRIVE FAILURE ANALYSIS
	
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
	
	@RequestMapping(value = "/divisions", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<Division>> findAllDivisions() throws JSONException {
		List<Division> divisionList = null;
		try {			
			divisionList = service.findAllOrderByCodeAsc();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((divisionList));
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
	
	@RequestMapping(value = "/assertType", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<Product>> findAllAssertTypeFromProduct() throws JSONException {
		List<Product> productList = null;
		try {			
			productList = service.findAllProduct();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((productList));
	}
	
	@PostMapping(value="/saveStipulations",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseStatus saveStipulationsData(
			@RequestParam("file") List<MultipartFile> file, 
			@RequestParam("stipulation") String stipulation,
			@RequestParam("inspectionId") String inspectionId,
			@RequestParam("dateOfStipulation") String dateOfStipulation,
			@RequestParam("dateComplied") String dateComplied,
			@RequestParam("compliance") String compliance,
			@RequestParam("compliedBy") String compliedBy,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("createdOn") String createdOn) {
		try {
			logger.info("Save Stipulations");
			logger.info("dateOfStipulation= "+dateOfStipulation);
			logger.info("dateComplied= "+dateComplied);
			DriveRequest stipulationsRequest = new DriveRequest();
			stipulationsRequest.setStipulation(stipulation);
			stipulationsRequest.setInspectionId(inspectionId);
			stipulationsRequest.setDateOfStipulation(Helper.convertStringToTimestamp(dateOfStipulation));
			stipulationsRequest.setDateComplied(Helper.convertStringToTimestamp(dateComplied));
			stipulationsRequest.setCompliance(compliance);
			stipulationsRequest.setCompliedBy(compliedBy);
			stipulationsRequest.setCreatedBy(createdBy);
			
			
			service.saveStipulationsData(stipulationsRequest, file);
			return Helper.findResponseStatus("Stipulations Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Stipulations data. "+e.getMessage());
			return Helper.findResponseStatus("Stipulations Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@PutMapping("/updateStipulations")
	@ResponseBody
	public ResponseStatus updateStipulationsData(
			@RequestParam("file") List<MultipartFile> file, 
			@RequestParam("id") String id,
			@RequestParam("stipulation") String stipulation,
			@RequestParam("inspectionId") String inspectionId,
			@RequestParam("dateOfStipulation") String dateOfStipulation,
			@RequestParam("dateComplied") String dateComplied,
			@RequestParam("compliance") String compliance,
			@RequestParam("compliedBy") String compliedBy,
			@RequestParam("updatedBy") String updatedBy,
			@RequestParam("updatedOn") String updatedOn,
			@RequestParam("attachment") String attachment) {
		try {
			logger.info("dateOfStipulation= "+dateOfStipulation);
			DriveRequest stipulationsRequest = new DriveRequest();
			stipulationsRequest.setId(Long.parseLong(id));
			stipulationsRequest.setStipulation(stipulation);
			stipulationsRequest.setInspectionId(inspectionId);
			stipulationsRequest.setDateOfStipulation( Helper.convertStringToTimestamp(dateOfStipulation));
			stipulationsRequest.setDateComplied(Helper.convertStringToTimestamp(dateComplied));
			stipulationsRequest.setCompliance(compliance);
			stipulationsRequest.setCompliedBy(compliedBy);
			stipulationsRequest.setUpdatedBy(updatedBy);
			stipulationsRequest.setAttachment(attachment);
			logger.info("updatedBy = "+updatedBy);
			String status = service.updateStipulationsData(stipulationsRequest, file);
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
	
	@RequestMapping(value = "/inspectionType", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<InspectionType>> findAllInspectionType() throws JSONException {
		List<InspectionType> inspectionsList = null;
		try {			
			inspectionsList = service.findAllOrderByInspectionTypeAsc();			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		
		return ResponseEntity.ok((inspectionsList));
	}
	
	@PostMapping(value="/saveInspections",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseStatus saveInspectionsData(
			@RequestParam("file") List<MultipartFile> file, 
			@RequestParam("inspectionType") String inspectionType,
			@RequestParam("section") String section,
			@RequestParam("sectionStartLocation") String sectionStartLocation,
			@RequestParam("sectionEndLocation") String sectionEndLocation,
			@RequestParam("dateOfInspection") String dateOfInspection,
			@RequestParam("tkm") String tkm,
			@RequestParam("rkm") String rkm,
			@RequestParam("remarks") String remarks,
			@RequestParam("authorisationDate") String authorisationDate,
			@RequestParam("chargingDate") String chargingDate,
			@RequestParam("station") String station,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("createdOn") String createdOn
			) {
		try {
			logger.info("Save Inspection");
			DriveRequest inspectionsRequest = new DriveRequest();
			inspectionsRequest.setInspectionType(inspectionType);
			inspectionsRequest.setSection(section);
			inspectionsRequest.setSectionStartLocation(sectionStartLocation);
			inspectionsRequest.setSectionEndLocation(sectionEndLocation);
			inspectionsRequest.setDateOfInspection(Helper.convertStringToTimestamp(dateOfInspection));
			inspectionsRequest.setTKM(Helper.parseDouble(tkm));
			inspectionsRequest.setRKM(Helper.parseDouble(rkm));
			inspectionsRequest.setRemarks(remarks);
			inspectionsRequest.setAuthorisationDate(Helper.convertStringToTimestamp(authorisationDate));
			inspectionsRequest.setChargingDate(Helper.convertStringToTimestamp(chargingDate));
			inspectionsRequest.setStation(station);
			inspectionsRequest.setCreatedBy(createdBy);
			
			service.saveInspectionsData(inspectionsRequest, file);
			return Helper.findResponseStatus("Inspections Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR >> While adding Inspections data. "+e.getMessage());
			return Helper.findResponseStatus("Inspections Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
		
	@PutMapping("/updateInspections")
	@ResponseBody
	public ResponseStatus updateInspectionsData(
			@RequestParam("file") List<MultipartFile> file, 
			@RequestParam("id") String id,
			@RequestParam("inspectionType") String inspectionType,
			@RequestParam("section") String section,
			@RequestParam("sectionStartLocation") String sectionStartLocation,
			@RequestParam("sectionEndLocation") String sectionEndLocation,
			@RequestParam("dateOfInspection") String dateOfInspection,
			@RequestParam("tkm") String tkm,
			@RequestParam("rkm") String rkm,
			@RequestParam("remarks") String remarks,
			@RequestParam("authorisationDate") String authorisationDate,
			@RequestParam("chargingDate") String chargingDate,
			@RequestParam("station") String station,
			@RequestParam("updatedBy") String updatedBy,
			@RequestParam("updatedOn") String updatedOn,
			@RequestParam("attachment") String attachment) {
		try {
			logger.info("Update Inspection");
			DriveRequest inspectionsRequest = new DriveRequest();
			inspectionsRequest.setId(Long.parseLong(id));
			inspectionsRequest.setInspectionType(inspectionType);
			inspectionsRequest.setSection(section);
			inspectionsRequest.setSectionStartLocation(sectionStartLocation);
			inspectionsRequest.setSectionEndLocation(sectionEndLocation);
			inspectionsRequest.setDateOfInspection(Helper.convertStringToTimestamp(dateOfInspection));
			inspectionsRequest.setTKM(Helper.parseDouble(tkm));
			inspectionsRequest.setRKM(Helper.parseDouble(rkm));
			inspectionsRequest.setRemarks(remarks);
			inspectionsRequest.setAuthorisationDate(Helper.convertStringToTimestamp(authorisationDate));
			inspectionsRequest.setChargingDate(Helper.convertStringToTimestamp(chargingDate));
			inspectionsRequest.setStation(station);
			inspectionsRequest.setUpdatedBy(updatedBy);
			inspectionsRequest.setAttachment(attachment);
			logger.info("calling update inspection");
			String status = service.updateInspectionsData(inspectionsRequest, file);
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
	
	@RequestMapping(value = "/inspectionsContentById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<ContentManagement>> findInspectionsContentDataById(@PathVariable("id") Long commonFileId){
		List<ContentManagement> depOptional= null;
		try {
			logger.info("common fileid = "+commonFileId);
			depOptional = service.findInspectionsContentById(commonFileId);
			if(depOptional != null)
				return new ResponseEntity<List<ContentManagement>>(depOptional, HttpStatus.OK);
			else
				return new ResponseEntity<List<ContentManagement>>(depOptional, HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while finding the Content Management Files by id "+e.getMessage());
			return new ResponseEntity<List<ContentManagement>>(depOptional, HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@RequestMapping(value = "/inspectionsFileInfoById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<CrsEigInspections> findInspectionsFileInfoById(@PathVariable("id") Long id){
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
		
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus deleteFile(@Valid @RequestBody DriveFileDeleteRequest request) throws JSONException {
		ResponseStatus response = new ResponseStatus();
		Optional<CrsEigInspections> depOptional= null;
		Optional<ContentManagement> depOptionalStipulations= null;
		try {
			logger.info("Request Data = "+request.toString());
			Long Id = request.getId();
			String fileName = request.getFileName();
			String type = request.getType();
			logger.info("CommonId = "+Id + " Row id = "+fileName+ " Type = "+type);
			//if(type.equalsIgnoreCase("Stipulation")) {
				depOptionalStipulations = service.findInspectionsContentByIdAndCommon(Id, Long.valueOf(fileName));
				if(depOptionalStipulations != null && depOptionalStipulations.isPresent()) {
					ContentManagement stipulationsUpdate = depOptionalStipulations.get();
					stipulationsUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
					service.updatefileStatus(stipulationsUpdate);
				/*
				 * logger.info("Sipulattions Data = "+stipulationsUpdate.toString()); String[]
				 * files = stipulationsUpdate.getAttachment().split(","); StringBuffer sb = new
				 * StringBuffer(); for(String file : files) { logger.info("File = "+file);
				 * if(!file.trim().equalsIgnoreCase(fileName.trim())) { sb.append(file); } }
				 * logger.info("After Removing = "+sb.toString());
				 * stipulationsUpdate.setAttachment(sb.toString());
				 * service.saveStipulationWithDoc(stipulationsUpdate);
				 */
				}
				
			/*}else if(type.equalsIgnoreCase("Inspection")) {
				depOptional = service.fin
				if(depOptional.isPresent()) {
					CrsEigInspections update = depOptional.get();
					logger.info("Inspection Data = "+update.toString());
					String[] files = update.getAttachment().split(",");
					StringBuffer sb = new StringBuffer();
					for(String file : files) {
						logger.info("File = "+file);
						if(!file.trim().equalsIgnoreCase(fileName.trim())) {
							sb.append(file);
						}
					}
					logger.info("After Removing = "+sb.toString());
					update.setAttachment(sb.toString());
					service.saveInspectionWithDoc(update);
				}
			}*/
			return response;
		} catch (NullPointerException e) {
			logger.error(e);
			return response;
		} catch (Exception e) {
			logger.error(e);
			return response;
		}
	}
	
	@RequestMapping(value = "/getDrivesBasedOnFromDateAndDepot/{fromDate}/{facilityId}/{requestType}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<DrivesResponse>> getDrivesBasedOnFromDateAndDepotType(@PathVariable("fromDate") Date fromDate , @PathVariable("facilityId") String facilityId, @PathVariable("requestType") String requestType  ) throws JSONException {
		logger.info("Enter into getDrivesBasedOnFromDateAndDepotType function");
		logger.info("** formdate ***"+fromDate+"** facility Id***"+facilityId+"** category  Name **"+requestType);
		List<DrivesResponse> drivesList = null;
		Date toDate = fromDate;
		String driveCategoryName = null;
		try {			
			logger.info("Calling service for dirves data");
			Optional<Facility> facilityData = facilityService.findByFacilityId(facilityId);
			if (facilityData.isPresent()) {
				if ("OHE".equals(facilityData.get().getDepotType())) {
					driveCategoryName = "OHE SCHEDULES";
				}else {
					driveCategoryName = "PSI SCHEDULES";
				}
				drivesList = service.getDrivesBasedOnFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNull(fromDate,toDate,facilityData.get().getDepotType(),requestType,driveCategoryName);	
			}
			
			logger.info("Fetched drives size = "+drivesList.size());
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the drives data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the drives data = "+e.getMessage());
		}
		logger.info("Exit from getDrivesBasedOnFromDateAndDepotType function");
		return ResponseEntity.ok((drivesList));
	}
	
	@RequestMapping(value = "/saveDDailyProgressRecord", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveDDailyProgressRecord(@Valid @RequestBody DriveRequest driveDailyProgressRequest) throws JSONException {		
		logger.info("** performed count **"+driveDailyProgressRequest.getPerformedCount() +"** drive **"+driveDailyProgressRequest.getDriveId());
		DriveDailyProgress DDProgress = null;
		try {			
			DDProgress = service.saveDriveDailyProgressRecord(driveDailyProgressRequest);
			return Helper.findResponseStatus("Drive Daily  Progress added successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Drive Daily Progress data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Daily Progressr save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/saveDriveDailyProgressRecord", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<DriveDailyProgress> saveDriveDailyProgressRecord(@Valid @RequestBody DriveRequest driveDailyProgressRequest) throws JSONException {		
		logger.info("** performed count **"+driveDailyProgressRequest.getPerformedCount() +"** drive **"+driveDailyProgressRequest.getDriveId());
		DriveDailyProgress DDProgress = null;
		try {			
			DDProgress = service.saveDriveDailyProgressRecord(driveDailyProgressRequest);
			return new ResponseEntity<DriveDailyProgress>(DDProgress,HttpStatus.OK);
		}catch (Exception e) {
			logger.error("ERROR >> While adding Drive Daily Progress data. "+e.getMessage());
			return new ResponseEntity<DriveDailyProgress>(DDProgress, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/getDDProgressBasedOnDirveAndFromDate/{driveId}/{fromDate}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<DriveDailyProgress> getDDProgressBasedOnDirveAndFromDate(@PathVariable("driveId") Long driveId,@PathVariable("fromDate") Date fromDate){
		logger.info("Enter into getDDProgressBasedOnDirveAndFromDate function");
		Optional<DriveDailyProgress> DDProgress= null;
		Optional<Drives> drive = service.findDriveById(driveId);
		try {
			if (drive.isPresent()) {
				DDProgress = service.findByDriveIdAndPerformedDate(drive.get(),fromDate);
			}
			if(DDProgress.isPresent())
				return new ResponseEntity<DriveDailyProgress>(DDProgress.get(), HttpStatus.OK);
			/*else
				return new ResponseEntity<DriveDailyProgress>(DDProgress.get(), HttpStatus.CONFLICT);*/
				
		} catch (Exception e) {
			logger.error("Error while find Drive Daily Progress Details by id");
			return new ResponseEntity<DriveDailyProgress>(DDProgress.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	@PostMapping("/saveDriveProgressId")
	@ResponseBody
	public ResponseStatus saveDriveProgressId(
			@RequestParam("assetIds") List<String> assetIds,
			@RequestParam("driveDailyProgressId") Long driveProgressid,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("createdOn") String createdOn) {
		ResponseStatus responseStatus = new ResponseStatus();
		
		logger.info("*** drive Id**"+driveProgressid+"*** created by **"+createdBy);
		
		try {
			service.saveDriveProgressId(assetIds,driveProgressid,createdBy,Helper.convertStringToTimestamp(createdOn));
			return Helper.findResponseStatus("success fully saved ", Constants.SUCCESS_CODE);			
		} catch (Exception e) {
			// TODO: handle exception
			return Helper.findResponseStatus("data save failed"+e.getMessage(), Constants.FAILURE_CODE);			
		}
		
	}
	
	@RequestMapping(value = "/getDriveProgressIdDataBasedOnDriveDailyProgress/{driveDailyProgressId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<DriveProgressId> findDriveProgressIdDataByDriveDailyProgressId(@PathVariable("driveDailyProgressId") Long driveDailyProgressId){
		logger.info("Enter into findDriveProgressIdDataByDriveDailyProgressId function with id*** "+driveDailyProgressId);
		List<DriveProgressId> driveProgressIdList = null;
		try {
			driveProgressIdList = service.findByDriveDailyProgressId(driveDailyProgressId);
			logger.info("Fetch drive progress id data count ::"+driveProgressIdList.size());
			return driveProgressIdList;		
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the drive progress id data = "+e.getMessage());
		}
		logger.info("Exit from findDriveProgressIdDataByDriveDailyProgressId function");
		return driveProgressIdList;
	}
	
	@RequestMapping(value = "/deleteDriveProgressId/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteDriveProgressId(@PathVariable("id") Long id) throws JSONException {
		logger.info("Enter into deleteDriveProgressId function with id*** "+id);
		try {
			Optional<DriveProgressId> driveProgressId = service.findByDriveProgressId(id);
			 if (driveProgressId.isPresent()) {
				DriveProgressId DProgressId = driveProgressId.get();
				Optional<DriveDailyProgress> DDP = service.findById(DProgressId.getDriveDailyProgressId().getId());
				if (DDP.isPresent()) {
					DriveDailyProgress driveDailyProgress = DDP.get();
					driveDailyProgress.setPerformedCount(driveDailyProgress.getPerformedCount() - 1);
					service.saveDriveDailyProgress(driveDailyProgress);
				}
			}
			 service.deleteDriveProgressId(id);
	       	return Helper.findResponseStatus("Drive Progress Id Deleted Successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Progress Id Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Drive Progress Id Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/getAlreadyDoneCountBasedOnDiveAndFromDate/{driveId}/{fromDate}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Double getAlreadyDoneCountBasedOnDiveAndFromDate(@PathVariable("driveId") Long driveId,@PathVariable("fromDate") Date fromDate){
		logger.info("Enter into getAlreadyDoneCountBasedOnDiveAndFromDate function");
		List<DriveDailyProgress> DDProgress= null;
		Double alreadyDoneCount = 0D;
		Optional<Drives> drive = service.findDriveById(driveId);
		try {
			if (drive.isPresent()) {
				DDProgress = service.findByDriveIdAndPerformedDateLessThan(drive.get(),fromDate);
			}
			for (DriveDailyProgress driveDailyProgress : DDProgress) {
				alreadyDoneCount = alreadyDoneCount+driveDailyProgress.getPerformedCount();
			}
			logger.info("*** already done count***"+alreadyDoneCount);
				return alreadyDoneCount;
			/*else
				return new ResponseEntity<DriveDailyProgress>(DDProgress.get(), HttpStatus.CONFLICT);*/
				
		} catch (Exception e) {
			logger.error("Error while finding already done count:");
			return alreadyDoneCount;
		}
	}
	
	
	@RequestMapping(value = "/existsDriveCategoryAssocAndId/{id}/{driveId}/{driveCategoryId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDriveCategoryAssocAndId(@PathVariable("id") Long id,@PathVariable("driveId") Long driveId,@PathVariable("driveCategoryId") Long driveCategoryId){
		
		logger.info("id=="+id+"driveId=="+driveId+"driveCategoryId=="+driveCategoryId);
		Boolean result;
		try {
			Optional<DriveCategoryAsso> assocData = service.findByDriveIdAndDriveCategoryId(service.findDrivesById(driveId).get(),service.findDrivesCategoryById(driveCategoryId).get());
			
			if(assocData.isPresent()) {
				DriveCategoryAsso assoc = assocData.get();
				logger.info("***id ***"+assoc.getId());
				if (id.equals(assoc.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and driveId and driveCategoryId..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/getDriveCheckList",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<Drives>> getDrives(){
		List<Drives> driveList= service.getDrives();
		logger.info("driveList"+driveList.size());
			return new ResponseEntity<List<Drives>>(driveList, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/getDrivesBasedOnCategory/{categoryId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<DriveCategoryAsso>> getDrivesBasedOnCategory(@PathVariable("categoryId") Long categoryId) throws JSONException {
		logger.info("Enter into getDrivesBasedOnCategory function");
		Optional<DriveCategory> driveCategory = service.findDriveCategoryById(categoryId);
		List<DriveCategoryAsso> driveCategoryAssoList = null;
		try {			
			if (driveCategory.isPresent()) {
				driveCategoryAssoList = service.findByDriveCategoryIdAndStatusId(driveCategory.get(),Constants.ACTIVE_STATUS_ID);
			}
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		logger.info("Exit from getDrivesBasedOnCategory function");
		return ResponseEntity.ok((driveCategoryAssoList));
	}
	
	@PostMapping(value="/copyDrives")
	@ResponseBody
	public ResponseStatus copyDrives(@RequestBody CopyDrivesRequest copyDrivesRequest) {
		ResponseStatus responseStatus = new ResponseStatus();
		
		logger.info("*** drive category **"+copyDrivesRequest.getDriveCategory()+"*** drives size **"+copyDrivesRequest.getDrives().size());

		try {			
			logger.info("Calling service with request parameters.");
			service.saveDrives(copyDrivesRequest);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Drive Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding drive data. "+npe.getMessage());
			return Helper.findResponseStatus("Drive Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding drive data. "+e.getMessage());
			return Helper.findResponseStatus("Drive Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/existsByDriveIdActivityIdAndId/{id}/{driveId}/{activityId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsByDriveIdActivityIdAndId(@PathVariable("id") Long id,@PathVariable("driveId") Long driveId,@PathVariable("activityId") String activityId){
		
		logger.info("id=="+id+"driveId=="+driveId+"activityId=="+activityId);
		Boolean result;
		try {
			Optional<DriveCheckList> checkListData = service.findByDriveIdAndActivityId(service.findDrivesById(driveId).get(),measureService.findByActivityId(activityId).get());
			
			if(checkListData.isPresent()) {
				DriveCheckList checkData = checkListData.get();
				logger.info("***id ***"+checkData.getId());
				if (id.equals(checkData.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and driveId and activityId..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existByDriveIdPositionIdAndId/{id}/{driveId}/{activityPositionId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existByDriveIdPositionIdAndId(@PathVariable("id") Long id,@PathVariable("driveId") Long driveId,@PathVariable("activityPositionId") String activityPositionId){
		
		logger.info("id=="+id+"driveId=="+driveId+"activityPositionId=="+activityPositionId);
		Boolean result;
		try {
			Optional<DriveCheckList> checkLstData = service.findByDriveIdAndActivityPositionId(service.findDrivesById(driveId).get(),activityPositionId);
			
			if(checkLstData.isPresent()) {
				DriveCheckList cData = checkLstData.get();
				logger.info("***id ***"+cData.getId());
				if (id.equals(cData.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and driveId and activityPositionId..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/getDDProgressDataBasedOnDrive/{driveId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DriveDailyProgress> getDDProgressDataBasedOnDrive(@PathVariable("driveId") Long driveId) {
		logger.info("Enter into getDDProgressDataBasedOnDrive function");
		List<DriveDailyProgress> DDProgress = new ArrayList<DriveDailyProgress>();
		Optional<Drives> drive = service.findDriveById(driveId);
		try {
			if (drive.isPresent()) {
				List<DriveDailyProgress> dDailyProgresses = service.findByDriveId(drive.get());
				for (DriveDailyProgress driveDailyProgress : dDailyProgresses) {
					if (driveDailyProgress.getDepot() != null) {
						Optional<Facility> facility = facilityService.findByFacilityId(driveDailyProgress.getDepot());

						if (facility.isPresent()) {
							driveDailyProgress.setDepot(facility.get().getFacilityName());
						}
					}
					DDProgress.add(driveDailyProgress);
				}
			}
			return DDProgress;
		} catch (Exception e) {
			logger.error("Error while find Drive Daily Progress Details by id");
		}
		return DDProgress;
	}
	
	@RequestMapping(value = "/getDrivesBasedOnCategoryId/{categoryId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<Drives>> getDrivesBasedOnCategoryId(@PathVariable("categoryId") Long categoryId) throws JSONException {
		logger.info("Enter into getDrivesBasedOnCategory function");
		Optional<DriveCategory> driveCategory = service.findDriveCategoryById(categoryId);
		//List<Drives> drivesList = null;
		
		List<Drives> drives = null;
		logger.info("driveslist---");
		try {			
			if (driveCategory.isPresent()) {
				
				drives = service.getByCategoryId(driveCategory.get());
				
			}
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		logger.info("Exit from getDrivesBasedOnCategory function");
		return ResponseEntity.ok((drives));
	}
	
	/*@RequestMapping(value = "/getTargetsBasedOnDrive/{category}/{driveId}/{zone}/{division}/{subDivision}/{facility}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<Drives>> getTargetsBasedOnDrive(@PathVariable("category") Long categoryId,@PathVariable("driveId") Long driveId,
			@PathVariable("zone") String zone,@PathVariable("division") String division,
			@PathVariable("subDivision") String subDivision,@PathVariable("facility") String facility) throws JSONException {
		logger.info("Enter into getTargetsBasedOnDrive function");	
		Optional<DriveCategory> driveCategory = service.findDriveCategoryById(categoryId);
		Optional<DriveCategoryAsso> drives= driveCategoryAssoRepository.findByDriveCategoryId(driveCategory);
				
		List<Drives> driveList = new ArrayList<>();
		
		try {	
			
			if(driveCategory != null && driveId.equals("null") && zone.equals("null") && division.equals("null") && subDivision.equals("null") && facility.equals("null")  )
			{	
				logger.info("if condition");
				if (driveCategory.isPresent()) {
					
				
					driveList = service.getByCategoryId(driveCategory.get());
				}
				else 
				{
										
						Drives drive = service.findDriveCategoryById(driveCategory.get(),driveId);
					
					
				}
		} 
		}catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		logger.info("driveslist==="+driveList.size());
		logger.info("Exit from getTargetsBasedOnDrive function");
		return ResponseEntity.ok((driveList));
	}*/
	
	@PostMapping(value="/saveTargets")
	@ResponseBody
	public ResponseStatus saveTargets(@RequestBody List<DriveTarget> driveTarget) {
		logger.info("*** Enter into saveTargets function ***");
		try {	
			logger.info("*** saveTargets function ***"+driveTarget.toString());
			service.saveTargets(driveTarget);
			logger.info("Preparing the return response and saveTargets function end ");
			return Helper.findResponseStatus("DriveTarget Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding DriveTarget Data. "+npe.getMessage());
			return Helper.findResponseStatus("DriveTarget Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding DriveTarget Data. "+e.getMessage());
			return Helper.findResponseStatus("DriveTarget   Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/getTargetsBasedOnDrive/{category}/{driveId}/{zone}/{division}/{subDivision}/{facility}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<DriveTargetResponse>> getTargetsBasedOnDrive(@PathVariable("category") Long categoryId,@PathVariable("driveId") String driveId,
			@PathVariable("zone") String zone,@PathVariable("division") String division,
			@PathVariable("subDivision") String subDivision,@PathVariable("facility") String facility) throws JSONException {
		
		logger.info("Enter into getTargetsBasedOnDrive function");	
		logger.info("categoryId=="+categoryId);
		logger.info("driveId=="+driveId);
		logger.info("zone=="+zone);
		logger.info("division=="+division);
		logger.info("subDivision=="+subDivision);
		logger.info("facility=="+facility);
		Optional<DriveCategory> driveCategory = service.findDriveCategoryById(categoryId);		
		
		List<Drives> drives = null;
		List<DriveTargetResponse> driveTargetReponse = new ArrayList<>();		
		try {

			if (categoryId != null && driveId.equals("null") && !zone.equals("null") && division.equals("null") && subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in if condiction");
				Optional<DriveCategory> driveCategry = service.findDriveCategoryById(categoryId);
				if(driveCategry.isPresent())
				{
					drives = service.getByCategoryId(driveCategry.get());
					logger.info("drives=="+drives.size());
				}			
				
				driveTargetReponse = service.getByDriveId(drives,zone);
				
			}
			else	if (categoryId != null && !driveId.equals("null") && !zone.equals("null") && division.equals("null") && subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 1st else if condition");				
				if(driveCategory.isPresent())
				{
					Optional<Drives> drivId = service.findDriveById(Long.parseLong(driveId));
				Optional<DriveCategoryAsso> drive= driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategory,drivId,Constants.ACTIVE_STATUS_ID);
				logger.info("active drive cat=="+drive);								
					if(drive.isPresent())
					{
					logger.info("for loop for drives and cat==");
					
					driveTargetReponse = service.getByDriveCategoryIdAndDriveId(drive.get().getDriveCategoryId(),drive.get().getDriveId(),zone);	
					
					logger.info("for loop end=="+driveTargetReponse.size());
				
				
				logger.info("drive cast asso"+driveTargetReponse.toString());
				
				}
			}
			}
			else if (categoryId != null && driveId.equals("null") && !zone.equals("null") && !division.equals("null") && subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 2nd else if condiction");
				if(driveCategory.isPresent()) {
					
					drives = service.getByCategoryId(driveCategory.get());
					logger.info("drives=="+drives.size());					
				}
				String div = division+"_DIV";
				Optional<Facility> fac = facilityRepository.findByFacilityNameAndZone(div,zone);
				driveTargetReponse = service.getByDriveId(drives,fac);
				
			}
			
			else if(categoryId != null && !driveId.equals("null") && !zone.equals("null") && !division.equals("null") && subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 3nd else if condiction");
				if(driveCategory.isPresent())
				{
					Optional<Drives> drivId = service.findDriveById(Long.parseLong(driveId));
				Optional<DriveCategoryAsso> drive= driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategory,drivId,Constants.ACTIVE_STATUS_ID);
				logger.info("active drive cat=="+drive);								
					if(drive.isPresent())
					{
					logger.info("for loop for drives and cat==");
					String div = division+"_DIV";
					Optional<Facility> fac = facilityRepository.findByFacilityNameAndZone(div,zone);
					
					driveTargetReponse = service.getByDriveCategoryIdAndDriveId(drive.get().getDriveCategoryId(),drive.get().getDriveId(),fac.get().getFacilityName());	
					
					logger.info("for loop end=="+driveTargetReponse.size());
				
				
				logger.info("drive cast asso"+driveTargetReponse.toString());
				
				}
			}
			}
			else if (categoryId != null && driveId.equals("null") && zone.equals("null") && !division.equals("null") && subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 4th else if condiction");
				if(driveCategory.isPresent()) {
					
					drives = service.getByCategoryId(driveCategory.get());
					logger.info("drives=="+drives.size());					
				}
				String div = division+"_DIV";
				Optional<Facility> fac = facilityRepository.findByFacilityName(div);
				driveTargetReponse = service.getByDriveId(drives,fac);
				
			}
			else	if (categoryId != null && !driveId.equals("null") && zone.equals("null") && !division.equals("null") && subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 5th  else if condition");				
				if(driveCategory.isPresent())
				{
					Optional<Drives> drivId = service.findDriveById(Long.parseLong(driveId));
				Optional<DriveCategoryAsso> drive= driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategory,drivId,Constants.ACTIVE_STATUS_ID);
				logger.info("active drive cat=="+drive);								
					if(drive.isPresent())
					{
					logger.info("for loop for drives and cat==");
					String div = division+"_DIV";
					driveTargetReponse = service.getByDriveCategoryIdAndDriveId(drive.get().getDriveCategoryId(),drive.get().getDriveId(),div);	
					
					logger.info("for loop end=="+driveTargetReponse.size());
				
				
				logger.info("drive cast asso"+driveTargetReponse.toString());
				
				}
			}
			}
			else if (categoryId != null && driveId.equals("null") && zone.equals("null") && !division.equals("null") && !subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 6th else if condiction");
				if(driveCategory.isPresent()) {
					
					drives = service.getByCategoryId(driveCategory.get());
					logger.info("drives=="+drives.size());					
				}
				String subDiv = subDivision+"_SUB_DIV";				
				Optional<Facility> fac = facilityRepository.findByFacilityNameAndDivision(subDiv,division);
				driveTargetReponse = service.getByDriveId(drives,fac);
				
			}
			else if (categoryId != null && driveId.equals("null") && zone.equals("null") && !division.equals("null") && !subDivision.equals("null")&& !facility.equals("null")) {
				logger.info("in 7th else if condiction");
				if(driveCategory.isPresent()) {
					
					drives = service.getByCategoryId(driveCategory.get());
					logger.info("drives=="+drives.size());					
				}
				Optional<Facility> fac = facilityRepository.findByFacilityNameAndDivisionAndSubDivision(facility,division,subDivision);
				driveTargetReponse = service.getByDriveId(drives,fac);
				
			}
			else if(categoryId != null && !driveId.equals("null") && zone.equals("null") && !division.equals("null") && !subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 8th else if condiction");	
							if(driveCategory.isPresent())
							{
								Optional<Drives> drivId = service.findDriveById(Long.parseLong(driveId));
							Optional<DriveCategoryAsso> drive= driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategory,drivId,Constants.ACTIVE_STATUS_ID);
							logger.info("active drive cat=="+drive);								
								if(drive.isPresent())
								{
								logger.info("for loop for drives and cat==");
								String subDiv = subDivision+"_SUB_DIV";	
								Optional<Facility> fac = facilityRepository.findByFacilityNameAndDivision(subDiv,division);
								
								driveTargetReponse = service.getByDriveCategoryIdAndDriveId(drive.get().getDriveCategoryId(),drive.get().getDriveId(),fac.get().getFacilityName());	
								
								logger.info("for loop end=="+driveTargetReponse.size());
							
							
							logger.info("drive cast asso"+driveTargetReponse.toString());
							
							}
						}
						}
			else if(categoryId != null && !driveId.equals("null") && zone.equals("null") && !division.equals("null") && !subDivision.equals("null")&& !facility.equals("null")) {
				logger.info("in 9th else if condiction");
				if(driveCategory.isPresent())
				{
					Optional<Drives> drivId = service.findDriveById(Long.parseLong(driveId));
				Optional<DriveCategoryAsso> drive= driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategory,drivId,Constants.ACTIVE_STATUS_ID);
				logger.info("active drive cat=="+drive);								
					if(drive.isPresent())
					{
					logger.info("for loop for drives and cat==");
					Optional<Facility> fac = facilityRepository.findByFacilityNameAndDivisionAndSubDivision(facility,division,subDivision);
					
					driveTargetReponse = service.getByDriveCategoryIdAndDriveId(drive.get().getDriveCategoryId(),drive.get().getDriveId(),fac.get().getFacilityName());	
					
					logger.info("for loop end=="+driveTargetReponse.size());
				
				
				logger.info("drive cast asso"+driveTargetReponse.toString());
				
				}
			}
			}
			else if (categoryId != null && driveId.equals("null") && zone.equals("null") && division.equals("null") && !subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 10th else if condiction");
				if(driveCategory.isPresent()) {
					
					drives = service.getByCategoryId(driveCategory.get());
					logger.info("drives=="+drives.size());					
				}
				String subDiv = subDivision+"_SUB_DIV";	
				Optional<Facility> fac = facilityRepository.findByFacilityName(subDiv);
				driveTargetReponse = service.getByDriveId(drives,fac);
				
			}
			else	if (categoryId != null && !driveId.equals("null") && zone.equals("null") && division.equals("null") && !subDivision.equals("null")&& facility.equals("null")) {
				logger.info("in 11th  else if condition");				
				if(driveCategory.isPresent())
				{
					Optional<Drives> drivId = service.findDriveById(Long.parseLong(driveId));
				Optional<DriveCategoryAsso> drive= driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategory,drivId,Constants.ACTIVE_STATUS_ID);
				logger.info("active drive cat=="+drive);								
					if(drive.isPresent())
					{
					logger.info("for loop for drives and cat==");
					String subDiv = subDivision+"_SUB_DIV";
					driveTargetReponse = service.getByDriveCategoryIdAndDriveId(drive.get().getDriveCategoryId(),drive.get().getDriveId(),subDiv);	
					
					logger.info("for loop end=="+driveTargetReponse.size());
				
				
				logger.info("drive cast asso"+driveTargetReponse.toString());
				
				}
			}
			}
			else if (categoryId != null && driveId.equals("null") && zone.equals("null") && division.equals("null") && !subDivision.equals("null")&& !facility.equals("null")) {
				logger.info("in 12th else if condiction");
				if(driveCategory.isPresent()) {
					
					drives = service.getByCategoryId(driveCategory.get());
					logger.info("drives=="+drives.size());					
				}							
				Optional<Facility> fac = facilityRepository.findByFacilityNameAndSubDivision(facility,subDivision);
				driveTargetReponse = service.getByDriveId(drives,fac);
				
			}
			else if(categoryId != null && !driveId.equals("null") && zone.equals("null") && division.equals("null") && !subDivision.equals("null")&& !facility.equals("null")) {
				logger.info("in 13th else if condiction");	
							if(driveCategory.isPresent())
							{
								Optional<Drives> drivId = service.findDriveById(Long.parseLong(driveId));
							Optional<DriveCategoryAsso> drive= driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategory,drivId,Constants.ACTIVE_STATUS_ID);
							logger.info("active drive cat=="+drive);								
								if(drive.isPresent())
								{
								logger.info("for loop for drives and cat==");
								
								Optional<Facility> fac = facilityRepository.findByFacilityNameAndSubDivision(facility,subDivision);
								
								driveTargetReponse = service.getByDriveCategoryIdAndDriveId(drive.get().getDriveCategoryId(),drive.get().getDriveId(),fac.get().getFacilityName());	
								
								logger.info("for loop end=="+driveTargetReponse.size());
							
							
							logger.info("drive cast asso"+driveTargetReponse.toString());
							
							}
						}
						}
			else if (categoryId != null && driveId.equals("null") && zone.equals("null") && division.equals("null") && subDivision.equals("null")&& !facility.equals("null")) {
				logger.info("in 14th else if condiction");
				if(driveCategory.isPresent()) {
					
					drives = service.getByCategoryId(driveCategory.get());
					logger.info("drives=="+drives.size());					
				}
					
				Optional<Facility> fac = facilityRepository.findByFacilityName(facility);
				driveTargetReponse = service.getByDriveId(drives,fac);
				
			}
			else	if (categoryId != null && !driveId.equals("null") && zone.equals("null") && division.equals("null") && subDivision.equals("null")&& !facility.equals("null")) {
				logger.info("in 11th  else if condition");				
				if(driveCategory.isPresent())
				{
					Optional<Drives> drivId = service.findDriveById(Long.parseLong(driveId));
				Optional<DriveCategoryAsso> drive= driveCategoryAssoRepository.getByDriveCategoryIdAndDriveIdAndStatusId(driveCategory,drivId,Constants.ACTIVE_STATUS_ID);
				logger.info("active drive cat=="+drive);								
					if(drive.isPresent())
					{
					logger.info("for loop for drives and cat==");					
					driveTargetReponse = service.getByDriveCategoryIdAndDriveId(drive.get().getDriveCategoryId(),drive.get().getDriveId(),facility);	
					
					logger.info("for loop end=="+driveTargetReponse.size());
				
				
				logger.info("drive cast asso"+driveTargetReponse.toString());
				
				}
			}
			}
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the targets data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the targets data = " + e.getMessage());
		}
		logger.info("Exit from getTowerCarBasedOnDivsion function");
		return ResponseEntity.ok((driveTargetReponse));
	}
	
	
}
