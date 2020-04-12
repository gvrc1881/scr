/**
 * 
 */
package com.scr.message.request;

/**
 * @author venkat
 *
 */
public class JobTypeRequest extends BaseRequest{
	private Integer jobTypeId;
	private String jobTypeName;
	public Integer getJobTypeId() {
		return jobTypeId;
	}
	public void setJobTypeId(Integer jobTypeId) {
		this.jobTypeId = jobTypeId;
	}
	public String getJobTypeName() {
		return jobTypeName;
	}
	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}
	
}
