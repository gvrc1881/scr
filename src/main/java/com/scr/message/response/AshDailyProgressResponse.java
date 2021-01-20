package com.scr.message.response;

import java.util.Date;

public class AshDailyProgressResponse {

	private String columnName;
	private int dailyProgress;
	private Date date;
	private Long depotId;
	private String cumProgress;
	private String monthlyProgress;
	private String monthlyTarget;
	private String cumTarget;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getDailyProgress() {
		return dailyProgress;
	}

	public void setDailyProgress(int dailyProgress) {
		this.dailyProgress = dailyProgress;
	}

	public String getCumProgress() {
		return cumProgress;
	}

	public void setCumProgress(String cumProgress) {
		this.cumProgress = cumProgress;
	}

	public String getMonthlyProgress() {
		return monthlyProgress;
	}

	public void setMonthlyProgress(String monthlyProgress) {
		this.monthlyProgress = monthlyProgress;
	}

	public String getMonthlyTarget() {
		return monthlyTarget;
	}

	public void setMonthlyTarget(String monthlyTarget) {
		this.monthlyTarget = monthlyTarget;
	}

	public String getCumTarget() {
		return cumTarget;
	}

	public void setCumTarget(String cumTarget) {
		this.cumTarget = cumTarget;
	}

}
