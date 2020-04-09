package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the energy_meter database table.
 * 
 */
@Entity
// @Table(name="energy_meter")

@Table(name = "energy_meter" , uniqueConstraints={@UniqueConstraint(name = "old_pk_energy_meter_uniq", columnNames ={"seq_id", "data_div"})})
//
@NamedQuery(name="EnergyMeter.findAll", query="SELECT e FROM EnergyMeter e")
public class EnergyMeter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String cmd;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="end_date")
	private Timestamp endDate;

	@Column(name="end_kvah")
	private String endKvah;

	@Column(name="end_kwh")
	private String endKwh;

	@Column(name="end_rkvah_lag")
	private String endRkvahLag;

	@Column(name="end_rkvah_lead")
	private String endRkvahLead;

	@Column(name="feeder_id")
	private String feederId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="m_end_reading")
	private String mEndReading;

	@Column(name="m_start_reading")
	private String mStartReading;

	@Column(name="meter_make")
	private String meterMake;

	@Column(name="meter_model")
	private String meterModel;

	@Column(name="meter_no")
	private String meterNo;

	@Column(name="multiplication_fac")
	private String multiplicationFac;

	private String remarks;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="start_date")
	private Timestamp startDate;

	@Column(name="start_kvah")
	private String startKvah;

	@Column(name="start_kwh")
	private String startKwh;

	@Column(name="start_rkvah_lag")
	private String startRkvahLag;

	@Column(name="start_rkvah_lead")
	private String startRkvahLead;

	public EnergyMeter() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
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

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getEndKvah() {
		return this.endKvah;
	}

	public void setEndKvah(String endKvah) {
		this.endKvah = endKvah;
	}

	public String getEndKwh() {
		return this.endKwh;
	}

	public void setEndKwh(String endKwh) {
		this.endKwh = endKwh;
	}

	public String getEndRkvahLag() {
		return this.endRkvahLag;
	}

	public void setEndRkvahLag(String endRkvahLag) {
		this.endRkvahLag = endRkvahLag;
	}

	public String getEndRkvahLead() {
		return this.endRkvahLead;
	}

	public void setEndRkvahLead(String endRkvahLead) {
		this.endRkvahLead = endRkvahLead;
	}

	public String getFeederId() {
		return this.feederId;
	}

	public void setFeederId(String feederId) {
		this.feederId = feederId;
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

	public String getMEndReading() {
		return this.mEndReading;
	}

	public void setMEndReading(String mEndReading) {
		this.mEndReading = mEndReading;
	}

	public String getMStartReading() {
		return this.mStartReading;
	}

	public void setMStartReading(String mStartReading) {
		this.mStartReading = mStartReading;
	}

	public String getMeterMake() {
		return this.meterMake;
	}

	public void setMeterMake(String meterMake) {
		this.meterMake = meterMake;
	}

	public String getMeterModel() {
		return this.meterModel;
	}

	public void setMeterModel(String meterModel) {
		this.meterModel = meterModel;
	}

	public String getMeterNo() {
		return this.meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getMultiplicationFac() {
		return this.multiplicationFac;
	}

	public void setMultiplicationFac(String multiplicationFac) {
		this.multiplicationFac = multiplicationFac;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getStartKvah() {
		return this.startKvah;
	}

	public void setStartKvah(String startKvah) {
		this.startKvah = startKvah;
	}

	public String getStartKwh() {
		return this.startKwh;
	}

	public void setStartKwh(String startKwh) {
		this.startKwh = startKwh;
	}

	public String getStartRkvahLag() {
		return this.startRkvahLag;
	}

	public void setStartRkvahLag(String startRkvahLag) {
		this.startRkvahLag = startRkvahLag;
	}

	public String getStartRkvahLead() {
		return this.startRkvahLead;
	}

	public void setStartRkvahLead(String startRkvahLead) {
		this.startRkvahLead = startRkvahLead;
	}

}