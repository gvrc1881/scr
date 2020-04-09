package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the energy_consumption database table.
 * 
 */
@Entity
// @Table(name="energy_consumption")

@Table(name = "energy_consumption" , uniqueConstraints={@UniqueConstraint(name = "old_pk_energy_consumption_uniq", columnNames ={"seq_id", "data_div"})})
//
@NamedQuery(name="EnergyConsumption.findAll", query="SELECT e FROM EnergyConsumption e")
public class EnergyConsumption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String cmd;

	private String cpf;

	@Column(name="created_by")
	private String createdBy;

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

	@Temporal(TemporalType.DATE)
	@Column(name="energy_reading_date")
	private Date energyReadingDate;

	@Column(name="joint_meter")
	private String jointMeter;

	private String kvah;

	private String kwh;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String location;

	@Column(name="location_type")
	private String locationType;

	@Column(name="max_load")
	private String maxLoad;

	@Column(name="max_load_time")
	private Timestamp maxLoadTime;

	private String pf;

	private String remarks;

	@Column(name="rkvah_lag")
	private String rkvahLag;

	@Column(name="rkvah_lead")
	private String rkvahLead;

	private String rmd;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="vol_max")
	private String volMax;

	@Column(name="vol_min")
	private String volMin;

	public EnergyConsumption() {
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

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public Date getEnergyReadingDate() {
		return this.energyReadingDate;
	}

	public void setEnergyReadingDate(Date energyReadingDate) {
		this.energyReadingDate = energyReadingDate;
	}

	public String getJointMeter() {
		return this.jointMeter;
	}

	public void setJointMeter(String jointMeter) {
		this.jointMeter = jointMeter;
	}

	public String getKvah() {
		return this.kvah;
	}

	public void setKvah(String kvah) {
		this.kvah = kvah;
	}

	public String getKwh() {
		return this.kwh;
	}

	public void setKwh(String kwh) {
		this.kwh = kwh;
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

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationType() {
		return this.locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getMaxLoad() {
		return this.maxLoad;
	}

	public void setMaxLoad(String maxLoad) {
		this.maxLoad = maxLoad;
	}

	public Timestamp getMaxLoadTime() {
		return this.maxLoadTime;
	}

	public void setMaxLoadTime(Timestamp maxLoadTime) {
		this.maxLoadTime = maxLoadTime;
	}

	public String getPf() {
		return this.pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRkvahLag() {
		return this.rkvahLag;
	}

	public void setRkvahLag(String rkvahLag) {
		this.rkvahLag = rkvahLag;
	}

	public String getRkvahLead() {
		return this.rkvahLead;
	}

	public void setRkvahLead(String rkvahLead) {
		this.rkvahLead = rkvahLead;
	}

	public String getRmd() {
		return this.rmd;
	}

	public void setRmd(String rmd) {
		this.rmd = rmd;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getVolMax() {
		return this.volMax;
	}

	public void setVolMax(String volMax) {
		this.volMax = volMax;
	}

	public String getVolMin() {
		return this.volMin;
	}

	public void setVolMin(String volMin) {
		this.volMin = volMin;
	}

}