package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the asset_master_data database table.
 * 
 */
@Entity
@Table(name = "asset_master_data" , uniqueConstraints={@UniqueConstraint(name = "old_pk_asset_master_data_uniq", columnNames ={"data_div", "seq_id"})})
@NamedQuery(name="AssetMasterData.findAll", query="SELECT a FROM AssetMasterData a")
public class AssetMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="adee_section")
	private String adeeSection;

	@Column(name="asset_id")
	private String assetId;

	@Column(name="asset_type")
	private String assetType;

	private String batch;

	@Column(name="capacity_rating")
	private String capacityRating;

	@Column(name="codal_life")
	private String codalLife;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

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

	@Column(name="end1_side1")
	private String end1Side1;

	@Column(name="end2_side2")
	private String end2Side2;

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

	@Column(name="location_position")
	private String locationPosition;

	@Column(name="lug_date")
	private Timestamp lugDate;

	@Column(name="major_section")
	private String majorSection;

	private String make;

	private String model;

	@Column(name="name_plate_details")
	private String namePlateDetails;

	@Column(name="oem_serial")
	private String oemSerial;

	@Column(name="parent_asset_type")
	private String parentAssetType;

	@Column(name="parent_asset_type_id")
	private String parentAssetTypeId;

	private String part1;

	private String part2;

	private String part3;

	@Column(name="position_id")
	private String positionId;

	private String remark1;

	private String remark2;

	@Column(name="rly_assigned_serial")
	private String rlyAssignedSerial;

	private String section;

	@Column(name="seq_id")
	private String seqId;

	private String source;

	private String station;

	@Column(name="strip_date")
	private Timestamp stripDate;

	private String structure;

	private String type;

	private String vendor;

	private String voltage;

	@Column(name="warranty_amc")
	private String warrantyAmc;

	@Column(name="warranty_amc_end_date")
	private Timestamp warrantyAmcEndDate;
	
	@Column(name="line")
	private String line;
	
	@Column(name="stagger")
	private String stagger;
	
	@Column(name="stagger1")
	private String stagger1;
	
	@Column(name="stagger2")
	private String stagger2;
	
	@Column(name="stagger3")
	private String stagger3;
	
	@Column(name="stay1_insulator_make")
	private String stay1InsulatorMake;
	
	@Column(name="stay1_insulator_batch")
	private String stay1InsulatorBatch;
	
	@Column(name="bracket1_insulator_make")
	private String bracket1InsulatorMake;
	
	@Column(name="bracket1_insulator_batch")
	private String bracket1InsulatorBatch;
	
	@Column(name="stag1_ton9_insulator_make")
	private String stag1Ton9InsulatorMake;
	
	@Column(name="stag1_ton9_insulator_batch")
	private String stag1Ton9InsulatorBatch;
	
	@Column(name="rod1_insulator_make")
	private String rod1InsulatorMake;
	
	@Column(name="rod1_insulator_batch")
	private String rod1InsulatorBatch;
	
	@Column(name="pedestal1_insulator_make")
	private String pedestal1InsulatorMake;
	
	@Column(name="pedestal1_insulator_batch")
	private String pedestal1InsulatorBatch;
	
	@Column(name="core1_insulator_make")
	private String core1InsulatorMake;
	
	@Column(name="core1_insulator_batch")
	private String core1InsulatorBatch;
	
	@Column(name="stay2_insulator_make")
	private String stay2InsulatorMake;
	
	@Column(name="stay2_insulator_batch")
	private String stay2InsulatorBatch;
	
	@Column(name="bracket2_insulator_make")
	private String bracket2InsulatorMake;
	
	@Column(name="bracket2_insulator_batch")
	private String bracket2InsulatorBatch;
	
	@Column(name="stag2_ton9_insulator_make")
	private String stag2Ton9InsulatorMake;
	
	@Column(name="stag2_ton9_insulator_batch")
	private String stag2Ton9InsulatorBatch;
	
	
	@Column(name="rod2_insulator_make")
	private String rod2InsulatorMake;
	
	@Column(name="rod2_insulator_batch")
	private String rod2InsulatorBatch;
	
	@Column(name="pedestal2_insulator_make")
	private String pedestal2InsulatorMake;
	
	@Column(name="pedestal2_insulator_batch")
	private String pedestal2InsulatorBatch;
	
	@Column(name="core2_insulator_make")
	private String core2InsulatorMake;
	
	@Column(name="stay3_insulator_make")
	private String stay3InsulatorMake;
	
	@Column(name="stay3InsulatorBatch")
	private String stay3InsulatorBatch;
	
	@Column(name="bracket3_insulator_make")
	private String bracket3InsulatorMake;
	
	@Column(name="bracket3_insulator_batch")
	private String bracket3InsulatorBatch;
	
	@Column(name="stag3_ton9_insulator_make")
	private String stag3Ton9InsulatorMake;
	
	@Column(name="stag3_ton9_insulator_batch")
	private String stag3Ton9InsulatorBatch;
	
	@Column(name="rod3_insulator_make")
	private String rod3InsulatorMake;
	
	@Column(name="rod3_insulator_batch")
	private String rod3InsulatorBatch;
	
	@Column(name="pedestal3_insulator_make")
	private String pedestal3InsulatorMake;
	
	@Column(name="pedestal3_insulator_batch")
	private String pedestal3InsulatorBatch;
	
	@Column(name="core3_insulator_make")
	private String core3InsulatorMake;
	
	@Column(name="core3_insulator_batch")
	private String core3InsulatorBatch;
	
	
	
	public AssetMasterData() {
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

	public String getBatch() {
		return this.batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getCapacityRating() {
		return this.capacityRating;
	}

	public void setCapacityRating(String capacityRating) {
		this.capacityRating = capacityRating;
	}

	public String getCodalLife() {
		return this.codalLife;
	}

	public void setCodalLife(String codalLife) {
		this.codalLife = codalLife;
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

	public String getEnd1Side1() {
		return this.end1Side1;
	}

	public void setEnd1Side1(String end1Side1) {
		this.end1Side1 = end1Side1;
	}

	public String getEnd2Side2() {
		return this.end2Side2;
	}

	public void setEnd2Side2(String end2Side2) {
		this.end2Side2 = end2Side2;
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

	public String getLocationPosition() {
		return this.locationPosition;
	}

	public void setLocationPosition(String locationPosition) {
		this.locationPosition = locationPosition;
	}

	public Timestamp getLugDate() {
		return this.lugDate;
	}

	public void setLugDate(Timestamp lugDate) {
		this.lugDate = lugDate;
	}

	public String getMajorSection() {
		return this.majorSection;
	}

	public void setMajorSection(String majorSection) {
		this.majorSection = majorSection;
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

	public String getNamePlateDetails() {
		return this.namePlateDetails;
	}

	public void setNamePlateDetails(String namePlateDetails) {
		this.namePlateDetails = namePlateDetails;
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

	public String getPositionId() {
		return this.positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
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

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVoltage() {
		return this.voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
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

	public String getStagger() {
		return stagger;
	}

	public void setStagger(String stagger) {
		this.stagger = stagger;
	}

	public String getStagger1() {
		return stagger1;
	}

	public void setStagger1(String stagger1) {
		this.stagger1 = stagger1;
	}

	public String getStagger2() {
		return stagger2;
	}

	public void setStagger2(String stagger2) {
		this.stagger2 = stagger2;
	}

	public String getStagger3() {
		return stagger3;
	}

	public void setStagger3(String stagger3) {
		this.stagger3 = stagger3;
	}

	public String getStay1InsulatorMake() {
		return stay1InsulatorMake;
	}

	public void setStay1InsulatorMake(String stay1InsulatorMake) {
		this.stay1InsulatorMake = stay1InsulatorMake;
	}

	public String getStay1InsulatorBatch() {
		return stay1InsulatorBatch;
	}

	public void setStay1InsulatorBatch(String stay1InsulatorBatch) {
		this.stay1InsulatorBatch = stay1InsulatorBatch;
	}

	public String getBracket1InsulatorMake() {
		return bracket1InsulatorMake;
	}

	public void setBracket1InsulatorMake(String bracket1InsulatorMake) {
		this.bracket1InsulatorMake = bracket1InsulatorMake;
	}

	public String getBracket1InsulatorBatch() {
		return bracket1InsulatorBatch;
	}

	public void setBracket1InsulatorBatch(String bracket1InsulatorBatch) {
		this.bracket1InsulatorBatch = bracket1InsulatorBatch;
	}

	public String getStag1Ton9InsulatorMake() {
		return stag1Ton9InsulatorMake;
	}

	public void setStag1Ton9InsulatorMake(String stag1Ton9InsulatorMake) {
		this.stag1Ton9InsulatorMake = stag1Ton9InsulatorMake;
	}

	public String getStag1Ton9InsulatorBatch() {
		return stag1Ton9InsulatorBatch;
	}

	public void setStag1Ton9InsulatorBatch(String stag1Ton9InsulatorBatch) {
		this.stag1Ton9InsulatorBatch = stag1Ton9InsulatorBatch;
	}

	public String getRod1InsulatorMake() {
		return rod1InsulatorMake;
	}

	public void setRod1InsulatorMake(String rod1InsulatorMake) {
		this.rod1InsulatorMake = rod1InsulatorMake;
	}

	public String getRod1InsulatorBatch() {
		return rod1InsulatorBatch;
	}

	public void setRod1InsulatorBatch(String rod1InsulatorBatch) {
		this.rod1InsulatorBatch = rod1InsulatorBatch;
	}

	public String getPedestal1InsulatorMake() {
		return pedestal1InsulatorMake;
	}

	public void setPedestal1InsulatorMake(String pedestal1InsulatorMake) {
		this.pedestal1InsulatorMake = pedestal1InsulatorMake;
	}

	public String getPedestal1InsulatorBatch() {
		return pedestal1InsulatorBatch;
	}

	public void setPedestal1InsulatorBatch(String pedestal1InsulatorBatch) {
		this.pedestal1InsulatorBatch = pedestal1InsulatorBatch;
	}

	public String getCore1InsulatorMake() {
		return core1InsulatorMake;
	}

	public void setCore1InsulatorMake(String core1InsulatorMake) {
		this.core1InsulatorMake = core1InsulatorMake;
	}

	public String getCore1InsulatorBatch() {
		return core1InsulatorBatch;
	}

	public void setCore1InsulatorBatch(String core1InsulatorBatch) {
		this.core1InsulatorBatch = core1InsulatorBatch;
	}

	public String getStay2InsulatorMake() {
		return stay2InsulatorMake;
	}

	public void setStay2InsulatorMake(String stay2InsulatorMake) {
		this.stay2InsulatorMake = stay2InsulatorMake;
	}

	public String getStay2InsulatorBatch() {
		return stay2InsulatorBatch;
	}

	public void setStay2InsulatorBatch(String stay2InsulatorBatch) {
		this.stay2InsulatorBatch = stay2InsulatorBatch;
	}

	public String getBracket2InsulatorMake() {
		return bracket2InsulatorMake;
	}

	public void setBracket2InsulatorMake(String bracket2InsulatorMake) {
		this.bracket2InsulatorMake = bracket2InsulatorMake;
	}

	public String getBracket2InsulatorBatch() {
		return bracket2InsulatorBatch;
	}

	public void setBracket2InsulatorBatch(String bracket2InsulatorBatch) {
		this.bracket2InsulatorBatch = bracket2InsulatorBatch;
	}

	public String getStag2Ton9InsulatorMake() {
		return stag2Ton9InsulatorMake;
	}

	public void setStag2Ton9InsulatorMake(String stag2Ton9InsulatorMake) {
		this.stag2Ton9InsulatorMake = stag2Ton9InsulatorMake;
	}

	public String getStag2Ton9InsulatorBatch() {
		return stag2Ton9InsulatorBatch;
	}

	public void setStag2Ton9InsulatorBatch(String stag2Ton9InsulatorBatch) {
		this.stag2Ton9InsulatorBatch = stag2Ton9InsulatorBatch;
	}

	public String getRod2InsulatorMake() {
		return rod2InsulatorMake;
	}

	public void setRod2InsulatorMake(String rod2InsulatorMake) {
		this.rod2InsulatorMake = rod2InsulatorMake;
	}

	public String getRod2InsulatorBatch() {
		return rod2InsulatorBatch;
	}

	public void setRod2InsulatorBatch(String rod2InsulatorBatch) {
		this.rod2InsulatorBatch = rod2InsulatorBatch;
	}

	public String getPedestal2InsulatorMake() {
		return pedestal2InsulatorMake;
	}

	public void setPedestal2InsulatorMake(String pedestal2InsulatorMake) {
		this.pedestal2InsulatorMake = pedestal2InsulatorMake;
	}

	public String getPedestal2InsulatorBatch() {
		return pedestal2InsulatorBatch;
	}

	public void setPedestal2InsulatorBatch(String pedestal2InsulatorBatch) {
		this.pedestal2InsulatorBatch = pedestal2InsulatorBatch;
	}

	public String getCore2InsulatorMake() {
		return core2InsulatorMake;
	}

	public void setCore2InsulatorMake(String core2InsulatorMake) {
		this.core2InsulatorMake = core2InsulatorMake;
	}

	public String getStay3InsulatorMake() {
		return stay3InsulatorMake;
	}

	public void setStay3InsulatorMake(String stay3InsulatorMake) {
		this.stay3InsulatorMake = stay3InsulatorMake;
	}

	public String getStay3InsulatorBatch() {
		return stay3InsulatorBatch;
	}

	public void setStay3InsulatorBatch(String stay3InsulatorBatch) {
		this.stay3InsulatorBatch = stay3InsulatorBatch;
	}

	public String getBracket3InsulatorMake() {
		return bracket3InsulatorMake;
	}

	public void setBracket3InsulatorMake(String bracket3InsulatorMake) {
		this.bracket3InsulatorMake = bracket3InsulatorMake;
	}

	public String getBracket3InsulatorBatch() {
		return bracket3InsulatorBatch;
	}

	public void setBracket3InsulatorBatch(String bracket3InsulatorBatch) {
		this.bracket3InsulatorBatch = bracket3InsulatorBatch;
	}

	public String getStag3Ton9InsulatorMake() {
		return stag3Ton9InsulatorMake;
	}

	public void setStag3Ton9InsulatorMake(String stag3Ton9InsulatorMake) {
		this.stag3Ton9InsulatorMake = stag3Ton9InsulatorMake;
	}

	public String getStag3Ton9InsulatorBatch() {
		return stag3Ton9InsulatorBatch;
	}

	public void setStag3Ton9InsulatorBatch(String stag3Ton9InsulatorBatch) {
		this.stag3Ton9InsulatorBatch = stag3Ton9InsulatorBatch;
	}

	public String getRod3InsulatorMake() {
		return rod3InsulatorMake;
	}

	public void setRod3InsulatorMake(String rod3InsulatorMake) {
		this.rod3InsulatorMake = rod3InsulatorMake;
	}

	public String getRod3InsulatorBatch() {
		return rod3InsulatorBatch;
	}

	public void setRod3InsulatorBatch(String rod3InsulatorBatch) {
		this.rod3InsulatorBatch = rod3InsulatorBatch;
	}

	public String getPedestal3InsulatorMake() {
		return pedestal3InsulatorMake;
	}

	public void setPedestal3InsulatorMake(String pedestal3InsulatorMake) {
		this.pedestal3InsulatorMake = pedestal3InsulatorMake;
	}

	public String getPedestal3InsulatorBatch() {
		return pedestal3InsulatorBatch;
	}

	public void setPedestal3InsulatorBatch(String pedestal3InsulatorBatch) {
		this.pedestal3InsulatorBatch = pedestal3InsulatorBatch;
	}

	public String getCore3InsulatorMake() {
		return core3InsulatorMake;
	}

	public void setCore3InsulatorMake(String core3InsulatorMake) {
		this.core3InsulatorMake = core3InsulatorMake;
	}

	public String getCore3InsulatorBatch() {
		return core3InsulatorBatch;
	}

	public void setCore3InsulatorBatch(String core3InsulatorBatch) {
		this.core3InsulatorBatch = core3InsulatorBatch;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
