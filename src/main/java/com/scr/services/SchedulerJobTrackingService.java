/**
 * 
 */
package com.scr.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.JobType;
import com.scr.model.Repository;
import com.scr.model.SchedulerJob;
import com.scr.model.SchedulerJobsTracking;
import com.scr.model.TimeInterval;
import com.scr.repository.SchedulerJobTrackingRepository;

/**
 * @author venkat
 *
 */
@Service
public class SchedulerJobTrackingService {

	@Autowired
	private SchedulerJobTrackingRepository schedulerJobTrackingRepository;

	@Autowired
	private RepositoryService repositoryService;
	
	/*@Autowired
	private DashBoardResource dashBoardResource;*/
	
	public SchedulerJobsTracking updateJobsHistoryStartStatus(JobType jobTypeId, SchedulerJob jobId, Repository repository,
			Timestamp currentTimeStamp, String currentTime, String jobProcessingStatus, String runType,
			TimeInterval timeInterval, String runBy) {
		SchedulerJobsTracking schedulerJobsTracking = new SchedulerJobsTracking();
		schedulerJobsTracking.setJobId(jobId);
		schedulerJobsTracking.setJobType(jobId);
		schedulerJobsTracking.setRepository(repository);
		schedulerJobsTracking.setTimeInterval(timeInterval);
		schedulerJobsTracking.setStartTime(currentTime);
		schedulerJobsTracking.setProcessedDate(currentTimeStamp);
		schedulerJobsTracking.setRunType(runType);
		schedulerJobsTracking.setRunBy(runBy);
		schedulerJobsTracking.setProcessStatus(jobProcessingStatus);
		schedulerJobTrackingRepository.save(schedulerJobsTracking);
		return schedulerJobsTracking;
	}

	public SchedulerJobsTracking updateJobsHistoryCompletedStatus(SchedulerJobsTracking schedulerJobsTrackingToUpdate,
			String currentTime, String jobCompletedStatus, String jobStatus) {
		schedulerJobsTrackingToUpdate.setEndTime(currentTime);
		schedulerJobsTrackingToUpdate.setProcessStatus(jobCompletedStatus);
		schedulerJobsTrackingToUpdate.setJobStatus(jobStatus);
		schedulerJobTrackingRepository.save(schedulerJobsTrackingToUpdate);
		return schedulerJobsTrackingToUpdate;
	}

	public List<SchedulerJobsTracking> findSchedulerJobTrackingOrderByProcessedDateAsc() {		
		return schedulerJobTrackingRepository.findAllByOrderByProcessedDateAsc();
	}

	public List<SchedulerJobsTracking> findSchedulerJobTrackingByDivisionCodeOrderByProcessedDateAsc(
			String divisionCode) {	
		Repository repository = repositoryService.findByRepositoryCode(divisionCode);
		return schedulerJobTrackingRepository.findByRepositoryOrderByProcessedDateAsc(repository);
	}

	public List<SchedulerJobsTracking> findSchedulerJobTrackingOrderByProcessedDateDesc() {		
		return schedulerJobTrackingRepository.findAllByOrderByProcessedDateDesc();
	}

	public List<SchedulerJobsTracking> findByJobIdAndProcessedDate(SchedulerJob jobId, Timestamp processedDate) {		
		return schedulerJobTrackingRepository.findByJobIdAndProcessedDate(jobId, processedDate);
	}
	
	/*public DashboardResponse getAssetTypes() {
		// TODO Auto-generated method stub
		return dashBoardResource.getAssetTypes();
	}*/

}
