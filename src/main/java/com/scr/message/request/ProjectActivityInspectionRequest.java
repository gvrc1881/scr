package com.scr.message.request;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import com.scr.model.WorkPhaseActivity;

public class ProjectActivityInspectionRequest implements Serializable {

	private String doneBy;

	private String remark;

	private String status;

	private WorkPhaseActivity activityId;

	private HashMap<String, String> measureMap;

	private HashMap<String, String> activityMap;
	
	private HashMap<String, String> multiMeasureMap;
	
	private HashMap<String, String> multiMeasureActivityMap;
	
	private Date date;

	public String getDoneBy() {
		return doneBy;
	}

	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public WorkPhaseActivity getActivityId() {
		return activityId;
	}

	public void setActivityId(WorkPhaseActivity activityId) {
		this.activityId = activityId;
	}

	public HashMap<String, String> getMeasureMap() {
		return measureMap;
	}

	public void setMeasureMap(HashMap<String, String> measureMap) {
		this.measureMap = measureMap;
	}

	public HashMap<String, String> getActivityMap() {
		return activityMap;
	}

	public void setActivityMap(HashMap<String, String> activityMap) {
		this.activityMap = activityMap;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public HashMap<String, String> getMultiMeasureMap() {
		return multiMeasureMap;
	}

	public void setMultiMeasureMap(HashMap<String, String> multiMeasureMap) {
		this.multiMeasureMap = multiMeasureMap;
	}

	public HashMap<String, String> getMultiMeasureActivityMap() {
		return multiMeasureActivityMap;
	}

	public void setMultiMeasureActivityMap(HashMap<String, String> multiMeasureActivityMap) {
		this.multiMeasureActivityMap = multiMeasureActivityMap;
	}

}
