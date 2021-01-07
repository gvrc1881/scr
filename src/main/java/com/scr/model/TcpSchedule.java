package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tcp_schedule")
@NamedQuery(name = "TcpSchedule.findAll", query = "SELECT tpcs FROM TcpSchedule tpcs")
public class TcpSchedule implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String location;

	private String assetType;

	private Timestamp dateTime;

	private String by;

	private String generalRemark;

	@ManyToOne
	@JoinColumn(name = "facilityId")
	private Facility facility;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getGeneralRemark() {
		return generalRemark;
	}

	public void setGeneralRemark(String generalRemark) {
		this.generalRemark = generalRemark;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "TcpSchedule [id=" + id + ", location=" + location + ", assetType=" + assetType + ", dateTime="
				+ dateTime + ", by=" + by + ", generalRemark=" + generalRemark + ", facility=" + facility + "]";
	}
	
	

}
