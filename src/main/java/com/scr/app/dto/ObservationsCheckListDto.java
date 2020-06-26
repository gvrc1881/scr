package com.scr.app.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObservationsCheckListDto {

	public ObservationsCheckListDto() {
		// TODO Auto-generated constructor stub
	}
	String seqId;
	String inspectionType;
	String observationCategory;
	String observationItem;
	String description;
	String fromDate;
	String thruDate;
	String priority;
	String displaySequence;
	private String severity;
	
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
	public String getObservationCategory() {
		return observationCategory;
	}
	public void setObservationCategory(String observationCategory) {
		this.observationCategory = observationCategory;
	}
	public String getObservationItem() {
		return observationItem;
	}
	public void setObservationItem(String observationItem) {
		this.observationItem = observationItem;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getDisplaySequence() {
		return displaySequence;
	}
	public void setDisplaySequence(String displaySequence) {
		this.displaySequence = displaySequence;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
}








