/**
 * 
 */
package com.scr.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.scr.message.request.RemarkRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.SchedulerJob;
import com.scr.model.User;

/**
 * @author vt1056
 *
 */

public class Helper {

	private static final Logger logger = Logger.getLogger(Helper.class);

	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static DateFormat dateFormatNoTime = new SimpleDateFormat("yyyy-MM-dd");

	public static ResponseStatus findResponseStatus(String statusMessage, Integer statusCode) {
		ResponseStatus status = new ResponseStatus();
		status.setCode(statusCode);
		status.setMessage(statusMessage);
		status.setTransactionId(UUID.randomUUID().toString());
		status.setTimestamp(System.currentTimeMillis());
		return status;
	}

	public static java.sql.Date convertStringToDate(String date) {
		return java.sql.Date.valueOf(date);
	}

	public static String convertDateToString(Date date) {
		return date == null ? dateFormatNoTime.format(new Date()) : dateFormatNoTime.format(date);
	}

	public static String currentTimeStampWithString() {
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static Timestamp convertStringToTimestamp(String dateString) {
		logger.info("Input : " + dateString.toString());
		if (dateString != null && !dateString.isEmpty() && !dateString.trim().equalsIgnoreCase("null")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			@SuppressWarnings("deprecation")
			Date date = new Date(dateString);
			String DateToStoreInDataBase = sdf.format(date);
			Timestamp ts = Timestamp.valueOf(DateToStoreInDataBase);
			return ts;
		} else {
			return null;
		}
	}
	
	public static Timestamp convertStringToTimestampec(String dateString) {
		logger.info("Input : " + dateString.toString());
		if (dateString != null && !dateString.isEmpty() && !dateString.trim().equalsIgnoreCase("null")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
			DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("CDT"));
			Date date = null;
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String DateToStoreInDataBase = destDf.format(date);
			Timestamp ts = Timestamp.valueOf(DateToStoreInDataBase);
			return ts;
		} else {
			return null;
		}
	}
	
	public static Timestamp currentTimeStamp() {
		return new Timestamp(Calendar.getInstance().getTime().getTime());
	}

	public static String currentTime() {
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String currentDate() {
		Date date = new Date();
		return dateFormatNoTime.format(date);
	}

	public static String getStringFromTimeStamp(Timestamp timestamp) {
		Date date = timestamp;
		return dateFormatNoTime.format(date);
	}

	public static boolean checkTimeToProcess(Timestamp lastRunTimeStamp, int frequency) throws ParseException {
		Timestamp currentTimeStamp = Helper.currentTimeStamp();
		String curr = currentTimeStamp.toString().split(" ")[0] + " 00:00:00";
		boolean itsTime = false;

		Calendar cal = Calendar.getInstance();
		cal.setTime(lastRunTimeStamp);
		if (lastRunTimeStamp == null) {
			itsTime = true;
		} else {
			switch (frequency) {
			case Constants.SCHEDULED_JOBS_FREQUENCY_DAILY:
				cal.add(Calendar.DATE, 1);
				break;
			case Constants.SCHEDULED_JOBS_FREQUENCY_WEEKLY:
				cal.add(Calendar.DATE, 7);
				break;
			case Constants.SCHEDULED_JOBS_FREQUENCY_FORTNIGHTLY:
				cal.add(Calendar.DATE, 15);
				break;
			case Constants.SCHEDULED_JOBS_FREQUENCY_MONTHLY:
				cal.add(Calendar.MONTH, 1);
				break;
			case Constants.SCHEDULED_JOBS_FREQUENCY_QUARTERLY:
				cal.add(Calendar.MONTH, 3);
				break;
			case Constants.SCHEDULED_JOBS_FREQUENCY_HALF_YEARLY:
				cal.add(Calendar.DATE, 6);
				break;
			case Constants.SCHEDULED_JOBS_FREQUENCY_YEARLY:
				cal.add(Calendar.YEAR, 1);
				break;
			}
			Timestamp scheduledTime = Timestamp.valueOf(dateFormat.format(cal.getTime()));
			String sc = scheduledTime.toString().split(" ")[0] + " 00:00:00";
			Date currentRunTimestamp = dateFormat.parse(curr);
			Date scheduledTimestamp = dateFormat.parse(sc);

			logger.info(dateFormat.format(currentRunTimestamp));
			logger.info(dateFormat.format(scheduledTimestamp));

			if (currentRunTimestamp.compareTo(scheduledTimestamp) > 0) {
				itsTime = true;
				logger.info("Current Run Timespan is after Schudeled Timespan");// true
			} else if (currentRunTimestamp.compareTo(scheduledTimestamp) < 0) {
				itsTime = false;
				logger.info("Current Run Timespan is before Schudeled Timespan");// false
			} else if (currentRunTimestamp.compareTo(scheduledTimestamp) == 0) {
				itsTime = true;
				logger.info("Current Run Timespan is equal to Schudeled Timespan");// true
			}

		}

		return itsTime;
	}

	public static int validateJobTypeFrequency(SchedulerJob job) {
		int jobType = 0;
		jobType = job.getTimeInterval().getTimeInterval().equalsIgnoreCase(Constants.SCHEDULED_JOBS_DAILY)
				? Constants.SCHEDULED_JOBS_FREQUENCY_DAILY
				: job.getTimeInterval().getTimeInterval().equalsIgnoreCase(Constants.SCHEDULED_JOBS_WEEKLY)
						? Constants.SCHEDULED_JOBS_FREQUENCY_WEEKLY
						: job.getTimeInterval().getTimeInterval().equalsIgnoreCase(Constants.SCHEDULED_JOBS_MONTHLY)
								? Constants.SCHEDULED_JOBS_FREQUENCY_MONTHLY
								: 0;
		return jobType;
	}

	public static String getFromDate(int timeIntervalFrequency) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -timeIntervalFrequency);
		return timeIntervalFrequency == 10 ? "2000-01-01 00:00:00+05:30" : dateFormatNoTime.format(cal.getTime());
	}

	public static boolean crunchifyEmailValidator(String email) {
		boolean isValid = false;
		try {
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();

			EmailValidator emailValidator = EmailValidator.getInstance();
			if (!emailValidator.isValid(email)) {
				return false;
			}
			isValid = true;
		} catch (AddressException e) {
			System.out.println("You are in catch block -- Exception Occurred for: " + email);
		}
		return isValid;
	}

	public static String getResetPasswordText(String username, String confirmationToken, String resetPasswordLink) {
		return "Dear " + username + "," + Constants.NEW_LINE + Constants.TAB
				+ " Please click below link, to reset your password." + Constants.NEW_LINE + Constants.NEW_LINE
				+ Constants.TAB + resetPasswordLink + "?token=" + confirmationToken + Constants.NEW_LINE
				+ Constants.NEW_LINE + " Thanks, " + Constants.NEW_LINE + " SCR Team";
	}

	public static String getRerunSchedulerText(User user, RemarkRequest remarkRequest) {
		return "Dear Admin" + "," + Constants.NEW_LINE + Constants.TAB + " Rerun the scheduler with below details."
				+ Constants.NEW_LINE + Constants.TAB + "1. Run By: " + user.getUsername() + Constants.NEW_LINE
				+ Constants.TAB + "2. Job Id: " + remarkRequest.getJobId() + Constants.NEW_LINE + Constants.TAB
				+ "3. Remark: " + remarkRequest.getRemark() + Constants.NEW_LINE + Constants.TAB + "4. Run Date: "
				+ remarkRequest.getRunDate() + Constants.NEW_LINE + Constants.NEW_LINE + " Thanks, "
				+ Constants.NEW_LINE + " SCR Team";
	}

	public static String getSchedulerDBConnectionFailedText(SchedulerJob job, ResponseStatus connectionStatus) {
		return "Dear " + job.getCreatedBy().getUsername() + "," + Constants.NEW_LINE + Constants.TAB
				+ " Databse Connection failed with below details." + Constants.NEW_LINE + Constants.TAB + "1. Host: "
				+ job.getRepository().getRepositoryIp() + Constants.NEW_LINE + Constants.TAB + "2. Port: "
				+ job.getRepository().getRepositoryPort() + Constants.NEW_LINE + Constants.TAB + "3. Database Name: "
				+ job.getRepository().getRepositoryDbName() + Constants.NEW_LINE + Constants.TAB
				+ "4. DataBase User Name : " + job.getRepository().getRepositoryUser() + Constants.NEW_LINE
				+ Constants.TAB + "5. DataBase Password : " + job.getRepository().getRepositoryPassword()
				+ Constants.NEW_LINE + Constants.TAB + "6. Failed Reason : " + connectionStatus.getMessage()
				+ Constants.NEW_LINE + Constants.NEW_LINE + " Thanks, " + Constants.NEW_LINE + " SCR Team";
	}

	public static String prepareChangeFileName(MultipartFile mf, String divisionCode, String createdBy) {
		logger.info("current data:" + Helper.currentTimeStampWithString());
		String fileName = createdBy + Constants.UNDER_SCORE + mf.getOriginalFilename().split(Constants.SPLIT_DOT)[0]
				+ Constants.UNDER_SCORE + divisionCode + Constants.UNDER_SCORE
				+ Helper.currentTimeStampWithString().split(" ")[0] + Constants.UNDER_SCORE
				+ Helper.currentTimeStampWithString().split(" ")[1].replace(":", "-") + Constants.DOT
				+ mf.getOriginalFilename().split(Constants.SPLIT_DOT)[1];
		return fileName;
	}

	public static String getCurrentYear() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	public static Timestamp getCurrentTimestamp() {
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		return ts;
	}

	public static Map<String, String> getSchedulerDBConnectionFailedTextModel(SchedulerJob job, ResponseStatus connectionStatus) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("name", job.getCreatedBy().getUsername());
		//model.put("location", "Belgium");
		//model.put("signature", "https://memorynotfound.com");
		model.put("host", job.getRepository().getRepositoryIp());
		model.put("Port", job.getRepository().getRepositoryPort());
		model.put("DatabaseName", job.getRepository().getRepositoryDbName());
		model.put("DataBaseUserName", job.getRepository().getRepositoryUser());
		model.put("DataBasePassword", job.getRepository().getRepositoryPassword());
		model.put("FailedReason", connectionStatus.getMessage());
		/*
		 * "Dear " + job.getCreatedBy().getUsername() + "," + Constants.NEW_LINE +
		 * Constants.TAB + " Databse Connection failed with below details." +
		 * Constants.NEW_LINE + Constants.TAB + "1. Host: " +
		 * job.getRepository().getRepositoryIp() + Constants.NEW_LINE + Constants.TAB +
		 * "2. Port: " + job.getRepository().getRepositoryPort() + Constants.NEW_LINE +
		 * Constants.TAB + "3. Database Name: " +
		 * job.getRepository().getRepositoryDbName() + Constants.NEW_LINE +
		 * Constants.TAB + "4. DataBase User Name : " +
		 * job.getRepository().getRepositoryUser() + Constants.NEW_LINE + Constants.TAB
		 * + "5. DataBase Password : " + job.getRepository().getRepositoryPassword() +
		 * Constants.NEW_LINE + Constants.TAB + "6. Failed Reason : " +
		 * connectionStatus.getMessage() + Constants.NEW_LINE + Constants.NEW_LINE +
		 * " Thanks, " + Constants.NEW_LINE + " SCR Team";
		 */
		 return model;
	}

	public static double parseDouble(String value) {
		if(value != null && !value.isEmpty() && !value.trim().equalsIgnoreCase("null")) {
			return Double.parseDouble(value);
		}
		return 0;
	}


}
