package com.scr.controller;


import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;

import com.scr.model.AssetScheduleAssoc;
import com.scr.services.AssetScheduleAssocService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class AssetScheduleAssocController {
	
	private static Logger logger = Logger.getLogger(AssetScheduleAssocController.class);
	
	@Autowired 
	AssetScheduleAssocService assetScheduleAssocService;
	
	@RequestMapping(value = "/addAssetSchAssoc" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addAssetSchAssoc(@RequestBody AssetScheduleAssoc assetScheduleAssoc) {
		
		logger.info("Enter into save function with below request parameters ");
		
		logger.info("Request Parameters = "+assetScheduleAssoc.toString());
		
	
		try {
			logger.info("Calling service with request parameters.");
			assetScheduleAssocService.save(assetScheduleAssoc);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("AssetScheAssoc added successfully", Constants.SUCCESS_CODE);
		}
		
		catch(NullPointerException npe) {
			logger.error("ERROR >> While adding AssetScheAssoc data. "+npe.getMessage());
			return Helper.findResponseStatus("AssetScheAssoc save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding AssetScheAssoc data. "+e.getMessage());
			return Helper.findResponseStatus("AssetScheAssoc save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateAssetSchAssoc" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateAssetSchAssoc(@RequestBody AssetScheduleAssoc assetScheduleAssoc) {
		logger.info("Enter into update function with below request parameters ");
		logger.info("Request Parameters = "+assetScheduleAssoc.toString());
		try {
			logger.info("Calling service with request parameters.");
			assetScheduleAssocService.save(assetScheduleAssoc);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("AssetScheAssoc updated successfully", Constants.SUCCESS_CODE);
	}catch(NullPointerException npe) {
		logger.error("ERROR >> While updating AssetScheAssoc data. "+npe.getMessage());
		return Helper.findResponseStatus("AssetScheAssoc update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
	}
	catch (Exception e) {
		logger.error("ERROR >> While updating AssetScheAssoc data. "+e.getMessage());
		return Helper.findResponseStatus("AssetScheAssoc update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
	}
		}
	

	@RequestMapping(value = "/deleteAssetSchAssoc/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteAssetScheduleAssocById(@PathVariable Long id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected AssetScheAssoc Id = "+id);
		try {
			assetScheduleAssocService.deleteAssetScheduleAssocById(id);
		return Helper.findResponseStatus("AssetScheAssoc deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		logger.error("ERROR >> While deleting AssetScheAssoc data"+npe.getMessage());
		return Helper.findResponseStatus("AssetScheAssoc Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error("ERROR >> While deleting AssetScheAssoc data"+e.getMessage());
		return Helper.findResponseStatus("AssetScheAssoc Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	 @RequestMapping(value = "/findAllAssetSchAssoc" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<AssetScheduleAssoc> findAllAssetSchAssoc() throws JSONException {
		 List<AssetScheduleAssoc> assetList = null;
		 try {
			   logger.info("Calling service for make data");	
		
			   assetList = assetScheduleAssocService.findAll();
		 logger.info("Fetched AssetScheAssoc data***"+assetList.size());
		return assetList;
	}catch (NullPointerException npe) {
		logger.error("ERROR >>> while fetching the AssetScheAssoc data = "+npe.getMessage());
	}
	catch (Exception e) {
		logger.error("ERROR >>> while fetching the AssetScheAssoc data = "+e.getMessage());
	}
		 logger.info("Exit from AssetScheAssoc function");
	return assetList;	
}
	
	@RequestMapping(value = "/findAssetSchAssocById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<AssetScheduleAssoc> findAssetSchAssocById(@PathVariable("id") Long id){

		Optional<AssetScheduleAssoc> assoc = null;
		try {
			logger.info("Selected AssetScheAssoc Id = "+id);
			
			assoc = assetScheduleAssocService.findAssetSchAssocById(id);
			if(assoc.isPresent()) {
				logger.info("AssetScheAssoc Data = "+assoc.get());
				return new ResponseEntity<AssetScheduleAssoc>(assoc.get(), HttpStatus.OK);
				
			}
			else
				return new ResponseEntity<AssetScheduleAssoc>(assoc.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find AssetScheAssoc Details by id, "+e.getMessage());
			return new ResponseEntity<AssetScheduleAssoc>(assoc.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/existAssetTypeSchedule/{assetType}/{scheduleCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existAssetTypeSchedule(@PathVariable("assetType") String assetType,@PathVariable("scheduleCode") String scheduleCode){		
		try {
			return assetScheduleAssocService.existsByassetTypeAndScheduleCode(assetType,scheduleCode);
		} catch (Exception e) {
			logger.error("Error while checking exists make code.");
			return false;
		}
	}
}