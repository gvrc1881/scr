package com.scr.model;

import java.io.Serializable;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "stipulations")

public class Stipulations implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "stipulation", columnDefinition = "TEXT")
	private String stipulation;

	@Column(name = "stipulation_to")
	private String stipulationTo;

	@Column(name = "date_of_stipulation")
	private Timestamp dateOfStipulation;

	@Column(name = "date_complied")
	private Timestamp dateComplied;

	@Column(name = "compliance", columnDefinition = "TEXT")
	private String compliance;

	@Column(name = "attachment")
	private String attachment;

	@Column(name = "complied_by")
	private String compliedBy;

	@Column(name = "credated_by")
	private String credatedBy;

	@Column(name = "created_on")
	private Timestamp CreatedOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	@Column(name = "status_id")
	private Integer statusId;

	@ManyToOne
	@JoinColumn(name = "asset_type", foreignKey = @ForeignKey(name = "fk_stipulations_asset_type"))
	private Product assetType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStipulation() {
		return stipulation;
	}

	public void setStipulation(String stipulation) {
		this.stipulation = stipulation;
	}

	public String getStipulationTo() {
		return stipulationTo;
	}

	public void setStipulationTo(String stipulationTo) {
		this.stipulationTo = stipulationTo;
	}

	public Timestamp getDateOfStipulation() {
		return dateOfStipulation;
	}

	public void setDateOfStipulation(Timestamp dateOfStipulation) {
		this.dateOfStipulation = dateOfStipulation;
	}

	public Timestamp getDateComplied() {
		return dateComplied;
	}

	public void setDateComplied(Timestamp dateComplied) {
		this.dateComplied = dateComplied;
	}

	public String getCompliance() {
		return compliance;
	}

	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getCompliedBy() {
		return compliedBy;
	}

	public void setCompliedBy(String compliedBy) {
		this.compliedBy = compliedBy;
	}

	public String getCredatedBy() {
		return credatedBy;
	}

	public void setCredatedBy(String credatedBy) {
		this.credatedBy = credatedBy;
	}

	public Timestamp getCreatedOn() {
		return CreatedOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		CreatedOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Product getAssetType() {
		return assetType;
	}

	public void setAssetType(Product assetType) {
		this.assetType = assetType;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	
	

}
