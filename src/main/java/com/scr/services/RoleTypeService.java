/**
 * 
 */
package com.scr.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.MasterRoles;
import com.scr.model.RoleType;
import com.scr.repository.MasterRolesRepository;
import com.scr.repository.RoleTypeRepository;

/**
 * @author vt1056
 *
 */
@Service
public class RoleTypeService {

	@Autowired
	private RoleTypeRepository roleTypeRepository;
	
	@Autowired
	private MasterRolesRepository masterRolesRepository;
	
	public List<RoleType> findAll() {		
		return roleTypeRepository.findAll();
	}
	
	public List<MasterRoles> findMasterByStatusId(Integer statusId) {
		return masterRolesRepository.findAll();
	}
	
	public List<RoleType> findAllByStatusId(Integer statusId) {		
		return roleTypeRepository.findAllByStatusId(statusId);
	}
	
	public void saveRole(@Valid RoleType role) {
		roleTypeRepository.save(role);		
	}

	public Optional<RoleType> findById(Integer id) {		
		return roleTypeRepository.findById(id);
	}

	public Optional<RoleType> findByRoleNameAndStatusId(String roleName, Integer statusId) {	
		return roleTypeRepository.findByRoleNameAndStatusId(roleName, statusId);
	}

	/*
	 * public boolean createRoleTypePermissions(int createdBy, int obj, int id) {
	 * return roleTypeRepository.createRoleTypePermissions(createdBy, obj, id); }
	 */

	public @Valid RoleType findByRoleTypeAndStatusId(String roleType, int statusId) {	
		return roleTypeRepository.findByRoleTypeAndStatusId(roleType, statusId);
	}

	public boolean existsByRoleTypeAndStatusId(String roleType, Integer statusId) {	
		return roleTypeRepository.existsByRoleTypeAndStatusId(roleType, statusId);
	}

	

	

	

}
