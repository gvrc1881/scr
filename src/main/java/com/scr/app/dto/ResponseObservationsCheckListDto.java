package com.scr.app.dto;

import java.util.List;

public class ResponseObservationsCheckListDto {
	
	private List<ObservationsCheckListDto> observationsCheckListDtos;
	private Integer count;
	
	public List<ObservationsCheckListDto> getObservationsCheckListDtos() {
		return observationsCheckListDtos;
	}
	public void setObservationsCheckListDtos(
			List<ObservationsCheckListDto> observationsCheckListDtos) {
		this.observationsCheckListDtos = observationsCheckListDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}