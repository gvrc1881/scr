/**
 * 
 */
package com.scr.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.mapper.RolePermissionMapper;
import com.scr.message.request.RolePermissionRequest;
import com.scr.message.response.RolePermissionResponse;
import com.scr.model.Permissions;
import com.scr.model.RolePermissions;
import com.scr.model.RoleType;
import com.scr.services.PermissionService;
import com.scr.services.RolePermissionService;
import com.scr.services.RoleTypeService;
import com.scr.util.Constants;

/**
 * @author vt1056
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class RolePermissionController {
	static Logger logger = LogManager.getLogger(RolePermissionController.class);
	
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleTypeService roleTypeService;

	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllRolesWithPermissions", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RolePermissionResponse> getAllRoles() throws JSONException {
		List<RolePermissionResponse> response = null;
		try {
			List<RoleType> rolesList = roleTypeService.findAllByStatusId(Constants.ACTIVE_STATUS_ID);
			List<RolePermissions> rolePermissionList = rolePermissionService.findAllByStatusId(Constants.ACTIVE_STATUS_ID);
			List<Permissions> permissionsList = permissionService.findAll();
			response = rolePermissionMapper.prepareRolePermissionResponse(rolesList, rolePermissionList, permissionsList);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/permissions", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Permissions> getAllPermissions() throws JSONException {
		List<Permissions> roleTypeUserList = null;
		try {
			roleTypeUserList = permissionService.findAllByStatusId(Constants.ACTIVE_STATUS_ID);
			return roleTypeUserList;
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return roleTypeUserList;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/updatePermission", method = RequestMethod.PUT, headers = "Accept=application/json")
	public Boolean saveRolePermission(@Valid @RequestBody RolePermissionRequest rolePermissionRequest) throws JSONException {
		try {
			//Optional<RoleType> roleTypes = roleTypeService.findById(rolePermissionRequest.getRoleId());
			Optional<RolePermissions> rolePermissions =	rolePermissionService.findByRoleTypeId(rolePermissionRequest.getRoleId());
			if(rolePermissions.isPresent()) {
				RolePermissions rolePermissionsToUpdate = rolePermissions.get();
				rolePermissionsToUpdate.setModifiedBy(rolePermissionRequest.getModifiedBy());
				rolePermissionsToUpdate.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
				rolePermissionsToUpdate.setPermissionId(rolePermissionRequest.getPermissionId());
				rolePermissionService.save(rolePermissionsToUpdate);
			}			
			return true;
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}
}
