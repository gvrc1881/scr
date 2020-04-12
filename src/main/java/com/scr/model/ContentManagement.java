/**
 * 
 */
package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author vt1056
 *
 */
@Entity
@Table(name="content_management")
@NamedQuery(name="ContentManagement.findAll", query="SELECT d FROM ContentManagement d")
public class ContentManagement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")	 
	private Long Id;	
	
	@Column(name="commonFileId")
	private Long commonFileId;
	
	@Column(name="gen_ops")
	private String genOps;
	
	@Column(name="zonal")
	private String zonal;
	
	@Column(name="division")
	private String division;
	
	@Column(name="funUnit")
	private String funUnit;
	
	@Column(name="topic")
	private String topic;
	
	@Column(name="description")
	private String description;
	
	@Column(name="originalFileName")
	private String originalFileName;
	
	@Column(name="changeFileName")
	private String changeFileName;	
	
	@Column(name = "created_by")
	private Integer createdBy;
	@Column(name = "created_date")
	private Timestamp createdDate;
	@Column(name = "modified_by")
	private Integer modifiedBy;
	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	@Column(name = "assetTypeRlyId")
	private String assetTypeRlyId;
	
	@Column(name = "make")
	private String make;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "docCategory")
	private String docCategory;
	
	public ContentManagement() {
		super();		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	

	public String getGenOps() {
		return genOps;
	}

	public void setGenOps(String genOps) {
		this.genOps = genOps;
	}

	public String getZonal() {
		return zonal;
	}

	public void setZonal(String zonal) {
		this.zonal = zonal;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getFunUnit() {
		return funUnit;
	}

	public void setFunUnit(String funUnit) {
		this.funUnit = funUnit;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getChangeFileName() {
		return changeFileName;
	}

	public void setChangeFileName(String changeFileName) {
		this.changeFileName = changeFileName;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}	

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getCommonFileId() {
		return commonFileId;
	}

	public void setCommonFileId(Long commonFileId) {
		this.commonFileId = commonFileId;
	}

	public String getAssetTypeRlyId() {
		return assetTypeRlyId;
	}

	public void setAssetTypeRlyId(String assetTypeRlyId) {
		this.assetTypeRlyId = assetTypeRlyId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(String docCategory) {
		this.docCategory = docCategory;
	}
	
	
}
