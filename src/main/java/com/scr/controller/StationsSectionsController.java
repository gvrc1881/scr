package com.scr.controller;

import java.util.List;
import java.util.Optional;
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
import com.scr.model.StationsSection;
import com.scr.services.StationsSectionsService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class StationsSectionsController {
	
	@Autowired
	private StationsSectionsService stationsSectionsService;
	
	
	@RequestMapping(value = "/findAllStationSections" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<StationsSection> findAllFPSectionsItems(){
		List<StationsSection> stationSectionsItem = stationsSectionsService.findAll();
		return stationSectionsItem;
	}
	
	@RequestMapping(value = "/addStationSections" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addStationSections(@RequestBody StationsSection stationsSection) {
		stationsSectionsService.save(stationsSection);
		return Helper.findResponseStatus("StationSections added successfully", Constants.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/findStationSectionsById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<StationsSection> findFPSectionsItemById(@PathVariable Long id){
		Optional<StationsSection> stationsSection = stationsSectionsService.findStationSectionsById(id);
		return new ResponseEntity<StationsSection>(stationsSection.get(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateStationSections" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateStationSections(@RequestBody StationsSection stationsSection) {
		stationsSectionsService.save(stationsSection);
		return Helper.findResponseStatus("Stations Sections updated successfully", Constants.SUCCESS_CODE);

	}
	
	@RequestMapping(value = "/deleteStationSections/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteStationSectionsById(@PathVariable Long id) {
		stationsSectionsService.deleteStationSectionsById(id);
		return Helper.findResponseStatus("Stations Sections Deleted successfully", Constants.SUCCESS_CODE);
	}

}
