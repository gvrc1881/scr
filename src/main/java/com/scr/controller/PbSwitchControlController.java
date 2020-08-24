package com.scr.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.model.PbSwitchControl;
import com.scr.services.PbSwitchControlService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class PbSwitchControlController {
	
	static Logger logger = LogManager.getLogger(PowerBlockController.class);
	
	@Autowired
	private PbSwitchControlService PbSwitchControlService;
	
	@RequestMapping(value = "/findByExtentTypeAndExtentCode/{extentType}/{extentCode}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<PbSwitchControl> findByExtentTypeAndExtentCode(@PathVariable("extentType") String extentType, @PathVariable("extentCode") List<String> extentCode) throws JSONException {
		logger.info("Enter into findByExtendTypeAndExtendCode function");
		List<PbSwitchControl> PBSwitchControlList = null;
		try {
			logger.info("Calling service for pb switch control data");
			PBSwitchControlList = PbSwitchControlService.findByPbExtentTypeAndPbExtentCodeIn(extentType,extentCode);
			logger.info("Fetched pb switch control data = "+PBSwitchControlList.size());
			return PBSwitchControlList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the pb switch control data = "+npe.getMessage());
		}catch (Exception e) {
			logger.error("ERROR >>> while fetching the pb switch control data = "+e.getMessage());
		}
		logger.info("Exit from findByExtentTypeAndExtentCode function");
		return PBSwitchControlList;	
	}

}
