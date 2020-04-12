/**
 * 
 */
package com.scr.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.ScheduleSettingMapper;
import com.scr.message.request.SchedulerJobRequest;
import com.scr.model.JobStatus;
import com.scr.model.JobType;
import com.scr.model.Repository;
import com.scr.model.SchedulerJob;
import com.scr.model.TimeInterval;
import com.scr.model.User;
import com.scr.repository.ScheduleSettingRepository;
import com.scr.util.Constants;

/**
 * @author venkat
 *
 */
@Service
public class ScheduleSettingService {

	private static final Logger logger = Logger.getLogger(ScheduleSettingService.class);
	
	@Autowired
	private ScheduleSettingRepository scheduleSettingRepository;

	@Autowired
	private ScheduleSettingMapper mapper;

	@Autowired
	private JobStatusService jobStatusService;
	
	
	public List<SchedulerJob> findAllByisActiveOrderByModifiedDateDesc(Integer isActive) {
		return scheduleSettingRepository.findAllByisActiveOrderByModifiedDateDesc(isActive);
	}

	public SchedulerJob saveSchedulerSettings(SchedulerJob schedulerJob) {
		return scheduleSettingRepository.save(schedulerJob);
	}

	public SchedulerJob findByJobId(Integer jobId) {
		return scheduleSettingRepository.findByJobId(jobId);
	}

	public SchedulerJob addSchedulerSettings(SchedulerJobRequest schedulerJobRequest, User user, JobType jobType,
			Repository repository, TimeInterval timeInterval) {
		SchedulerJob schedulerJob = null;
		if (schedulerJobRequest != null && user != null && repository != null && timeInterval != null) {
			schedulerJob = mapper.repareAddSchedulerJob(schedulerJobRequest, user, jobType, repository, timeInterval);
			saveSchedulerSettings(schedulerJob);
		}
		return schedulerJob;
	}

	public SchedulerJob updateSchedulerSettings(SchedulerJobRequest schedulerJobRequest, User user, JobType jobType,
			Repository repository, TimeInterval timeInterval) {
		SchedulerJob schedulerJobToUpdate = null;
		if (schedulerJobRequest != null && user != null && repository != null && timeInterval != null) {
			schedulerJobToUpdate = findByJobId(schedulerJobRequest.getJobId());
			schedulerJobToUpdate = mapper.prepareUpdateSchedulerJob(schedulerJobToUpdate, schedulerJobRequest, user,
					jobType, timeInterval, repository);
			saveSchedulerSettings(schedulerJobToUpdate);
		}
		return schedulerJobToUpdate;
	}

	public void updateLastRanTimestamp(SchedulerJob job) {	
		saveSchedulerSettings(job);
	}

	public SchedulerJob findByJobIdAndIsActive(Integer jobId, Integer isActive) {		
		return scheduleSettingRepository.findByJobIdAndIsActive(jobId, isActive);
	}

	public void updateJobStatus(int statucCode,Integer jobId) {
		SchedulerJob schedulerJobToUpdate = null;
		JobStatus jobStatus = null;
		try {			
			schedulerJobToUpdate = findByJobIdAndIsActive(jobId, Constants.ACTIVE_STATUS_ID);
			jobStatus = jobStatusService.findByJobStatusId(statucCode);
			schedulerJobToUpdate.setJobStatus(jobStatus);
			saveSchedulerSettings(schedulerJobToUpdate);
		} catch (Exception e) {
			logger.error("ERROR >>> "+e);
		}
		
	}

	/*
	 * public Boolean existsByRepositoryIdByisActive(Integer repositoryId, int
	 * isActive) { return
	 * scheduleSettingRepository.existsByRepositoryAndisActive(repositoryId,
	 * isActive); }
	 */
	}
