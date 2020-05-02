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
import com.scr.model.ObservationsCheckList;
import com.scr.services.ObservationCheckListService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ObservationCheckListController {
	@Autowired
	private ObservationCheckListService observationCheckListService;
	
	
	@RequestMapping(value = "/findAllObservationCheckList" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ObservationsCheckList> findAllObservationCheckList(){
		List<ObservationsCheckList> observationCheckList = observationCheckListService.findAll();
		return observationCheckList;
	}
	
	@RequestMapping(value = "/addObservationCheckList" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addObservationCheckList(@RequestBody ObservationsCheckList observationsCheckList) {
		observationCheckListService.save(observationsCheckList);
		return Helper.findResponseStatus("observation Check List added successfully", Constants.SUCCESS_CODE);

	}
	
	
	@RequestMapping(value = "/findObservationCheckListById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<ObservationsCheckList> findObservationCheckListById(@PathVariable Long id){
		Optional<ObservationsCheckList> observationsCheckList = observationCheckListService.findObservationCheckListById(id);
		return new ResponseEntity<ObservationsCheckList>(observationsCheckList.get(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/updateObservationCheckList" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateObservationCheckList(@RequestBody ObservationsCheckList observationsCheckList) {
		observationCheckListService.save(observationsCheckList);
		return Helper.findResponseStatus("observation Check List updated successfully", Constants.SUCCESS_CODE);

	}
	
	@RequestMapping(value = "/deleteObservationCheckList/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteObservationCheckListById(@PathVariable Long id) {
		observationCheckListService.deleteObservationCheckListById(id);
		return Helper.findResponseStatus("observation Check List Deleted successfully", Constants.SUCCESS_CODE);
	}
}