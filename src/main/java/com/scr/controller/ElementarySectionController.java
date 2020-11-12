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
import com.scr.model.ElementarySections;
import com.scr.services.ElementarySectionService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ElementarySectionController {

	static Logger logger = LogManager.getLogger(ElementarySectionController.class);

	@Autowired
	private ElementarySectionService elementarySectionService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllEleSections", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<ElementarySections> findAllEleSections() throws JSONException {
		logger.info("Enter into findAll Elementary Section function");
		List<ElementarySections> eleSectionsList = null;
		try {
			logger.info("Calling service for elementary section data");
			eleSectionsList = elementarySectionService.findAll();
			logger.info("Fetched elementary section data = " + eleSectionsList.size());
			return eleSectionsList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the elementary section data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the elementary section data = " + e.getMessage());
		}
		logger.info("Exit from findAll Elementary Section function");
		return eleSectionsList;
	}
	@RequestMapping(value="/addElementarySections",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addElementarySections(@RequestBody ElementarySections elementarySections) {
		logger.info("Enter into addElementarySections function with below request parameters ");
		logger.info("Request Parameters = "+elementarySections.toString());
		try {
			logger.info("Calling service with request parameters.");
			elementarySectionService.save(elementarySections);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("elementarySections Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding elementarySections data. "+npe.getMessage());
			return Helper.findResponseStatus("elementarySections save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding elementarySections data. "+e.getMessage());
			return Helper.findResponseStatus("elementarySections save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findElementarySectionsById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<ElementarySections> findElementarySectionsById(@PathVariable Long id){
		Optional<ElementarySections> elementarySections = null;
		try {
			logger.info("Selected elementarySections Id = "+id);
			elementarySections = elementarySectionService.findElementarySectionsById(id);
			if(elementarySections.isPresent()) {
				logger.info("elementarySections Data = "+elementarySections.get());
				return new ResponseEntity<ElementarySections>(elementarySections.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<ElementarySections>(elementarySections.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find elementarySections Details by id, "+e.getMessage());
			return new ResponseEntity<ElementarySections>(elementarySections.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateElementarySections" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateElementarySections(@RequestBody ElementarySections elementarySections) {
		logger.info("Enter into updateElementarySections function with below request parameters ");
		logger.info("Request Parameters = "+elementarySections.toString());
		try {
			logger.info("Calling service with request parameters.");
			elementarySectionService.save(elementarySections);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("elementarySections Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating elementarySections data. "+npe.getMessage());
			return Helper.findResponseStatus("elementarySections update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating elementarySections data. "+e.getMessage());
			return Helper.findResponseStatus("elementarySections update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteElementarySections/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteElementarySectionsById(@PathVariable Long id) {
		logger.info("Enter into deleteElementarySectionsById function");
		logger.info("Selected ElementarySections Id = "+id);
		try {
			elementarySectionService.deleteElementarySectionsById(id);
			return Helper.findResponseStatus("ElementarySections deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting ElementarySections data"+npe.getMessage());
			return Helper.findResponseStatus("ElementarySections Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting ElementarySections data"+e.getMessage());
			return Helper.findResponseStatus("ElementarySections Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

}
