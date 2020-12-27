package com.scr.message.request;

import java.util.Date;
import com.scr.model.WorkGroup;
import com.scr.model.Works;

public class AssistanceRequest {
	
	private Long id;
    private Works workId;
	private WorkGroup workGroupId;
	private String typeOfAssistance;
	private String assistance;
	private String requestedBy;
	private Date requestedDate;
	private String requestTo;
	private String responseBy;
	private Date responseDate;
	private String response;
	private String remark;
	private String status;
	private String attachment;
	private String createdBy;
	private String updatedBy;

	
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
	
	
	

}
