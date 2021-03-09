package com.scr.controller;

import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.StatusType;
import com.scr.services.StatusTypeService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class StatusTypeController {

	
static Logger logger = LogManager.getLogger(StatusTypeController.class);
	
	@Autowired
	private StatusTypeService statusTypeService;
	
	
	@RequestMapping(value = "/findAllStatusType", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<StatusType> findAllStatusType() throws JSONException {
		logger.info("Enter into findAll findAllStatusType function");
		List<StatusType> statusTypeList = null;
		try {
			logger.info("Calling service for StatusType data");
			statusTypeList = statusTypeService.findAll();
			logger.info("Fetched StatusType data = " + statusTypeList.size());
			return statusTypeList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the StatusType data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the StatusType data = " + e.getMessage());
		}
		logger.info("Exit from findAll StatusType function");
		return statusTypeList;
	}
	
	@RequestMapping(value="/addStatusType",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addStatusType(@RequestBody StatusType statusType) {
		logger.info("Enter into addStatusType function with below request parameters ");
		logger.info("Request Parameters = "+statusType.toString());
		try {
			logger.info("Calling service with request parameters.");
			statusTypeService.save(statusType);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("statusType Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding statusType data. "+npe.getMessage());
			return Helper.findResponseStatus("statusType save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding statusType data. "+e.getMessage());
			return Helper.findResponseStatus("statusType save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findStatusTypeById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<StatusType> findStatusTypeById(@PathVariable Long id){
		Optional<StatusType> statusType = null;
		try {
			logger.info("Selected statusType Id = "+id);
			statusType = statusTypeService.findStatusTypeById(id);
			if(statusType.isPresent()) {
				logger.info("Status Type Data = "+statusType.get());
				return new ResponseEntity<StatusType>(statusType.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<StatusType>(statusType.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find StatusType Details by id, "+e.getMessage());
			return new ResponseEntity<StatusType>(statusType.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateStatusType" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateStatusType(@RequestBody StatusType statusType) {
		logger.info("Enter into updateStatusType function with below request parameters ");
		logger.info("Request Parameters = "+statusType.toString());
		try {
			logger.info("Calling service with request parameters.");
			statusTypeService.save(statusType);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("statusType Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating statusType data. "+npe.getMessage());
			return Helper.findResponseStatus("statusType update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating statusType data. "+e.getMessage());
			return Helper.findResponseStatus("statusType update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteStatusType/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteStatusTypeById(@PathVariable Long id) {
		logger.info("Enter into deleteStatusTypeById function");
		logger.info("Selected statusType Id = "+id);
		try {
			statusTypeService.deleteStatusTypeById(id);
			return Helper.findResponseStatus("statusType deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting statusType data"+npe.getMessage());
			return Helper.findResponseStatus("statusType Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting statusType data"+e.getMessage());
			return Helper.findResponseStatus("statusType Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	@RequestMapping(value = "/existsStatusTypeId/{statusTypeId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsStatusTypeId(@PathVariable("statusTypeId") String statusTypeId){		
		try {
			return statusTypeService.existsByStatusTypeId(statusTypeId);
		} catch (Exception e) {
			logger.error("Error while checking existsByStatusTypeId.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsStatusTypeDescription/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDescription(@PathVariable("description") String description){		
		try {
			return statusTypeService.existsByDescription(description);
		} catch (Exception e) {
			logger.error("Error while checking exists Description.");
			return false;
		}
	}
	@RequestMapping(value = "/findByStatusTypeIdAndId/{id}/{statusTypeId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByFacilityNameAndId(@PathVariable("id") Long id,@PathVariable("statusTypeId") String statusTypeId){
		
		logger.info("id=="+id+"statusTypeId=="+statusTypeId);
		Boolean result;
		try {
			Optional<StatusType> stData = statusTypeService.findByStatusTypeId(statusTypeId);
			
			if(stData.isPresent()) {
				StatusType statusType = stData.get();
				logger.info("***id ***"+statusType.getId());
				if (id.equals(statusType.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and statusTypeId..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/findByStatusTypeDescriptionAndId/{id}/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findByDescriptionAndId(@PathVariable("id") Long id,@PathVariable("description") String description){
		
		logger.info("id=="+id+"description=="+description);
		Boolean result;
		try {
			Optional<StatusType> statData = statusTypeService.findByDescription(description);
			
			if(statData.isPresent()) {
				StatusType statusType = statData.get();
				logger.info("***id ***"+statusType.getId());
				if (id.equals(statusType.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and description..."+e.getMessage());
			return false;
		}
	}
	
}
