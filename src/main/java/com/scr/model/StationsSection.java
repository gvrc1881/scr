package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the stations_sections database table.
 * 
 */
@Entity
@Table(name = "stations_sections" , uniqueConstraints={@UniqueConstraint(name = "old_pk_stations_sections_uniq", columnNames ={"data_div", "seq_id"})})
@NamedQuery(name="StationsSection.findAll", query="SELECT s FROM StationsSection s")
public class StationsSection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String division;

	@Column(name="dn_section")
	private String dnSection;

	@Column(name="dn_section_name")
	private String dnSectionName;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="major_section_route")
	private String majorSectionRoute;

	private String remark;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="station_code")
	private String stationCode;

	@Column(name="station_name")
	private String stationName;

	@Column(name="up_section")
	private String upSection;

	@Column(name="up_section_name")
	private String upSectionName;

	public StationsSection() {
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

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDnSection() {
		return this.dnSection;
	}

	public void setDnSection(String dnSection) {
		this.dnSection = dnSection;
	}

	public String getDnSectionName() {
		return this.dnSectionName;
	}

	public void setDnSectionName(String dnSectionName) {
		this.dnSectionName = dnSectionName;
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

	public String getMajorSectionRoute() {
		return this.majorSectionRoute;
	}

	public void setMajorSectionRoute(String majorSectionRoute) {
		this.majorSectionRoute = majorSectionRoute;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getUpSection() {
		return this.upSection;
	}

	public void setUpSection(String upSection) {
		this.upSection = upSection;
	}

	public String getUpSectionName() {
		return this.upSectionName;
	}

	public void setUpSectionName(String upSectionName) {
		this.upSectionName = upSectionName;
	}

}