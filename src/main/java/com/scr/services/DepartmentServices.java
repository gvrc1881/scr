/**
 * 
 */
package com.scr.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Department;
import com.scr.repository.DepartmentRepository;

/**
 * @author vt1056
 *
 */
@Service
public class DepartmentServices {
	
	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Department> findByStatusId(Integer statusId) {		
		return departmentRepository.findByStatusId(statusId);
	}

	public void saveDepartment(@Valid Department department) {
		departmentRepository.save(department);
	}

	public Optional<Department> findByDepartmentId(Integer departmentId) {	
		return departmentRepository.findByDepartmentId(departmentId);
	}

	public @Valid Department findByDepartmentIdAndStatusId(Integer departmentId, int statusId) {	
		return departmentRepository.findByDepartmentIdAndStatusId(departmentId, statusId);
	}

	public Boolean existsByDepartmentNameAndStatusId(String departmentName, Integer statusId) {		
		return departmentRepository.existsByDepartmentNameAndStatusId(departmentName, statusId);
	}

}
