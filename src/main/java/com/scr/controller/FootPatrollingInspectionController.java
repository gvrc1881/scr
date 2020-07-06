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
import com.scr.model.Compliance;
import com.scr.model.FootPatrollingInspection;
import com.scr.model.Observation;
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
	
	
	//The Below code related to Foot patrollingInspection service
	
	//findAllFpInspection
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
	
	//Add FpInspection
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
	
	//findFpInspetionById
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
	
	//update FpInspection
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
	
	//delete fpInspection
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
	
	//The Below code related to Observation services
	
	//findAllObservations
	@RequestMapping(value = "/findAllObservationItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<Observation> findAllObservationItems(){
		log.info("Enter into findAllObservationItems function");
		List<Observation> observationItems = null;
		try {
			log.info("Calling service for  observations data");
			observationItems = footPatrollingInspectionService.findAllObservations();
			log.info("Fetched fp Inspection Item data ***"+observationItems.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the observations  Item data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the observations  Item data = "+e.getMessage());
		}
		log.info("Exit from findAllObservationItems function");
		return observationItems;
	}
	//Add observations
	@RequestMapping(value = "/addObservation" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addObservation(@RequestBody Observation observation) {
		log.info("Enter into addObservation function with below request parameters ");
		log.info("Request Parameters = "+observation.toString());
		try {
			log.info("Calling service with request parameters.");
			footPatrollingInspectionService.save(observation);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("observation added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding observation data. "+npe.getMessage());
			return Helper.findResponseStatus("observation save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding observation data. "+e.getMessage());
			return Helper.findResponseStatus("observation save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	//findObservationItemById
	@RequestMapping(value = "/findObservationItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Observation> findObservationItemById(@PathVariable Long id){
		Optional<Observation> observationItem = null;
		try {
			log.info("Selected  observationItem Id = "+id);
			observationItem = footPatrollingInspectionService.findObservationItemById(id);
			if(observationItem.isPresent()) {
				log.info("observationItem Data = "+observationItem.get());
				return new ResponseEntity<Observation>(observationItem.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<Observation>(observationItem.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find observationItem  Details by id, "+e.getMessage());
			return new ResponseEntity<Observation>(observationItem.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Update observationItem
	@RequestMapping(value = "/updateObservationItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateObservationItem(@RequestBody Observation observation) {
		log.info("Enter into updateObservationItem function with below request parameters ");
		log.info("Request Parameters = "+observation.toString());
		try {
			log.info("Calling service with request parameters.");
			footPatrollingInspectionService.save(observation);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("observationItem updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating  observationItem data. "+npe.getMessage());
			return Helper.findResponseStatus("observationItem  update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating  observationItem data. "+e.getMessage());
			return Helper.findResponseStatus("observationItem update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	//Delete ObservationItem
	@RequestMapping(value = "/deleteObservationItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteObservationItemById(@PathVariable Long id) {
		log.info("Enter into delete Observation ItemById function");
		log.info("Selected Observation Item Id = "+id);
		try {
			footPatrollingInspectionService.deleteObservationItemById(id);
			return Helper.findResponseStatus("ObservationItem Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting ObservationItem data"+npe.getMessage());
			return Helper.findResponseStatus("ObservationItem Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Observation Item data"+e.getMessage());
			return Helper.findResponseStatus("Observation Item Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

	
	//The Below code related to Compliance services
	
	//findAllCompliance
		@RequestMapping(value = "/findAllComplianceItems" , method = RequestMethod.GET , headers = "Accept=application/json")
		public List<Compliance> findAllComplianceItems(){
			log.info("Enter into findAllComplianceItems function");
			List<Compliance> complianceItem = null;
			try {
				log.info("Calling service for  Compliance data");
				complianceItem = footPatrollingInspectionService.findAllCompliances();
				log.info("Fetched Compliance Item data ***"+complianceItem.size());
			}catch (NullPointerException npe) {
				log.error("ERROR >>> while fetching the  Compliance Item data = "+npe.getMessage());
			}
			catch (Exception e) {
				log.error("ERROR >>> while fetching the   Compliance Item data = "+e.getMessage());
			}
			log.info("Exit from findAllComplianceItems function");
			return complianceItem;
		}
		
		//Add Compliance
		@RequestMapping(value = "/addCompliance" , method = RequestMethod.POST , headers = "Accept=application/json")
		public ResponseStatus addCompliance(@RequestBody Compliance compliance) {
			log.info("Enter into addCompliance function with below request parameters ");
			log.info("Request Parameters = "+compliance.toString());
			try {
				log.info("Calling service with request parameters.");
				footPatrollingInspectionService.save(compliance);
				log.info("Preparing the return response");
				return Helper.findResponseStatus("compliance added successfully", Constants.SUCCESS_CODE);
			}catch(NullPointerException npe) {
				log.error("ERROR >> While adding compliance data. "+npe.getMessage());
				return Helper.findResponseStatus("compliance save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
			}
			catch (Exception e) {
				log.error("ERROR >> While adding compliance data. "+e.getMessage());
				return Helper.findResponseStatus("compliance save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
		}
		//Add Compliance Ended
		
		//find complianceById
		@RequestMapping(value = "/findComplianceItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
		public ResponseEntity<Compliance> findComplianceItemById(@PathVariable Long id){
			Optional<Compliance> complianceItem = null;
			try {
				log.info("Selected  observationItem Id = "+id);
				complianceItem = footPatrollingInspectionService.findComplianceItemById(id);
				if(complianceItem.isPresent()) {
					log.info("complianceItem Data = "+complianceItem.get());
					return new ResponseEntity<Compliance>(complianceItem.get(), HttpStatus.OK);
				}
				else
					return new ResponseEntity<Compliance>(complianceItem.get(), HttpStatus.CONFLICT);
			} catch (Exception e) {
				log.error("Error >>  while find compliance Item  Details by id, "+e.getMessage());
				return new ResponseEntity<Compliance>(complianceItem.get(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		//update compliance
		@RequestMapping(value = "/updateComplianceItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
		public ResponseStatus updateComplianceItem(@RequestBody Compliance compliance) {
			log.info("Enter into update ComplianceItem function with below request parameters ");
			log.info("Request Parameters = "+compliance.toString());
			try {
				log.info("Calling service with request parameters.");
				footPatrollingInspectionService.save(compliance);
				log.info("Preparing the return response");
				return Helper.findResponseStatus("complianceItem updated successfully", Constants.SUCCESS_CODE);	
			}catch(NullPointerException npe) {
				log.error("ERROR >> While updating  complianceItem data. "+npe.getMessage());
				return Helper.findResponseStatus("complianceItem  update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
			}
			catch (Exception e) {
				log.error("ERROR >> While updating  complianceItem data. "+e.getMessage());
				return Helper.findResponseStatus("complianceItem update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
		}
		
		//Delete Compliance
		@RequestMapping(value = "/deleteComplianceItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
		public ResponseStatus deleteComplianceItemById(@PathVariable Long id) {
			log.info("Enter into delete ComplianceItemById function");
			log.info("Selected Compliance Item Id = "+id);
			try {
				footPatrollingInspectionService.deleteComplianceItemById(id);
				return Helper.findResponseStatus("Compliance Deleted successfully", Constants.SUCCESS_CODE);
			} catch (NullPointerException npe) {
				log.error("ERROR >> While deleting ComplianceItem data"+npe.getMessage());
				return Helper.findResponseStatus("ComplianceItem Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
			} catch (Exception e) {
				log.error("ERROR >> While deleting Compliance Item  data"+e.getMessage());
				return Helper.findResponseStatus("Compliance Item Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
			}
		}
}
