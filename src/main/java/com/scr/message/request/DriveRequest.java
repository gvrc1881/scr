package com.scr.message.request;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;

import com.scr.model.FunctionalLocationTypes;

public class DriveRequest {

	private Long id;

	private String name;

	private String description;

	private Timestamp fromDate;

	private Timestamp toDate;
	
	private Date frmDate;

	private Date toDat;

	private String assetType;

	private String frequency;
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
	
	private String driveScope;

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
	private String inspectionId;
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
	
	// DRIVE TARGETS
	private String unitType;
	private String unitName;
	private double target;
	private String poulation;
	
	// DRIVE DAILY PROGRESS
	//private Long activityId;
	private Date performedDate;
	//private String division;
	private String depot;
	//private String section;
	private double performedCount;
	private String supervisor;
	
	//FAILURE ANALYSIS
	private String failure_id;
	private String reported;
	private String reportDescription;
	private String repurcussion;
	private Timestamp date;
	private String div;
	//private BigInteger failureSection;
	//private BigInteger assetType;
	private String assetId;
	private String subAssetType;
	private String subAssetId;
	private String make;
	private String model;
	private String rootCause;
	private String actionPlan;
	private String actionStatus;
	private String approvedBy;
	private Timestamp actionTargetDate;
	private Timestamp actionCompletedDate;
	private String actionDescription;
	
	private String avoidable;	
	
	private String remarkDetails;	
	
	private String remarkBrief;
	// DRIVE CATEGORY
	private String authority;
	
	// DRIVE CATEGORY ASS
	private String driveCategoryId;
	
	private Long oldDriveId;
	private String contentLink;
	private FunctionalLocationTypes functionalLocationTypes;
	private FunctionalLocationTypes depotTypes;
	private Long depoTypes;
	

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

	

	

	public String getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
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

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public String getPoulation() {
		return poulation;
	}

	public void setPoulation(String poulation) {
		this.poulation = poulation;
	}

	public Date getPerformedDate() {
		return performedDate;
	}

	public void setPerformedDate(Date performedDate) {
		this.performedDate = performedDate;
	}

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public double getPerformedCount() {
		return performedCount;
	}

	public void setPerformedCount(double performedCount) {
		this.performedCount = performedCount;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getFailure_id() {
		return failure_id;
	}

	public void setFailure_id(String failure_id) {
		this.failure_id = failure_id;
	}

	public String getReported() {
		return reported;
	}

	public void setReported(String reported) {
		this.reported = reported;
	}

	public String getRepurcussion() {
		return repurcussion;
	}

	public void setRepurcussion(String repurcussion) {
		this.repurcussion = repurcussion;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	

	public String getSubAssetType() {
		return subAssetType;
	}

	public void setSubAssetType(String subAssetType) {
		this.subAssetType = subAssetType;
	}

	public String getSubAssetId() {
		return subAssetId;
	}

	public void setSubAssetId(String subAssetId) {
		this.subAssetId = subAssetId;
	}

	
	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getActionTargetDate() {
		return actionTargetDate;
	}

	public void setActionTargetDate(Timestamp actionTargetDate) {
		this.actionTargetDate = actionTargetDate;
	}

	public Timestamp getActionCompletedDate() {
		return actionCompletedDate;
	}

	public void setActionCompletedDate(Timestamp actionCompletedDate) {
		this.actionCompletedDate = actionCompletedDate;
	}

	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	
	
	

	public String getAvoidable() {
		return avoidable;
	}

	public void setAvoidable(String avoidable) {
		this.avoidable = avoidable;
	}

	public String getRemarkDetails() {
		return remarkDetails;
	}

	public void setRemarkDetails(String remarkDetails) {
		this.remarkDetails = remarkDetails;
	}

	public String getRemarkBrief() {
		return remarkBrief;
	}

	public void setRemarkBrief(String remarkBrief) {
		this.remarkBrief = remarkBrief;
	}

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getDriveCategoryId() {
		return driveCategoryId;
	}

	public void setDriveCategoryId(String driveCategoryId) {
		this.driveCategoryId = driveCategoryId;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Long getOldDriveId() {
		return oldDriveId;
	}

	public String getContentLink() {
		return contentLink;
	}

	public void setContentLink(String contentLink) {
		this.contentLink = contentLink;
	}

	public void setOldDriveId(Long oldDriveId) {
		this.oldDriveId = oldDriveId;
	}

	public FunctionalLocationTypes getFunctionalLocationTypes() {
		return functionalLocationTypes;
	}

	public void setFunctionalLocationTypes(FunctionalLocationTypes functionalLocationTypes) {
		this.functionalLocationTypes = functionalLocationTypes;
	}

	public String getDriveScope() {
		return driveScope;
	}

	public void setDriveScope(String driveScope) {
		this.driveScope = driveScope;
	}

	public Date getFrmDate() {
		return frmDate;
	}

	public void setFrmDate(Date frmDate) {
		this.frmDate = frmDate;
	}

	public Date getToDat() {
		return toDat;
	}

	public void setToDat(Date toDat) {
		this.toDat = toDat;
	}

	public FunctionalLocationTypes getDepotTypes() {
		return depotTypes;
	}

	public void setDepotTypes(FunctionalLocationTypes depotTypes) {
		this.depotTypes = depotTypes;
	}

	public Long getDepoTypes() {
		return depoTypes;
	}

	public void setDepoTypes(Long depoTypes) {
		this.depoTypes = depoTypes;
	}



	

	

	

	
}
