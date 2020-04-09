package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the report_group database table.
 * 
 */
@Entity
@Table(name = "report_group" , uniqueConstraints={@UniqueConstraint(name = "old_pk_report_group_uniq", columnNames ={"report_group_id"})})
@NamedQuery(name="ReportGroup.findAll", query="SELECT r FROM ReportGroup r")
public class ReportGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String application;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	private String description;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="report_group_id")
	private String reportGroupId;

	@Column(name="sequence_num")
	private BigDecimal sequenceNum;

	@Column(name="show_in_select")
	private String showInSelect;

	public ReportGroup() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplication() {
		return this.application;
	}

	public void setApplication(String application) {
		this.application = application;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public BigDecimal getSequenceNum() {
		return this.sequenceNum;
	}

	public void setSequenceNum(BigDecimal sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getShowInSelect() {
		return this.showInSelect;
	}

	public void setShowInSelect(String showInSelect) {
		this.showInSelect = showInSelect;
	}

}