package com.scr.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.jobs.CommonUtility;
import com.scr.jobs.response.ErrorResponse;
import com.scr.mapper.RolePermissionMapper;
import com.scr.message.request.LoginForm;
import com.scr.message.response.JwtResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.ConfirmationToken;
import com.scr.model.Facility;
import com.scr.model.RolePermissions;
import com.scr.model.RoleType;
import com.scr.model.SchedulerOperationTypesTracking;
import com.scr.model.User;
import com.scr.repository.ConfirmationTokenRepository;
import com.scr.repository.RolePermissionRepository;
import com.scr.repository.RoleRepository;
import com.scr.repository.UserRepository;
import com.scr.security.jwt.JwtProvider;
import com.scr.services.EmailSenderService;
import com.scr.services.RoleTypeService;
import com.scr.services.SchedulerOperationTypesTrackingService;
import com.scr.services.UserServices;
import com.scr.util.Constants;
import com.scr.util.ExcelGenerator;
import com.scr.util.Helper;
import com.scr.util.PasswordEncryption;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class AuthController {

	static Logger logger = LogManager.getLogger(AuthController.class);

	//@Autowired
	//AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private UserServices userServices;

	@Autowired
	RoleRepository roleRepository;

	//@Autowired
	//PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	private RoleTypeService roleTypeService;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Value("${scr.web.reset.password.url}")
	private String resetPasswordLink;
	
	@Autowired
	private ExcelGenerator excelGenerator;
	
	@Autowired
	private SchedulerOperationTypesTrackingService schedulerOperationTypesTrackingService;
	
	@Autowired
	private CommonUtility CommonUtility;
	
	@Autowired
	private PasswordEncryption passwordEncryption;
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/auth/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
	
		
		logger.info("loginRequest ="+loginRequest.toString());
		String jwt = null;
		try {
		Optional<User> user = userServices.findByUserName(loginRequest.getUserName());
		if (user.isPresent()) {
			String encryPassword = passwordEncryption.encode(loginRequest.getPassword());
			//logger.info(user.get().toString());
			if (encryPassword.equalsIgnoreCase(user.get().getPassword())) {
				jwt = jwtProvider.generateJwtToken(user.get());
			}else {
				
				 List<String> details = new ArrayList<>();
			       // details.add(ex.getLocalizedMessage());
			        ErrorResponse error = new ErrorResponse("Record Not Found", details);
			        return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
			}
		}else {
			 List<String> details = new ArrayList<>();
		       // details.add(ex.getLocalizedMessage());
		        ErrorResponse error = new ErrorResponse("Record Not Found", details);
		        return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
		}
		}catch (Exception e) {
			List<String> details = new ArrayList<>();
		        details.add(e.getLocalizedMessage());
		        ErrorResponse error = new ErrorResponse("Record Not Found", details);
		        return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/auth/addUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public User addUser(@RequestBody User user) throws JSONException {
		User tusersadd = null;
		try {
			logger.info("Adding User to the List Started");			
			user.setStatusId(Constants.ACTIVE_STATUS_ID);
			String password = user.getPassword();
			user.setPassword(passwordEncryption.encode(password));
			user.setCreated_date(new Date(Calendar.getInstance().getTime().getTime()));
			user.setModified_date(new Date(Calendar.getInstance().getTime().getTime()));
			userServices.saveUser(user);
			logger.info("Adding User to the List Ended");
			
			Optional<User> users = userServices.findByEmail(user.getEmail());
			if(users.isPresent()) {
			
			RoleType roleType = new RoleType();
			roleType.setStatusId(Constants.ACTIVE_STATUS_ID);
			roleType.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
			roleType.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
			String roleName = "ROLE_APP";
			roleType.setRoleName(roleName);
			roleType.setRoleType("Admin");
			roleType.setCreatedBy(users.get().getId().intValue());
			roleType.setModifiedBy(users.get().getId().intValue());
			roleType.setStatusId(Constants.ACTIVE_STATUS_ID);
			roleTypeService.saveRole(roleType);
			RoleType rolTypeToUpdate = roleTypeService.findByRoleTypeAndStatusId(roleType.getRoleType(), Constants.ACTIVE_STATUS_ID);
			User userToUpdate = users.get();
			userToUpdate.setRoleTypeId(rolTypeToUpdate.getId());
			userServices.saveUser(userToUpdate);			
			
			RolePermissions rolePermissions = rolePermissionMapper.prepareRolePermissionAdd(roleType);
			rolePermissions.setPermissionId(5);
			rolePermissionRepository.save(rolePermissions);
			roleType = roleTypeService.findByRoleTypeAndStatusId(roleType.getRoleType(), Constants.ACTIVE_STATUS_ID);
			}
			
			return user;
		} catch (NullPointerException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return tusersadd;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/auth/forgotPassword", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus forgotPassword(@RequestBody User user) throws JSONException {

		try {
			Optional<User> users = userServices.findByEmail(user.getEmail());
			if (users != null) {
				try {
					if (Helper.crunchifyEmailValidator(users.get().getEmail())) {
						ConfirmationToken confirmationToken = new ConfirmationToken(users.get());
						// save it
						confirmationTokenRepository.save(confirmationToken);
						// create the email
						SimpleMailMessage mailMessage = new SimpleMailMessage();
						mailMessage.setTo(users.get().getEmail());
						mailMessage.setSubject(Constants.RESET_PASSWORD_SUBJECT);
						mailMessage.setFrom("nairobley@gmail.com");											
						mailMessage.setText(Helper.getResetPasswordText(users.get().getUsername(), confirmationToken.getConfirmationToken(), resetPasswordLink));
						emailSenderService.sendEmail(mailMessage);
						return Helper.findResponseStatus("Email Sent Successfully.", Constants.SUCCESS_CODE);
					} else {
						return Helper.findResponseStatus("Email Does Exists.", Constants.FAILURE_CODE);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("ERROR >>> " + e);
					return Helper.findResponseStatus("Email Does Exists.", Constants.FAILURE_CODE);
				}
			} else {
				return Helper.findResponseStatus("Email Not Registred.", Constants.FAILURE_CODE);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error(e);
			return Helper.findResponseStatus("Email Does Not Exists.", Constants.FAILURE_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return Helper.findResponseStatus("Email Does Not Exists.", Constants.FAILURE_CODE);
		}

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/auth/resetPassword", method = RequestMethod.POST, headers = "Accept=application/json")
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
			e.printStackTrace();
			logger.error(e);
			return Helper.findResponseStatus("Password Updated Failed", Constants.FAILURE_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return Helper.findResponseStatus("Password Updated Failed", Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/auth/confirm-reset", method = { RequestMethod.POST, RequestMethod.POST })
	public ResponseStatus validateResetToken(@RequestBody ConfirmationToken token) {
		ResponseStatus response = new ResponseStatus();
		ConfirmationToken confirmationToken = confirmationTokenRepository
				.findByConfirmationToken(token.getConfirmationToken());
		if (confirmationToken != null) {
			User user = userServices.findByEmailIdIgnoreCase(confirmationToken.getUser().getEmail());
			response.setUser(user);
			response.setCode(Constants.SUCCESS_CODE);
			response.setMessage("Valid Token");			
			return response;
		} else {			
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage("InValid Token");
			return response;
		}
	}

	//@CrossOrigin(origins = "*")
	@RequestMapping(value = "/auth/checkUsersExists", method = RequestMethod.GET, headers = "Accept=application/json")
	public boolean checkUsersExists() {
		try {
			return userServices.existsByStatusId(Constants.ACTIVE_STATUS_ID);
		} catch (Exception e) {
			logger.error("ERROR >>> " + e);
			return true;
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/auth/download/{trackingId}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> download(@PathVariable("trackingId") Integer trackingId, HttpServletResponse response) throws JSONException, IOException {
			logger.info("Downloading XSL File");
			 String xlsxFileName = "PId_"+trackingId+"_"+Helper.currentDate()+"_"+Helper.currentTime()+".xlsx";
		    List<SchedulerOperationTypesTracking>  schedulerOperationTypesTrackingsList = schedulerOperationTypesTrackingService.findByTrackingIdOrderByProcessedDateAsc(trackingId);
		    ByteArrayInputStream in = excelGenerator.customersToExcel(schedulerOperationTypesTrackingsList);
		    HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename="+xlsxFileName);
	     return ResponseEntity
	                  .ok()
	                  .headers(headers)
	                  .body(new InputStreamResource(in));
	
	}
	
	@RequestMapping(value = "/userHierarchy/{user}", method = RequestMethod.GET , headers = "Accept=application/json")
	public List<Facility> findUserHierarchy(@PathVariable("user") String user) {
		logger.info("**** user name****"+user);
		List<Facility> facilityList = CommonUtility.findUserHierarchy(user);
		//return JSONObject.quote(CommonUtility.findUserHierarchy(user));
		return facilityList;
	}
	
}