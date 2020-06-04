package com.scr.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.FootPatrollingSection;
import com.scr.services.FootPatrollingSectionsService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class FootPatrollingSectionsController {
	static Logger log = LogManager.getLogger(FootPatrollingSectionsController.class);

	@Autowired
	private FootPatrollingSectionsService footPatrollingSectionsService;
	
	
	@RequestMapping(value = "/findAllFPSectionsItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<FootPatrollingSection> findAllFPSectionsItems(){
		log.info("Enter into findAllFPSectionsItems function");
		List<FootPatrollingSection> fpSectionsItem = null;
		try {
			log.info("Calling service for  fpSectionsItem data");
			fpSectionsItem = footPatrollingSectionsService.findAll();
			log.info("Fetched fp SectionsItem data ***"+fpSectionsItem.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the fp SectionsItem data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the fpSectionsItem data = "+e.getMessage());
		}
		log.info("Exit from findAllFPSectionsItems function");
		return fpSectionsItem;
	}
	
	@RequestMapping(value = "/addFPSectionsItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addFPSectionsItem(@RequestBody FootPatrollingSection footPatrollingSection) {
		log.info("Enter into addFPSectionsItem function with below request parameters ");
		log.info("Request Parameters = "+footPatrollingSection.toString());
		try {
			log.info("Calling service with request parameters.");
			footPatrollingSectionsService.save(footPatrollingSection);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Foot PatrollingSections added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Foot PatrollingSections data. "+npe.getMessage());
			return Helper.findResponseStatus("Foot PatrollingSections save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Foot PatrollingSections data. "+e.getMessage());
			return Helper.findResponseStatus("Foot PatrollingSections save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findFPSectionsItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<FootPatrollingSection> findFPSectionsItemById(@PathVariable Long id){
		Optional<FootPatrollingSection> footPatrollingSections = null;
		try {
			log.info("Selected FootPatrolling Section Id = "+id);
			footPatrollingSections = footPatrollingSectionsService.findFPSectionsItemById(id);
			if(footPatrollingSections.isPresent()) {
				log.info("FootPatrolling Section Data = "+footPatrollingSections.get());
				return new ResponseEntity<FootPatrollingSection>(footPatrollingSections.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<FootPatrollingSection>(footPatrollingSections.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find FootPatrolling Section Details by id, "+e.getMessage());
			return new ResponseEntity<FootPatrollingSection>(footPatrollingSections.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateFPSectionsItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateFPSectionsItem(@RequestBody FootPatrollingSection footPatrollingSection) {
		log.info("Enter into updateFPSectionsItem function with below request parameters ");
		log.info("Request Parameters = "+footPatrollingSection.toString());
		try {
			log.info("Calling service with request parameters.");
			footPatrollingSectionsService.save(footPatrollingSection);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("FootPatrolling Section updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating FootPatrolling Section data. "+npe.getMessage());
			return Helper.findResponseStatus("FootPatrolling Section update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating FootPatrolling Section data. "+e.getMessage());
			return Helper.findResponseStatus("FootPatrolling Section update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteFPSectionsItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteFPSectionsItemById(@PathVariable Long id) {
		log.info("Enter into deleteFPSectionsItemById function");
		log.info("Selected Foot Patrolling sections Id = "+id);
		try {
			footPatrollingSectionsService.deleteFPSectionsItemById(id);
			return Helper.findResponseStatus("Foot Patrolling sections Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Foot Patrolling sections data"+npe.getMessage());
			return Helper.findResponseStatus("Foot Patrolling sections Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Foot Patrolling sections data"+e.getMessage());
			return Helper.findResponseStatus("Foot Patrolling sections Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	@RequestMapping(value = "/existsFpSection/{fpSection}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDepartmentName(@PathVariable("fpSection") String fpSection){

		try {
			return footPatrollingSectionsService.existsByFpSection(fpSection);
		} catch (Exception e) {
			log.error("Error while checking exists fpSections.");
			return false;
		}
	}
}
