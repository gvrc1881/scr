package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the precautionary_measure database table.
 * 
 */
@Entity
@Table(name = "precautionary_measure" , uniqueConstraints={@UniqueConstraint(name = "old_pk_precautionary_measure_uniq", columnNames ={"seq_id", "data_div"})})
@NamedQuery(name="PrecautionaryMeasure.findAll", query="SELECT p FROM PrecautionaryMeasure p")
public class PrecautionaryMeasure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="done_by")
	private String doneBy;

	@Column(name="facility_name")
	private String facilityName;

	@Column(name="from_date_time")
	private Timestamp fromDateTime;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String location;

	@Column(name="precautionary_measure")
	private String precautionaryMeasure;

	@Column(columnDefinition="TEXT")
	private String remarks;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="thru_date_time")
	private Timestamp thruDateTime;

	public PrecautionaryMeasure() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getDoneBy() {
		return this.doneBy;
	}

	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}

	public String getFacilityName() {
		return this.facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public Timestamp getFromDateTime() {
		return this.fromDateTime;
	}

	public void setFromDateTime(Timestamp fromDateTime) {
		this.fromDateTime = fromDateTime;
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

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPrecautionaryMeasure() {
		return this.precautionaryMeasure;
	}

	public void setPrecautionaryMeasure(String precautionaryMeasure) {
		this.precautionaryMeasure = precautionaryMeasure;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public Timestamp getThruDateTime() {
		return this.thruDateTime;
	}

	public void setThruDateTime(Timestamp thruDateTime) {
		this.thruDateTime = thruDateTime;
	}

}
