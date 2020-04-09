/**
 * 
 */
package com.scr.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.JobsHistory;
import com.scr.repository.JobsHistoryRepository;
import com.scr.util.Constants;

/**
 * @author vt1056
 *
 */
@Service
public class JobsHistoryService {

	@Autowired
	private JobsHistoryRepository jobsHistoryRepository;
	
	public JobsHistory saveJobsHistory(JobsHistory jobsHistory) {		
		return jobsHistoryRepository.save(jobsHistory);
	}

	public void updateJobStatus(JobsHistory jobsHistory, String jobStatus, String currentTime, int successTablesCount, int failedTablesCount, Integer processCompleted, String operation, List<String> faileTablesList) {
		if(faileTablesList == null) {
		jobsHistory.setEndTime(currentTime);
		jobsHistory.setSuccessTablesCount(successTablesCount);
		jobsHistory.setFailedTablesCount(failedTablesCount);
		jobsHistory.setTotalTablesCount(jobsHistory.getSuccessTablesCount() + jobsHistory.getFailedTablesCount());
		jobsHistory.setOperationType(operation);
		jobsHistory.setJobStatus(jobStatus);
		jobsHistory.setStatus(Constants.JOB_COMPLETED_STATUS);		
		saveJobsHistory(jobsHistory);
		}else {
			Integer failed = jobsHistory.getFailedTablesCount();
			Integer success = jobsHistory.getSuccessTablesCount();
			
			failed = faileTablesList.size() - successTablesCount;
			success = success + successTablesCount;
			jobsHistory.setFailedTablesCount(failed);
			jobsHistory.setSuccessTablesCount(success);
			
			if(faileTablesList.size() == successTablesCount) {
				jobsHistory.setStatus(Constants.JOB_SUCCESS_MESSAGE);
			}else {
				jobsHistory.setStatus(Constants.JOB_FAILED_MESSAGE);
			}
			saveJobsHistory(jobsHistory);
		}
	}


	public JobsHistory updateJobsHistoryStartStatus(Integer operationId, String operationType, String currentTime,
			Timestamp currentTimeStamp, String jobProcessingStatus) {
		JobsHistory jobsHistory = new JobsHistory();	
		jobsHistory.setProcessedDate(currentTimeStamp);
		jobsHistory.setStartTime(currentTime);	
		jobsHistory.setStatus(jobProcessingStatus);
		jobsHistory.setOperationId(operationId);
		jobsHistory.setOperationType(operationType);
		jobsHistory = saveJobsHistory(jobsHistory);
		return jobsHistory;
	}

	public List<JobsHistory> findByOperationId(Integer operationId) {		
		return jobsHistoryRepository.findByOperationIdAndStatusNotIn(operationId, Constants.JOB_SUCCESS_MESSAGE);
	}

	public JobsHistory findByOperationIdAndOperationType(Integer operationId, String operationType) {		
		return jobsHistoryRepository.findByOperationIdAndOperationType(operationId, operationType);
	}

}
