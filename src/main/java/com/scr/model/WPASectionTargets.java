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
@Table(name = "wpa_section_targets")
@NamedQuery(name = "WPASectionTargets.findAll", query = "SELECT w FROM WPASectionTargets w")

public class WPASectionTargets implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "work_phase_activity_id", foreignKey = @ForeignKey(name = "fk_wpa_section_targets_work_phase_activity"))
	private WorkPhaseActivity workPhaseActivityId;

	@ManyToOne
	@JoinColumn(name = "work_group_id", foreignKey = @ForeignKey(name = "fk_wpa_section_targets_work_group"))
	private WorkGroup workGroupId;

	@Column(name = "year_type")
	private String yearType;

	@Column(name = "year")
	private Integer year;

	@Column(name = "apr")
	private Double apr;

	@Column(name = "may")
	private Double may;

	@Column(name = "jun")
	private Double jun;;

	@Column(name = "jul")
	private Double jul;

	@Column(name = "aug")
	private Double aug;

	@Column(name = "sep")
	private Double sep;

	@Column(name = "oct")
	private Double oct;

	@Column(name = "nov")
	private Double nov;

	@Column(name = "dec")
	private Double dec;

	@Column(name = "jan")
	private Double jan;

	@Column(name = "feb")
	private Double feb;

	@Column(name = "mar")
	private Double mar;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public WPASectionTargets() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYearType() {
		return yearType;
	}

	public void setYearType(String yearType) {
		this.yearType = yearType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getApr() {
		return apr;
	}

	public void setApr(Double apr) {
		this.apr = apr;
	}

	public Double getMay() {
		return may;
	}

	public void setMay(Double may) {
		this.may = may;
	}

	public Double getJun() {
		return jun;
	}

	public void setJun(Double jun) {
		this.jun = jun;
	}

	public Double getJul() {
		return jul;
	}

	public void setJul(Double jul) {
		this.jul = jul;
	}

	public Double getAug() {
		return aug;
	}

	public void setAug(Double aug) {
		this.aug = aug;
	}

	public Double getSep() {
		return sep;
	}

	public void setSep(Double sep) {
		this.sep = sep;
	}

	public Double getOct() {
		return oct;
	}

	public void setOct(Double oct) {
		this.oct = oct;
	}

	public Double getNov() {
		return nov;
	}

	public void setNov(Double nov) {
		this.nov = nov;
	}

	public Double getDec() {
		return dec;
	}

	public void setDec(Double dec) {
		this.dec = dec;
	}

	public Double getJan() {
		return jan;
	}

	public void setJan(Double jan) {
		this.jan = jan;
	}

	public Double getFeb() {
		return feb;
	}

	public void setFeb(Double feb) {
		this.feb = feb;
	}

	public Double getMar() {
		return mar;
	}

	public void setMar(Double mar) {
		this.mar = mar;
	}

	public WorkPhaseActivity getWorkPhaseActivityId() {
		return workPhaseActivityId;
	}

	public void setWorkPhaseActivityId(WorkPhaseActivity workPhaseActivityId) {
		this.workPhaseActivityId = workPhaseActivityId;
	}

	public WorkGroup getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(WorkGroup workGroupId) {
		this.workGroupId = workGroupId;
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

}
