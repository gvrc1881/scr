package com.scr.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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

import com.scr.services.TimeIntervalService;
import com.scr.services.UserServices;
import com.scr.util.Constants;
import com.scr.util.Helper;
import com.scr.message.request.TimeIntervalRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.TimeInterval;
import com.scr.model.User;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TimeIntervalControler {

	private Logger logger = Logger.getLogger(TimeIntervalControler.class);

	@Autowired
	private TimeIntervalService timeIntervalService;

	@Autowired
	private UserServices userService;	

	@RequestMapping(value = "/findAllTimeIntervals", method = RequestMethod.GET, headers="accept=application/json")
	public ResponseEntity<List<TimeInterval>> getAllTimeIntervalServiceDetails() {
		List<TimeInterval> timeIntervals = null;
		try {
			logger.info("Getting timeIntervals Details");
			timeIntervals = timeIntervalService.findAllByisActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);
			return new ResponseEntity<List<TimeInterval>>(timeIntervals, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while getting timeIntervals Details");
			return new ResponseEntity<List<TimeInterval>>(timeIntervals, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/findTimeIntervalById/{timeIntervalId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimeInterval> findRepositryById(@PathVariable("timeIntervalId") Integer timeIntervalId) {
		TimeInterval timeInterval = null;
		try {
			timeInterval = timeIntervalService.findByTimeIntervalId(timeIntervalId);
			if (timeInterval != null)
				return new ResponseEntity<TimeInterval>(timeInterval, HttpStatus.OK);
			else
				return new ResponseEntity<TimeInterval>(timeInterval, HttpStatus.CONFLICT);

		} catch (Exception e) {
			logger.error("Error while find JobType Details by id");
			return new ResponseEntity<TimeInterval>(timeInterval, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/existsTimeInterval/{timeInterval}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean existsByJobTypeName(@PathVariable("timeInterval") Integer timeInterval) {
		try {
			return timeIntervalService.existsByTimeInterval(timeInterval);
		} catch (Exception e) {
			logger.error("Error while checking exists TimeInterval.");
			return false;
		}
	}

	@RequestMapping(value = "/addTimeInterval", method = RequestMethod.POST, headers="accept=application/json")
	public ResponseStatus addTimeIntervalDetails(@RequestBody TimeIntervalRequest timeIntervalRequest) {
		try {
			logger.info("Adding timeInterval Details");
			Optional<User> user = userService.findById(new Long(timeIntervalRequest.getCreatedBy()));
			if (user.isPresent()) {				
				TimeInterval flag = timeIntervalService.addTimeInterval(timeIntervalRequest, user.get());
				if (flag != null)
					return Helper.findResponseStatus("TimeInterval Added Successfully", Constants.SUCCESS_CODE);
				else
					return Helper.findResponseStatus("TimeInterval Addition is Fail", Constants.FAILURE_CODE);
			} else
				return Helper.findResponseStatus("TimeInterval Addition is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("Error while Adding repositorys Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/deleteTimeInterval/{timeIntervalId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseStatus deleteTimeIntervalDetails(@PathVariable("timeIntervalId") Integer timeIntervalId) {
		try {
			TimeInterval timeInterval = timeIntervalService.findByTimeIntervalId(timeIntervalId);
			timeInterval.setIsActive(Constants.UNACTIVE_STATUS_ID);
			timeInterval.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			logger.info("deleteing repositorys Details");
			TimeInterval flag = timeIntervalService.saveTimeInterval(timeInterval);
			if (flag != null)
				return Helper.findResponseStatus("TimeInterval Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("TimeInterval Deletion is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("Error while deleteing TimeInterval Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/updateTimeInterval", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseStatus updateTimeIntervalDetails(@RequestBody TimeIntervalRequest timeIntervalRequest) {
		try {
			Optional<User> user = userService.findById(new Long(timeIntervalRequest.getModifiedBy()));
			if (user.isPresent()) {
			logger.info("deleteing repositorys Details");
			TimeInterval flag = timeIntervalService.updateTimeInterval(timeIntervalRequest, user.get());
			if (flag != null)
				return Helper.findResponseStatus("TimeInterval Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("TimeInterval Updation is Fail", Constants.FAILURE_CODE);
			}else
				return Helper.findResponseStatus("TimeInterval Updation is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("Error while Upadating TimeInterval  Details");
			e.printStackTrace();
			return Helper.findResponseStatus(e.getLocalizedMessage(), Constants.FAILURE_CODE);
		}
	}

}
