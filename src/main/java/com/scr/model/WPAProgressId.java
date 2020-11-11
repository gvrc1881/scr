package com.scr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "wpa_progress_id")
@NamedQuery(name = "WPAProgressId.findAll", query = "SELECT wpapi FROM WPAProgressId wpapi")
public class WPAProgressId implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "wpa_daily_progress_id", foreignKey = @ForeignKey(name = "fk_wpa_progress_id_wpa_daily_progress"))
	private WPADailyProgress wpaDailyProgressId;

	private String assetId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WPADailyProgress getWpaDailyProgressId() {
		return wpaDailyProgressId;
	}

	public void setWpaDailyProgressId(WPADailyProgress wpaDailyProgressId) {
		this.wpaDailyProgressId = wpaDailyProgressId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

}
