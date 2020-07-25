package com.scr.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.EnergyConsumptionResponse;
import com.scr.services.EnergyConsumptionService;

@RestController
@RequestMapping("/scr/api")
public class EnergyConsumptionController{
	static Logger logger = LogManager.getLogger(EnergyConsumptionController.class);
	
	@Autowired
	private EnergyConsumptionService service;
	
	@RequestMapping(value = "/energyConsumption/{fromDate}/{toDate}/{feederId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<EnergyConsumptionResponse>> findEnergyConsumption(
				@PathVariable("fromDate") String fromDate,
				@PathVariable("toDate") String toDate,
				@PathVariable("feederId") String feederId) throws JSONException {
		logger.info("Enter into energyConsumption function");
		logger.info("from date = "+fromDate +" todate = "+toDate+" feederId = "+feederId);
		List<EnergyConsumptionResponse> usersList = null;
		try {			
			logger.info("Calling service for energyConsumption data");
			usersList = service.findEnergyConsumption(fromDate, toDate, feederId);	
			logger.info("Fetched energyConsumption data = "+usersList);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the energyConsumption data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the energyConsumption data = "+e.getMessage());
		}
		logger.info("Exit from energyConsumption function");
		return ResponseEntity.ok((usersList));
	}
	
	
}