/**
 * 
 */
package com.scr.mapper;

import java.sql.Timestamp;
import java.util.Calendar;
import org.springframework.stereotype.Component;

import com.scr.message.request.TimeIntervalRequest;
import com.scr.model.TimeInterval;
import com.scr.model.User;
import com.scr.util.Constants;

/**
 * @author venkat
 *
 */
@Component
public class TimeInteralMapper {

	public TimeInterval prepareAddTimeIntervalMapper(TimeIntervalRequest timeIntervalRequest, User user) {
		TimeInterval timeInterval = new TimeInterval();
		timeInterval.setIsActive(Constants.ACTIVE_STATUS_ID);
		timeInterval.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		timeInterval.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		timeInterval.setCreatedBy(user);
		timeInterval.setModifiedBy(user);
		timeInterval.setTimeInterval(timeIntervalRequest.getTimeInterval());
		timeInterval.setStartDate(timeIntervalRequest.getStartDate());
		return timeInterval;
	}

	public TimeInterval prepareUpdateTimeIntervalMapper(TimeInterval timeIntervalToUpdate,
			TimeIntervalRequest timeIntervalRequest, User user) {
		timeIntervalToUpdate.setModifiedBy(user);
		timeIntervalToUpdate.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		timeIntervalToUpdate.setTimeInterval(timeIntervalRequest.getTimeInterval());
		timeIntervalToUpdate.setStartDate(timeIntervalRequest.getStartDate());
		return timeIntervalToUpdate;
	}

}
