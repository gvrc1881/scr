package com.scr.message.response;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class EnergyConsumptionResponse {
	
	private Date req_date;
	private String feeder_id;
	private String feeder_name;
	private Double multiplication_fac;
	private Date requested_reading_date;
	private String first_reading_after_meter_fix;
	private Date meter_start_date;
	private Date recent_reading_date;
	private Integer no_of_days_lapsed_reading;
	private String prev_kwh;
	private Double cur_kwh;
	private String prev_kvah;
	private Double cur_kvah;
	private String prev_rkvah_lag;
	private Double cur_rkvah_lag;
	private String prev_rkvah_lead;
	private Double cur_rkvah_lead;
	private Double cur_cmd;
	private Double cur_rmd;
	private Double cur_vol_max;
	private Double cur_vol_min;
	private Double cur_max_load;
	
	
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
	public Date getRequested_reading_date() {
		return requested_reading_date;
	}
	public void setRequested_reading_date(Date requested_reading_date) {
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
	public Date getRecent_reading_date() {
		return recent_reading_date;
	}
	public void setRecent_reading_date(Date recent_reading_date) {
		this.recent_reading_date = recent_reading_date;
	}
	public Integer getNo_of_days_lapsed_reading() {
		return no_of_days_lapsed_reading;
	}
	public void setNo_of_days_lapsed_reading(Integer no_of_days_lapsed_reading) {
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
	
	
}
