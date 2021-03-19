package com.scr.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.jobs.CommonUtility;
import com.scr.message.response.AshDailyProgressResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.message.response.ThermovisionMeasureResponse;
import com.scr.model.AshDailyProgress;
import com.scr.model.AssetMasterData;
import com.scr.model.Facility;
import com.scr.services.AshDailyProgressService;
import com.scr.services.AssetMasterDataService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class AshDailyProgressController {
	
	static Logger log = LogManager.getLogger(AshDailyProgressController.class);
	
	@Autowired
	private AshDailyProgressService ashDailyProgressService;
	
	@Autowired
	private CommonUtility  commonUtility;
	
	@RequestMapping(value = "/getAshDailyProgress/{fromDate}/{depotId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<AshDailyProgressResponse> getAshDailyProgressData(@PathVariable("fromDate") Date fromDate , @PathVariable("depotId") Long depotId) {
		log.info("Enter into getAshDailyProgressData function date***"+fromDate+"*** facilityId"+depotId);
		List<AshDailyProgressResponse> assetMasterItem = null;
		try {  
			log.info("Calling service for  ashDailyProgress data");
			assetMasterItem = ashDailyProgressService.getAshDailyPrgress(fromDate,depotId);
			log.info("Fetched ash daily progress  data size ***" + assetMasterItem.size());
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the ash daily progress data = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the ash daily progress data = " + e.getMessage());
		}
		log.info("Exit from getAshDailyProgressData function");
		return assetMasterItem;
	}
	
	
	@PostMapping(value="/saveAshDailyProgress")
	@ResponseBody
	public ResponseStatus saveAshDailyProgress(@RequestBody List<AshDailyProgressResponse> ashDailyProgressResponses) {
		log.info("*** Enter into saveAshDailyProgress function ***");
		try {			
			ashDailyProgressService.saveAshDailyProgress(ashDailyProgressResponses);
			log.info("Preparing the return response and saveAshDailyProgress function end ");
			return Helper.findResponseStatus("Ash Daily Progress Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Ash Daily Progress Data. "+npe.getMessage());
			return Helper.findResponseStatus("Ash Daily Progress Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Ash Daily Progress Data. "+e.getMessage());
			return Helper.findResponseStatus("Ash Daily Progress  Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}


@RequestMapping(value = "/getAshDailyProgressBasedOnApprovedStatus/{fromDate}/{depotId}/{loggedUserData}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<AshDailyProgress> getAshDailyProgressDataBasedOnApprovedStatus(@PathVariable("fromDate") Date fromDate , 
			@PathVariable("depotId") String depotId,@PathVariable("loggedUserData") String loggedUserData) {
		log.info("Enter into getAshDailyProgressData function date***"+fromDate+"*** facilityId"+depotId);
		List<AshDailyProgress> ashDailyProgressList = null;
		
		List<Facility> fac= new ArrayList<>();
		try {  
			log.info("Calling service for  ashDailyProgress data");
			if(!fromDate.equals(null) && depotId.equals("null") && !loggedUserData.equals(null)) {
				log.info("in if ");					
				List<Facility> facility = commonUtility.findUserHierarchy(loggedUserData);
				
				log.info("facilities=="+facility.size());
				
				for (Facility facility2 : facility) {
					
					fac.add(facility2);					
					
				}
				ashDailyProgressList = ashDailyProgressService.findByDateAndFacilityIn(fromDate,fac);
								
				
			}else {
				log.info("in else");				
				
				
				ashDailyProgressList = ashDailyProgressService.findByDateAndFacility(fromDate,new Long(depotId));
			}
			log.info("Fetched ash daily progress  data size ***" + ashDailyProgressList.size());
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the ash daily progress data = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the ash daily progress data = " + e.getMessage());
		}
		log.info("Exit from getAshDailyProgressData function");
		return ashDailyProgressList;
	}
	
	@PostMapping(value="/saveApprovedAshDailyProgress")
	@ResponseBody
	public ResponseStatus saveApprovedAshDailyProgress(@RequestBody List<AshDailyProgress> ashDailyProgresses) {
		log.info("*** Enter into saveAshDailyProgress function ***");
		log.info("save list===="+ashDailyProgresses.toString());
		try {			
			ashDailyProgressService.saveApprovedAshDailyProgress(ashDailyProgresses);
			log.info("Preparing the return response and saveAshDailyProgress function end ");
			return Helper.findResponseStatus("Ash Daily Progress Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Ash Daily Progress Data. "+npe.getMessage());
			return Helper.findResponseStatus("Ash Daily Progress Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Ash Daily Progress Data. "+e.getMessage());
			return Helper.findResponseStatus("Ash Daily Progress  Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
}
