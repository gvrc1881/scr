package com.scr.message.response;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.scr.model.WorkGroup;
import com.scr.model.WorkPhaseActivity;

@Component
public class WPADailyProgressResponse {

	private Integer id;
	private Double performedCount;
	private WorkGroup workGroupId;
	private WorkPhaseActivity workPhaseActivityId;
	private Double alreadyDoneCount;
	private String population;
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPerformedCount() {
		return performedCount;
	}

	public void setPerformedCount(Double performedCount) {
		this.performedCount = performedCount;
	}

	public WorkGroup getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(WorkGroup workGroupId) {
		this.workGroupId = workGroupId;
	}

	public WorkPhaseActivity getWorkPhaseActivityId() {
		return workPhaseActivityId;
	}

	public void setWorkPhaseActivityId(WorkPhaseActivity workPhaseActivityId) {
		this.workPhaseActivityId = workPhaseActivityId;
	}

	public Double getAlreadyDoneCount() {
		return alreadyDoneCount;
	}

	public void setAlreadyDoneCount(Double alreadyDoneCount) {
		this.alreadyDoneCount = alreadyDoneCount;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "WPADailyProgressResponse [id=" + id + ", performedCount=" + performedCount + ", workGroupId="
				+ workGroupId + ", workPhaseActivityId=" + workPhaseActivityId + ", alreadyDoneCount="
				+ alreadyDoneCount + ", population=" + population + ", date=" + date + "]";
	}


}
