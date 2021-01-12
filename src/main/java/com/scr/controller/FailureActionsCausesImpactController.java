
package com.scr.controller;

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

import com.scr.message.response.ResponseStatus;
import com.scr.model.Failure;
import com.scr.model.FailureActionsCausesImpact;


import com.scr.services.FailureActionsCausesImpactService;

import com.scr.util.Constants;
import com.scr.util.Helper;



@RestController
@RequestMapping("/scr/api")
public class FailureActionsCausesImpactController {
	
	static Logger logger = LogManager.getLogger(FailureActionsCausesImpactController.class);
	
	
	@Autowired
	private FailureActionsCausesImpactService failureImpactService;
	
	
	 @RequestMapping(value = "/actions" , method = RequestMethod.GET , headers = "Accept=application/json")
		public List<FailureActionsCausesImpact> findActions() throws JSONException {
			 List<FailureActionsCausesImpact> actionsList = null;
			 try {
				   logger.info("Calling service for failure actions data");	
			
				   actionsList = failureImpactService.findAll();
			 logger.info("Fetched failure actions data***"+actionsList.size());
			return actionsList;
		}catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the failure actions data = "+npe.getMessage());
		}
		catch (Exception e) {
			logger.error("ERROR >>> while fetching the failure actions data = "+e.getMessage());
		}
			 logger.info("Exit from failure actions function");
		return actionsList;	
	}
	 @RequestMapping(value = "/actionsBasedOnFailureId/{failureSeqId}" , method = RequestMethod.GET , headers = "Accept=application/json")
		public List<FailureActionsCausesImpact> findActionsByFailureSeqId(@PathVariable("failureSeqId") String failureSeqId) throws JSONException {
			 List<FailureActionsCausesImpact> actionsList = null;
			 try {
				   logger.info("Calling service for failure actions data");	
			
				   actionsList = failureImpactService.findByFailureSeqId(Long.parseLong(failureSeqId));
			 logger.info("Fetched failure actions data***"+actionsList.size());
			return actionsList;
		}catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the failure actions data = "+npe.getMessage());
		}
		catch (Exception e) {
			logger.error("ERROR >>> while fetching the failure actions data = "+e.getMessage());
		}
			 logger.info("Exit from failure actions function");
		return actionsList;	
	}
	 
	@RequestMapping(value = "/saveActions", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveActions(@RequestBody FailureActionsCausesImpact failureRequest) throws JSONException {	
		
		logger.info("Request Parameters = "+failureRequest.toString());
		try {			
			logger.info("Calling service with request parameters.");
			FailureActionsCausesImpact failureImpact = failureImpactService.saveActions(failureRequest);
			failureRequest.setSeqId(failureImpact.getId().toString());
			failureRequest.setFailureSeqId(failureRequest.getFailureSeqId());
			failureImpactService.saveActions(failureRequest);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus(" Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Failure Type "+" data. "+npe.getMessage());
			return Helper.findResponseStatus("Failure Type "+" Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Failure Type "+" data. "+e.getMessage());
			return Helper.findResponseStatus("Failure Type "+" Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateActions", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateActions(@RequestBody FailureActionsCausesImpact failureRequest) throws JSONException {	
		
		logger.info("Request Parameters = "+failureRequest.toString());
		try {			
			logger.info("Calling service with request parameters.");
			FailureActionsCausesImpact failureImpact = failureImpactService.saveActions(failureRequest);
			failureRequest.setFailureSeqId(failureRequest.getFailureSeqId());
			failureImpactService.saveActions(failureRequest);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus(" Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Failure Type "+" data. "+npe.getMessage());
			return Helper.findResponseStatus("Failure Type "+" Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Failure Type "+" data. "+e.getMessage());
			return Helper.findResponseStatus("Failure Type "+" Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/deleteActions/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteActionById(@PathVariable Long id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected Actions Id = "+id);
		try {
			failureImpactService.deleteActionById(id);
		return Helper.findResponseStatus("Actions deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		logger.error("ERROR >> While deleting Actions data"+npe.getMessage());
		return Helper.findResponseStatus("Actions Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error("ERROR >> While deleting Actions data"+e.getMessage());
		return Helper.findResponseStatus("Actions Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	@RequestMapping(value = "/ActionsById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<FailureActionsCausesImpact> findActionsById(@PathVariable("id") Long id){
		Optional<FailureActionsCausesImpact> failActions= null;
		try {
			failActions = failureImpactService.findActionsById(id);
			if(failActions.isPresent())
				return new ResponseEntity<FailureActionsCausesImpact>(failActions.get(), HttpStatus.OK);
			else
				return new ResponseEntity<FailureActionsCausesImpact>(failActions.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find Failure Type Details by id");
			return new ResponseEntity<FailureActionsCausesImpact>(failActions.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
