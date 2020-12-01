package com.scr.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.request.UserJurisdictionRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.EnergyBillPayment;
import com.scr.services.UserJurisdictionService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class UserJurisdictionController {
	
	static Logger logger = LogManager.getLogger(UserJurisdictionController.class);
	
	@Autowired
	private UserJurisdictionService userJurisdictionService;
	
	@RequestMapping(value = "/addUserJurisdiction", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveEneBillPayment(@RequestBody UserJurisdictionRequest userJurisdictionRequest){
		logger.info("Enter into save function with below request parameters ");
		logger.info("Request Parameters = "+userJurisdictionRequest.toString());
		try {
			logger.info("Calling service with request parameters.");
			 userJurisdictionService.saveUserJurisdiction(userJurisdictionRequest);
			return Helper.findResponseStatus("User Jurisdiction added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding User Jurisdiction  data. "+npe.getMessage());
			return Helper.findResponseStatus("User Jurisdiction add is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding User Jurisdiction data. "+e.getMessage());
			return Helper.findResponseStatus("User Jurisdiction add is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}

}
