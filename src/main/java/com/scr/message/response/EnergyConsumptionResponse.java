package com.scr.message.response;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class EnergyConsumptionResponse {
	private Long id;
	private Date req_date;
	private String feeder_id;
	private String feeder_name;
	private Double multiplication_fac;
	private String requested_reading_date;
	private String first_reading_after_meter_fix;
	private Date meter_start_date;
	private String recent_reading_date;
	private String no_of_days_lapsed_reading;
	private String reading_gap_days;
	
	private String data_div;
	
	private String prev_kwh;
	private Double cur_kwh;
	private Double consumption_kwh;
	
	private String prev_kvah;
	private Double cur_kvah;
	private Double consumption_kvah;
	
	private String prev_rkvah_lag;
	private Double cur_rkvah_lag;
	private Double consumption_rkvah_lag;
	
	private String prev_rkvah_lead;
	private Double cur_rkvah_lead;
	private Double consumption_rkvah_lead;
	
	private Double cur_cmd;
	private Double cur_rmd;
	private Double cur_vol_max;
	private Double cur_vol_min;
	private Double cur_max_load;
	
	private Double rmd;
	private Double cpf;
	private Double pf;
	
	private Date updatedOn;
	private String updatedBy;
	
	private Date joint_reading_date;
	private Integer no_of_days_lapsed_j_reading;
	private Double jr_kwh;
	private Double jr_kvah;
	private Double jr_rkvah_lag;
	private Double jr_rkvah_lead;
	
	private String max_load_time_hhmm;
	private String remarks;
	
	private String joint_meter;
	
	private String energyReadingDate;
	
	public Date getReq_date() {
		return req_date;
	}
	public void setReq_date(Date req_date) {
		this.req_date = req_date;
	}
	public String getFeeder_id() {
		return feeder_id;
	}
	public void setFeeder_id(String feeder_id) {
		this.feeder_id = feeder_id;
	}
	public String getFeeder_name() {
		return feeder_name;
	}
	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}
	public Double getMultiplication_fac() {
		return multiplication_fac;
	}
	public void setMultiplication_fac(Double multiplication_fac) {
		this.multiplication_fac = multiplication_fac;
	}
	public String getRequested_reading_date() {
		return requested_reading_date;
	}
	public void setRequested_reading_date(String requested_reading_date) {
		this.requested_reading_date = requested_reading_date;
	}
	public String getFirst_reading_after_meter_fix() {
		return first_reading_after_meter_fix;
	}
	public void setFirst_reading_after_meter_fix(String first_reading_after_meter_fix) {
		this.first_reading_after_meter_fix = first_reading_after_meter_fix;
	}
	public Date getMeter_start_date() {
		return meter_start_date;
	}
	public void setMeter_start_date(Date meter_start_date) {
		this.meter_start_date = meter_start_date;
	}
	public String getRecent_reading_date() {
		return recent_reading_date;
	}
	public void setRecent_reading_date(String recent_reading_date) {
		this.recent_reading_date = recent_reading_date;
	}
	public String getNo_of_days_lapsed_reading() {
		return no_of_days_lapsed_reading;
	}
	public void setNo_of_days_lapsed_reading(String no_of_days_lapsed_reading) {
		this.no_of_days_lapsed_reading = no_of_days_lapsed_reading;
	}
	
	
	public Double getCur_kwh() {
		return cur_kwh;
	}
	public void setCur_kwh(Double cur_kwh) {
		this.cur_kwh = cur_kwh;
	}
	
	public Double getCur_rkvah_lag() {
		return cur_rkvah_lag;
	}
	public void setCur_rkvah_lag(Double cur_rkvah_lag) {
		this.cur_rkvah_lag = cur_rkvah_lag;
	}
	
	public Double getCur_rkvah_lead() {
		return cur_rkvah_lead;
	}
	public void setCur_rkvah_lead(Double cur_rkvah_lead) {
		this.cur_rkvah_lead = cur_rkvah_lead;
	}
	public Double getCur_cmd() {
		return cur_cmd;
	}
	public void setCur_cmd(Double cur_cmd) {
		this.cur_cmd = cur_cmd;
	}
	public Double getCur_rmd() {
		return cur_rmd;
	}
	public void setCur_rmd(Double cur_rmd) {
		this.cur_rmd = cur_rmd;
	}
	public Double getCur_vol_max() {
		return cur_vol_max;
	}
	public void setCur_vol_max(Double cur_vol_max) {
		this.cur_vol_max = cur_vol_max;
	}
	public Double getCur_vol_min() {
		return cur_vol_min;
	}
	public void setCur_vol_min(Double cur_vol_min) {
		this.cur_vol_min = cur_vol_min;
	}
	public Double getCur_max_load() {
		return cur_max_load;
	}
	public void setCur_max_load(Double cur_max_load) {
		this.cur_max_load = cur_max_load;
	}
	public String getPrev_kwh() {
		return prev_kwh;
	}
	public void setPrev_kwh(String prev_kwh) {
		this.prev_kwh = prev_kwh;
	}
	public String getPrev_kvah() {
		return prev_kvah;
	}
	public void setPrev_kvah(String prev_kvah) {
		this.prev_kvah = prev_kvah;
	}
	public Double getCur_kvah() {
		return cur_kvah;
	}
	public void setCur_kvah(Double cur_kvah) {
		this.cur_kvah = cur_kvah;
	}
	public String getPrev_rkvah_lag() {
		return prev_rkvah_lag;
	}
	public void setPrev_rkvah_lag(String prev_rkvah_lag) {
		this.prev_rkvah_lag = prev_rkvah_lag;
	}
	public String getPrev_rkvah_lead() {
		return prev_rkvah_lead;
	}
	public void setPrev_rkvah_lead(String prev_rkvah_lead) {
		this.prev_rkvah_lead = prev_rkvah_lead;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getConsumption_kwh() {
		return consumption_kwh;
	}
	public void setConsumption_kwh(Double consumption_kwh) {
		this.consumption_kwh = consumption_kwh;
	}
	public Double getConsumption_kvah() {
		return consumption_kvah;
	}
	public void setConsumption_kvah(Double consumption_kvah) {
		this.consumption_kvah = consumption_kvah;
	}
	public Double getConsumption_rkvah_lag() {
		return consumption_rkvah_lag;
	}
	public void setConsumption_rkvah_lag(Double consumption_rkvah_lag) {
		this.consumption_rkvah_lag = consumption_rkvah_lag;
	}
	public Double getConsumption_rkvah_lead() {
		return consumption_rkvah_lead;
	}
	public void setConsumption_rkvah_lead(Double consumption_rkvah_lead) {
		this.consumption_rkvah_lead = consumption_rkvah_lead;
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
	public String getReading_gap_days() {
		return reading_gap_days;
	}
	public void setReading_gap_days(String reading_gap_days) {
		this.reading_gap_days = reading_gap_days;
	}
	
	public Date getJoint_reading_date() {
		return joint_reading_date;
	}
	public void setJoint_reading_date(Date joint_reading_date) {
		this.joint_reading_date = joint_reading_date;
	}
	public Integer getNo_of_days_lapsed_j_reading() {
		return no_of_days_lapsed_j_reading;
	}
	public void setNo_of_days_lapsed_j_reading(Integer no_of_days_lapsed_j_reading) {
		this.no_of_days_lapsed_j_reading = no_of_days_lapsed_j_reading;
	}
	public Double getJr_kwh() {
		return jr_kwh;
	}
	public void setJr_kwh(Double jr_kwh) {
		this.jr_kwh = jr_kwh;
	}
	public Double getJr_kvah() {
		return jr_kvah;
	}
	public void setJr_kvah(Double jr_kvah) {
		this.jr_kvah = jr_kvah;
	}
	public Double getJr_rkvah_lag() {
		return jr_rkvah_lag;
	}
	public void setJr_rkvah_lag(Double jr_rkvah_lag) {
		this.jr_rkvah_lag = jr_rkvah_lag;
	}
	public Double getJr_rkvah_lead() {
		return jr_rkvah_lead;
	}
	public void setJr_rkvah_lead(Double jr_rkvah_lead) {
		this.jr_rkvah_lead = jr_rkvah_lead;
	}
	public String getMax_load_time_hhmm() {
		return max_load_time_hhmm;
	}
	public void setMax_load_time_hhmm(String max_load_time_hhmm) {
		this.max_load_time_hhmm = max_load_time_hhmm;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getJoint_meter() {
		return joint_meter;
	}
	public void setJoint_meter(String joint_meter) {
		this.joint_meter = joint_meter;
	}
	public String getData_div() {
		return data_div;
	}
	public void setData_div(String data_div) {
		this.data_div = data_div;
	}
	public String getEnergyReadingDate() {
		return energyReadingDate;
	}
	public void setEnergyReadingDate(String energyReadingDate) {
		this.energyReadingDate = energyReadingDate;
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
	@Override
	public String toString() {
		return "EnergyConsumptionResponse [id=" + id + ", req_date=" + req_date + ", feeder_id=" + feeder_id
				+ ", feeder_name=" + feeder_name + ", multiplication_fac=" + multiplication_fac
				+ ", requested_reading_date=" + requested_reading_date + ", first_reading_after_meter_fix="
				+ first_reading_after_meter_fix + ", meter_start_date=" + meter_start_date + ", recent_reading_date="
				+ recent_reading_date + ", no_of_days_lapsed_reading=" + no_of_days_lapsed_reading
				+ ", reading_gap_days=" + reading_gap_days + ", data_div=" + data_div + ", prev_kwh=" + prev_kwh
				+ ", cur_kwh=" + cur_kwh + ", consumption_kwh=" + consumption_kwh + ", prev_kvah=" + prev_kvah
				+ ", cur_kvah=" + cur_kvah + ", consumption_kvah=" + consumption_kvah + ", prev_rkvah_lag="
				+ prev_rkvah_lag + ", cur_rkvah_lag=" + cur_rkvah_lag + ", consumption_rkvah_lag="
				+ consumption_rkvah_lag + ", prev_rkvah_lead=" + prev_rkvah_lead + ", cur_rkvah_lead=" + cur_rkvah_lead
				+ ", consumption_rkvah_lead=" + consumption_rkvah_lead + ", cur_cmd=" + cur_cmd + ", cur_rmd=" + cur_rmd
				+ ", cur_vol_max=" + cur_vol_max + ", cur_vol_min=" + cur_vol_min + ", cur_max_load=" + cur_max_load
				+ ", rmd=" + rmd + ", cpf=" + cpf + ", pf=" + pf + ", updatedOn=" + updatedOn + ", updatedBy="
				+ updatedBy + ", joint_reading_date=" + joint_reading_date + ", no_of_days_lapsed_j_reading="
				+ no_of_days_lapsed_j_reading + ", jr_kwh=" + jr_kwh + ", jr_kvah=" + jr_kvah + ", jr_rkvah_lag="
				+ jr_rkvah_lag + ", jr_rkvah_lead=" + jr_rkvah_lead + ", max_load_time_hhmm=" + max_load_time_hhmm
				+ ", remarks=" + remarks + ", joint_meter=" + joint_meter + ", energyReadingDate=" + energyReadingDate
				+ "]";
	}
	
	
}
