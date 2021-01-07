package com.scr.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.scr.message.response.ThermovisionMeasureResponse;
import com.scr.model.WPADailyProgress;
import com.scr.services.ThermovisionMeasuresServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ThermovisionMeasuresController {
	
	private Logger logger = Logger.getLogger(ThermovisionMeasuresController.class);
	
	@Autowired
	private ThermovisionMeasuresServices thermovisionMeasuresServices;
	
	@RequestMapping(value = "/getThermovisionMeasures/{date}/{facilityId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<ThermovisionMeasureResponse>> findThermovisionMeasure(
				@PathVariable("date") String fromDate,
				@PathVariable("facilityId") String division) throws JSONException {
		logger.info("Enter into Thermovision Measure function");
		logger.info("from date = "+fromDate +" Division = "+division);
		List<ThermovisionMeasureResponse> thermovisionMeasureList = null;
		try {			
			logger.info("Calling service for Thermovision Measure data");
			thermovisionMeasureList = thermovisionMeasuresServices.findThermovisionMeasure(fromDate, division);	
			logger.info("Fetched Thermovision Measure data = "+thermovisionMeasureList);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the Thermovision Measure data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the Thermovision Measure data = "+e.getMessage());
		}
		logger.info("Exit from Thermovision Measure function");
		return ResponseEntity.ok((thermovisionMeasureList));
	}
	
	
	@PostMapping(value="/saveThermovisionMeasures")
	@ResponseBody
	public ResponseStatus saveThermovisionMeasures(@RequestBody List<ThermovisionMeasureResponse> thermovisionMeasureResponse) {
		logger.info("*** Enter into saveThermovisionMeasures function ***");
		try {			
			thermovisionMeasuresServices.saveThermovisionMeasures(thermovisionMeasureResponse);
			logger.info("Preparing the return response and saveThermovisionMeasures function end ");
			return Helper.findResponseStatus("Thermovision Measure Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Thermovision Measure Data. "+npe.getMessage());
			return Helper.findResponseStatus("Thermovision Measure Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Thermovision Measure Data. "+e.getMessage());
			return Helper.findResponseStatus("Thermovision Measure  Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	

}
