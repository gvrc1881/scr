package com.scr.message.response;

import java.util.Date;

import com.scr.model.PrecautionaryMeasuresMaster;

public class PrecautionaryMeasureResponse {
	private Integer count;
	private String dataDiv;
	private String doneBy;
	private String facilityId;
	private Date dateOfWork;
	private String location;
	private String remarks;
	private String approvedStatus;
	private String approveBy;
	private Long precautionaryMeasure;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getDataDiv() {
		return dataDiv;
	}
	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}
	public String getDoneBy() {
		return doneBy;
	}
	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public Date getDateOfWork() {
		return dateOfWork;
	}
	public void setDateOfWork(Date dateOfWork) {
		this.dateOfWork = dateOfWork;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getApprovedStatus() {
		return approvedStatus;
	}
	public void setApprovedStatus(String approvedStatus) {
		this.approvedStatus = approvedStatus;
	}
	public String getApproveBy() {
		return approveBy;
	}
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}
	public Long getPrecautionaryMeasure() {
		return precautionaryMeasure;
	}
	public void setPrecautionaryMeasure(Long precautionaryMeasure) {
		this.precautionaryMeasure = precautionaryMeasure;
	}
	@Override
	public String toString() {
		return "PrecautionaryMeasureResponse [count=" + count + ", dataDiv=" + dataDiv + ", doneBy=" + doneBy
				+ ", facilityId=" + facilityId + ", dateOfWork=" + dateOfWork + ", location=" + location + ", remarks="
				+ remarks + ", approvedStatus=" + approvedStatus + ", approveBy=" + approveBy
				+ ", precautionaryMeasure=" + precautionaryMeasure + "]";
	}


}
