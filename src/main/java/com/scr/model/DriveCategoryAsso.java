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
@Table(name = "drive_category_asso")
@NamedQuery(name="DriveCategoryAsso.findAll", query="SELECT dca FROM DriveCategoryAsso dca")
public class DriveCategoryAsso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "active")
	private String active;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	@ManyToOne
	@JoinColumn(name = "drive_id", foreignKey = @ForeignKey(name = "fk_drive_category_asso_drive_id"))
	private Drives driveId;
	
	@ManyToOne
	@JoinColumn(name = "drive_category_id", foreignKey = @ForeignKey(name = "fk_drive_category_asso_drive_category_id"))
	private DriveCategory driveCategoryId;

	@Column(name = "status_id")
	private Integer statusId;
	
	@ManyToOne
	@JoinColumn(name = "report_sub_heading", foreignKey = @ForeignKey(name = "fk_drive_category_asso_drive_report_sub_heading"))
	private DriveReportSubHeadings reportSubHeading;
	
	private Long reportOrder;
	
	private String reportDisplayId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Drives getDriveId() {
		return driveId;
	}

	public void setDriveId(Drives driveId) {
		this.driveId = driveId;
	}

	public DriveCategory getDriveCategoryId() {
		return driveCategoryId;
	}

	public void setDriveCategoryId(DriveCategory driveCategoryId) {
		this.driveCategoryId = driveCategoryId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public DriveReportSubHeadings getReportSubHeading() {
		return reportSubHeading;
	}

	public void setReportSubHeading(DriveReportSubHeadings reportSubHeading) {
		this.reportSubHeading = reportSubHeading;
	}

	public Long getReportOrder() {
		return reportOrder;
	}

	public void setReportOrder(Long reportOrder) {
		this.reportOrder = reportOrder;
	}

	public String getReportDisplayId() {
		return reportDisplayId;
	}

	public void setReportDisplayId(String reportDisplayId) {
		this.reportDisplayId = reportDisplayId;
	}

	@Override
	public String toString() {
		return "DriveCategoryAsso [id=" + id + ", active=" + active + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", driveId=" + driveId
				+ ", driveCategoryId=" + driveCategoryId + ", statusId=" + statusId + ", reportSubHeading="
				+ reportSubHeading + ", reportOrder=" + reportOrder + ", reportDisplayId=" + reportDisplayId + "]";
	}



}
