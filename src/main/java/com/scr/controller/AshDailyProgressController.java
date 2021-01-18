package com.scr.controller;

import java.util.Date;
import java.util.List;

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

import com.scr.message.response.AshDailyProgressResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.message.response.ThermovisionMeasureResponse;
import com.scr.model.AssetMasterData;
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

}
