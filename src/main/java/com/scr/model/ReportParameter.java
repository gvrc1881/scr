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

@Table(name = "report_parameter")
public class ReportParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "report_Id")
	private String reportId;

	@Column(name = "parameter_name")
	private String parameterName;

	@Column(name = "short_name")
	private String shortName;

	@Column(name = "data_type")
	private String dataType;

	@Column(name = "services")
	private String services;

	@Column(name = "data_wizard")
	private String dataWizard;

	@Column(name = "prompt_label")
	private String promptLabel;
	

	

	public String getReportId() {
		return reportId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setReportName(String reportId) {
		this.reportId = reportId;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getDataWizard() {
		return dataWizard;
	}

	public void setDataWizard(String dataWizard) {
		this.dataWizard = dataWizard;
	}

	public String getPromptLabel() {
		return promptLabel;
	}

	public void setPromptLabel(String promptLabel) {
		this.promptLabel = promptLabel;
	}

	@Override
	public String toString() {
		return "ReportParameter [reportId =" + reportId + ", parameterName =" + parameterName + ", shortName="
				+ shortName + ", dataType=" + dataType + ", services=" + services + ", dataWizard=" + dataWizard
				+ ", promptLabel=" + promptLabel + "]";
	}

}
