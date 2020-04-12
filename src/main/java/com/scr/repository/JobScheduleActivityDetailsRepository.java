/**
 * 
 */
package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.JobScheduleActivityDetails;

/**
 * @author venkat
 *
 */
@Repository
public interface JobScheduleActivityDetailsRepository extends JpaRepository<JobScheduleActivityDetails, Long> {

}
