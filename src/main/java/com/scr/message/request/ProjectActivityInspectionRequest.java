package com.scr.message.request;

import java.io.Serializable;
import java.util.HashMap;

public class ProjectActivityInspectionRequest implements Serializable {
	
	private HashMap<String,String> measureMap;
	
	private HashMap<String,String> activityMap;

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
	
	

}
