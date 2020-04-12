/**
 * 
 */
package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.JobType;

/**
 * @author venkat
 *
 */
@Repository
public interface jobTypeRepository extends JpaRepository<JobType, Long>{

	List<JobType> findAllByIsActiveOrderByModifiedDateDesc(Integer isActive);

	JobType findByJobTypeId(Integer jobTypeId);

	Boolean existsByJobTypeNameAndIsActive(String jobTypeName, Integer isActive);
	
}
