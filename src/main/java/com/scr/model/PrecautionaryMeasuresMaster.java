package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the precautionary_measures_master database table.
 * 
 */
@Entity
@Table(name = "precautionary_measures_master" , uniqueConstraints={@UniqueConstraint(name = "old_pk_precautionary_measures_master_uniq", columnNames ={"precautionary_measure", "data_div"})})
@NamedQuery(name="PrecautionaryMeasuresMaster.findAll", query="SELECT p FROM PrecautionaryMeasuresMaster p")
public class PrecautionaryMeasuresMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="done_by")
	private String doneBy;

	@Column(name="from_date_time")
	private Timestamp fromDateTime;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String location;

	@Column(name="precautionary_measure")
	private String precautionaryMeasure;

	@Column(name="thru_date_time")
	private Timestamp thruDateTime;

	public PrecautionaryMeasuresMaster() {
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

	public Timestamp getThruDateTime() {
		return this.thruDateTime;
	}

	public void setThruDateTime(Timestamp thruDateTime) {
		this.thruDateTime = thruDateTime;
	}

}
