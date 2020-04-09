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

import com.scr.message.request.SchedulerJobRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.message.response.ScheduledJobResponse;
import com.scr.model.JobType;
import com.scr.model.Repository;
import com.scr.model.SchedulerJob;
import com.scr.model.TimeInterval;
import com.scr.model.User;
import com.scr.services.JobTypeService;
import com.scr.services.RepositoryService;
import com.scr.services.ScheduleSettingService;
import com.scr.services.TimeIntervalService;
import com.scr.services.UserServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ScheduleSettingControler {
	private Logger logger = Logger.getLogger(ScheduleSettingControler.class);

	@Autowired
	private ScheduleSettingService scheduleSettingService;

	@Autowired
	private UserServices userService;

	@Autowired
	private JobTypeService jobTypeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private TimeIntervalService timeIntervalService;
	
	@RequestMapping(value = "/findAllSchedulerJobs", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<SchedulerJob>> getAllScheduleSettingDetails() {
		List<SchedulerJob> schedulerJob = null;
		try {
			logger.info("Getting Scheduled Jobs Details");			
			schedulerJob = scheduleSettingService.findAllByisActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);						
			return new ResponseEntity<List<SchedulerJob>>(schedulerJob, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while getting Scheduled Jobs Details "+e.getMessage());
			return new ResponseEntity<List<SchedulerJob>>(schedulerJob, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/findAllSchedulerJobsList", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<ScheduledJobResponse> getScheduledJobsList() {
		ScheduledJobResponse scheduledJob = null;
		try {
			scheduledJob = new ScheduledJobResponse();
			List<Repository> repositoryList = repositoryService.findAllByIsActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);
			List<TimeInterval> timeIntervalList = timeIntervalService.findAllByisActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);
			List<JobType> jobTypeList = jobTypeService.findAllByIsActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);
			scheduledJob.setRepositoryList(repositoryList);
			scheduledJob.setTimeIntervalList(timeIntervalList);
			scheduledJob.setJobTypeList(jobTypeList);
			return new ResponseEntity<ScheduledJobResponse>(scheduledJob, HttpStatus.OK);
		}catch (Exception e) {
			logger.error("Error while getting Scheduled Jobs Details "+e.getMessage());
			return new ResponseEntity<ScheduledJobResponse>(scheduledJob, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	/*
	 * @RequestMapping(value = "/existsRepositoryName/{repositoryName}", method =
	 * RequestMethod.GET , headers="accept=application/json") public Boolean
	 * existsByRepositoryName(@PathVariable("repositoryName") String
	 * repositoryName){ try { if(!repositoryName.isEmpty()) { Repository repository
	 * = repositoryService.findByRepositoryNameAndIsActive(repositoryName,
	 * Constants.ACTIVE_STATUS_ID); return
	 * scheduleSettingService.existsByRepositoryIdByisActive(repository.
	 * getRepositoryId(), Constants.ACTIVE_STATUS_ID); } else return false; } catch
	 * (Exception e) { logger.error("Error while checking exists repository id.");
	 * return false; } }
	 */
	
	@RequestMapping(value = "/findScheduleJobById/{jobId}", method = RequestMethod.GET , headers="accept=application/json")	
	public ResponseEntity<SchedulerJob> findRepositryById(@PathVariable("jobId") Integer jobId){
		SchedulerJob schedulerJob = null;
		try {
			schedulerJob = scheduleSettingService.findByJobId(jobId);
			if(schedulerJob != null)
				return new ResponseEntity<SchedulerJob>(schedulerJob, HttpStatus.OK);
			else
				return new ResponseEntity<SchedulerJob>(schedulerJob, HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find schedule job by id");
			return new ResponseEntity<SchedulerJob>(schedulerJob, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/addSchedulerSettings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseStatus addScheduleSettingDetails(@RequestBody SchedulerJobRequest schedulerJobRequest) {
		try {
			logger.info("Adding Scheduled Jobs Details");
			Optional<User> user = userService.findById(new Long(schedulerJobRequest.getCreatedBy()));
			if (user.isPresent()) {
				JobType jobType = jobTypeService.findByJobTypeId(schedulerJobRequest.getJobTypeId());
				Repository repository = repositoryService.findByRepositoryId(schedulerJobRequest.getRepositoryId());
				TimeInterval timeInterval = timeIntervalService.findByTimeIntervalId(schedulerJobRequest.getTimeIntervalId());
				SchedulerJob flag = scheduleSettingService.addSchedulerSettings(schedulerJobRequest, user.get(), jobType, repository, timeInterval);
				if (flag != null)
					return Helper.findResponseStatus("Scheduler Added Successfully", Constants.SUCCESS_CODE);
				else
					return Helper.findResponseStatus("Scheduler Addition is Fail", Constants.FAILURE_CODE);
			} else
				return Helper.findResponseStatus("Scheduler Addition is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("Error while Adding Scheduler Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/deleteSchedulerSettings/{jobId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseStatus deleteScheduleSettingDetails(@PathVariable("jobId") Integer jobId) {
		try {
			SchedulerJob schedulerJob = scheduleSettingService.findByJobId(jobId);
			schedulerJob.setIsActive(Constants.UNACTIVE_STATUS_ID);
			schedulerJob.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			logger.info("deleteing scheduler Details");
			SchedulerJob flag = scheduleSettingService.saveSchedulerSettings(schedulerJob);
			if (flag != null)
				return Helper.findResponseStatus("Scheduler Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("Scheduler Deletion is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("Error while deleteing repositorys Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/updateSchedulerSettings", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseStatus updateScheduleSettingDetails(@RequestBody SchedulerJobRequest schedulerJobRequest) {
		try {
			Optional<User> user = userService.findById(new Long(schedulerJobRequest.getModifiedBy()));
			if (user.isPresent()) {
				JobType jobType = jobTypeService.findByJobTypeId(schedulerJobRequest.getJobTypeId());
				Repository repository = repositoryService.findByRepositoryId(schedulerJobRequest.getRepositoryId());
				TimeInterval timeInterval = timeIntervalService.findByTimeIntervalId(schedulerJobRequest.getTimeIntervalId());
		
			logger.info("Updated Scheduler Details");
			SchedulerJob flag = scheduleSettingService.updateSchedulerSettings(schedulerJobRequest, user.get(), jobType, repository, timeInterval);
			if (flag != null)
				return Helper.findResponseStatus("Scheduler Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("Scheduler Updation is Fail", Constants.FAILURE_CODE);
			}else
				return Helper.findResponseStatus("Scheduler Updation is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("Error while Upadating Scheduler  Details");
			e.printStackTrace();
			return Helper.findResponseStatus(e.getLocalizedMessage(), Constants.FAILURE_CODE);
		}
	}

}
