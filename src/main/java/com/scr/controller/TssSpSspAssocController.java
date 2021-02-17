package com.scr.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.model.Facility;
import com.scr.model.TssSpSspAssoc;
import com.scr.services.FacilityService;
import com.scr.services.TssSpSspAssocService;
import java.util.Optional;


@RestController
@RequestMapping("/scr/api")
public class TssSpSspAssocController {
	
	static Logger logger = LogManager.getLogger(TssSpSspAssocController.class);
	
	@Autowired
	private TssSpSspAssocService tssSpSspService;
	
	@Autowired
	private FacilityService facilityService;
	
	@RequestMapping(value = "/getSpSSpBasedOnTss/{subStation}", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<TssSpSspAssoc>> getSpSSpBasedOnTss(@PathVariable("subStation") String subStation){
		List<TssSpSspAssoc> sspList = null;
		Optional<Facility> fac = facilityService.findByFacilityId(subStation);
		logger.info("substation ===="+fac);
		if(fac.isPresent()) {
			logger.info("in if condition ===="+fac);
			sspList= tssSpSspService.findByTssFacilityId(fac.get());			
		}
		logger.info("Fetched tss data = "+sspList.size());
		return new ResponseEntity<List<TssSpSspAssoc>>(sspList,HttpStatus.OK);	
		
	}

}
