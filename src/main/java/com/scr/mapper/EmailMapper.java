/**
 * 
 */
package com.scr.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


import com.scr.message.request.RemarkRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.MobileSMS;
import com.scr.model.SchedulerJob;
import com.scr.model.User;
import com.scr.services.EmailSenderService;

import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@Component
public class EmailMapper {
	
	@Value("${admin.mail}")
	private String adminMail;
	
	
	
	@Autowired
	private EmailSenderService emailSenderService;

	public ResponseStatus sendRerunMailWithRemark(RemarkRequest remarkRequest, User user) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(adminMail);
			mailMessage.setCc(user.getEmail());
			mailMessage.setSubject(Constants.RERUN_SCHEDULER_SUBJECT);
			mailMessage.setFrom("nairobley@gmail.com");
			mailMessage.setText(Helper.getRerunSchedulerText(user, remarkRequest));
			emailSenderService.sendEmail(mailMessage);
			responseStatus.setCode(Constants.SUCCESS_CODE);
		} catch (Exception e) {
			responseStatus.setCode(Constants.FAILURE_CODE);
		}
		return responseStatus;
	}

	public MobileSMS sendDBConnectionFailedMail(ResponseStatus connectionStatus, SchedulerJob job) {
		ResponseStatus responseStatus = new ResponseStatus();
		MobileSMS mobileSMS = new MobileSMS();
		try {
			
			  SimpleMailMessage mailMessage = new SimpleMailMessage();
			  mailMessage.setTo(adminMail);
			  mailMessage.setCc(job.getCreatedBy().getEmail());
			  mailMessage.setSubject(Constants.SCHEDULER_DB_CONNECTION_FAILED_SUBJECT);
			  mailMessage.setFrom("nairobley@gmail.com");
			  mailMessage.setText(Helper.getSchedulerDBConnectionFailedText(job,
			  connectionStatus)); emailSenderService.sendEmail(mailMessage);
			 
		//	log.info("Sending Email with Thymeleaf HTML Template Example");

			/*
			 * Mail mail = new Mail(); mail.setFrom("nairobley@gmail.com");
			 * mail.setTo(adminMail); mail.setCc(job.getCreatedBy().getEmail());
			 * mail.setSubject(Constants.SCHEDULER_DB_CONNECTION_FAILED_SUBJECT);
			 * 
			 * mobileSMS.setRecipient(adminMail);
			 * mobileSMS.setSendTime(Helper.getCurrentTimestamp());
			 * mobileSMS.setMessage(Helper.getSchedulerDBConnectionFailedText(job,
			 * connectionStatus)); mobileSMS.setMessageType("EMAIL");
			 * 
			 * Map model = Helper.getSchedulerDBConnectionFailedTextModel(job,
			 * connectionStatus); mail.setModel(model);
			 * 
			 * emailService.sendSimpleMessage(mail);
			 */
			//responseStatus.setCode(Constants.SUCCESS_CODE);
			mobileSMS.setRecipient(adminMail);
			mobileSMS.setSendTime(Helper.getCurrentTimestamp());
			mobileSMS.setMessage(Helper.getSchedulerDBConnectionFailedText(job,
			connectionStatus)); 
			mobileSMS.setMessageType("EMAIL");
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
