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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.Drives;
import com.scr.model.EnergyMeter;
import com.scr.model.User;
import com.scr.model.UserDefualtFacConsIndEtc;
import com.scr.services.EnergyMeterService;
import com.scr.services.UserDefualtFacConsIndEtcService;
import com.scr.services.UserServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class EnergyMeterController {
	
	static Logger log = Logger.getLogger(EnergyMeterController.class);
	
	@Autowired
	private EnergyMeterService energyMeterService;
	
	@Autowired
	private UserDefualtFacConsIndEtcService userDefualtFacConsIndEtcService;
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllEnergyMeter/{userName}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<EnergyMeter> findAllEnergyMeter(@PathVariable("userName") String userName) throws JSONException {
		log.info("Enter into findAllEnergyMeter function");
		List<EnergyMeter> energyMeterList = null;
		try {
			log.info("Calling service for energy meters data");
			energyMeterList = energyMeterService.findAll(userName);
			log.info("Fetched energy meters data = "+energyMeterList.size());
			return energyMeterList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the energy meters data = "+npe.getMessage());
		}catch (Exception e) {
			log.error("ERROR >>> while fetching the energy meters data = "+e.getMessage());
		}
		log.info("Exit from findAllEnergyMeter function");
		return energyMeterList;	
	}
	
	@RequestMapping(value = "/addEnergyMeter", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveEnergyMeter(@RequestBody EnergyMeter energyMeter){
		log.info("Enter into saveEnergyMeter function with below request parameters ");
		/*Optional<UserDefualtFacConsIndEtc> userDefault = userDefualtFacConsIndEtcService.findByUserLoginId(energyMeter.getDataDiv());
		if (userDefault.isPresent()) {
			energyMeter.setDataDiv(userDefault.get().getDivision().toUpperCase());
		}*/
		energyMeter.setDataDiv(energyMeter.getDataDiv().toUpperCase());
		log.info("Request Parameters = "+energyMeter.toString());
		try {
			log.info("Calling service with request parameters.");
			EnergyMeter eneMeter = energyMeterService.save(energyMeter);
			energyMeter.setSeqId(eneMeter.getId().toString());
			energyMeterService.save(energyMeter);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Energy meter added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding energy meter data. "+npe.getMessage());
			return Helper.findResponseStatus("Energy meter save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding energy meter data. "+e.getMessage());
			return Helper.findResponseStatus("Energy meter save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findEnergyMeter/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<EnergyMeter> findById(@PathVariable("id") Long id){
		Optional<EnergyMeter> eleMeter = null;
		try {
			log.info("Selected Energy Meter Id = "+id);
			eleMeter = energyMeterService.findById(id);
			if(eleMeter.isPresent()) {
				log.info("Energy Meter Data = "+eleMeter.get());
				return new ResponseEntity<EnergyMeter>(eleMeter.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<EnergyMeter>(eleMeter.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			log.error("Error >>  while find Energy Meter Details by id, "+e.getMessage());
			return new ResponseEntity<EnergyMeter>(eleMeter.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updateEnergyMeter" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateEnergyMeter(@RequestBody EnergyMeter energyMeter) {
		log.info("Enter into updateEnergyMeter function with below request parameters ");
		log.info("Request Parameters = "+energyMeter.toString());
		try {
			log.info("Calling service with request parameters.");
			energyMeterService.save(energyMeter);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Energy meter updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating energy meter data. "+npe.getMessage());
			return Helper.findResponseStatus("Energy meter update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating energy meter data. "+e.getMessage());
			return Helper.findResponseStatus("Energy meter update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteEnergyMeter/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteEnergyMeter(@PathVariable Long id) {
		log.info("Enter into deleteEnergyMeter function");
		log.info("Selected Energy Meter Id = "+id);
		try {
			energyMeterService.deleteById(id);
			return Helper.findResponseStatus("Energy Meter Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting energy meter data"+npe.getMessage());
			return Helper.findResponseStatus("Energy Meter Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting energy meter data"+e.getMessage());
			return Helper.findResponseStatus("Energy Meter Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existsFeederAndStartDate/{feederId}/{startDate}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFeederAndStartDate(@PathVariable("feederId") String feeder ,@PathVariable("startDate") String startDate){
			
		try {
            log.info("Request for checking exists feeder and start date...");
			return energyMeterService.existsByFeederAndStartDate(feeder,Helper.convertStringToTimestamp(startDate));
		} catch (Exception e) {
			log.error("Error while checking exists feeder and start date..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existsFeederHavingEndDate/{feederId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFeeder(@PathVariable("feederId") String feeder ){
			
		try {
            log.info("Request for checking exists feeder and end date...");
			return energyMeterService.existsByFeederAndEndDateIsNull(feeder);
		} catch (Exception e) {
			log.error("Error while checking exists feeder and end date..."+e.getMessage());
			return false;
		}
	}

}
