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
public interface JobsHistoryRepository extends JpaRepository<JobsHistory, Long>{
	

	List<JobsHistory> findByOperationIdAndStatusNotIn(Integer operationId, String status);

	JobsHistory findByOperationIdAndOperationType(Integer operationId, String operationType);

}
