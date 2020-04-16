package com.scr.message.request;

import java.sql.Timestamp;

public class DriveRequest {

	private Long id;

	private String name;

	private String description;

	private Timestamp fromDate;

	private Timestamp toDate;

	private String assetType;

	private String assetDescription;

	private String criteria;

	private double target_qty;

	private String functionalUnit;

	private String isIdRequired;

	private String checklist;

	private String active;

	private String createdBy;

	private String updatedBy;

	private Timestamp createdOn;

	private Timestamp updatedOn;

	private String depotType;

	private String section;
	private String guage;
	private Timestamp targetDate;
	private String status;
	private String division;
	private String executionAgency;
	private double TKM;
	private double RKM;
	private String crsInspection;
	private String crsAuthorisation;
	private String targetSetBy;
	private String doublingTrippling;
	private String state;
	private String phase;
	private String proposalScheme;
	private String sanctionByBoard;
	private String yearOfSanction;
	private Timestamp dateOfCompletion;

	private String inspectionType;

	private String sectionStartLocation;
	private String sectionEndLocation;
	private Timestamp dateOfInspection;
	private String remarks;
	private Timestamp authorisationDate;
	private Timestamp chargingDate;
	private String attachment;
	private String station;
	private String stipulationsId;

	private String stipulation;
	private String stipulationTo;
	private Timestamp dateOfStipulation;
	private Timestamp dateComplied;
	private String compliance;
	private String compliedBy;
	
	private String activityPositionId;
	private Integer displayOrder;
	private double lowerLimit;
	private double upperLimit;
	private String reportColumnHeader;
	private String activityId;
	private String driveId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public double getTarget_qty() {
		return target_qty;
	}

	public void setTarget_qty(double target_qty) {
		this.target_qty = target_qty;
	}

	public String getFunctionalUnit() {
		return functionalUnit;
	}

	public void setFunctionalUnit(String functionalUnit) {
		this.functionalUnit = functionalUnit;
	}

	public String getIsIdRequired() {
		return isIdRequired;
	}

	public void setIsIdRequired(String isIdRequired) {
		this.isIdRequired = isIdRequired;
	}

	public String getChecklist() {
		return checklist;
	}

	public void setChecklist(String checklist) {
		this.checklist = checklist;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	

	public String getDepotType() {
		return depotType;
	}

	public void setDepotType(String depotType) {
		this.depotType = depotType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getGuage() {
		return guage;
	}

	public void setGuage(String guage) {
		this.guage = guage;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getExecutionAgency() {
		return executionAgency;
	}

	public void setExecutionAgency(String executionAgency) {
		this.executionAgency = executionAgency;
	}

	public double getTKM() {
		return TKM;
	}

	public void setTKM(double tKM) {
		TKM = tKM;
	}

	public double getRKM() {
		return RKM;
	}

	public void setRKM(double rKM) {
		RKM = rKM;
	}

	public String getCrsInspection() {
		return crsInspection;
	}

	public void setCrsInspection(String crsInspection) {
		this.crsInspection = crsInspection;
	}

	public String getCrsAuthorisation() {
		return crsAuthorisation;
	}

	public void setCrsAuthorisation(String crsAuthorisation) {
		this.crsAuthorisation = crsAuthorisation;
	}

	public String getTargetSetBy() {
		return targetSetBy;
	}

	public void setTargetSetBy(String targetSetBy) {
		this.targetSetBy = targetSetBy;
	}

	public String getDoublingTrippling() {
		return doublingTrippling;
	}

	public void setDoublingTrippling(String doublingTrippling) {
		this.doublingTrippling = doublingTrippling;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getProposalScheme() {
		return proposalScheme;
	}

	public void setProposalScheme(String proposalScheme) {
		this.proposalScheme = proposalScheme;
	}

	public String getSanctionByBoard() {
		return sanctionByBoard;
	}

	public void setSanctionByBoard(String sanctionByBoard) {
		this.sanctionByBoard = sanctionByBoard;
	}

	public String getYearOfSanction() {
		return yearOfSanction;
	}

	public void setYearOfSanction(String yearOfSanction) {
		this.yearOfSanction = yearOfSanction;
	}

	

	public String getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}

	public String getSectionStartLocation() {
		return sectionStartLocation;
	}

	public void setSectionStartLocation(String sectionStartLocation) {
		this.sectionStartLocation = sectionStartLocation;
	}

	public String getSectionEndLocation() {
		return sectionEndLocation;
	}

	public void setSectionEndLocation(String sectionEndLocation) {
		this.sectionEndLocation = sectionEndLocation;
	}

	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStipulationsId() {
		return stipulationsId;
	}

	public void setStipulationsId(String stipulationsId) {
		this.stipulationsId = stipulationsId;
	}

	public String getStipulation() {
		return stipulation;
	}

	public void setStipulation(String stipulation) {
		this.stipulation = stipulation;
	}

	public String getStipulationTo() {
		return stipulationTo;
	}

	public void setStipulationTo(String stipulationTo) {
		this.stipulationTo = stipulationTo;
	}

	

	public String getCompliance() {
		return compliance;
	}

	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}

	public String getCompliedBy() {
		return compliedBy;
	}

	public void setCompliedBy(String compliedBy) {
		this.compliedBy = compliedBy;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Timestamp getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Timestamp targetDate) {
		this.targetDate = targetDate;
	}

	public Timestamp getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setDateOfCompletion(Timestamp dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public Timestamp getDateOfInspection() {
		return dateOfInspection;
	}

	public void setDateOfInspection(Timestamp dateOfInspection) {
		this.dateOfInspection = dateOfInspection;
	}

	public Timestamp getAuthorisationDate() {
		return authorisationDate;
	}

	public void setAuthorisationDate(Timestamp authorisationDate) {
		this.authorisationDate = authorisationDate;
	}

	public Timestamp getChargingDate() {
		return chargingDate;
	}

	public void setChargingDate(Timestamp chargingDate) {
		this.chargingDate = chargingDate;
	}

	public Timestamp getDateOfStipulation() {
		return dateOfStipulation;
	}

	public void setDateOfStipulation(Timestamp dateOfStipulation) {
		this.dateOfStipulation = dateOfStipulation;
	}

	public Timestamp getDateComplied() {
		return dateComplied;
	}

	public void setDateComplied(Timestamp dateComplied) {
		this.dateComplied = dateComplied;
	}
	
	
	
	public String getActivityPositionId() {
		return activityPositionId;
	}

	public void setActivityPositionId(String activityPositionId) {
		this.activityPositionId = activityPositionId;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public String getReportColumnHeader() {
		return reportColumnHeader;
	}

	public void setReportColumnHeader(String reportColumnHeader) {
		this.reportColumnHeader = reportColumnHeader;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getDriveId() {
		return driveId;
	}

	public void setDriveId(String driveId) {
		this.driveId = driveId;
	}

	@Override
	public String toString() {
		return "DriveRequest [id=" + id + ", name=" + name + ", description=" + description + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", assetType=" + assetType + ", assetDescription=" + assetDescription
				+ ", criteria=" + criteria + ", target_qty=" + target_qty + ", functionalUnit=" + functionalUnit
				+ ", isIdRequired=" + isIdRequired + ", checklist=" + checklist + ", active=" + active + ", createdBy="
				+ createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", depotType=" + depotType + ", section=" + section + ", guage=" + guage + ", targetDate="
				+ targetDate + ", status=" + status + ", division=" + division + ", executionAgency=" + executionAgency
				+ ", TKM=" + TKM + ", RKM=" + RKM + ", crsInspection=" + crsInspection + ", crsAuthorisation="
				+ crsAuthorisation + ", targetSetBy=" + targetSetBy + ", doublingTrippling=" + doublingTrippling
				+ ", state=" + state + ", phase=" + phase + ", proposalScheme=" + proposalScheme + ", sanctionByBoard="
				+ sanctionByBoard + ", yearOfSanction=" + yearOfSanction + ", dateOfCompletion=" + dateOfCompletion
				+ ", inspectionType=" + inspectionType + ", sectionStartLocation=" + sectionStartLocation
				+ ", sectionEndLocation=" + sectionEndLocation + ", dateOfInspection=" + dateOfInspection + ", remarks="
				+ remarks + ", authorisationDate=" + authorisationDate + ", chargingDate=" + chargingDate
				+ ", attachment=" + attachment + ", station=" + station + ", stipulationsId=" + stipulationsId
				+ ", stipulation=" + stipulation + ", stipulationTo=" + stipulationTo + ", dateOfStipulation="
				+ dateOfStipulation + ", dateComplied=" + dateComplied + ", compliance=" + compliance + ", compliedBy="
				+ compliedBy + ", activityPositionId=" + activityPositionId + ", displayOrder=" + displayOrder
				+ ", lowerLimit=" + lowerLimit + ", upperLimit=" + upperLimit + ", reportColumnHeader="
				+ reportColumnHeader + ", activityId=" + activityId + ", driveId=" + driveId + "]";
	}

	
	
	
	

}
