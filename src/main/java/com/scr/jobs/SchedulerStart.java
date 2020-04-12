/**
 * 
 */
package com.scr.jobs;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scr.mapper.EmailMapper;
import com.scr.mapper.SMSMapper;
import com.scr.message.response.ResponseStatus;
import com.scr.model.DivisionsHistory;
import com.scr.model.JobsHistory;
import com.scr.model.MobileSMS;
import com.scr.model.SchedulerJob;
import com.scr.model.SchedulerJobsTracking;
import com.scr.model.SchedulerOperationTypesTracking;
import com.scr.repository.MobileSMSRepository;
import com.scr.services.DivisionHistoryService;
import com.scr.services.JobsHistoryService;
import com.scr.services.ScheduleSettingService;
import com.scr.services.SchedulerJobTrackingService;
import com.scr.services.SchedulerOperationTypesTrackingService;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@Component
public class SchedulerStart {

	@Autowired
	private ScheduleSettingService scheduleSettingService;

	@Autowired
	private DivisionsToTempJob divisionsToTempJob;

	@Autowired
	private TempToZonal tempToZonal;

	@Autowired
	private SchedulerJobTrackingService schedulerJobTrackingService;

	@Autowired
	private SchedulerOperationTypesTrackingService schedulerOperationTypesTrackingService;
	
	@Autowired
	private DivisionHistoryService divisionHistoryService;
	
	@Autowired
	private JobsHistoryService jobsHistoryService;
	
	@Autowired
	private CommonUtility commonUtility;

	@Autowired
	private EmailMapper emailMapper;

	@Autowired
	private SMSMapper smsMapper;
	
	@Autowired
	private MobileSMSRepository mobileSMSRepository;
	
	@Autowired
	DataSource dataSource;
	private static final Logger logger = Logger.getLogger(SchedulerStart.class);

	@Scheduled(cron = "0 22 18 * * *")
	public void scheduleStart() throws SQLException {

		try {
			logger.info(
					"\t ************************************* Scheduler Job Started *************************************");
			List<SchedulerJob> scheduledJobsList = scheduleSettingService
					.findAllByisActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);
			for (SchedulerJob job : scheduledJobsList) {
				int timeIntervalFrequency = Helper.validateJobTypeFrequency(job);
				if (timeIntervalFrequency != 0) {
					Timestamp lastRunTimestamp = job.getLastRunTimestamp();
					if (lastRunTimestamp == null
							|| Helper.checkTimeToProcess(lastRunTimestamp, timeIntervalFrequency)) {
						jobsStarted(job, Constants.RUN_SCHEDULER_TYPE, timeIntervalFrequency,
								Constants.RUN_SCHEDULER_TYPE);
						commonUtility.usersCreateUpdateDelete(job);
					} else {
						logger.info("\t Job already processed for this " + job.getRepository().getRepositoryCode()
								+ ". please wait for next time interval");
					}
				} else {
					logger.info("\t Please update proper time interval(Daily, Weekly, Monthly)");
				}				
			}
			if (scheduledJobsList.isEmpty()) {
				logger.info("\t\t No Jobs are configured. ");
			}
			logger.info(
					"\t ************************************* Scheduler Job Finished *************************************");
		} catch (Exception e) {
			logger.info(" ERROR >>> Failed Scheduler Job with " + e.getMessage());
		}
	}

	public void jobsStarted(SchedulerJob job, String runType, Integer timeIntervalFrequency, String runBy) {
		try {

			logger.info(" *************************************** Started for Division '"
					+ job.getRepository().getRepositoryName() + "' at " + Helper.currentTimeStamp()
					+ " *******************************************");
			logger.info("\t IP= " + job.getRepository().getRepositoryIp() + " \tDB Name= "
					+ job.getRepository().getRepositoryDbName() + " \tUser= " + job.getRepository().getRepositoryUser()
					+ " \tPassword= " + job.getRepository().getRepositoryPassword() + " \tPort= "
					+ job.getRepository().getRepositoryPort());
			logger.info("\t Frequency= " + timeIntervalFrequency);

			ResponseStatus connectionStatus = commonUtility.checkDBConnection(job);
			if (connectionStatus.getCode() == Constants.SUCCESS_CODE) {
				
				jobsRunning(job, runType, timeIntervalFrequency, runBy);
				
				
			} else {
				logger.info("\t\t Division " + job.getRepository().getRepositoryName() + " Failed "
						+ connectionStatus.getMessage());
				logger.info("\t\t Sending mail");
				MobileSMS emailResponse = emailMapper.sendDBConnectionFailedMail(connectionStatus, job);
				if (emailResponse.getSmsStatus().equalsIgnoreCase(String.valueOf(Constants.SUCCESS_CODE))) {
					try {
						logger.info("Mail status updating into Database.");
						mobileSMSRepository.save(emailResponse);
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					logger.info("\t\t Mail sent successfully.");
				}
				else {
					mobileSMSRepository.save(emailResponse);
					logger.info("\\t\t Sending mail failed.");
				}
				
				logger.info("\t\t Sending SMS");
				MobileSMS smsResponse = smsMapper.sendSMS(connectionStatus, job);
				if (smsResponse.getSmsStatus().equalsIgnoreCase(String.valueOf(Constants.SUCCESS_CODE))) {
					try {
						logger.info("Mail status updating into Database.");
					mobileSMSRepository.save(smsResponse);
					}catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("\t\t SMS sent successfully.");
				}
				else {
					mobileSMSRepository.save(smsResponse);
					logger.info("\\t\t Sending SMS failed.");
				}
				
				
			}

			logger.info(" *************************************** Finished for Division '"
					+ job.getRepository().getRepositoryName() + "' at " + Helper.currentTimeStamp()
					+ " *******************************************");

		} catch (Exception e) {

		}

	}
	
	public void runFailedTablesOnly(SchedulerJob schedulerJob, String runRerunType, int timeIntervalFrequency,
			String username, String processedDate) {
		try {			
			
			List<SchedulerJobsTracking> schedulerJobsTracking = schedulerJobTrackingService.findByJobIdAndProcessedDate(schedulerJob, java.sql.Timestamp.valueOf(processedDate));
			List<SchedulerOperationTypesTracking> operationTypesTrackings = schedulerOperationTypesTrackingService.findByTrackingIdOrderByProcessedDateAsc(schedulerJobsTracking.get(0).getTrackingId());
			LinkedHashMap<String, List<JobsHistory>> jHashMap = new LinkedHashMap<>();
			for(SchedulerOperationTypesTracking tracking : operationTypesTrackings) {
				List<JobsHistory> jobs =  jobsHistoryService.findByOperationId(tracking.getOperationId());
				jHashMap.put(tracking.getOperationId()+"-"+tracking.getJobType(), jobs);
				
			}
			LinkedHashMap<String, List<DivisionsHistory>> diHashMap = new LinkedHashMap<>();
			for (Map.Entry<String, List<JobsHistory>> entry : jHashMap.entrySet()) {
				for(JobsHistory history : entry.getValue()) {
					List<DivisionsHistory> diHistories = divisionHistoryService.findByJobsHistoryIdAndStatusNotIn(history.getId(), Constants.JOB_SUCCESS_MESSAGE);
					if(diHistories != null && !diHistories.isEmpty()) {
						diHashMap.put(entry.getKey()+"-"+history.getId(), diHistories);
						runFailedTables(entry.getKey().split("-")[1], history, history.getOperationType(), diHistories, schedulerJob, entry.getKey().split("-")[0]);
						for(DivisionsHistory divisionsHistory: diHistories) {
							System.out.println(divisionsHistory.getJobsHistoryId());
						}
					}
				}
			}
			
		}catch (Exception e) {
			logger.info("ERROR >>> "+e.getMessage());
		}
	}

	private void runFailedTables(String jobtype, JobsHistory history, String operationType, List<DivisionsHistory> diHistories, SchedulerJob schedulerJob, String operationId) {
		try {
			if(jobtype.equalsIgnoreCase(Constants.SCHEDULER_DIVISION_TO_TEMP_JOB)) {
				//if(history.getOperationType().equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_INSERT)) {
				ResponseStatus  responseStatus =	divisionsToTempJob.runFailed(diHistories, jobtype, schedulerJob, operationId, operationType);
				/*}else if(history.getOperationType().equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_UPDATE)) {
					
				}else if(history.getOperationType().equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_DELETE)) {
					
				}*/
			}else if(jobtype.equalsIgnoreCase(Constants.SCHEDULER_TEMP_TO_ZONAL_JOB)) {
				ResponseStatus  responseStatus =	tempToZonal.runFailed(diHistories, jobtype, schedulerJob, operationId, operationType);
				
				if(history.getOperationType().equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_INSERT)) {
					
				}else if(history.getOperationType().equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_UPDATE)) {
					
				}else if(history.getOperationType().equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_DELETE)) {
					
				}
			}
		}catch (Exception e) {
			logger.info("ERROR >>> "+e.getMessage());
		}
		
	}

	private void jobsRunning(SchedulerJob job, String runType, Integer timeIntervalFrequency, String runBy) {

		try {
			logger.info("\t\t Updating Job Status.");
			SchedulerJobsTracking schedulerJobsTrackingToUpdate = schedulerJobTrackingService
					.updateJobsHistoryStartStatus(job.getJobType(), job, job.getRepository(), Helper.currentTimeStamp(),
							Helper.currentTime(), Constants.JOB_PROCESSING_STATUS, runType, job.getTimeInterval(),
							runBy);
			logger.info("\t\t Updated Job Status as PROCESSING in scheduler job tracking table.");

			logger.info("\t\t\t Job " + Constants.SCHEDULER_DIVISION_TO_TEMP_JOB + " Started.");
			ResponseStatus divisionToTempResponse = divisionsToTempJob.startDivisionsToTempJob(job, runType,
					timeIntervalFrequency, schedulerJobsTrackingToUpdate.getTrackingId(),
					Constants.SCHEDULER_DIVISION_TO_TEMP_JOB);
			logger.info("\t\t\t Job " + Constants.SCHEDULER_DIVISION_TO_TEMP_JOB + " Finished.");
			ResponseStatus tempToZonalResponse = null;
			//if (job.getJobType().getJobTypeName().equalsIgnoreCase(Constants.SCHEDULER_TEMP_TO_ZONAL_JOB)) {

				logger.info("\t\t\t Job " + Constants.SCHEDULER_TEMP_TO_ZONAL_JOB + " Started.");
				tempToZonalResponse = tempToZonal.startJobTempToZonalProcess(job, runType,
						timeIntervalFrequency, schedulerJobsTrackingToUpdate.getTrackingId(),
						Constants.SCHEDULER_TEMP_TO_ZONAL_JOB);
				logger.info("\t\t\t Job " + Constants.SCHEDULER_TEMP_TO_ZONAL_JOB + " Finished.");
			//}
			logger.info("\t\t Updating Job Status.");
			if (divisionToTempResponse.getCode() == Constants.SUCCESS_CODE) {
				if(tempToZonalResponse != null && tempToZonalResponse.getCode() == Constants.SUCCESS_CODE) {
					schedulerJobsTrackingToUpdate = schedulerJobTrackingService.updateJobsHistoryCompletedStatus(
						schedulerJobsTrackingToUpdate, Helper.currentTime(), Constants.JOB_COMPLETED_STATUS,
				Constants.JOB_SUCCESS_MESSAGE);
				}
				else if(tempToZonalResponse != null && tempToZonalResponse.getCode() != Constants.SUCCESS_CODE) {
					schedulerJobsTrackingToUpdate = schedulerJobTrackingService.updateJobsHistoryCompletedStatus(
							schedulerJobsTrackingToUpdate, Helper.currentTime(), Constants.JOB_COMPLETED_STATUS,
					tempToZonalResponse.getMessage());
				}
				else if(tempToZonalResponse == null) {
					schedulerJobsTrackingToUpdate = schedulerJobTrackingService.updateJobsHistoryCompletedStatus(
							schedulerJobsTrackingToUpdate, Helper.currentTime(), Constants.JOB_COMPLETED_STATUS,
					Constants.JOB_SUCCESS_MESSAGE);
				}
			}
			else
				schedulerJobsTrackingToUpdate = schedulerJobTrackingService.updateJobsHistoryCompletedStatus(
						schedulerJobsTrackingToUpdate, Helper.currentTime(), Constants.JOB_COMPLETED_STATUS,
						Constants.JOB_FAILED_MESSAGE);
			logger.info("\t\t Updated Job Status as COMPLETED in scheduler job tracking table.");

			job.setLastRunTimestamp(Helper.currentTimeStamp());
			scheduleSettingService.updateLastRanTimestamp(job);
			logger.info("\t\t Update last run timestamp in scheduler jobs table.");
		} catch (Exception e) {

		}

	}

	

}
