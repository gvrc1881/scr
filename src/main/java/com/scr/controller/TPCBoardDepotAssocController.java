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
import com.scr.model.TpcBoardReportingFacility;
import com.scr.services.TPCBoardDepotAssocService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TPCBoardDepotAssocController {
	static Logger log = Logger.getLogger(TPCBoardDepotAssocController.class);

	@Autowired
	private TPCBoardDepotAssocService tpcBoardDepotAssocService;
	
	
	@RequestMapping(value = "/findAllTPCBoardDepotAssoc" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<TpcBoardReportingFacility> findAllTPCBoardDepotAssoc(){
		log.info("Enter into findAllTPCBoardDepotAssoc function");
		List<TpcBoardReportingFacility> tpcBoardDepotAssoc = null;
		try {
			log.info("Calling service for Tpc Board DepotAssoc data");
			tpcBoardDepotAssoc = tpcBoardDepotAssocService.findAll();
			log.info("Fetched Tpc Board DepotAssoc data"+tpcBoardDepotAssoc.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the Tpc Board DepotAssoc data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the Tpc Board DepotAssoc data = "+e.getMessage());
		}
		log.info("Exit from findAllTPCBoardDepotAssoc function");
		return tpcBoardDepotAssoc;
	}
	
	@RequestMapping(value = "/addTPCBoardDeotAssoc" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addTPCBoardDeotAssoc(@RequestBody TpcBoardReportingFacility tpcBoardReportingFacility) {
		log.info("Enter into addTPCBoardDeotAssoc function with below request parameters ");
		log.info("Request Parameters = "+tpcBoardReportingFacility.toString());
		try {
			log.info("Calling service with request parameters.");
			tpcBoardDepotAssocService.save(tpcBoardReportingFacility);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Tpc Board DepotAssoc added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Tpc Board DepotAssoc data. "+npe.getMessage());
			return Helper.findResponseStatus("Tpc Board DepotAssoc save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Tpc Board DepotAssoc data. "+e.getMessage());
			return Helper.findResponseStatus("Tpc Board DepotAssoc save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findTPCBoardDepotAssocById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<TpcBoardReportingFacility> findTPCBoardDepotAssocById(@PathVariable Long id){
		Optional<TpcBoardReportingFacility> tpcBoardDepotAssoc = null;
		try {
			log.info("Selected Tpc Board DepotAssoc Id = "+id);
			tpcBoardDepotAssoc = tpcBoardDepotAssocService.findTPCBoardDepotAssocById(id);
			if(tpcBoardDepotAssoc.isPresent()) {
				log.info("Tpc Board DepotAssoc Data = "+tpcBoardDepotAssoc.get());
				return new ResponseEntity<TpcBoardReportingFacility>(tpcBoardDepotAssoc.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<TpcBoardReportingFacility>(tpcBoardDepotAssoc.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Tpc Board DepotAssoc Details by id, "+e.getMessage());
			return new ResponseEntity<TpcBoardReportingFacility>(tpcBoardDepotAssoc.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updateTPCBoardDepotAssoc" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTPCBoardDepotAssoc(@RequestBody TpcBoardReportingFacility tpcBoardReportingFacility) {
		log.info("Enter into updateTPCBoardDepotAssoc function with below request parameters ");
		log.info("Request Parameters = "+tpcBoardReportingFacility.toString());
		try {
			log.info("Calling service with request parameters.");
			tpcBoardDepotAssocService.save(tpcBoardReportingFacility);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Tpc Board DepotAssoc updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating Tpc Board DepotAssoc data. "+npe.getMessage());
			return Helper.findResponseStatus("Tpc Board DepotAssoc update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating Tpc Board DepotAssoc data. "+e.getMessage());
			return Helper.findResponseStatus("Tpc Board DepotAssoc update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteTPCBoardDepotAssoc/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteTPCBoardDepotAssocById(@PathVariable Long id) {
		log.info("Enter into deleteTPCBoardDepotAssocById function");
		log.info("Selected Tpc Board DepotAssoc Id = "+id);
		try {
			tpcBoardDepotAssocService.deleteTPCBoardDepotAssocById(id);
			return Helper.findResponseStatus("Tpc Board DepotAssoc Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Tpc Board DepotAssoc data"+npe.getMessage());
			return Helper.findResponseStatus("Tpc Board DepotAssoc Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Tpc Board DepotAssoc data"+e.getMessage());
			return Helper.findResponseStatus("Tpc Board DepotAssoc Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
}