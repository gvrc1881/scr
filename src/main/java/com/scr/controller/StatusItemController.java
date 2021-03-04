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
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.StatusItem;
import com.scr.services.StatusItemService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class StatusItemController {
	
	static Logger logger = LogManager.getLogger(StatusItemController.class);

	
	@Autowired
	private StatusItemService statusItemService;
	
	
	@RequestMapping(value = "/findAllStatusItem", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<StatusItem> findAllStatusItem() throws JSONException {
		logger.info("Enter into findAll StatusItem function");
		List<StatusItem> statusItemList = null;
		try {
			logger.info("Calling service for StatusItem data");
			statusItemList = statusItemService.findAll();
			logger.info("Fetched StatusItem data = " + statusItemList.size());
			return statusItemList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the StatusItem data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the StatusItem data = " + e.getMessage());
		}
		logger.info("Exit from findAll StatusItem function");
		return statusItemList;
	}
	
	@RequestMapping(value="/addStatusItem",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addStatusItem(@RequestBody StatusItem statusItem) {
		logger.info("Enter into addStatusItem function with below request parameters ");
		logger.info("Request Parameters = "+statusItem.toString());
		try {
			logger.info("Calling service with request parameters.");
			statusItemService.save(statusItem);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("statusItem Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding statusItem data. "+npe.getMessage());
			return Helper.findResponseStatus("statusItem save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding statusItem data. "+e.getMessage());
			return Helper.findResponseStatus("statusItem save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findStatusItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<StatusItem> findStatusItemById(@PathVariable Long id){
		Optional<StatusItem> statusItem = null;
		try {
			logger.info("Selected statusItem Id = "+id);
			statusItem = statusItemService.findStatusItemById(id);
			if(statusItem.isPresent()) {
				logger.info("Status Item Data = "+statusItem.get());
				return new ResponseEntity<StatusItem>(statusItem.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<StatusItem>(statusItem.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find StatusItem Details by id, "+e.getMessage());
			return new ResponseEntity<StatusItem>(statusItem.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateStatusItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateStatusItem(@RequestBody StatusItem statusItem) {
		logger.info("Enter into updateStatusItem function with below request parameters ");
		logger.info("Request Parameters = "+statusItem.toString());
		try {
			logger.info("Calling service with request parameters.");
			statusItemService.save(statusItem);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("statusItem Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating statusItem data. "+npe.getMessage());
			return Helper.findResponseStatus("statusItem update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating statusItem data. "+e.getMessage());
			return Helper.findResponseStatus("statusItem update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteStatusItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteStatusItemById(@PathVariable Long id) {
		logger.info("Enter into deleteStatusItemById function");
		logger.info("Selected statusItem Id = "+id);
		try {
			statusItemService.deleteStatusItemById(id);
			return Helper.findResponseStatus("statusItem deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting statusItem data"+npe.getMessage());
			return Helper.findResponseStatus("statusItem Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting statusItem data"+e.getMessage());
			return Helper.findResponseStatus("statusItem Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

}
