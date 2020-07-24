package com.scr.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;

import com.scr.model.MeasureOrActivityList;
import com.scr.services.MeasureOrActivityListService;

import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class MeasureOrActivityListController {
	
	static Logger log = Logger.getLogger(MeasureOrActivityListController.class);
	
	@Autowired
	private MeasureOrActivityListService measureService;
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllMeasures", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<MeasureOrActivityList> findAllMeasures() throws JSONException {
		log.info("Enter into findAllMeasures function");
		List<MeasureOrActivityList> measuresList = null;
		try {
			log.info("Calling service for Measures data");
			measuresList = measureService.findAll();
			log.info("Fetched Measures data = "+measuresList.size());
			return measuresList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the energy meters data = "+npe.getMessage());
		}catch (Exception e) {
			log.error("ERROR >>> while fetching the energy meters data = "+e.getMessage());
		}
		log.info("Exit from findAllEnergyMeter function");
		return measuresList;	
	}
	
	@RequestMapping(value = "/addMeasures", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveMeasure(@RequestBody MeasureOrActivityList measures){
		log.info("Enter into saveMeasures function with below request parameters ");
		log.info("Request Parameters = "+measures.toString());
		try {
			log.info("Calling service with request parameters.");
			measureService.save(measures);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Measures added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding measures data. "+npe.getMessage());
			return Helper.findResponseStatus("Measures save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding measure data. "+e.getMessage());
			return Helper.findResponseStatus("Measure save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findMeasures/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<MeasureOrActivityList> findById(@PathVariable("id") Long id){
		Optional<MeasureOrActivityList> measure = null;
		try {
			log.info("Selected measure Id = "+id);
			measure = measureService.findById(id);
			if(measure.isPresent()) {
				log.info("Measure Data = "+measure.get());
				return new ResponseEntity<MeasureOrActivityList>(measure.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<MeasureOrActivityList>(measure.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			log.error("Error >>  while find Measure Details by id, "+e.getMessage());
			return new ResponseEntity<MeasureOrActivityList>(measure.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updateMeasures" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateMeasures(@RequestBody MeasureOrActivityList measures) {
		log.info("Enter into updatemeasures function with below request parameters ");
		log.info("Request Parameters = "+measures.toString());
		try {
			log.info("Calling service with request parameters.");
			measureService.save(measures);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Measuers updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating measure data. "+npe.getMessage());
			return Helper.findResponseStatus("Measure update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating measure data. "+e.getMessage());
			return Helper.findResponseStatus("Measure update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteMeasures/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteMeasures(@PathVariable Long id) {
		log.info("Enter into deleteMesure function");
		log.info("Selected Measure Id = "+id);
		try {
			measureService.deleteById(id);
			return Helper.findResponseStatus("Measures Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Measure data"+npe.getMessage());
			return Helper.findResponseStatus("Mesure Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting mesure data"+e.getMessage());
			return Helper.findResponseStatus("Measure Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	@RequestMapping(value = "/existsActivityId/{activityId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
		public Boolean existsActivityId(@PathVariable("activityId") String activityId ){
				
			try {
	            log.info("Request for checking exists activity Id...");
				return measureService.existsByActivityId(activityId);
			} catch (Exception e) {
				log.error("Error while checking exists activity Id..."+e.getMessage());
				return false;
			}
		}
	@RequestMapping(value = "/existsActivityName/{activityName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsActivityName(@PathVariable("activityName") String activityName ){
			
		try {
            log.info("Request for checking exists activity name...");
			return measureService.existsByActivityName(activityName);
		} catch (Exception e) {
			log.error("Error while checking exists activity name..."+e.getMessage());
			return false;
		}
	}
@RequestMapping(value = "/existsActivityNameAndUnitOfMeasure/{activityName}/{unitOfMeasure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsActivityNameAndUnitOfMeasure(@PathVariable("activityName") String activityName ,@PathVariable("unitOfMeasure") String unitOfMeasure){
			log.info("Exist====="+activityName+"unit"+unitOfMeasure);
		try {
            log.info("Request for checking exists activiyName and Unit of measure...");
			return measureService.existsByActivityNameAndUnitOfMeasure(activityName,unitOfMeasure);
		} catch (Exception e) {
			log.error("Error while checking exists activiyName and Unit of measure..."+e.getMessage());
			return false;
		}
	}

}
