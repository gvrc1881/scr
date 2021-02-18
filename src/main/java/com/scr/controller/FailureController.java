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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scr.jobs.CommonUtility;
import com.scr.message.response.FailureResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Failure;
import com.scr.model.FailureActionsCausesImpact;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Model;
import com.scr.model.ProductCategoryMember;
import com.scr.model.TssFeederMaster;
import com.scr.model.TssSpSspAssoc;
import com.scr.model.UserDefualtFacConsIndEtc;
import com.scr.model.Works;
import com.scr.repository.FacilityRepository;
import com.scr.services.ContentManagementService;
import com.scr.services.FacilityService;
import com.scr.services.FailureActionsCausesImpactService;
import com.scr.services.FailureService;
import com.scr.services.TssSpSspAssocService;
import com.scr.services.UserDefualtFacConsIndEtcService;
import com.scr.util.Constants;
import com.scr.util.Helper;
import com.scr.model.AssetMasterData;
import com.scr.model.ContentManagement;
import com.scr.model.Facility;


@RestController
@RequestMapping("/scr/api")
public class FailureController {
	
	static Logger logger = LogManager.getLogger(FailureController.class);
	
	@Autowired
	private FailureService failureService;
	
	@Autowired
	private ContentManagementService contentManagementService;
	
	@Autowired
	private FailureActionsCausesImpactService failureImpactService;
	
	@Autowired
	private CommonUtility  commonUtility;
	@Autowired
	private FacilityService facilityService;
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	@Autowired
	private TssSpSspAssocService tssSpSspService;
	
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
	@RequestMapping(value = "/getCascades/{subStation}", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<AssetMasterData>> findByAssetIdBasedOnFacility(@PathVariable("subStation") Long subStation){
		logger.info("subStation==="+subStation);
		Optional<Facility> fac = facilityRepository.findById(subStation);
		logger.info("facility==="+fac);
		List<AssetMasterData> assetId= failureService.findByAssetIdBasedOnFacility(fac.get().getFacilityId());
		logger.info("Fetched assets data = "+assetId.size());
		return new ResponseEntity<List<AssetMasterData>>(assetId,HttpStatus.OK);	
		
	}
	/*@RequestMapping(value = "/findFaciltiyBasedOnAssetId/{assetId}", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<AssetMasterData>> findFaciltiyBasedOnAssetId(@PathVariable("assetId") String assetId){
		List<AssetMasterData> facilityId= failureService.findByAssetId(assetId);
		logger.info("Fetched assets data = "+facilityId.size());
		return new ResponseEntity<List<AssetMasterData>>(facilityId,HttpStatus.OK);	
		
	}*/
	
	@RequestMapping(value = "/findFaciltiyBasedOnAssetId/{assetId}/{subStation}", method = RequestMethod.GET, headers = "accept=application/json")
	public ResponseEntity<TssSpSspAssoc> findFaciltiyBasedOnAssetId(@PathVariable("assetId") String assetId,@PathVariable("subStation") String subStation) {
		logger.info("** Enter into findFaciltiyBasedOnAssetId function ***");
		Optional<AssetMasterData> amd = null;
		Optional<Facility> fac = null;
		Optional<TssSpSspAssoc> sspList =null; 
		try {
			logger.info("** assetId = " + assetId);
			amd = failureService.findByAssetId(assetId);
			if (amd.isPresent()) {
				 fac  = facilityRepository.findByFacilityId(amd.get().getFacilityId());
				 Optional<Facility> facility  = facilityRepository.findByFacilityId(subStation);
				 logger.info("facility==="+fac.toString());
				 sspList = tssSpSspService.findBySspSpFacilityIdAndTssFacilityId(Long.toString(fac.get().getId()),facility.get());
				 logger.info("** ssplist === " + sspList.toString());
				return new ResponseEntity<TssSpSspAssoc>(sspList.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<TssSpSspAssoc>(sspList.get(), HttpStatus.CONFLICT);

		} catch (Exception e) {
			logger.error("Error >>  while find facility Details by assetId, " + e.getMessage());
			return new ResponseEntity<TssSpSspAssoc>(sspList.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@RequestMapping(value = "/findByFeedOfAndFromDateTime/{feedOf}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByFeedOfAndFromDateTime(@PathVariable("feedOf") String feedOf ,@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure){
		logger.info("Exist====="+feedOf+"fromDateTime"+fromDateTime);
		try {
			logger.info("Request for checking exists feedOf and fromDateTime...");
			return failureService.existsByFeedOfAndFromDateTimeAndTypeOfFailure(feedOf,fromDateTime,typeOfFailure);
		} catch (Exception e) {
			logger.error("Error while checking exists feedOf and fromDateTime..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/findBySubStationAndEquipmentAndFromDateTime/{subStation}/{equipment}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findBySubStationAndEquipmentAndFromDateTime(@PathVariable("subStation") String subStation ,@PathVariable("equipment") String equipment,@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure){
		logger.info("Exist====="+subStation+equipment+"fromDateTime"+fromDateTime);
		try {
			logger.info("Request for checking exists subStation and equipment and fromDateTime...");
			return failureService.existsBySubStationAndEquipmentAndFromDateTimeAndTypeOfFailure(subStation,equipment,fromDateTime,typeOfFailure);
		} catch (Exception e) {
			logger.error("Error while checking exists subStation and equipment and fromDateTime..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/findBySubStationAndOccurence/{subStation}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findBySubStationAndOccurence(@PathVariable("subStation") String subStation ,@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure){
		logger.info("Exist====="+subStation+fromDateTime);
		try {
			logger.info("Request for checking exists subStation and fromDateTime ");
			return failureService.existsBySubStationAndOccurrenceAndTypeOfFailure(subStation,fromDateTime,typeOfFailure);
		} catch (Exception e) {
			logger.error("Error while checking exists subStation and fromDateTime ..."+e.getMessage());
			return false;
		}
	}
	
	/*@RequestMapping(value = "/findByOccurenceAndPlaceAndFromDateTime/{occurrence}/{occurrence1}/{place}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByOccurenceAndPlaceAndFromDateTime(@PathVariable("occurrence") String occurrence ,@PathVariable("occurrence1") String occurrence1 ,@PathVariable("place") String place,
			@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure ){
		logger.info("Exist====="+occurrence+place+"fromDateTime"+fromDateTime);*/
	@PostMapping("/findByOccurenceAndPlaceAndFromDateTime")
	@ResponseBody
	public Boolean findByOccurenceAndPlaceAndFromDateTime(		
			@RequestBody  FailureResponse failureResponse
			) {
		try {
			logger.info("Request for checking exists occurrence and place and fromDateTime...");
			logger.info("ocuur=="+failureResponse.getOccurrence()+"place=="+failureResponse.getPlace()+"timee="+failureResponse.getFromDateTime()+"type fail=="+failureResponse.getTypeOfFailure());
			
			return failureService.existsByOccurrenceAndPlaceAndFromDateTimeAndTypeOfFailure(failureResponse.getOccurrence(),failureResponse.getPlace(),Timestamp.valueOf(failureResponse.getFromDateTime()),failureResponse.getTypeOfFailure());
		} catch (Exception e) {
			logger.error("Error while checking exists occurrence and place and fromDateTime..."+e.getMessage());
			return false;
		}
	}

	/*@RequestMapping(value = "/findBySubStationAndLocationAndFromDateTime/{subStation}/{location}/{location1}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findBySubStationAndLocationAndFromDateTime(@PathVariable("subStation") String subStation ,@PathVariable("location") String location,@PathVariable("location1") String location1,
			@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure){
		logger.info("Exist====="+subStation+"location==="+location+"fromDateTime"+fromDateTime);
		try {
			logger.info("Request for checking exists subStation and place and fromDateTime...");
			String loc = location+"/"+location1;
			logger.info("loc=="+loc);
			return failureService.existsBySubStationAndLocationAndFromDateTime(subStation,loc,fromDateTime,typeOfFailure);
		} catch (Exception e) {
			logger.error("Error while checking exists subStation and location and fromDateTime..."+e.getMessage());
			return false;
		}
	}
	*/
	

@PostMapping("/findBySubStationAndLocationAndFromDateTime")
@ResponseBody
public Boolean findBySubStationAndLocationAndFromDateTime(		
		@RequestBody  FailureResponse failureResponse
		) {
	try {
		logger.info("date fro ui=="+failureResponse.getFromDateTime());
		logger.info("Request for checking exists subStation and place and fromDateTime...");
				
		return failureService.existsBySubStationAndLocationAndFromDateTimeAndTypeOfFailure(failureResponse.getSubStation(),
				failureResponse.getLocation(),failureResponse.getFromDateTime(),
				failureResponse.getTypeOfFailure());
	} catch (Exception e) {
		logger.error("Error while checking exists subStation and location and fromDateTime..."+e.getMessage());
		return false;
	}
	
}

@RequestMapping(value = "/findByFeedOfAndFromDateTimeAndId/{id}/{feedOf}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean existsActivityNameAndId(@PathVariable("id") Long id,@PathVariable("feedOf") String feedOf,@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure){
	
	logger.info("id=="+id+"feedOf=="+feedOf);
	Boolean result;
	try {
		Optional<Failure> failData = failureService.findByFeedOfAndFromDateTimeAndTypeOfFailure(feedOf,fromDateTime,typeOfFailure);
		
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

@RequestMapping(value = "/findBySubStationAndEquipmentAndFromDateTimeAndId/{id}/{subStation}/{equipment}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean findBySubStationAndEquipmentAndFromDateTimeAndId(@PathVariable("id") Long id,@PathVariable("subStation") String subStation,@PathVariable("equipment") String equipment,
		@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure){
	
	logger.info("id=="+id+"subStation=="+subStation);
	Boolean result;
	try {
		Optional<Failure> faiData = failureService.findBySubStationAndEquipmentAndFromDateTimeAndTypeOfFailure(subStation,equipment,fromDateTime,typeOfFailure);
		
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

@RequestMapping(value = "/findBySubStationAndOccurenceAndId/{id}/{subStation}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean findBySubStationAndOccurenceAndId(@PathVariable("id") Long id,@PathVariable("subStation") String subStation,@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure){
	
	logger.info("id=="+id+"subStation=="+subStation);
	Boolean result;
	try {
		Optional<Failure> faiData = failureService.findBySubStationAndOccurrenceAndTypeOfFailure(subStation,fromDateTime,typeOfFailure);
		
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

/*@RequestMapping(value = "/findByOccurenceAndPlaceAndFromDateTimeAndId/{id}/{occurrence}/{place}/{fromDateTime}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean findByOccurenceAndPlaceAndFromDateTimeAndId(@PathVariable("id") Long id,@PathVariable("occurrence") String occurrence,@PathVariable("place") String place,@PathVariable("fromDateTime") Timestamp fromDateTime){
	
*/	
@PostMapping("/findByOccurenceAndPlaceAndFromDateTimeAndId")
@ResponseBody
public Boolean findByOccurenceAndPlaceAndFromDateTimeAndId(		
		@RequestBody  FailureResponse failureResponse
		) {
logger.info("id=="+failureResponse.getId()+"subStation=="+failureResponse.getOccurrence());
	Boolean result;
	try {
		Optional<Failure> faiDat = failureService.findByOccurrenceAndPlaceAndFromDateTimeAndTypeOfFailure(failureResponse.getOccurrence(),failureResponse.getPlace(),Timestamp.valueOf(failureResponse.getFromDateTime()),failureResponse.getTypeOfFailure());
		
		if(faiDat.isPresent()) {
			Failure fa = faiDat.get();
			logger.info("***id ***"+fa.getId());
			if (failureResponse.getId().equals(fa.getId())) {
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

/*@RequestMapping(value = "/findBySubStationAndLocationAndFromDateTimeAndId/{id}/{subStation}/{location}/{fromDateTime}/{typeOfFailure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
public Boolean findBySubStationAndLocationAndFromDateTimeAndId(@PathVariable("id") Long id,@PathVariable("subStation") String subStation,@PathVariable("location") String location,@PathVariable("location1") String location1,
		@PathVariable("fromDateTime") Timestamp fromDateTime,@PathVariable("typeOfFailure") String typeOfFailure){
	*/
@PostMapping("/findBySubStationAndLocationAndFromDateTimeAndId")
@ResponseBody
public Boolean findBySubStationAndLocationAndFromDateTimeAndId(		
		@RequestBody  FailureResponse failureResponse
		) {

	
	Boolean result;
	try {
		
		Optional<Failure> faiDa = failureService.findBySubStationAndLocationAndFromDateTimeAndTypeOfFailure(failureResponse.getSubStation(),failureResponse.getLocation(),Timestamp.valueOf(failureResponse.getFromDateTime()),failureResponse.getTypeOfFailure());
		
		if(faiDa.isPresent()) {
			Failure fad = faiDa.get();
			logger.info("***id ***"+fad.getId());
			if (failureResponse.getId().equals(fad.getId())) {
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
		}else if(failureType.equals("FAILURE_OCCURENCE")) {
			logger.info("fac=="+fac);
			for (Facility facility2 : facility) {
				
				fac.add(facility2.getDivision());
				
			}
			failureList = failureService.findFailureByTypeAndDataDiv(failureType,fac);
		}
		else
			
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
	

@PostMapping("/unUsualOccurenceFailureUploadFiles")
@ResponseBody
public ResponseStatus uploadAttachedFiles(
		@RequestParam("file") List<MultipartFile> file,
		@RequestParam("unUsualOccurenceFailId") Long unUsualOccurenceFailId,
		@RequestParam("contentCategory") String contentCategory,
		@RequestParam("description") String description,
		@RequestParam("divisionCode") String divisionCode,
		@RequestParam("createdBy") String createdBy,
		@RequestParam("zonal") String zonal, 
		@RequestParam("FU") String FU,
		@RequestParam("contentTopic") String contentTopic) {
	ResponseStatus responseStatus = new ResponseStatus();
	try {
		logger.info("File Name: "+contentCategory);
		logger.info("fun_unit=="+FU);
		responseStatus = failureService.storeUploadedFiles(file, contentCategory, description, divisionCode, createdBy, zonal,FU, contentTopic,unUsualOccurenceFailId);
		logger.info("File Saved Successfully!");
	} catch (NullPointerException e) {
		logger.error(e);
		return Helper.findResponseStatus("File saving is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error(e);
		return Helper.findResponseStatus("File saving is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
	return responseStatus;
}

@RequestMapping(value = "/unUsualOccurenceFailureAttachedDocumentList/{unUsualOccurenceFailId}", method = RequestMethod.GET ,headers = "Accept=application/json")	
public ResponseEntity<List<ContentManagement>> getDocumentList( @PathVariable("unUsualOccurenceFailId") Long unUsualOccurenceFailId){
	List<ContentManagement> contentManagementList = new ArrayList<>();
	try {
		logger.info("Getting Project  Details  = "+unUsualOccurenceFailId);	
		Optional<Failure> uuoObj =failureService.findFailureTypeById(unUsualOccurenceFailId);
		if (uuoObj.isPresent()) {
			Failure uuoFailure = uuoObj.get();
			if(uuoFailure.getContentLink() != null) {
				contentManagementList = contentManagementService.findByCommonFileId(Long.parseLong(uuoFailure.getContentLink()));
			}
			
			logger.info("content size:::"+contentManagementList.size());
		}
		return new ResponseEntity<List<ContentManagement>>(contentManagementList, HttpStatus.OK);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Error while getting DivisionHistory Details"+e.getMessage());
		return new ResponseEntity<List<ContentManagement>>(contentManagementList, HttpStatus.CONFLICT);
	}	
}

}
