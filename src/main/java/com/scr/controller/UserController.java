/**
 * 
 */
package com.scr.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.jobs.CommonUtility;
import com.scr.mapper.UserMapper;
import com.scr.message.request.LoginForm;
import com.scr.message.response.LoggedUserResponse;
import com.scr.message.response.MenuPermissionResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Facility;
import com.scr.model.MasterRoles;
import com.scr.model.PageRolePermission;
import com.scr.model.Permissions;
import com.scr.model.RolePermissions;
import com.scr.model.RoleType;
import com.scr.model.User;
import com.scr.model.UserJurisdiction;
import com.scr.model.Works;
import com.scr.services.PermissionService;
import com.scr.services.RolePermissionService;
import com.scr.services.RoleTypeService;
import com.scr.services.UserJurisdictionService;
import com.scr.services.UserServices;
import com.scr.util.Constants;
import com.scr.util.Helper;
import com.scr.util.PasswordEncryption;

/**
 * @author vt1056
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class UserController {
	static Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
    private UserServices userServices;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RoleTypeService roleTypeService;
	//@Autowired
	//PasswordEncoder encoder;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncryption passwordEncryption;
	
	@Autowired
	private CommonUtility commonUtility;
	
	@Autowired
	private UserJurisdictionService userJurisdictionService;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/userData", method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<User> getUserData(@Valid @RequestBody LoginForm loginRequest) throws JSONException {
		User userData = null;		
		try {			
			Optional<User> users = userServices.findByUserName(loginRequest.getUserName());
			if (users != null) {
				userData = users.get();
			}
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((userData));
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/loggedUserData", method = RequestMethod.POST , headers = "Accept=application/json")
	public LoggedUserResponse getLoggedUserData(@Valid @RequestBody LoginForm loginRequest) throws JSONException {
		User userData = null;
		LoggedUserResponse response = new LoggedUserResponse();
		List<Facility> zoneFacilities = new ArrayList<>();
		List<Facility> divisionFacilities = new ArrayList<>();
		List<Facility> subDivisionFacilities = new ArrayList<>();
		List<Facility> depotFacilities = new ArrayList<>();
		try {			
			Optional<User> users = userServices.findByEmail(loginRequest.getEmail());
			if (users != null) {
				userData = users.get();
			//	logger.info("Logged User Data: "+userData);
				logger.info("User Role: "+userData.getMasterRoles());
				Set<MasterRoles> masterRoles = userData.getMasterRoles();
				List<MenuPermissionResponse> menuResponses = null;
				for(MasterRoles masterRole: masterRoles) {
					List<PageRolePermission> pageRolePermissionList = userServices.findPageRolePermissionByMasterRoleId(masterRole);
					logger.info("Length = "+pageRolePermissionList.size());
					menuResponses = userMapper.preparePageRoePermissions(pageRolePermissionList);
					
					/*
					 * List<Menu> menuList = new ArrayList<Menu>(); for(PageRolePermission pageRole:
					 * pageRolePermissionList) { logger.info("Role Id ="+pageRole.getMenuId()); Menu
					 * menu = userServices.findMenuById(pageRole.getMenuId().getId());
					 * logger.info("Menu ="+menu.toString()); menuList.add(menu); }
					 */
				}
				
				List<Permissions> permissionsList = permissionService.findAll();
				List<RoleType> rolesList = roleTypeService.findAllByStatusId(Constants.ACTIVE_STATUS_ID);
				List<RolePermissions> rolePermissionsList = rolePermissionService.findAllByStatusId(Constants.ACTIVE_STATUS_ID);
				response = userMapper.prepareLoggedUserData(userData, permissionsList, rolesList, rolePermissionsList);;
				response.setMenuPermissionResponses(menuResponses);
				List<Facility> facilities = commonUtility.findUserHierarchy(response.getUserName());
				List<Works> projectList = userJurisdictionService.findByUserId(userData);
				/*List<Works> workList = new ArrayList<>();
				for (UserJurisdiction userJurisdiction : userjurisdictionList) {
					logger.info("in loop");
					Works work = userJurisdiction.getWorkId();
					workList.add(work);
					logger.info("*** after add ***");
				}*/
				logger.info("** list size ***"+projectList.size());
				for (Facility facility : facilities) {
					if ("ZONE".equals(facility.getDepotType())) {
						zoneFacilities.add(facility);
					} else if("DIV".equals(facility.getDepotType())) {
						divisionFacilities.add(facility);
					}else if("SUB_DIV".equals(facility.getDepotType())) {
						subDivisionFacilities.add(facility);
					}else {
						depotFacilities.add(facility);
					}
						
				}
				response.setZoneList(zoneFacilities);
				response.setDivisionList(divisionFacilities);
				response.setSubDivisionList(subDivisionFacilities);
				response.setDepotList(depotFacilities);
				response.setProjects(projectList);
				return response;
			}
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/currentPassword", method = RequestMethod.POST , headers = "Accept=application/json")
	public boolean checkCurrentPassword(@Valid @RequestBody LoginForm loginRequest) throws JSONException {
		try {						
			logger.info("loginRequest ="+loginRequest.toString());
			Optional<User> users = userServices.findByEmail(loginRequest.getEmail());
			if (users.isPresent()) {
			//	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
				String pwdEncrypt = passwordEncryption.encode(loginRequest.getPassword());
				logger.info("Converted Password = "+pwdEncrypt);
				logger.info("DB Password = "+users.get().getPassword());
				logger.info("condition = "+(pwdEncrypt.equalsIgnoreCase(users.get().getPassword())));
				//return encoder.matches(loginRequest.getPassword(), users.get().getPassword()) ? true : false;
				return pwdEncrypt.equalsIgnoreCase(users.get().getPassword()) ? true : false;
			}
			return false;
		} catch (NullPointerException e) {			
			logger.error(e);
			return false;
		} catch (Exception e) {			
			logger.error(e);
			return false;
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus updatePassword(@RequestBody User user) throws JSONException {
		try {
			Optional<User> users = userServices.findByEmail(user.getEmail());
			if (users.isPresent()) {
				User userToUpdate = users.get();
				userToUpdate.setPassword(passwordEncryption.encode(user.getPassword()));
				userServices.saveUser(userToUpdate);
				return Helper.findResponseStatus("Password Updated Successfully.", Constants.SUCCESS_CODE);
			} else {
				return Helper.findResponseStatus("Password Updated Failed", Constants.FAILURE_CODE);
			}
		} catch (NullPointerException e) {			
			logger.error(e);
			return Helper.findResponseStatus("Password Updated Failed", Constants.FAILURE_CODE);
		} catch (Exception e) {			
			logger.error(e);
			return Helper.findResponseStatus("Password Updated Failed", Constants.FAILURE_CODE);
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/allUsers", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<User>> findAllUsers() throws JSONException {
		List<User> usersList = null;
		try {			
			usersList = userServices.findAllByStatusId(Constants.ACTIVE_STATUS_ID);			
		} catch (NullPointerException e) {			
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((usersList));
	}
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findUserById/{id}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<User> findUserById(@PathVariable("id") Long id) throws JSONException {
		List<User> usersList = null;
		try {			
			usersList = userServices.findByIdAndStatusId(id,Constants.ACTIVE_STATUS_ID);			
		} catch (NullPointerException e) {		
			logger.error(e);
		} catch (Exception e) {			
			logger.error(e);
		}
		return ResponseEntity.ok((usersList.get(0)));
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Boolean deleteUser(@PathVariable("id") Long id) throws JSONException {
		try {
			userServices.deleteById(id);
		} catch (NullPointerException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return true;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/editUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<User> editUser(@RequestBody User user) throws JSONException {
		ResponseEntity<User> tusersedit = null;
		try {
			Optional<User> users = userServices.findByEmail(user.getEmail());
			if (users != null) {
				User userToUpdate = users.get();
			logger.info("Edit User Details Started");
			userToUpdate.setDepartment(user.getDepartment());
			userToUpdate.setRoleTypeId(user.getRoleTypeId());
			userToUpdate.setUsername(user.getUsername());
			userToUpdate.setFirst_name(user.getFirst_name());
			userToUpdate.setLast_name(user.getLast_name());
			userToUpdate.setPhone(user.getPhone());
			userToUpdate.setGender(user.getGender());
			userToUpdate.setDivisionCode(user.getDivisionCode());
			userToUpdate.setModified_date(new Date(Calendar.getInstance().getTime().getTime()));
			userServices.saveUser(userToUpdate);
		   logger.info("Edit User Details Ended");	
			}
		return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return tusersedit;
	
	}
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> addUser(@RequestBody User user) throws JSONException {
		ResponseEntity<User> tusersadd = null;
		try {
			logger.info("Adding User to the List Started");
			System.out.println("User: "+user.toString());
			user.setStatusId(Constants.ACTIVE_STATUS_ID);
			String password = user.getPassword();
			user.setPassword(passwordEncryption.encode(password));
			user.setCreated_date(new Date(Calendar.getInstance().getTime().getTime()));
			user.setModified_date(new Date(Calendar.getInstance().getTime().getTime()));
			userServices.saveUser(user);
			logger.info("Adding User to the List Ended");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NullPointerException e) {
			logger.error(e);			
		} catch (Exception e) {			
			logger.error(e);
		}
		return tusersadd;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/duplicateEmail", method = RequestMethod.POST)
	public ResponseEntity<User> Duplicate(@RequestBody User user) throws JSONException {
		ResponseEntity<User> tusersduplicate = null;
		try {
			logger.info("User DuplicateEmail Details Started");
		  Optional<User> duplicateEmail = userServices.findByEmail(user.getEmail());
		  User duplicateEmailuser = null;
		if (duplicateEmail != null) {
			duplicateEmailuser = duplicateEmail.get();
		}
		logger.info("User DuplicateEmail Details Ended");	

		return new ResponseEntity<User>(duplicateEmailuser, HttpStatus.OK);
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return tusersduplicate;
	}
	
}
