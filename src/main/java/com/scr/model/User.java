package com.scr.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT r FROM User r")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @NotBlank
   // @Size(min=3, max = 50)
    private String user_id;

    //@NotBlank
   // @Size(min=3, max = 50)
    private String username;

	/*
	 * @NaturalId
	 * 
	 * @NotBlank
	 * 
	 * @Size(max = 50)
	 * 
	 * @Email
	 */
    private String email;

   // @NotBlank
  //  @Size(min=6, max = 100)
    private String password;    
    
	
	private String first_name;	
	private String last_name;	
	private Integer gender;	
	private Date day_of_birth;	
	private Integer department;	
	private String designation;	
	private String address;	
	private String phone;		
	private Date issue_date;	
	private Date expire_date;
	private Integer privilege_id;	
	private Integer password_reset;	
	@Column(name="created_by")
	private Integer createdBy;	
	private Date created_date;	
	@Column(name="modified_by")
	private Integer modifiedBy;	
	private Date modified_date;	
	@Column(name = "status_id")
	private int statusId;
    private Integer roleTypeId;    
    private String divisionCode;

	//bi-directional many-to-one association to Jobstatus
    @JsonIgnore
	@OneToMany(mappedBy="createdBy", fetch = FetchType.EAGER)
	private List<JobStatus> jobStatusesCreatedBy;

	//bi-directional many-to-one association to Jobstatus
    @JsonIgnore
	@OneToMany(mappedBy="modifiedBy", fetch = FetchType.EAGER)
	private List<JobStatus> jobStatusesModifiedBy;

	//bi-directional many-to-one association to Jobtype
    @JsonIgnore
	@OneToMany(mappedBy="createdBy", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
	private List<JobType> jobTypesCreatedBy = new ArrayList<JobType>();

	//bi-directional many-to-one association to Jobtype
    @JsonIgnore
	@OneToMany(mappedBy="modifiedBy", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
	private List<JobType> jobTypesModifiedBy = new ArrayList<JobType>();	

	//bi-directional many-to-one association to Repositorytype
    @JsonIgnore
	@OneToMany(mappedBy="createdBy", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
	private List<Repository> repositoryCreatedBy = new ArrayList<Repository>();

	//bi-directional many-to-one association to Repositorytype
    @JsonIgnore
	@OneToMany(mappedBy="modifiedBy", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
	private List<Repository> repositoryModifiedBy = new ArrayList<Repository>();

	//bi-directional many-to-one association to Schedulejob
	@JsonIgnore
	@OneToMany(mappedBy="createdBy", fetch = FetchType.EAGER)
	private List<SchedulerJob> schedulerJobsCreatedBy;

	//bi-directional many-to-one association to Schedulejob
	@JsonIgnore
	@OneToMany(mappedBy="modifiedBy", fetch = FetchType.EAGER)
	private List<SchedulerJob> schedulerJobsModifiedBy;

	//bi-directional many-to-one association to Timeinterval	
	@JsonIgnore
	@OneToMany(mappedBy="createdBy", fetch = FetchType.EAGER)
	private List<TimeInterval> timeIntervalsCreatedBy;

	//bi-directional many-to-one association to Timeinterval
	@JsonIgnore
	@OneToMany(mappedBy="modifiedBy", fetch = FetchType.EAGER)
	private List<TimeInterval> timeIntervalsModifiedBy;
	

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();*/
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"), 
    	inverseJoinColumns = @JoinColumn(name = "master_role_id", referencedColumnName="id"))
    private Set<MasterRoles> masterRoles = new HashSet<>();

    public User() {}

	/*
	 * public User(Long id, @NotBlank @Size(min = 3, max = 50) String user_id,
	 * 
	 * @NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(max =
	 * 50) @Email String email,
	 * 
	 * @NotBlank @Size(min = 6, max = 100) String password, String first_name,
	 * String last_name, int gender, Date day_of_birth, int department, String
	 * designation, String address, String phone, Date issue_date, Date expire_date,
	 * int privilege_id, int password_reset, Integer created_by, Date created_date,
	 * int modified_by, Date modified_date, int status_id, Set<Role> roles) {
	 * super(); this.id = id; this.user_id = user_id; this.username = username;
	 * this.email = email; this.password = password; this.first_name = first_name;
	 * this.last_name = last_name; this.gender = gender; this.day_of_birth =
	 * day_of_birth; this.department = department; this.designation = designation;
	 * this.address = address; this.phone = phone; this.issue_date = issue_date;
	 * this.expire_date = expire_date; this.privilege_id = privilege_id;
	 * this.password_reset = password_reset; this.createdBy = created_by;
	 * this.created_date = created_date; this.modifiedBy = modified_by;
	 * this.modified_date = modified_date; this.statusId = status_id; this.roles =
	 * roles; }
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getDay_of_birth() {
		return day_of_birth;
	}

	public void setDay_of_birth(Date day_of_birth) {
		this.day_of_birth = day_of_birth;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}

	public Date getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}

	public Integer getPrivilege_id() {
		return privilege_id;
	}

	public void setPrivilege_id(Integer privilege_id) {
		this.privilege_id = privilege_id;
	}

	public Integer getPassword_reset() {
		return password_reset;
	}

	public void setPassword_reset(Integer password_reset) {
		this.password_reset = password_reset;
	}

	public Integer getCreated_by() {
		return createdBy;
	}

	public void setCreated_by(Integer created_by) {
		this.createdBy = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Integer getModified_by() {
		return modifiedBy;
	}

	public void setModified_by(Integer modified_by) {
		this.modifiedBy = modified_by;
	}

	public Date getModified_date() {
		return modified_date;
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	/*public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}*/
	
	
	
	
	public List<JobStatus> getJobStatusesCreatedBy() {
		return this.jobStatusesCreatedBy;
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
	
	public Set<MasterRoles> getMasterRoles() {
		return masterRoles;
	}

	public void setMasterRoles(Set<MasterRoles> masterRoles) {
		this.masterRoles = masterRoles;
	}

	/*public MasterRoles getMasterRoles() {
		return masterRoles;
	}

	public void setMasterRoles(MasterRoles masterRoles) {
		this.masterRoles = masterRoles;
	}*/

	public void setJobStatusesCreatedBy(List<JobStatus> jobStatusesCreatedBy) {
		this.jobStatusesCreatedBy = jobStatusesCreatedBy;
	}

	public void setTimeIntervalsModifiedBy(List<TimeInterval> timeIntervalsModifiedBy) {
		this.timeIntervalsModifiedBy = timeIntervalsModifiedBy;
	}

	public void setJobStatucsesCtreatedBy(List<JobStatus> jobStatusesCreatedBy) {
		this.jobStatusesCreatedBy = jobStatusesCreatedBy;
	}

	public JobStatus addJobStatusesCreatedBy(JobStatus jobStatusesCreatedBy) {
		getJobStatusesCreatedBy().add(jobStatusesCreatedBy);
		jobStatusesCreatedBy.setCreatedBy(this);

		return jobStatusesCreatedBy;
	}

	public JobStatus removeJobStatusesCreatedBy(JobStatus jobStatusesCreatedBy) {
		getJobStatusesCreatedBy().remove(jobStatusesCreatedBy);
		jobStatusesCreatedBy.setCreatedBy(null);

		return jobStatusesCreatedBy;
	}

	public List<JobStatus> getJobStatusesModifiedBy() {
		return this.jobStatusesModifiedBy;
	}

	public void setJobStatusesModifiedBy(List<JobStatus> jobStatusesModifiedBy) {
		this.jobStatusesModifiedBy = jobStatusesModifiedBy;
	}

	public JobStatus addJobStatusesModified(JobStatus jobStatusesModifiedBy) {
		getJobStatusesModifiedBy().add(jobStatusesModifiedBy);
		jobStatusesModifiedBy.setModifiedBy(this);

		return jobStatusesModifiedBy;
	}

	public JobStatus removeJobStatusesModifiedBy(JobStatus jobStatusesModifiedBy) {
		getJobStatusesModifiedBy().remove(jobStatusesModifiedBy);
		jobStatusesModifiedBy.setModifiedBy(null);

		return jobStatusesModifiedBy;
	}

	public List<JobType> getJobTypesCreatedBy() {
		return this.jobTypesCreatedBy;
	}

	public void setJobTypesCreatedBy(List<JobType> jobTypesCreatedBy) {
		this.jobTypesCreatedBy = jobTypesCreatedBy;
	}

	public JobType addJobTypesCreatedBy(JobType jobTypesCreatedBy) {
		getJobTypesCreatedBy().add(jobTypesCreatedBy);
		jobTypesCreatedBy.setCreatedBy(this);

		return jobTypesCreatedBy;
	}

	public JobType removeJobTypesCreatedBy(JobType jobTypesCreatedBy) {
		getJobTypesCreatedBy().remove(jobTypesCreatedBy);
		jobTypesCreatedBy.setCreatedBy(null);

		return jobTypesCreatedBy;
	}

	public List<JobType> getJobTypesModifiedBy() {
		return this.jobTypesModifiedBy;
	}

	public void setJobTypesModifiedBy(List<JobType> jobTypesModifiedBy) {
		this.jobTypesModifiedBy = jobTypesModifiedBy;
	}

	public JobType addJobTypesModifiedBy(JobType jobTypesModifiedBy) {
		getJobTypesModifiedBy().add(jobTypesModifiedBy);
		jobTypesModifiedBy.setModifiedBy(this);

		return jobTypesModifiedBy;
	}

	public JobType removeJobTypesModifiedBy(JobType jobTypesModifiedBy) {
		getJobTypesModifiedBy().remove(jobTypesModifiedBy);
		jobTypesModifiedBy.setModifiedBy(null);

		return jobTypesModifiedBy;
	}

	
	public List<Repository> getRepositoryCreatedBy() {
		return this.repositoryCreatedBy;
	}

	public void setRepositoryCreatedBy(List<Repository> repositoryTypesCreatedBy) {
		this.repositoryCreatedBy = repositoryTypesCreatedBy;
	}

	public Repository addRepositoryCreatedBy(Repository repositoryTypesCreatedBy) {
		getRepositoryCreatedBy().add(repositoryTypesCreatedBy);
		repositoryTypesCreatedBy.setCreatedBy(this);

		return repositoryTypesCreatedBy;
	}

	public Repository removeRepositoryCreatedBy(Repository repositoryTypesCreatedBy) {
		getRepositoryCreatedBy().remove(repositoryTypesCreatedBy);
		repositoryTypesCreatedBy.setCreatedBy(null);

		return repositoryTypesCreatedBy;
	}

	public List<Repository> getRepositoryModifiedBy() {
		return this.repositoryModifiedBy;
	}

	public void setRepositoryModifiedBy(List<Repository> repositoryTypesModifiedBy) {
		this.repositoryModifiedBy = repositoryTypesModifiedBy;
	}

	public Repository addRepositoryModifiedBy(Repository repositoryTypesModifiedBy) {
		getRepositoryModifiedBy().add(repositoryTypesModifiedBy);
		repositoryTypesModifiedBy.setModifiedBy(this);

		return repositoryTypesModifiedBy;
	}

	public Repository removeRepositoryModifiedBy(Repository repositoryTypesModifiedBy) {
		getRepositoryModifiedBy().remove(repositoryTypesModifiedBy);
		repositoryTypesModifiedBy.setModifiedBy(null);

		return repositoryTypesModifiedBy;
	}

	public List<SchedulerJob> getSchedulerJobsCreatedBy() {
		return this.schedulerJobsCreatedBy;
	}

	public void setSchedulerJobsCreatedBy(List<SchedulerJob> schedulerJobsCreatedBy) {
		this.schedulerJobsCreatedBy = schedulerJobsCreatedBy;
	}

	public SchedulerJob addSchedulerJobsCreatedBy(SchedulerJob schedulerJobsCreatedBy) {
		getSchedulerJobsCreatedBy().add(schedulerJobsCreatedBy);
		schedulerJobsCreatedBy.setCreatedBy(this);

		return schedulerJobsCreatedBy;
	}

	public SchedulerJob removeSchedulerJobsCreatedBy(SchedulerJob schedulerJobsCreatedBy) {
		getSchedulerJobsCreatedBy().remove(schedulerJobsCreatedBy);
		schedulerJobsCreatedBy.setCreatedBy(null);

		return schedulerJobsCreatedBy;
	}

	public List<SchedulerJob> getSchedulerJobsModifiedBy() {
		return this.schedulerJobsModifiedBy;
	}

	public void setSchedulerJobsModifiedBy(List<SchedulerJob> schedulerJobsModifiedBy) {
		this.schedulerJobsModifiedBy = schedulerJobsModifiedBy;
	}

	public SchedulerJob addSchedulerJobsModifiedBy(SchedulerJob schedulerJobsModifiedBy) {
		getSchedulerJobsModifiedBy().add(schedulerJobsModifiedBy);
		schedulerJobsModifiedBy.setModifiedBy(this);

		return schedulerJobsModifiedBy;
	}

	public SchedulerJob removeSchedulerJobsModifiedBy(SchedulerJob schedulerJobsModifiedBy) {
		getSchedulerJobsModifiedBy().remove(schedulerJobsModifiedBy);
		schedulerJobsModifiedBy.setModifiedBy(null);

		return schedulerJobsModifiedBy;
	}

	public List<TimeInterval> getTimeIntervalsCreatedBy() {
		return this.timeIntervalsCreatedBy;
	}

	public void setTimeIntervalsCreatedBy(List<TimeInterval> timeIntervalsCreatedBy) {
		this.timeIntervalsCreatedBy = timeIntervalsCreatedBy;
	}

	public TimeInterval addTimeIntervalsCreatedBy(TimeInterval timeIntervalsCreatedBy) {
		getTimeIntervalsCreatedBy().add(timeIntervalsCreatedBy);
		timeIntervalsCreatedBy.setCreatedBy(this);

		return timeIntervalsCreatedBy;
	}

	public TimeInterval removeTimeintervalsCreatedBy(TimeInterval timeIntervalsCreatedBy) {
		getTimeIntervalsCreatedBy().remove(timeIntervalsCreatedBy);
		timeIntervalsCreatedBy.setCreatedBy(null);

		return timeIntervalsCreatedBy;
	}

	public List<TimeInterval> getTimeIntervalsModifiedBy() {
		return this.timeIntervalsModifiedBy;
	}

	public void setTimeintervalsModifiedBy(List<TimeInterval> timeIntervalsModifiedBy) {
		this.timeIntervalsModifiedBy = timeIntervalsModifiedBy;
	}

	public TimeInterval addTimeintervalsModifiedBy(TimeInterval timeIntervalsModifiedBy) {
		getTimeIntervalsModifiedBy().add(timeIntervalsModifiedBy);
		timeIntervalsModifiedBy.setModifiedBy(this);

		return timeIntervalsModifiedBy;
	}

	public TimeInterval removeTimeintervalsModifiedBy(TimeInterval timeIntervalsModifiedBy) {
		getTimeIntervalsModifiedBy().remove(timeIntervalsModifiedBy);
		timeIntervalsModifiedBy.setModifiedBy(null);

		return timeIntervalsModifiedBy;
	}

	public Integer getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", user_id=" + user_id + ", username=" + username + ", email=" + email + ", password="
				+ password + ", first_name=" + first_name + ", last_name=" + last_name + ", gender=" + gender
				+ ", day_of_birth=" + day_of_birth + ", department=" + department + ", designation=" + designation
				+ ", address=" + address + ", phone=" + phone + ", issue_date=" + issue_date + ", expire_date="
				+ expire_date + ", privilege_id=" + privilege_id + ", password_reset=" + password_reset + ", createdBy="
				+ createdBy + ", created_date=" + created_date + ", modifiedBy=" + modifiedBy + ", modified_date="
				+ modified_date + ", statusId=" + statusId + ", roleTypeId=" + roleTypeId + ", divisionCode="
				+ divisionCode + ", jobStatusesCreatedBy=" + jobStatusesCreatedBy + ", jobStatusesModifiedBy="
				+ jobStatusesModifiedBy + ", jobTypesCreatedBy=" + jobTypesCreatedBy + ", jobTypesModifiedBy="
				+ jobTypesModifiedBy + ", repositoryCreatedBy=" + repositoryCreatedBy + ", repositoryModifiedBy="
				+ repositoryModifiedBy + ", schedulerJobsCreatedBy=" + schedulerJobsCreatedBy
				+ ", schedulerJobsModifiedBy=" + schedulerJobsModifiedBy + ", timeIntervalsCreatedBy="
				+ timeIntervalsCreatedBy + ", timeIntervalsModifiedBy=" + timeIntervalsModifiedBy + ", masterRoles="
				+ masterRoles + ", getId()=" + getId() + ", getUser_id()=" + getUser_id() + ", getUsername()="
				+ getUsername() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword()
				+ ", getFirst_name()=" + getFirst_name() + ", getLast_name()=" + getLast_name() + ", getGender()="
				+ getGender() + ", getDay_of_birth()=" + getDay_of_birth() + ", getDepartment()=" + getDepartment()
				+ ", getDesignation()=" + getDesignation() + ", getAddress()=" + getAddress() + ", getPhone()="
				+ getPhone() + ", getIssue_date()=" + getIssue_date() + ", getExpire_date()=" + getExpire_date()
				+ ", getPrivilege_id()=" + getPrivilege_id() + ", getPassword_reset()=" + getPassword_reset()
				+ ", getCreated_by()=" + getCreated_by() + ", getCreated_date()=" + getCreated_date()
				+ ", getModified_by()=" + getModified_by() + ", getModified_date()=" + getModified_date()
				+ ", getStatusId()=" + getStatusId() + ", getJobStatusesCreatedBy()=" + getJobStatusesCreatedBy()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getModifiedBy()=" + getModifiedBy() + ", getMasterRoles()="
				+ getMasterRoles() + ", getJobStatusesModifiedBy()=" + getJobStatusesModifiedBy()
				+ ", getJobTypesCreatedBy()=" + getJobTypesCreatedBy() + ", getJobTypesModifiedBy()="
				+ getJobTypesModifiedBy() + ", getRepositoryCreatedBy()=" + getRepositoryCreatedBy()
				+ ", getRepositoryModifiedBy()=" + getRepositoryModifiedBy() + ", getSchedulerJobsCreatedBy()="
				+ getSchedulerJobsCreatedBy() + ", getSchedulerJobsModifiedBy()=" + getSchedulerJobsModifiedBy()
				+ ", getTimeIntervalsCreatedBy()=" + getTimeIntervalsCreatedBy() + ", getTimeIntervalsModifiedBy()="
				+ getTimeIntervalsModifiedBy() + ", getRoleTypeId()=" + getRoleTypeId() + ", getDivisionCode()="
				+ getDivisionCode() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	

	
	
	
	/*
	 * @Override public String toString() { return "User [id=" + id + ", user_id=" +
	 * user_id + ", username=" + username + ", email=" + email + ", password=" +
	 * password + ", first_name=" + first_name + ", last_name=" + last_name +
	 * ", gender=" + gender + ", day_of_birth=" + day_of_birth + ", department=" +
	 * department + ", designation=" + designation + ", address=" + address +
	 * ", phone=" + phone + ", issue_date=" + issue_date + ", expire_date=" +
	 * expire_date + ", privilege_id=" + privilege_id + ", password_reset=" +
	 * password_reset + ", createdBy=" + createdBy + ", created_date=" +
	 * created_date + ", modifiedBy=" + modifiedBy + ", modified_date=" +
	 * modified_date + ", statusId=" + statusId + ", jobStatusesCreatedBy=" +
	 * jobStatusesCreatedBy + ", jobStatusesModifiedBy=" + jobStatusesModifiedBy +
	 * ", jobTypesCreatedBy=" + jobTypesCreatedBy + ", jobTypesModifiedBy=" +
	 * jobTypesModifiedBy + ", repositoryCreatedBy=" + repositoryCreatedBy +
	 * ", repositoryModifiedBy=" + repositoryModifiedBy +
	 * ", schedulerJobsCreatedBy=" + schedulerJobsCreatedBy +
	 * ", schedulerJobsModifiedBy=" + schedulerJobsModifiedBy +
	 * ", timeIntervalsCreatedBy=" + timeIntervalsCreatedBy +
	 * ", timeIntervalsModifiedBy=" + timeIntervalsModifiedBy + ", roles=" + roles +
	 * "]"; }
	 */


   
}