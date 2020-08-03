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

import com.scr.model.SubSector;
import com.scr.services.SubSectorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SubSectorController {

	static Logger logger = LogManager.getLogger(SubSectorController.class);

	@Autowired
	private SubSectorService subSectorService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllSubSector", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<SubSector> findAllSubSector() throws JSONException {
		logger.info("Enter into findAll Sub Sector function");
		List<SubSector> subSectorList = null;
		try {
			logger.info("Calling service for sub sector data");
			subSectorList = subSectorService.findAll();
			logger.info("Fetched sub sector data = " + subSectorList.size());
			return subSectorList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the sub sector data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the sub sector data = " + e.getMessage());
		}
		logger.info("Exit from findAll Sub Sector function");
		return subSectorList;
	}

}
