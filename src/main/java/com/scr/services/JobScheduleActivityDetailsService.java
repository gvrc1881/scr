/**
 * 
 */
package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.response.ResponseStatus;
import com.scr.model.JobScheduleActivityDetails;
import com.scr.repository.JobScheduleActivityDetailsRepository;
import com.scr.util.Constants;

/**
 * @author venkat
 *
 */
@Service
public class JobScheduleActivityDetailsService {

	@Autowired
	private JobScheduleActivityDetailsRepository jobScheduleActivityDetailsRepository;

	public ResponseStatus updateJobScheduleActivityDetails(String operation) {
		ResponseStatus response = new ResponseStatus();
		try {
			List<JobScheduleActivityDetails> jobScheduleActivityDetailsServicesList = jobScheduleActivityDetailsRepository
					.findAll();
			for (JobScheduleActivityDetails jobScheduleActivityDetailsServiceToUpdate : jobScheduleActivityDetailsServicesList) {
				jobScheduleActivityDetailsServiceToUpdate.setCreateUpdateDelete(operation);
				jobScheduleActivityDetailsRepository.save(jobScheduleActivityDetailsServiceToUpdate);
			}
			response.setCode(Constants.SUCCESS_CODE);
		} catch (Exception e) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage("ERROR >>> "+e);
		}
		return response;
	}

}
