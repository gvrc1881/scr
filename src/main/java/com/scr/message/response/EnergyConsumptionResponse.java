package com.scr.message.response;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class EnergyConsumptionResponse {
	private Long id;
	private Date reqDate;
	private String feederId;
	private String feederName;
	private Double multiplicationFac;
	private String requestedReadingDate;
	private String firstReadingAfterMeterFix;
	private Date meterStartDate;
	private String recentReadingDate;
	private String noOfDaysLapsedReading;
	private String readingGapDays;
	
	private String dataDiv;
	
	private String prevKwh;
	private Double curKwh;
	private Double consumptionKwh;
	
	private String prevKvah;
	private Double curKvah;
	private Double consumptionKvah;
	
	private String prevRkvahLag;
	private Double curRkvahLag;
	private Double consumptionRkvahLag;
	
	private String prevRkvahLead;
	private Double curRkvahLead;
	private Double consumptionRkvahLead;
	
	private Double curCmd;
	private Double curRmd;
	private Double curVolMax;
	private Double curVolMin;
	private Double curMaxLoad;
	
	private Double rmd;
	private Double cpf;
	private Double pf;
	
	private Date updatedOn;
	private String updatedBy;
	
	private Date jointReadingDate;
	private Integer noOfDaysLapsedJReading;
	private Double jrKwh;
	private Double jrKvah;
	private Double jrRkvahLag;
	private Double jrRkvahLead;
	
	private String mavgReadingDate;
	private Double mavgKwhValue;
	private Double mavgKvahValue;
	private Double mavgRkvahLagValue;
	private Double mavgRkvahLeadValue;
	
	private Double avgKwh;
	private Double avgKvah;
	private Double avgRkvahLag;
	private Double avgRkvahLead;
	
	private String maxLoadTimeHhmm;
	private String remarks;	
	private String jointMeter;	
	private String energyReadingDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getReqDate() {
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	public String getFeederId() {
		return feederId;
	}
	public void setFeederId(String feederId) {
		this.feederId = feederId;
	}
	public String getFeederName() {
		return feederName;
	}
	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}
	public Double getMultiplicationFac() {
		return multiplicationFac;
	}
	public void setMultiplicationFac(Double multiplicationFac) {
		this.multiplicationFac = multiplicationFac;
	}
	public String getRequestedReadingDate() {
		return requestedReadingDate;
	}
	public void setRequestedReadingDate(String requestedReadingDate) {
		this.requestedReadingDate = requestedReadingDate;
	}
	public String getFirstReadingAfterMeterFix() {
		return firstReadingAfterMeterFix;
	}
	public void setFirstReadingAfterMeterFix(String firstReadingAfterMeterFix) {
		this.firstReadingAfterMeterFix = firstReadingAfterMeterFix;
	}
	public Date getMeterStartDate() {
		return meterStartDate;
	}
	public void setMeterStartDate(Date meterStartDate) {
		this.meterStartDate = meterStartDate;
	}
	public String getRecentReadingDate() {
		return recentReadingDate;
	}
	public void setRecentReadingDate(String recentReadingDate) {
		this.recentReadingDate = recentReadingDate;
	}
	public String getNoOfDaysLapsedReading() {
		return noOfDaysLapsedReading;
	}
	public void setNoOfDaysLapsedReading(String noOfDaysLapsedReading) {
		this.noOfDaysLapsedReading = noOfDaysLapsedReading;
	}
	public String getReadingGapDays() {
		return readingGapDays;
	}
	public void setReadingGapDays(String readingGapDays) {
		this.readingGapDays = readingGapDays;
	}
	public String getDataDiv() {
		return dataDiv;
	}
	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}
	public String getPrevKwh() {
		return prevKwh;
	}
	public void setPrevKwh(String prevKwh) {
		this.prevKwh = prevKwh;
	}
	public Double getCurKwh() {
		return curKwh;
	}
	public void setCurKwh(Double curKwh) {
		this.curKwh = curKwh;
	}
	public Double getConsumptionKwh() {
		return consumptionKwh;
	}
	public void setConsumptionKwh(Double consumptionKwh) {
		this.consumptionKwh = consumptionKwh;
	}
	public String getPrevKvah() {
		return prevKvah;
	}
	public void setPrevKvah(String prevKvah) {
		this.prevKvah = prevKvah;
	}
	public Double getCurKvah() {
		return curKvah;
	}
	public void setCurKvah(Double curKvah) {
		this.curKvah = curKvah;
	}
	public Double getConsumptionKvah() {
		return consumptionKvah;
	}
	public void setConsumptionKvah(Double consumptionKvah) {
		this.consumptionKvah = consumptionKvah;
	}
	public String getPrevRkvahLag() {
		return prevRkvahLag;
	}
	public void setPrevRkvahLag(String prevRkvahLag) {
		this.prevRkvahLag = prevRkvahLag;
	}
	public Double getCurRkvahLag() {
		return curRkvahLag;
	}
	public void setCurRkvahLag(Double curRkvahLag) {
		this.curRkvahLag = curRkvahLag;
	}
	public Double getConsumptionRkvahLag() {
		return consumptionRkvahLag;
	}
	public void setConsumptionRkvahLag(Double consumptionRkvahLag) {
		this.consumptionRkvahLag = consumptionRkvahLag;
	}
	public String getPrevRkvahLead() {
		return prevRkvahLead;
	}
	public void setPrevRkvahLead(String prevRkvahLead) {
		this.prevRkvahLead = prevRkvahLead;
	}
	public Double getCurRkvahLead() {
		return curRkvahLead;
	}
	public void setCurRkvahLead(Double curRkvahLead) {
		this.curRkvahLead = curRkvahLead;
	}
	public Double getConsumptionRkvahLead() {
		return consumptionRkvahLead;
	}
	public void setConsumptionRkvahLead(Double consumptionRkvahLead) {
		this.consumptionRkvahLead = consumptionRkvahLead;
	}
	public Double getCurCmd() {
		return curCmd;
	}
	public void setCurCmd(Double curCmd) {
		this.curCmd = curCmd;
	}
	public Double getCurRmd() {
		return curRmd;
	}
	public void setCurRmd(Double curRmd) {
		this.curRmd = curRmd;
	}
	public Double getCurVolMax() {
		return curVolMax;
	}
	public void setCurVolMax(Double curVolMax) {
		this.curVolMax = curVolMax;
	}
	public Double getCurVolMin() {
		return curVolMin;
	}
	public void setCurVolMin(Double curVolMin) {
		this.curVolMin = curVolMin;
	}
	public Double getCurMaxLoad() {
		return curMaxLoad;
	}
	public void setCurMaxLoad(Double curMaxLoad) {
		this.curMaxLoad = curMaxLoad;
	}
	public Double getRmd() {
		return rmd;
	}
	public void setRmd(Double rmd) {
		this.rmd = rmd;
	}
	public Double getCpf() {
		return cpf;
	}
	public void setCpf(Double cpf) {
		this.cpf = cpf;
	}
	public Double getPf() {
		return pf;
	}
	public void setPf(Double pf) {
		this.pf = pf;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getJointReadingDate() {
		return jointReadingDate;
	}
	public void setJointReadingDate(Date jointReadingDate) {
		this.jointReadingDate = jointReadingDate;
	}
	public Integer getNoOfDaysLapsedJReading() {
		return noOfDaysLapsedJReading;
	}
	public void setNoOfDaysLapsedJReading(Integer noOfDaysLapsedJReading) {
		this.noOfDaysLapsedJReading = noOfDaysLapsedJReading;
	}
	public Double getJrKwh() {
		return jrKwh;
	}
	public void setJrKwh(Double jrKwh) {
		this.jrKwh = jrKwh;
	}
	public Double getJrKvah() {
		return jrKvah;
	}
	public void setJrKvah(Double jrKvah) {
		this.jrKvah = jrKvah;
	}
	public Double getJrRkvahLag() {
		return jrRkvahLag;
	}
	public void setJrRkvahLag(Double jrRkvahLag) {
		this.jrRkvahLag = jrRkvahLag;
	}
	public Double getJrRkvahLead() {
		return jrRkvahLead;
	}
	public void setJrRkvahLead(Double jrRkvahLead) {
		this.jrRkvahLead = jrRkvahLead;
	}
	public String getMavgReadingDate() {
		return mavgReadingDate;
	}
	public void setMavgReadingDate(String mavgReadingDate) {
		this.mavgReadingDate = mavgReadingDate;
	}
	public Double getMavgKwhValue() {
		return mavgKwhValue;
	}
	public void setMavgKwhValue(Double mavgKwhValue) {
		this.mavgKwhValue = mavgKwhValue;
	}
	public Double getMavgKvahValue() {
		return mavgKvahValue;
	}
	public void setMavgKvahValue(Double mavgKvahValue) {
		this.mavgKvahValue = mavgKvahValue;
	}
	public Double getMavgRkvahLagValue() {
		return mavgRkvahLagValue;
	}
	public void setMavgRkvahLagValue(Double mavgRkvahLagValue) {
		this.mavgRkvahLagValue = mavgRkvahLagValue;
	}
	public Double getMavgRkvahLeadValue() {
		return mavgRkvahLeadValue;
	}
	public void setMavgRkvahLeadValue(Double mavgRkvahLeadValue) {
		this.mavgRkvahLeadValue = mavgRkvahLeadValue;
	}
	public Double getAvgKwh() {
		return avgKwh;
	}
	public void setAvgKwh(Double avgKwh) {
		this.avgKwh = avgKwh;
	}
	public Double getAvgKvah() {
		return avgKvah;
	}
	public void setAvgKvah(Double avgKvah) {
		this.avgKvah = avgKvah;
	}
	public Double getAvgRkvahLag() {
		return avgRkvahLag;
	}
	public void setAvgRkvahLag(Double avgRkvahLag) {
		this.avgRkvahLag = avgRkvahLag;
	}
	public Double getAvgRkvahLead() {
		return avgRkvahLead;
	}
	public void setAvgRkvahLead(Double avgRkvahLead) {
		this.avgRkvahLead = avgRkvahLead;
	}
	public String getMaxLoadTimeHhmm() {
		return maxLoadTimeHhmm;
	}
	public void setMaxLoadTimeHhmm(String maxLoadTimeHhmm) {
		this.maxLoadTimeHhmm = maxLoadTimeHhmm;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getJointMeter() {
		return jointMeter;
	}
	public void setJointMeter(String jointMeter) {
		this.jointMeter = jointMeter;
	}
	public String getEnergyReadingDate() {
		return energyReadingDate;
	}
	public void setEnergyReadingDate(String energyReadingDate) {
		this.energyReadingDate = energyReadingDate;
	}
	@Override
	public String toString() {
		return "EnergyConsumptionResponse [id=" + id + ", reqDate=" + reqDate + ", feederId=" + feederId
				+ ", feederName=" + feederName + ", multiplicationFac=" + multiplicationFac + ", requestedReadingDate="
				+ requestedReadingDate + ", firstReadingAfterMeterFix=" + firstReadingAfterMeterFix
				+ ", meterStartDate=" + meterStartDate + ", recentReadingDate=" + recentReadingDate
				+ ", noOfDaysLapsedReading=" + noOfDaysLapsedReading + ", readingGapDays=" + readingGapDays
				+ ", dataDiv=" + dataDiv + ", prevKwh=" + prevKwh + ", curKwh=" + curKwh + ", consumptionKwh="
				+ consumptionKwh + ", prevKvah=" + prevKvah + ", curKvah=" + curKvah + ", consumptionKvah="
				+ consumptionKvah + ", prevRkvahLag=" + prevRkvahLag + ", curRkvahLag=" + curRkvahLag
				+ ", consumptionRkvahLag=" + consumptionRkvahLag + ", prevRkvahLead=" + prevRkvahLead
				+ ", curRkvahLead=" + curRkvahLead + ", consumptionRkvahLead=" + consumptionRkvahLead + ", curCmd="
				+ curCmd + ", curRmd=" + curRmd + ", curVolMax=" + curVolMax + ", curVolMin=" + curVolMin
				+ ", curMaxLoad=" + curMaxLoad + ", rmd=" + rmd + ", cpf=" + cpf + ", pf=" + pf + ", updatedOn="
				+ updatedOn + ", updatedBy=" + updatedBy + ", jointReadingDate=" + jointReadingDate
				+ ", noOfDaysLapsedJReading=" + noOfDaysLapsedJReading + ", jrKwh=" + jrKwh + ", jrKvah=" + jrKvah
				+ ", jrRkvahLag=" + jrRkvahLag + ", jrRkvahLead=" + jrRkvahLead + ", mavgReadingDate=" + mavgReadingDate
				+ ", mavgKwhValue=" + mavgKwhValue + ", mavgKvahValue=" + mavgKvahValue + ", mavgRkvahLagValue="
				+ mavgRkvahLagValue + ", mavgRkvahLeadValue=" + mavgRkvahLeadValue + ", avgKwh=" + avgKwh + ", avgKvah="
				+ avgKvah + ", avgRkvahLag=" + avgRkvahLag + ", avgRkvahLead=" + avgRkvahLead + ", maxLoadTimeHhmm="
				+ maxLoadTimeHhmm + ", remarks=" + remarks + ", jointMeter=" + jointMeter + ", energyReadingDate="
				+ energyReadingDate + "]";
	}
	
	
}
