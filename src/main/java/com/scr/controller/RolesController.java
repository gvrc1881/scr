package com.scr.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.mapper.RolePermissionMapper;
import com.scr.model.MasterRoles;
import com.scr.model.RolePermissions;
import com.scr.model.RoleType;
import com.scr.repository.RolePermissionRepository;
import com.scr.services.RoleTypeService;
import com.scr.util.Constants;
import com.scr.util.WebUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class RolesController {

	static Logger logger = LogManager.getLogger(RolesController.class);

	@Autowired
	private RoleTypeService roleTypeService;

	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllRoles", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RoleType> getAllRoles(HttpServletRequest request) throws JSONException {
		List<RoleType> roleTypeUserList = null;
		try {
			System.out.println(WebUtils.getClientIp(request));
			roleTypeUserList = roleTypeService.findAllByStatusId(Constants.ACTIVE_STATUS_ID);
			return roleTypeUserList;
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return roleTypeUserList;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findMasterRoles", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<MasterRoles> getMasterRoles() throws JSONException {
		List<MasterRoles> roleTypeUserList = null;
		try {
			roleTypeUserList = roleTypeService.findMasterByStatusId(Constants.ACTIVE_STATUS_ID);
			return roleTypeUserList;
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return roleTypeUserList;
	}
	
	

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/roleById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Optional<RoleType> getRoleById(@PathVariable("id") Integer id) throws JSONException {
		Optional<RoleType> roleTypeUserList = null;
		try {
			roleTypeUserList = roleTypeService.findById(id);
			return roleTypeUserList;
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return roleTypeUserList;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findDuplicateRole/{name}/{statusId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public boolean getDuplicateRoleByName(@PathVariable("name") String name, @PathVariable("statusId") Integer statusId)
			throws JSONException {
		try {
			boolean flag = roleTypeService.existsByRoleTypeAndStatusId(name, statusId);
			return flag;
		} catch (NullPointerException e) {
			logger.error(e);
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/addRole", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<RoleType> addRole(@Valid @RequestBody RoleType roleType) throws JSONException {
		try {
			roleType.setStatusId(Constants.ACTIVE_STATUS_ID);
			roleType.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
			roleType.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
			String roleName = "ROLE_APP";
			roleType.setRoleName(roleName);
			roleTypeService.saveRole(roleType);
			RolePermissions rolePermissions = rolePermissionMapper.prepareRolePermissionAdd(roleType);
			rolePermissionRepository.save(rolePermissions);
			roleType = roleTypeService.findByRoleTypeAndStatusId(roleType.getRoleType(), Constants.ACTIVE_STATUS_ID);
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ResponseEntity.ok().body(roleType);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/deleteRole/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Boolean deleteRole(@PathVariable("id") Integer id) throws JSONException {
		try {
			Optional<RoleType> roleTypes = roleTypeService.findById(id);
			if (roleTypes != null && roleTypes.get() != null) {
				RoleType roleTypeToUpdate = roleTypes.get();
				roleTypeToUpdate.setId(id.intValue());
				roleTypeToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
				roleTypeService.saveRole(roleTypeToUpdate);
			}
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return true;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/editRole", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<RoleType> editRole(@Valid @RequestBody RoleType roleType) throws JSONException {

		try {
			roleType.setStatusId(Constants.ACTIVE_STATUS_ID);
			roleType.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
			roleType.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));

			String roleName = "ROLE_APP";
			roleType.setRoleName(roleName);
			roleTypeService.saveRole(roleType);

			roleType = roleTypeService.findByRoleTypeAndStatusId(roleType.getRoleType(), Constants.ACTIVE_STATUS_ID);
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return ResponseEntity.ok().body(roleType);
	}
	

}
