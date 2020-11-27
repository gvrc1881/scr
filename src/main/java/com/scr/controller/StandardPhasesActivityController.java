package com.scr.controller;


import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.StandardPhaseActivity;
import com.scr.model.StandardPhases;
import com.scr.services.StandardPhasesActivityService;
import com.scr.util.Constants;
import com.scr.util.Helper;


@RestController
@RequestMapping("/scr/api")
public class StandardPhasesActivityController {
	
	
private Logger logger = Logger.getLogger(StandardPhasesActivityController.class);
	
	@Autowired
	private StandardPhasesActivityService standardPhasesActivityService;
		
	@CrossOrigin(origins = "*")
		
	
	@RequestMapping(value="/findAllStandardPhasesActivity", method=RequestMethod.GET, headers = "Accept=application/json")
	public List<StandardPhaseActivity> findAllStandardPhasesActivity(){
		logger.info("Enter into findAll StandardPhases Activity function");
		List<StandardPhaseActivity> standardPhaseActivity=null;
		try {
			logger.info("calling service for standardPhases Activity Data");
			standardPhaseActivity=standardPhasesActivityService.findAll();
			logger.info("fetched standardPhases Data"+standardPhaseActivity.size());
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the standardPhases Activity data = "+npe.getMessage());
			
		}
		catch (Exception e) {
			logger.error("ERROR >>> while fetching the standardPhases Activity data = "+e.getMessage());
		}
		logger.info("Exit from standard Phases Activity function");
		return standardPhaseActivity;
	}
	@RequestMapping(value="/addStandardPhaseActivity",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addStandardPhaseActivity(@RequestBody StandardPhaseActivity standardPhaseActivity) {
		logger.info("Enter into addStandardPhaseActivity function with below request parameters ");
		logger.info("Request Parameters = "+standardPhaseActivity.toString());
		try {
			logger.info("Calling service with request parameters.");
			standardPhasesActivityService.save(standardPhaseActivity);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Standard Phases Activity Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Standard Phases Activity data. "+npe.getMessage());
			return Helper.findResponseStatus("Standard Phases Activity save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Standard Phases Activity data. "+e.getMessage());
			return Helper.findResponseStatus("Standard Phases Activity save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findStandardPhasesActivityById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<StandardPhaseActivity> findStandardPhasesActivityById(@PathVariable Integer id){
		Optional<StandardPhaseActivity> standardPhaseActivity = null;
		try {
			logger.info("Standard PhaseActivity  Id = "+id);
			standardPhaseActivity = standardPhasesActivityService.findStandardPhaseActivityById(id);
			if(standardPhaseActivity.isPresent()) {
				logger.info("sector Data = "+standardPhaseActivity.get());
				return new ResponseEntity<StandardPhaseActivity>(standardPhaseActivity.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<StandardPhaseActivity>(standardPhaseActivity.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find standard PhaseActivity Details by id, "+e.getMessage());
			return new ResponseEntity<StandardPhaseActivity>(standardPhaseActivity.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateStandardPhasesActivity" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateStandardPhasesActivity(@RequestBody StandardPhaseActivity standardPhaseActivity) {
		logger.info("Enter into update StandardPhasesActivity function with below request parameters ");
		logger.info("Request Parameters = "+standardPhaseActivity.toString());
		try {
			logger.info("Calling service with request parameters.");
			standardPhasesActivityService.save(standardPhaseActivity);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Standard Phases Activity Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating Standard Phases Activity data. "+npe.getMessage());
			return Helper.findResponseStatus("Standard Phases Activity update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating Standard Phases Activity data. "+e.getMessage());
			return Helper.findResponseStatus("Standard Phases Activity update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteStandardPhaseActivity/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteStandardPhaseActivityById(@PathVariable Integer id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected Standard Phase Activity Id = "+id);
		try {
			standardPhasesActivityService.deleteById(id);
		return Helper.findResponseStatus("Standard Phase Activity deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		logger.error("ERROR >> While deleting Standard Phase Activity data"+npe.getMessage());
		return Helper.findResponseStatus("Standard Phase Activity Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error("ERROR >> While deleting Standard Phase Activity data"+e.getMessage());
		return Helper.findResponseStatus("Standard Phase Activity Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	
	
}
