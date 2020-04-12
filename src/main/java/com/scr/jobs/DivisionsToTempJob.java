/**
 * 
 */
package com.scr.jobs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.jobs.response.ExecuteInsertReaponse;
import com.scr.jobs.response.InsertQueriesResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.DivisionsHistory;
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
public class DivisionsToTempJob {

	private static final Logger logger = Logger.getLogger(DivisionsToTempJob.class);
	
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
	 * @param schedulerDivisionToTempJob
	 * @throws SQLException
	 */
	public ResponseStatus startDivisionsToTempJob(SchedulerJob job, String runType, Integer timeIntervalFrequency, Integer trackingId, String schedulerDivisionToTempJob) throws SQLException {		
		ResponseStatus response = new ResponseStatus();		
		try {
						
			Repository repository = job.getRepository();
			TimeInterval timeInterval = job.getTimeInterval();				
				
			String fromDate = Helper.getFromDate(timeIntervalFrequency);			
			if (runType.equalsIgnoreCase(Constants.RUN_RELOAD_TYPE) || job.getLastRunTimestamp() == null)
				fromDate = "2000-01-01 00:00:00+05:30";
			else
				//fromDate = Helper.getStringFromTimeStamp(job.getLastRunTimestamp());
				fromDate = job.getLastRunTimestamp().toString();
			
			String tempSchema = job.getRepository().getRepositoryCode();
			String divisionSchema = "public";
			
			logger.info("\t\t Updating " + schedulerDivisionToTempJob +" Job  Status as PROCESSING.");			
			SchedulerOperationTypesTracking schedulerOperationTypesTrackingToUpdate= schedulerOperationTypesTrackingService.saveOperationTypeDetatils(trackingId, schedulerDivisionToTempJob, Helper.currentTime(), divisionSchema, tempSchema,"", Constants.JOB_PROCESSING_STATUS);			
			logger.info("\t\t Updated " + schedulerDivisionToTempJob +" Job Status as PROCESSING.");
			
			/*if(runType.equalsIgnoreCase(Constants.RUN_RELOAD_TYPE)) {
				ResponseStatus status = 
			}
			*/
			
			// EXECUTE INSERT QUERIES
			ResponseStatus insertResponse = startInsertUpdateDeleteOperations(job, repository, timeInterval, fromDate, runType, trackingId, schedulerDivisionToTempJob, Constants.SCHEDULED_JOBS_OPERATION_INSERT, tempSchema, divisionSchema, schedulerOperationTypesTrackingToUpdate.getOperationId());
			
			// EXECUTE UPDATE QUERIES
			ResponseStatus updateResponse = startInsertUpdateDeleteOperations(job, repository, timeInterval, fromDate, runType, trackingId, schedulerDivisionToTempJob, Constants.SCHEDULED_JOBS_OPERATION_UPDATE, tempSchema, divisionSchema, schedulerOperationTypesTrackingToUpdate.getOperationId());
			
			//EXECUTE DELETE QUERIES
			ResponseStatus deleteResponse = startInsertUpdateDeleteOperations(job, repository, timeInterval, fromDate, runType, trackingId, schedulerDivisionToTempJob, Constants.SCHEDULED_JOBS_OPERATION_DELETE, tempSchema, divisionSchema, schedulerOperationTypesTrackingToUpdate.getOperationId());
			
			if(insertResponse.getCode() == Constants.SUCCESS_CODE &&
					updateResponse.getCode() == Constants.SUCCESS_CODE && 
					deleteResponse.getCode() == Constants.SUCCESS_CODE) {
				logger.info("\t\t Updating " + schedulerDivisionToTempJob + " Job  Status as COMPLETED.");
				schedulerOperationTypesTrackingService.updateOperationTypeStatus(
						schedulerOperationTypesTrackingToUpdate, Constants.JOB_COMPLETED_STATUS,
						Helper.currentTime(), Constants.JOB_SUCCESS_MESSAGE);
				logger.info("\t\t Updated " + schedulerDivisionToTempJob + " Job  Status as COMPLETED.");
			}else {
				logger.info("\t\t Updating " + schedulerDivisionToTempJob + " Job  Status as COMPLETED.");
				schedulerOperationTypesTrackingService.updateOperationTypeStatus(
						schedulerOperationTypesTrackingToUpdate, Constants.JOB_COMPLETED_STATUS,
						Helper.currentTime(),  Constants.JOB_FAILED_MESSAGE);
				logger.info("\t\t Updated " + schedulerDivisionToTempJob + " Job  Status as COMPLETED.");
			}
			response.setCode(Constants.SUCCESS_CODE);
		}catch (Exception e) {
			logger.info(" >>> ERROR "+e.getMessage());
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(">>> ERROR "+e);
		}
		return response;
	}
	

public ResponseStatus runFailed(List<DivisionsHistory> diHistories, String jobtype, 
		SchedulerJob schedulerJob, String operationId, String operationType) {
		ResponseStatus responseStatus = new ResponseStatus();
		logger.info("jobtype::::"+jobtype);
		try {
			List<String> taList = PredicateUtil.findFailedTables(diHistories, Constants.JOB_SUCCESS_MESSAGE);
			if(operationType.equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_INSERT)) {
				responseStatus = runDivisionToTempOperations(schedulerJob, Constants.SCHEDULED_JOBS_OPERATION_INSERT, "created_tx_stamp", schedulerJob.getLastRunTimestamp().toString(), schedulerJob.getRepository().getRepositoryCode(), "public", Integer.parseInt(operationId), taList);				
			}else if(operationType.equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_UPDATE)) {
				responseStatus = runDivisionToTempOperations(schedulerJob, Constants.SCHEDULED_JOBS_OPERATION_UPDATE, "last_updated_tx_stamp", schedulerJob.getLastRunTimestamp().toString(), schedulerJob.getRepository().getRepositoryCode(), "public", Integer.parseInt(operationId), taList);
			}else if(operationType.equalsIgnoreCase(Constants.SCHEDULED_JOBS_OPERATION_DELETE)) {
				responseStatus = runDivisionToTempDeleteOperations(schedulerJob, Constants.SCHEDULED_JOBS_OPERATION_DELETE, "last_updated_tx_stamp", schedulerJob.getLastRunTimestamp().toString(), schedulerJob.getRepository().getRepositoryCode(), "public", Integer.parseInt(operationId), taList);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return responseStatus;
}
	
	/**
	 * 
	 * @param job
	 * @param repository
	 * @param timeInterval
	 * @param fromDate
	 * @param runType
	 * @param trackingId
	 * @param schedulerDivisionToTempJob
	 * @param operationType
	 * @param tempSchema
	 * @param divisionSchema
	 * @param operationId 
	 */
	private ResponseStatus startInsertUpdateDeleteOperations(SchedulerJob job, Repository repository, TimeInterval timeInterval,
			String fromDate, String runType, Integer trackingId, String schedulerDivisionToTempJob,
			String operationType, String tempSchema, String divisionSchema, Integer operationId) {
		ResponseStatus responseStatus = new ResponseStatus();
		ResponseStatus deleteDataResponse = new ResponseStatus();
		try {
				logger.info("\t\t\t Started Operation for " + operationType);			
				
				switch (operationType) {
				case Constants.SCHEDULED_JOBS_OPERATION_INSERT:
					deleteDataResponse = commonUtility.deleteExistingDivisionData(tempSchema, "_cre");
					if(deleteDataResponse.getCode() == Constants.SUCCESS_CODE)
						responseStatus = runDivisionToTempOperations(job, operationType, "created_tx_stamp", fromDate, tempSchema, divisionSchema, operationId, null);
					else
						responseStatus = deleteDataResponse;
					break;
				case Constants.SCHEDULED_JOBS_OPERATION_UPDATE:
					deleteDataResponse = commonUtility.deleteExistingDivisionData(tempSchema, "_upd");
					if(deleteDataResponse.getCode() == Constants.SUCCESS_CODE)
						responseStatus = runDivisionToTempOperations(job, operationType, "last_updated_tx_stamp", fromDate, tempSchema, divisionSchema, operationId, null);
					else
						responseStatus = deleteDataResponse;
					break;
				case Constants.SCHEDULED_JOBS_OPERATION_DELETE:
					deleteDataResponse = commonUtility.deleteExistingDivisionData(tempSchema, "_del");
					if(deleteDataResponse.getCode() == Constants.SUCCESS_CODE)
						responseStatus = runDivisionToTempDeleteOperations(job, operationType, "last_updated_tx_stamp", fromDate, tempSchema, divisionSchema, operationId, null);
					else
						responseStatus = deleteDataResponse;
					break;
				default:
					break;
				}

		} catch (Exception e) {
			logger.error("ERROR >>> " + e);
			responseStatus.setCode(Constants.SUCCESS_CODE);
			responseStatus.setMessage("ERROR >>> "+e);
		}
		return responseStatus;
	}

	private ResponseStatus runDivisionToTempDeleteOperations(SchedulerJob job, String operationType, String timestamp,
			String fromDate, String tempSchema, String divisionSchema,
			Integer operationId, List<String> failedTablesList) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {

			
			Repository repository = job.getRepository();
			job.getTimeInterval();				
			String port = repository.getRepositoryPort();
			String host = repository.getRepositoryIp();
			String user = repository.getRepositoryUser();
			String password = repository.getRepositoryPassword();
			String dbname = repository.getRepositoryDbName();
				
			int successTablesCount = 0;
			int failedTablesCount = 0;		
			JobsHistory jobsHistory = null;
			if(failedTablesList == null) {
			logger.info("\t\t\t\t Updating Job Operation Type "+ operationType +" Status as PROCESSING.");			
			jobsHistory = jobsHistoryService.updateJobsHistoryStartStatus(operationId, operationType, Helper.currentTime(), Helper.currentTimeStamp(), Constants.JOB_PROCESSING_STATUS);
			logger.info("\t\t\t\t Updated Job Operation Type "+ operationType +" Status as PROCESSING.");
			}else {
				jobsHistory = jobsHistoryService.findByOperationIdAndOperationType(operationId, operationType);
			}
				//FIND INSERT QUERY FOR EACH TABLE
				InsertQueriesResponse insertQueriesResponse = commonUtility.getTableNamesWithDeleteQuery(host, port, dbname, user, password, fromDate, tempSchema, divisionSchema, divisionSchema, timestamp, operationType);
				
				if(insertQueriesResponse.getCode() == Constants.SUCCESS_CODE) {
					boolean divisionFlag = true;				
					for (Map.Entry<String, String> queries : insertQueriesResponse.getInsertQueries().entrySet()) {
						String tableName = "";
						String query = "";
						ExecuteInsertReaponse response = null;
						try {
							tableName = queries.getKey();		
							if(failedTablesList != null && !failedTablesList.contains(tableName))
								continue;
								
							query = queries.getValue();
							logger.info("\t\t\t\t **********************************************************************************");
							logger.info("\t\t\t\t DB Link Query ="+query);
							logger.info("\t\t\t\t Table Name= "+tableName);	
							
							response = commonUtility.executeInsertQuery(query);						
							
							logger.info("\t\t\t\t Records: " + response.getRecords());
							
							logger.info("\t\t\t\t Status: " + response.getStatus());
							if(response.getStatusCode() == Constants.SUCCESS_CODE)
								successTablesCount += 1;
							else {
								divisionFlag = false;
								failedTablesCount += 1;
							}
							divisionHistoryService.saveDivisionHistoryDetails(tableName, repository.getRepositoryCode(), jobsHistory.getId(), response.getRecords(), response.getStatus(), operationType, failedTablesList);
							logger.info("\t\t\t\t Saved data divisions history successfully for table "+tableName);
							logger.info("\t\t\t\t **********************************************************************************\n");
						}
						catch (Exception e) {
							logger.info("\t\t\t\t ERROR >>> : " + Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());						
						}	
					}
					if(divisionFlag) {
						logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
						jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_SUCCESS_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, operationType, failedTablesList);
						logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");	
					}
					else {
						logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
						jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_FAILED_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, operationType, failedTablesList);
						logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");
					}
					logger.info("\t\t\t Saved data jobs history successfully for Division "+repository.getRepositoryName());
				}else {
					logger.info("\t\t\t *************************************** Finished Operation "+operationType +" at "+ Helper.currentTimeStamp() +" with ERROR >>> "+ insertQueriesResponse.getMessage() +"*******************************************");
					logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
					jobsHistoryService.updateJobStatus(jobsHistory, insertQueriesResponse.getMessage(),Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, operationType, failedTablesList);
					logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");	
					logger.info("\t\t\t Saved data jobs history successfully for Division "+repository.getRepositoryName());
				}						
			logger.info("\t\t\t Successfully Finished Operation "+operationType);
			logger.info("**************************************************************************************************************\n\n");
		
		}catch (Exception e) {
			logger.error("ERROR >>> " + e);
		}
		return responseStatus;
	}


	/**
	 * 
	 * @param job
	 * @param operationType
	 * @param timestamp
	 * @param fromDate
	 * @param tempSchema
	 * @param divisionSchema
	 * @param operationId
	 * @param diHistories 
	 * @return
	 */
	private ResponseStatus runDivisionToTempOperations(SchedulerJob job,  String operationType,
			String timestamp, String fromDate, String tempSchema,
			String divisionSchema, Integer operationId,
			List<String> faileTablesList) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {

			
			Repository repository = job.getRepository();
			job.getTimeInterval();				
			String port = repository.getRepositoryPort();
			String host = repository.getRepositoryIp();
			String user = repository.getRepositoryUser();
			String password = repository.getRepositoryPassword();
			String dbname = repository.getRepositoryDbName();
				
			int successTablesCount = 0;
			int failedTablesCount = 0;	
			JobsHistory jobsHistory = null;
			if(faileTablesList == null) {
				logger.info("\t\t\t\t Updating Job Operation Type "+ operationType +" Status as PROCESSING.");			
				jobsHistory = jobsHistoryService.updateJobsHistoryStartStatus(operationId, operationType, Helper.currentTime(), Helper.currentTimeStamp(), Constants.JOB_PROCESSING_STATUS);
				logger.info("\t\t\t\t Updated Job Operation Type "+ operationType +" Status as PROCESSING.");	
			}
			else {
				jobsHistory = jobsHistoryService.findByOperationIdAndOperationType(operationId, operationType);
			}
			//FIND INSERT QUERY FOR EACH TABLE
				InsertQueriesResponse insertQueriesResponse = commonUtility.getTableNamesWithInsertionQuery(host, port, dbname, user, password, fromDate, tempSchema, divisionSchema, divisionSchema, timestamp, operationType);
				
				if(insertQueriesResponse.getCode() == Constants.SUCCESS_CODE) {
					boolean divisionFlag = true;				
					for (Map.Entry<String, String> queries : insertQueriesResponse.getInsertQueries().entrySet()) {
						String tableName = "";
						String query = "";
						ExecuteInsertReaponse response = null;
						try {
							
							tableName = queries.getKey();		
							if(faileTablesList != null && !faileTablesList.contains(tableName))
								continue;
							
							query = queries.getValue();
							logger.info("\t\t\t\t **********************************************************************************");
							logger.info("\t\t\t\t DB Link Query ="+query);
							logger.info("\t\t\t\t Table Name= "+tableName);	
							
							response = commonUtility.executeInsertQuery(query);						
							
							logger.info("\t\t\t\t Records: " + response.getRecords());
							
							logger.info("\t\t\t\t Status: " + response.getStatus());
							if(response.getStatusCode() == Constants.SUCCESS_CODE)
								successTablesCount += 1;
							else {
								divisionFlag = false;
								failedTablesCount += 1;
							}
							divisionHistoryService.saveDivisionHistoryDetails(tableName, repository.getRepositoryCode(), jobsHistory.getId(), response.getRecords(), response.getStatus(), operationType, faileTablesList);
							logger.info("\t\t\t\t Saved data divisions history successfully for table "+tableName);
							logger.info("\t\t\t\t **********************************************************************************\n");
						}
						catch (Exception e) {
							logger.info("\t\t\t\t ERROR >>> : " + Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());						
						}	
					}
					if(divisionFlag) {						
						logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
						jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_SUCCESS_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, operationType, faileTablesList);
						logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");						
					}
					else {
						
						logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
						jobsHistoryService.updateJobStatus(jobsHistory, Constants.JOB_FAILED_MESSAGE,Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, operationType, faileTablesList);
						logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");
					}
					logger.info("\t\t\t Saved data jobs history successfully for Division "+repository.getRepositoryName());
				}else {
					logger.info("\t\t\t *************************************** Finished Operation "+operationType +" at "+ Helper.currentTimeStamp() +" with ERROR >>> "+ insertQueriesResponse.getMessage() +"*******************************************");
					logger.info("\t\t Updating Job Operation Type "+ operationType +" Status as COMPLETED.");			
					jobsHistoryService.updateJobStatus(jobsHistory, insertQueriesResponse.getMessage(),Helper.currentTime(), successTablesCount, failedTablesCount, Constants.PROCESS_COMPLETED, operationType, faileTablesList);
					logger.info("\t\t Updated Job Operation Type "+ operationType +" Status as COMPLETED.");	
					logger.info("\t\t\t Saved data jobs history successfully for Division "+repository.getRepositoryName());
				}						
			logger.info("\t\t\t Successfully Finished Operation "+operationType);
			logger.info("**************************************************************************************************************\n\n");
		
		}catch (Exception e) {
			logger.error("ERROR >>> " + e);
		}
		return responseStatus;
	}


	
}
