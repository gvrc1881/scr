package com.scr.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "userJurisdiction")
@NamedQuery(name = "UserJurisdiction.findAll", query = "SELECT uj FROM UserJurisdiction uj")
public class UserJurisdiction {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_by")
	private Integer updatedBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@ManyToOne
	@JoinColumn(name = "work_id", foreignKey = @ForeignKey(name = "fk_user_jurisdiction_works"))
	private Works workId;

	@ManyToOne
	@JoinColumn(name = "work_group_id", foreignKey = @ForeignKey(name = "fk_user_jurisdiction_work_group"))
	private WorkGroup workGroupId;

	@ManyToOne
	@JoinColumn(name = "section", foreignKey = @ForeignKey(name = "fk_user_jurisdiction_section"))
	private WorkGroup section;
	
	@ManyToOne
	@JoinColumn(name="userId" , foreignKey = @ForeignKey(name = "fk_user_jurisdiction_user"))
	private User userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Works getWorkId() {
		return workId;
	}

	public void setWorkId(Works workId) {
		this.workId = workId;
	}

	public WorkGroup getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(WorkGroup workGroupId) {
		this.workGroupId = workGroupId;
	}

	public WorkGroup getSection() {
		return section;
	}

	public void setSection(WorkGroup section) {
		this.section = section;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserJurisdiction [id=" + id + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy="
				+ updatedBy + ", updatedOn=" + updatedOn + ", workId=" + workId + ", workGroupId=" + workGroupId
				+ ", section=" + section + ", userId=" + userId + "]";
	}

}
