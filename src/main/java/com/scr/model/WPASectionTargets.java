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

	@Column(name = "work_phase_activity_id")
	private String workPhaseActivityId;

	@Column(name = "work_grade_id")
	private String workGradeId;

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

	public WPASectionTargets() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorkPhaseActivityId() {
		return workPhaseActivityId;
	}

	public void setWorkPhaseActivityId(String workPhaseActivityId) {
		this.workPhaseActivityId = workPhaseActivityId;
	}

	public String getWorkGradeId() {
		return workGradeId;
	}

	public void setWorkGradeId(String workGradeId) {
		this.workGradeId = workGradeId;
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


}
