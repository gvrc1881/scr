package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the guidance_item database table.
 * 
 */
@Entity
@Table(name="guidence_item")
@NamedQuery(name="GuidenceItem.findAll", query="SELECT g FROM GuidenceItem g")
public class GuidenceItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="agency_rb_rdso")
	private String agencyRbRdso;

	private String closed;

	@Column(name="closed_remark" ,columnDefinition="TEXT")
	private String closedRemark;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="details_of_issue" , columnDefinition="TEXT")
	private String detailsOfIssue;

	private String heading;

	@Column(name="letter_no")
	private String letterNo;

	@Column(name="report_continue")
	private String reportContinue;

	private String response;

	@Column(name="short_description")
	private String shortDescription;

	private String status;

	public GuidenceItem() {
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgencyRbRdso() {
		return this.agencyRbRdso;
	}

	public void setAgencyRbRdso(String agencyRbRdso) {
		this.agencyRbRdso = agencyRbRdso;
	}

	public String getClosed() {
		return this.closed;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	public String getClosedRemark() {
		return this.closedRemark;
	}

	public void setClosedRemark(String closedRemark) {
		this.closedRemark = closedRemark;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDetailsOfIssue() {
		return this.detailsOfIssue;
	}

	public void setDetailsOfIssue(String detailsOfIssue) {
		this.detailsOfIssue = detailsOfIssue;
	}

	public String getHeading() {
		return this.heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getLetterNo() {
		return this.letterNo;
	}

	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}

	public String getReportContinue() {
		return this.reportContinue;
	}

	public void setReportContinue(String reportContinue) {
		this.reportContinue = reportContinue;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
