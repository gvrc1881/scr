package com.scr.controller;


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

import com.scr.message.request.JobTypeRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.JobType;
import com.scr.model.User;
import com.scr.services.JobTypeService;
import com.scr.services.UserServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class JobTypeControler {
	
	private Logger logger = Logger.getLogger(JobTypeControler.class);
	
	@Autowired
	private JobTypeService jobTypeService;
	
	@Autowired
	private UserServices userServices;
	
	
	
	@RequestMapping(value = "/findAllJobTypes", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<JobType>> getAllJobTypesDetails(){
		List<JobType> jobTypes = null;
		try {
			logger.info("Getting JobType Details");			
			jobTypes = jobTypeService.findAllByIsActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);  
			return new ResponseEntity<List<JobType>>(jobTypes, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting JobType Details"+e.getMessage());
			return new ResponseEntity<List<JobType>>(jobTypes, HttpStatus.CONFLICT);
		}	
	}
	
	@RequestMapping(value = "/findJobTypeById/{jobTypeId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<JobType> findRepositryById(@PathVariable("jobTypeId") Integer jobTypeId){
		JobType jobType = null;
		try {
			jobType = jobTypeService.findByJobTypeId(jobTypeId);
			if(jobType != null)
				return new ResponseEntity<JobType>(jobType, HttpStatus.OK);
			else
				return new ResponseEntity<JobType>(jobType, HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find JobType Details by id");
			return new ResponseEntity<JobType>(jobType, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/existsjobTypeName/{jobTypeName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsByJobTypeName(@PathVariable("jobTypeName") String jobTypeName){		
		try {
			return jobTypeService.existsByJobTypeName(jobTypeName);
		} catch (Exception e) {
			logger.error("Error while checking exists jobtype name.");
			return false;
		}
	}
	
	@RequestMapping(value = "/addJobType", method = RequestMethod.POST ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseStatus addJobTypeDetails(@RequestBody JobTypeRequest jobTypeRequest){
		try {
			logger.info("Adding jobType  Details");
			Optional<User> user = userServices.findById(new Long(jobTypeRequest.getCreatedBy()));
			if(user.isPresent()) {			
			JobType flag = jobTypeService.saveJobType(jobTypeRequest, user.get()) ;//jobTypeService.saveJobType(jobType);  
			if (flag != null)
				return Helper.findResponseStatus("JobType Added Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("JobType Addition is Fail", Constants.FAILURE_CODE);
			}else
				return Helper.findResponseStatus("JobType Addition is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {			
			logger.error("Error while Adding JobType Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/deleteJobType/{jobTypeId}", method = RequestMethod.DELETE ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseStatus deleteJobTypeDetails(@PathVariable("jobTypeId") Integer  jobTypeId){
		try {
			logger.info("Deleteing jobType  Details");
			JobType jobType = jobTypeService.findByJobTypeId(jobTypeId);
			jobType.setIsActive(Constants.UNACTIVE_STATUS_ID);
			JobType flag = jobTypeService.saveJobType(jobType);  
			if (flag != null)
				return Helper.findResponseStatus("JobType Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("JobType Deletion is Fail", Constants.FAILURE_CODE);	
		} catch (Exception e) {
			logger.error("Error while deleteing JobType Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/updateJobType", method = RequestMethod.PUT ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseStatus updateJobTypeDetails(@RequestBody JobTypeRequest  jobTypeRequest){
		try {
			logger.info("Upadating jobType  Details");
			Optional<User> user = userServices.findById(new Long(jobTypeRequest.getModifiedBy()));
			if(user.isPresent()) {
			JobType flag = jobTypeService.updateJobType(jobTypeRequest, user.get());  
			if (flag != null)
				return Helper.findResponseStatus("JobType Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("JobType Updation is Fail", Constants.FAILURE_CODE);
			}else
				return Helper.findResponseStatus("JobType Updation is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("Error while Upadating JobType  Details");
			e.printStackTrace();
			return Helper.findResponseStatus(e.getLocalizedMessage(), Constants.FAILURE_CODE);
		}
	}
}
	
