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
import com.scr.model.StationsSection;
import com.scr.services.StationsSectionsService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class StationsSectionsController {
	static Logger logger = LogManager.getLogger(StationsSectionsController.class);

	
	@Autowired
	private StationsSectionsService stationsSectionsService;
	
	
	@RequestMapping(value = "/findAllStationSections" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<StationsSection> findAllStationSections(){
		List<StationsSection> ssList = stationsSectionsService.findAll();
		return ssList;
	}
	
	@RequestMapping(value = "/addStationSections" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addStationSections(@RequestBody StationsSection stationsSection) {
		stationsSectionsService.save(stationsSection);
		return Helper.findResponseStatus("stations Section added successfully", Constants.SUCCESS_CODE);
	}
	@RequestMapping(value = "/findStationSectionsById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<StationsSection> findStationSectionsById(@PathVariable Long id){
		Optional<StationsSection> ssList = stationsSectionsService.findStationSectionsById(id);
		return new ResponseEntity<StationsSection>(ssList.get(), HttpStatus.OK);
	}
	@RequestMapping(value = "/updateStationSections" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateStationSections(@RequestBody StationsSection stationsSection) {
		stationsSectionsService.save(stationsSection);
		return Helper.findResponseStatus("stations Section updated successfully", Constants.SUCCESS_CODE);
	}
	@RequestMapping(value = "/deleteStationSections/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteStationSectionsById(@PathVariable Long id) {
		stationsSectionsService.deleteStationSectionsById(new Long(id));
		return Helper.findResponseStatus("Stations Section deleted successfully", Constants.SUCCESS_CODE);
	}
	@RequestMapping(value = "/existsStationCode/{stationCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsStationCode(@PathVariable("stationCode") String stationCode){
		logger.info("Enter station Code"+stationCode);
		try {
			return stationsSectionsService.existsByStationCode(stationCode);
		} catch (Exception e) {
			logger.error("Error while checking exists Station Code.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsStationName/{stationName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDriveDescription(@PathVariable("stationName") String stationName){	
		logger.info("Enter station Name"+stationName);
		try {
			return stationsSectionsService.existsByStationName(stationName);
		} catch (Exception e) {
			logger.error("Error while checking exists stationName.");
			return false;
		}
	}
}
