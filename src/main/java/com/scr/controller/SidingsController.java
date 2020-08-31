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
import com.scr.model.SidingDetails;
import com.scr.services.SidingsService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SidingsController {
	private static final Long id = null;

	static Logger log = LogManager.getLogger(SidingsController.class);

	@Autowired
	private SidingsService sidingsService;
	

	
	@RequestMapping(value = "/findAllSidingsItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<SidingDetails> findAllSidingsItems(){
		log.info("Enter into findAllSidingsItems function");
		List<SidingDetails> sidingsItem = null;
		try {
			log.info("Calling service for sidings Item data");
			sidingsItem = sidingsService.findAll();
			log.info("Fetched sidings Item data ***"+sidingsItem.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the sidings Item data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the sidings Item data = "+e.getMessage());
		}
		log.info("Exit from findAllSidingsItems function");
		return sidingsItem;
	}
	@RequestMapping(value = "/addSlidingsItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addSlidingsItem(@RequestBody SidingDetails sidingDetails) {
		log.info("Enter into addSlidingsItem function with below request parameters ");
		log.info("Request Parameters = "+sidingDetails.toString());
		try {
			log.info("Calling service with request parameters.");
			sidingsService.save(sidingDetails);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Sidings Item added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Sidings Item data. "+npe.getMessage());
			return Helper.findResponseStatus("Sidings Item save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Sidings Item data. "+e.getMessage());
			return Helper.findResponseStatus("Sidings Item save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findSidingsItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<SidingDetails> findSidingsItemById(@PathVariable Long id){
		Optional<SidingDetails> sidingsItem = null;
		try {
			log.info("Selected sidings Item Id = "+id);
			sidingsItem = sidingsService.findSidingsItemById(id);
			if(sidingsItem.isPresent()) {
				log.info("sidings Item Data = "+sidingsItem.get());
				return new ResponseEntity<SidingDetails>(sidingsItem.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<SidingDetails>(sidingsItem.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find sidings Item Details by id, "+e.getMessage());
			return new ResponseEntity<SidingDetails>(sidingsItem.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateSlidingsItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateSlidingsItem(@RequestBody SidingDetails sidingDetails) {
		log.info("Enter into updateSlidingsItem function with below request parameters ");
		log.info("Request Parameters = "+sidingDetails.toString());
		try {
			log.info("Calling service with request parameters.");
			sidingsService.save(sidingDetails);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("siding Details updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating siding Details data. "+npe.getMessage());
			return Helper.findResponseStatus("siding Details update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating siding Details data. "+e.getMessage());
			return Helper.findResponseStatus("siding Details update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/deleteSidingsItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteSidingsItemById(@PathVariable Long id) {
		log.info("Enter into deleteSidingsItemById function");
		log.info("Selected Sidings Item Id = "+id);
		try {
			sidingsService.deleteSidingsItemById(id);
			return Helper.findResponseStatus("Sidings Item deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Sidings Item data"+npe.getMessage());
			return Helper.findResponseStatus("Sidings Item Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Sidings Item data"+e.getMessage());
			return Helper.findResponseStatus("Sidings Item Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existsSidingCode/{sidingCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsSidingCode(@PathVariable("sidingCode") String sidingCode){		
		try {
			return sidingsService.existsBySidingCode(sidingCode);
		} catch (Exception e) {
			log.error("Error while checking exists sidingCode.");
			return false;
		}
	}
	@RequestMapping(value = "/findBySidingCodeAndId/{id}/{sidingCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean findBySidingCodeAndId(@PathVariable("id") Long id,@PathVariable("sidingCode") String sidingCode){
		
		log.info("id=="+id+"sidingCode=="+sidingCode);
		Boolean result;
		try {
			Optional<SidingDetails> sidingsData = sidingsService.findBySidingCode(sidingCode);
			
			if(sidingsData.isPresent()) {
				SidingDetails sidingDetails = sidingsData.get();
				log.info("***id ***"+sidingDetails.getId());
				if (id.equals(sidingDetails.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and sidingCode..."+e.getMessage());
			return false;
		}
	}
}
