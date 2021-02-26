package com.scr.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.request.OheThermovisionMeasureRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.message.response.ThermovisionMeasureResponse;
import com.scr.model.Facility;
import com.scr.model.ThermovisionMeasures;
import com.scr.services.ThermovisionMeasuresServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ThermovisionMeasuresController {
	
	private Logger logger = Logger.getLogger(ThermovisionMeasuresController.class);
	
	@Autowired
	private ThermovisionMeasuresServices thermovisionMeasuresServices;
	
	@RequestMapping(value = "/getThermovisionMeasures/{date}/{facilityId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<ThermovisionMeasureResponse>> findThermovisionMeasure(
				@PathVariable("date") String fromDate,
				@PathVariable("facilityId") String division) throws JSONException {
		logger.info("Enter into Thermovision Measure function");
		logger.info("from date = "+fromDate +" Division = "+division);
		List<ThermovisionMeasureResponse> thermovisionMeasureList = null;
		try {			
			logger.info("Calling service for Thermovision Measure data");
			thermovisionMeasureList = thermovisionMeasuresServices.findThermovisionMeasure(fromDate, division);	
			logger.info("Fetched Thermovision Measure data = "+thermovisionMeasureList);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the Thermovision Measure data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the Thermovision Measure data = "+e.getMessage());
		}
		logger.info("Exit from Thermovision Measure function");
		return ResponseEntity.ok((thermovisionMeasureList));
	}
	
	
	@PostMapping(value="/saveThermovisionMeasures")
	@ResponseBody
	public ResponseStatus saveThermovisionMeasures(@RequestBody List<ThermovisionMeasureResponse> thermovisionMeasureResponse) {
		logger.info("*** Enter into saveThermovisionMeasures function ***");
		try {			
			thermovisionMeasuresServices.saveThermovisionMeasures(thermovisionMeasureResponse);
			logger.info("Preparing the return response and saveThermovisionMeasures function end ");
			return Helper.findResponseStatus("Thermovision Measure Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Thermovision Measure Data. "+npe.getMessage());
			return Helper.findResponseStatus("Thermovision Measure Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Thermovision Measure Data. "+e.getMessage());
			return Helper.findResponseStatus("Thermovision Measure  Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@PostMapping(value="/saveOheThermovisionMeasure")
	@ResponseBody
	public ResponseStatus saveOheThermovisionMeasure(@RequestBody OheThermovisionMeasureRequest oheThermovisionMeasureRequest) {
		logger.info("*** Enter into saveOheThermovisionMeasure function ***");
		try {			
			thermovisionMeasuresServices.saveOheThermovisionMeasure(oheThermovisionMeasureRequest);
			logger.info("Preparing the return response and saveThermovisionMeasures function end ");
			return Helper.findResponseStatus("Thermovision Measure Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Thermovision Measure Data. "+npe.getMessage());
			return Helper.findResponseStatus("Thermovision Measure Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Thermovision Measure Data. "+e.getMessage());
			return Helper.findResponseStatus("Thermovision Measure  Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/getOherThermovisionMeasures/{facilityId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<ThermovisionMeasures>> findOheThermovisionMeasure(
				@PathVariable("facilityId") Long facilityId) throws JSONException {
		logger.info("Enter into ohe Thermovision Measure function");
		List<ThermovisionMeasures> thermovisionMeasureList = null;
		try {			
			logger.info("Calling service for Thermovision Measure data");
			thermovisionMeasureList = thermovisionMeasuresServices.findOheThermovisionMeasure( facilityId);	
			logger.info("Fetched Thermovision Measure data = "+thermovisionMeasureList);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the Thermovision Measure data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the Thermovision Measure data = "+e.getMessage());
		}
		logger.info("Exit from Thermovision Measure function");
		return ResponseEntity.ok((thermovisionMeasureList));
	}
	
	@PostMapping(value="/saveThermoMeasureRetest")
	@ResponseBody
	public ResponseStatus saveThermoMeasureRetest(@RequestBody ThermovisionMeasureResponse thermovisionMeasureResponse) {
		logger.info("*** Enter into saveThermoMeasureRetest function ***");
		try {			
			thermovisionMeasuresServices.saveThermoMeasureRetest(thermovisionMeasureResponse);
			logger.info("Preparing the return response and saveThermovisionMeasures function end ");
			return Helper.findResponseStatus("Thermovision Measure Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Thermovision Measure Data. "+npe.getMessage());
			return Helper.findResponseStatus("Thermovision Measure Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Thermovision Measure Data. "+e.getMessage());
			return Helper.findResponseStatus("Thermovision Measure  Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/getOheThermoMeasuresData/{facilityId}/{fromDate}/{thruDate}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<List<ThermovisionMeasures>> getOheThermoMeasuresData(
				@PathVariable("facilityId") Long facilityId,@PathVariable("fromDate") Date fromDate , @PathVariable("thruDate") Date thruDate) throws JSONException {
		logger.info("Enter intogetOheThermoMeasuresData function");
		List<ThermovisionMeasures> thermovisionMeasureList = null;
		try {			
			logger.info("Calling service for Thermovision Measure data");
			thermovisionMeasureList = thermovisionMeasuresServices.findOheThermovisionMeasure( facilityId,fromDate,thruDate);	
			logger.info("Fetched Thermovision Measure data = "+thermovisionMeasureList);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching the Thermovision Measure data = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching the Thermovision Measure data = "+e.getMessage());
		}
		logger.info("Exit from Thermovision Measure function");
		return ResponseEntity.ok((thermovisionMeasureList));
	}
	
	@PostMapping("/findOheThermovisionMeasurements")
	@ResponseBody	
	public Boolean findOheThermovisionMeasurements(
			@RequestParam("date") Date date,
			@RequestParam("facilityId") Long facility,
			@RequestParam("connectionPoint1") String connectionPoint1,
			@RequestParam("location") String location
			){
			
		try {
            logger.info("Request for checking exists feeder and start date..."+date+"*** facilityId ***"+facility+"*** connection point ***"+connectionPoint1+"*** location ***"+location);
			return thermovisionMeasuresServices.existsByTcpScheduleIdAndlocationAndConnectionPoint1(date,facility,connectionPoint1,location);
            //return energyMeterService.existsByFeederAndStartDate(feeder,Helper.convertStringToTimestamp(startDate));
		} catch (Exception e) {
			logger.error("Error while checking exists feeder and start date..."+e.getMessage());
			return false;
		}
	}

}
