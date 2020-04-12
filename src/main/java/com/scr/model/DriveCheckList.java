package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the report_registry database table.
 * 
 */

@Entity
@Table(name = "drive_check_list")
@NamedQuery(name = "DriveCheckList.findAll", query = "SELECT dcl FROM DriveCheckList dcl")
public class DriveCheckList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "activity_position_id")
	private String activityPositionId;

	@Column(name = "display_order")
	private Integer displayOrder;

	@Column(name = "active")
	private String active;

	@Column(name = "lower_limit")
	private double lowerLimit;

	@Column(name = "upper_limit")
	private double upperLimit;

	@Column(name = "report_column_header")
	private String reportColumnHeader;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "updatedby")
	private String updatedBy;

	@Column(name = "createdon")
	private Timestamp createdOn;

	@Column(name = "updatedon")
	private Timestamp updatedOn;
	
	@Column(name = "status_id")
	private Integer statusId;

	@ManyToOne
	@JoinColumn(name = "activity_id", foreignKey = @ForeignKey(name = "fk_drive_check_list_activity_id"))
	private MeasureOrActivityList activityId;

	@ManyToOne
	@JoinColumn(name = "drive_id", foreignKey = @ForeignKey(name = "fk_drive_check_list_drive_id"))
	private Drives driveId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public MeasureOrActivityList getActivityId() {
		return activityId;
	}

	public void setActivityId(MeasureOrActivityList activityId) {
		this.activityId = activityId;
	}

	public Drives getDriveId() {
		return driveId;
	}

	public void setDriveId(Drives driveId) {
		this.driveId = driveId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	
}
