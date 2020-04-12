/**
 * 
 */
package com.scr.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.scr.message.response.ResponseStatus;
import com.scr.model.MobileSMS;
import com.scr.model.SchedulerJob;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@Component
public class SMSMapper {
	@Value("${scr.mobile.url}")
	private String url;
	
	@Value("${scr.mobile.username}")
	private String userName;
	
	@Value("${scr.mobile.password}")
	private String password;
	
	@Value("${scr.mobile.from}")
	private String from;

	public MobileSMS sendSMS(ResponseStatus connectionStatus, SchedulerJob job) {		
		ResponseStatus responseStatus = new ResponseStatus();
		MobileSMS mobileSMS = new MobileSMS();
		try {
			String message = Helper.getSchedulerDBConnectionFailedText(job, connectionStatus);
			final String uri = url+ "username="+userName+"&password="+password+"&to="+job.getCreatedBy().getPhone()+"&from="+from+
					"&message="+ message +"";
	
			mobileSMS.setRecipient(job.getCreatedBy().getPhone());
			mobileSMS.setSendTime(Helper.getCurrentTimestamp());
			mobileSMS.setMessage(message);
			mobileSMS.setMessageType("MOBILE");
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(uri, String.class);			
			responseStatus.setCode(Constants.SUCCESS_CODE);
			responseStatus.setMessage(result);
			
			mobileSMS.setReceivedTime(Helper.currentTimeStamp());
			mobileSMS.setSmsStatus(String.valueOf(Constants.SUCCESS_CODE));
		} catch (Exception e) {
			responseStatus.setCode(Constants.FAILURE_CODE);
			mobileSMS.setFailedReason(e.getMessage());
			mobileSMS.setSmsStatus(String.valueOf(Constants.FAILURE_CODE));
		}
		return mobileSMS;
	}
}
