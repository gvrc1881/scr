package com.scr.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.PbSwitchControl;
import com.scr.model.SwitchMaintenenceHistory;
import com.scr.services.SwitchMaintenenceHistoryService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SwitchMaintenenceHistoryController {
	
	static Logger logger = LogManager.getLogger(SwitchMaintenenceHistoryController.class);
	
	@Autowired
	private SwitchMaintenenceHistoryService switchMaintenenceHistoryService;
	
	@RequestMapping(value = "/addSwitchMaintenenceHistory" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addSwitchMaintenenceHistory(@RequestBody SwitchMaintenenceHistory switchMaintenenceHistory) {
		logger.info("Enter into addSwitchMaintenenceHistory function with below request parameters ");
		logger.info("Request Parameters = "+switchMaintenenceHistory.toString());
		try {
			logger.info("Calling service with request parameters.");
			switchMaintenenceHistoryService.save(switchMaintenenceHistory);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Switch Maintenence History added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Switch Maintenence History data. "+npe.getMessage());
			return Helper.findResponseStatus("Switch Maintenence History save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Switch Maintenence History . "+e.getMessage());
			return Helper.findResponseStatus("Switch Maintenence History save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}

}
