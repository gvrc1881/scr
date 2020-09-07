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
	private String zone;
	private String div;
	private String subdiv;
	private String materialDesc;
	private Integer qoh;
	private String uom;

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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getSubdiv() {
		return subdiv;
	}

	public void setSubdiv(String subdiv) {
		this.subdiv = subdiv;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public Integer getQoh() {
		return qoh;
	}

	public void setQoh(Integer qoh) {
		this.qoh = qoh;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

}
