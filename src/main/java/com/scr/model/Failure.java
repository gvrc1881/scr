package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the failures database table.
 * 
 */
@Entity
// @Table(name="failures")

@Table(name = "failures" , uniqueConstraints={@UniqueConstraint(name = "old_pk_failures_uniq", columnNames ={"data_div", "failure_seq_id"})})
//
@NamedQuery(name="Failure.findAll", query="SELECT f FROM Failure f")
public class Failure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="actual_fault_distance")
	private String actualFaultDistance;

	@Column(name="asset_id")
	private String assetId;

	@Column(name="asset_type")
	private String assetType;

	@Column(name="cascade_assets")
	private String cascadeAssets;

	@Column(name="cause_of_failure")
	private String causeOfFailure;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	private String current;

	@Column(name="current_status")
	private String currentStatus;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="division_local")
	private String divisionLocal;

	private String duration;

	private String equipment;

	@Column(name="extended_of")
	private String extendedOf;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="failure_date_time")
	private Timestamp failureDateTime;

	@Column(name="failure_seq_id")
	private String failureSeqId;

	@Column(name="fault_distance")
	private String faultDistance;

	@Column(name="feed_extended_duration")
	private String feedExtendedDuration;

	@Column(name="feed_extended_from_date_time")
	private Timestamp feedExtendedFromDateTime;

	@Column(name="feed_extended_thru_date_time")
	private Timestamp feedExtendedThruDateTime;

	@Column(name="feed_of")
	private String feedOf;

	@Column(name="from_date_time")
	private Timestamp fromDateTime;

	private String impact;

	@Column(name="internal_external")
	private String internalExternal;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String location;

	@Column(name="max_demand")
	private String maxDemand;

	@Column(name="nature_of_closure")
	private String natureOfClosure;

	private String occurrence;

	@Column(name="phase_angle")
	private String phaseAngle;

	private String place;

	@Column(name="punctuality_affected_to")
	private String punctualityAffectedTo;

	@Column(name="r_value")
	private String rValue;

	@Column(name="relay_indication")
	private String relayIndication;

	private String remarks;

	@Column(name="resume_date_time")
	private Timestamp resumeDateTime;

	private String section;

	private String staff;

	@Column(name="sub_station")
	private String subStation;

	@Column(name="thru_date_time")
	private Timestamp thruDateTime;

	@Column(name="time_delay")
	private String timeDelay;

	@Column(name="tpc_board")
	private String tpcBoard;

	@Column(name="train_no")
	private String trainNo;

	@Column(name="tripped_identified_fault")
	private String trippedIdentifiedFault;

	@Column(name="type_of_failure")
	private String typeOfFailure;

	private String voltage;

	@Column(name="x_value")
	private String xValue;

	public Failure() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActualFaultDistance() {
		return this.actualFaultDistance;
	}

	public void setActualFaultDistance(String actualFaultDistance) {
		this.actualFaultDistance = actualFaultDistance;
	}

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetType() {
		return this.assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getCascadeAssets() {
		return this.cascadeAssets;
	}

	public void setCascadeAssets(String cascadeAssets) {
		this.cascadeAssets = cascadeAssets;
	}

	public String getCauseOfFailure() {
		return this.causeOfFailure;
	}

	public void setCauseOfFailure(String causeOfFailure) {
		this.causeOfFailure = causeOfFailure;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getCreatedStamp() {
		return this.createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return this.createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public String getCurrent() {
		return this.current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getCurrentStatus() {
		return this.currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getDivisionLocal() {
		return this.divisionLocal;
	}

	public void setDivisionLocal(String divisionLocal) {
		this.divisionLocal = divisionLocal;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEquipment() {
		return this.equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getExtendedOf() {
		return this.extendedOf;
	}

	public void setExtendedOf(String extendedOf) {
		this.extendedOf = extendedOf;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Timestamp getFailureDateTime() {
		return this.failureDateTime;
	}

	public void setFailureDateTime(Timestamp failureDateTime) {
		this.failureDateTime = failureDateTime;
	}

	public String getFailureSeqId() {
		return this.failureSeqId;
	}

	public void setFailureSeqId(String failureSeqId) {
		this.failureSeqId = failureSeqId;
	}

	public String getFaultDistance() {
		return this.faultDistance;
	}

	public void setFaultDistance(String faultDistance) {
		this.faultDistance = faultDistance;
	}

	public String getFeedExtendedDuration() {
		return this.feedExtendedDuration;
	}

	public void setFeedExtendedDuration(String feedExtendedDuration) {
		this.feedExtendedDuration = feedExtendedDuration;
	}

	public Timestamp getFeedExtendedFromDateTime() {
		return this.feedExtendedFromDateTime;
	}

	public void setFeedExtendedFromDateTime(Timestamp feedExtendedFromDateTime) {
		this.feedExtendedFromDateTime = feedExtendedFromDateTime;
	}

	public Timestamp getFeedExtendedThruDateTime() {
		return this.feedExtendedThruDateTime;
	}

	public void setFeedExtendedThruDateTime(Timestamp feedExtendedThruDateTime) {
		this.feedExtendedThruDateTime = feedExtendedThruDateTime;
	}

	public String getFeedOf() {
		return this.feedOf;
	}

	public void setFeedOf(String feedOf) {
		this.feedOf = feedOf;
	}

	public Timestamp getFromDateTime() {
		return this.fromDateTime;
	}

	public void setFromDateTime(Timestamp fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public String getImpact() {
		return this.impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public String getInternalExternal() {
		return this.internalExternal;
	}

	public void setInternalExternal(String internalExternal) {
		this.internalExternal = internalExternal;
	}

	public Timestamp getLastUpdatedStamp() {
		return this.lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return this.lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMaxDemand() {
		return this.maxDemand;
	}

	public void setMaxDemand(String maxDemand) {
		this.maxDemand = maxDemand;
	}

	public String getNatureOfClosure() {
		return this.natureOfClosure;
	}

	public void setNatureOfClosure(String natureOfClosure) {
		this.natureOfClosure = natureOfClosure;
	}

	public String getOccurrence() {
		return this.occurrence;
	}

	public void setOccurrence(String occurrence) {
		this.occurrence = occurrence;
	}

	public String getPhaseAngle() {
		return this.phaseAngle;
	}

	public void setPhaseAngle(String phaseAngle) {
		this.phaseAngle = phaseAngle;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPunctualityAffectedTo() {
		return this.punctualityAffectedTo;
	}

	public void setPunctualityAffectedTo(String punctualityAffectedTo) {
		this.punctualityAffectedTo = punctualityAffectedTo;
	}

	public String getRValue() {
		return this.rValue;
	}

	public void setRValue(String rValue) {
		this.rValue = rValue;
	}

	public String getRelayIndication() {
		return this.relayIndication;
	}

	public void setRelayIndication(String relayIndication) {
		this.relayIndication = relayIndication;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getResumeDateTime() {
		return this.resumeDateTime;
	}

	public void setResumeDateTime(Timestamp resumeDateTime) {
		this.resumeDateTime = resumeDateTime;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getStaff() {
		return this.staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getSubStation() {
		return this.subStation;
	}

	public void setSubStation(String subStation) {
		this.subStation = subStation;
	}

	public Timestamp getThruDateTime() {
		return this.thruDateTime;
	}

	public void setThruDateTime(Timestamp thruDateTime) {
		this.thruDateTime = thruDateTime;
	}

	public String getTimeDelay() {
		return this.timeDelay;
	}

	public void setTimeDelay(String timeDelay) {
		this.timeDelay = timeDelay;
	}

	public String getTpcBoard() {
		return this.tpcBoard;
	}

	public void setTpcBoard(String tpcBoard) {
		this.tpcBoard = tpcBoard;
	}

	public String getTrainNo() {
		return this.trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrippedIdentifiedFault() {
		return this.trippedIdentifiedFault;
	}

	public void setTrippedIdentifiedFault(String trippedIdentifiedFault) {
		this.trippedIdentifiedFault = trippedIdentifiedFault;
	}

	public String getTypeOfFailure() {
		return this.typeOfFailure;
	}

	public void setTypeOfFailure(String typeOfFailure) {
		this.typeOfFailure = typeOfFailure;
	}

	public String getVoltage() {
		return this.voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getXValue() {
		return this.xValue;
	}

	public void setXValue(String xValue) {
		this.xValue = xValue;
	}

}