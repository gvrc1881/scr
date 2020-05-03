package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@Autowired
	private DailySummaryService dailySummaryService;
	
	
	@RequestMapping(value = "/findAllDailySummary" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<DailyProgressSummery> findAllDailySummary(){
		List<DailyProgressSummery> dailySummary = dailySummaryService.findAll();
		return dailySummary;
	}
	
	@RequestMapping(value = "/addDailySummary" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addDailySummary(@RequestBody DailyProgressSummery dailyProgressSummery) {
		dailySummaryService.save(dailyProgressSummery);
		return Helper.findResponseStatus("Daily Summary added successfully", Constants.SUCCESS_CODE);

	}
	
	
	@RequestMapping(value = "/findDailySummaryById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<DailyProgressSummery> findDailySummaryById(@PathVariable Long id){
		Optional<DailyProgressSummery> dailySummary = dailySummaryService.findDailySummaryById(id);
		return new ResponseEntity<DailyProgressSummery>(dailySummary.get(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/updateDailySummary" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateDailySummary(@RequestBody DailyProgressSummery dailyProgressSummery) {
		dailySummaryService.save(dailyProgressSummery);
		return Helper.findResponseStatus("Daily Summary updated successfully", Constants.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/deleteDailySummary/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteDailySummaryById(@PathVariable Long id) {
		dailySummaryService.deleteDailySummaryById(id);
		return Helper.findResponseStatus("Daily Summary Deleted successfully", Constants.SUCCESS_CODE);

	}

}
