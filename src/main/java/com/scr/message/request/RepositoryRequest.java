/**
 * 
 */
package com.scr.message.request;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author venkat
 *
 */
public @Data class RepositoryRequest {
	
	private Integer repositoryId;
	private Integer createdBy;
	private Timestamp createdDate;
	private Integer isActive;
	private Integer modifiedBy;
	private Timestamp modifiedDate;
	private String repositoryCode;
	private String repositoryDbName;
	private String repositoryIp;
	private String repositoryName;
	private String repositoryPassword;
	private String repositoryPort;
	private String repositoryUser;
	public Integer getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(Integer repositoryId) {
		this.repositoryId = repositoryId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getRepositoryCode() {
		return repositoryCode;
	}
	public void setRepositoryCode(String repositoryCode) {
		this.repositoryCode = repositoryCode;
	}
	public String getRepositoryDbName() {
		return repositoryDbName;
	}
	public void setRepositoryDbName(String repositoryDbName) {
		this.repositoryDbName = repositoryDbName;
	}
	public String getRepositoryIp() {
		return repositoryIp;
	}
	public void setRepositoryIp(String repositoryIp) {
		this.repositoryIp = repositoryIp;
	}
	public String getRepositoryName() {
		return repositoryName;
	}
	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}
	public String getRepositoryPassword() {
		return repositoryPassword;
	}
	public void setRepositoryPassword(String repositoryPassword) {
		this.repositoryPassword = repositoryPassword;
	}
	public String getRepositoryPort() {
		return repositoryPort;
	}
	public void setRepositoryPort(String repositoryPort) {
		this.repositoryPort = repositoryPort;
	}
	public String getRepositoryUser() {
		return repositoryUser;
	}
	public void setRepositoryUser(String repositoryUser) {
		this.repositoryUser = repositoryUser;
	}
	
	
}
