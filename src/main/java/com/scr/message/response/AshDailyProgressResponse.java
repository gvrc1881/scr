package com.scr.message.response;

import java.util.Date;

public class AshDailyProgressResponse {

	private String columnName;
	private int dailyProgress;
	private Date date;
	private Long depotId;

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

}
