package com.scr.message.request;

public class InspectionFileDeleteRequest {
	private Long id;
	private String fileName;
	private String type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "InspectionFileDeleteRequest [id=" + id + ", fileName=" + fileName + ", type=" + type + "]";
	}
	

}
