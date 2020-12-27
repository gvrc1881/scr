package com.scr.model;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "assistance")
@NamedQuery(name = "assistance.findAll", query = "SELECT ass FROM Assistance ass")
public class Assistance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "work_id", foreignKey = @ForeignKey(name = "fk_assistance_works"))
	private Works workId;
	
	@ManyToOne
	@JoinColumn(name = "work_group_id", foreignKey = @ForeignKey(name = "fk_assistance_work_group"))
	private WorkGroup workGroupId;
	
	@Column(name="type_of_assistance")
	private String typeOfAssistance;
	
	@Column(name="assistance")
	private String assistance;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="requested_by")
	private String requestedBy;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Temporal(TemporalType.DATE)
	private Date requestedDate;
	
	@Column(name="request_to")
	private String requestTo;
	
	@Column(name="response_by")
	private String responseBy;
	
	@Temporal(TemporalType.DATE)
	private Date responseDate;
	
	private String response;
	
	private String remark;
	
	@Column(name="status")
	private String status;
	
	@Column(name = "attachment")
	private String attachment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTypeOfAssistance() {
		return typeOfAssistance;
	}

	public void setTypeOfAssistance(String typeOfAssistance) {
		this.typeOfAssistance = typeOfAssistance;
	}

	public String getAssistance() {
		return assistance;
	}

	public void setAssistance(String assistance) {
		this.assistance = assistance;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getRequestTo() {
		return requestTo;
	}

	public void setRequestTo(String requestTo) {
		this.requestTo = requestTo;
	}

	public String getResponseBy() {
		return responseBy;
	}

	public void setResponseBy(String responseBy) {
		this.responseBy = responseBy;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "Assistance [id=" + id + ", workId=" + workId + ", workGroupId=" + workGroupId + ", typeOfAssistance="
				+ typeOfAssistance + ", assistance=" + assistance + ", createdBy=" + createdBy + ", requestedBy="
				+ requestedBy + ", updatedBy=" + updatedBy + ", requestedDate=" + requestedDate + ", requestTo="
				+ requestTo + ", responseBy=" + responseBy + ", responseDate=" + responseDate + ", response=" + response
				+ ", remark=" + remark + ", status=" + status + ", attachment=" + attachment + "]";
	}
	
	

}
