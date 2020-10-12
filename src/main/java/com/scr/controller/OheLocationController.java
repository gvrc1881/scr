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
import com.scr.model.OheLocation;
import com.scr.services.OheLocationService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class OheLocationController {
	static Logger log = LogManager.getLogger(OheLocationController.class);

	@Autowired
	private OheLocationService oheLocationService;
	
	@RequestMapping(value = "/findAllOheLocationItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<OheLocation> findAllOheLocationItems(){
		log.info("Enter into findAllOheLocationItems function");
		List<OheLocation> oheLocationItem = null;
		try {
			log.info("Calling service for  oheLocation data");
			oheLocationItem = oheLocationService.findAll();
			log.info("Fetched oheLocationItem data ***"+oheLocationItem.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the oheLocation data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the oheLocation data = "+e.getMessage());
		}
		log.info("Exit from findAllOheLocationItems function");
		return oheLocationItem;
	}
	
	@RequestMapping(value = "/addOheLocationItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addOheLocationItem(@RequestBody OheLocation oheLocation) {
		log.info("Enter into addOheLocationItem function with below request parameters ");
		log.info("Request Parameters = "+oheLocation.toString());
		try {
			log.info("Calling service with request parameters.");
			oheLocationService.save(oheLocation);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("OheLocation added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding OheLocation data. "+npe.getMessage());
			return Helper.findResponseStatus("OheLocation Data save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding OheLocation data. "+e.getMessage());
			return Helper.findResponseStatus("OheLocation save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findOheLocationById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<OheLocation> findOheLocationById(@PathVariable Long id){
		Optional<OheLocation> oheLocation = null;
		try {
			log.info("Selected OheLocation Id = "+id);
			oheLocation = oheLocationService.findOheLocationById(id);
			if(oheLocation.isPresent()) {
				log.info("OheLocation Data = "+oheLocation.get());
				return new ResponseEntity<OheLocation>(oheLocation.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<OheLocation>(oheLocation.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find OheLocation Details by id, "+e.getMessage());
			return new ResponseEntity<OheLocation>(oheLocation.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateOheLocation" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateOheLocation(@RequestBody OheLocation oheLocation) {
		log.info("Enter into updateOheLocation function with below request parameters ");
		log.info("Request Parameters = "+oheLocation.toString());
		try {
			log.info("Calling service with request parameters.");
			oheLocationService.save(oheLocation);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Ohe Location updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating Ohe Location. "+npe.getMessage());
			return Helper.findResponseStatus("Ohe Location update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating Ohe Location data. "+e.getMessage());
			return Helper.findResponseStatus("Ohe Location update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteOheLocation/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteOheLocationById(@PathVariable Long id) {
		log.info("Enter into deleteOheLocationById function");
		log.info("Selected  Ohe Location Data Id = "+id);
		try {
			oheLocationService.deleteOheLocationById(id);
			return Helper.findResponseStatus("Ohe Location Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Ohe Location Data"+npe.getMessage());
			return Helper.findResponseStatus("Ohe Location Data Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Ohe Location Data"+e.getMessage());
			return Helper.findResponseStatus("Ohe Location Data Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
}
