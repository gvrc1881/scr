/**
 * 
 */
package com.scr.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.response.DashboardDetailsResponse;
import com.scr.message.response.DashboardResponse;
import com.scr.model.JobsHistory;
import com.scr.model.Repository;
import com.scr.model.SchedulerJobsTracking;
import com.scr.model.SchedulerOperationTypesTracking;
import com.scr.services.RepositoryService;
import com.scr.services.SchedulerOperationTypesTrackingService;
import com.scr.services.SchedulerTrackingService;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@Component
public class SchedulerTrackingMapper {

	@Autowired
	private SchedulerOperationTypesTrackingService schedulerOperationTypesTrackingService;
	
	@Autowired
	private SchedulerTrackingService schedulerTrackingService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	public DashboardResponse prepareDashboardResponse(String divisionCode,
			List<SchedulerJobsTracking> schedulerJobsTrackingsList) {
		DashboardResponse response = new DashboardResponse();
		try {
			
			//schedulerJobsTrackingsList = findLastSevenRecords(schedulerJobsTrackingsList);
			
			List<Repository> repositories = repositoryService.findAllByIsActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);
			Integer lastTrackingId = 0;//schedulerJobsTrackingsList.get(schedulerJobsTrackingsList.size()-1).getTrackingId();
			String divisionName = "";//schedulerJobsTrackingsList.get(schedulerJobsTrackingsList.size()-1).getRepository().getRepositoryName();			
			List<SchedulerOperationTypesTracking> lastSchedulerOperationTypesTrackingsList =  null;//schedulerOperationTypesTrackingService.findByTrackingIdOrderByProcessedDateAsc(lastTrackingId);
			LinkedHashMap<String, int[]> lastOperationTypesList = new LinkedHashMap<>();
			List<DashboardDetailsResponse> lastProcessedDetails = new ArrayList<>();
			
			if(divisionCode.equalsIgnoreCase(Constants.ALL)) {				
				for(Repository repository : repositories) {
					for(SchedulerJobsTracking tracking : schedulerJobsTrackingsList) {
						if(repository.getRepositoryCode() == tracking.getRepository().getRepositoryCode()) {
							lastTrackingId = tracking.getTrackingId();							
							lastSchedulerOperationTypesTrackingsList = schedulerOperationTypesTrackingService.findByTrackingIdOrderByProcessedDateAsc(tracking.getTrackingId());
							divisionName = tracking.getRepository().getRepositoryName();
							for (SchedulerOperationTypesTracking schedulerOperationTypesTracking : lastSchedulerOperationTypesTrackingsList) {
								List<JobsHistory> jobsHistories = schedulerTrackingService
										.findByOperationIdOrderByProcessedDateDesc(schedulerOperationTypesTracking.getOperationId());
								String type = "";
								int[] records = new int[2];
								for (JobsHistory jobsHistory : jobsHistories) {
									type = jobsHistory.getOperationType() + "-" + schedulerOperationTypesTracking.getTrackingId()
											+ "-" + jobsHistory.getOperationId();
									records[0] = jobsHistory.getSuccessTablesCount();
									records[1] = jobsHistory.getFailedTablesCount();
									lastProcessedDetails.add(getDetails(divisionName, schedulerOperationTypesTracking.getJobType(),
											jobsHistory.getOperationType(), schedulerOperationTypesTracking.getTrackingId(),
											jobsHistory.getOperationId(), jobsHistory.getSuccessTablesCount(),
											jobsHistory.getFailedTablesCount(), tracking.getProcessedDate().toString(), divisionCode));

									lastOperationTypesList.put(
											divisionName + "-" + schedulerOperationTypesTracking.getJobType() + "-" + type,
											records);
								}
							}	
							
						}
					}
				}
				
			}else {

				lastTrackingId =  0;//schedulerJobsTrackingsList.get(schedulerJobsTrackingsList.size()-1).getTrackingId();
				divisionName = "";//schedulerJobsTrackingsList.get(schedulerJobsTrackingsList.size()-1).getRepository().getRepositoryName();			
				for(Repository repository : repositories) {
					for(SchedulerJobsTracking tracking : schedulerJobsTrackingsList) {
						if(repository.getRepositoryCode() == tracking.getRepository().getRepositoryCode()) {
							lastTrackingId = tracking.getTrackingId();
							divisionName = tracking.getRepository().getRepositoryName();
							divisionCode = tracking.getRepository().getRepositoryCode();
						}
						if(lastTrackingId != 0)
							break;
					}
					if(lastTrackingId != 0)
						break;
				}				
				lastSchedulerOperationTypesTrackingsList = schedulerOperationTypesTrackingService.findByTrackingIdOrderByProcessedDateAsc(lastTrackingId);
				for (SchedulerOperationTypesTracking schedulerOperationTypesTracking : lastSchedulerOperationTypesTrackingsList) {
					List<JobsHistory> jobsHistories = schedulerTrackingService
							.findByOperationIdOrderByProcessedDateDesc(schedulerOperationTypesTracking.getOperationId());
					String type = "";
					int[] records = new int[2];
					for (JobsHistory jobsHistory : jobsHistories) {
						type = jobsHistory.getOperationType() + "-" + schedulerOperationTypesTracking.getTrackingId()
								+ "-" + jobsHistory.getOperationId();
						records[0] = jobsHistory.getSuccessTablesCount();
						records[1] = jobsHistory.getFailedTablesCount();
						lastProcessedDetails.add(getDetails(divisionName, schedulerOperationTypesTracking.getJobType(),
								jobsHistory.getOperationType(), schedulerOperationTypesTracking.getTrackingId(),
								jobsHistory.getOperationId(), jobsHistory.getSuccessTablesCount(),
								jobsHistory.getFailedTablesCount(), jobsHistory.getProcessedDate().toString(), divisionCode));

						lastOperationTypesList.put(
								divisionName + "-" + schedulerOperationTypesTracking.getJobType() + "-" + type,
								records);
					}
				}	
			}
			
			LinkedHashMap<String, Integer> trackingIdsList = new LinkedHashMap<>();				
				for(SchedulerJobsTracking schedulerJobsTracking : schedulerJobsTrackingsList) {
					if(divisionCode.equalsIgnoreCase(divisionCode)) {
						trackingIdsList.put(schedulerJobsTracking.getRepository().getRepositoryName()+"-"+schedulerJobsTracking.getRepository().getRepositoryCode()+"-"+schedulerJobsTracking.getTrackingId()+"-"+Helper.getStringFromTimeStamp(schedulerJobsTracking.getProcessedDate()).replace("-", "*"), schedulerJobsTracking.getTrackingId());
					}else if(divisionCode.equalsIgnoreCase(Constants.ALL)){
						trackingIdsList.put(schedulerJobsTracking.getRepository().getRepositoryName()+"-"+schedulerJobsTracking.getRepository().getRepositoryCode()+"-"+schedulerJobsTracking.getTrackingId()+"-"+Helper.getStringFromTimeStamp(schedulerJobsTracking.getProcessedDate()).replace("-", "*"), schedulerJobsTracking.getTrackingId());
					}
				}			
			System.out.println(trackingIdsList);
			LinkedHashMap<String, Integer> jobIdsList = new LinkedHashMap<String, Integer>();
			for (Entry<String, Integer> entry : trackingIdsList.entrySet()) {
				List<SchedulerOperationTypesTracking> schedulerOperationTypesTrackingsList = schedulerOperationTypesTrackingService.findByTrackingIdOrderByProcessedDateAsc(entry.getValue());
				for(SchedulerOperationTypesTracking schedulerOperationTypesTracking : schedulerOperationTypesTrackingsList) {
					jobIdsList.put(entry.getKey().split("-")[0]+"-"+entry.getKey().split("-")[1]+"-"+schedulerOperationTypesTracking.getJobType()+"-"+entry.getValue()+"-"+entry.getKey().split("-")[entry.getKey().split("-").length-1], schedulerOperationTypesTracking.getOperationId());
				}
			}
					
			System.out.println(trackingIdsList);
			List<DashboardDetailsResponse> lastOneWeekDetails = new ArrayList<DashboardDetailsResponse>();
			LinkedHashMap<String, int[]> operationTypesList = new LinkedHashMap<>();
			for (Entry<String, Integer> entry : jobIdsList.entrySet()) {
				List<JobsHistory> jobsHistories = schedulerTrackingService.findByOperationIdOrderByProcessedDateDesc(entry.getValue());
				String type = "";
				int[] records = new int[2];
				for(JobsHistory jobsHistory : jobsHistories) {
					type = jobsHistory.getOperationType();
					records[0] = jobsHistory.getSuccessTablesCount() != null ? jobsHistory.getSuccessTablesCount() : 0;
					records[1] = jobsHistory.getFailedTablesCount() != null ? jobsHistory.getFailedTablesCount() : 0;
					lastOneWeekDetails.add(getDetails(
							entry.getKey().split("-")[0], 
							entry.getKey().split("-")[2],
							type, 
							Integer.parseInt(entry.getKey().split("-")[3]), 
							entry.getValue(),
							jobsHistory.getSuccessTablesCount(), 
							jobsHistory.getFailedTablesCount(),
							entry.getKey().split("-")[entry.getKey().split("-").length-1],
							entry.getKey().split("-")[1]));
					operationTypesList.put(entry.getKey().split("-")[0]+"-"+entry.getKey().split("-")[1]+"-"+type+"-"+entry.getKey().split("-")[2]+"-"+entry.getValue(), records);
				}
				
			}			
			response.setLastProcessedDetails(lastProcessedDetails);
			response.setLastOneWeekDetails(lastOneWeekDetails);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private DashboardDetailsResponse getDetails(String divisionName, String jobType, String operationType, Integer trackingId,
			Integer operationId, Integer successTablesCount, 
			Integer failedTablesCount, String timestamp, String divisionCode) {
		DashboardDetailsResponse response = new DashboardDetailsResponse();
		response.setDivisionName(divisionName);
		response.setDivisionCode(divisionCode);
		response.setJobType(jobType);
		response.setOperationType(operationType);
		response.setTrackingId(trackingId);
		response.setOperationId(operationId);
		response.setSuccessTables(successTablesCount);
		response.setFailedTables(failedTablesCount);		
		response.setDate((timestamp.replace("*", "-")));
		return response;
	}

}
