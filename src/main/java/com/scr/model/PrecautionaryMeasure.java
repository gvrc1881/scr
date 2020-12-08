package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the precautionary_measure database table.
 * 
 */
@Entity
@Table(name = "precautionary_measure")
@NamedQuery(name="PrecautionaryMeasure.findAll", query="SELECT p FROM PrecautionaryMeasure p")
public class PrecautionaryMeasure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;
	
	@Column(name="count")
	private Integer count;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="done_by")
	private String doneBy;
	
	@Column(name="facility_id")
	private String facilityId;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfWork;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String location;

	@Column(columnDefinition="TEXT")
	private String remarks;

	@Column(name="seq_id")
	private String seqId;

	
	
	@ManyToOne
	@JoinColumn(name = "precautionary_measure", foreignKey = @ForeignKey(name = "fk_precautionary_measure_precautionary_measures_master"))
	private PrecautionaryMeasuresMaster precautionaryMeasure;

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

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	

	public Date getDateOfWork() {
		return dateOfWork;
	}

	public void setDateOfWork(Date dateOfWork) {
		this.dateOfWork = dateOfWork;
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
	public PrecautionaryMeasuresMaster getPrecautionaryMeasure() {
		return precautionaryMeasure;
	}

	public void setPrecautionaryMeasure(PrecautionaryMeasuresMaster precautionaryMeasure) {
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

	

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "PrecautionaryMeasure [id=" + id + ", createdDate=" + createdDate + ", createdStamp=" + createdStamp
				+ ", createdTxStamp=" + createdTxStamp + ", count=" + count + ", dataDiv=" + dataDiv + ", doneBy="
				+ doneBy + ", facilityId=" + facilityId + ", dateOfWork=" + dateOfWork + ", lastUpdatedStamp="
				+ lastUpdatedStamp + ", lastUpdatedTxStamp=" + lastUpdatedTxStamp + ", location=" + location
				+ ", remarks=" + remarks + ", seqId=" + seqId + ", precautionaryMeasure=" + precautionaryMeasure + "]";
	}

}
