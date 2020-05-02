package com.scr.controller;

import java.util.List;
import java.util.Optional;
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
import com.scr.model.ObservationCategory;
import com.scr.services.ObservationCategoriesService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ObservationCategoriesController {
	@Autowired
	private ObservationCategoriesService observationCategoriesService;
	
	
	@RequestMapping(value = "/findAllObservationCategory" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ObservationCategory> findAllObservationCategory(){
		List<ObservationCategory> observationCategory = observationCategoriesService.findAll();
		return observationCategory;
	}
	
	@RequestMapping(value = "/addObservationCategories" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addObservationCategories(@RequestBody ObservationCategory observationCategory) {
		observationCategoriesService.save(observationCategory);
		return Helper.findResponseStatus("observation Category added successfully", Constants.SUCCESS_CODE);

	}
	
	
	@RequestMapping(value = "/findObservationCategoriesById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<ObservationCategory> findObservationCategoriesById(@PathVariable Long id){
		Optional<ObservationCategory> observationCategory = observationCategoriesService.findObservationCategoriesById(id);
		return new ResponseEntity<ObservationCategory>(observationCategory.get(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/updateObservationCategories" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateObservationCategories(@RequestBody ObservationCategory observationCategory) {
		observationCategoriesService.save(observationCategory);
		return Helper.findResponseStatus("observation Category updated successfully", Constants.SUCCESS_CODE);

	}
	
	@RequestMapping(value = "/deleteObservationCategories/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteObservationCategoryById(@PathVariable Long id) {
		observationCategoriesService.deleteObservationCategoryById(id);
		return Helper.findResponseStatus("observation Category Deleted successfully", Constants.SUCCESS_CODE);
	}
}