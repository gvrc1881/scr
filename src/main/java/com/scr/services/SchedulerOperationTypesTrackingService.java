/**
 * 
 */
package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.SchedulerOperationTypesTracking;
import com.scr.repository.SchedulerOperationTypesTrackingRepository;
import com.scr.util.Helper;

/**
 * @author venkat
 *
 */
@Service
public class SchedulerOperationTypesTrackingService {

	@Autowired
	private SchedulerOperationTypesTrackingRepository schedulerOperationTypesTrackingRepository;
	
	public SchedulerOperationTypesTracking saveOperationTypeDetatils(Integer trackingId, String jobType, String startTime,
			String divisionSchema, String tempSchema, String zonalSchema,String jobProcessingStatus) {
		SchedulerOperationTypesTracking schedulerOperationTypesTracking = new SchedulerOperationTypesTracking();
		schedulerOperationTypesTracking.setTrackingId(trackingId);
		schedulerOperationTypesTracking.setJobType(jobType);
		schedulerOperationTypesTracking.setStartTime(startTime);
		schedulerOperationTypesTracking.setDivisionSchema(divisionSchema);
		schedulerOperationTypesTracking.setTempSchema(tempSchema);
		schedulerOperationTypesTracking.setZonalSchema(zonalSchema);
		schedulerOperationTypesTracking.setProcessStatus(jobProcessingStatus);
		schedulerOperationTypesTracking.setProcessedDate(Helper.currentTimeStamp());
		schedulerOperationTypesTrackingRepository.save(schedulerOperationTypesTracking);
		return schedulerOperationTypesTracking;
	}

	public void updateOperationTypeStatus(SchedulerOperationTypesTracking schedulerOperationTypesTrackingToUpdate,
			String jobCompletedStatus, String endTime, String status) {
		schedulerOperationTypesTrackingToUpdate.setEndTime(endTime);
		schedulerOperationTypesTrackingToUpdate.setProcessStatus(jobCompletedStatus);
		schedulerOperationTypesTrackingToUpdate.setJobStatus(status);
		schedulerOperationTypesTrackingRepository.save(schedulerOperationTypesTrackingToUpdate);
	}

	public List<SchedulerOperationTypesTracking> findByTrackingIdOrderByProcessedDateAsc(Integer trackingId) {
		return schedulerOperationTypesTrackingRepository.findByTrackingIdOrderByProcessedDateAsc(trackingId);
	}

	

}
