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

import com.scr.model.Sector;
import com.scr.services.SectorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SectorController {

	static Logger logger = LogManager.getLogger(SectorController.class);

	@Autowired
	private SectorService sectorService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllSector", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Sector> findAllSector() throws JSONException {
		logger.info("Enter into findAll Sector function");
		List<Sector> sectorList = null;
		try {
			logger.info("Calling service for sector data");
			sectorList = sectorService.findAll();
			logger.info("Fetched sector data = " + sectorList.size());
			return sectorList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the sector data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the sector data = " + e.getMessage());
		}
		logger.info("Exit from findAll Sector function");
		return sectorList;
	}

}
