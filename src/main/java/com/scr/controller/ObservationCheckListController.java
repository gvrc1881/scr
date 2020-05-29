package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
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
	static Logger log = Logger.getLogger(ObservationCheckListController.class);

	@Autowired
	private ObservationCheckListService observationCheckListService;
	
	
	@RequestMapping(value = "/findAllObservationCheckList" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ObservationsCheckList> findAllObservationCheckList(){
		log.info("Enter into findAllObservationCheckList function");
		List<ObservationsCheckList> observationCheckList = null;
		try {
			log.info("Calling service for observation CheckList data");
			observationCheckList = observationCheckListService.findAll();
			log.info("Fetched observation CheckList data"+observationCheckList.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the observation CheckList data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the observation CheckList data = "+e.getMessage());
		}
		log.info("Exit from findAllObservationCheckList function");
		return observationCheckList;
	}
	
	@RequestMapping(value = "/addObservationCheckList" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addObservationCheckList(@RequestBody ObservationsCheckList observationsCheckList) {
		log.info("Enter into addObservationCheckList function with below request parameters ");
		log.info("Request Parameters = "+observationsCheckList.toString());
		try {
			log.info("Calling service with request parameters.");
			observationCheckListService.save(observationsCheckList);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Observations CheckList added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Observations CheckList data. "+npe.getMessage());
			return Helper.findResponseStatus("Observations CheckList save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Observations CheckList data. "+e.getMessage());
			return Helper.findResponseStatus("Observations CheckList save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findObservationCheckListById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<ObservationsCheckList> findObservationCheckListById(@PathVariable("id") Long id){
		Optional<ObservationsCheckList> observationsCheckList =  null;
		try {
			log.info("Selected observationsCheckList Id = "+id);
			observationsCheckList = observationCheckListService.findObservationCheckListById(id);
			if(observationsCheckList.isPresent()) {
				log.info("ObservationsCheckList Data = "+observationsCheckList.get());
				return new ResponseEntity<ObservationsCheckList>(observationsCheckList.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<ObservationsCheckList>(observationsCheckList.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			log.error("Error >>  while find ObservationsCheckList Details by id, "+e.getMessage());
			return new ResponseEntity<ObservationsCheckList>(observationsCheckList.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateObservationCheckList" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateObservationCheckList(@RequestBody ObservationsCheckList observationsCheckList) {
		log.info("Enter into updateObservationCheckList function with below request parameters ");
		log.info("Request Parameters = "+observationsCheckList.toString());
		try {
			log.info("Calling service with request parameters.");
			observationCheckListService.save(observationsCheckList);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Observations CheckList updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating Observations CheckList data. "+npe.getMessage());
			return Helper.findResponseStatus("Observations CheckList update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating Observations CheckList data. "+e.getMessage());
			return Helper.findResponseStatus("Observations CheckList update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	
	@RequestMapping(value = "/deleteObservationCheckList/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteObservationCheckListById(@PathVariable Long id) {
		log.info("Enter into deleteObservationCheckListById function");
		log.info("Selected Observation CheckList Id = "+id);
		try {
			observationCheckListService.deleteObservationCheckListById(id);
			return Helper.findResponseStatus("Observation CheckList Deleted successful", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Observation CheckList data"+npe.getMessage());
			return Helper.findResponseStatus("Observation CheckList Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Observation CheckList data"+e.getMessage());
			return Helper.findResponseStatus("Observation CheckList Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
}