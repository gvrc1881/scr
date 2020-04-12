/**
 * 
 */
package com.scr.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.mapper.SchedulerTrackingMapper;
import com.scr.message.response.DashboardResponse;
import com.scr.model.DivisionsHistory;
import com.scr.model.JobsHistory;
import com.scr.model.SchedulerJobsTracking;
import com.scr.model.SchedulerOperationTypesTracking;
import com.scr.services.SchedulerJobTrackingService;
import com.scr.services.SchedulerOperationTypesTrackingService;
import com.scr.services.SchedulerTrackingService;
import com.scr.util.Constants;

/**
 * @author vt1056
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SchedulerTrackingController {
	private Logger logger = Logger.getLogger(SchedulerTrackingController.class);
	
	@Autowired
	private SchedulerTrackingService schedulerTrackingService;
	
	@Autowired
	private SchedulerJobTrackingService schedulerJobTrackingService;
	
	@Autowired
	private SchedulerOperationTypesTrackingService schedulerOperationTypesTrackingService;
	
	@Autowired
	private SchedulerTrackingMapper schedulerTrackingMapper;
	
	@RequestMapping(value = "/findJobsHistoryInfo/{operationId}", method = RequestMethod.GET ,headers = "Accept=application/json")	
	public ResponseEntity<List<JobsHistory>> getAllJobTypesDetails(@PathVariable("operationId") Integer operationId){
		List<JobsHistory> jobsHistories = null;
		try {
			logger.info("Getting JobsHistory Details");			
			jobsHistories = schedulerTrackingService.findByOperationIdOrderByProcessedDateDesc(operationId);
			return new ResponseEntity<List<JobsHistory>>(jobsHistories, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting JobsHistory Details"+e.getMessage());
			return new ResponseEntity<List<JobsHistory>>(jobsHistories, HttpStatus.CONFLICT);
		}	
	}
	
	@RequestMapping(value = "/findOperationsInfo/{trackingId}", method = RequestMethod.GET ,headers = "Accept=application/json")	
	public ResponseEntity<List<SchedulerOperationTypesTracking>> findOperationsInfo(@PathVariable("trackingId") Integer trackingId){
		List<SchedulerOperationTypesTracking> schedulerOperationTypesTrackingsList = null;
		try {
			logger.info("Getting JobsHistory Details");			
			schedulerOperationTypesTrackingsList = schedulerOperationTypesTrackingService.findByTrackingIdOrderByProcessedDateAsc(trackingId);
			return new ResponseEntity<List<SchedulerOperationTypesTracking>>(schedulerOperationTypesTrackingsList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting JobsHistory Details"+e.getMessage());
			return new ResponseEntity<List<SchedulerOperationTypesTracking>>(schedulerOperationTypesTrackingsList, HttpStatus.CONFLICT);
		}	
	}
	
	
	@RequestMapping(value = "/divisionInfo/{jobsHistoryId}", method = RequestMethod.GET ,headers = "Accept=application/json")	
	public ResponseEntity<List<DivisionsHistory>> getDivisionHistory( @PathVariable("jobsHistoryId") Integer jobsHistoryId){
		List<DivisionsHistory> divisionsHistories = null;
		try {
			logger.info("Getting DivisionHistory Details  = "+jobsHistoryId);			
			divisionsHistories = schedulerTrackingService.findDivisionsHistoryByJobsHistoryId(jobsHistoryId);
			return new ResponseEntity<List<DivisionsHistory>>(divisionsHistories, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting DivisionHistory Details"+e.getMessage());
			return new ResponseEntity<List<DivisionsHistory>>(divisionsHistories, HttpStatus.CONFLICT);
		}	
	}
	
	@RequestMapping(value = "/findAllJobInfo", method = RequestMethod.GET ,headers = "Accept=application/json")	
	public ResponseEntity<List<SchedulerJobsTracking>> getAllJobTypesInfoDetails(){
		List<SchedulerJobsTracking> schedulerJobsTrackings = null;
		try {
			logger.info("Getting JobsHistory Details");			
			schedulerJobsTrackings = schedulerJobTrackingService.findSchedulerJobTrackingOrderByProcessedDateDesc();
			return new ResponseEntity<List<SchedulerJobsTracking>>(schedulerJobsTrackings, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting JobsHistory Details"+e.getMessage());
			return new ResponseEntity<List<SchedulerJobsTracking>>(schedulerJobsTrackings, HttpStatus.CONFLICT);
		}	
	}
	
	@RequestMapping(value = "/dashboard/{divisionCode}", method = RequestMethod.GET ,headers = "Accept=application/json")
	public DashboardResponse getDashboard(@PathVariable("divisionCode") String divisionCode) {
		DashboardResponse response = new DashboardResponse();
		try {
			logger.info("DivisionCode: "+divisionCode);
			List<SchedulerJobsTracking> schedulerJobsTrackingsList = null;
			if(divisionCode.equalsIgnoreCase(Constants.ALL))
				schedulerJobsTrackingsList = schedulerJobTrackingService.findSchedulerJobTrackingOrderByProcessedDateAsc();
			else
				schedulerJobsTrackingsList = schedulerJobTrackingService.findSchedulerJobTrackingByDivisionCodeOrderByProcessedDateAsc(divisionCode);
			logger.info(schedulerJobsTrackingsList.size());
			response = schedulerTrackingMapper.prepareDashboardResponse(divisionCode, schedulerJobsTrackingsList);			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
