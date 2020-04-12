/**
 * 
 */
package com.scr.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scr.model.JobStatus;
import com.scr.repository.JobStatusRepository;

/**
 * @author vt1056
 *
 */
@Service
public class JobStatusService {

	private JobStatusRepository jobStatusRepository;
	
	public List<JobStatus> findAll() {		
		return jobStatusRepository.findAll();
	}

	public JobStatus findByJobStatusId(Integer jobStatusId) {	
		return jobStatusRepository.findByJobStatusId(jobStatusId);
	}

}
