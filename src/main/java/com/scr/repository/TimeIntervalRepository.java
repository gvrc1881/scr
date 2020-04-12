/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.TimeInterval;

/**
 * @author venkat
 *
 */
@Repository
public interface TimeIntervalRepository extends JpaRepository<TimeInterval, Long>{

	List<TimeInterval> findAllByisActiveOrderByModifiedDateDesc(Integer isActive);

	TimeInterval findByTimeIntervalId(Integer timeIntervalId);

	Boolean existsByTimeIntervalAndIsActive(Integer timeInterval, Integer isActive);

}
