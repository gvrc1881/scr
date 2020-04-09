package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the asset_master_history database table.
 * 
 */
@Entity
@Table(name = "asset_master_history" , uniqueConstraints={@UniqueConstraint(name = "old_pk_asset_master_history_uniq", columnNames ={"data_div", "seq_id"})})
@NamedQuery(name="AssetMasterHistory.findAll", query="SELECT a FROM AssetMasterHistory a")
public class AssetMasterHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="aoh_date")
	private Timestamp aohDate;

	@Column(name="asset_id")
	private String assetId;

	@Column(name="asset_type")
	private String assetType;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	private String curvature;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="date_of_commision")
	private Timestamp dateOfCommision;

	@Column(name="date_of_manufacture")
	private Timestamp dateOfManufacture;

	@Column(name="date_of_received")
	private Timestamp dateOfReceived;

	@Column(name="elementary_section")
	private String elementarySection;

	@Column(name="equipped_date")
	private Timestamp equippedDate;

	@Column(name="expiry_date")
	private Timestamp expiryDate;

	@Column(name="facility_id")
	private String facilityId;

	private String implantation;

	private double kilometer;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String latitude;

	@Column(name="left_span")
	private String leftSpan;

	private String longitude;

	private String make;

	private String model;

	@Column(name="next_schedule_date")
	private Timestamp nextScheduleDate;

	@Column(name="next_schedule_due")
	private String nextScheduleDue;

	@Column(name="oem_serial")
	private String oemSerial;

	@Column(name="parent_asset_type")
	private String parentAssetType;

	@Column(name="parent_asset_type_id")
	private String parentAssetTypeId;

	private String part1;

	private String part2;

	private String part3;

	@Column(name="poh_date")
	private Timestamp pohDate;

	@Column(name="position_id")
	private String positionId;

	@Column(name="recent_schedule_date")
	private Timestamp recentScheduleDate;

	@Column(name="recent_schedule_done")
	private String recentScheduleDone;

	@Column(name="right_span")
	private String rightSpan;

	@Column(name="rly_assigned_serial")
	private String rlyAssignedSerial;

	private String section;

	@Column(name="seq_id")
	private String seqId;

	private String source;

	@Column(name="strip_date")
	private Timestamp stripDate;

	private String structure;

	private String vendor;

	@Column(name="warranty_amc")
	private String warrantyAmc;

	@Column(name="warranty_amc_end_date")
	private Timestamp warrantyAmcEndDate;

	public AssetMasterHistory() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getAohDate() {
		return this.aohDate;
	}

	public void setAohDate(Timestamp aohDate) {
		this.aohDate = aohDate;
	}

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetType() {
		return this.assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public Timestamp getDateOfCommision() {
		return this.dateOfCommision;
	}

	public void setDateOfCommision(Timestamp dateOfCommision) {
		this.dateOfCommision = dateOfCommision;
	}

	public Timestamp getDateOfManufacture() {
		return this.dateOfManufacture;
	}

	public void setDateOfManufacture(Timestamp dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public Timestamp getDateOfReceived() {
		return this.dateOfReceived;
	}

	public void setDateOfReceived(Timestamp dateOfReceived) {
		this.dateOfReceived = dateOfReceived;
	}

	public String getElementarySection() {
		return this.elementarySection;
	}

	public void setElementarySection(String elementarySection) {
		this.elementarySection = elementarySection;
	}

	public Timestamp getEquippedDate() {
		return this.equippedDate;
	}

	public void setEquippedDate(Timestamp equippedDate) {
		this.equippedDate = equippedDate;
	}

	public Timestamp getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getImplantation() {
		return this.implantation;
	}

	public void setImplantation(String implantation) {
		this.implantation = implantation;
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

	public String getLeftSpan() {
		return this.leftSpan;
	}

	public void setLeftSpan(String leftSpan) {
		this.leftSpan = leftSpan;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMake() {
		return this.make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Timestamp getNextScheduleDate() {
		return this.nextScheduleDate;
	}

	public void setNextScheduleDate(Timestamp nextScheduleDate) {
		this.nextScheduleDate = nextScheduleDate;
	}

	public String getNextScheduleDue() {
		return this.nextScheduleDue;
	}

	public void setNextScheduleDue(String nextScheduleDue) {
		this.nextScheduleDue = nextScheduleDue;
	}

	public String getOemSerial() {
		return this.oemSerial;
	}

	public void setOemSerial(String oemSerial) {
		this.oemSerial = oemSerial;
	}

	public String getParentAssetType() {
		return this.parentAssetType;
	}

	public void setParentAssetType(String parentAssetType) {
		this.parentAssetType = parentAssetType;
	}

	public String getParentAssetTypeId() {
		return this.parentAssetTypeId;
	}

	public void setParentAssetTypeId(String parentAssetTypeId) {
		this.parentAssetTypeId = parentAssetTypeId;
	}

	public String getPart1() {
		return this.part1;
	}

	public void setPart1(String part1) {
		this.part1 = part1;
	}

	public String getPart2() {
		return this.part2;
	}

	public void setPart2(String part2) {
		this.part2 = part2;
	}

	public String getPart3() {
		return this.part3;
	}

	public void setPart3(String part3) {
		this.part3 = part3;
	}

	public Timestamp getPohDate() {
		return this.pohDate;
	}

	public void setPohDate(Timestamp pohDate) {
		this.pohDate = pohDate;
	}

	public String getPositionId() {
		return this.positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public Timestamp getRecentScheduleDate() {
		return this.recentScheduleDate;
	}

	public void setRecentScheduleDate(Timestamp recentScheduleDate) {
		this.recentScheduleDate = recentScheduleDate;
	}

	public String getRecentScheduleDone() {
		return this.recentScheduleDone;
	}

	public void setRecentScheduleDone(String recentScheduleDone) {
		this.recentScheduleDone = recentScheduleDone;
	}

	public String getRightSpan() {
		return this.rightSpan;
	}

	public void setRightSpan(String rightSpan) {
		this.rightSpan = rightSpan;
	}

	public String getRlyAssignedSerial() {
		return this.rlyAssignedSerial;
	}

	public void setRlyAssignedSerial(String rlyAssignedSerial) {
		this.rlyAssignedSerial = rlyAssignedSerial;
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

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Timestamp getStripDate() {
		return this.stripDate;
	}

	public void setStripDate(Timestamp stripDate) {
		this.stripDate = stripDate;
	}

	public String getStructure() {
		return this.structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getWarrantyAmc() {
		return this.warrantyAmc;
	}

	public void setWarrantyAmc(String warrantyAmc) {
		this.warrantyAmc = warrantyAmc;
	}

	public Timestamp getWarrantyAmcEndDate() {
		return this.warrantyAmcEndDate;
	}

	public void setWarrantyAmcEndDate(Timestamp warrantyAmcEndDate) {
		this.warrantyAmcEndDate = warrantyAmcEndDate;
	}

}
