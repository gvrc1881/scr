package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the report_group_member database table.
 * 
 */
@Entity
@Table(name = "report_group_member" , uniqueConstraints={@UniqueConstraint(name = "old_pk_report_group_member_uniq", columnNames ={"report_group_id", "report_id"})})
@NamedQuery(name="ReportGroupMember.findAll", query="SELECT r FROM ReportGroupMember r")
public class ReportGroupMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="report_group_id")
	private String reportGroupId;

	@Column(name="report_id")
	private String reportId;

	@Column(name="sequence_num")
	private BigDecimal sequenceNum;

	public ReportGroupMember() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedStamp() {
		return this.createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return this.createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public Timestamp getLastUpdatedStamp() {
		return this.lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return this.lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getReportGroupId() {
		return this.reportGroupId;
	}

	public void setReportGroupId(String reportGroupId) {
		this.reportGroupId = reportGroupId;
	}

	public String getReportId() {
		return this.reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public BigDecimal getSequenceNum() {
		return this.sequenceNum;
	}

	public void setSequenceNum(BigDecimal sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

}