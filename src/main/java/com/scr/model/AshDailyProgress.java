package com.scr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;

@Entity
@NamedQuery(name = "AshDailyProgress.findAll", query = "SELECT ash FROM AshDailyProgress ash")
public class AshDailyProgress implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int sclAoh;

	private int sclPoh;

	private int mclAoh;

	private int mclPoh;

	private int atdAoh;

	private int atdPoh;

	private int siAoh;

	private int smAoh;

	private int turnoutAoh;

	private int crossoverAoh;

	private int overlapAoh;

	private int gantryAoh;

	private int ptfeAoh;

	private String remark;

	private Date date;

	@ManyToOne
	@JoinColumn(name = "facility", foreignKey = @ForeignKey(name = "fk_ash_daily_progress_facility"))
	private Facility facility;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSclAoh() {
		return sclAoh;
	}

	public void setSclAoh(int sclAoh) {
		this.sclAoh = sclAoh;
	}

	public int getSclPoh() {
		return sclPoh;
	}

	public void setSclPoh(int sclPoh) {
		this.sclPoh = sclPoh;
	}

	public int getMclAoh() {
		return mclAoh;
	}

	public void setMclAoh(int mclAoh) {
		this.mclAoh = mclAoh;
	}

	public int getMclPoh() {
		return mclPoh;
	}

	public void setMclPoh(int mclPoh) {
		this.mclPoh = mclPoh;
	}

	public int getAtdAoh() {
		return atdAoh;
	}

	public void setAtdAoh(int atdAoh) {
		this.atdAoh = atdAoh;
	}

	public int getAtdPoh() {
		return atdPoh;
	}

	public void setAtdPoh(int atdPoh) {
		this.atdPoh = atdPoh;
	}

	public int getSiAoh() {
		return siAoh;
	}

	public void setSiAoh(int siAoh) {
		this.siAoh = siAoh;
	}

	public int getSmAoh() {
		return smAoh;
	}

	public void setSmAoh(int smAoh) {
		this.smAoh = smAoh;
	}

	public int getTurnoutAoh() {
		return turnoutAoh;
	}

	public void setTurnoutAoh(int turnoutAoh) {
		this.turnoutAoh = turnoutAoh;
	}

	public int getCrossoverAoh() {
		return crossoverAoh;
	}

	public void setCrossoverAoh(int crossoverAoh) {
		this.crossoverAoh = crossoverAoh;
	}

	public int getOverlapAoh() {
		return overlapAoh;
	}

	public void setOverlapAoh(int overlapAoh) {
		this.overlapAoh = overlapAoh;
	}

	public int getGantryAoh() {
		return gantryAoh;
	}

	public void setGantryAoh(int gantryAoh) {
		this.gantryAoh = gantryAoh;
	}

	public int getPtfeAoh() {
		return ptfeAoh;
	}

	public void setPtfeAoh(int ptfeAoh) {
		this.ptfeAoh = ptfeAoh;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	@Override
	public String toString() {
		return "AshDailyProgress [id=" + id + ", sclAoh=" + sclAoh + ", sclPoh=" + sclPoh + ", mclAoh=" + mclAoh
				+ ", mclPoh=" + mclPoh + ", atdAoh=" + atdAoh + ", atdPoh=" + atdPoh + ", siAoh=" + siAoh + ", smAoh="
				+ smAoh + ", turnoutAoh=" + turnoutAoh + ", crossoverAoh=" + crossoverAoh + ", overlapAoh=" + overlapAoh
				+ ", gantryAoh=" + gantryAoh + ", ptfeAoh=" + ptfeAoh + ", remark=" + remark + ", date=" + date
				+ ", facility=" + facility + "]";
	}

}
