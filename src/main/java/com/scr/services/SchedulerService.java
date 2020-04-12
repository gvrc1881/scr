/**
 * 
 */
package com.scr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.jobs.SchedulerStart;
import com.scr.message.response.ResponseStatus;
import com.scr.model.SchedulerJob;
import com.scr.model.User;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@Service
public class SchedulerService {

	@Autowired
	private SchedulerStart start = null;

	@Autowired
	private ScheduleSettingService scheduleSettingService;

	public ResponseStatus runSchedulerJob() {
		ResponseStatus status = new ResponseStatus();
		try {
			start.scheduleStart();
			status = Helper.findResponseStatus(Constants.JOB_SUCCESS_MESSAGE, Constants.SUCCESS_CODE);
		} catch (Exception e) {
			status = Helper.findResponseStatus("FAILED", Constants.FAILURE_CODE);
		}
		return status;
	}

	public ResponseStatus reRunByIdByType(Integer runTypeId, Integer jobId, User user, String processedDate) {
		ResponseStatus status = new ResponseStatus();
		try {
			SchedulerJob schedulerJob = scheduleSettingService.findByJobId(jobId);		
			int timeIntervalFrequency = Helper.validateJobTypeFrequency(schedulerJob);				
			if(timeIntervalFrequency != 0) {
				switch (runTypeId) {
				case 1:
					start.runFailedTablesOnly(schedulerJob, Constants.RUN_RERUN_TYPE, timeIntervalFrequency, user.getUsername(), processedDate);
					//start.jobsStarted(schedulerJob, Constants.RUN_RERUN_TYPE, timeIntervalFrequency, user.getUsername());
					break;
				case 2:
					//start.startReloadJob(schedulerJob, Constants.RUN_RELOAD_TYPE, user);
					start.jobsStarted(schedulerJob, Constants.RUN_RELOAD_TYPE, timeIntervalFrequency, user.getUsername());
					break;
				default:
					break;
				}
				status = Helper.findResponseStatus(Constants.JOB_SUCCESS_MESSAGE, Constants.SUCCESS_CODE);
			}
			else {
				status = Helper.findResponseStatus("\t Please update proper time interval(Daily, Weekly, Monthly)", Constants.FAILURE_CODE);
			}
		} catch (Exception e) {
			status = Helper.findResponseStatus("FAILED", Constants.FAILURE_CODE);
		}
		return status;
	}

}
