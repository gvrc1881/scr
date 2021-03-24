package com.scr.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.EnergyConsumptionResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Division;
import com.scr.model.Zone;
import com.scr.services.EnergyConsumptionService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@RestController
@RequestMapping("/scr/api")
public class EnergyConsumptionController{
	static Logger logger = LogManager.getLogger(EnergyConsumptionController.class);
	
	@Autowired
	private EnergyConsumptionService service;
	
	@RequestMapping(value = "/energyConsumption/{fromDate}/{toDate}/{feederId}/{division}/{psiDepot}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<EnergyConsumptionResponse>> findEnergyConsumption(
				@PathVariable("fromDate") String fromDate,
				@PathVariable("toDate") String toDate,
				@PathVariable("feederId") String feederId,
				@PathVariable("division") String division,
				@PathVariable("psiDepot") String psiDepot) throws JSONException {
		logger.info("Enter into Energy Consumption function");
		logger.info("from date = "+fromDate +" todate = "+toDate+" feederId = "+feederId+" Division = "+division+" PSI Depot ="+psiDepot);
		List<EnergyConsumptionResponse> usersList = null;
		try {			
			logger.info("Calling service for Energy Consumption data");
			if ("undefined".equals(psiDepot)) {
				psiDepot = null;
			}
			usersList = service.findEnergyConsumption(fromDate, toDate, feederId, division,psiDepot);	
			logger.info("Fetched Energy Consumption data = "+usersList);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the Energy Consumption data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the Energy Consumption data = "+e.getMessage());
		}
		logger.info("Exit from Energy Consumption function");
		return ResponseEntity.ok((usersList));
	}
	
	@RequestMapping(value = "/updateEnergyConsumption" ,method = RequestMethod.PUT , headers= "accept=application/json")
	public ResponseStatus updateEnergyConsumption(@RequestBody EnergyConsumptionResponse request){
		logger.info("Enter into updateEnergyConsumption method with below request parameters ");
		logger.info("Request Parameters = "+request.toString());
		try {			
			logger.info("Calling service with request parameters.");
			service.saveEnergyConsumption(request);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("EnergyConsumption Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding EnergyConsumption data. "+npe.getMessage());
			return Helper.findResponseStatus("EnergyConsumption Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding EnergyConsumption data. "+e.getMessage());
			return Helper.findResponseStatus("EnergyConsumption Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
		
	}
	
	
	
	
}