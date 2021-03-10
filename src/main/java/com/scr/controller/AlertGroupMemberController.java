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
import com.scr.model.AlertGroupMember;
import com.scr.model.Receipents;
import com.scr.services.AlertGroupMemberService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class AlertGroupMemberController {
	
static Logger log = Logger.getLogger(AlertGroupController.class);
	
	@Autowired
	private AlertGroupMemberService alertGroupMemberService;
	
	@RequestMapping(value = "/findAllAlertGroupMember", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<AlertGroupMember> findAllAlertGroupMember() throws JSONException {
		List<AlertGroupMember> alertGroupMemberList = null;
		try {
			log.info("Calling service for alertGroup Member List data");

			alertGroupMemberList = alertGroupMemberService.findAll();
			log.info("Fetched alert Group data***" + alertGroupMemberList.size());
			return alertGroupMemberList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the  alert Group Member data = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the alert Group Member data = " + e.getMessage());
		}
		log.info("Exit from AlertGroup function");
		return alertGroupMemberList;
	}
	@RequestMapping(value = "/findAllReceipents", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Receipents> findAllReceipents() throws JSONException {
		List<Receipents> receipentsList = null;
		try {
			log.info("Calling service for Receipents List data");

			receipentsList = alertGroupMemberService.findAllReceipentsList();
			log.info("Fetched alert Group data***" + receipentsList.size());
			return receipentsList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the  receipents List = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the receipents List data = " + e.getMessage());
		}
		log.info("Exit from Receipents List function");
		return receipentsList;
	}
	@RequestMapping(value = "/findReceipentsItemById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Receipents> findReceipentsById(@PathVariable Long id) {
		Optional<Receipents> receipents = null;
		try {
			log.info("Selected Receipents Id = " + id);
			receipents = alertGroupMemberService.findReceipentsItemById(id);
			if (receipents.isPresent()) {
				log.info("Asset Master Data = " + receipents.get());
				return new ResponseEntity<Receipents>(receipents.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<Receipents>(receipents.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Receipents Details by id, " + e.getMessage());
			return new ResponseEntity<Receipents>(receipents.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/addAlertGroupMemberItem", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveAlertGroupMemberItem(@Valid @RequestBody AlertGroupMember alertGroupMember)
			throws JSONException {
		log.info("Enter into saveAlertGroupMemberItem function with below request parameters ");
		log.info("Request Parameters = " + alertGroupMember.toString());
		try {
			log.info("Calling service with request parameters.");
			alertGroupMemberService.save(alertGroupMember);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Alert Group Member Data Added Successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While adding alertGroup Member . " + npe.getMessage());
			return Helper.findResponseStatus("Alert Group Member Data Addition is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While adding Alert Group Member data. " + e.getMessage());
			return Helper.findResponseStatus("Alert Group Member Data Addition is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/findAlertGroupMemberItemById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<AlertGroupMember> findAlertGroupMemberItemById(@PathVariable Long id) {
		Optional<AlertGroupMember> alertGroupMember = null;
		try {
			log.info("Selected Alert Group Member Id = " + id);
			alertGroupMember = alertGroupMemberService.findAlertGroupMemberItemById(id);
			if (alertGroupMember.isPresent()) {
				log.info("Asset Master Data = " + alertGroupMember.get());
				return new ResponseEntity<AlertGroupMember>(alertGroupMember.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<AlertGroupMember>(alertGroupMember.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find alert Group Member Data Details by id, " + e.getMessage());
			return new ResponseEntity<AlertGroupMember>(alertGroupMember.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateAlertGroupMember", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateAlertGroupMember(@RequestBody AlertGroupMember alertGroupMember) {
		log.info("Enter into updateAlertGroupMember Data function with below request parameters ");
		log.info("Request Parameters = " + alertGroupMember.toString());
		try {
			log.info("Calling service with request parameters.");
			alertGroupMemberService.save(alertGroupMember);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("alertGroup Member Updated successful", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While updating AlertGroup Member data. " + npe.getMessage());
			return Helper.findResponseStatus("alertGroup Member update is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While updating alertGroup Member data. " + e.getMessage());
			return Helper.findResponseStatus("alertGroup Member update is Failed with " + e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/deleteAlertGroupMember/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteAlertGroupMemberById(@PathVariable Long id) {
		log.info("Enter into deleteAlertGroupById function");
		log.info("Selected  AlertGroup Member Data Id = " + id);
		try {
			alertGroupMemberService.deleteAlertGroupMemberById(id);
			return Helper.findResponseStatus("AlertGroup Member Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting alertGroup Member Data" + npe.getMessage());
			return Helper.findResponseStatus("alert Group Member Data Deletion is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While deleting alert Group Member Data" + e.getMessage());
			return Helper.findResponseStatus("alertGroup Member Data Deletion is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/existsAlertGroupMemberName/{name}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsAlertGroupMemberName(@PathVariable("name") String name){		
		try {
			return alertGroupMemberService.existsByName(name);
		} catch (Exception e) {
			log.error("Error while checking existsName.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsAlertGroupMemberDescription/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsAlertGroupMemberDescription(@PathVariable("description") String description){		
		try {
			return alertGroupMemberService.existsByDescription(description);
		} catch (Exception e) {
			log.error("Error while checking exists Description.");
			return false;
		}
	}
	@RequestMapping(value = "/findByAlertGroupMemberNameAndId/{id}/{name}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAlertGroupMemberNameAndId(@PathVariable("id") Long id,@PathVariable("name") String name){
		
		log.info("id=="+id+"name=="+name);
		Boolean result;
		try {
			Optional<AlertGroupMember> stData = alertGroupMemberService.findByName(name);
			
			if(stData.isPresent()) {
				AlertGroupMember alertGroup = stData.get();
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
	@RequestMapping(value = "/findByAlertGroupMemberDescriptionAndId/{id}/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByAlertGroupMemberDescriptionAndId(@PathVariable("id") Long id,@PathVariable("description") String description){
		
		log.info("id=="+id+"description=="+description);
		Boolean result;
		try {
			Optional<AlertGroupMember> alertGroupMemberData = alertGroupMemberService.findByDescription(description);
			
			if(alertGroupMemberData.isPresent()) {
				AlertGroupMember alertGroup = alertGroupMemberData.get();
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
