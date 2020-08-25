package com.scr.controller;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
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
import com.scr.model.AssetsScheduleHistory;
import com.scr.model.PowerBlock;
import com.scr.model.PowerBlock;
import com.scr.services.PowerBlockService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class PowerBlockController {
	
	static Logger logger = LogManager.getLogger(PowerBlockController.class);
	
	
	@Autowired
	private PowerBlockService powerBlockService;
	
	
	@RequestMapping(value = "/findAllPowerBlock", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<PowerBlock> findAllPowerBlock() throws JSONException {
		logger.info("Enter into findAllPowerBlock function");
		List<PowerBlock> powerBlockList = null;
		try {
			logger.info("Calling service for power blocks data");
			powerBlockList = powerBlockService.findAll();
			logger.info("Fetched power blocks data = "+powerBlockList.size());
			return powerBlockList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the power blocks data = "+npe.getMessage());
		}catch (Exception e) {
			logger.error("ERROR >>> while fetching the power blocks data = "+e.getMessage());
		}
		logger.info("Exit from findAllPowerBlock function");
		return powerBlockList;	
	}
	
	@RequestMapping(value = "/addPowerBlock", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus savePowerBlock(@RequestBody PowerBlock powerBlock){
		logger.info("Enter into savePowerBlock function with below request parameters ");
		logger.info("Request Parameters = "+powerBlock.toString());
		try {
			logger.info("Calling service with request parameters.");
			powerBlockService.save(powerBlock);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Power block added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding power block data. "+npe.getMessage());
			return Helper.findResponseStatus("Power block save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding power block data. "+e.getMessage());
			return Helper.findResponseStatus("Power blocks save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findPowerBlock/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<PowerBlock> findById(@PathVariable("id") Long id){
		Optional<PowerBlock> eleMeter = null;
		try {
			logger.info("Selected power block Id = "+id);
			eleMeter = powerBlockService.findById(id);
			if(eleMeter.isPresent()) {
				logger.info("Power block Data = "+eleMeter.get());
				return new ResponseEntity<PowerBlock>(eleMeter.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<PowerBlock>(eleMeter.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error >>  while find Power Block Details by id, "+e.getMessage());
			return new ResponseEntity<PowerBlock>(eleMeter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updatePowerBlock" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updatePowerBlock(@RequestBody PowerBlock powerBlock) {
		logger.info("Enter into updatePowerBlock function with below request parameters ");
		logger.info("Request Parameters = "+powerBlock.toString());
		try {
			logger.info("Calling service with request parameters.");
			powerBlockService.save(powerBlock);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Power block updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating Power block data. "+npe.getMessage());
			return Helper.findResponseStatus("Power block update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating power block data. "+e.getMessage());
			return Helper.findResponseStatus("Power blocks update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deletePowerBlock/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deletePowerBlock(@PathVariable Long id) {
		logger.info("Enter into deletePowerBlock function");
		logger.info("Selected Power block Id = "+id);
		try {
			powerBlockService.deleteById(id);
			return Helper.findResponseStatus("Power block Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting power block data"+npe.getMessage());
			return Helper.findResponseStatus("Power block Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting power block data"+e.getMessage());
			return Helper.findResponseStatus("Power block Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	@RequestMapping(value = "/getPowerBlocksBasedOnFacilityIdAndCreatedDate" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<PowerBlock>> getPowerBlocksBasedOnFacilityIdAndCreatedDate(@RequestBody PowerBlock powerBlockObj){
		List<PowerBlock> powerBlocksList = powerBlockService.findPowerBlocks( powerBlockObj.getFacilityId(), powerBlockObj.getCreatedDate());
		logger.info("powerBlocksListSize"+powerBlocksList.size());
		logger.info("powerBlocksList"+powerBlocksList);
		return new ResponseEntity<List<PowerBlock>>(powerBlocksList,HttpStatus.OK);
		
	}

}
