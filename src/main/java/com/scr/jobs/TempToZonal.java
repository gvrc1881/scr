/**
 * 
 */
package com.scr.jobs;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.jobs.response.ExecuteInsertReaponse;
import com.scr.jobs.response.InsertQueriesResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.DivisionsHistory;
import com.scr.model.JobType;
import com.scr.model.JobsHistory;
import com.scr.model.Repository;
import com.scr.model.SchedulerJob;
import com.scr.model.SchedulerOperationTypesTracking;
import com.scr.model.TimeInterval;
import com.scr.services.DivisionHistoryService;
import com.scr.services.JobsHistoryService;
import com.scr.services.SchedulerOperationTypesTrackingService;
import com.scr.util.Constants;
import com.scr.util.Helper;
import com.scr.util.PredicateUtil;

/**
 * @author vt1056
 *
 */
@Component
public class TempToZonal {
private static final Logger logger = Logger.getLogger(TempToZonal.class);
	
	@Autowired
	private DivisionHistoryService divisionHistoryService;
	
	@Autowired
	private JobsHistoryService jobsHistoryService;	
	
	@Autowired
	private SchedulerOperationTypesTrackingService schedulerOperationTypesTrackingService;
	
	@Autowired
	private CommonUtility commonUtility;
	
	/**
	 * 
	 * @param job
	 * @param runType
	 * @param timeIntervalFrequency
	 * @param trackingId
	 * @param schedulerTempToZonalJob
	 */
	public ResponseStatus startJobTempToZonalProcess(SchedulerJob job, String runType, int timeIntervalFrequency, Integer trackingId, String schedulerTempToZonalJob) {
		ResponseStatus response = new ResponseStatus();
		try {
			logger.info("Frequency: "+timeIntervalFrequency);
			
			
			Repository repository = job.getRepository();
			JobType jobType = job.getJobType();
			TimeInterval timeInterval = job.getTimeInterval();	
			String divisionSchema = job.getRepository().getRepositoryCode();
			String zonalSchema = "public";			
			
			logger.info("\t\t Updating " + schedulerTempToZonalJob +" Job  Status as PROCESSING.");			
			SchedulerOperationTypesTracking schedulerOperationTypesTrackingToUpdate= schedulerOperationTypesTrackingService.saveOperationTypeDetatils(trackingId, schedulerTempToZonalJob, Helper.currentTime(), divisionSchema, "",zonalSchema, Constants.JOB_PROCESSING_STATUS);			
			logger.info("\t\t Updated " + schedulerTempToZonalJob +" Job Status as PROCESSING.");

			
			if(runType.equalsIgnoreCase(Constants.RUN_RELOAD_TYPE)) {
				ResponseStatus deleteStatus = commonUtility.deleteZonalTablesData(divisionSchema, zonalSchema);
				if(deleteStatus.getCode() == Constants.SUCCESS_CODE) {
					logger.info("\t\t	Deleted division "+ divisionSchema +" tables data in  zonal");
				}
			}
			
			// EXECUTE INSERT QUERIES
			ResponseStatus insertResponse = runInsertUpdateDeleteOperation(job, repository,jobType,timeInterval, Constants.SCHEDULED_JOBS_OPERATION_INSERT, schedulerTempToZonalJob, divisionSchema, zonalSchema, runType, trackingId, schedulerOperationTypesTrackingToUpdate.getOperationId(), null);
			
			// EXECUTE UPDATE QUERIES
			ResponseStatus updateResponse = runInsertUpdateDeleteOperation(job, repository,jobType,timeInterval, Constants.SCHEDULED_JOBS_OPERATION_UPDATE, schedulerTempToZonalJob, divisionSchema, zonalSchema, runType, trackingId, schedulerOperationTypesTrackingToUpdate.getOperationId(), null);
			
			//EXECUTE DELETE QUERIES
			ResponseStatus deleteResponse = runInsertUpdateDeleteOperation(job, repository,jobType,timeInterval, Constants.SCHEDULED_JOBS_OPERATION_DELETE, schedulerTempToZonalJob, divisionSchema, zonalSchema, runType, trackingId, schedulerOperationTypesTrackingToUpdate.getOperationId(), null);
			
			if(insertResponse.getCode() == Constants.SUCCESS_CODE &&
					updateResponse.getCode() == Constants.SUCCESS_CODE && 
					deleteResponse.getCode() == Constants.SUCCESS_CODE) {
				logger.info("\t\t Updating " + schedulerTempToZonalJob + " Job  Status as COMPLETED.");
				schedulerOperationTypesTrackingService.updateOperationTypeStatus(
						schedulerOperationTypesTrackingToUpdate, Constants.JOB_COMPLETED_STATUS,
						Helper.currentTime(), Constants.JOB_SUCCESS_MESSAGE);
				logger.info("\t\t Updated " + schedulerTempToZonalJob + " Job  Status as COMPLETED.");
			}else {
				logger.info("\t\t Updating " + schedulerTempToZonalJob + " Job  Status as COMPLETED.");
				schedulerOperationTypesTrackingService.updateOperationTypeStatus(
						schedulerOperationTypesTrackingToUpdate, Constants.JOB_COMPLETED_STATUS,
						Helper.currentTime(), Constants.JOB_FAILED_MESSAGE);
				logger.info("\t\t Updated " + schedulerTempToZonalJob + " Job  Status as COMPLETED.");
			}
			response.setCode(Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.info(" >>> ERROR "+e.getMessage());
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage("ERROR >>> "+e);
		}
		return response;
	}
	
	public ResponseStatus runFailed(List<DivisionsHistory> diHistories, String jobtype, SchedulerJob schedulerJob,
			String operationId, String operationType) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			List<String> taList = PredicateUtil.findFailedTables(diHistories, Constants.JOB_SUCCESS_MESSAGE);
			//if(operationType.equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_INSERT)) {
				responseStatus = runInsertUpdateDeleteOperation(schedulerJob, schedulerJob.getRepository(), schedulerJob.getJobType(), schedulerJob.getTimeInterval(), operationType, Constants.SCHEDULER_TEMP_TO_ZONAL_JOB, schedulerJob.getRepository().getRepositoryCode(),"zonal","", 0, Integer.parseInt(operationId), taList);
			//}else if(operationType.equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_UPDATE)) {
				//responseStatus = runDivisionToTempOperations(schedulerJob, jobtype, "last_updated_tx_stamp", schedulerJob.getLastRunTimestamp().toString(), schedulerJob.getRepository().getRepositoryCode(), "public", Integer.parseInt(operationId), taList);
			//}else if(operationType.equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_DELETE)) {
				//responseStatus = runDivisionToTempDeleteOperations(schedulerJob, jobtype, "last_updated_tx_stamp", schedulerJob.getLastRunTimestamp().toString(), schedulerJob.getRepository().getRepositoryCode(), "public", Integer.parseInt(operationId), taList);
			//}
			
		}
		catch (Exception e) {
			
		}
		return responseStatus;
	}
	
	/**
	 * 
	 * @param job
	 * @param repository
	 * @param jobType
	 * @param timeInterval
	 * @param operationType
	 * @param divisionSchema
	 * @param zonalSchema
	 * @param schedulerTempToZonalJob
	 * @param runType
	 * @param trackingId
	 * @param operationId 
	 * @param taList 
	 */
	private ResponseStatus runInsertUpdateDeleteOperation(SchedulerJob job, Repository repository, JobType jobType, TimeInterval timeInterval,
			String operationType, String schedulerTempToZonalJob, 
			String divisionSchema, String zonalSchema,  String runType,
			Integer trackingId, Integer operationId, List<String> taList) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
					
		switch (operationType) {
		case Constants.SCHEDULED_JOBS_OPERATION_INSERT:
			 responseStatus = tempToZonalInsert(zonalSchema, divisionSchema, jobType, timeInterval, repository, operationId, operationType, taList);
			break;
		case Constants.SCHEDULED_JOBS_OPERATION_UPDATE:
			responseStatus = tempToZonalUpdate(zonalSchema, divisionSchema, jobType, timeInterval, repository, operationId, operationType, taList);
			break;
		case Constants.SCHEDULED_JOBS_OPERATION_DELETE:
			responseStatus = tempToZonalDelete(zonalSchema, divisionSchema, jobType, timeInterval, repository, operationId, operationType, taList);
			break;
		default:
			break;
		}
	}catch (Exception e) {
		logger.error("ERROR >>> "+e);
	}
		return responseStatus;
	}
	
	/**
	 * 
	 * @param zonalSchema
	 * @param divisionSchema
	 * @param jobType
	 * @param timeInterval
	 * @param repository
	 * @param operationId
	 * @param operationType
	 * @param taList 
	 * @return
	 */
	private ResponseStatus tempToZonalDelete(String zonalSchema, String divisionSchema, JobType jobType,
			TimeInterval timeInterval, Repository repository, Integer operationId,
			String operationType, List<String> taList) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			logger.info("\t\t\t Started Operation "+Constants.SCHEDULED_JOBS_OPERATION_DELETE);			
			JobsHistory jobsHistory= null;
			if(taList == null) {
			logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as PROCESSING.");			
			jobsHistory = jobsHistoryService.updateJobsHistoryStartStatus(operationId, operationType, Helper.currentTime(), Helper.currentTimeStamp(), Constants.JOB_PROCESSING_STATUS);
			logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as PROCESSING.");	
			}else {
				jobsHistory = jobsHistoryService.findByOperationIdAndOperationType(operationId, operationType);
			}
			
			InsertQueriesResponse insertQueriesResponse = commonUtility.findDeleteQueryTempToZonal(zonalSchema, divisionSchema);
			int successTablesCount = 0;
			int failedTablesCount = 0;			
			if(insertQueriesResponse.getCode() == Constants.SUCCESS_CODE) {
				
				boolean divisionFlag = true;				
				for (Map.Entry<String, String> queries : insertQueriesResponse.getInsertQueries().entrySet()) {
					String tableName = "";
					String query = "";
					ExecuteInsertReaponse response = null;
					try {
						tableName = queries.getKey();	
						if(taList != null && !taList.contains(tableName))
							continue;
						
						query = queries.getValue();
						logger.info("\t\t\t **********************************************************************************");
						logger.info("\t\t\t DB Link Query ="+query);
						logger.info("\t\t\t Table Name= "+tableName);	
						
						response = commonUtility.executeInsertQuery(query);						
						
						logger.info("\t\t\t Records: " + response.getRecords());
						
						logger.info("\t\t\t Status: " + response.getStatus());
						if(response.getStatusCode() == Constants.SUCCESS_CODE)
							successTablesCount += 1;
						else {
							divisionFlag = false;
							failedTablesCount += 1;
						}
						divisionHistoryService.saveDivisionHistoryDetails(tableName, repository.getRepositoryCode(), jobsHistory.getId(), response.getRecords(), response.getStatus(), Constants.SCHEDULED_JOBS_OPERATION_DELETE, taList);
						logger.info("\t\t\t Saved data divisions history successfully for table "+tableName);
						logger.info("\t\t\t **********************************************************************************\n");
						// added by adiReddy
						responseStatus.setCode(Constants.SUCCESS_CODE);
					}catch (Exception e) {
						responseStatus.setCode(Constants.FAILURE_CODE);
					}
				}
				if(divisionFlag) {
					logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
					jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_SUCCESS_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_DELETE, taList);
					logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");				
				}
				else {
					logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
					jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_FAILED_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount,  Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_DELETE, taList);
					logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");					
				}
				logger.info("\t\t Saved data jobs history successfully for Division "+repository.getRepositoryName());
				
				
			}else {
				responseStatus.setCode(Constants.FAILURE_CODE);
				responseStatus.setMessage(insertQueriesResponse.getMessage());
				
				logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
				jobsHistoryService.updateJobStatus(jobsHistory, insertQueriesResponse.getMessage(),Helper.currentTime(), successTablesCount, failedTablesCount,  Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_INSERT, taList);
				logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");	
				
			}
			logger.info("\t\t\t Finished Operation "+Constants.SCHEDULED_JOBS_OPERATION_DELETE);
		}catch (Exception e) {
			responseStatus.setCode(Constants.FAILURE_CODE);
			responseStatus.setMessage("ERROR >>> "+e);
		}
		return responseStatus;
	}
	
	/**
	 * 
	 * @param zonalSchema
	 * @param divisionSchema
	 * @param jobType
	 * @param timeInterval
	 * @param repository
	 * @param operationId
	 * @param operationType
	 * @param taList 
	 * @return
	 */
	private ResponseStatus tempToZonalUpdate(String zonalSchema, String divisionSchema, JobType jobType,
			TimeInterval timeInterval, Repository repository, Integer operationId, 
			String operationType, List<String> taList) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			logger.info("\t\t\t Started Operation "+Constants.SCHEDULED_JOBS_OPERATION_UPDATE);
			JobsHistory jobsHistory = null;
			
			if(taList == null) {
			logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as PROCESSING.");			
			jobsHistory = jobsHistoryService.updateJobsHistoryStartStatus(operationId, operationType, Helper.currentTime(), Helper.currentTimeStamp(), Constants.JOB_PROCESSING_STATUS);
			logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as PROCESSING.");	
			}
			else {
				jobsHistory = jobsHistoryService.findByOperationIdAndOperationType(operationId, operationType);
			}
			
			InsertQueriesResponse insertQueriesResponse = commonUtility.findUpdateQueryZonalToTemp(zonalSchema, divisionSchema);
			int successTablesCount = 0;
			int failedTablesCount = 0;			
			if(insertQueriesResponse.getCode() == Constants.SUCCESS_CODE) {
				boolean divisionFlag = true;				
				for (Map.Entry<String, String> queries : insertQueriesResponse.getInsertQueries().entrySet()) {
					String tableName = "";
					String query = "";
					ExecuteInsertReaponse response = null;
					try {
						tableName = queries.getKey();		
						if(taList != null && !taList.contains(tableName))
							continue;
						
						query = queries.getValue();
						logger.info("\t\t\t **********************************************************************************");
						logger.info("\t\t\t DB Link Query ="+query);
						logger.info("\t\t\t Table Name= "+tableName);	
						
						response = commonUtility.executeInsertQuery(query);						
						
						logger.info("\t\t\t Records: " + response.getRecords());
						
						logger.info("\t\t\t Status: " + response.getStatus());
						if(response.getStatusCode() == Constants.SUCCESS_CODE)
							successTablesCount += 1;
						else {
							divisionFlag = false;
							failedTablesCount += 1;
						}
						divisionHistoryService.saveDivisionHistoryDetails(tableName, repository.getRepositoryCode(), jobsHistory.getId(), response.getRecords(), response.getStatus(), Constants.SCHEDULED_JOBS_OPERATION_UPDATE, taList);
						logger.info("\t\t\t Saved data divisions history successfully for table "+tableName);
						logger.info("\t\t\t **********************************************************************************\n");
						// added by adiReddy
						responseStatus.setCode(Constants.SUCCESS_CODE);
					}catch (Exception e) {
						responseStatus.setCode(Constants.FAILURE_CODE);
					}
				}
				if(divisionFlag) {					
					logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
					jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_SUCCESS_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_UPDATE, taList);
					logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");
				}
				else {
					logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
					jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_FAILED_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount,  Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_UPDATE, taList);
					logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");					
				}
				logger.info("\t\t Saved data jobs history successfully for Division "+repository.getRepositoryName());
				
				
			}else {
				responseStatus.setCode(Constants.FAILURE_CODE);
				responseStatus.setMessage(insertQueriesResponse.getMessage());
				
				logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
				jobsHistoryService.updateJobStatus(jobsHistory, insertQueriesResponse.getMessage(),Helper.currentTime(), successTablesCount, failedTablesCount,  Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_INSERT, taList);
				logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");	
				
			}
			logger.info("\t\t\t Finished Operation "+Constants.SCHEDULED_JOBS_OPERATION_UPDATE);
		}catch (Exception e) {
			responseStatus.setCode(Constants.FAILURE_CODE);
			responseStatus.setMessage("ERROR >>> "+e);
		}
		return responseStatus;
	}
	
	/**
	 * 
	 * @param zonalSchema
	 * @param divisionSchema
	 * @param jobType
	 * @param timeInterval
	 * @param repository
	 * @param operationId
	 * @param operationType
	 * @param taList 
	 * @return
	 */
	private ResponseStatus tempToZonalInsert(String zonalSchema, 
			String divisionSchema, JobType jobType, TimeInterval timeInterval, 
			Repository repository,  Integer operationId, String operationType, 
			List<String> taList) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			logger.info("\t\t\t Started Operation "+Constants.SCHEDULED_JOBS_OPERATION_INSERT);
			JobsHistory jobsHistory = null;
			
			if(taList == null) {
			logger.info("\t\t\t\t Updating Job Operation Type "+ operationType +" Status as PROCESSING.");			
			jobsHistory = jobsHistoryService.updateJobsHistoryStartStatus(operationId, operationType, Helper.currentTime(), Helper.currentTimeStamp(), Constants.JOB_PROCESSING_STATUS);
			logger.info("\t\t\t\t Updated Job Operation Type "+ operationType +" Status as PROCESSING.");	
			}else {
				jobsHistory = jobsHistoryService.findByOperationIdAndOperationType(operationId, operationType);
			}
			InsertQueriesResponse insertQueriesResponse = commonUtility.findInsertionQueryZonalToTemp(zonalSchema, divisionSchema);
			int successTablesCount = 0;
			int failedTablesCount = 0;
			if(insertQueriesResponse.getCode() == Constants.SUCCESS_CODE) {
				boolean divisionFlag = true;				
				for (Map.Entry<String, String> queries : insertQueriesResponse.getInsertQueries().entrySet()) {
					String tableName = "";
					String query = "";
					ExecuteInsertReaponse response = null;
					try {
						tableName = queries.getKey();	
						if(taList != null && !taList.contains(tableName))
							continue;
						query = queries.getValue();
						logger.info("\t\t\t **********************************************************************************");
						logger.info("\t\t\t DB Link Query ="+query);
						logger.info("\t\t\t Table Name= "+tableName);	
						
						response = commonUtility.executeInsertQuery(query);						
						
						logger.info("\t\t\t Records: " + response.getRecords());
						
						logger.info("\t\t\t Status: " + response.getStatus());
						if(response.getStatusCode() == Constants.SUCCESS_CODE)
							successTablesCount += 1;
						else {
							divisionFlag = false;
							failedTablesCount += 1;
						}
						divisionHistoryService.saveDivisionHistoryDetails(tableName, repository.getRepositoryCode(), jobsHistory.getId(), response.getRecords(), response.getStatus(), Constants.SCHEDULED_JOBS_OPERATION_INSERT, taList);
						logger.info("\t\t\t Saved data divisions history successfully for table "+tableName);
						logger.info("\t\t\t **********************************************************************************\n");
					}catch (Exception e) {
						
					}
				}
				if(divisionFlag) {
					logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
					jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_SUCCESS_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_INSERT, taList);
					logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");
				}
				else {
					logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
					jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_FAILED_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount,  Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_INSERT, taList);
					logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");
				}
				logger.info("\t\t Saved data jobs history successfully for Division "+repository.getRepositoryName());
				responseStatus.setCode(Constants.SUCCESS_CODE);
				responseStatus.setMessage(Constants.JOB_SUCCESS_MESSAGE);
				
			}else {
				responseStatus.setCode(Constants.FAILURE_CODE);
				responseStatus.setMessage(insertQueriesResponse.getMessage());
				logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
				jobsHistoryService.updateJobStatus(jobsHistory, insertQueriesResponse.getMessage(),Helper.currentTime(), successTablesCount, failedTablesCount,  Constants.PROCESS_COMPLETED, Constants.SCHEDULED_JOBS_OPERATION_INSERT, taList);
				logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");	
				
				logger.info("\t\t Saved data jobs history successfully for Division "+repository.getRepositoryName());
			}
			logger.info("\t\t\t Finished Operation "+Constants.SCHEDULED_JOBS_OPERATION_INSERT);
		}catch (Exception e) {
			responseStatus.setCode(Constants.FAILURE_CODE);
			responseStatus.setMessage("ERROR >>> "+e);
		}
		return responseStatus;
	}

	

}
