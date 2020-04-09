package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ohe_location database table.
 * 
 */
@Entity
@Table(name = "ohe_location" , uniqueConstraints={@UniqueConstraint(name = "old_pk_ohe_location_uniq", columnNames ={"seq_id", "data_div"})})
@NamedQuery(name="OheLocation.findAll", query="SELECT o FROM OheLocation o")
public class OheLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="adee_section")
	private String adeeSection;

	private String altitude;

	private String chainage;

	@Column(name="chainage_remark")
	private String chainageRemark;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	private String curvature;

	@Column(name="curvature_remark")
	private String curvatureRemark;

	@Column(name="data_div")
	private String dataDiv;

	private Timestamp date;

	private String division;

	@Column(name="eng_feature")
	private String engFeature;

	@Column(name="facility_id")
	private String facilityId;

	private String heading;

	private double kilometer;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String latitude;

	private String longitude;

	@Column(name="major_section")
	private String majorSection;

	@Column(name="ohe_feature")
	private String oheFeature;

	@Column(name="ohe_mast")
	private String oheMast;

	@Column(name="ohe_sequence")
	private String oheSequence;

	private String pwi;

	@Column(name="remark_one")
	private String remarkOne;

	@Column(name="remark_two")
	private String remarkTwo;

	private String satellites;

	private String section;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="sequence_no")
	private String sequenceNo;

	private String span;

	@Column(name="span_remark")
	private String spanRemark;

	private String speed;

	@Column(name="structure_type")
	private String structureType;

	@Column(name="track_line")
	private String trackLine;

	private String validity;

	public OheLocation() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdeeSection() {
		return this.adeeSection;
	}

	public void setAdeeSection(String adeeSection) {
		this.adeeSection = adeeSection;
	}

	public String getAltitude() {
		return this.altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getChainage() {
		return this.chainage;
	}

	public void setChainage(String chainage) {
		this.chainage = chainage;
	}

	public String getChainageRemark() {
		return this.chainageRemark;
	}

	public void setChainageRemark(String chainageRemark) {
		this.chainageRemark = chainageRemark;
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

	public String getCurvature() {
		return this.curvature;
	}

	public void setCurvature(String curvature) {
		this.curvature = curvature;
	}

	public String getCurvatureRemark() {
		return this.curvatureRemark;
	}

	public void setCurvatureRemark(String curvatureRemark) {
		this.curvatureRemark = curvatureRemark;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEngFeature() {
		return this.engFeature;
	}

	public void setEngFeature(String engFeature) {
		this.engFeature = engFeature;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getHeading() {
		return this.heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public double getKilometer() {
		return this.kilometer;
	}

	public void setKilometer(double kilometer) {
		this.kilometer = kilometer;
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

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMajorSection() {
		return this.majorSection;
	}

	public void setMajorSection(String majorSection) {
		this.majorSection = majorSection;
	}

	public String getOheFeature() {
		return this.oheFeature;
	}

	public void setOheFeature(String oheFeature) {
		this.oheFeature = oheFeature;
	}

	public String getOheMast() {
		return this.oheMast;
	}

	public void setOheMast(String oheMast) {
		this.oheMast = oheMast;
	}

	public String getOheSequence() {
		return this.oheSequence;
	}

	public void setOheSequence(String oheSequence) {
		this.oheSequence = oheSequence;
	}

	public String getPwi() {
		return this.pwi;
	}

	public void setPwi(String pwi) {
		this.pwi = pwi;
	}

	public String getRemarkOne() {
		return this.remarkOne;
	}

	public void setRemarkOne(String remarkOne) {
		this.remarkOne = remarkOne;
	}

	public String getRemarkTwo() {
		return this.remarkTwo;
	}

	public void setRemarkTwo(String remarkTwo) {
		this.remarkTwo = remarkTwo;
	}

	public String getSatellites() {
		return this.satellites;
	}

	public void setSatellites(String satellites) {
		this.satellites = satellites;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getSpan() {
		return this.span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public String getSpanRemark() {
		return this.spanRemark;
	}

	public void setSpanRemark(String spanRemark) {
		this.spanRemark = spanRemark;
	}

	public String getSpeed() {
		return this.speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getStructureType() {
		return this.structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}

	public String getTrackLine() {
		return this.trackLine;
	}

	public void setTrackLine(String trackLine) {
		this.trackLine = trackLine;
	}

	public String getValidity() {
		return this.validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

}
