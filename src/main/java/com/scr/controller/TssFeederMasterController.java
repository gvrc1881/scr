package com.scr.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.model.TssFeederMaster;
import com.scr.services.TssFeederMasterService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TssFeederMasterController {
	
	@Autowired
	private TssFeederMasterService tssFeederMasterService;
	
	static Logger log = Logger.getLogger(TssFeederMasterController.class);
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllTssFeederMaster", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TssFeederMaster> tssFeederMasterList() throws JSONException {
		List<TssFeederMaster> tssFeederMasterList = null;
		try {
			tssFeederMasterList = tssFeederMasterService.findAllOrderByFeederNameAsc();
		return tssFeederMasterList;
		} catch (NullPointerException e) {
			log.error(e);
		}
		catch (Exception e) {
			log.error(e);
		}
		return tssFeederMasterList;	
	}

}
