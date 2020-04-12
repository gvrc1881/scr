/**
 * 
 */
package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.JobTypeMapper;
import com.scr.message.request.JobTypeRequest;
import com.scr.model.JobType;
import com.scr.model.User;
import com.scr.repository.jobTypeRepository;
import com.scr.util.Constants;

/**
 * @author venkat
 *
 */
@Service
public class JobTypeService {

	@Autowired
	private jobTypeRepository jobTypeRepository;

	@Autowired
	private JobTypeMapper jobTypeMapper;
	

	
	public List<JobType> findAllByIsActiveOrderByModifiedDateDesc(Integer isActive) {
		return jobTypeRepository.findAllByIsActiveOrderByModifiedDateDesc(isActive);
	}

	public JobType saveJobType(JobType jobType) {
		return jobTypeRepository.save(jobType);
	}

	public JobType findByJobTypeId(Integer jobTypeId) {
		return jobTypeRepository.findByJobTypeId(jobTypeId);
	}

	public Boolean existsByJobTypeName(String jobTypeName) {
		return jobTypeRepository.existsByJobTypeNameAndIsActive(jobTypeName, Constants.ACTIVE_STATUS_ID);
	}

	public JobType saveJobType(JobTypeRequest jobTypeRequest, User user) {
		JobType jobType = null;
		if (jobTypeRequest != null && user != null) {
			jobType = jobTypeMapper.prepareAddJobTypeMapper(jobTypeRequest, user);
			jobType = saveJobType(jobType);			
		}
		return jobType;
	}

	public JobType updateJobType(JobTypeRequest jobTypeRequest, User user) {
		JobType jobTypeToUpdate = null;
		if (jobTypeRequest != null && user != null) {
			jobTypeToUpdate = findByJobTypeId(jobTypeRequest.getJobTypeId());
			jobTypeToUpdate = jobTypeMapper.prepareUpdateJobTypeMapper(jobTypeToUpdate, jobTypeRequest, user);
			jobTypeToUpdate = saveJobType(jobTypeToUpdate);
		}
		return jobTypeToUpdate;
	}

}
