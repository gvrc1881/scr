/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.scr.model.SchedulerJob;


/**
 * @author venkat
 *
 */
@Repository
public interface ScheduleSettingRepository extends JpaRepository<SchedulerJob, Long>{

	List<SchedulerJob> findAllByisActiveOrderByModifiedDateDesc(Integer isActive);

	SchedulerJob findByJobId(Integer jobId);

	SchedulerJob findByJobIdAndIsActive(Integer jobId, Integer isActive);

	//Boolean existsByRepositoryAndisActive(Integer repositoryId, int isActive);

}
