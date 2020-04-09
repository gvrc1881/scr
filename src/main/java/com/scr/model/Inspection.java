package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the inspection database table.
 * 
 */
@Entity
//
@Table(name = "inspection" , uniqueConstraints={@UniqueConstraint(name = "old_pk_inspection_uniq", columnNames ={"inspection_seq_id", "data_div"})})
//
@NamedQuery(name="Inspection.findAll", query="SELECT i FROM Inspection i")
public class Inspection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

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

	@Column(name="current_status")
	private String currentStatus;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="end_location")
	private String endLocation;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="from_date_time")
	private Timestamp fromDateTime;

	@Column(name="inspection_seq_id")
	private String inspectionSeqId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="name_of_staff")
	private String nameOfStaff;

	@Column(name="next_day_plan")
	private String nextDayPlan;

	private String observation;

	@Column(name="place_of_work")
	private String placeOfWork;

	private String remarks;

	private String schedule;

	private String section;

	@Column(name="staff_type")
	private String staffType;

	@Column(name="start_location")
	private String startLocation;

	@Column(name="status_of_work")
	private String statusOfWork;

	private String tech;

	@Column(name="thru_date_time")
	private Timestamp thruDateTime;

	@Column(name="type_of_work")
	private String typeOfWork;

	public Inspection() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEndLocation() {
		return this.endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Timestamp getFromDateTime() {
		return this.fromDateTime;
	}

	public void setFromDateTime(Timestamp fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public String getInspectionSeqId() {
		return this.inspectionSeqId;
	}

	public void setInspectionSeqId(String inspectionSeqId) {
		this.inspectionSeqId = inspectionSeqId;
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

	public String getNameOfStaff() {
		return this.nameOfStaff;
	}

	public void setNameOfStaff(String nameOfStaff) {
		this.nameOfStaff = nameOfStaff;
	}

	public String getNextDayPlan() {
		return this.nextDayPlan;
	}

	public void setNextDayPlan(String nextDayPlan) {
		this.nextDayPlan = nextDayPlan;
	}

	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getPlaceOfWork() {
		return this.placeOfWork;
	}

	public void setPlaceOfWork(String placeOfWork) {
		this.placeOfWork = placeOfWork;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getStaffType() {
		return this.staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public String getStartLocation() {
		return this.startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getStatusOfWork() {
		return this.statusOfWork;
	}

	public void setStatusOfWork(String statusOfWork) {
		this.statusOfWork = statusOfWork;
	}

	public String getTech() {
		return this.tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public Timestamp getThruDateTime() {
		return this.thruDateTime;
	}

	public void setThruDateTime(Timestamp thruDateTime) {
		this.thruDateTime = thruDateTime;
	}

	public String getTypeOfWork() {
		return this.typeOfWork;
	}

	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}

}