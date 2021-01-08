package com.scr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.jobs.CommonUtility;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Failure;
import com.scr.model.FailureActionsCausesImpact;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Model;
import com.scr.model.ProductCategoryMember;
import com.scr.model.TssFeederMaster;
import com.scr.model.UserDefualtFacConsIndEtc;
import com.scr.services.FailureActionsCausesImpactService;
import com.scr.services.FailureService;
import com.scr.services.UserDefualtFacConsIndEtcService;
import com.scr.util.Constants;
import com.scr.util.Helper;
import com.scr.model.AssetMasterData;
import com.scr.model.Facility;


@RestController
@RequestMapping("/scr/api")
public class FailureController {
	
	static Logger logger = LogManager.getLogger(FailureController.class);
	
	@Autowired
	private FailureService failureService;
	
	@Autowired
	private FailureActionsCausesImpactService failureImpactService;
	
	@Autowired
	private CommonUtility  commonUtility;
	
	@RequestMapping(value = "/failuresByType/{failureType}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<Failure>> findFailureByType(
				@PathVariable("failureType") String failureType) throws JSONException {
		logger.info("Enter into failures function");
		logger.info("failureType = "+failureType);
		List<Failure> failureList = null;
		try {			
			logger.info("Calling service for getting relevent type data");
			failureList = failureService.findFailureByType(failureType);	
			logger.info("Fetched data = "+failureList);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the failure type data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the failure type data = "+e.getMessage());
		}
		logger.info("Exit from energyConsumption function");
		return ResponseEntity.ok((failureList));
	}
	
	@RequestMapping(value = "/saveFailureByType", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveFailureByType(@RequestBody Failure failureRequest) throws JSONException {	
		logger.info("Enter into saveFailureByType function with below request parameters r value***"+failureRequest.getrValue()+"** x valu **"+failureRequest.getxValue());
		logger.info("Request Parameters = "+failureRequest.toString());
		try {			
			logger.info("Calling service with request parameters.");
			Failure failure = failureService.saveFailureByType(failureRequest);
			failureRequest.setFailureSeqId(failure.getId().toString());
			failureService.saveFailureByType(failureRequest);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Failure Type "+failureRequest.getTypeOfFailure()+" Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Failure Type "+failureRequest.getTypeOfFailure()+" data. "+npe.getMessage());
			return Helper.findResponseStatus("Failure Type "+failureRequest.getTypeOfFailure()+" Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Failure Type "+failureRequest.getTypeOfFailure()+" data. "+e.getMessage());
			return Helper.findResponseStatus("Failure Type "+failureRequest.getTypeOfFailure()+" Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateFailureByType", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateFailureByType(@RequestBody Failure failureRequest) throws JSONException {	
		logger.info("Enter into saveFailureByType function with below request parameters ");
		logger.info("Request Parameters = "+failureRequest.toString());
		try {			
			logger.info("Calling service with request parameters.");
			failureService.updateFailureByType(failureRequest);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Failure Type "+failureRequest.getTypeOfFailure()+" Updated Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Failure Type "+failureRequest.getTypeOfFailure()+" data. "+npe.getMessage());
			return Helper.findResponseStatus("Failure Type "+failureRequest.getTypeOfFailure()+" Updation is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Failure Type "+failureRequest.getTypeOfFailure()+" data. "+e.getMessage());
			return Helper.findResponseStatus("Failure Type "+failureRequest.getTypeOfFailure()+" Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteFailureTypeById/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteFailureTypeById(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = failureService.deleteFailureTypeById(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Failure Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Failure Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Failure Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/failureTypeById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Failure> findFailureTypeById(@PathVariable("id") Long id){
		Optional<Failure> faOptional= null;
		try {
			faOptional = failureService.findFailureTypeById(id);
			if(faOptional.isPresent())
				return new ResponseEntity<Failure>(faOptional.get(), HttpStatus.OK);
			else
				return new ResponseEntity<Failure>(faOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Failure Type Details by id");
			return new ResponseEntity<Failure>(faOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@RequestMapping(value = "/findAllFailures", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Failure> findAllFailures() throws JSONException {
		logger.info("Enter into findAllFailures function");
		List<Failure> failuresList = null;
		try {
			logger.info("Calling service for Measures data");
			failuresList = failureService.findAll();
			logger.info("Fetched Measures data = "+failuresList.size());
			return failuresList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the failures data = "+npe.getMessage());
		}catch (Exception e) {
			logger.error("ERROR >>> while fetching the failures data = "+e.getMessage());
		}
		logger.info("Exit from findAllfailures function");
		return failuresList;	
	}
	
	
/*	@RequestMapping(value = "/getEquipments", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<AssetMasterData>> findByAssetId(String productId){
		List<AssetMasterData> assetId= failureService.findByAssetId(productId);
		logger.info("Fetched assets data = "+assetId.size());
		return new ResponseEntity<List<AssetMasterData>>(assetId,HttpStatus.OK);	
		
	}*/
	@RequestMapping(value = "/getEquipments/{subStation}", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<AssetMasterData>> findByAssetIdBasedOnFacilityName(@PathVariable("subStation") String subStation){
		List<AssetMasterData> assetId= failureService.findByAssetIdBasedOnFacilityName(subStation);
		logger.info("Fetched assets data = "+assetId.size());
		return new ResponseEntity<List<AssetMasterData>>(assetId,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/findByFeedOfAndFromDateTime/{feedOf}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByFeedOfAndFromDateTime(@PathVariable("feedOf") String feedOf ,@PathVariable("fromDateTime") Timestamp fromDateTime){
		logger.info("Exist====="+feedOf+"fromDateTime"+fromDateTime);
		try {
			logger.info("Request for checking exists feedOf and fromDateTime...");
			return failureService.existsByFeedOfAndFromDateTime(feedOf,fromDateTime);
		} catch (Exception e) {
			logger.error("Error while checking exists feedOf and fromDateTime..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/findBySubStationAndEquipmentAndFromDateTime/{subStation}/{equipment}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findBySubStationAndEquipmentAndFromDateTime(@PathVariable("subStation") String subStation ,@PathVariable("equipment") String equipment,@PathVariable("fromDateTime") Timestamp fromDateTime){
		logger.info("Exist====="+subStation+equipment+"fromDateTime"+fromDateTime);
		try {
			logger.info("Request for checking exists subStation and equipment and fromDateTime...");
			return failureService.existsBySubStationAndEquipmentAndFromDateTime(subStation,equipment,fromDateTime);
		} catch (Exception e) {
			logger.error("Error while checking exists subStation and equipment and fromDateTime..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/findBySubStationAndOccurence/{subStation}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findBySubStationAndOccurence(@PathVariable("subStation") String subStation ,@PathVariable("fromDateTime") Timestamp fromDateTime){
		logger.info("Exist====="+subStation+fromDateTime);
		try {
			logger.info("Request for checking exists subStation and fromDateTime ");
			return failureService.existsBySubStationAndOccurrence(subStation,fromDateTime);
		} catch (Exception e) {
			logger.error("Error while checking exists subStation and fromDateTime ..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/findByOccurenceAndPlaceAndFromDateTime/{occurrence}/{place}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByOccurenceAndPlaceAndFromDateTime(@PathVariable("occurrence") String occurrence ,@PathVariable("place") String place,@PathVariable("fromDateTime") Timestamp fromDateTime){
		logger.info("Exist====="+occurrence+place+"fromDateTime"+fromDateTime);
		try {
			logger.info("Request for checking exists occurrence and place and fromDateTime...");
			return failureService.existsByOccurrenceAndPlaceAndFromDateTime(occurrence,place,fromDateTime);
		} catch (Exception e) {
			logger.error("Error while checking exists occurrence and place and fromDateTime..."+e.getMessage());
			return false;
		}
	}

	@RequestMapping(value = "/findBySubStationAndLocationAndFromDateTime/{subStation}/{location}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findBySubStationAndLocationAndFromDateTime(@PathVariable("subStation") String subStation ,@PathVariable("location") String location,@PathVariable("fromDateTime") Timestamp fromDateTime){
		logger.info("Exist====="+subStation+location+"fromDateTime"+fromDateTime);
		try {
			logger.info("Request for checking exists subStation and place and fromDateTime...");
			return failureService.existsBySubStationAndLocationAndFromDateTime(subStation,location,fromDateTime);
		} catch (Exception e) {
			logger.error("Error while checking exists subStation and location and fromDateTime..."+e.getMessage());
			return false;
		}
	}
	

@RequestMapping(value = "/findByFeedOfAndFromDateTimeAndId/{id}/{feedOf}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean existsActivityNameAndId(@PathVariable("id") Long id,@PathVariable("feedOf") String feedOf,@PathVariable("fromDateTime") Timestamp fromDateTime){
	
	logger.info("id=="+id+"feedOf=="+feedOf);
	Boolean result;
	try {
		Optional<Failure> failData = failureService.findByFeedOfAndFromDateTime(feedOf,fromDateTime);
		
		if(failData.isPresent()) {
			Failure fail = failData.get();
			logger.info("***id ***"+fail.getId());
			if (id.equals(fail.getId())) {
				return result = false;
			} else {
				return result = true;
			}
		}
		else 
			return  result = false;
	} catch (Exception e) {
		logger.error("Error while checking exists id and feedOf..."+e.getMessage());
		return false;
	}
}

@RequestMapping(value = "/findBySubStationAndEquipmentAndFromDateTimeAndId/{id}/{subStation}/{equipment}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean findBySubStationAndEquipmentAndFromDateTimeAndId(@PathVariable("id") Long id,@PathVariable("subStation") String subStation,@PathVariable("equipment") String equipment,@PathVariable("fromDateTime") Timestamp fromDateTime){
	
	logger.info("id=="+id+"subStation=="+subStation);
	Boolean result;
	try {
		Optional<Failure> faiData = failureService.findBySubStationAndEquipmentAndFromDateTime(subStation,equipment,fromDateTime);
		
		if(faiData.isPresent()) {
			Failure fai = faiData.get();
			logger.info("***id ***"+fai.getId());
			if (id.equals(fai.getId())) {
				return result = false;
			} else {
				return result = true;
			}
		}
		else 
			return  result = false;
	} catch (Exception e) {
		logger.error("Error while checking exists id and subStation..."+e.getMessage());
		return false;
	}
}

@RequestMapping(value = "/findBySubStationAndOccurenceAndId/{id}/{subStation}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean findBySubStationAndOccurenceAndId(@PathVariable("id") Long id,@PathVariable("subStation") String subStation,@PathVariable("fromDateTime") Timestamp fromDateTime){
	
	logger.info("id=="+id+"subStation=="+subStation);
	Boolean result;
	try {
		Optional<Failure> faiData = failureService.findBySubStationAndOccurrence(subStation,fromDateTime);
		
		if(faiData.isPresent()) {
			Failure fai = faiData.get();
			logger.info("***id ***"+fai.getId());
			if (id.equals(fai.getId())) {
				return result = false;
			} else {
				return result = true;
			}
		}
		else 
			return  result = false;
	} catch (Exception e) {
		logger.error("Error while checking exists id and subStation..."+e.getMessage());
		return false;
	}
}

@RequestMapping(value = "/findByOccurenceAndPlaceAndFromDateTimeAndId/{id}/{occurrence}/{place}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean findByOccurenceAndPlaceAndFromDateTimeAndId(@PathVariable("id") Long id,@PathVariable("occurrence") String occurrence,@PathVariable("place") String place,@PathVariable("fromDateTime") Timestamp fromDateTime){
	
	logger.info("id=="+id+"subStation=="+fromDateTime);
	Boolean result;
	try {
		Optional<Failure> faiDat = failureService.findByOccurrenceAndPlaceAndFromDateTime(occurrence,place,fromDateTime);
		
		if(faiDat.isPresent()) {
			Failure fa = faiDat.get();
			logger.info("***id ***"+fa.getId());
			if (id.equals(fa.getId())) {
				return result = false;
			} else {
				return result = true;
			}
		}
		else 
			return  result = false;
	} catch (Exception e) {
		logger.error("Error while checking exists id and subStation..."+e.getMessage());
		return false;
	}
}

@RequestMapping(value = "/findBySubStationAndLocationAndFromDateTimeAndId/{id}/{subStation}/{location}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean findBySubStationAndLocationAndFromDateTimeAndId(@PathVariable("id") Long id,@PathVariable("subStation") String subStation,@PathVariable("location") String location,@PathVariable("fromDateTime") Timestamp fromDateTime){
	
	logger.info("id=="+id+"subStation=="+fromDateTime);
	Boolean result;
	try {
		Optional<Failure> faiDa = failureService.findBySubStationAndLocationAndFromDateTime(subStation,location,fromDateTime);
		
		if(faiDa.isPresent()) {
			Failure fad = faiDa.get();
			logger.info("***id ***"+fad.getId());
			if (id.equals(fad.getId())) {
				return result = false;
			} else {
				return result = true;
			}
		}
		else 
			return  result = false;
	} catch (Exception e) {
		logger.error("Error while checking exists id and subStation..."+e.getMessage());
		return false;
	}
}

@RequestMapping(value = "/failuresByTypesbasedOnDivision/{failureType}/{loggedUserData}", method = RequestMethod.GET , headers = "Accept=application/json")
public ResponseEntity<List<Failure>> findFailureByType(
			@PathVariable("failureType") String failureType,@PathVariable("loggedUserData") String loggedUserData) throws JSONException {
	logger.info("Enter into failures function");
	logger.info("failureType = "+failureType);
	logger.info("user=="+loggedUserData);
	List<Failure> failureList = null;
	List<String> fac= new ArrayList<>();
	try {			
		logger.info("Calling service for getting relevent type data");
		List<Facility> facility = commonUtility.findUserHierarchy(loggedUserData);
		logger.info("facilities=="+facility.size());
		for (Facility facility2 : facility) {
			
			fac.add(facility2.getFacilityId());
			
		}
		if(failureType.equals("GRID_FAILURE")) {
			failureList = failureService.findFailureByTypeAndFeedOf(failureType,fac);
		}else
			
		failureList = failureService.findFailureByTypeAndSubStation(failureType,fac);	
		logger.info("Fetched data = "+failureList);
	} catch (NullPointerException e) {			
		logger.error("ERROR >>> while fetching the failure type data = "+e.getMessage());
	} catch (Exception e) {			
		logger.error("ERROR >>> while fetching the failure type data = "+e.getMessage());
	}
	logger.info("Exit from failures function");
	return ResponseEntity.ok((failureList));
}
	
}
