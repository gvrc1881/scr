package com.scr.controller;

import java.util.ArrayList;
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

import com.scr.jobs.CommonUtility;
import com.scr.message.response.ResponseStatus;
import com.scr.model.EnergyMeter;
import com.scr.model.Facility;
import com.scr.model.Make;
import com.scr.model.TssFeederMaster;
import com.scr.services.TssFeederMasterService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TssFeederMasterController {
	
	@Autowired
	private TssFeederMasterService tssFeederMasterService;
	
	@Autowired
	private CommonUtility  commonUtility;
	
	static Logger log = Logger.getLogger(TssFeederMasterController.class);
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllTssFeederMaster", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TssFeederMaster> tssFeederMasterList() throws JSONException {
		List<TssFeederMaster> tssFeederMasterList = null;
		try {
			tssFeederMasterList = tssFeederMasterService.findAllOrderByFeederNameAsc();
		return tssFeederMasterList;
		} catch (NullPointerException e) {
			log.error(e);
		}
		catch (Exception e) {
			log.error(e);
		}
		return tssFeederMasterList;	
	}
	
	@RequestMapping(value = "/findAllFeedersBAsedOnDivision/{loggedUserData}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TssFeederMaster> findAllFeedersBAsedOnDivision(@PathVariable("loggedUserData") String loggedUserData) throws JSONException {
		List<TssFeederMaster> tssFeederMasterList = null;
		List<String> fac= new ArrayList<>();
		try {
			List<Facility> facility = commonUtility.findUserHierarchy(loggedUserData);
			for (Facility facility2 : facility) {
				
				fac.add(facility2.getDivision());
				
			}
			tssFeederMasterList = tssFeederMasterService.getAllOrderByFeederNameAsc(fac);
		return tssFeederMasterList;
		} catch (NullPointerException e) {
			log.error(e);
		}
		catch (Exception e) {
			log.error(e);
		}
		return tssFeederMasterList;	
	}
	@RequestMapping(value = "/addFeeder", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveFeeder(@RequestBody TssFeederMaster feeder){
		log.info("Enter into save Feeder function with below request parameters ");
		log.info("Request Parameters = "+feeder.toString());
		//feeder.setDataDiv(feeder.getdivision().toUpperCase());
		try {
			log.info("Calling service with request parameters.");
			TssFeederMaster tss = tssFeederMasterService.save(feeder);
			feeder.setFeederId(tss.getId().toString());
			tssFeederMasterService.save(feeder);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Feeder added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Feeder data. "+npe.getMessage());
			return Helper.findResponseStatus("Feeder save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding feeder data. "+e.getMessage());
			return Helper.findResponseStatus("Feeder save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findFeederById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<TssFeederMaster> findById(@PathVariable("id") Long id){
		Optional<TssFeederMaster> feeder = null;
		try {
			log.info("Selected feeder Id = "+id);
			feeder = tssFeederMasterService.findById(id);
			if(feeder.isPresent()) {
				log.info("Measure Data = "+feeder.get());
				return new ResponseEntity<TssFeederMaster>(feeder.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<TssFeederMaster>(feeder.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			log.error("Error >>  while find feeder Details by id, "+e.getMessage());
			return new ResponseEntity<TssFeederMaster>(feeder.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updateFeeder" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateFeeder(@RequestBody TssFeederMaster feeder) {
		log.info("Enter into updatefeeder function with below request parameters ");
		log.info("Request Parameters = "+feeder.toString());
		try {
			log.info("Calling service with request parameters.");
			TssFeederMaster tss = tssFeederMasterService.save(feeder);
			feeder.setFeederId(tss.getId().toString());
			tssFeederMasterService.save(feeder);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Feeder updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating feeder data. "+npe.getMessage());
			return Helper.findResponseStatus("Feeder update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating feeder data. "+e.getMessage());
			return Helper.findResponseStatus("Feeder update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteFeeder/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteFeeder(@PathVariable Long id) {
		log.info("Enter into deleteFeeder function");
		log.info("Selected Feeder Id = "+id);
		try {
			tssFeederMasterService.deleteById(id);
			return Helper.findResponseStatus("Feeder Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Feeder data"+npe.getMessage());
			return Helper.findResponseStatus("Feeder Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting feeder data"+e.getMessage());
			return Helper.findResponseStatus("Feeder Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existsFeederName/{feederName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFeederName(@PathVariable("feederName") String feederName ){
			
		try {
            log.info("Request for checking exists feeder name...");
			return tssFeederMasterService.existsByFeederName(feederName);
		} catch (Exception e) {
			log.error("Error while checking exists feeder name..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existsFeederNameAndId/{id}/{feederName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFeederNameAndId(@PathVariable("id") Long id,@PathVariable("feederName") String feederName){
		
		log.info("id=="+id+"feederName=="+feederName);
		Boolean result;
		try {
			Optional<TssFeederMaster> tssData = tssFeederMasterService.findByFeederName(feederName);
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(tssData.isPresent()) {
				TssFeederMaster tss = tssData.get();
				log.info("***id ***"+tss.getId());
				if (id.equals(tss.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and feederName..."+e.getMessage());
			return false;
		}
	}

}
