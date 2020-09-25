package com.scr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.scr.message.request.InspectionRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Compliance;
import com.scr.model.ContentManagement;
import com.scr.model.FootPatrollingInspection;
import com.scr.model.Inspection;
import com.scr.model.Observation;
import com.scr.services.ContentManagementService;
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
	
	@Autowired
	private ContentManagementService contentManagementService;
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
	@PostMapping(value="/addObservation",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseStatus addObservation(
			@RequestParam("file") List<MultipartFile> file, 
			@RequestParam("inspectionSeqId") String inspectionSeqId,
			@RequestParam("location") String location,
			@RequestParam("observationCategory") String observationCategory,
			@RequestParam("observationItem") String observationItem,
			@RequestParam("description") String description,
			@RequestParam("actionRequired") String actionRequired,
			@RequestParam("createdBy") String createdBy
			) {
		try {
			log.info("add Observation");
			InspectionRequest observationsRequest = new InspectionRequest();
			observationsRequest.setInspectionSeqId(inspectionSeqId);
			observationsRequest.setLocation(location);
			observationsRequest.setObservationCategory(observationCategory);
			observationsRequest.setObservationItem(observationItem);
			observationsRequest.setDescription(description);
			observationsRequest.setActionRequired(actionRequired);
			observationsRequest.setCreatedBy(createdBy);
			footPatrollingInspectionService.save(observationsRequest, file);
			return Helper.findResponseStatus("Observations Data Added Successfully", Constants.SUCCESS_CODE);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR >> While adding Observations data. "+e.getMessage());
			return Helper.findResponseStatus("Observations Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	//Update Observations
	@PutMapping("/updateObservationItem")
	@ResponseBody
	public ResponseStatus updateObservationItem(
			@RequestParam("file") List<MultipartFile> file, 
			@RequestParam("id") Long id,
			@RequestParam("inspectionSeqId") String inspectionSeqId,
			@RequestParam("location") String location,
			@RequestParam("observationCategory") String observationCategory,
			@RequestParam("observationItem") String observationItem,
			@RequestParam("description") String description,
			@RequestParam("actionRequired") String actionRequired,
			@RequestParam("updatedBy") String updatedBy,
			@RequestParam("attachment") String attachment) {
		try {
			log.info("Update Observations");
			InspectionRequest observationsRequest = new InspectionRequest();
			observationsRequest.setId(id);
			observationsRequest.setInspectionSeqId(inspectionSeqId);
			observationsRequest.setLocation(location);
			observationsRequest.setObservationCategory(observationCategory);
			observationsRequest.setObservationItem(observationItem);
			observationsRequest.setDescription(description);
			observationsRequest.setActionRequired(actionRequired);
			observationsRequest.setUpdatedBy(updatedBy);
			observationsRequest.setAttachment(attachment);
			log.info("calling update observations");
			String status = footPatrollingInspectionService.updateObservationsData(observationsRequest, file);
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("Observations Data Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
		}catch (Exception e) {
			log.error("ERROR >> While updating Observations data. "+e.getMessage());
			return Helper.findResponseStatus("Observations Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
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
	
	@RequestMapping(value = "/attachedObservationList/{observationId}", method = RequestMethod.GET ,headers = "Accept=application/json")	
	public ResponseEntity<List<ContentManagement>> getDocumentList( @PathVariable("observationId") Long observationId){
		List<ContentManagement> contentManagementList = new ArrayList<>();
		try {
			log.info("Getting observation  Details  = "+observationId);	
			Optional<Observation> obseravtionsObj =footPatrollingInspectionService.findById(observationId);
			if (obseravtionsObj.isPresent()) {
				Observation observation = obseravtionsObj.get();
				if(observation.getAttachment() != null) {
					contentManagementList = contentManagementService.findByCommonFileId(Long.parseLong(observation.getAttachment()));
				}	
				log.info("content size:::"+contentManagementList.size());
			}
			return new ResponseEntity<List<ContentManagement>>(contentManagementList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while getting DivisionHistory Details"+e.getMessage());
			return new ResponseEntity<List<ContentManagement>>(contentManagementList, HttpStatus.CONFLICT);
		}	
	}
	@RequestMapping(value = "/observationsContentById/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<ContentManagement>> findObservationsContentDataById(@PathVariable("id") Long commonFileId){
		List<ContentManagement> depOptional= null;
		try {
			log.info("common fileid = "+commonFileId);
			depOptional = footPatrollingInspectionService.findObservationsContentById(commonFileId);
			if(depOptional != null)
				return new ResponseEntity<List<ContentManagement>>(depOptional, HttpStatus.OK);
			else
				return new ResponseEntity<List<ContentManagement>>(depOptional, HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			log.error("Error while finding the Content Management Files by id "+e.getMessage());
			return new ResponseEntity<List<ContentManagement>>(depOptional, HttpStatus.INTERNAL_SERVER_ERROR);
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
		//Add Compliances
		@PostMapping(value="/addCompliance",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		@ResponseBody
		public ResponseStatus addCompliance(
				@RequestParam("file") List<MultipartFile> file, 
				@RequestParam("obeservationSeqId") String obeservationSeqId,
				@RequestParam("status") String status,
				@RequestParam("action") String action,
				@RequestParam("complianceBy") String complianceBy,
				@RequestParam("compliedDateTime") String compliedDateTime,
				@RequestParam("createdBy") String createdBy
				) {
			try {
				log.info("add Compliances");
				InspectionRequest complianceRequest = new InspectionRequest();
				complianceRequest.setObeservationSeqId(obeservationSeqId);
				complianceRequest.setStatus(status);
				complianceRequest.setAction(action);
				complianceRequest.setComplianceBy(complianceBy);
				complianceRequest.setCompliedDateTime(Helper.convertStringToTimestamp(compliedDateTime));
				complianceRequest.setCreatedBy(createdBy);
				footPatrollingInspectionService.saveCompliances(complianceRequest, file);
				return Helper.findResponseStatus("Compliances Data Added Successfully", Constants.SUCCESS_CODE);
			}catch (Exception e) {
				e.printStackTrace();
				log.error("ERROR >> While adding Compliances data. "+e.getMessage());
				return Helper.findResponseStatus("Compliances Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
		}
		//Update Compliances
		@PutMapping("/updateComplianceItem")
		@ResponseBody
		public ResponseStatus updateComplianceItem(
				@RequestParam("file") List<MultipartFile> file, 
				@RequestParam("id") Long id,
				@RequestParam("obeservationSeqId") String obeservationSeqId,
				@RequestParam("status") String status,
				@RequestParam("action") String action,
				@RequestParam("complianceBy") String complianceBy,
				@RequestParam("compliedDateTime") String compliedDateTime,
				@RequestParam("updatedBy") String updatedBy,
				@RequestParam("attachment") String attachment) {
			try {
				log.info("Update Observations");
				InspectionRequest compliancesRequest = new InspectionRequest();
				compliancesRequest.setId(id);
				compliancesRequest.setObeservationSeqId(obeservationSeqId);
				compliancesRequest.setStatus(status);
				compliancesRequest.setAction(action);
				compliancesRequest.setComplianceBy(complianceBy);
				compliancesRequest.setCompliedDateTime(Helper.convertStringToTimestamp(compliedDateTime));
				compliancesRequest.setUpdatedBy(updatedBy);
				compliancesRequest.setAttachment(attachment);
				log.info("calling update observations");
				String ComStatus = footPatrollingInspectionService.updateCompliancesData(compliancesRequest, file);
				if(ComStatus.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
					return Helper.findResponseStatus("Compliances Data Updated Successfully", Constants.SUCCESS_CODE);
				else
					return Helper.findResponseStatus(status, Constants.FAILURE_CODE);
			}catch (Exception e) {
				log.error("ERROR >> While updating Compliances data. "+e.getMessage());
				return Helper.findResponseStatus("Compliances Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
		}
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
		@RequestMapping(value = "/complianceStatus", method = RequestMethod.GET ,headers = "accept=application/json")	
		public ResponseEntity<List<Compliance>> findByStatus(String status){
			List<Compliance> complianceStatus= footPatrollingInspectionService.findByStatus(status);
			return new ResponseEntity<List<Compliance>>(complianceStatus,HttpStatus.OK);	
			
		}
		
		
		//search list for observations
		
		@RequestMapping(value = "/inspectionIdByFacilityId/{section}/{facilityId}/{nameOfStaff}/{fromDateTime}",method = RequestMethod.GET  , headers="accept=application/json" )
		public ResponseEntity<List<Inspection>> findInspectionIdByFacilityId(
				@PathVariable("section") String section,
				@PathVariable("facilityId") String facilityId ,
				@PathVariable("nameOfStaff") String nameOfStaff,
				@PathVariable("fromDateTime") String fromDateTime){
			log.info("Section = "+section +" FacilityId = "+facilityId+" NameOfStaff = "+nameOfStaff+" FromDateTime = "+fromDateTime);
			List<Inspection> inspectionList= footPatrollingInspectionService.findObservation(section,facilityId,nameOfStaff,Helper.convertStringToTimestamp(fromDateTime));
			log.info("inspectionListSize"+inspectionList.size());
			log.info("inspectionList"+inspectionList);
				return new ResponseEntity<List<Inspection>>(inspectionList, HttpStatus.OK);		
		}
		
}
