package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
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
import com.scr.model.FootPatrollingInspection;
import com.scr.services.FootPatrollingInspectionService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class FootPatrollingInspectionController {
	static Logger log = LogManager.getLogger(FootPatrollingInspectionController.class);

	@Autowired
	private FootPatrollingInspectionService footPatrollingInspectionService;
	
	@RequestMapping(value = "/findAllFPInspectionItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<FootPatrollingInspection> findAllFPInspectionItems(){
		log.info("Enter into findAllFPInspectionItems function");
		List<FootPatrollingInspection> fpInspectionItem = null;
		try {
			log.info("Calling service for  fpInspectionItem data");
			fpInspectionItem = footPatrollingInspectionService.findAll();
			log.info("Fetched fp Inspection Item data ***"+fpInspectionItem.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the fp Inspection Item data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the fp Inspection Item data = "+e.getMessage());
		}
		log.info("Exit from findAllFPInspectionItems function");
		return fpInspectionItem;
	}
	
	@RequestMapping(value = "/addFPInspection" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addFPInspection(@RequestBody FootPatrollingInspection footPatrollingInspection) {
		log.info("Enter into addFPInspection function with below request parameters ");
		log.info("Request Parameters = "+footPatrollingInspection.toString());
		try {
			log.info("Calling service with request parameters.");
			footPatrollingInspectionService.save(footPatrollingInspection);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Foot PatrollingInspection added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Foot PatrollingInspection data. "+npe.getMessage());
			return Helper.findResponseStatus("Foot PatrollingInspection save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Foot PatrollingInspection data. "+e.getMessage());
			return Helper.findResponseStatus("Foot PatrollingInspection save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/findFPInspectionItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<FootPatrollingInspection> findFPInspectionItemById(@PathVariable Long id){
		Optional<FootPatrollingInspection> footPatrollingInspection = null;
		try {
			log.info("Selected FootPatrolling Inspection Id = "+id);
			footPatrollingInspection = footPatrollingInspectionService.findFPInspectionItemById(id);
			if(footPatrollingInspection.isPresent()) {
				log.info("FootPatrolling Inspection Data = "+footPatrollingInspection.get());
				return new ResponseEntity<FootPatrollingInspection>(footPatrollingInspection.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<FootPatrollingInspection>(footPatrollingInspection.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find FootPatrolling Inspection Details by id, "+e.getMessage());
			return new ResponseEntity<FootPatrollingInspection>(footPatrollingInspection.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateFPInspectionItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateFPInspectionItem(@RequestBody FootPatrollingInspection footPatrollingInspection) {
		log.info("Enter into updateFPInspectionItem function with below request parameters ");
		log.info("Request Parameters = "+footPatrollingInspection.toString());
		try {
			log.info("Calling service with request parameters.");
			footPatrollingInspectionService.save(footPatrollingInspection);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Foot Patrolling Inspection updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating FootPatrolling Inspection data. "+npe.getMessage());
			return Helper.findResponseStatus("FootPatrolling Inspection update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating FootPatrolling Inspection data. "+e.getMessage());
			return Helper.findResponseStatus("FootPatrolling Inspection update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteFPInspectionItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteFPInspectionItemById(@PathVariable Long id) {
		log.info("Enter into delete FP InspectionItemById function");
		log.info("Selected Foot Patrolling Inspection Id = "+id);
		try {
			footPatrollingInspectionService.deleteFPInspectionItemById(id);
			return Helper.findResponseStatus("Foot Patrolling Inspection Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Foot Patrolling Inspection data"+npe.getMessage());
			return Helper.findResponseStatus("Foot Patrolling Inspection Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Foot Patrolling Inspection data"+e.getMessage());
			return Helper.findResponseStatus("Foot Patrolling Inspection Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

}
