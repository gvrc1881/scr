package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the switch_maintenence_history database table.
 * 
 */
@Entity
@Table(name = "switch_maintenence_history" , uniqueConstraints={@UniqueConstraint(name = "old_pk_switch_maintenence_history_uniq", columnNames ={"seq_id", "data_div"})})
@NamedQuery(name="SwitchMaintenenceHistory.findAll", query="SELECT s FROM SwitchMaintenenceHistory s")
public class SwitchMaintenenceHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="close_done_time_lapse")
	private String closeDoneTimeLapse;

	@Column(name="close_time_lapse")
	private String closeTimeLapse;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="delete_status")
	private String deleteStatus;

	@Column(name="field_no_io_close")
	private String fieldNoIoClose;

	@Column(name="field_no_io_close_done")
	private String fieldNoIoCloseDone;

	@Column(name="field_no_io_open")
	private String fieldNoIoOpen;

	@Column(name="field_no_io_open_done")
	private String fieldNoIoOpenDone;

	@Column(name="io_closed_by")
	private String ioClosedBy;

	@Column(name="io_closed_date_time")
	private Timestamp ioClosedDateTime;

	@Column(name="io_closed_date_time_done")
	private Timestamp ioClosedDateTimeDone;

	@Column(name="io_location")
	private String ioLocation;

	@Column(name="io_opened_by")
	private String ioOpenedBy;

	@Column(name="io_opened_date_time")
	private Timestamp ioOpenedDateTime;

	@Column(name="io_opened_date_time_done")
	private Timestamp ioOpenedDateTimeDone;

	@Column(name="io_type")
	private String ioType;

	@Column(name="is_field_operated")
	private String isFieldOperated;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="open_done_time_lapse")
	private String openDoneTimeLapse;

	@Column(name="open_time_lapse")
	private String openTimeLapse;

	@Column(name="pb_operation_seq_id")
	private String pbOperationSeqId;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="tpc_no_io_close")
	private String tpcNoIoClose;

	@Column(name="tpc_no_io_close_done")
	private String tpcNoIoCloseDone;

	@Column(name="tpc_no_io_open")
	private String tpcNoIoOpen;

	@Column(name="tpc_no_io_open_done")
	private String tpcNoIoOpenDone;

	public SwitchMaintenenceHistory() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCloseDoneTimeLapse() {
		return this.closeDoneTimeLapse;
	}

	public void setCloseDoneTimeLapse(String closeDoneTimeLapse) {
		this.closeDoneTimeLapse = closeDoneTimeLapse;
	}

	public String getCloseTimeLapse() {
		return this.closeTimeLapse;
	}

	public void setCloseTimeLapse(String closeTimeLapse) {
		this.closeTimeLapse = closeTimeLapse;
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

	public String getDeleteStatus() {
		return this.deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getFieldNoIoClose() {
		return this.fieldNoIoClose;
	}

	public void setFieldNoIoClose(String fieldNoIoClose) {
		this.fieldNoIoClose = fieldNoIoClose;
	}

	public String getFieldNoIoCloseDone() {
		return this.fieldNoIoCloseDone;
	}

	public void setFieldNoIoCloseDone(String fieldNoIoCloseDone) {
		this.fieldNoIoCloseDone = fieldNoIoCloseDone;
	}

	public String getFieldNoIoOpen() {
		return this.fieldNoIoOpen;
	}

	public void setFieldNoIoOpen(String fieldNoIoOpen) {
		this.fieldNoIoOpen = fieldNoIoOpen;
	}

	public String getFieldNoIoOpenDone() {
		return this.fieldNoIoOpenDone;
	}

	public void setFieldNoIoOpenDone(String fieldNoIoOpenDone) {
		this.fieldNoIoOpenDone = fieldNoIoOpenDone;
	}

	public String getIoClosedBy() {
		return this.ioClosedBy;
	}

	public void setIoClosedBy(String ioClosedBy) {
		this.ioClosedBy = ioClosedBy;
	}

	public Timestamp getIoClosedDateTime() {
		return this.ioClosedDateTime;
	}

	public void setIoClosedDateTime(Timestamp ioClosedDateTime) {
		this.ioClosedDateTime = ioClosedDateTime;
	}

	public Timestamp getIoClosedDateTimeDone() {
		return this.ioClosedDateTimeDone;
	}

	public void setIoClosedDateTimeDone(Timestamp ioClosedDateTimeDone) {
		this.ioClosedDateTimeDone = ioClosedDateTimeDone;
	}

	public String getIoLocation() {
		return this.ioLocation;
	}

	public void setIoLocation(String ioLocation) {
		this.ioLocation = ioLocation;
	}

	public String getIoOpenedBy() {
		return this.ioOpenedBy;
	}

	public void setIoOpenedBy(String ioOpenedBy) {
		this.ioOpenedBy = ioOpenedBy;
	}

	public Timestamp getIoOpenedDateTime() {
		return this.ioOpenedDateTime;
	}

	public void setIoOpenedDateTime(Timestamp ioOpenedDateTime) {
		this.ioOpenedDateTime = ioOpenedDateTime;
	}

	public Timestamp getIoOpenedDateTimeDone() {
		return this.ioOpenedDateTimeDone;
	}

	public void setIoOpenedDateTimeDone(Timestamp ioOpenedDateTimeDone) {
		this.ioOpenedDateTimeDone = ioOpenedDateTimeDone;
	}

	public String getIoType() {
		return this.ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	public String getIsFieldOperated() {
		return this.isFieldOperated;
	}

	public void setIsFieldOperated(String isFieldOperated) {
		this.isFieldOperated = isFieldOperated;
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

	public String getOpenDoneTimeLapse() {
		return this.openDoneTimeLapse;
	}

	public void setOpenDoneTimeLapse(String openDoneTimeLapse) {
		this.openDoneTimeLapse = openDoneTimeLapse;
	}

	public String getOpenTimeLapse() {
		return this.openTimeLapse;
	}

	public void setOpenTimeLapse(String openTimeLapse) {
		this.openTimeLapse = openTimeLapse;
	}

	public String getPbOperationSeqId() {
		return this.pbOperationSeqId;
	}

	public void setPbOperationSeqId(String pbOperationSeqId) {
		this.pbOperationSeqId = pbOperationSeqId;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getTpcNoIoClose() {
		return this.tpcNoIoClose;
	}

	public void setTpcNoIoClose(String tpcNoIoClose) {
		this.tpcNoIoClose = tpcNoIoClose;
	}

	public String getTpcNoIoCloseDone() {
		return this.tpcNoIoCloseDone;
	}

	public void setTpcNoIoCloseDone(String tpcNoIoCloseDone) {
		this.tpcNoIoCloseDone = tpcNoIoCloseDone;
	}

	public String getTpcNoIoOpen() {
		return this.tpcNoIoOpen;
	}

	public void setTpcNoIoOpen(String tpcNoIoOpen) {
		this.tpcNoIoOpen = tpcNoIoOpen;
	}

	public String getTpcNoIoOpenDone() {
		return this.tpcNoIoOpenDone;
	}

	public void setTpcNoIoOpenDone(String tpcNoIoOpenDone) {
		this.tpcNoIoOpenDone = tpcNoIoOpenDone;
	}

}