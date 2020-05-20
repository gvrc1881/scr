package com.scr.controller;

import java.io.FileNotFoundException;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
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
import org.springframework.web.server.ResponseStatusException;
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
	public ResponseEntity<List<StationsSection>> findAllStationSections(){
		logger.info("Enter into findAllStationSections");
		List<StationsSection> List = null;
		try {			
			logger.info("Calling service for Stations Sections");
			List = stationsSectionsService.findAll();	
			logger.info("Fetched Stations Sections = "+List);
		} catch (NullPointerException e) {			
			logger.error("ERROR >>> while fetching  Stations Sections = "+e.getMessage());
		} catch (Exception e) {			
			logger.error("ERROR >>> while fetching  Stations Sections = "+e.getMessage());
		}
		logger.info("Exit from findAllStationSections");
		return ResponseEntity.ok((List));
	}
	
	//2.save Data
	@RequestMapping(value = "/addStationSections" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addStationSections(@Valid @RequestBody StationsSection stationsSection)throws JSONException {
		Log.info("addStationSections"+stationsSection);
		try {
			logger.info("Enter into addStationSections");
			logger.info("Request parameters = "+stationsSection.toString());
			
			stationsSectionsService.save(stationsSection);
			return Helper.findResponseStatus("sucessfully createsd" ,Constants.SUCCESS_CODE);
		} catch (Exception ex) {
			logger.error("ERROR >>> while saving station sections data, "+ex.getMessage());
			throw new ResponseStatusException(HttpStatus.OK, "Server Error", ex);
		}
	}
	
	@RequestMapping(value = "/findStationSectionsById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<StationsSection> findFPSectionsItemById(@PathVariable Long id) throws FileNotFoundException{
		return new ResponseEntity<StationsSection>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/updateStationSections" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateStationSections(@RequestBody StationsSection stationsSection) {
		try {
			stationsSectionsService.save(stationsSection);
			return Helper.findResponseStatus("Stations Sections updated successfully", Constants.SUCCESS_CODE);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.FOUND,"Server Error");
		}
	}
	@RequestMapping(value = "/deleteStationSections/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteStationSectionsById(@PathVariable Long id) {
		stationsSectionsService.deleteStationSectionsById(id);
		return Helper.findResponseStatus("Stations Sections Deleted successfully", Constants.SUCCESS_CODE);
	}

}
