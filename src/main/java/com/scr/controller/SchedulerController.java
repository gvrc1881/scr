/**
 * 
 */
package com.scr.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.mapper.EmailMapper;
import com.scr.message.request.RemarkRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.User;
import com.scr.services.RemarkService;
import com.scr.services.SchedulerService;
import com.scr.services.UserServices;
import com.scr.util.Constants;


/**
 * @author vt1056
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SchedulerController {
	
	static Logger logger = LogManager.getLogger(SchedulerController.class);
	
	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private RemarkService remarkService;
	
	@Autowired
	private EmailMapper emailMapper;
	
	@Autowired
	private UserServices userServices;
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/runAll", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseStatus getAllRoles() throws JSONException {
		ResponseStatus status = new ResponseStatus();
		try {
			logger.info("Scheduler....");
			status = schedulerService.runSchedulerJob();
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return status;
	}	
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/reRunWithRemark", method = RequestMethod.POST, headers = "Accept=application/json")
	@Async
	public ResponseStatus reRunWithRemark(@RequestBody RemarkRequest remarkRequest) throws JSONException {
		ResponseStatus status = new ResponseStatus();
		try {
			logger.info("Updating Remark Details");
			remarkRequest.setRunDate(new Date(Calendar.getInstance().getTime().getTime()));
			remarkService.saveRemark(remarkRequest);
			logger.info("Updated Remark Details");
			
			logger.info("Sending mail regarding rerun the scheduler");
			Optional<User> user = userServices.findById(new Long(remarkRequest.getRunBy()));
			ResponseStatus emailResponse =	emailMapper.sendRerunMailWithRemark(remarkRequest, user.get());
			if(emailResponse.getCode() == Constants.SUCCESS_CODE)
				logger.info("Sent mail successfully.");
			else
				logger.info("Sending email failed.");
			schedulerService.reRunByIdByType(remarkRequest.getRunTypeId(), remarkRequest.getJobId(), user.get(), remarkRequest.getProcessedDate());
			
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return status;
	}
	
	
}
