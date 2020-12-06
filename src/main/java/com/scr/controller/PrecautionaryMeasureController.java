package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
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
import com.scr.model.PrecautionaryMeasure;
import com.scr.services.PrecautionaryMeasureService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class PrecautionaryMeasureController {
	
	static Logger logger = LogManager.getLogger(PrecautionaryMeasureController.class);
	
	
	@Autowired
	private PrecautionaryMeasureService precautionaryMeasureService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllPrecautionaryMeasures", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<PrecautionaryMeasure> findAllPrecautionaryMeasures() throws JSONException {
		logger.info("Enter into findAll PrecautionaryMeasure function");
		List<PrecautionaryMeasure> precautionaryMeasureList = null;
		try {
			logger.info("Calling service for PrecautionaryMeasure data");
			precautionaryMeasureList = precautionaryMeasureService.findAll();
			logger.info("Fetched precautionaryMeasure data = " + precautionaryMeasureList.size());
			return precautionaryMeasureList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the PrecautionaryMeasure data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the PrecautionaryMeasure data = " + e.getMessage());
		}
		logger.info("Exit from findAll PrecautionaryMeasure function");
		return precautionaryMeasureList;
	}
	@RequestMapping(value="/addPrecautionaryMeasures",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addPrecautionaryMeasures(@RequestBody PrecautionaryMeasure precautionaryMeasure) {
		logger.info("Enter into addPrecautionaryMeasures function with below request parameters ");
		logger.info("Request Parameters = "+precautionaryMeasure.toString());
		try {
			logger.info("Calling service with request parameters.");
			precautionaryMeasureService.save(precautionaryMeasure);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("precautionaryMeasure Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding precautionaryMeasure data. "+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding precautionaryMeasure data. "+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findPrecautionaryMeasureById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<PrecautionaryMeasure> findPrecautionaryMeasureById(@PathVariable Long id){
		Optional<PrecautionaryMeasure> precautionaryMeasure = null;
		try {
			logger.info("Selected precautionaryMeasure Id = "+id);
			precautionaryMeasure = precautionaryMeasureService.findPrecautionaryMeasureById(id);
			if(precautionaryMeasure.isPresent()) {
				logger.info("PrecautionaryMeasure Data = "+precautionaryMeasure.get());
				return new ResponseEntity<PrecautionaryMeasure>(precautionaryMeasure.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<PrecautionaryMeasure>(precautionaryMeasure.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find PrecautionaryMeasure Details by id, "+e.getMessage());
			return new ResponseEntity<PrecautionaryMeasure>(precautionaryMeasure.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updatePrecautionaryMeasure" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updatePrecautionaryMeasure(@RequestBody PrecautionaryMeasure precautionaryMeasure) {
		logger.info("Enter into updatePrecautionaryMeasure function with below request parameters ");
		logger.info("Request Parameters = "+precautionaryMeasure.toString());
		try {
			logger.info("Calling service with request parameters.");
			precautionaryMeasureService.save(precautionaryMeasure);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("precautionaryMeasure Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating precautionaryMeasure data. "+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating precautionaryMeasure data. "+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deletePrecautionaryMeasure/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deletePrecautionaryMeasureById(@PathVariable Long id) {
		logger.info("Enter into deletePrecautionaryMeasureById function");
		logger.info("Selected precautionaryMeasure Id = "+id);
		try {
			precautionaryMeasureService.deletePrecautionaryMeasureById(id);
			return Helper.findResponseStatus("precautionaryMeasure deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting precautionaryMeasure data"+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting precautionaryMeasure data"+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

}
