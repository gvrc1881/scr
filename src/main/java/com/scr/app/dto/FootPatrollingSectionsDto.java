package com.scr.app.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FootPatrollingSectionsDto {

	public FootPatrollingSectionsDto() {
		// TODO Auto-generated constructor stub
	}
	
	String seqId;
	String facilityDepot;
	String fpSection;
	String fromDate;
	String toDate;
	String fromLocation;
	String toLocation;
	String remarks;
	
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getFacilityDepot() {
		return facilityDepot;
	}
	public void setFacilityDepot(String facilityDepot) {
		this.facilityDepot = facilityDepot;
	}
	public String getFpSection() {
		return fpSection;
	}
	public void setFpSection(String fpSection) {
		this.fpSection = fpSection;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}