/**
 * 
 */
package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Role;
import com.scr.repository.RoleRepository;

/**
 * @author vt1056
 *
 */
@Service
public class RolesServices {

	@Autowired
	private RoleRepository roleRepository;
	public List<Role> findAll() {		
		return roleRepository.findAll();
	}
	public Optional<Role> findById(Long id) {		
		return roleRepository.findById(id);
	}
	public Optional<Role> findByNameAndStatusId(String name, Integer statusId) {
		return roleRepository.findByNameAndStatusId(name, statusId);
	}
	
	
	
	
}
