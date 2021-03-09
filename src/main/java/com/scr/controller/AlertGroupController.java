package com.scr.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.AlertGroup;
import com.scr.model.StatusType;
import com.scr.services.AlertGroupService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class AlertGroupController {
	
	static Logger log = Logger.getLogger(AlertGroupController.class);
	
	@Autowired
	private AlertGroupService alertGroupService;
	
	@RequestMapping(value = "/findAllAlertGroup", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<AlertGroup> findAllAlertGroup() throws JSONException {
		List<AlertGroup> alertGroupList = null;
		try {
			log.info("Calling service for alertGroupList data");

			alertGroupList = alertGroupService.findAll();
			log.info("Fetched alert Group data***" + alertGroupList.size());
			return alertGroupList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the  alert Group data = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the alert Group data = " + e.getMessage());
		}
		log.info("Exit from AlertGroup function");
		return alertGroupList;
	}

	@RequestMapping(value = "/addAlertGroupItem", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveAlertGroupItem(@Valid @RequestBody AlertGroup alertGroup)
			throws JSONException {
		log.info("Enter into saveAlertGroupItem function with below request parameters ");
		log.info("Request Parameters = " + alertGroup.toString());
		try {
			log.info("Calling service with request parameters.");
			alertGroupService.save(alertGroup);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Alert Group Data Added Successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While adding alertGroup . " + npe.getMessage());
			return Helper.findResponseStatus("Alert Group Data Addition is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While adding Alert Group  data. " + e.getMessage());
			return Helper.findResponseStatus("Alert Group Data Addition is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/findAlertGroupItemById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<AlertGroup> findAlertGroupItemById(@PathVariable Long id) {
		Optional<AlertGroup> alertGroup = null;
		try {
			log.info("Selected Asset Master Data Id = " + id);
			alertGroup = alertGroupService.findAlertGroupItemById(id);
			if (alertGroup.isPresent()) {
				log.info("Asset Master Data = " + alertGroup.get());
				return new ResponseEntity<AlertGroup>(alertGroup.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<AlertGroup>(alertGroup.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Asset Master Data Details by id, " + e.getMessage());
			return new ResponseEntity<AlertGroup>(alertGroup.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateAlertGroup", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateAlertGroup(@RequestBody AlertGroup alertGroup) {
		log.info("Enter into updateAlertGroupData function with below request parameters ");
		log.info("Request Parameters = " + alertGroup.toString());
		try {
			log.info("Calling service with request parameters.");
			alertGroupService.save(alertGroup);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("alertGroup Updated successful", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While updating AlertGroup data. " + npe.getMessage());
			return Helper.findResponseStatus("alertGroup update is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While updating alertGroup data. " + e.getMessage());
			return Helper.findResponseStatus("alertGroup update is Failed with " + e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/deleteAlertGroup/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteAlertGroupById(@PathVariable Long id) {
		log.info("Enter into deleteAlertGroupById function");
		log.info("Selected  AlertGroup Data Id = " + id);
		try {
			alertGroupService.deleteAlertGroupById(id);
			return Helper.findResponseStatus("AlertGroup Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting alertGroup Data" + npe.getMessage());
			return Helper.findResponseStatus("alert Group Data Deletion is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While deleting alert Group Data" + e.getMessage());
			return Helper.findResponseStatus("alertGroup Data Deletion is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/existsAlertGroupName/{name}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsAlertGroupName(@PathVariable("name") String name){		
		try {
			return alertGroupService.existsByName(name);
		} catch (Exception e) {
			log.error("Error while checking existsName.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsAlertGroupDescription/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsAlertGroupDescription(@PathVariable("description") String description){		
		try {
			return alertGroupService.existsByDescription(description);
		} catch (Exception e) {
			log.error("Error while checking exists Description.");
			return false;
		}
	}
	@RequestMapping(value = "/findByAlertGroupNameAndId/{id}/{name}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAlertGroupNameAndId(@PathVariable("id") Long id,@PathVariable("name") String name){
		
		log.info("id=="+id+"name=="+name);
		Boolean result;
		try {
			Optional<AlertGroup> stData = alertGroupService.findByName(name);
			
			if(stData.isPresent()) {
				AlertGroup alertGroup = stData.get();
				log.info("***id ***"+alertGroup.getId());
				if (id.equals(alertGroup.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and name..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/findByAlertGroupDescriptionAndId/{id}/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAlertGroupDescriptionAndId(@PathVariable("id") Long id,@PathVariable("description") String description){
		
		log.info("id=="+id+"description=="+description);
		Boolean result;
		try {
			Optional<AlertGroup> alertGroupData = alertGroupService.findByDescription(description);
			
			if(alertGroupData.isPresent()) {
				AlertGroup alertGroup = alertGroupData.get();
				log.info("***id ***"+alertGroup.getId());
				if (id.equals(alertGroup.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and description..."+e.getMessage());
			return false;
		}
	}

}
