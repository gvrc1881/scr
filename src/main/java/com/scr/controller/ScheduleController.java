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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;

import com.scr.model.Schedule;
import com.scr.services.ScheduleService;

import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ScheduleController {
	
	static Logger log = Logger.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduleService scheduleService;
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllSchedule", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Schedule> findAllSchedule() throws JSONException {
		log.info("Enter into findAllSchedule function");
		List<Schedule> scheduleList = null;
		try {
			log.info("Calling service for Schedules data");
			scheduleList = scheduleService.findAllOrderByscheduleCodeAsc();
			log.info("Fetched Schedules data = "+scheduleList.size());
			return scheduleList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the schedules data = "+npe.getMessage());
		}catch (Exception e) {
			log.error("ERROR >>> while fetching the schedules data = "+e.getMessage());
		}
		log.info("Exit from findAllschedule function");
		return scheduleList;	
	}
	

	
	
	
	
	

	





}
