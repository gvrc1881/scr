/**
 * 
 */
package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.RolePermissions;
import com.scr.repository.RolePermissionRepository;

/**
 * @author vt1056
 *
 */
@Service
public class RolePermissionService {

	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	public RolePermissions save(RolePermissions rolePermissions) {	
		return rolePermissionRepository.save(rolePermissions);
	}

	public List<RolePermissions> findAllByStatusId(int statusId) {		
		return rolePermissionRepository.findAllByStatusId(statusId);
	}

	public Optional<RolePermissions> findByRoleTypeId(Integer roleId) {		
		return rolePermissionRepository.findByRoleTypeId(roleId);
	}

}
