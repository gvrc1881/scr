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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.Failure;
import com.scr.services.FailureService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@RestController
@RequestMapping("/scr/api")
public class FailureController {
	
	static Logger logger = LogManager.getLogger(FailureController.class);
	
	@Autowired
	private FailureService failureService;
	
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
	public ResponseStatus saveFailureByType(@Valid @RequestBody Failure failureRequest) throws JSONException {	
		logger.info("Enter into saveFailureByType function with below request parameters ");
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
	
	@RequestMapping(value = "/saveFailureByType", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateFailureByType(@Valid @RequestBody Failure failureRequest) throws JSONException {	
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
}
