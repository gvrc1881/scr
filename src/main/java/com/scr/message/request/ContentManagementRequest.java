/**
 * 
 */
package com.scr.message.request;

/**
 * @author vt1056
 *
 */
public class ContentManagementRequest {
	private String GenOps;
	private String zonal;
	private String divisionCode;
	private String funUnit;
	private String topic;
	private Long id;
	private Integer modifiedBy;
	private String description;
	private String oldDescription;
	private String createdBy;
	private String createdDate;
	private Long commonFileId;
	
	public String getGenOps() {
		return GenOps;
	}
	public void setGenOps(String genOps) {
		GenOps = genOps;
	}
	public String getZonal() {
		return zonal;
	}
	public void setZonal(String zonal) {
		this.zonal = zonal;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getDescription() {
		return description;
	}	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOldDescription() {
		return oldDescription;
	}
	public void setOldDescription(String oldDescription) {
		this.oldDescription = oldDescription;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Long getCommonFileId() {
		return commonFileId;
	}
	public void setCommonFileId(Long commonFileId) {
		this.commonFileId = commonFileId;
	}
	
	
}
