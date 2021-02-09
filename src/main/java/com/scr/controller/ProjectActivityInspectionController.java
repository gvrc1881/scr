package com.scr.controller;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.request.ProjectActivityInspectionRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.services.ProjectActivityInspectionService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ProjectActivityInspectionController {
	
	static Logger logger = LogManager.getLogger(ProjectActivityInspectionController.class);
	
	@Autowired
	private ProjectActivityInspectionService projectActivityInsSerivce;
	
	@RequestMapping(value = "/saveProjActIns", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveEntry(@Valid @RequestBody ProjectActivityInspectionRequest projectActivityInspectionRequest) throws JSONException {	
		logger.info("Enter into saveEntry function with below request parameters ");
		logger.info("Request Parameters = "+projectActivityInspectionRequest.toString());
		try {
			logger.info("Calling service with request parameters.");
			
			projectActivityInsSerivce.saveEntry(projectActivityInspectionRequest);
			
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("PAI saveEntry Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding PAI data. "+npe.getMessage());
			return Helper.findResponseStatus("PAI saveEntry Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding saveEntry data. "+e.getMessage());
			return Helper.findResponseStatus("PAI Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}

}
