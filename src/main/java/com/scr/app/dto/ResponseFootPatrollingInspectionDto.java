package com.scr.app.dto;

import java.util.List;

public class ResponseFootPatrollingInspectionDto
{

    private List<FootPatrollingInspectionDto> footPatrollingInspectionDtos;
    private Integer count;
	
    public List<FootPatrollingInspectionDto> getFootPatrollingInspectionDtos() {
		return footPatrollingInspectionDtos;
	}
	public void setFootPatrollingInspectionDtos(
			List<FootPatrollingInspectionDto> footPatrollingInspectionDtos) {
		this.footPatrollingInspectionDtos = footPatrollingInspectionDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

   


}