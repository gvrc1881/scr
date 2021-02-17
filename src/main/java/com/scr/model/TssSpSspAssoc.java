package com.scr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tss_sp_ssp_assoc",uniqueConstraints={@UniqueConstraint(name = "old_pk_tss_sp_ssp_assoc_uniq", columnNames ={"tss_facility_id", "ssp_sp_facilityId"})})

public class TssSpSspAssoc implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "connected_sp_ssp")
	private String connectedSpSsp;
	
	@Column(name = "tss_name")
	private String tssName;
	
	
	@Column(name = "ssp_sp_facilityId")
	private String sspSpFacilityId;
	
	@ManyToOne
	@JoinColumn(name = "tss_facility_id", foreignKey = @ForeignKey(name = "fk_tss_sp_ssp_assoc_facility"))
	private Facility tssFacilityId;

	public TssSpSspAssoc() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConnectedSpSsp() {
		return connectedSpSsp;
	}

	public void setConnectedSpSsp(String connectedSpSsp) {
		this.connectedSpSsp = connectedSpSsp;
	}

	public String getTssName() {
		return tssName;
	}

	public void setTssName(String tssName) {
		this.tssName = tssName;
	}
	

	public String getSspSpFacilityId() {
		return sspSpFacilityId;
	}

	public void setSspSpFacilityId(String sspSpFacilityId) {
		this.sspSpFacilityId = sspSpFacilityId;
	}

	public Facility getTssFacilityId() {
		return tssFacilityId;
	}

	public void setTssFacilityId(Facility tssFacilityId) {
		this.tssFacilityId = tssFacilityId;
	}

	@Override
	public String toString() {
		return "TssSpSspAssoc [id=" + id + ", connectedSpSsp=" + connectedSpSsp + ", tssName=" + tssName
				+ ", sspSpFacilityId=" + sspSpFacilityId + ", tssFacilityId=" + tssFacilityId + "]";
	}



	
	
}
