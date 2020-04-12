package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.sql.Timestamp;

/**
 * The persistent class for the repository database table.
 * 
 */
@Entity
@NamedQuery(name = "Repository.findAll", query = "SELECT r FROM Repository r")
public class Repository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repository_id")
	private Integer repositoryId;

	// bi-directional many-to-one association to User	
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by")
	@JsonBackReference(value="created_by")
	private User createdBy;

	// bi-directional many-to-one association to User
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "modified_by")
	@JsonBackReference(value="modified_by")
	private User modifiedBy;

	// bi-directional many-to-one association to Schedulejob
	/*
	 * @OneToMany(mappedBy = "repository")
	 * 
	 * @JsonIgnore private List<SchedulerJob> schedulerJobs;
	 */

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "is_active")
	private Integer isActive;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	@Column(name = "repository_code")
	private String repositoryCode;

	@Column(name = "repository_db_name")
	private String repositoryDbName;

	@Column(name = "repository_ip")
	private String repositoryIp;

	@Column(name = "repository_name")
	private String repositoryName;

	@Column(name = "repository_password")
	private String repositoryPassword;

	@Column(name = "repository_port")
	private String repositoryPort;

	@Column(name = "repository_user")
	private String repositoryUser;

	public Repository() {
	}
	
	

	public Repository(Integer repositoryId, User createdBy, User modifiedBy, 
			Timestamp createdDate, Integer isActive, Timestamp modifiedDate, String repositoryCode,
			String repositoryDbName, String repositoryIp, String repositoryName, String repositoryPassword,
			String repositoryPort, String repositoryUser) {
		super();
		this.repositoryId = repositoryId;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	//	this.schedulerJobs = schedulerJobs;
		this.createdDate = createdDate;
		this.isActive = isActive;
		this.modifiedDate = modifiedDate;
		this.repositoryCode = repositoryCode;
		this.repositoryDbName = repositoryDbName;
		this.repositoryIp = repositoryIp;
		this.repositoryName = repositoryName;
		this.repositoryPassword = repositoryPassword;
		this.repositoryPort = repositoryPort;
		this.repositoryUser = repositoryUser;
	}



	public Integer getRepositoryId() {
		return this.repositoryId;
	}

	public void setRepositoryId(Integer repositoryId) {
		this.repositoryId = repositoryId;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/*
	 * public List<SchedulerJob> getSchedulerJobs() { return schedulerJobs; }
	 * 
	 * public void setSchedulerJobs(List<SchedulerJob> schedulerJobs) {
	 * this.schedulerJobs = schedulerJobs; }
	 */

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRepositoryCode() {
		return this.repositoryCode;
	}

	public void setRepositoryCode(String repositoryCode) {
		this.repositoryCode = repositoryCode;
	}

	public String getRepositoryDbName() {
		return this.repositoryDbName;
	}

	public void setRepositoryDbName(String repositoryDbName) {
		this.repositoryDbName = repositoryDbName;
	}

	public String getRepositoryIp() {
		return this.repositoryIp;
	}

	public void setRepositoryIp(String repositoryIp) {
		this.repositoryIp = repositoryIp;
	}

	public String getRepositoryName() {
		return this.repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getRepositoryPassword() {
		return this.repositoryPassword;
	}

	public void setRepositoryPassword(String repositoryPassword) {
		this.repositoryPassword = repositoryPassword;
	}

	public String getRepositoryPort() {
		return this.repositoryPort;
	}

	public void setRepositoryPort(String repositoryPort) {
		this.repositoryPort = repositoryPort;
	}

	public String getRepositoryUser() {
		return this.repositoryUser;
	}

	public void setRepositoryUser(String repositoryUser) {
		this.repositoryUser = repositoryUser;
	}

	/*
	 * public SchedulerJob addSchedulejob(SchedulerJob schedulerJob) {
	 * getSchedulerJobs().add(schedulerJob); schedulerJob.setRepository(this);
	 * 
	 * return schedulerJob; }
	 * 
	 * public SchedulerJob removeSchedulejob(SchedulerJob schedulerJob) {
	 * getSchedulerJobs().remove(schedulerJob); schedulerJob.setRepository(null);
	 * 
	 * return schedulerJob; }
	 */


	/*
	 * @Override public String toString() { return "Repository [repositoryId=" +
	 * repositoryId + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy +
	 * ", createdDate=" + createdDate + ", isActive=" + isActive + ", modifiedDate="
	 * + modifiedDate + ", repositoryCode=" + repositoryCode + ", repositoryDbName="
	 * + repositoryDbName + ", repositoryIp=" + repositoryIp + ", repositoryName=" +
	 * repositoryName + ", repositoryPassword=" + repositoryPassword +
	 * ", repositoryPort=" + repositoryPort + ", repositoryUser=" + repositoryUser +
	 * "]"; }
	 */
	
	

}