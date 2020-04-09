package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@Table(name = "person" , uniqueConstraints={@UniqueConstraint(name = "old_pk_person_uniq", columnNames ={"data_div", "party_id"})})
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	private Date birthDate;

	@Column(name="card_id")
	private String cardId;

	private String comments;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Temporal(TemporalType.DATE)
	@Column(name="deceased_date")
	private Date deceasedDate;

	@Column(name="employment_status_enum_id")
	private String employmentStatusEnumId;

	@Column(name="existing_customer")
	private String existingCustomer;

	@Column(name="father_name")
	private String fatherName;

	@Column(name="first_name")
	private String firstName;

	@Column(name="first_name_local")
	private String firstNameLocal;

	private String gender;

	private double height;

	@Column(name="husband_name")
	private String husbandName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="last_name_local")
	private String lastNameLocal;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="marital_status")
	private String maritalStatus;

	@Column(name="member_id")
	private String memberId;

	@Column(name="middle_name")
	private String middleName;

	@Column(name="middle_name_local")
	private String middleNameLocal;

	@Column(name="months_with_employer")
	private BigDecimal monthsWithEmployer;

	@Column(name="mother_name")
	private String motherName;

	@Column(name="mothers_maiden_name")
	private String mothersMaidenName;

	private String nickname;

	private String occupation;

	@Column(name="other_local")
	private String otherLocal;

	@Column(name="party_id")
	private String partyId;

	@Temporal(TemporalType.DATE)
	@Column(name="passport_expire_date")
	private Date passportExpireDate;

	@Column(name="passport_issued_in")
	private String passportIssuedIn;

	@Column(name="passport_number")
	private String passportNumber;

	@Column(name="personal_title")
	private String personalTitle;

	@Column(name="physically_challenged")
	private String physicallyChallenged;

	@Column(name="residence_status_enum_id")
	private String residenceStatusEnumId;

	private String salutation;

	@Column(name="social_security_number")
	private String socialSecurityNumber;

	private String suffix;

	@Column(name="total_years_work_experience")
	private double totalYearsWorkExperience;

	private double weight;

	@Column(name="years_with_employer")
	private BigDecimal yearsWithEmployer;

	public Person() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCreatedStamp() {
		return this.createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return this.createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public Date getDeceasedDate() {
		return this.deceasedDate;
	}

	public void setDeceasedDate(Date deceasedDate) {
		this.deceasedDate = deceasedDate;
	}

	public String getEmploymentStatusEnumId() {
		return this.employmentStatusEnumId;
	}

	public void setEmploymentStatusEnumId(String employmentStatusEnumId) {
		this.employmentStatusEnumId = employmentStatusEnumId;
	}

	public String getExistingCustomer() {
		return this.existingCustomer;
	}

	public void setExistingCustomer(String existingCustomer) {
		this.existingCustomer = existingCustomer;
	}

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstNameLocal() {
		return this.firstNameLocal;
	}

	public void setFirstNameLocal(String firstNameLocal) {
		this.firstNameLocal = firstNameLocal;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getHusbandName() {
		return this.husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastNameLocal() {
		return this.lastNameLocal;
	}

	public void setLastNameLocal(String lastNameLocal) {
		this.lastNameLocal = lastNameLocal;
	}

	public Timestamp getLastUpdatedStamp() {
		return this.lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return this.lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMiddleNameLocal() {
		return this.middleNameLocal;
	}

	public void setMiddleNameLocal(String middleNameLocal) {
		this.middleNameLocal = middleNameLocal;
	}

	public BigDecimal getMonthsWithEmployer() {
		return this.monthsWithEmployer;
	}

	public void setMonthsWithEmployer(BigDecimal monthsWithEmployer) {
		this.monthsWithEmployer = monthsWithEmployer;
	}

	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMothersMaidenName() {
		return this.mothersMaidenName;
	}

	public void setMothersMaidenName(String mothersMaidenName) {
		this.mothersMaidenName = mothersMaidenName;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getOtherLocal() {
		return this.otherLocal;
	}

	public void setOtherLocal(String otherLocal) {
		this.otherLocal = otherLocal;
	}

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public Date getPassportExpireDate() {
		return this.passportExpireDate;
	}

	public void setPassportExpireDate(Date passportExpireDate) {
		this.passportExpireDate = passportExpireDate;
	}

	public String getPassportIssuedIn() {
		return this.passportIssuedIn;
	}

	public void setPassportIssuedIn(String passportIssuedIn) {
		this.passportIssuedIn = passportIssuedIn;
	}

	public String getPassportNumber() {
		return this.passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPersonalTitle() {
		return this.personalTitle;
	}

	public void setPersonalTitle(String personalTitle) {
		this.personalTitle = personalTitle;
	}

	public String getPhysicallyChallenged() {
		return this.physicallyChallenged;
	}

	public void setPhysicallyChallenged(String physicallyChallenged) {
		this.physicallyChallenged = physicallyChallenged;
	}

	public String getResidenceStatusEnumId() {
		return this.residenceStatusEnumId;
	}

	public void setResidenceStatusEnumId(String residenceStatusEnumId) {
		this.residenceStatusEnumId = residenceStatusEnumId;
	}

	public String getSalutation() {
		return this.salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getSocialSecurityNumber() {
		return this.socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public double getTotalYearsWorkExperience() {
		return this.totalYearsWorkExperience;
	}

	public void setTotalYearsWorkExperience(double totalYearsWorkExperience) {
		this.totalYearsWorkExperience = totalYearsWorkExperience;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public BigDecimal getYearsWithEmployer() {
		return this.yearsWithEmployer;
	}

	public void setYearsWithEmployer(BigDecimal yearsWithEmployer) {
		this.yearsWithEmployer = yearsWithEmployer;
	}

}
