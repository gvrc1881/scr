package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "emp_id")
	private String empId;

	@Column(name = "name")
	private String name;

	@Column(name = "father_name")
	private String fatherName;

	@Column(name = "gender")
	private String gender;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "pf")
	private String pf;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "date_of_joining")
	private String dateOfJoining;

	@Column(name = "date_of_retirement")
	private String dateOfRetirement;

	@Column(name = "cug_phno")
	private String cugPhno;

	@Column(name = "adhar_no")
	private String adharNo;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "location")
	private String location;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@Column(name = "mail_id")
	private long mailId;

	@Column(name = "from_date")
	private Date fromDate;

	@Column(name = "thru_date")
	private Date thruDate;

	@Column(name = "is_password_set")
	private Boolean isPasswordSet;

	@Column(name = "post_id")
	private long PostId;

	@ManyToOne
	@JoinColumn(name="department_id",foreignKey=@ForeignKey(name = "fk_employee_department_id"))
	private Department departmentId;

	@ManyToOne
	@JoinColumn(name="designation_id",foreignKey=@ForeignKey(name = "fk_employee_designation_id"))
	private Designation designationId;

	@ManyToOne
	@JoinColumn(name="division_id",foreignKey=@ForeignKey(name = "fk_employee_division_id"))
	private Division divisionId;

	@ManyToOne
	@JoinColumn(name="repmgrid_id",foreignKey=@ForeignKey(name = "fk_employee_repmgrid_id"))
	private Employee repmgridId;

	/*@ManyToOne
	@JoinColumn(name="status_id",foreignKey=@ForeignKey(name = "fk_employee_status_id"))
	private Status statusId;*/

	@ManyToOne
	@JoinColumn(name="sub_department_id",foreignKey=@ForeignKey(name = "fk_employee_sub_department_id"))
	private SubDepartment subDepartmentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getDateOfRetirement() {
		return dateOfRetirement;
	}

	public void setDateOfRetirement(String dateOfRetirement) {
		this.dateOfRetirement = dateOfRetirement;
	}

	public String getCugPhno() {
		return cugPhno;
	}

	public void setCugPhno(String cugPhno) {
		this.cugPhno = cugPhno;
	}

	public String getAdharNo() {
		return adharNo;
	}

	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getThruDate() {
		return thruDate;
	}

	public void setThruDate(Date thruDate) {
		this.thruDate = thruDate;
	}

	public Boolean getIsPasswordSet() {
		return isPasswordSet;
	}

	public void setIsPasswordSet(Boolean isPasswordSet) {
		this.isPasswordSet = isPasswordSet;
	}

	public long getPostId() {
		return PostId;
	}

	public void setPostId(long postId) {
		PostId = postId;
	}

	public Department getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}

	public Designation getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Designation designationId) {
		this.designationId = designationId;
	}

	public Division getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Division divisionId) {
		this.divisionId = divisionId;
	}

	public Employee getRepmgridId() {
		return repmgridId;
	}

	public void setRepmgridId(Employee repmgridId) {
		this.repmgridId = repmgridId;
	}

/*	public Status getStatusId() {
		return statusId;
	}

	public void setStatusId(Status statusId) {
		this.statusId = statusId;
	}*/

	public SubDepartment getSubDepartmentId() {
		return subDepartmentId;
	}

	public void setSubDepartmentId(SubDepartment subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}

}
