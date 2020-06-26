package com.scr.app.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObservationCategoriesDto {

	public ObservationCategoriesDto() {
		// TODO Auto-generated constructor stub
	}
	String seqId;
	String inspectionType;
	String department;
	String observationCategory;
	String description;
	String remark;
	String fromDate;
	String thruDate;
	
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getInspectionType() {
		return inspectionType;
	}
	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getObservationCategory() {
		return observationCategory;
	}
	public void setObservationCategory(String observationCategory) {
		this.observationCategory = observationCategory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
}


