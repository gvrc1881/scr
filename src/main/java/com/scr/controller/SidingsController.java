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
import com.scr.model.SidingDetails;
import com.scr.model.StationsSection;
import com.scr.services.SidingsService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SidingsController {
	
	@Autowired
	private SidingsService sidingsService;
	
	
	@RequestMapping(value = "/stationCode", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<StationsSection>> findStationCode(){
		List<StationsSection> stationCode= sidingsService.findStationCode();
		return new ResponseEntity<List<StationsSection>>(stationCode,HttpStatus.OK);	
		
	}
	
	@RequestMapping(value = "/findAllSidingsItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<SidingDetails> findAllSidingsItems(){
		List<SidingDetails> sidingsItem = sidingsService.findAll();
		return sidingsItem;
	}
	@RequestMapping(value = "/addSlidingsItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addSlidingsItem(@RequestBody SidingDetails sidingDetails) {
		sidingsService.save(sidingDetails);
		return Helper.findResponseStatus("Sidings Item added successfully", Constants.SUCCESS_CODE);
	}
	@RequestMapping(value = "/findSidingsItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<SidingDetails> findSidingsItemById(@PathVariable Long id){
		Optional<SidingDetails> sidingsItem = sidingsService.findSidingsItemById(id);
		return new ResponseEntity<SidingDetails>(sidingsItem.get(), HttpStatus.OK);
	}
	@RequestMapping(value = "/updateSlidingsItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateSlidingsItem(@RequestBody SidingDetails sidingDetails) {
		sidingsService.save(sidingDetails);
		return Helper.findResponseStatus("Sidings Item updated successfully", Constants.SUCCESS_CODE);
	}
	@RequestMapping(value = "/deleteSidingsItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteSidingsItemById(@PathVariable Long id) {
		sidingsService.deleteSidingsItemById(new Long(id));
		return Helper.findResponseStatus("Sidings Item deleted successfully", Constants.SUCCESS_CODE);
	}
	

}
