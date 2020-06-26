package com.scr.app.dto;

import java.util.List;

public class ResponseInspectionTypeDto {

	private List<InspectionTypeDto> inspectionTypeDtos;
	private Integer count;
	
	public List<InspectionTypeDto> getInspectionTypeDtos() {
		return inspectionTypeDtos;
	}
	public void setInspectionTypeDtos(List<InspectionTypeDto> inspectionTypeDtos) {
		this.inspectionTypeDtos = inspectionTypeDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
	
	
}

