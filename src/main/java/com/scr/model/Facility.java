package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the facility database table.
 * 
 */
@Entity
//

@Table(name = "facility" , uniqueConstraints={@UniqueConstraint(name = "old_pk_facility_uniq", columnNames ={"data_div", "facility_id"})})
//
@NamedQuery(name="Facility.findAll", query="SELECT f FROM Facility f")
public class Facility implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="closed_date")
	private Timestamp closedDate;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="default_inventory_item_type_id")
	private String defaultInventoryItemTypeId;

	@Column(name="default_weight_uom_id")
	private String defaultWeightUomId;

	@Column(name="depot_type")
	private String depotType;

	private String description;

	private String division;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="facility_name")
	private String facilityName;

	@Column(name="facility_type_id")
	private String facilityTypeId;

	@Column(name="fp_track_enable")
	private String fpTrackEnable;

	@Column(name="fp_track_record_frequency")
	private BigDecimal fpTrackRecordFrequency;

	@Column(name="is_disable")
	private String isDisable;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="opened_date")
	private Timestamp openedDate;

	@Column(name="parent_depot")
	private String parentDepot;

	@Column(name="parent_facility_id")
	private String parentFacilityId;

	@Column(name="primary_facility_group_id")
	private String primaryFacilityGroupId;

	private String remarks;

	@Column(name="reserve_order_enum_id")
	private String reserveOrderEnumId;

	@Column(name="sub_division")
	private String subDivision;

	public Facility() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getClosedDate() {
		return this.closedDate;
	}

	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
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

	public String getDefaultInventoryItemTypeId() {
		return this.defaultInventoryItemTypeId;
	}

	public void setDefaultInventoryItemTypeId(String defaultInventoryItemTypeId) {
		this.defaultInventoryItemTypeId = defaultInventoryItemTypeId;
	}

	public String getDefaultWeightUomId() {
		return this.defaultWeightUomId;
	}

	public void setDefaultWeightUomId(String defaultWeightUomId) {
		this.defaultWeightUomId = defaultWeightUomId;
	}

	public String getDepotType() {
		return this.depotType;
	}

	public void setDepotType(String depotType) {
		this.depotType = depotType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return this.facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityTypeId() {
		return this.facilityTypeId;
	}

	public void setFacilityTypeId(String facilityTypeId) {
		this.facilityTypeId = facilityTypeId;
	}

	public String getFpTrackEnable() {
		return this.fpTrackEnable;
	}

	public void setFpTrackEnable(String fpTrackEnable) {
		this.fpTrackEnable = fpTrackEnable;
	}

	public BigDecimal getFpTrackRecordFrequency() {
		return this.fpTrackRecordFrequency;
	}

	public void setFpTrackRecordFrequency(BigDecimal fpTrackRecordFrequency) {
		this.fpTrackRecordFrequency = fpTrackRecordFrequency;
	}

	public String getIsDisable() {
		return this.isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
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

	public Timestamp getOpenedDate() {
		return this.openedDate;
	}

	public void setOpenedDate(Timestamp openedDate) {
		this.openedDate = openedDate;
	}

	public String getParentDepot() {
		return this.parentDepot;
	}

	public void setParentDepot(String parentDepot) {
		this.parentDepot = parentDepot;
	}

	public String getParentFacilityId() {
		return this.parentFacilityId;
	}

	public void setParentFacilityId(String parentFacilityId) {
		this.parentFacilityId = parentFacilityId;
	}

	public String getPrimaryFacilityGroupId() {
		return this.primaryFacilityGroupId;
	}

	public void setPrimaryFacilityGroupId(String primaryFacilityGroupId) {
		this.primaryFacilityGroupId = primaryFacilityGroupId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReserveOrderEnumId() {
		return this.reserveOrderEnumId;
	}

	public void setReserveOrderEnumId(String reserveOrderEnumId) {
		this.reserveOrderEnumId = reserveOrderEnumId;
	}

	public String getSubDivision() {
		return this.subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

}