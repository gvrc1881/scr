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
import org.springframework.web.bind.annotation.RestController;
import com.scr.jobs.CommonUtility;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Facility;
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
	
	@Autowired
	private CommonUtility  commonUtility;
	
	
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
	@RequestMapping(value = "/findAllTPCBoardDepotAssocOnDivision/{loggedUserData}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TpcBoardReportingFacility> findAllTPCBoardDepotAssocBasedOnDivision(@PathVariable("loggedUserData") String loggedUserData) throws JSONException {
		List<TpcBoardReportingFacility> tpcBoardList = null;
		List<String> fac= new ArrayList<>();
		try {
			List<Facility> facility = commonUtility.findUserHierarchy(loggedUserData);
			for (Facility facility2 : facility) {
				
				fac.add(facility2.getDivision());
				
			}
			tpcBoardList = tpcBoardDepotAssocService.getAllOrderByTpcBoardAsc(fac);
		return tpcBoardList;
		} catch (NullPointerException e) {
			log.error(e);
		}
		catch (Exception e) {
			log.error(e);
		}
		return tpcBoardList;	
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
	
	@RequestMapping(value = "/existsTpcBoardAndUnitName/{tpcBoard}/{unitName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsTpcBoardAndDataDiv(@PathVariable("tpcBoard") String tpcBoard ,@PathVariable("unitName") String unitName){
			
		try {
			log.info("Request for checking exists tpcBoard and unitName.");
			return tpcBoardDepotAssocService.existsByTpcBoardAndUnitName(tpcBoard,unitName);	
		} catch (Exception e) {
			log.error("Error while checking exists tpcBoard and unitName..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existTpcBoardUnitNameAndId/{id}/{tpcBoard}/{unitName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existTpcBoardUnitNameAndId(@PathVariable("id") Long id,@PathVariable("tpcBoard") String tpcBoard,@PathVariable("unitName") String unitName){
		
		log.info("id=="+id+"tpcBoard=="+tpcBoard);
		Boolean result;
		try {
			Optional<TpcBoardReportingFacility> tpcBoardDepotAssoc = tpcBoardDepotAssocService.findByTpcBoardAndUnitName(tpcBoard,unitName);
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(tpcBoardDepotAssoc.isPresent()) {
				TpcBoardReportingFacility tpcBoardAssocDetails = tpcBoardDepotAssoc.get();
				log.info("***id ***"+tpcBoardAssocDetails.getId());
				if (id.equals(tpcBoardAssocDetails.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and unitName..."+e.getMessage());
			return false;
		}
	}
}