package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_defualt_fac_cons_ind_etc database table.
 * 
 */
@Entity
@Table(name = "user_defualt_fac_cons_ind_etc" , uniqueConstraints={@UniqueConstraint(name = "old_pk_user_defualt_fac_cons_ind_etc_uniq", columnNames ={"data_div", "sequence_id"})})
@NamedQuery(name="UserDefualtFacConsIndEtc.findAll", query="SELECT u FROM UserDefualtFacConsIndEtc u")
public class UserDefualtFacConsIndEtc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String consignee;

	@Column(name="consignee_code")
	private String consigneeCode;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String depot;

	private String description;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="facility_name")
	private String facilityName;

	@Column(name="facility_type_id")
	private String facilityTypeId;

	@Column(name="from_date")
	private Timestamp fromDate;

	private String indentor;

	@Column(name="is_warehouse_manager")
	private String isWarehouseManager;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="matris_reqd_at")
	private String matrisReqdAt;

	private String remarks;

	@Column(name="sequence_id")
	private String sequenceId;

	@Column(name="thru_date")
	private Timestamp thruDate;

	@Column(name="user_login_id")
	private String userLoginId;

	public UserDefualtFacConsIndEtc() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneeCode() {
		return this.consigneeCode;
	}

	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
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

	public String getDepot() {
		return this.depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Timestamp getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public String getIndentor() {
		return this.indentor;
	}

	public void setIndentor(String indentor) {
		this.indentor = indentor;
	}

	public String getIsWarehouseManager() {
		return this.isWarehouseManager;
	}

	public void setIsWarehouseManager(String isWarehouseManager) {
		this.isWarehouseManager = isWarehouseManager;
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

	public String getMatrisReqdAt() {
		return this.matrisReqdAt;
	}

	public void setMatrisReqdAt(String matrisReqdAt) {
		this.matrisReqdAt = matrisReqdAt;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSequenceId() {
		return this.sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Timestamp getThruDate() {
		return this.thruDate;
	}

	public void setThruDate(Timestamp thruDate) {
		this.thruDate = thruDate;
	}

	public String getUserLoginId() {
		return this.userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

}