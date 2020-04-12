/**
 * 
 */
package com.scr.mapper;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.scr.message.request.SchedulerJobRequest;
import com.scr.model.JobType;
import com.scr.model.Repository;
import com.scr.model.SchedulerJob;
import com.scr.model.TimeInterval;
import com.scr.model.User;
import com.scr.util.Constants;

/**
 * @author vt1056
 *
 */
@Component
public class ScheduleSettingMapper {

	public SchedulerJob repareAddSchedulerJob(SchedulerJobRequest schedulerJobRequest, User user, JobType jobType,
			Repository repository, TimeInterval timeInterval) {
		SchedulerJob schedulerJob = new SchedulerJob();
		schedulerJob.setIsActive(Constants.ACTIVE_STATUS_ID);
		schedulerJob.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		schedulerJob.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		schedulerJob.setCreatedBy(user);
		schedulerJob.setModifiedBy(user);
		schedulerJob.setJobType(jobType);
		schedulerJob.setRepository(repository);
		schedulerJob.setTimeInterval(timeInterval);
		return schedulerJob;
	}

	public SchedulerJob prepareUpdateSchedulerJob(SchedulerJob schedulerJobToUpdate, SchedulerJobRequest schedulerJobRequest, User user, JobType jobType,
			TimeInterval timeInterval, Repository repository) {		
		schedulerJobToUpdate.setModifiedBy(user);
		schedulerJobToUpdate.setIsActive(Constants.ACTIVE_STATUS_ID);
		schedulerJobToUpdate.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		schedulerJobToUpdate.setJobId(schedulerJobRequest.getJobId());
		schedulerJobToUpdate.setJobType(jobType);
		schedulerJobToUpdate.setRepository(repository);
		schedulerJobToUpdate.setTimeInterval(timeInterval);
		return schedulerJobToUpdate;
	}

}
