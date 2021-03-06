package com.scr.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.model.StatusItem;
import com.scr.services.CommonUtilService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class CommonUtilController {
static Logger logger = LogManager.getLogger(CommonUtilController.class);
	
	@Autowired
	private CommonUtilService service;
	
	@RequestMapping(value = "/statusItem/{statusTypeId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<StatusItem>> findStatusItem(@PathVariable("statusTypeId") String statusTypeId) throws JSONException {
		List<StatusItem> usersList = null;
		try {			
			usersList = service.findStatusItem(statusTypeId);			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((usersList));
	}
	
	@RequestMapping(value = "/getMultipleStatusItems/{statusTypeId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<StatusItem>> findMultipuleStatusItems(@PathVariable("statusTypeId") String statusTypeId) throws JSONException {
		List<StatusItem> usersList = null;
		logger.info("*** multiple status types ***"+statusTypeId);
		String[] statusTypeArray = statusTypeId.split(",");
		List<String> statusTypeList = new ArrayList<String>();
		for (String statusType : statusTypeArray) {
			statusTypeList.add(statusType);
		}
		try {			
			usersList = service.findMutipleStatusItems(statusTypeList);	
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((usersList));
	}
}
