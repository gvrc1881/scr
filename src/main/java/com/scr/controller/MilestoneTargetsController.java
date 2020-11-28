package com.scr.controller;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.scr.model.MilestoneTargets;
import com.scr.model.WorkPhases;
import com.scr.model.Works;
import com.scr.services.MilestoneTargetsService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")



public class MilestoneTargetsController {
	
	private Logger log = Logger.getLogger(MilestoneTargetsController.class);
	
	@Autowired
	private MilestoneTargetsService milestoneTargetsSerivce;
	
	

	@RequestMapping(value = "/addMilestoneTargets", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus save(@RequestBody MilestoneTargets targets){
		log.info("Enter into save function with below request parameters ");
		log.info("Request Parameters = "+targets.toString());		
		try {
			log.info("Calling service with request parameters.");
			milestoneTargetsSerivce.save(targets);
				log.info("Preparing the return response");
				return Helper.findResponseStatus("Targets added successfully", Constants.SUCCESS_CODE);
			}catch(NullPointerException npe) {
				log.error("ERROR >> While adding Targets data. "+npe.getMessage());
				return Helper.findResponseStatus("Targets save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
			}
			catch (Exception e) {
				log.error("ERROR >> While adding Targets data. "+e.getMessage());
				return Helper.findResponseStatus("Targets save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
	}
	
	@RequestMapping(value = "/updateMilestoneTargets" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateMilestoneTargets(@RequestBody MilestoneTargets targets) {
		log.info("Enter into update function with below request parameters ");
		log.info("Request Parameters = "+targets.toString());
		try {
			log.info("Calling service with request parameters.");
		
			milestoneTargetsSerivce.save(targets);
			log.info("Preparing the return response");
		return Helper.findResponseStatus("Targets updated successfully", Constants.SUCCESS_CODE);
	}catch(NullPointerException npe) {
		log.error("ERROR >> While updating Targets data. "+npe.getMessage());
		return Helper.findResponseStatus("Targets update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
	}
	catch (Exception e) {
		log.error("ERROR >> While updating Targets data. "+e.getMessage());
		return Helper.findResponseStatus("Targets update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
	}
		}
	
	
	 @RequestMapping(value = "/findAllMilestoneTargets" , method = RequestMethod.GET , headers = "Accept=application/json")
		public List<MilestoneTargets> findAll() throws JSONException {
			 List<MilestoneTargets> targetsList = null;
			 try {
				   log.info("Calling service for  Targets data");	
			
				   targetsList = milestoneTargetsSerivce.findAll();
			 log.info("Fetched Targets data***"+targetsList.size());
			
			return targetsList;
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the Targets data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the Targets data = "+e.getMessage());
		}
			 log.info("Exit from Targets function");
		return targetsList;	
	}
	 
	 @RequestMapping(value = "/findByMilestoneTargetsById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
		public ResponseEntity<MilestoneTargets> findByMilestoneTargetsById(@PathVariable("id") Integer id){
			Optional<MilestoneTargets> target = null;
			try {
				log.info("Selected MilestoneTargets Id = "+id);
				target = milestoneTargetsSerivce.findById(id);
				if(target.isPresent()) {
					log.info("MilestoneTargets Data = "+target.get());
					return new ResponseEntity<MilestoneTargets>(target.get(), HttpStatus.OK);
				}
				else
					return new ResponseEntity<MilestoneTargets>(target.get(), HttpStatus.CONFLICT);
			} catch (Exception e) {
				log.error("Error >>  while find MilestoneTargets Details by id, "+e.getMessage());
				return new ResponseEntity<MilestoneTargets>(target.get(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/deleteMilestoneTargets/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
		public ResponseStatus deleteMilestoneTargets(@PathVariable Integer id) {
			log.info("Enter into deleteById function");
			log.info("Selected Targets Id = "+id);
			try {
				milestoneTargetsSerivce.deleteById(id);
			return Helper.findResponseStatus("Targets deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Targets data"+npe.getMessage());
			return Helper.findResponseStatus("Targets Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Targets data"+e.getMessage());
			return Helper.findResponseStatus("Targets Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

}
