package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the foot_patrolling_sections database table.
 * 
 */
@Entity
// @Table(name="foot_patrolling_sections")

@Table(name = "foot_patrolling_sections" , uniqueConstraints={@UniqueConstraint(name = "old_pk_foot_patrolling_sections_uniq", columnNames ={"data_div", "seq_id"})})
//
@NamedQuery(name="FootPatrollingSection.findAll", query="SELECT f FROM FootPatrollingSection f")
public class FootPatrollingSection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="facility_depot")
	private String facilityDepot;

	@Column(name="fp_section")
	private String fpSection;

	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;

	@Column(name="from_location")
	private String fromLocation;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String remarks;

	@Column(name="seq_id")
	private String seqId;

	@Temporal(TemporalType.DATE)
	@Column(name="to_date")
	private Date toDate;

	@Column(name="to_location")
	private String toLocation;

	public FootPatrollingSection() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDataDiv() {
		return dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getFacilityDepot() {
		return facilityDepot;
	}

	public void setFacilityDepot(String facilityDepot) {
		this.facilityDepot = facilityDepot;
	}

	public String getFpSection() {
		return fpSection;
	}

	public void setFpSection(String fpSection) {
		this.fpSection = fpSection;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	@Override
	public String toString() {
		return "FootPatrollingSection [id=" + id + ", createdStamp=" + createdStamp + ", createdTxStamp="
				+ createdTxStamp + ", dataDiv=" + dataDiv + ", facilityDepot=" + facilityDepot + ", fpSection="
				+ fpSection + ", fromDate=" + fromDate + ", fromLocation=" + fromLocation + ", lastUpdatedStamp="
				+ lastUpdatedStamp + ", lastUpdatedTxStamp=" + lastUpdatedTxStamp + ", remarks=" + remarks + ", seqId="
				+ seqId + ", toDate=" + toDate + ", toLocation=" + toLocation + "]";
	}

	
	

}