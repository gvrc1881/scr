package com.scr.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "workPhases")
@NamedQuery(name = "WorkPhases.findAll", query = "SELECT wp FROM WorkPhases wp")
public class WorkPhases {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String phaseName;

	private String description;

	private Integer sequence;

	private String dependencyToStart;

	private double weightage;

	@Temporal(TemporalType.DATE)
	private Date plannedStartDate;

	@Temporal(TemporalType.DATE)
	private Date commenceDate;

	@Temporal(TemporalType.DATE)
	private Date targetCompletionDate;

	@Temporal(TemporalType.DATE)
	private Date completionDate;

	private String status;

	@ManyToOne
	@JoinColumn(name = "work_id", foreignKey = @ForeignKey(name = "fk_work_phases_works"))
	private Works workId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getDependencyToStart() {
		return dependencyToStart;
	}

	public void setDependencyToStart(String dependencyToStart) {
		this.dependencyToStart = dependencyToStart;
	}

	public double getWeightage() {
		return weightage;
	}

	public void setWeightage(double weightage) {
		this.weightage = weightage;
	}

	public Date getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(Date plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public Date getCommenceDate() {
		return commenceDate;
	}

	public void setCommenceDate(Date commenceDate) {
		this.commenceDate = commenceDate;
	}

	public Date getTargetCompletionDate() {
		return targetCompletionDate;
	}

	public void setTargetCompletionDate(Date targetCompletionDate) {
		this.targetCompletionDate = targetCompletionDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Works getWorkId() {
		return workId;
	}

	public void setWorkId(Works workId) {
		this.workId = workId;
	}

	@Override
	public String toString() {
		return "WorkPhases [id=" + id + ", phaseName=" + phaseName + ", description=" + description + ", sequence="
				+ sequence + ", dependencyToStart=" + dependencyToStart + ", weightage=" + weightage
				+ ", plannedStartDate=" + plannedStartDate + ", commenceDate=" + commenceDate
				+ ", targetCompletionDate=" + targetCompletionDate + ", completionDate=" + completionDate + ", status="
				+ status + ", workId=" + workId + "]";
	}

}
