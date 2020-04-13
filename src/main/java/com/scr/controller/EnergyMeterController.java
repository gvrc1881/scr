package com.scr.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.EnergyMeter;
import com.scr.services.EnergyMeterService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class EnergyMeterController {
	
	static Logger log = Logger.getLogger(EnergyMeterController.class);
	
	@Autowired
	private EnergyMeterService energyMeterService;
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllEnergyMeter", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<EnergyMeter> energyMeterList() throws JSONException {
		List<EnergyMeter> energyMeterList = null;
		try {
			energyMeterList = energyMeterService.findAll();
		return energyMeterList;
		} catch (NullPointerException e) {
			log.error(e);
		}
		catch (Exception e) {
			log.error(e);
		}
		return energyMeterList;	
	}
	
	@RequestMapping(value = "/addEnergyMeter", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveEnergyMeter(@RequestBody EnergyMeter energyMeter){
		EnergyMeter saveEnergyMeter = energyMeterService.save(energyMeter);
		if (saveEnergyMeter != null) {
			return Helper.findResponseStatus("Energy meter added successfully", Constants.SUCCESS_CODE);
		}
		return null;
	}
	
	
	@RequestMapping(value = "/findEnergyMeter/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<EnergyMeter> findById(@PathVariable("id") Long id){
		Optional<EnergyMeter> ele = energyMeterService.findById(id);
		return new ResponseEntity<>(ele.get(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateEnergyMeter" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateEnergyMeter(@RequestBody EnergyMeter energyMeter) {
		energyMeterService.save(energyMeter);
		return Helper.findResponseStatus("Energy Meter updated successfully", Constants.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/deleteEnergyMeter/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteEnergyMeter(@PathVariable Long id) {
		energyMeterService.deleteById(id);
		return Helper.findResponseStatus("Energy Meter Deleted successfully", Constants.SUCCESS_CODE);
	}

}
