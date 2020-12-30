package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * The persistent class for the ohe_location database table.
 * 
 */
@Entity
@Table(name = "ohe_location")
@NamedQuery(name="OheLocation.findAll", query="SELECT o FROM OheLocation o")
public class OheLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(name="foundation")
	private String foundation;

	private String heading;

	private Double kilometer;

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
	
	@Column(name="project")
	private String project;

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
	
	@Column(name="location")
	private String location;

	private String validity;

	public OheLocation() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdeeSection() {
		return adeeSection;
	}

	public void setAdeeSection(String adeeSection) {
		this.adeeSection = adeeSection;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getChainage() {
		return chainage;
	}

	public void setChainage(String chainage) {
		this.chainage = chainage;
	}

	public String getChainageRemark() {
		return chainageRemark;
	}

	public void setChainageRemark(String chainageRemark) {
		this.chainageRemark = chainageRemark;
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

	public String getCurvature() {
		return curvature;
	}

	public void setCurvature(String curvature) {
		this.curvature = curvature;
	}

	public String getCurvatureRemark() {
		return curvatureRemark;
	}

	public void setCurvatureRemark(String curvatureRemark) {
		this.curvatureRemark = curvatureRemark;
	}

	public String getDataDiv() {
		return dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEngFeature() {
		return engFeature;
	}

	public void setEngFeature(String engFeature) {
		this.engFeature = engFeature;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFoundation() {
		return foundation;
	}

	public void setFoundation(String foundation) {
		this.foundation = foundation;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public Double getKilometer() {
		return kilometer;
	}

	public void setKilometer(Double kilometer) {
		this.kilometer = kilometer;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMajorSection() {
		return majorSection;
	}

	public void setMajorSection(String majorSection) {
		this.majorSection = majorSection;
	}

	public String getOheFeature() {
		return oheFeature;
	}

	public void setOheFeature(String oheFeature) {
		this.oheFeature = oheFeature;
	}

	public String getOheMast() {
		return oheMast;
	}

	public void setOheMast(String oheMast) {
		this.oheMast = oheMast;
	}

	public String getOheSequence() {
		return oheSequence;
	}

	public void setOheSequence(String oheSequence) {
		this.oheSequence = oheSequence;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPwi() {
		return pwi;
	}

	public void setPwi(String pwi) {
		this.pwi = pwi;
	}

	public String getRemarkOne() {
		return remarkOne;
	}

	public void setRemarkOne(String remarkOne) {
		this.remarkOne = remarkOne;
	}

	public String getRemarkTwo() {
		return remarkTwo;
	}

	public void setRemarkTwo(String remarkTwo) {
		this.remarkTwo = remarkTwo;
	}

	public String getSatellites() {
		return satellites;
	}

	public void setSatellites(String satellites) {
		this.satellites = satellites;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getSpan() {
		return span;
	}

	public void setSpan(String span) {
		this.span = span;
	}

	public String getSpanRemark() {
		return spanRemark;
	}

	public void setSpanRemark(String spanRemark) {
		this.spanRemark = spanRemark;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getStructureType() {
		return structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}

	public String getTrackLine() {
		return trackLine;
	}

	public void setTrackLine(String trackLine) {
		this.trackLine = trackLine;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	@Override
	public String toString() {
		return "OheLocation [id=" + id + ", adeeSection=" + adeeSection + ", altitude=" + altitude + ", chainage="
				+ chainage + ", chainageRemark=" + chainageRemark + ", createdStamp=" + createdStamp
				+ ", createdTxStamp=" + createdTxStamp + ", curvature=" + curvature + ", curvatureRemark="
				+ curvatureRemark + ", dataDiv=" + dataDiv + ", date=" + date + ", division=" + division
				+ ", engFeature=" + engFeature + ", facilityId=" + facilityId + ", foundation=" + foundation
				+ ", heading=" + heading + ", kilometer=" + kilometer + ", lastUpdatedStamp=" + lastUpdatedStamp
				+ ", lastUpdatedTxStamp=" + lastUpdatedTxStamp + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", majorSection=" + majorSection + ", oheFeature=" + oheFeature + ", oheMast=" + oheMast
				+ ", oheSequence=" + oheSequence + ", project=" + project + ", pwi=" + pwi + ", remarkOne=" + remarkOne
				+ ", remarkTwo=" + remarkTwo + ", satellites=" + satellites + ", section=" + section + ", seqId="
				+ seqId + ", sequenceNo=" + sequenceNo + ", span=" + span + ", spanRemark=" + spanRemark + ", speed="
				+ speed + ", structureType=" + structureType + ", trackLine=" + trackLine + ", location=" + location
				+ ", validity=" + validity + "]";
	}

}
