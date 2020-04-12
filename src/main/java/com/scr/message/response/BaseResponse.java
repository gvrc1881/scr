package com.scr.message.response;

import lombok.Data;

public @Data class BaseResponse {
	private ResponseStatus status;

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	
	
}