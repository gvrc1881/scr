package com.scr.controller;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.request.UserJurisdictionRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.EnergyBillPayment;
import com.scr.model.User;
import com.scr.model.UserJurisdiction;
import com.scr.model.WorkGroup;
import com.scr.model.Works;
import com.scr.services.UserJurisdictionService;
import com.scr.services.UserServices;
import com.scr.services.WorksServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class UserJurisdictionController {
	
	static Logger logger = LogManager.getLogger(UserJurisdictionController.class);
	
	@Autowired
	private UserJurisdictionService userJurisdictionService;
	
	@Autowired
	private WorksServices worksServices;
	
	@Autowired
	private UserServices userServices;
	
	@RequestMapping(value = "/addUserJurisdiction", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveUserJurisdiction(@RequestBody UserJurisdictionRequest userJurisdictionRequest){
		logger.info("Enter into save function with below request parameters ");
		logger.info("Request Parameters = "+userJurisdictionRequest.toString());
		try {
			logger.info("Calling service with request parameters.");
			 userJurisdictionService.saveUserJurisdiction(userJurisdictionRequest);
			return Helper.findResponseStatus("User Jurisdiction added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding User Jurisdiction  data. "+npe.getMessage());
			return Helper.findResponseStatus("User Jurisdiction add is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding User Jurisdiction data. "+e.getMessage());
			return Helper.findResponseStatus("User Jurisdiction add is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/getUserJurisdiction", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<UserJurisdiction> getUserJurisdiction() throws JSONException {
		List<UserJurisdiction> userJurisdictions = null;
		try {
		logger.info("Fetch userJurisdictions Started");	
		userJurisdictions = userJurisdictionService.findAll();
		logger.info("Fetch userJurisdictions Ended");
		return userJurisdictions;
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return userJurisdictions;	
	}
	
	@RequestMapping(value = "/getUserJurisdictionById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<UserJurisdiction> findById(@PathVariable("id") Long id){
		Optional<UserJurisdiction> userJurisdiction = null;
		try {
			logger.info("Selected User Jurisdiction Id = "+id);
			userJurisdiction =  userJurisdictionService.findById(id);
			if(userJurisdiction.isPresent()) {
				logger.info("User Jurisdiction Data = "+userJurisdiction.get());
				return new ResponseEntity<UserJurisdiction>(userJurisdiction.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<UserJurisdiction>(userJurisdiction.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find User Jurisdiction Details by id, "+e.getMessage());
			return new ResponseEntity<UserJurisdiction>(userJurisdiction.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/deleteUserJurisdiction/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteUserJurisdiction(@PathVariable Long id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected user jurisdiction Id = "+id);
		try {
			userJurisdictionService.deleteById(id);
			return Helper.findResponseStatus("User Jurisdiction Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting User Jurisdiction data"+npe.getMessage());
			return Helper.findResponseStatus("User Jurisdiction Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting User Jurisdiction data"+e.getMessage());
			return Helper.findResponseStatus("User Jurisdiction Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/updateUserJurisdiction", method = RequestMethod.PUT , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus updateUserJurisdiction(@RequestBody UserJurisdictionRequest userJurisdictionRequest){
		logger.info("Enter into update function with below request parameters ");
		logger.info("Request Parameters = "+userJurisdictionRequest.toString());
		try {
			logger.info("Calling service with request parameters.");
			userJurisdictionService.deleteById(userJurisdictionRequest.getId());
			 userJurisdictionService.saveUserJurisdiction(userJurisdictionRequest);
			return Helper.findResponseStatus("User Jurisdiction added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding User Jurisdiction  data. "+npe.getMessage());
			return Helper.findResponseStatus("User Jurisdiction add is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding User Jurisdiction data. "+e.getMessage());
			return Helper.findResponseStatus("User Jurisdiction add is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/getWorkGroupsBasedOnWorkAndUser/{workId}/{userName}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<WorkGroup> getWorkGroupsBasedOnWorkAndUser(@PathVariable("workId") Integer workId,@PathVariable("userName") String userName){
		logger.info("Enter into getWorkGroupsBasedOnWorkAndUser function ");
		List<WorkGroup> workGroups = null;
		try {
			Optional<Works> work = worksServices.findById(workId);
			Optional<User> user = userServices.findByUserName(userName);
			if (work.isPresent() && user.isPresent()) {
				workGroups = userJurisdictionService.findDistinctWorkGroupIdByWorkIdAndUserId(work.get(),user.get());
			}
			return workGroups;
		} catch (Exception e) {
			logger.error("Error >>  while find work Group Details by work id and user, "+e.getMessage());
		}
		return workGroups;
	}

}
