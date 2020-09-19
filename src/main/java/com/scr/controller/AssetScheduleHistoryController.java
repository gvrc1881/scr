package com.scr.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.request.AssetsScheduleHistoryRequest;
import com.scr.message.response.AssetsScheduleHistoryResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.AssetsScheduleHistory;
import com.scr.services.AssetScheduleHistoryService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class AssetScheduleHistoryController {
	static Logger logger = LogManager.getLogger(AssetScheduleHistoryController.class);
	
	@Autowired
	private AssetScheduleHistoryService service;
	
	@RequestMapping(value = "/ashistory", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<AssetsScheduleHistory>> findAll() throws JSONException {
		logger.info("Enter into findAllash function");
		List<AssetsScheduleHistory> histories = null;
		try {			
			logger.info("Calling service for ASH data");
			histories = service.findAllAshs();	
			logger.info("Fetched ash data = "+histories);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the ash data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the ash data = "+e.getMessage());
		}
		logger.info("Exit from findAllash function");
		return ResponseEntity.ok((histories));
	}
	
	@RequestMapping(value = "/ashistoryWithDepo", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<AssetsScheduleHistoryResponse>> findAshWithFacilityName() throws JSONException {
		logger.info("Enter into findAshWithFacilityName function");
		List<AssetsScheduleHistoryResponse> histories = null;
		try {			
			logger.info("Calling service for ASH data");
			histories = service.findAshWithFacilityName();	
			logger.info("Fetched ash data = "+histories);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the ash data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the ash data = "+e.getMessage());
		}
		logger.info("Exit from findAshWithFacilityName function");
		return ResponseEntity.ok((histories));
	}
	@RequestMapping(value = "/ashistory/{fromDate}/{toDate}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<AssetsScheduleHistoryResponse>> findAshDateRange() throws JSONException {
		logger.info("Enter into findAshWithFacilityName function");
		List<AssetsScheduleHistoryResponse> histories = null;
		try {			
			logger.info("Calling service for ASH data");
			histories = service.findAshWithFacilityName();	
			logger.info("Fetched ash data = "+histories);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the ash data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the ash data = "+e.getMessage());
		}
		logger.info("Exit from findAshWithFacilityName function");
		return ResponseEntity.ok((histories));
	}
	
	@RequestMapping(value = "/saveAsh", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveAshData(@Valid @RequestBody AssetsScheduleHistoryRequest ashRequest) throws JSONException {	
		logger.info("Enter into saveAshData function with below request parameters ");
		logger.info("Request Parameters = "+ashRequest.toString());
		try {
			logger.info("Calling service with request parameters.");
			String inputStr = ashRequest.getAssetId();
			JSONArray array= new JSONArray(inputStr);
			//JSONObject json=array.getJSONObject(0);
			for(Object obj:array) {
				String str=obj.toString();
			//if (assetIds != null && !assetIds.isEmpty()) {
				String[] assets = str.split("_");
					ashRequest.setAssetId(assets[0]);
					ashRequest.setAssetType(assets[1]);
					service.saveAshData(ashRequest);
			}
			
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Ash Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding ash data. "+npe.getMessage());
			return Helper.findResponseStatus("Ash Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding ash data. "+e.getMessage());
			return Helper.findResponseStatus("Ash Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	/*@RequestMapping(value = "/updateAsh", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateAshData(@Valid @RequestBody AssetsScheduleHistoryRequest ashRequest) throws JSONException {		
		try {			
			String status = service.updateAshData(ashRequest);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Ash Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating drive data. "+e.getMessage());
			return Helper.findResponseStatus("Ash Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}*/
	
	@RequestMapping(value = "/deleteAsh/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteAsh(@PathVariable("id") Long id) throws JSONException {
		try {
			String status = service.deleteAsh(id);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Ash Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Ash Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Ash Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
		
	
}
