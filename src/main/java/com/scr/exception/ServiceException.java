package com.scr.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scr.message.response.BaseResponse;
import com.scr.message.response.ResponseStatus;



public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = -7488442087294338294L;

	private final static Logger logger = LoggerFactory.getLogger(ServiceException.class);

    private BaseResponse response;
    
    public ServiceException(int code,String msg,String uuid) {
		super(msg);
		BaseResponse bResponse = new BaseResponse();
		ResponseStatus status = new ResponseStatus();
		status.setCode(code);
		status.setMessage(msg);
		status.setTransactionId(uuid);
		status.setTimestamp(System.currentTimeMillis());
		bResponse.setStatus(status);
		this.response = bResponse;
	}
    
    public ServiceException(BaseResponse response) {
		super(response.getStatus().getMessage());
		this.response = response;
	}
	
	public ServiceException(BaseResponse response,Throwable tw) {
		super(response.getStatus().getMessage());
		this.response = response;
		logger.info("EXP Created --> " + toString() ,tw);
	}
	
	public BaseResponse getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return "ServiceException [response=" + response + "]";
	}
}
