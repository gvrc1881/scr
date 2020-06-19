package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
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
import com.scr.model.ObservationCategory;
import com.scr.services.ObservationCategoriesService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ObservationCategoriesController {
	static Logger log = Logger.getLogger(ObservationCategoriesController.class);

	@Autowired
	private ObservationCategoriesService observationCategoriesService;
	
	
	@RequestMapping(value = "/findAllObservationCategory" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ObservationCategory> findAllObservationCategory(){
		log.info("Enter into findAllObservationCategory function");
		List<ObservationCategory> observationCategory = null;
		try {
			log.info("Calling service for observation Category data");
			observationCategory = observationCategoriesService.findAll();
			log.info("Fetched observation Category data"+observationCategory.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the observation Category data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the observation Category data = "+e.getMessage());
		}
		log.info("Exit from findAllObservationCategory function");
		return observationCategory;
	}
	@RequestMapping(value = "/addObservationCategories" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addObservationCategories(@RequestBody ObservationCategory observationCategory) {
		log.info("Enter into addObservationCategories function with below request parameters ");
		log.info("Request Parameters = "+observationCategory.toString());
		try {
			log.info("Calling service with request parameters.");
			observationCategoriesService.save(observationCategory);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("ObservationCategory added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding observation Category data. "+npe.getMessage());
			return Helper.findResponseStatus("observation Category save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding observation Category data. "+e.getMessage());
			return Helper.findResponseStatus("observation Category save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/findObservationCategoriesById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<ObservationCategory> findObservationCategoriesById(@PathVariable Long id){
		Optional<ObservationCategory> observationCategory = null;
		try {
			log.info("Selected Observation Category Id = "+id);
			observationCategory = observationCategoriesService.findObservationCategoriesById(id);
			if(observationCategory.isPresent()) {
				log.info("observation Category Data = "+observationCategory.get());
				return new ResponseEntity<ObservationCategory>(observationCategory.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<ObservationCategory>(observationCategory.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find observation Category Details by id, "+e.getMessage());
			return new ResponseEntity<ObservationCategory>(observationCategory.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateObservationCategories" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateObservationCategories(@RequestBody ObservationCategory observationCategory) {
		log.info("Enter into updateObservationCategories function with below request parameters ");
		log.info("Request Parameters = "+observationCategory.toString());
		try {
			log.info("Calling service with request parameters.");
			observationCategoriesService.save(observationCategory);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("observation Category updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating observation Category data. "+npe.getMessage());
			return Helper.findResponseStatus("observation Category update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating observation Category data. "+e.getMessage());
			return Helper.findResponseStatus("observation Category update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteObservationCategories/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteObservationCategoryById(@PathVariable Long id) {
		log.info("Enter into deleteObservationCategoryById function");
		log.info("Selected observation Category Id = "+id);
		try {
			observationCategoriesService.deleteObservationCategoryById(id);
			return Helper.findResponseStatus("observation Category Deleted successful", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting observation Category data"+npe.getMessage());
			return Helper.findResponseStatus("observation Category Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting observation Category data"+e.getMessage());
			return Helper.findResponseStatus("observation Category Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existsInspectionTypeAndObservationCategory/{inspectionType}/{observationCategory}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsInspectionTypeAndObservationCategory(@PathVariable("inspectionType") String inspectionType ,@PathVariable("observationCategory") String observationCategory){
			
		try {
			log.info("Request for checking exists inspection Type and observationCategory.");
			return observationCategoriesService.existsByInspectionTypeAndObservationCategory(inspectionType,observationCategory);	
		} catch (Exception e) {
			log.error("Error while checking exists inspectionType and observationCategory..."+e.getMessage());
			return false;
		}
	}
	
	
}