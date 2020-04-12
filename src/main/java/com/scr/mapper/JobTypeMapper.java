/**
 * 
 */
package com.scr.mapper;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.scr.message.request.JobTypeRequest;
import com.scr.model.JobType;
import com.scr.model.User;
import com.scr.util.Constants;

/**
 * @author venkat
 *
 */
@Component
public class JobTypeMapper {

	public JobType prepareAddJobTypeMapper(JobTypeRequest jobTypeRequest, User user) {
		JobType jobType = new JobType();
		jobType.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		jobType.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		jobType.setJobTypeName(jobTypeRequest.getJobTypeName());
		jobType.setModifiedBy(user);
		jobType.setCreatedBy(user);
		jobType.setIsActive(Constants.ACTIVE_STATUS_ID);
		return jobType;
	}

	public JobType prepareUpdateJobTypeMapper(JobType jobTypeToUpdate, JobTypeRequest jobTypeRequest, User user) {
		jobTypeToUpdate.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		jobTypeToUpdate.setJobTypeName(jobTypeRequest.getJobTypeName());
		jobTypeToUpdate.setModifiedBy(user);
		return jobTypeToUpdate;
	}

}
