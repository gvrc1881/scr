package com.scr.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.model.ElementarySection;
import com.scr.services.ElementarySectionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ElementarySectionController {

	static Logger logger = LogManager.getLogger(ElementarySectionController.class);

	@Autowired
	private ElementarySectionService elementarySectionService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllEleSections", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<ElementarySection> findAllEleSections() throws JSONException {
		logger.info("Enter into findAll Elementary Section function");
		List<ElementarySection> eleSectionsList = null;
		try {
			logger.info("Calling service for elementary section data");
			eleSectionsList = elementarySectionService.findAll();
			logger.info("Fetched elementary section data = " + eleSectionsList.size());
			return eleSectionsList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the elementary section data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the elementary section data = " + e.getMessage());
		}
		logger.info("Exit from findAll Elementary Section function");
		return eleSectionsList;
	}

}
