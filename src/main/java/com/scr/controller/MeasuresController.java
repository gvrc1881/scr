package com.scr.controller;


import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;

import com.scr.model.Measures;
import com.scr.services.MeasuresService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class MeasuresController {
	
	private static Logger logger = Logger.getLogger(MeasuresController.class);
	
	@Autowired 
	MeasuresService measuresService;
	
	 @RequestMapping(value = "/findAllMesures" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<Measures> findAllMesures() throws JSONException {
		 List<Measures> measures = null;
		 try {
			   logger.info("Calling service for make data");	
		
			   measures = measuresService.findAll();
		 logger.info("Fetched make data***"+measures.size());
		return measures;
	}catch (NullPointerException npe) {
		logger.error("ERROR >>> while fetching the measures data = "+npe.getMessage());
	}
	catch (Exception e) {
		logger.error("ERROR >>> while fetching the measures data = "+e.getMessage());
	}
		 logger.info("Exit from measures function");
	return measures;	
}
	
	}

	

	

