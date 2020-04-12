/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.JobStatus;

/**
 * @author vt1056
 *
 */
@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {

	List<JobStatus> findAll();

	JobStatus findByJobStatusId(Integer jobTypeId);

}
