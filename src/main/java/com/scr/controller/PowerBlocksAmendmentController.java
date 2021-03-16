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
import com.scr.model.PowerBlock;
import com.scr.model.PowerBlocksAmendment;
import com.scr.services.PowerBlocksAmendmentServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class PowerBlocksAmendmentController {
	
	static Logger logger = LogManager.getLogger(PowerBlocksAmendmentController.class);
	
	@Autowired
	private PowerBlocksAmendmentServices powerBlocksAmendmentServices;
	
	
	@RequestMapping(value = "/getPBAmendmentBasedOnPBOperationId/{pbOperationId}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<PowerBlocksAmendment> findById(@PathVariable("pbOperationId") String pbOperationId){
		Optional<PowerBlocksAmendment> powerBlockAmendment = null;
		try {
			logger.info("Selected power blocks Amendment Id = "+pbOperationId);
			powerBlockAmendment = powerBlocksAmendmentServices.findByPbOperationSeqId(pbOperationId);
			if(powerBlockAmendment.isPresent()) {
				logger.info("Power blocks Amendment Data = "+powerBlockAmendment.get());
				return new ResponseEntity<PowerBlocksAmendment>(powerBlockAmendment.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<PowerBlocksAmendment>(powerBlockAmendment.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error >>  while find Power Blocks Amendment Details by id, "+e.getMessage());
			return new ResponseEntity<PowerBlocksAmendment>(powerBlockAmendment.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/savePBAmendment", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus savePowerBlockAmendment(@RequestBody PowerBlocksAmendment powerBlocksAmendment){
		logger.info("Enter into savePowerBlockAmendment function with below request parameters ");
		logger.info("Request Parameters = "+powerBlocksAmendment.toString());
		try {
			logger.info("Calling service with request parameters.");
			powerBlocksAmendmentServices.save(powerBlocksAmendment);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Power blocks Amendment added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding power blocks Amendment data. "+npe.getMessage());
			return Helper.findResponseStatus("Power blocks Amendment save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding power blocks Amendment    data. "+e.getMessage());
			return Helper.findResponseStatus("Power blocks Amendment save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/updatePowerBlockAmendment" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updatePowerBlockAmendment(@RequestBody PowerBlocksAmendment powerBlocksAmendment) {
		logger.info("Enter into updatePowerBlockAmendment function with below request parameters ");
		logger.info("Request Parameters = "+powerBlocksAmendment.toString());
		try {
			logger.info("Calling service with request parameters.");
			powerBlocksAmendmentServices.save(powerBlocksAmendment);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Power blocks amendment updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating Power block amendment data. "+npe.getMessage());
			return Helper.findResponseStatus("Power block amendment update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating power block amendment data. "+e.getMessage());
			return Helper.findResponseStatus("Power blocks amendment update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}

}
