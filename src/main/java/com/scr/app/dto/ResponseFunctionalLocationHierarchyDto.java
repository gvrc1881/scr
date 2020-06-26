package com.scr.app.dto;

import java.util.List;

public class ResponseFunctionalLocationHierarchyDto {

	private List<FunctionalLocationHierarchyDto> functionalLocationHierarchyDtos;
	private Integer count;
	
	public List<FunctionalLocationHierarchyDto> getFunctionalLocationHierarchyDtos() {
		return functionalLocationHierarchyDtos;
	}
	public void setFunctionalLocationHierarchyDtos(
			List<FunctionalLocationHierarchyDto> functionalLocationHierarchyDtos) {
		this.functionalLocationHierarchyDtos = functionalLocationHierarchyDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
	
	
}