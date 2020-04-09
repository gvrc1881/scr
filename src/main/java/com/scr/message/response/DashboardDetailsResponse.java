/**
 * 
 */
package com.scr.message.response;

/**
 * @author vt1056
 *
 */
public class DashboardDetailsResponse {
	private String divisionName;
	private String divisionCode;
	private String jobType;
	private String operationType;
	private Integer trackingId;
	private Integer operationId;
	private Integer successTables;
	private Integer failedTables;
	private String Date;
	private String assetType;
	private Integer totalAssetTypes;

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Integer getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public Integer getSuccessTables() {
		return successTables;
	}

	public void setSuccessTables(Integer successTables) {
		this.successTables = successTables;
	}

	public Integer getFailedTables() {
		return failedTables;
	}

	public void setFailedTables(Integer failedTables) {
		this.failedTables = failedTables;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public Integer getTotalAssetTypes() {
		return totalAssetTypes;
	}

	public void setTotalAssetTypes(Integer totalAssetTypes) {
		this.totalAssetTypes = totalAssetTypes;
	}

}
