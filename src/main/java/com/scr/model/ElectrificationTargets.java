package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "electrification_targets")
public class ElectrificationTargets implements Serializable {
 	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "section")
	private String section;

	@Column(name = "guage")
	private String guage;

	@Column(name = "target_date")
	private Date targetDate;

	@Column(name = "status")
	private String status;

	@Column(name = "division")
	private String division;

	@Column(name = "execution_agency")
	private String executionAgency;

	@Column(name = "TKM")
	private double TKM;

	@Column(name = "RKM")
	private double RKM;

	@Column(name = "crs_inspection")
	private String crsInspection;

	@Column(name = "crs_authorisation")
	private String crsAuthorisation;

	@Column(name = "target_set_by")
	private String targetSetBy;

	@Column(name = "doubling_trippling")
	private String doublingTrippling;

	@Column(name = "state")
	private String state;

	@Column(name = "phase")
	private String phase;

	@Column(name = "proposal_scheme")
	private String proposalScheme;

	@Column(name = "sanction_by_board")
	private String sanctionByBoard;

	@Column(name = "year_of_sanction")
	private String yearOfSanction;

	@Column(name = "date_of_completion")
	private Date dateOfCompletion;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	@Column(name = "status_id")
	private Integer statusId;

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

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
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

	public void setRKM(double RKM) {
		this.RKM = RKM;
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

	public Date getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setDateOfCompletion(Date dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	
	
}

	
