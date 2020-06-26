package com.scr.app.dto;

import java.util.List;

public class ResponseFootPatrollingSectionsDto {
	
	private List<FootPatrollingSectionsDto> footPatrollingSectionsDtos;
	private Integer count;
	
	public List<FootPatrollingSectionsDto> getFootPatrollingSectionsDtos() {
		return footPatrollingSectionsDtos;
	}
	public void setFootPatrollingSectionsDtos(
			List<FootPatrollingSectionsDto> footPatrollingSectionsDtos) {
		this.footPatrollingSectionsDtos = footPatrollingSectionsDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
}