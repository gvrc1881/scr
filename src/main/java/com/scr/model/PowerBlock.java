package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the power_blocks database table.
 * 
 */
@Entity
@Table(name = "power_blocks" , uniqueConstraints={@UniqueConstraint(name = "old_pk_power_blocks_uniq", columnNames ={"pb_operation_seq_id", "data_div"})})
@NamedQuery(name="PowerBlock.findAll", query="SELECT p FROM PowerBlock p")
public class PowerBlock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="between_trains")
	private String betweenTrains;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="current_status")
	private String currentStatus;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="elementary_section_code")
	private String elementarySectionCode;

	@Column(name="equipment_to_work")
	private String equipmentToWork;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="field_no_ptw_issue")
	private String fieldNoPtwIssue;

	@Column(name="field_no_ptw_return")
	private String fieldNoPtwReturn;

	@Column(name="from_location")
	private String fromLocation;

	@Column(name="from_time")
	private Timestamp fromTime;

	@Column(name="grant_period")
	private String grantPeriod;

	@Column(name="km_of_work")
	private String kmOfWork;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String line;

	private String line2;

	@Column(name="message_time")
	private Timestamp messageTime;

	@Column(name="pb_granted_from_date_time")
	private Timestamp pbGrantedFromDateTime;

	@Column(name="pb_granted_thru_date_time")
	private Timestamp pbGrantedThruDateTime;

	@Column(name="pb_operation_seq_id")
	private String pbOperationSeqId;

	@Column(name="pb_requested_from_date_time")
	private Timestamp pbRequestedFromDateTime;

	@Column(name="pb_requested_thru_date_time")
	private Timestamp pbRequestedThruDateTime;

	private String post;

	@Column(name="power_block_section")
	private String powerBlockSection;

	@Column(name="ptw_availed_from_date_time")
	private Timestamp ptwAvailedFromDateTime;

	@Column(name="ptw_availed_thru_date_time")
	private Timestamp ptwAvailedThruDateTime;

	@Column(name="ptw_details_by_manual")
	private String ptwDetailsByManual;

	private String purpose;

	private String remarks;

	@Column(name="req_department")
	private String reqDepartment;

	@Column(name="req_period")
	private String reqPeriod;

	@Column(name="reqn_by")
	private String reqnBy;

	private String schedule;

	private String section;

	@Column(name="section_controller")
	private String sectionController;

	@Column(name="shadow_block")
	private String shadowBlock;

	@Column(name="special_remarks")
	private String specialRemarks;

	@Column(name="staff_to_work")
	private String staffToWork;

	@Column(name="supervisor_incharge")
	private String supervisorIncharge;

	@Column(name="switching_equipment")
	private String switchingEquipment;

	@Column(name="switching_station")
	private String switchingStation;

	private String tkm;

	@Column(name="to_location")
	private String toLocation;

	@Column(name="to_time")
	private Timestamp toTime;

	@Column(name="tpc_board")
	private String tpcBoard;

	@Column(name="tpc_no_ptw_issue")
	private String tpcNoPtwIssue;

	@Column(name="tpc_no_ptw_return")
	private String tpcNoPtwReturn;

	@Column(name="type_of_operation")
	private String typeOfOperation;

	public PowerBlock() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBetweenTrains() {
		return this.betweenTrains;
	}

	public void setBetweenTrains(String betweenTrains) {
		this.betweenTrains = betweenTrains;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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

	public String getCurrentStatus() {
		return this.currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getElementarySectionCode() {
		return this.elementarySectionCode;
	}

	public void setElementarySectionCode(String elementarySectionCode) {
		this.elementarySectionCode = elementarySectionCode;
	}

	public String getEquipmentToWork() {
		return this.equipmentToWork;
	}

	public void setEquipmentToWork(String equipmentToWork) {
		this.equipmentToWork = equipmentToWork;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFieldNoPtwIssue() {
		return this.fieldNoPtwIssue;
	}

	public void setFieldNoPtwIssue(String fieldNoPtwIssue) {
		this.fieldNoPtwIssue = fieldNoPtwIssue;
	}

	public String getFieldNoPtwReturn() {
		return this.fieldNoPtwReturn;
	}

	public void setFieldNoPtwReturn(String fieldNoPtwReturn) {
		this.fieldNoPtwReturn = fieldNoPtwReturn;
	}

	public String getFromLocation() {
		return this.fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public Timestamp getFromTime() {
		return this.fromTime;
	}

	public void setFromTime(Timestamp fromTime) {
		this.fromTime = fromTime;
	}

	public String getGrantPeriod() {
		return this.grantPeriod;
	}

	public void setGrantPeriod(String grantPeriod) {
		this.grantPeriod = grantPeriod;
	}

	public String getKmOfWork() {
		return this.kmOfWork;
	}

	public void setKmOfWork(String kmOfWork) {
		this.kmOfWork = kmOfWork;
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

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLine2() {
		return this.line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public Timestamp getMessageTime() {
		return this.messageTime;
	}

	public void setMessageTime(Timestamp messageTime) {
		this.messageTime = messageTime;
	}

	public Timestamp getPbGrantedFromDateTime() {
		return this.pbGrantedFromDateTime;
	}

	public void setPbGrantedFromDateTime(Timestamp pbGrantedFromDateTime) {
		this.pbGrantedFromDateTime = pbGrantedFromDateTime;
	}

	public Timestamp getPbGrantedThruDateTime() {
		return this.pbGrantedThruDateTime;
	}

	public void setPbGrantedThruDateTime(Timestamp pbGrantedThruDateTime) {
		this.pbGrantedThruDateTime = pbGrantedThruDateTime;
	}

	public String getPbOperationSeqId() {
		return this.pbOperationSeqId;
	}

	public void setPbOperationSeqId(String pbOperationSeqId) {
		this.pbOperationSeqId = pbOperationSeqId;
	}

	public Timestamp getPbRequestedFromDateTime() {
		return this.pbRequestedFromDateTime;
	}

	public void setPbRequestedFromDateTime(Timestamp pbRequestedFromDateTime) {
		this.pbRequestedFromDateTime = pbRequestedFromDateTime;
	}

	public Timestamp getPbRequestedThruDateTime() {
		return this.pbRequestedThruDateTime;
	}

	public void setPbRequestedThruDateTime(Timestamp pbRequestedThruDateTime) {
		this.pbRequestedThruDateTime = pbRequestedThruDateTime;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPowerBlockSection() {
		return this.powerBlockSection;
	}

	public void setPowerBlockSection(String powerBlockSection) {
		this.powerBlockSection = powerBlockSection;
	}

	public Timestamp getPtwAvailedFromDateTime() {
		return this.ptwAvailedFromDateTime;
	}

	public void setPtwAvailedFromDateTime(Timestamp ptwAvailedFromDateTime) {
		this.ptwAvailedFromDateTime = ptwAvailedFromDateTime;
	}

	public Timestamp getPtwAvailedThruDateTime() {
		return this.ptwAvailedThruDateTime;
	}

	public void setPtwAvailedThruDateTime(Timestamp ptwAvailedThruDateTime) {
		this.ptwAvailedThruDateTime = ptwAvailedThruDateTime;
	}

	public String getPtwDetailsByManual() {
		return this.ptwDetailsByManual;
	}

	public void setPtwDetailsByManual(String ptwDetailsByManual) {
		this.ptwDetailsByManual = ptwDetailsByManual;
	}

	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReqDepartment() {
		return this.reqDepartment;
	}

	public void setReqDepartment(String reqDepartment) {
		this.reqDepartment = reqDepartment;
	}

	public String getReqPeriod() {
		return this.reqPeriod;
	}

	public void setReqPeriod(String reqPeriod) {
		this.reqPeriod = reqPeriod;
	}

	public String getReqnBy() {
		return this.reqnBy;
	}

	public void setReqnBy(String reqnBy) {
		this.reqnBy = reqnBy;
	}

	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSectionController() {
		return this.sectionController;
	}

	public void setSectionController(String sectionController) {
		this.sectionController = sectionController;
	}

	public String getShadowBlock() {
		return this.shadowBlock;
	}

	public void setShadowBlock(String shadowBlock) {
		this.shadowBlock = shadowBlock;
	}

	public String getSpecialRemarks() {
		return this.specialRemarks;
	}

	public void setSpecialRemarks(String specialRemarks) {
		this.specialRemarks = specialRemarks;
	}

	public String getStaffToWork() {
		return this.staffToWork;
	}

	public void setStaffToWork(String staffToWork) {
		this.staffToWork = staffToWork;
	}

	public String getSupervisorIncharge() {
		return this.supervisorIncharge;
	}

	public void setSupervisorIncharge(String supervisorIncharge) {
		this.supervisorIncharge = supervisorIncharge;
	}

	public String getSwitchingEquipment() {
		return this.switchingEquipment;
	}

	public void setSwitchingEquipment(String switchingEquipment) {
		this.switchingEquipment = switchingEquipment;
	}

	public String getSwitchingStation() {
		return this.switchingStation;
	}

	public void setSwitchingStation(String switchingStation) {
		this.switchingStation = switchingStation;
	}

	public String getTkm() {
		return this.tkm;
	}

	public void setTkm(String tkm) {
		this.tkm = tkm;
	}

	public String getToLocation() {
		return this.toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public Timestamp getToTime() {
		return this.toTime;
	}

	public void setToTime(Timestamp toTime) {
		this.toTime = toTime;
	}

	public String getTpcBoard() {
		return this.tpcBoard;
	}

	public void setTpcBoard(String tpcBoard) {
		this.tpcBoard = tpcBoard;
	}

	public String getTpcNoPtwIssue() {
		return this.tpcNoPtwIssue;
	}

	public void setTpcNoPtwIssue(String tpcNoPtwIssue) {
		this.tpcNoPtwIssue = tpcNoPtwIssue;
	}

	public String getTpcNoPtwReturn() {
		return this.tpcNoPtwReturn;
	}

	public void setTpcNoPtwReturn(String tpcNoPtwReturn) {
		this.tpcNoPtwReturn = tpcNoPtwReturn;
	}

	public String getTypeOfOperation() {
		return this.typeOfOperation;
	}

	public void setTypeOfOperation(String typeOfOperation) {
		this.typeOfOperation = typeOfOperation;
	}

}
