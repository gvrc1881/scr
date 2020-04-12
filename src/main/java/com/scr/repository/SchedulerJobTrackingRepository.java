/**
 * 
 */
package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.SchedulerJob;
import com.scr.model.SchedulerJobsTracking;

/**
 * @author venkat
 *
 */
@Repository
public interface SchedulerJobTrackingRepository extends JpaRepository<SchedulerJobsTracking, Long>{

	List<SchedulerJobsTracking> findAllByOrderByProcessedDateAsc();

	List<SchedulerJobsTracking> findByRepositoryOrderByProcessedDateAsc(com.scr.model.Repository repository);

	//List<SchedulerJobsTracking> findSchedulerJobTrackingOrderByProcessedDate();

	List<SchedulerJobsTracking> findAllByOrderByProcessedDateDesc();

	List<SchedulerJobsTracking> findByJobIdAndProcessedDate(SchedulerJob jobId, Timestamp processedDate);

}
