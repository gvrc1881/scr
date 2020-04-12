/**
 * 
 */
package com.scr.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.TimeInteralMapper;
import com.scr.message.request.TimeIntervalRequest;
import com.scr.model.TimeInterval;
import com.scr.model.User;
import com.scr.repository.TimeIntervalRepository;
import com.scr.util.Constants;

/**
 * @author venkat
 *
 */
@Service
public class TimeIntervalService {

	@Autowired
	private TimeIntervalRepository timeIntervalRepository;
	
	@Autowired
	private TimeInteralMapper timeInteralMapper;
	
	public List<TimeInterval> findAllByisActiveOrderByModifiedDateDesc(Integer isActive) {
		return timeIntervalRepository.findAllByisActiveOrderByModifiedDateDesc(isActive);
	}

	public TimeInterval saveTimeInterval(TimeInterval timeInterval) {
		return timeIntervalRepository.save(timeInterval);
	}

	public TimeInterval findByTimeIntervalId(Integer timeIntervalId) {
		return timeIntervalRepository.findByTimeIntervalId(timeIntervalId);
	}

	public Boolean existsByTimeInterval(Integer timeInterval) {		
		return timeIntervalRepository.existsByTimeIntervalAndIsActive(timeInterval, Constants.ACTIVE_STATUS_ID);
	}

	public TimeInterval addTimeInterval(TimeIntervalRequest timeIntervalRequest, User user) {
		TimeInterval timeInterval = null;
		if(timeIntervalRequest != null && user != null) {
			timeInterval = timeInteralMapper.prepareAddTimeIntervalMapper(timeIntervalRequest, user);
			timeInterval = saveTimeInterval(timeInterval);
		}
		return timeInterval;
	}

	public TimeInterval updateTimeInterval(TimeIntervalRequest timeIntervalRequest, User user) {
		TimeInterval timeIntervalToUpdate = null;
		if(timeIntervalRequest != null && user != null) {
			timeIntervalToUpdate = findByTimeIntervalId(timeIntervalRequest.getTimeIntervalId());
			timeIntervalToUpdate = timeInteralMapper.prepareUpdateTimeIntervalMapper(timeIntervalToUpdate, timeIntervalRequest, user);
			timeIntervalToUpdate = saveTimeInterval(timeIntervalToUpdate);
		}
		return timeIntervalToUpdate;
	}

}
