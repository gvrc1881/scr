package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.scr.model.AssetMonthlyTarget;
import com.scr.model.AssetScheduleAssoc;
import com.scr.services.AssetMonthlyTargetsService;
import com.scr.util.Constants;
import com.scr.util.Helper;


@CrossOrigin(origins="*",maxAge=3600)
@RestController
@RequestMapping("/scr/api")

public class AssetMonthlyTargetsController {
	
	static Logger log = LogManager.getLogger(AssetMonthlyTargetsController.class);
	
	@Autowired
	private AssetMonthlyTargetsService targetsService;
	
	@RequestMapping(value = "/findAllMonthlyTargetItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<AssetMonthlyTarget> findAllMonthlyTargetItems(){
		log.info("Enter into findAllMonthlyTargetItems function");
		List<AssetMonthlyTarget> targetItem = null;
		try {
			log.info("Calling service for  oheLocation data");
			targetItem = targetsService.findAll();
			log.info("Fetched MonthlyTargetItem data ***"+targetItem.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the MonthlyTargetItem data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the MonthlyTargetItem data = "+e.getMessage());
		}
		log.info("Exit from findAllMonthlyTargetItems function");
		return targetItem;
	}
	
	@RequestMapping(value = "/addMonthlyTargetItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addMonthlyTargetItem(@RequestBody AssetMonthlyTarget assetMonthlyTarget) {
		log.info("Enter into addMonthlyTargetItem function with below request parameters ");
		log.info("Request Parameters = "+assetMonthlyTarget.toString());
		try {
			log.info("Calling service with request parameters.");
			targetsService.save(assetMonthlyTarget);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("AssetMonthlyTarget added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding AssetMonthlyTarget data. "+npe.getMessage());
			return Helper.findResponseStatus("AssetMonthlyTarget Data save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding AssetMonthlyTarget data. "+e.getMessage());
			return Helper.findResponseStatus("AssetMonthlyTarget save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findAssetMonthlyTargetById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<AssetMonthlyTarget> findAssetMonthlyTargetById(@PathVariable Long id){
		Optional<AssetMonthlyTarget> assetMonthlyTarget = null;
		try {
			log.info("Selected assetMonthlyTarget Id = "+id);
			assetMonthlyTarget = targetsService.findAssetMonthlyTargetById(id);
			if(assetMonthlyTarget.isPresent()) {
				log.info("AssetMonthlyTarget Data = "+assetMonthlyTarget.get());
				return new ResponseEntity<AssetMonthlyTarget>(assetMonthlyTarget.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<AssetMonthlyTarget>(assetMonthlyTarget.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find AssetMonthlyTarget Details by id, "+e.getMessage());
			return new ResponseEntity<AssetMonthlyTarget>(assetMonthlyTarget.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/updateAssetMonthlyTarget")
	@ResponseBody
	public ResponseStatus updateAssetMonthlyTarget(@RequestBody List<AssetMonthlyTarget> assetMonthlyTarget) {
		log.info("*** Enter into updateAssetMonthlyTarget function ***");
		try {
			log.info("save to save==="+assetMonthlyTarget.toString());
			targetsService.updateAssetMonthlyTarget(assetMonthlyTarget);
			log.info("Preparing the return response and updateAssetMonthlyTarget function end ");
			return Helper.findResponseStatus("Asset MonthlyTarget  Data updated Successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While adding Asset MonthlyTarget Data. " + npe.getMessage());
			return Helper.findResponseStatus("Asset MonthlyTarget update is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While Asset MonthlyTarget update Data. " + e.getMessage());
			return Helper.findResponseStatus("Asset MonthlyTarget update is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/getScheduleAssocOnDpr/{isDpr}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<AssetScheduleAssoc> getScheduleAssocOnDpr(@PathVariable("isDpr") String isDpr){
		log.info("Enter into getScheduleAssocOnDpr function");
		List<AssetScheduleAssoc> scheduleAssocList = null;
		try {
			log.info("Calling service for  scheduleAssocList data");
			scheduleAssocList = targetsService.findByIsDpr(isDpr);
			log.info("Fetched fp Inspection Item data ***"+scheduleAssocList.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the scheduleAssocList  Item data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the scheduleAssocList  Item data = "+e.getMessage());
		}
		log.info("Exit from findAllObservationItems function");
		return scheduleAssocList;
	}
	
	
	@RequestMapping(value = "/getAssetMonthlyTargetsBasedOnFacilityIdYear/{facilityId}/{year}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<AssetMonthlyTarget> getAssetMonthlyTargetsBasedOnFacilityIdYear(
			@PathVariable("facilityId") String facilityId,@PathVariable("year") String year) {
		log.info("Enter into getAssetMonthlyTargetsBasedOnFacilityIdYear function Id***" + facilityId + "facilityId"
				+ year + "**year***");
		List<AssetMonthlyTarget> assetMonthlyTarget = null;

		try {
			assetMonthlyTarget = targetsService.findByFacilityIdAndYear(facilityId,year);
			return assetMonthlyTarget;
		} catch (Exception e) {
			log.error("Error >>  while find Asset MonthlyTarget data" + e.getMessage());
		}
		return assetMonthlyTarget;
	}
	
	
}
