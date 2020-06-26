package com.scr.app.dto;

import java.util.List;

public class ResponseFpMovementDto {

	private Integer count;
	private List<FpMovementDto> fpMovementDtos;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<FpMovementDto> getFpMovementDtos() {
		return fpMovementDtos;
	}

	public void setFpMovementDtos(List<FpMovementDto> fpMovementDtos) {
		this.fpMovementDtos = fpMovementDtos;
	}

}
