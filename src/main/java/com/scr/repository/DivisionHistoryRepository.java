/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.DivisionsHistory;

/**
 * @author vt1056
 *
 */
@Repository
public interface DivisionHistoryRepository extends JpaRepository<DivisionsHistory, Long>{

	List<DivisionsHistory> findByJobsHistoryId(Integer jobsHistoryId);

	List<DivisionsHistory> findByJobsHistoryIdAndStatusNotIn(Integer jobsHistoryId, String status);

	DivisionsHistory findByJobsHistoryIdAndTableNameAndOperationType(Integer jobsHistoryId, String tableName, String operationType);
	
}
