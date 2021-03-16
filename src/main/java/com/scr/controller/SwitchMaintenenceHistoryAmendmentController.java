package com.scr.controller;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.PowerBlocksAmendment;
import com.scr.model.SwitchMaintenenceHistoryAmendment;
import com.scr.services.SwitchMaintenenceHistoryAmendmentService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SwitchMaintenenceHistoryAmendmentController {
	
	static Logger logger = LogManager.getLogger(SwitchMaintenenceHistoryAmendmentController.class);
	
	@Autowired
	private SwitchMaintenenceHistoryAmendmentService switchMaintenenceHistoryAmendmentService;
	
	
	@RequestMapping(value = "/getSwitchAmendmentBasedOnSMHId/{switchId}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<SwitchMaintenenceHistoryAmendment> findById(@PathVariable("switchId") String switchId){
		Optional<SwitchMaintenenceHistoryAmendment> switchMaintenenceHistoryAmendment = null;
		try {
			logger.info("Selected switch maintenence history amendment  Id = "+switchId);
			switchMaintenenceHistoryAmendment = switchMaintenenceHistoryAmendmentService.findBySeqId(switchId);
			if(switchMaintenenceHistoryAmendment.isPresent()) {
				logger.info("switch maintenence history amendment Data = "+switchMaintenenceHistoryAmendment.get());
				return new ResponseEntity<SwitchMaintenenceHistoryAmendment>(switchMaintenenceHistoryAmendment.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<SwitchMaintenenceHistoryAmendment>(switchMaintenenceHistoryAmendment.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error >>  while find switch maintenence history amendment Details by id, "+e.getMessage());
			return new ResponseEntity<SwitchMaintenenceHistoryAmendment>(switchMaintenenceHistoryAmendment.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/saveSMHAmendment", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveSwitchMaintenenceHistoryAmendment(@RequestBody SwitchMaintenenceHistoryAmendment switchMaintenenceHistoryAmendment){
		logger.info("Enter into saveSwitchMaintenenceHistoryAmendment function with below request parameters ");
		logger.info("Request Parameters = "+switchMaintenenceHistoryAmendment.toString());
		try {
			logger.info("Calling service with request parameters.");
			switchMaintenenceHistoryAmendmentService.save(switchMaintenenceHistoryAmendment);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("switch maintenence history amendment added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding switch maintenence history amendment data. "+npe.getMessage());
			return Helper.findResponseStatus("switch maintenence history amendment save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding switch maintenence history amendment data. "+e.getMessage());
			return Helper.findResponseStatus("switch maintenence history amendment save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/updateSwitchMaintenenceHistoryAmendment" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateSwitchMaintenenceHistoryAmendment(@RequestBody SwitchMaintenenceHistoryAmendment switchMaintenenceHistoryAmendment) {
		logger.info("Enter into updateSwitchMaintenenceHistoryAmendment function with below request parameters ");
		logger.info("Request Parameters = "+switchMaintenenceHistoryAmendment.toString());
		try {
			logger.info("Calling service with request parameters.");
			switchMaintenenceHistoryAmendmentService.save(switchMaintenenceHistoryAmendment);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("switch maintenence history amendment updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating switch maintenence history amendment data. "+npe.getMessage());
			return Helper.findResponseStatus("switch maintenence history amendment update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating switch maintenence history amendment data. "+e.getMessage());
			return Helper.findResponseStatus("switch maintenence history amendment update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	

}
