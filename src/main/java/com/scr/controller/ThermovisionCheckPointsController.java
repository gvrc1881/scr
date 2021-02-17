package com.scr.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import com.scr.message.request.CopyCheckPointsRequest;
import com.scr.message.request.OheLocationAndAssetsRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Facility;
import com.scr.model.TestInspection;
import com.scr.model.ThermovisionCheckPoints;
import com.scr.services.FacilityService;
import com.scr.services.ThermovisionCheckPointsService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ThermovisionCheckPointsController {
	
private Logger logger = Logger.getLogger(ThermovisionCheckPointsController.class);
	
	@Autowired
	private ThermovisionCheckPointsService thermovisionCheckPointServices;
	
	@Autowired
	private FacilityService facilityService;
	
	
	@RequestMapping(value = "/addCheckPoints", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus save(@RequestBody ThermovisionCheckPoints checkPoints){
		logger.info("Enter into save function with below request parameters ");
		logger.info("Request Parameters = "+checkPoints.toString());		
		try {
			logger.info("Calling service with request parameters.");
			thermovisionCheckPointServices.save(checkPoints);
				logger.info("Preparing the return response");
				return Helper.findResponseStatus("Thermovision CheckPoints added successfully", Constants.SUCCESS_CODE);
			}catch(NullPointerException npe) {
				logger.error("ERROR >> While adding Thermovision CheckPoints data. "+npe.getMessage());
				return Helper.findResponseStatus("Thermovision CheckPoints save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
			}
			catch (Exception e) {
				logger.error("ERROR >> While adding Thermovision CheckPoints data. "+e.getMessage());
				return Helper.findResponseStatus("Thermovision CheckPoints save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
	}
	@RequestMapping(value = "/findCheckPointsBasedOnFacilityId/{facilityId}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ThermovisionCheckPoints> findCheckPointsBasedOnFacilityId(@PathVariable("facilityId") Long facilityId){
		logger.info("Enter into findCheckPointsBasedOnFacilityId function ");
		List<ThermovisionCheckPoints> checkPoints = null;
		try {
			Optional<Facility> facility = facilityService.findFacilityById(facilityId);
			if (facility.isPresent()) {
				checkPoints = thermovisionCheckPointServices.getThermovisionCheckPointsBasedOnFacilityId(facility.get());
			}
			return checkPoints;
		} catch (Exception e) {
			logger.error("Error >>  while find check points based on facility id, "+e.getMessage());
		}
		return checkPoints;
	}
	
	@PostMapping(value="/updateCheckPoints")
	@ResponseBody
	public ResponseStatus updateCheckPoints(@RequestBody List<ThermovisionCheckPoints> checkPoints) {
		logger.info("*** Enter into updateCheckPoints function ***");
		try {			
			thermovisionCheckPointServices.updateCheckPoints(checkPoints);
			logger.info("Preparing the return response and updateCheckPoints function end ");
			return Helper.findResponseStatus("Thermovision Check Points Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Thermovision Check Points  Data. "+npe.getMessage());
			return Helper.findResponseStatus("Thermovision Check Points  Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Thermovision Check Points  Data. "+e.getMessage());
			return Helper.findResponseStatus("Thermovision Check Points Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
//	@RequestMapping(value = "/getComparisonPointsOnFacilityIdAndDescription/{facilityId}/{checkPointDescription}",method = RequestMethod.GET  , headers="accept=application/json" )
//	public ResponseEntity<List<ThermovisionCheckPoints>> getComparisonPointsOnFacilityIdAndDescription(@PathVariable("facilityId") Facility facilityId,@PathVariable("checkPointDescription") String checkPointDescription){
//		logger.info("** Enter into getComparisonPoints OnFacilityId AndDescription functions ***");
//		List<ThermovisionCheckPoints> checkPointsList= thermovisionCheckPointServices.findByFacilityIdAndCheckPointDescription(facilityId,checkPointDescription);
//		logger.info("** preparing response and getComparisonPoints OnFacilityId AndDescription function end ***");
//			return new ResponseEntity<List<ThermovisionCheckPoints>>(checkPointsList, HttpStatus.OK);		
//	}
	@RequestMapping(value = "/getComparisonPointsOnFacilityIdAndDescription/{facilityId}/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ThermovisionCheckPoints> findCheckPointsBasedOnFacilityId(@PathVariable("facilityId") Long facilityId,@PathVariable("checkPointDescription") Long id){
		logger.info("Enter into findCheckPointsBasedOnFacilityId function ");
		List<ThermovisionCheckPoints> checkPoints = null;
		try {
			Optional<Facility> facility = facilityService.findFacilityById(facilityId);
			if (facility.isPresent()) {
				checkPoints = thermovisionCheckPointServices.findByFacilityIdAndIdNotIn(facility.get(),id);
			}
			return checkPoints;
		} catch (Exception e) {
			logger.error("Error >>  while find check points based on facility Id, "+e.getMessage());
		}
		return checkPoints;
	}
	
	@RequestMapping(value = "/saveCopyCheckPoints", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveCopyCheckPoints(@Valid @RequestBody List<ThermovisionCheckPoints> thermovisionCheckPoints) throws JSONException {	
		logger.info("Enter into saveOheLocationAndAssets function with below request parameters ");
		logger.info("Request Parameters = "+thermovisionCheckPoints.toString());
		try {			
			logger.info("Calling service with request parameters.");
			thermovisionCheckPointServices.saveCopyCheckPointsData(thermovisionCheckPoints);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Copy Thermovision CheckPoints Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Copy Thermovision CheckPoints data. "+npe.getMessage());
			return Helper.findResponseStatus("Copy Thermovision CheckPoints Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Copy Thermovision CheckPoints. "+e.getMessage());
			return Helper.findResponseStatus("Copy Thermovision CheckPoints Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/findByFacilityIdCheckPointPartAndCheckPoint1Description/{facilityId}/{checkPointPart}/{checkPoint1Description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFacilityIdCheckPointPartAndCheckPoint1Description(@PathVariable("facilityId") Long facilityId ,@PathVariable("checkPointPart") String checkPointPart,@PathVariable("checkPoint1Description") String checkPoint1Description){
		
		try {
			logger.info("Request for checking exists facilityId checkPointPart  and checkPoint1Description ...");
			return thermovisionCheckPointServices.existsByFacilityIdAndCheckPointPartAndCheckPoint1Description(facilityService.findFacilityById(facilityId).get(),checkPointPart,checkPoint1Description);
		} catch (Exception e) {
			logger.error("Error while checking exists name "+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existFacilityIdCheckPointPartAndCheckPoint1DescriptionAndId/{id}/{facilityId}/{checkPointPart}/{checkPoint1Description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existFacilityIdCheckPointPartAndCheckPoint1DescriptionAndId(@PathVariable("id") Long id,@PathVariable("facilityId") Long facilityId ,@PathVariable("checkPointPart") String checkPointPart,@PathVariable("checkPoint1Description") String checkPoint1Description){
		
		logger.info("id=="+id+"facilityId=="+facilityId);
		Boolean result;
		try {
			Optional<ThermovisionCheckPoints> thermovisionCheckPointsData = thermovisionCheckPointServices.findByFacilityIdAndCheckPointPartAndCheckPoint1Description (facilityService.findFacilityById(facilityId).get(),checkPointPart,checkPoint1Description);
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(thermovisionCheckPointsData.isPresent()) {
				ThermovisionCheckPoints checkPoin = thermovisionCheckPointsData.get();
				logger.info("***id ***"+checkPoin.getId());
				if (id.equals(checkPoin.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and name..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/findByFacilityIdCheckPointPartAndCheckPoint2Description/{facilityId}/{checkPointPart}/{checkPoint2Description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFacilityIdCheckPointPartAndCheckPoint2Description(@PathVariable("facilityId") Long facilityId ,@PathVariable("checkPointPart") String checkPointPart,@PathVariable("checkPoint2Description") String checkPoint2Description){
		
		try {
			logger.info("Request for checking exists facilityId checkPointPart  and checkPoint1Description ...");
			return thermovisionCheckPointServices.existsByFacilityIdAndCheckPointPartAndCheckPoint2Description(facilityService.findFacilityById(facilityId).get(),checkPointPart,checkPoint2Description);
		} catch (Exception e) {
			logger.error("Error while checking exists name "+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existFacilityIdCheckPointPartAndCheckPoint2DescriptionAndId/{id}/{facilityId}/{checkPointPart}/{checkPoint2Description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existFacilityIdCheckPointPartAndCheckPoint2DescriptionAndId(@PathVariable("id") Long id,@PathVariable("facilityId") Long facilityId ,@PathVariable("checkPointPart") String checkPointPart,@PathVariable("checkPoint2Description") String checkPoint2Description){
		
		logger.info("id=="+id+"facilityId=="+facilityId);
		Boolean result;
		try {
			Optional<ThermovisionCheckPoints> thermovisionCheckPointsData = thermovisionCheckPointServices.findByFacilityIdAndCheckPointPartAndCheckPoint2Description (facilityService.findFacilityById(facilityId).get(),checkPointPart,checkPoint2Description);
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(thermovisionCheckPointsData.isPresent()) {
				ThermovisionCheckPoints checkPoin = thermovisionCheckPointsData.get();
				logger.info("***id ***"+checkPoin.getId());
				if (id.equals(checkPoin.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and name..."+e.getMessage());
			return false;
		}
	}
}
