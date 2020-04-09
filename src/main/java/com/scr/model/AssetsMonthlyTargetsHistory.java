package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the assets_monthly_targets_history database table.
 * 
 */
@Entity
@Table(name = "assets_monthly_targets_history" , uniqueConstraints={@UniqueConstraint(name = "old_pk_assets_monthly_targets_history_uniq", columnNames ={"seq_id", "data_div"})})
@NamedQuery(name="AssetsMonthlyTargetsHistory.findAll", query="SELECT a FROM AssetsMonthlyTargetsHistory a")
public class AssetsMonthlyTargetsHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private double apr;

	@Column(name="asset_type")
	private String assetType;

	private double aug;

	@Column(name="completed_monthly")
	private double completedMonthly;

	private double completedyearly;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private double dec;

	private String depot;

	private double feb;

	private double jan;

	private double jul;

	private double jun;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private double mar;

	private double may;

	private double nov;

	private double oct;

	@Column(name="schedule_code")
	private String scheduleCode;

	private double sep;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="target_month")
	private double targetMonth;

	@Column(name="target_year")
	private double targetYear;

	@Column(name="total_population")
	private double totalPopulation;

	public AssetsMonthlyTargetsHistory() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getApr() {
		return this.apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public String getAssetType() {
		return this.assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public double getAug() {
		return this.aug;
	}

	public void setAug(double aug) {
		this.aug = aug;
	}

	public double getCompletedMonthly() {
		return this.completedMonthly;
	}

	public void setCompletedMonthly(double completedMonthly) {
		this.completedMonthly = completedMonthly;
	}

	public double getCompletedyearly() {
		return this.completedyearly;
	}

	public void setCompletedyearly(double completedyearly) {
		this.completedyearly = completedyearly;
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

	public double getDec() {
		return this.dec;
	}

	public void setDec(double dec) {
		this.dec = dec;
	}

	public String getDepot() {
		return this.depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public double getFeb() {
		return this.feb;
	}

	public void setFeb(double feb) {
		this.feb = feb;
	}

	public double getJan() {
		return this.jan;
	}

	public void setJan(double jan) {
		this.jan = jan;
	}

	public double getJul() {
		return this.jul;
	}

	public void setJul(double jul) {
		this.jul = jul;
	}

	public double getJun() {
		return this.jun;
	}

	public void setJun(double jun) {
		this.jun = jun;
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

	public double getMar() {
		return this.mar;
	}

	public void setMar(double mar) {
		this.mar = mar;
	}

	public double getMay() {
		return this.may;
	}

	public void setMay(double may) {
		this.may = may;
	}

	public double getNov() {
		return this.nov;
	}

	public void setNov(double nov) {
		this.nov = nov;
	}

	public double getOct() {
		return this.oct;
	}

	public void setOct(double oct) {
		this.oct = oct;
	}

	public String getScheduleCode() {
		return this.scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public double getSep() {
		return this.sep;
	}

	public void setSep(double sep) {
		this.sep = sep;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public double getTargetMonth() {
		return this.targetMonth;
	}

	public void setTargetMonth(double targetMonth) {
		this.targetMonth = targetMonth;
	}

	public double getTargetYear() {
		return this.targetYear;
	}

	public void setTargetYear(double targetYear) {
		this.targetYear = targetYear;
	}

	public double getTotalPopulation() {
		return this.totalPopulation;
	}

	public void setTotalPopulation(double totalPopulation) {
		this.totalPopulation = totalPopulation;
	}

}
