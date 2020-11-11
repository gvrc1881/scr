package com.scr.message.request;

import java.io.Serializable;
import java.util.List;

import com.scr.model.StandardPhaseActivity;
import com.scr.model.StandardPhases;
import com.scr.model.Works;

public class CopyWPAndWPA implements Serializable {

	private List<StandardPhases> standardPhases;

	private List<StandardPhaseActivity> standardPhaseActivities;
	
	private Works work;

	public List<StandardPhases> getStandardPhases() {
		return standardPhases;
	}

	public void setStandardPhases(List<StandardPhases> standardPhases) {
		this.standardPhases = standardPhases;
	}

	public List<StandardPhaseActivity> getStandardPhaseActivities() {
		return standardPhaseActivities;
	}

	public void setStandardPhaseActivities(List<StandardPhaseActivity> standardPhaseActivities) {
		this.standardPhaseActivities = standardPhaseActivities;
	}

	public Works getWork() {
		return work;
	}

	public void setWork(Works work) {
		this.work = work;
	}

}
