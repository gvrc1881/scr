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
import javax.persistence.Table;
import com.scr.model.Stipulations;
@Entity
@Table(name = "crs_eig_inspections")

public class CrsEigInspections implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "inspection_type")
	private String inspectionType;

	@Column(name = "section")
	private String section;

	@Column(name = "section_start_location")
	private String sectionStartLocation;

	@Column(name = "section_end_location")
	private String sectionEndLocation;

	@Column(name = "date_of_inspection")
	private Timestamp dateOfInspection;

	@Column(name = "RKM")
	private double RKM;

	@Column(name = "TKM")
	private double TKM;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "authorisation_date")
	private Timestamp authorisationDate;

	@Column(name = "charging_date")
	private Timestamp chargingDate;

	@Column(name = "attachment")
	private String attachment;

	@Column(name = "station")
	private String station;

	@Column(name = "credated_by")
	private String credatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@Column(name = "status_id")
	private Integer statusId;
	
	@Column(name = "crs_authorisation_no")
	private String crsAuthorisationNo;
	
	@Column(name = "inspection_status")
	private String inspectionStatus;
	
	@ManyToOne
	@JoinColumn(name = "stipulations_id", foreignKey = @ForeignKey(name = "fk_crs_eig_inspections_stipulations_id"))
	private Stipulations stipulationsId;
	
	@ManyToOne
	@JoinColumn(name = "work_group_id", foreignKey = @ForeignKey(name = "fk_crs_eig_inspections_work_group"))
	private WorkGroup workGroupId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
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

	public Timestamp getDateOfInspection() {
		return dateOfInspection;
	}

	public void setDateOfInspection(Timestamp dateOfInspection) {
		this.dateOfInspection = dateOfInspection;
	}

	public double getRKM() {
		return RKM;
	}

	public void setRKM(double rKM) {
		RKM = rKM;
	}

	public double getTKM() {
		return TKM;
	}

	public void setTKM(double tKM) {
		TKM = tKM;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getCredatedBy() {
		return credatedBy;
	}

	public void setCredatedBy(String credatedBy) {
		this.credatedBy = credatedBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Stipulations getStipulationsId() {
		return stipulationsId;
	}

	public void setStipulationsId(Stipulations stipulationsId) {
		this.stipulationsId = stipulationsId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getCrsAuthorisationNo() {
		return crsAuthorisationNo;
	}

	public void setCrsAuthorisationNo(String crsAuthorisationNo) {
		this.crsAuthorisationNo = crsAuthorisationNo;
	}

	public String getInspectionStatus() {
		return inspectionStatus;
	}

	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}

	public WorkGroup getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(WorkGroup workGroupId) {
		this.workGroupId = workGroupId;
	}

	@Override
	public String toString() {
		return "CrsEigInspections [id=" + id + ", inspectionType=" + inspectionType + ", section=" + section
				+ ", sectionStartLocation=" + sectionStartLocation + ", sectionEndLocation=" + sectionEndLocation
				+ ", dateOfInspection=" + dateOfInspection + ", RKM=" + RKM + ", TKM=" + TKM + ", remarks=" + remarks
				+ ", authorisationDate=" + authorisationDate + ", chargingDate=" + chargingDate + ", attachment="
				+ attachment + ", station=" + station + ", credatedBy=" + credatedBy + ", createdOn=" + createdOn
				+ ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", statusId=" + statusId
				+ ", crsAuthorisationNo=" + crsAuthorisationNo + ", inspectionStatus=" + inspectionStatus
				+ ", stipulationsId=" + stipulationsId + ", workGroupId=" + workGroupId + "]";
	}



}
