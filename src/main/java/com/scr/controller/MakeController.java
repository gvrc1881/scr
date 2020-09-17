package com.scr.controller;


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
import com.scr.message.response.ResponseStatus;

import com.scr.model.Make;
import com.scr.services.MakeService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class MakeController {
	
	private static Logger logger = Logger.getLogger(MakeController.class);
	
	@Autowired 
	MakeService makeService;
	
	@RequestMapping(value = "/addMake" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addMake(@RequestBody Make make) {
		
		logger.info("Enter into save function with below request parameters ");
		
		logger.info("Request Parameters = "+make.toString());
		
		make.setMakeCode(make.getMakeCode().toUpperCase());
		make.setMakeName(make.getMakeCode().toUpperCase());
		try {
			logger.info("Calling service with request parameters.");
		makeService.save(make);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("Make added successfully", Constants.SUCCESS_CODE);
		}
		
		catch(NullPointerException npe) {
			logger.error("ERROR >> While adding make data. "+npe.getMessage());
			return Helper.findResponseStatus("make save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding make data. "+e.getMessage());
			return Helper.findResponseStatus("make save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateMake" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateMake(@RequestBody Make make) {
		logger.info("Enter into update function with below request parameters ");
		logger.info("Request Parameters = "+make.toString());
		try {
			logger.info("Calling service with request parameters.");
		make.setMakeCode(make.getMakeCode().toUpperCase());
		//make.setMakeName(make.getMakeName().toUpperCase());
		makeService.save(make);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("Make updated successfully", Constants.SUCCESS_CODE);
	}catch(NullPointerException npe) {
		logger.error("ERROR >> While updating make data. "+npe.getMessage());
		return Helper.findResponseStatus("make update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
	}
	catch (Exception e) {
		logger.error("ERROR >> While updating make data. "+e.getMessage());
		return Helper.findResponseStatus("make update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
	}
		}
	

	@RequestMapping(value = "/deleteMake/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteMakeById(@PathVariable Long id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected make Id = "+id);
		try {
		makeService.deleteMakeById(id);
		return Helper.findResponseStatus("Make deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		logger.error("ERROR >> While deleting make data"+npe.getMessage());
		return Helper.findResponseStatus("make Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error("ERROR >> While deleting make data"+e.getMessage());
		return Helper.findResponseStatus("make Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	 @RequestMapping(value = "/findAllMake" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<Make> findAllMake() throws JSONException {
		 List<Make> make = null;
		 try {
			   logger.info("Calling service for make data");	
		
		 make = makeService.findAllOrderBymakeCodeAsc();
		 logger.info("Fetched make data***"+make.size());
		return make;
	}catch (NullPointerException npe) {
		logger.error("ERROR >>> while fetching the make data = "+npe.getMessage());
	}
	catch (Exception e) {
		logger.error("ERROR >>> while fetching the make data = "+e.getMessage());
	}
		 logger.info("Exit from make function");
	return make;	
}
	
	@RequestMapping(value = "/findMakeById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Make> findMakeById(@PathVariable("id") Long id){

		Optional<Make> make = null;
		try {
			logger.info("Selected make Id = "+id);
			
			make = makeService.findMakeById(id);
			if(make.isPresent()) {
				logger.info("make Data = "+make.get());
				return new ResponseEntity<Make>(make.get(), HttpStatus.OK);
				
			}
			else
				return new ResponseEntity<Make>(make.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find make Details by id, "+e.getMessage());
			return new ResponseEntity<Make>(make.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/existsMakeName/{makeName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsMakeName(@PathVariable("makeName") String makeName ){		
		try {
			return makeService.existsByMakeName(makeName);
		} catch (Exception e) {
			logger.error("Error while checking exists make Name.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsMakeCode/{makeCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsMakeCode(@PathVariable("makeCode") String makeCode){		
		try {
			return makeService.existsByMakeCode(makeCode.toUpperCase());
		} catch (Exception e) {
			logger.error("Error while checking exists make code.");
			return false;
		}
	}
	@RequestMapping(value = "/existsMakeCodeAndId/{id}/{makeCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsMakeCodeAndId(@PathVariable("id") Long id,@PathVariable("makeCode") String makeCode){
		
		logger.info("id=="+id+"makecode=="+makeCode);
		Boolean result;
		try {
			Optional<Make> makeData = makeService.findByMakeCode(makeCode);
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(makeData.isPresent()) {
				Make make = makeData.get();
				logger.info("***id ***"+make.getId());
				if (id.equals(make.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and makecode..."+e.getMessage());
			return false;
		}
	}
}