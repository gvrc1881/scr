package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the works database table.
 * 
 */
@Entity
@Table(name = "works")
@NamedQuery(name = "Works.findAll", query = "SELECT w FROM Works w")
public class Works implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String allocation;

	private String division;

	@Column(name = "estd_latest_antic_cost")
	private String estdLatestAnticCost;

	@Column(name = "executed_by")
	private String executedBy;

	@Column(name = "executing_agency")
	private String executingAgency;

	@Column(name = "financial_progress_percentage")
	private String financialProgressPercentage;

	@Column(name = "latest_revised_cost")
	private BigDecimal latestRevisedCost;

	@Column(name = "pb_law_lswp")
	private String pbLawLswp;

	@Column(name = "pb_law_lswp_code")
	private String pbLawLswpCode;

	@Column(name = "physical_progress_percentage")
	private String physicalProgressPercentage;

	@Column(name = "present_status")
	private String presentStatus;

	@Column(name = "re_works")
	private String reWorks;

	private Double rkm;

	@Column(name = "sanction_cost")
	private BigDecimal sanctionCost;

	private String section;

	@Column(name = "status_remarks")
	private String statusRemarks;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "target_start_date")
	private Date targetStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "target_date_of_completion")
	private Date targetDateOfCompletion;

	private Double tkm;

	@Column(name = "work_name")
	private String workName;

	@Column(name = "year_of_sanction")
	private Integer yearOfSanction;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@Column(name = "loa_no")
	private String loaNo;

	@Column(name = "loa_date")
	@Temporal(TemporalType.DATE)
	private Date loaDate;

	@Column(name = "start_km")
	private Double startKm;

	@Column(name = "end_km")
	private Double endKm;

	@Column(name = "tender_value")
	private Double tenderValue;

	@Column(name = "commencement_date")
	@Temporal(TemporalType.DATE)
	private Date commencementDate;

	@Column(name = "expected_completion")
	@Temporal(TemporalType.DATE)
	private Date expectedCompletion;

	private String lineType;

	public Works() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAllocation() {
		return this.allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEstdLatestAnticCost() {
		return this.estdLatestAnticCost;
	}

	public void setEstdLatestAnticCost(String estdLatestAnticCost) {
		this.estdLatestAnticCost = estdLatestAnticCost;
	}

	public String getExecutedBy() {
		return this.executedBy;
	}

	public void setExecutedBy(String executedBy) {
		this.executedBy = executedBy;
	}

	public String getExecutingAgency() {
		return this.executingAgency;
	}

	public void setExecutingAgency(String executingAgency) {
		this.executingAgency = executingAgency;
	}

	public String getFinancialProgressPercentage() {
		return this.financialProgressPercentage;
	}

	public void setFinancialProgressPercentage(String financialProgressPercentage) {
		this.financialProgressPercentage = financialProgressPercentage;
	}

	public BigDecimal getLatestRevisedCost() {
		return this.latestRevisedCost;
	}

	public void setLatestRevisedCost(BigDecimal latestRevisedCost) {
		this.latestRevisedCost = latestRevisedCost;
	}

	public String getPbLawLswp() {
		return this.pbLawLswp;
	}

	public void setPbLawLswp(String pbLawLswp) {
		this.pbLawLswp = pbLawLswp;
	}

	public String getPbLawLswpCode() {
		return this.pbLawLswpCode;
	}

	public void setPbLawLswpCode(String pbLawLswpCode) {
		this.pbLawLswpCode = pbLawLswpCode;
	}

	public String getPhysicalProgressPercentage() {
		return this.physicalProgressPercentage;
	}

	public void setPhysicalProgressPercentage(String physicalProgressPercentage) {
		this.physicalProgressPercentage = physicalProgressPercentage;
	}

	public String getPresentStatus() {
		return this.presentStatus;
	}

	public void setPresentStatus(String presentStatus) {
		this.presentStatus = presentStatus;
	}

	public String getReWorks() {
		return this.reWorks;
	}

	public void setReWorks(String reWorks) {
		this.reWorks = reWorks;
	}

	public BigDecimal getSanctionCost() {
		return this.sanctionCost;
	}

	public void setSanctionCost(BigDecimal sanctionCost) {
		this.sanctionCost = sanctionCost;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getStatusRemarks() {
		return this.statusRemarks;
	}

	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}

	public Date getTargetDateOfCompletion() {
		return this.targetDateOfCompletion;
	}

	public void setTargetDateOfCompletion(Date targetDateOfCompletion) {
		this.targetDateOfCompletion = targetDateOfCompletion;
	}

	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Integer getYearOfSanction() {
		return this.yearOfSanction;
	}

	public void setYearOfSanction(Integer yearOfSanction) {
		this.yearOfSanction = yearOfSanction;
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

	public Double getRkm() {
		return rkm;
	}

	public void setRkm(Double rkm) {
		this.rkm = rkm;
	}

	public Double getTkm() {
		return tkm;
	}

	public void setTkm(Double tkm) {
		this.tkm = tkm;
	}

	public String getLoaNo() {
		return loaNo;
	}

	public void setLoaNo(String loaNo) {
		this.loaNo = loaNo;
	}

	public Date getLoaDate() {
		return loaDate;
	}

	public void setLoaDate(Date loaDate) {
		this.loaDate = loaDate;
	}

	public Double getStartKm() {
		return startKm;
	}

	public void setStartKm(Double startKm) {
		this.startKm = startKm;
	}

	public Double getEndKm() {
		return endKm;
	}

	public void setEndKm(Double endKm) {
		this.endKm = endKm;
	}

	public Double getTenderValue() {
		return tenderValue;
	}

	public void setTenderValue(Double tenderValue) {
		this.tenderValue = tenderValue;
	}

	public Date getCommencementDate() {
		return commencementDate;
	}

	public void setCommencementDate(Date commencementDate) {
		this.commencementDate = commencementDate;
	}

	public Date getExpectedCompletion() {
		return expectedCompletion;
	}

	public void setExpectedCompletion(Date expectedCompletion) {
		this.expectedCompletion = expectedCompletion;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public Date getTargetStartDate() {
		return targetStartDate;
	}

	public void setTargetStartDate(Date targetStartDate) {
		this.targetStartDate = targetStartDate;
	}

	@Override
	public String toString() {
		return "Works [id=" + id + ", allocation=" + allocation + ", division=" + division + ", estdLatestAnticCost="
				+ estdLatestAnticCost + ", executedBy=" + executedBy + ", executingAgency=" + executingAgency
				+ ", financialProgressPercentage=" + financialProgressPercentage + ", latestRevisedCost="
				+ latestRevisedCost + ", pbLawLswp=" + pbLawLswp + ", pbLawLswpCode=" + pbLawLswpCode
				+ ", physicalProgressPercentage=" + physicalProgressPercentage + ", presentStatus=" + presentStatus
				+ ", reWorks=" + reWorks + ", rkm=" + rkm + ", sanctionCost=" + sanctionCost + ", section=" + section
				+ ", statusRemarks=" + statusRemarks + ", targetStartDate=" + targetStartDate
				+ ", targetDateOfCompletion=" + targetDateOfCompletion + ", tkm=" + tkm + ", workName=" + workName
				+ ", yearOfSanction=" + yearOfSanction + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", loaNo=" + loaNo + ", loaDate=" + loaDate
				+ ", startKm=" + startKm + ", endKm=" + endKm + ", tenderValue=" + tenderValue + ", commencementDate="
				+ commencementDate + ", expectedCompletion=" + expectedCompletion + ", lineType=" + lineType + "]";
	}


}