/**
 * 
 */
package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.MasterRoles;
import com.scr.model.Menu;
import com.scr.model.PageRolePermission;
import com.scr.model.User;
import com.scr.repository.MenuRepository;
import com.scr.repository.PageRolePermissionRepository;
import com.scr.repository.UserRepository;

/**
 * @author vt1056
 *
 */
@Service
public class UserServices {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PageRolePermissionRepository pageRolePermissionRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	public Optional<User> findByEmail(String email) {	
		return userRepository.findByEmail(email);
	}

	public List<User> findAllByStatusId(Integer statusId) {		
		return userRepository.findAllByStatusId(statusId);
	}

	public Optional<User> findById(Long id) {		
		return userRepository.findById(id);
	}

	public void deleteById(Long id) {		
		userRepository.deleteById(id);
	}

	public void saveUser(User user) {		
		userRepository.save(user);
	}

	public List<User> findByIdAndStatusId(Long id, Integer statusId) {		
		return userRepository.findByIdAndStatusId(id, statusId);
	}

	public boolean existsByStatusId(Integer statusId) {		
		return userRepository.existsByStatusId(statusId);
	}

	public boolean existsByEmailAndStatusId(String email, Integer statusId) {		
		return userRepository.existsByEmailAndStatusId(email, statusId);
	}
	public User findByEmailIdIgnoreCase(String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}

	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

	public List<PageRolePermission> findPageRolePermissionByMasterRoleId(MasterRoles masterRoleId) {
		return pageRolePermissionRepository.findPageRolePermissionByMasterRoleId(masterRoleId);
	}

	public Menu findMenuById(Integer id) {
		return menuRepository.findMenuById(id);
	}

}
