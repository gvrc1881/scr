/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.JobsHistory;

/**
 * @author vt1056
 *
 */
@Repository
public interface SchedulerTrackingRepository extends JpaRepository<JobsHistory, Long>{

	List<JobsHistory> findByOperationIdOrderByProcessedDateAsc(Integer operationId);

	//List<JobsHistory> findByOperationIdOrderByProcessedDateDesc(Integer operationId);

	List<JobsHistory> findByOperationIdOrderByProcessedDate(Integer operationId);

	

}
