package com.scr.controller;


import java.util.List;
import java.util.Optional;
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
import com.scr.message.response.ResponseStatus;

import com.scr.model.Facility;

import com.scr.services.FacilityService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class FacilityController {
	
	private static Logger logger = Logger.getLogger(FacilityController.class);
	
	@Autowired 
	FacilityService facilityService;
	
	@RequestMapping(value = "/addFacility" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addFacility(@RequestBody Facility facility) {
		
		logger.info("Enter into save function with below request parameters ");
		
		logger.info("Request Parameters = "+facility.toString());
		
	
		try {
			logger.info("Calling service with request parameters.");
			facilityService.save(facility);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("Functional Unit added successfully", Constants.SUCCESS_CODE);
		}
		
		catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Functional Unit data. "+npe.getMessage());
			return Helper.findResponseStatus("Functional Unit save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Functional Unit data. "+e.getMessage());
			return Helper.findResponseStatus("Functional Unit save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateFacility" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateFacility(@RequestBody Facility facility) {
		logger.info("Enter into update function with below request parameters ");
		logger.info("Request Parameters = "+facility.toString());
		try {
			logger.info("Calling service with request parameters.");
			facilityService.save(facility);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("Functional unit updated successfully", Constants.SUCCESS_CODE);
	}catch(NullPointerException npe) {
		logger.error("ERROR >> While updating Functional Unit data. "+npe.getMessage());
		return Helper.findResponseStatus("Functional Unit update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
	}
	catch (Exception e) {
		logger.error("ERROR >> While updating Functional unit data. "+e.getMessage());
		return Helper.findResponseStatus("Functional Unit update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
	}
		}
	

	@RequestMapping(value = "/deleteFacility/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteFacilityById(@PathVariable Long id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected Functional Unit Id = "+id);
		try {
			facilityService.deleteFacilityById(id);
		return Helper.findResponseStatus("Functional Unit deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		logger.error("ERROR >> While deleting Functional Unit data"+npe.getMessage());
		return Helper.findResponseStatus("Functional Unit Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error("ERROR >> While deleting Functional Unit data"+e.getMessage());
		return Helper.findResponseStatus("Functional Unit Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	 @RequestMapping(value = "/findAllFacility" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<Facility> findAllFacility() throws JSONException {
		 List<Facility> facList = null;
		 try {
			   logger.info("Calling service for Functional Unit data");	
		
			   facList = facilityService.findAll();
		 logger.info("Fetched Functional Unit data***"+facList.size());
		
		return facList;
	}catch (NullPointerException npe) {
		logger.error("ERROR >>> while fetching the Functional Unit data = "+npe.getMessage());
	}
	catch (Exception e) {
		logger.error("ERROR >>> while fetching the Functional Unit data = "+e.getMessage());
	}
		 logger.info("Exit from Functional Unit function");
	return facList;	
}
	
	@RequestMapping(value = "/findFacilityById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Facility> findFacilityById(@PathVariable("id") Long id){

		Optional<Facility> fac = null;
		try {
			logger.info("Selected Functional Unit Id = "+id);
			
			fac = facilityService.findFacilityById(id);
			if(fac.isPresent()) {
				logger.info("AssetScheAssoc Data = "+fac.get());
				return new ResponseEntity<Facility>(fac.get(), HttpStatus.OK);
				
			}
			else
				return new ResponseEntity<Facility>(fac.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find Functional Unit Details by id, "+e.getMessage());
			return new ResponseEntity<Facility>(fac.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/findByFacilityName/{facilityName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existFacilityName(@PathVariable("facilityName") String facilityname){		
		try {
			return facilityService.existsByFacilityName(facilityname);
		} catch (Exception e) {
			logger.error("Error while checking exists Functional Unit code.");
			return false;
		}
	}
	
	@RequestMapping(value = "/findByFacilityId/{facilityId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsByFacilityId(@PathVariable("facilityId") String facilityId){		
		try {
			return facilityService.existsByFacilityId(facilityId);
		} catch (Exception e) {
			logger.error("Error while checking exists Functional Unit Id.");
			return false;
		}
	}
	

}