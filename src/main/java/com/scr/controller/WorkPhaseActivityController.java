package com.scr.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.scr.message.response.ResponseStatus;
import com.scr.model.StandardPhaseActivity;
import com.scr.model.StandardPhases;
import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
import com.scr.services.WorkPhaseActivityService;
import com.scr.services.WorkPhaseSerivce;
import com.scr.util.Constants;
import com.scr.util.Helper;


@RestController
@RequestMapping("/scr/api")

public class WorkPhaseActivityController {
	

	
private Logger log = Logger.getLogger(WorkPhaseController.class);
	
	@Autowired
	private WorkPhaseSerivce workPhaseServices;
	
	@Autowired
	private WorkPhaseActivityService workPhaseActivityService;
	
	@CrossOrigin(origins = "*")
	
	/*	@RequestMapping(value = "/findByPhaseActivityOnPhaseId/{workPhaseId}" , method = RequestMethod.GET , headers = "Accept=application/json")
	

	public List<WorkPhaseActivity> findByPhaseActivityOnPhaseId(@PathVariable("workPhaseId") Integer workPhaseId){
		log.info("Enter into findByPhaseActivityOnPhaseId function ");
		List<WorkPhaseActivity> workActivity = null;
		log.info("list==="+workActivity);
		try {
			Optional<WorkPhases> workPhases = workPhaseServices.findById(workPhaseId);
			if (workPhases.isPresent()) {
				workActivity = workPhaseActivityService.getWorkPhaseActivityBasedOnWorkPhaseId(workPhases.get());
			}
			return workActivity;
		} catch (Exception e) {
			log.error("Error >>  while find work Phase Activity Details by work phase id, "+e.getMessage());
		}
		return workActivity;
	}*/
	
	@RequestMapping(value = "/findByPhaseActivityOnPhaseId/{workPhaseId}", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<WorkPhaseActivity>> findByPhaseActivityOnPhaseId(@PathVariable("workPhaseId") String workPhases){
		log.info("** Enter into findByPhaseActivityOnPhaseId  functions ***"+workPhases);
	
		List<WorkPhaseActivity> workAct=null;		
		
		List<WorkPhases> workPhaseList = new ArrayList<>();
		
		log.info("list==="+workAct);		
			String phaseIds[]=workPhases.split(",");			
			
			for(String workPhaseId:phaseIds)
			{
				Optional<WorkPhases> workPhase = workPhaseServices.findById(Integer.parseInt(workPhaseId));							
			
			if (workPhase.isPresent()) 
				workPhaseList.add(workPhase.get());
				
			}
			workAct = workPhaseActivityService.getWorkPhaseActivityBasedOnWorkPhaseIdIn(workPhaseList);
			return new ResponseEntity<List<WorkPhaseActivity>>(workAct,HttpStatus.OK);		

	}
	
	@RequestMapping(value = "/addPhaseActivity", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus save(@RequestBody WorkPhaseActivity workPhaseActivity){
		log.info("Enter into save function with below request parameters ");
		log.info("Request Parameters = "+workPhaseActivity.toString());		
		try {
			log.info("Calling service with request parameters.");
			workPhaseActivityService.save(workPhaseActivity);
				log.info("Preparing the return response");
				return Helper.findResponseStatus("Project phaseActivity added successfully", Constants.SUCCESS_CODE);
			}catch(NullPointerException npe) {
				log.error("ERROR >> While adding Project phaseActivity data. "+npe.getMessage());
				return Helper.findResponseStatus("Project phaseActivity save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
			}
			catch (Exception e) {
				log.error("ERROR >> While adding Project phaseActivity data. "+e.getMessage());
				return Helper.findResponseStatus("Project phaseActivity save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
	}
	
	@PostMapping(value="/updatePhaseActivity")
	@ResponseBody
	public ResponseStatus updatePhaseActivity(@RequestBody List<WorkPhaseActivity> workPhaseActivity) {
		log.info("*** Enter into updateWorkPhaseActivity function ***");
		try {			
			workPhaseActivityService.updateWorkPhaseActivity(workPhaseActivity);
			log.info("Preparing the return response and updateWorkPhaseActivity function end ");
			return Helper.findResponseStatus("Project phase Activities Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Project phases Data. "+npe.getMessage());
			return Helper.findResponseStatus("Project phase Activities Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Project phase Activities Data. "+e.getMessage());
			return Helper.findResponseStatus("Project phase Activities  Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deletePhaseActivity/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteGroupsSectionsById(@PathVariable Integer id) {
		log.info("Enter into deleteById function");
		log.info("Selected Phase Activity Id = "+id);
		try {
			workPhaseActivityService.deleteById(id);
		return Helper.findResponseStatus("Phase Activity deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		log.error("ERROR >> While deleting Phase Activity data"+npe.getMessage());
		return Helper.findResponseStatus("Phase Activity Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		log.error("ERROR >> While deleting Phase Activity data"+e.getMessage());
		return Helper.findResponseStatus("Phase Activity Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	
	@RequestMapping(value = "/existsByWorkPhaseIdAndName/{workPhase}/{name}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsByWorkPhaseIdAndName(@PathVariable("workPhase") Integer workPhase ,@PathVariable("name") String name){
		log.info("Exist====="+"workPhase=="+workPhase+"name==="+name);
		try {
			log.info("Request for checking exists workphase and activity name ...");
			return workPhaseActivityService.existsByWorkPhaseIdAndName(workPhaseServices.findById(workPhase).get(),name);
		} catch (Exception e) {
			log.error("Error while checking exists  workPhase and  name..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existsByWorkPhaseIdAndSequence/{workPhase}/{sequence}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsByWorkPhaseIdAndSequence(@PathVariable("workPhase") Integer workPhase ,@PathVariable("sequence") BigDecimal sequence){
		log.info("Exist====="+"workPhase=="+workPhase+"sequence==="+sequence);
		try {
			log.info("Request for checking exists work and sequence ...");
			return workPhaseActivityService.existsByWorkPhaseIdAndSequence(workPhaseServices.findById(workPhase).get(),sequence);
		} catch (Exception e) {
			log.error("Error while checking exists  work and sequence..."+e.getMessage());
			return false;
		}
	}

}
