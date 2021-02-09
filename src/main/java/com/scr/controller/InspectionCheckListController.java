package com.scr.controller;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.model.InspectionCheckList;
import com.scr.model.TestInspection;
import com.scr.model.WPADailyProgress;
import com.scr.services.InspectionCheckListService;
import com.scr.services.TestInspectionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class InspectionCheckListController {
	
	static Logger log = Logger.getLogger(InspectionCheckListController.class);
	
	@Autowired
	private InspectionCheckListService inspectionCheckListService;
	
	@Autowired
	private TestInspectionService testInspectionService;
	
	@RequestMapping(value = "/getInsCheckListBasedOnTestInsId/{testInsId}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<InspectionCheckList> getInsCheckListBasedOnTestInsId(@PathVariable("testInsId") Long testInsId){
		log.info("Enter into getWPADailyProgressBasedOnId function Id***"+testInsId);
		List<InspectionCheckList> insCheckList = null;
		
		try {
			Optional<TestInspection> testIns = testInspectionService.findTestInspectionById(testInsId);
			if (testIns.isPresent()) {
				insCheckList = inspectionCheckListService.findByTestInspectionId(testIns.get());
			}
			return insCheckList;
		} catch (Exception e) {
			log.error("Error >>  while find WPA daily progess data by id, "+e.getMessage());
		}
		return insCheckList;
	}

}
