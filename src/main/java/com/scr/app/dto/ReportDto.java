package com.scr.app.dto;

public class ReportDto {
	
	private String reportId;
	private String facilityId;
	private String subDivision;
	private String fromDate;
	private String thruDate;
	private byte[] reportResult;

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getThruDate() {
		return thruDate;
	}

	public void setThruDate(String thruDate) {
		this.thruDate = thruDate;
	}

	public byte[] getReportResult() {
		return reportResult;
	}

	public void setReportResult(byte[] reportResult) {
		this.reportResult = reportResult;
	}

}
