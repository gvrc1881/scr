/**
 * 
 */
package com.scr.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Department;

/**
 * @author vt1056
 *
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

	List<Department> findByStatusId(Integer statusId);

	Optional<Department> findByDepartmentId(Integer departmentId);

	@Valid
	Department findByDepartmentIdAndStatusId(Integer departmentId, int statusId);

	Boolean existsByDepartmentNameAndStatusId(String departmentName, Integer statusId);

}
