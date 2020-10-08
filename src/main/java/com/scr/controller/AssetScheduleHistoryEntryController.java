package com.scr.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.request.ASHEntryRequest;
import com.scr.message.request.AssetsScheduleHistoryRequest;
import com.scr.message.response.ASHMeasuresActvities;
import com.scr.message.response.ResponseStatus;
import com.scr.services.AssetScheduleHistoryEntryService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class AssetScheduleHistoryEntryController {
	static Logger logger = LogManager.getLogger(AssetScheduleHistoryEntryController.class);
	
	@Autowired
	private AssetScheduleHistoryEntryService service;
	
	
	@RequestMapping(value = "/measure/{assetType}/{scheduleCode}/{make}/{model}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<ASHMeasuresActvities>> findMeasures(@PathVariable("assetType") String assetType ,@PathVariable("scheduleCode") String scheduleCode ,@PathVariable("make") String make,@PathVariable("model") String model) throws JSONException {
		logger.info("Enter into findMeasures function");
		List<ASHMeasuresActvities> measures = null;
		try {			
			logger.info("Calling service for measures");
			measures = service.findMeasuresOrActivities("measurement",assetType,scheduleCode,make,model);	
			logger.info("Fetched measures data = "+measures);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the measures data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the measures data = "+e.getMessage());
		}
		logger.info("Exit from findMeasures function");
		return ResponseEntity.ok((measures));
	}
	@RequestMapping(value = "/activity/{assetType}/{scheduleCode}/{make}/{model}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<ASHMeasuresActvities>> findActivities(@PathVariable("assetType") String assetType ,@PathVariable("scheduleCode") String scheduleCode ,@PathVariable("make") String make,@PathVariable("model") String model) throws JSONException {
		logger.info("Enter into findActivities function");
		List<ASHMeasuresActvities> activities = null;
		try {			
			logger.info("Calling service for measures");
			activities = service.findMeasuresOrActivities("activity",assetType,scheduleCode,make,model);	
			logger.info("Fetched activities data = "+activities);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the activities data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the activities data = "+e.getMessage());
		}
		logger.info("Exit from findActivities function");
		return ResponseEntity.ok((activities));
	}
	
	@RequestMapping(value = "/saveEntry", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveEntry(@Valid @RequestBody ASHEntryRequest ashEntryRequest) throws JSONException {	
		logger.info("Enter into saveEntry function with below request parameters ");
		logger.info("Request Parameters = "+ashEntryRequest.toString());
		try {
			logger.info("Calling service with request parameters.");
			
			service.saveEntry(ashEntryRequest);
			
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("ASh saveEntry Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding ash data. "+npe.getMessage());
			return Helper.findResponseStatus("ASH saveEntry Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding saveEntry data. "+e.getMessage());
			return Helper.findResponseStatus("Ash Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	
		
	
}
