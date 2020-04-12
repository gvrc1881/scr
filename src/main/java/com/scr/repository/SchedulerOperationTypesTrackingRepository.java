/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.SchedulerOperationTypesTracking;

/**
 * @author venkat
 *
 */
@Repository
public interface SchedulerOperationTypesTrackingRepository extends JpaRepository<SchedulerOperationTypesTracking, Long>{

	List<SchedulerOperationTypesTracking> findByTrackingIdOrderByProcessedDateAsc(Integer trackingId);

}
