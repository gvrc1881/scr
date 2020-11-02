package com.scr.controller;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.scr.message.request.AssetScheduleActivityAssocRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.AssetScheduleActivityAssoc;
import com.scr.model.AssetScheduleAssoc;
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
	public ResponseStatus addAssetSchActAssoc(@Valid @RequestBody AssetScheduleActivityAssocRequest assetScheduleActivityAssoc) throws JSONException {
		
		logger.info("Enter into save function with below request parameters ");
		
		logger.info("Request Parameters = "+assetScheduleActivityAssoc.toString());	
	
		try {
			logger.info("Calling service with request parameters.");
			 AssetScheduleActivityAssoc asaa = assetScheduleActivityAssocService.saveAssocData(assetScheduleActivityAssoc);
			 logger.info("*** id **"+asaa.getId());
			 logger.info("*** id **"+asaa.getId().toString());
			 asaa.setSeqId(asaa.getId().toString());
			 assetScheduleActivityAssocService.save(asaa);
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
	public ResponseStatus updateAssetSchActAssoc(@Valid @RequestBody AssetScheduleActivityAssocRequest assetScheduleActivityAssocReq) {
		logger.info("Enter into update function with below request parameters ");
		logger.info("Request Parameters = "+assetScheduleActivityAssocReq.toString());
		try {
			logger.info("Calling service with request parameters.");
			String status=assetScheduleActivityAssocService.updateAssocData(assetScheduleActivityAssocReq);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("AssetSchActivityAssoc Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			logger.error("ERROR >> While updating AssetSchActivityAssoc data. "+e.getMessage());
			return Helper.findResponseStatus("AssetSchActivityAssoc Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
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
	 
	
	/* @RequestMapping(value = "/findAllAssetSchActAssoc/{from}/{to}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<AssetScheduleActivityAssoc> findAllAssetSchActAssoc(@PathVariable("from") int from,@PathVariable("to") int to)  {
		 
		 logger.info("\"Enter into AllAssetSchActAssoc function");
		 
		 List<AssetScheduleActivityAssoc> assetAssocList = null;
		 try {
			   logger.info("Calling service for Asset Sch Activity Assoc data");	
		
			   assetAssocList = assetScheduleActivityAssocService.findPaginated(from, to);;
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
}*/
	
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
			logger.info("Request for checking exists asaSeqId and activityPositionId and make code and model code...");
			return assetScheduleActivityAssocService.existsByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(asaSeqId,activityPositionId,makeCode,modelCode);
		} catch (Exception e) {
			logger.error("Error while checking exists asaSeqId and activityPositionId and make code and model code..."+e.getMessage());
			return false;
		}
	}

	@RequestMapping(value = "/findByAsaSeqPositionIdActivity/{asaSeqId}/{activityId}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAsaSeqPositionIdActivity(@PathVariable("asaSeqId") String asaSeqId ,@PathVariable("activityId") String activityId,
			@PathVariable("makeCode") String makeCode,@PathVariable("modelCode") String modelCode){
		logger.info("Exist====="+asaSeqId+activityId+makeCode+modelCode);
		try {
			logger.info("Request for checking exists asaSeqId and activityId and make code and model code...");
			return assetScheduleActivityAssocService.existsByAsaSeqIdAndactivityIdAndMakeCodeAndModelCode(asaSeqId,activityId,makeCode,modelCode);
		} catch (Exception e) {
			logger.error("Error while checking exists asaSeqId and activityId and make code and model code..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/findByAsaSeqActivityDisplay/{asaSeqId}/{activityId}/{displayOrder}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAsaSeqActivityDisplay(@PathVariable("asaSeqId") String asaSeqId ,@PathVariable("activityId") String activityId,
			@PathVariable("displayOrder") BigDecimal displayOrder,@PathVariable("makeCode") String makeCode,@PathVariable("modelCode") String modelCode){
		logger.info("Exist====="+"asaSeqId=="+asaSeqId+"activityId==="+activityId+"displayOrder=="+displayOrder+"makeCode=="+makeCode+"modelCode=="+modelCode);
		try {
			logger.info("Request for checking exists asaSeqId and activityPositionId and displayorder and make code and model code ...");
			return assetScheduleActivityAssocService.existsByAsaSeqIdAndactivityIdAndDisplayOrderAndMakeCodeAndModelCode(asaSeqId,activityId,displayOrder,makeCode,modelCode);
		} catch (Exception e) {
			logger.error("Error while checking exists asaSeqId and activityId and displayorder and make code and model code..."+e.getMessage());
			return false;
		}
	}
	
	 @RequestMapping(value = "/findByAsaSeqPositionIdAndId/{id}/{asaSeqId}/{activityPositionId}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAsaSeqPositionIdAndId(@PathVariable("id") Long id,@PathVariable("asaSeqId") String asaSeqId,
			@PathVariable("activityPositionId") String activityPositionId,@PathVariable("makeCode") String makeCode,@PathVariable("modelCode") String modelCode){
		
		logger.info("id=="+id+"asaSeqId=="+asaSeqId+"activityPositionId=="+activityPositionId+"makeCode"+makeCode+"modelCode=="+modelCode);
		Boolean result;
		try {
			Optional<AssetScheduleActivityAssoc> asaaData = assetScheduleActivityAssocService.findByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(asaSeqId,activityPositionId,makeCode,modelCode);
			
			if(asaaData.isPresent()) {
				AssetScheduleActivityAssoc asaa = asaaData.get();
				logger.info("***id ***"+asaa.getId());
			if (id.equals(asaa.getId())) {
					return result = false;
				} else {
					return result = true;
				}
		}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and makecode and modelCode..."+e.getMessage());
			return false;
		}
}
	
	@RequestMapping(value = "/findByAsaSeqPositionIdActivityAndId/{id}/{asaSeqId}/{activityId}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAsaSeqPositionIdActivityAndId(@PathVariable("id") Long id,@PathVariable("asaSeqId") String asaSeqId,
			@PathVariable("activityId") String activityId,@PathVariable("makeCode") String makeCode,@PathVariable("modelCode") String modelCode){
		
		logger.info("id=="+id+"asaSeqId=="+asaSeqId+"activityId=="+activityId+"makeCode"+makeCode+"modelCode=="+modelCode);
		Boolean result;
		try {
			Optional<AssetScheduleActivityAssoc> asaData = assetScheduleActivityAssocService.findByAsaSeqIdAndActivityIdAndMakeCodeAndModelCode(asaSeqId,activityId,makeCode,modelCode);
			
			if(asaData.isPresent()) {
				AssetScheduleActivityAssoc asa = asaData.get();
				logger.info("***id ***"+asa.getId());
				if (id.equals(asa.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and makecode and modelCode..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/findByAsaSeqActivityDisplayAndId/{id}/{asaSeqId}/{activityId}/{displayOrder}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAsaSeqActivityDisplayAndId(@PathVariable("id") Long id,@PathVariable("asaSeqId") String asaSeqId,
		@PathVariable("activityId") String activityId,@PathVariable("displayOrder") BigDecimal displayOrder,@PathVariable("makeCode") String makeCode,@PathVariable("modelCode") String modelCode){
		
		logger.info("id=="+id+"asaSeqId=="+asaSeqId+"activityId=="+activityId+"displayOrder==="+displayOrder+"makeCode"+makeCode+"modelCode=="+modelCode);
		Boolean result;
		try {
			Optional<AssetScheduleActivityAssoc> asData = assetScheduleActivityAssocService.findByAsaSeqIdAndActivityIdAndDisplayOrderAndMakeCodeAndModelCode(asaSeqId,activityId,displayOrder,makeCode,modelCode);
			
			if(asData.isPresent()) {
				AssetScheduleActivityAssoc as = asData.get();
				logger.info("***id ***"+as.getId());
				if (id.equals(as.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and makecode and modelCode..."+e.getMessage());
			return false;
	}
	}
}