package com.scr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Embeddable
@Entity

@Table(name = "report_repository")
public class ReportRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "active")
	private String active;
	
	@Column(name = "report_Id")
	private String reportId;

	@Column(name = "report_category")
	private String reportCategory;

	@Column(name = "jrxml_version")
	private String jrxmlVersion;

	@Column(name = "jrxml_name")
	private String jrxmlName;

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportCategory() {
		return reportCategory;
	}

	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}

	public String getJrxmlVersion() {
		return jrxmlVersion;
	}

	public void setJrxmlVersion(String jrxmlVersion) {
		this.jrxmlVersion = jrxmlVersion;
	}

	public String getJrxmlName() {
		return jrxmlName;
	}

	public void setJrxmlName(String jrxmlName) {
		this.jrxmlName = jrxmlName;
	}


	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "ReportRepository [id=" + id + ", active=" + active + ", reportId=" + reportId + ", reportCategory="
				+ reportCategory + ", jrxmlVersion=" + jrxmlVersion + ", jrxmlName=" + jrxmlName + "]";
	}

	

	

}