package com.scr.app.dto;

import java.util.List;

public class ResponseObservationCategoriesDto {
	
	private List<ObservationCategoriesDto> observationCategoriesDtos;
	private Integer count;
	
	public List<ObservationCategoriesDto> getObservationCategoriesDtos() {
		return observationCategoriesDtos;
	}
	public void setObservationCategoriesDtos(List<ObservationCategoriesDto> observationCategoriesDtos) {
		this.observationCategoriesDtos = observationCategoriesDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}