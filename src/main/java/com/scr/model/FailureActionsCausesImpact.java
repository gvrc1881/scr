package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "failure_actions_causes_impact" )


public class FailureActionsCausesImpact implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="failure_seq_id")
	private String failureSeqId ;
	
	@Column(name="failure_activity")
	private String failureActivity ;
	
	@Column(name="from_time")
	private Timestamp fromTime ; 
	
	@Column(name="thru_time")
	private Timestamp thruTime ;
	
	@Column(name="location")
	private String location ;
	
	@Column(name="by"  )
	private String by ;
	
	@Column(name="remarks" ,columnDefinition="TEXT")
	private String remarks;
	
	@Column(name="special_remarks" ,columnDefinition="TEXT")
	private String specialRemarks;
	
	@Column(name="root_cause",columnDefinition="TEXT")
	private String rootCause ;
	
	@Column(name="seq_id")
	private String seqId  ;
	
	@Column(name="train_no")
	private String trainNo ;
	
	@Column(name="data_div")
	private String dataDiv;
	
	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;
	
	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;
	
	public FailureActionsCausesImpact() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFailureSeqId() {
		return failureSeqId;
	}

	public void setFailureSeqId(String failureSeqId) {
		this.failureSeqId = failureSeqId;
	}

	public String getFailureActivity() {
		return failureActivity;
	}

	public void setFailureActivity(String failureActivity) {
		this.failureActivity = failureActivity;
	}

	public Timestamp getFromTime() {
		return fromTime;
	}

	public void setFromTime(Timestamp fromTime) {
		this.fromTime = fromTime;
	}

	public Timestamp getThruTime() {
		return thruTime;
	}

	public void setThruTime(Timestamp thruTime) {
		this.thruTime = thruTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSpecialRemarks() {
		return specialRemarks;
	}

	public void setSpecialRemarks(String specialRemarks) {
		this.specialRemarks = specialRemarks;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getDataDiv() {
		return dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public Timestamp getCreatedStamp() {
		return createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public Timestamp getLastUpdatedStamp() {
		return lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}


	
	
	

}
