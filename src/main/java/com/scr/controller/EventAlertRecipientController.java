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
import com.scr.model.Event;
import com.scr.model.EventAlertRecipient;
import com.scr.model.EventType;
import com.scr.services.AlertGroupMemberService;
import com.scr.services.AlertGroupService;
import com.scr.services.EventAlertRecipientService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class EventAlertRecipientController {
	
	static Logger log = Logger.getLogger(EventAlertRecipientController.class);
	
	
	@Autowired
	private EventAlertRecipientService eventAlertRecipientService;
	
	@Autowired
	private AlertGroupMemberService alertGroupMemberService;
	
	@Autowired
	private AlertGroupService alertGroupService;
	
	@RequestMapping(value = "/findAllEventAlertRecipient", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<EventAlertRecipient> findAllEventAlertRecipient() throws JSONException {
		List<EventAlertRecipient> eventAlertRecipientList = null;
		try {
			log.info("Calling service for alertGroup Member List data");

			eventAlertRecipientList = eventAlertRecipientService.findAll();
			log.info("Fetched eventAlert Recipient data***" + eventAlertRecipientList.size());
			return eventAlertRecipientList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the  event AlertRecipient data = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the event AlertRecipient data = " + e.getMessage());
		}
		log.info("Exit from eventAlert Recipient function");
		return eventAlertRecipientList;
	}
	@RequestMapping(value = "/findAllEventType", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<EventType> findAllEventType() throws JSONException {
		List<EventType> eventTypeList = null;
		try {
			log.info("Calling service for alertGroup Member List data");

			eventTypeList = eventAlertRecipientService.findAllEventType();
			log.info("Fetched eventAlert Recipient data***" + eventTypeList.size());
			return eventTypeList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the  event TypeList data = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the event TypeList data = " + e.getMessage());
		}
		log.info("Exit from event TypeList function");
		return eventTypeList;
	}
	@RequestMapping(value = "/addEventAlertRecipient", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveEventAlertRecipient(@Valid @RequestBody EventAlertRecipient eventAlertRecipient)
			throws JSONException {
		log.info("Enter into saveEventAlertRecipient function with below request parameters ");
		log.info("Request Parameters = " + eventAlertRecipient.toString());
		try {
			log.info("Calling service with request parameters.");
			eventAlertRecipientService.save(eventAlertRecipient);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Event Alert Recipient Added Successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While adding Event Alert Recipient . " + npe.getMessage());
			return Helper.findResponseStatus("Event Alert Recipient Addition is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While adding Event Alert Recipient . " + e.getMessage());
			return Helper.findResponseStatus("Event Alert Recipient  Addition is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findEventTypeItemById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<EventType> findEventItemById(@PathVariable Long id) {
		Optional<EventType> event = null;
		try {
			log.info("Selected eventAlertRecipient Id = " + id);
			event = eventAlertRecipientService.findByEventTypeId(id);
			if (event.isPresent()) {
				log.info("event Data = " + event.get());
				return new ResponseEntity<EventType>(event.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<EventType>(event.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find event  Data Details by id, " + e.getMessage());
			return new ResponseEntity<EventType>(event.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/findEventAlertRecipientItemById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<EventAlertRecipient> findEventAlertRecipientItemById(@PathVariable Long id) {
		Optional<EventAlertRecipient> eventAlertRecipient = null;
		try {
			log.info("Selected eventAlertRecipient Id = " + id);
			eventAlertRecipient = eventAlertRecipientService.findEventAlertRecipientItemById(id);
			if (eventAlertRecipient.isPresent()) {
				log.info("Asset Master Data = " + eventAlertRecipient.get());
				return new ResponseEntity<EventAlertRecipient>(eventAlertRecipient.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<EventAlertRecipient>(eventAlertRecipient.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find event Alert Recipient Data Details by id, " + e.getMessage());
			return new ResponseEntity<EventAlertRecipient>(eventAlertRecipient.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateEventAlertRecipient", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateEventAlertRecipient(@RequestBody EventAlertRecipient eventAlertRecipient) {
		log.info("Enter into updateEventAlertRecipient Data function with below request parameters ");
		log.info("Request Parameters = " + eventAlertRecipient.toString());
		try {
			log.info("Calling service with request parameters.");
			eventAlertRecipientService.save(eventAlertRecipient);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("event Alert Recipient Updated successful", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While updating event Alert Recipient data. " + npe.getMessage());
			return Helper.findResponseStatus("event Alert Recipient update is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While updating event Alert Recipient data. " + e.getMessage());
			return Helper.findResponseStatus("event Alert Recipient update is Failed with " + e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/deleteEventAlertRecipient/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteEventAlertRecipientById(@PathVariable Long id) {
		log.info("Enter into deleteEventAlertRecipientById function");
		log.info("Selected  Event Alert Recipient  Data Id = " + id);
		try {
			eventAlertRecipientService.deleteEventAlertRecipientById(id);
			return Helper.findResponseStatus("Event Alert Recipient Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Event Alert Recipient Data" + npe.getMessage());
			return Helper.findResponseStatus("Event Alert Recipient Data Deletion is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While deleting Event Alert Recipient Data" + e.getMessage());
			return Helper.findResponseStatus("Event Alert Recipient Data Deletion is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/existsName/{name}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsName(@PathVariable("name") String name){		
		try {
			return eventAlertRecipientService.existsByName(name);
		} catch (Exception e) {
			log.error("Error while checking existsName.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsDescription/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDescription(@PathVariable("description") String description){		
		try {
			return eventAlertRecipientService.existsByDescription(description);
		} catch (Exception e) {
			log.error("Error while checking exists Description.");
			return false;
		}
	}
	@RequestMapping(value = "/findByNameAndId/{id}/{name}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByNameAndId(@PathVariable("id") Long id,@PathVariable("name") String name){
		
		log.info("id=="+id+"name=="+name);
		Boolean result;
		try {
			Optional<EventAlertRecipient> stData = eventAlertRecipientService.findByName(name);
			
			if(stData.isPresent()) {
				EventAlertRecipient eventAlertRecipient = stData.get();
				log.info("***id ***"+eventAlertRecipient.getId());
				if (id.equals(eventAlertRecipient.getId())) {
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
	@RequestMapping(value = "/findByDescriptionAndId/{id}/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByDescriptionAndId(@PathVariable("id") Long id,@PathVariable("description") String description){
		
		log.info("id=="+id+"description=="+description);
		Boolean result;
		try {
			Optional<EventAlertRecipient> eventAlertRecipientData = eventAlertRecipientService.findByDescription(description);
			
			if(eventAlertRecipientData.isPresent()) {
				EventAlertRecipient eventAlertRecipient = eventAlertRecipientData.get();
				log.info("***id ***"+eventAlertRecipient.getId());
				if (id.equals(eventAlertRecipient.getId())) {
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
	@RequestMapping(value = "/findByEventTypeIdAlertGroupIdAndAlertGroupMemberId/{eventTypeId}/{alertGroupId}/{alertGroupMemberId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsEventTypeIdAlertGroupIdAndAlertGroupMemberId(@PathVariable("eventTypeId") Long eventTypeId ,@PathVariable("alertGroupId") Long alertGroupId,@PathVariable("alertGroupMemberId") Long alertGroupMemberId){
		
		try {
			log.info("Request for checking exists eventTypeId alertGroupId  and alertGroupMemberId ...");
			return eventAlertRecipientService.existsByEventTypeIdAndAlertGroupIdAndAlertGroupMemberId(eventAlertRecipientService.findByEventTypeId(eventTypeId).get(),alertGroupService.findAlertGroupItemById(alertGroupId).get(),alertGroupMemberService.findAlertGroupMemberItemById(alertGroupMemberId).get());
		} catch (Exception e) {
			log.error("Error while checking exists eventTypeId "+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existEventTypeIdAlertGroupIdAndAlertGroupMemberIdAndId/{id}/{eventTypeId}/{alertGroupId}/{alertGroupMemberId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existEventTypeIdAlertGroupIdAndAlertGroupMemberIdAndId(@PathVariable("id") Long id,@PathVariable("eventTypeId") Long eventTypeId ,@PathVariable("alertGroupId") Long alertGroupId,@PathVariable("alertGroupMemberId") Long alertGroupMemberId){
		
		log.info("id=="+id+"eventTypeId=="+eventTypeId);
		Boolean result;
		try {
			Optional<EventAlertRecipient> eventAlertRecipientData = eventAlertRecipientService.findByEventTypeIdAndAlertGroupIdAndAlertGroupMemberId (eventAlertRecipientService.findByEventTypeId(eventTypeId).get(),alertGroupService.findAlertGroupItemById(alertGroupId).get(),alertGroupMemberService.findAlertGroupMemberItemById(alertGroupMemberId).get());
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(eventAlertRecipientData.isPresent()) {
				EventAlertRecipient testInpe = eventAlertRecipientData.get();
				log.info("***id ***"+testInpe.getId());
				if (id.equals(testInpe.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and eventTypeId..."+e.getMessage());
			return false;
		}
	}
}
