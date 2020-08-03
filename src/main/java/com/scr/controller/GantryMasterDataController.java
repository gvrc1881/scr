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

import com.scr.model.GantryMasterData;
import com.scr.services.GantryMasterDataService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class GantryMasterDataController {

	static Logger logger = LogManager.getLogger(GantryMasterDataController.class);

	@Autowired
	private GantryMasterDataService gantryMasterDataService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllGantrys", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<GantryMasterData> findAllGantrys() throws JSONException {
		logger.info("Enter into findAll Gantrys function");
		List<GantryMasterData> gantryList = null;
		try {
			logger.info("Calling service for gantrys data");
			gantryList = gantryMasterDataService.findAll();
			logger.info("Fetched sector data = " + gantryList.size());
			return gantryList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the gantrys data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the gantrys data = " + e.getMessage());
		}
		logger.info("Exit from findAll Gantrys function");
		return gantryList;
	}

}
