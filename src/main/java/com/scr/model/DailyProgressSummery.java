package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;



@Entity
@Table(name = "daily_progress_summery" , uniqueConstraints={@UniqueConstraint(columnNames ={"seq_id"})})
@NamedQuery(name="DailyProgressSummery.findAll", query="SELECT d FROM DailyProgressSummery d")
public class DailyProgressSummery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;
	
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

	@Column(name="day_progress",columnDefinition="TEXT")
	private String dayProgress;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="foot_inspection" , columnDefinition="TEXT")
	private String footInspection;

	@Column(name="foot_patrolling" , columnDefinition="TEXT")
	private String footPatrolling;

	@Column(name="foot_plate_inspection" , columnDefinition="TEXT")
	private String footPlateInspection;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String location;

	@Column(name="name_of_staff")
	private String nameOfStaff;

	@Column(name="non_power_block" , columnDefinition="TEXT")
	private String nonPowerBlock;

	@Column(name="npb_progress" , columnDefinition="TEXT")
	private String npbProgress;

	@Column(name="power_block" , columnDefinition="TEXT")
	private String powerBlock;

	@Column(name="psi_progress" , columnDefinition="TEXT")
	private String psiProgress;

	private String remarks;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="serial_no")
	private String serialNo;

	@Column(name="staff_strength" , columnDefinition="TEXT")
	private String staffStrength;

	@Column(columnDefinition="TEXT")
	private String supervisor;

	@Column(name="tomorrow_forecast" , columnDefinition="TEXT")
	private String tomorrowForecast;

	public DailyProgressSummery() {
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

	

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
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

	public String getDayProgress() {
		return this.dayProgress;
	}

	public void setDayProgress(String dayProgress) {
		this.dayProgress = dayProgress;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFootInspection() {
		return this.footInspection;
	}

	public void setFootInspection(String footInspection) {
		this.footInspection = footInspection;
	}

	public String getFootPatrolling() {
		return this.footPatrolling;
	}

	public void setFootPatrolling(String footPatrolling) {
		this.footPatrolling = footPatrolling;
	}

	public String getFootPlateInspection() {
		return this.footPlateInspection;
	}

	public void setFootPlateInspection(String footPlateInspection) {
		this.footPlateInspection = footPlateInspection;
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

	public String getNameOfStaff() {
		return this.nameOfStaff;
	}

	public void setNameOfStaff(String nameOfStaff) {
		this.nameOfStaff = nameOfStaff;
	}

	public String getNonPowerBlock() {
		return this.nonPowerBlock;
	}

	public void setNonPowerBlock(String nonPowerBlock) {
		this.nonPowerBlock = nonPowerBlock;
	}

	public String getNpbProgress() {
		return this.npbProgress;
	}

	public void setNpbProgress(String npbProgress) {
		this.npbProgress = npbProgress;
	}

	public String getPowerBlock() {
		return this.powerBlock;
	}

	public void setPowerBlock(String powerBlock) {
		this.powerBlock = powerBlock;
	}

	public String getPsiProgress() {
		return this.psiProgress;
	}

	public void setPsiProgress(String psiProgress) {
		this.psiProgress = psiProgress;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getStaffStrength() {
		return this.staffStrength;
	}

	public void setStaffStrength(String staffStrength) {
		this.staffStrength = staffStrength;
	}

	public String getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getTomorrowForecast() {
		return this.tomorrowForecast;
	}

	public void setTomorrowForecast(String tomorrowForecast) {
		this.tomorrowForecast = tomorrowForecast;
	}

	@Override
	public String toString() {
		return "DailyProgressSummery [id=" + id + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", createdOn=" + createdOn + ", createdStamp=" + createdStamp + ", createdTxStamp=" + createdTxStamp
				+ ", currentStatus=" + currentStatus + ", dataDiv=" + dataDiv + ", dayProgress=" + dayProgress
				+ ", facilityId=" + facilityId + ", footInspection=" + footInspection + ", footPatrolling="
				+ footPatrolling + ", footPlateInspection=" + footPlateInspection + ", lastUpdatedStamp="
				+ lastUpdatedStamp + ", lastUpdatedTxStamp=" + lastUpdatedTxStamp + ", location=" + location
				+ ", nameOfStaff=" + nameOfStaff + ", nonPowerBlock=" + nonPowerBlock + ", npbProgress=" + npbProgress
				+ ", powerBlock=" + powerBlock + ", psiProgress=" + psiProgress + ", remarks=" + remarks + ", seqId="
				+ seqId + ", serialNo=" + serialNo + ", staffStrength=" + staffStrength + ", supervisor=" + supervisor
				+ ", tomorrowForecast=" + tomorrowForecast + "]";
	}

}