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

import com.scr.model.AssetScheduleActivityAssoc;
import com.scr.services.AssetScheduleActivityAssocService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class AssetScheduleActivityAssocController {
	
	private static Logger logger = Logger.getLogger(AssetScheduleActivityAssocController.class);
	
	@Autowired 
	AssetScheduleActivityAssocService assetScheduleActivityAssocService;
	
	@RequestMapping(value = "/addAssetSchActAssoc" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addAssetSchActAssoc(@RequestBody AssetScheduleActivityAssoc assetScheduleActivityAssoc) {
		
		logger.info("Enter into save function with below request parameters ");
		
		logger.info("Request Parameters = "+assetScheduleActivityAssoc.toString());
		
	
		try {
			logger.info("Calling service with request parameters.");
			assetScheduleActivityAssocService.save(assetScheduleActivityAssoc);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("AssetScheActivityAssoc added successfully", Constants.SUCCESS_CODE);
		}
		
		catch(NullPointerException npe) {
			logger.error("ERROR >> While adding AssetScheActivityAssoc data. "+npe.getMessage());
			return Helper.findResponseStatus("AssetScheActivityAssoc save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding AssetScheActivityAssoc data. "+e.getMessage());
			return Helper.findResponseStatus("AssetScheActivityAssoc save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateAssetSchActAssoc" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateAssetSchActAssoc(@RequestBody AssetScheduleActivityAssoc assetScheduleActivityAssoc) {
		logger.info("Enter into update function with below request parameters ");
		logger.info("Request Parameters = "+assetScheduleActivityAssoc.toString());
		try {
			logger.info("Calling service with request parameters.");
			assetScheduleActivityAssocService.save(assetScheduleActivityAssoc);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("AssetScheActivityAssoc updated successfully", Constants.SUCCESS_CODE);
	}catch(NullPointerException npe) {
		logger.error("ERROR >> While updating AssetScheActivityAssoc data. "+npe.getMessage());
		return Helper.findResponseStatus("AssetScheActivityAssoc update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
	}
	catch (Exception e) {
		logger.error("ERROR >> While updating AssetScheAssoc data. "+e.getMessage());
		return Helper.findResponseStatus("AssetScheActivityAssoc update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
	}
		}
	

	@RequestMapping(value = "/deleteAssetSchActAssoc/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteAssetSchActAssocById(@PathVariable Long id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected AssetScheActivityAssoc Id = "+id);
		try {
			assetScheduleActivityAssocService.deleteAssetSchActAssocById(id);
		return Helper.findResponseStatus("AssetScheActivityAssoc deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		logger.error("ERROR >> While deleting AssetScheActivityAssoc data"+npe.getMessage());
		return Helper.findResponseStatus("AssetScheActivityAssoc Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error("ERROR >> While deleting AssetScheActivityAssoc data"+e.getMessage());
		return Helper.findResponseStatus("AssetScheActivityAssoc Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	 @RequestMapping(value = "/findAllAssetSchActAssoc" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<AssetScheduleActivityAssoc> findAllAssetSchActAssoc() throws JSONException {
		 List<AssetScheduleActivityAssoc> assetAssocList = null;
		 try {
			   logger.info("Calling service for make data");	
		
			   assetAssocList = assetScheduleActivityAssocService.findAll();
		 logger.info("Fetched AssetScheActivityAssoc data***"+assetAssocList.size());
		return assetAssocList;
	}catch (NullPointerException npe) {
		logger.error("ERROR >>> while fetching the AssetScheActivityAssoc data = "+npe.getMessage());
	}
	catch (Exception e) {
		logger.error("ERROR >>> while fetching the AssetScheActivityAssoc data = "+e.getMessage());
	}
		 logger.info("Exit from AssetScheActivityAssoc function");
	return assetAssocList;	
}
	
	@RequestMapping(value = "/findAssetSchActAssocById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<AssetScheduleActivityAssoc> findAssetSchActAssocById(@PathVariable("id") Long id){

		Optional<AssetScheduleActivityAssoc> assetAssocList = null;
		try {
			logger.info("Selected AssetScheAssoc Id = "+id);
			
			assetAssocList = assetScheduleActivityAssocService.findAssetSchAssocById(id);
			if(assetAssocList.isPresent()) {
				logger.info("AssetScheActivityAssoc Data = "+assetAssocList.get());
				return new ResponseEntity<AssetScheduleActivityAssoc>(assetAssocList.get(), HttpStatus.OK);
				
			}
			else
				return new ResponseEntity<AssetScheduleActivityAssoc>(assetAssocList.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find AssetScheActivityAssoc Details by id, "+e.getMessage());
			return new ResponseEntity<AssetScheduleActivityAssoc>(assetAssocList.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/findByAsaSeqPositionId/{asaSeqId}/{activityPositionId}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAsaSeqPositionId(@PathVariable("asaSeqId") String asaSeqId ,@PathVariable("activityPositionId") String activityPositionId,
			@PathVariable("makeCode") String makeCode,@PathVariable("modelCode") String modelCode){
		logger.info("Exist====="+asaSeqId+activityPositionId+makeCode+makeCode);
		try {
			logger.info("Request for checking exists asaSeqId and activityPositionId...");
			return assetScheduleActivityAssocService.existsByAsaSeqIdAndActivityPositionId(asaSeqId,activityPositionId,makeCode,modelCode);
		} catch (Exception e) {
			logger.error("Error while checking exists asaSeqId and activityPositionId..."+e.getMessage());
			return false;
		}
	}

	@RequestMapping(value = "/findByAsaSeqPositionIdActivity/{asaSeqId}/{activityId}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAsaSeqPositionIdActivity(@PathVariable("asaSeqId") String asaSeqId ,@PathVariable("activityId") String activityId,
			@PathVariable("makeCode") String makeCode,@PathVariable("modelCode") String modelCode){
		logger.info("Exist====="+asaSeqId+activityId+makeCode+makeCode);
		try {
			logger.info("Request for checking exists asaSeqId and activityPositionId...");
			return assetScheduleActivityAssocService.existsByAsaSeqIdAndactivityId(asaSeqId,activityId,makeCode,modelCode);
		} catch (Exception e) {
			logger.error("Error while checking exists asaSeqId and activityId..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/findByAsaSeqActivityDisplay/{asaSeqId}/{activityId}/{displayOrder}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAsaSeqActivityDisplay(@PathVariable("asaSeqId") String asaSeqId ,@PathVariable("activityId") String activityId,
			@PathVariable("displayOrder") String displayOrder,@PathVariable("makeCode") String makeCode,@PathVariable("modelCode") String modelCode){
		logger.info("Exist====="+asaSeqId+activityId+makeCode+makeCode);
		try {
			logger.info("Request for checking exists asaSeqId and activityPositionId...");
			return assetScheduleActivityAssocService.existsByAsaSeqIdAndactivityDisplayOrder(asaSeqId,activityId,displayOrder,makeCode,modelCode);
		} catch (Exception e) {
			logger.error("Error while checking exists asaSeqId and activityId..."+e.getMessage());
			return false;
		}
	}
	
}