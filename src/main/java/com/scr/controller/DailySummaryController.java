package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import com.scr.model.DailyProgressSummery;
import com.scr.services.DailySummaryService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class DailySummaryController {
	static Logger log = LogManager.getLogger(DailySummaryController.class);
	
	@Autowired
	private DailySummaryService dailySummaryService;
	
	
	@RequestMapping(value = "/findAllDailySummary" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<DailyProgressSummery> findAllDailySummary(){
		log.info("Enter into findAllDailySummary function");
		List<DailyProgressSummery> dailySummary = null;
		try {
			log.info("Calling service for daily Summary data");
			dailySummary = dailySummaryService.findAll();
			log.info("Fetched daily Summary data"+dailySummary.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the daily Summary data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the daily Summary data = "+e.getMessage());
		}
		log.info("Exit from findAllDailySummary function");
		return dailySummary;
	}
	
	@RequestMapping(value = "/addDailySummary" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addDailySummary(@RequestBody DailyProgressSummery dailyProgressSummery) {
		log.info("Enter into addDailySummary function with below request parameters ");
		log.info("Request Parameters = "+dailyProgressSummery.toString());
		try {
			log.info("Calling service with request parameters.");
			dailySummaryService.save(dailyProgressSummery);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Daily Summary added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Daily Summary data. "+npe.getMessage());
			return Helper.findResponseStatus("Daily Summary save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Daily Summary data. "+e.getMessage());
			return Helper.findResponseStatus("Daily Summary save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findDailySummaryById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<DailyProgressSummery> findDailySummaryById(@PathVariable Long id){
		Optional<DailyProgressSummery> dailySummary = null;
		try {
			log.info("Selected daily Summary Id = "+id);
			dailySummary = dailySummaryService.findDailySummaryById(id);
			if(dailySummary.isPresent()) {
				log.info("daily Summary Data = "+dailySummary.get());
				return new ResponseEntity<DailyProgressSummery>(dailySummary.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<DailyProgressSummery>(dailySummary.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find daily Summary Details by id, "+e.getMessage());
			return new ResponseEntity<DailyProgressSummery>(dailySummary.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateDailySummary" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateDailySummary(@RequestBody DailyProgressSummery dailyProgressSummery) {
		log.info("Enter into updateDailySummary function with below request parameters ");
		log.info("Request Parameters = "+dailyProgressSummery.toString());
		try {
			log.info("Calling service with request parameters.");
			dailySummaryService.save(dailyProgressSummery);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("daily Summary updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating daily Summary data. "+npe.getMessage());
			return Helper.findResponseStatus("daily Summary update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating daily Summary data. "+e.getMessage());
			return Helper.findResponseStatus("daily Summary update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteDailySummary/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteDailySummaryById(@PathVariable Long id) {
		log.info("Enter into deleteDailySummaryById function");
		log.info("Selected Daily Summary Id = "+id);
		try {
			dailySummaryService.deleteDailySummaryById(id);
			return Helper.findResponseStatus("Daily Summary Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Daily Summary data"+npe.getMessage());
			return Helper.findResponseStatus("Daily Summary Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Daily Summary data"+e.getMessage());
			return Helper.findResponseStatus("Daily Summary Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existsFacilityIdAndCreatedDate/{facilityId}/{createdDate}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFacilityIdAndCreatedDate(@PathVariable("facilityId") String facilityId ,@PathVariable("createdDate") String createdDate){
		
		try {
			log.info("Request for checking exists facilityId and createdDate...");
			return dailySummaryService.existsByFacilityIdAndCreatedDate(facilityId,Helper.convertStringToTimestamp(createdDate));
		} catch (Exception e) {
			log.error("Error while checking exists facilityId "+e.getMessage());
			return false;
		}
	}
	
}
