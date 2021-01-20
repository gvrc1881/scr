package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Facility;
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
	@RequestMapping(value = "/getComparisonPointsOnFacilityIdAndDescription/{facilityId}/{checkPointDescription}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ThermovisionCheckPoints> findCheckPointsBasedOnFacilityId(@PathVariable("facilityId") Long facilityId,@PathVariable("checkPointDescription") String checkPointDescription){
		logger.info("Enter into findCheckPointsBasedOnFacilityId function ");
		List<ThermovisionCheckPoints> checkPoints = null;
		try {
			Optional<Facility> facility = facilityService.findFacilityById(facilityId);
			if (facility.isPresent()) {
				checkPoints = thermovisionCheckPointServices.findByFacilityIdAndCheckPointDescription(facility.get(),checkPointDescription);
			}
			return checkPoints;
		} catch (Exception e) {
			logger.error("Error >>  while find check points based on facility id, "+e.getMessage());
		}
		return checkPoints;
	}
}
