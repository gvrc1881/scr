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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.StandardPhases;
import com.scr.services.StandardPhaseService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@RestController
@RequestMapping("/scr/api")
public class StandardPhaseController {
private Logger log = Logger.getLogger(StandardPhaseController.class);
	
	@Autowired
	private StandardPhaseService standardPhaseService;
	
	@CrossOrigin(origins = "*")
	
	@RequestMapping(value="/findAllStandardPhases", method=RequestMethod.GET, headers = "Accept=application/json")
	public List<StandardPhases> findAllStandardPhases(){
		log.info("Enter into findAll StandardPhases function");
		List<StandardPhases> standardPhases=null;
		try {
			log.info("calling service for standardPhases Data");
			standardPhases=standardPhaseService.findAll();
			log.info("fetched standardPhases Data"+standardPhases.size());
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the standardPhases data = "+npe.getMessage());
			
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the standardPhases data = "+e.getMessage());
		}
		log.info("Exit from standard Phases function");
		return standardPhases;
	}
	
	@RequestMapping(value = "/findStandardPhasesById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<StandardPhases> findStandardPhasesById(@PathVariable("id") Integer id){

		Optional<StandardPhases> standardPhases = null;
		try {
			log.info("Selected standardPhases Id = "+id);
			
			standardPhases = standardPhaseService.findStandardPhasesById(id);
			if(standardPhases.isPresent()) {
				log.info("standardPhases Data = "+standardPhases.get());
				return new ResponseEntity<StandardPhases>(standardPhases.get(), HttpStatus.OK);
				
			}
			else
				return new ResponseEntity<StandardPhases>(standardPhases.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find StandardPhases Details by id, "+e.getMessage());
			return new ResponseEntity<StandardPhases>(standardPhases.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/addStandardPhases", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus save(@RequestBody StandardPhases standardPhases){
		log.info("Enter into save function with below request parameters ");
		log.info("Request Parameters = "+standardPhases.toString());		
		try {
			log.info("Calling service with request parameters.");
			standardPhaseService.save(standardPhases);
				log.info("Preparing the return response");
				return Helper.findResponseStatus("Standard Phases added successfully", Constants.SUCCESS_CODE);
			}catch(NullPointerException npe) {
				log.error("ERROR >> While adding Standard Phases data. "+npe.getMessage());
				return Helper.findResponseStatus("Standard Phases save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
			}
			catch (Exception e) {
				log.error("ERROR >> While adding Standard Phases data. "+e.getMessage());
				return Helper.findResponseStatus("Standard Phases save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
	}
	
	@RequestMapping(value = "/updateStandardPhases" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateStandardPhases(@RequestBody StandardPhases standardPhases) {
		log.info("*** Enter into updateStandardPhases function ***");
		try {			
			standardPhaseService.save(standardPhases);
			log.info("Preparing the return response ");
			return Helper.findResponseStatus("Standard phases  Data updated Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Standard phases Data. "+npe.getMessage());
			return Helper.findResponseStatus("Standard phase  Updation is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating Standard phases  Data. "+e.getMessage());
			return Helper.findResponseStatus("Standard phases  Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteStandardPhases/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteStandardPhasesById(@PathVariable Integer id) {
		log.info("Enter into deleteById function");
		log.info("Selected StandardPhases Id = "+id);
		try {
			standardPhaseService.deleteById(id);
		return Helper.findResponseStatus("StandardPhases deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		log.error("ERROR >> While deleting StandardPhases data"+npe.getMessage());
		return Helper.findResponseStatus("StandardPhases Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		log.error("ERROR >> While deleting StandardPhases data"+e.getMessage());
		return Helper.findResponseStatus("StandardPhases Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	@RequestMapping(value = "/existsDescriptionAndTypeOfWork/{description}/{typeOfWork}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDescriptionAndTypeOfWork(@PathVariable("description") String description ,@PathVariable("typeOfWork") String typeOfWork){
			
		try {
			log.info("Request for checking exists description and typeOfWork.");
			return standardPhaseService.existsByDescriptionAndTypeOfWork(description,typeOfWork);	
		} catch (Exception e) {
			log.error("Error while checking exists description and typeOfWork..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existDescriptionTypeOfWorkAndId/{id}/{description}/{typeOfWork}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existDescriptionTypeOfWorkAndId(@PathVariable("id") Integer id,@PathVariable("description") String description,@PathVariable("typeOfWork") String typeOfWork){
		
		log.info("id=="+id+"description=="+description);
		Boolean result;
		try {
			Optional<StandardPhases> standardPhasesData = standardPhaseService.findByDescriptionAndTypeOfWork(description,typeOfWork);
			if(standardPhasesData.isPresent()) {
				StandardPhases standardPhases = standardPhasesData.get();
				log.info("***id ***"+standardPhases.getId());
				if (id.equals(standardPhases.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and typeOfWork..."+e.getMessage());
			return false;
		}
	}	
	@RequestMapping(value = "/existsSequenceAndTypeOfWork/{sequence}/{typeOfWork}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsSequenceAndTypeOfWork(@PathVariable("sequence") Integer sequence ,@PathVariable("typeOfWork") String typeOfWork){
			
		try {
			log.info("Request for checking exists sequence and typeOfWork.");
			return standardPhaseService.existsBySequenceAndTypeOfWork(sequence,typeOfWork);	
		} catch (Exception e) {
			log.error("Error while checking exists sequence and typeOfWork..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existSequenceTypeOfWorkAndId/{id}/{sequence}/{typeOfWork}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existSequenceTypeOfWorkAndId(@PathVariable("id") Integer id,@PathVariable("sequence") Integer sequence,@PathVariable("typeOfWork") String typeOfWork){
		
		log.info("id=="+id+"sequence=="+sequence);
		Boolean result;
		try {
			Optional<StandardPhases> standardPhasesData = standardPhaseService.findBySequenceAndTypeOfWork(sequence,typeOfWork);
			if(standardPhasesData.isPresent()) {
				StandardPhases standardPhases = standardPhasesData.get();
				log.info("***id ***"+standardPhases.getId());
				if (id.equals(standardPhases.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and sequence..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existsNameAndTypeOfWork/{name}/{typeOfWork}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsNameAndTypeOfWork(@PathVariable("name") String name ,@PathVariable("typeOfWork") String typeOfWork){
			
		try {
			log.info("Request for checking exists name and typeOfWork.");
			return standardPhaseService.existsByNameAndTypeOfWork(name,typeOfWork);	
		} catch (Exception e) {
			log.error("Error while checking exists name and typeOfWork..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existNameTypeOfWorkAndId/{id}/{sequence}/{typeOfWork}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existSequenceTypeOfWorkAndId(@PathVariable("id") Integer id,@PathVariable("name") String name,@PathVariable("typeOfWork") String typeOfWork){
		
		log.info("id=="+id+"name=="+name);
		Boolean result;
		try {
			Optional<StandardPhases> standardPhasesData = standardPhaseService.findByNameAndTypeOfWork(name,typeOfWork);
			if(standardPhasesData.isPresent()) {
				StandardPhases standardPhases = standardPhasesData.get();
				log.info("***id ***"+standardPhases.getId());
				if (id.equals(standardPhases.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and name..."+e.getMessage());
			return false;
		}
	}
}
