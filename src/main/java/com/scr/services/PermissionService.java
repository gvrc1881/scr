/**
 * 
 */
package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Permissions;
import com.scr.repository.PermissionRepository;

/**
 * @author vt1056
 *
 */
@Service
public class PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	
	public List<Permissions> findAll() {		
		return permissionRepository.findAll();
	}

	public List<Permissions> findAllByStatusId(int statusId) {		
		return permissionRepository.findAllByStatusId(statusId);
	}
	
}
