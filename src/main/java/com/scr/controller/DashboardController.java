/**
 * 
 */
package com.scr.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.request.DashboardParametersRequest;
import com.scr.message.response.DashboardGraphsResponse;
import com.scr.services.DashboardService;

/**
 * @author 
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class DashboardController {
	
	static Logger logger = LogManager.getLogger(DashboardController.class);
	
	@Autowired
	private DashboardService service;
	
	@RequestMapping(value = "/findGraphsData", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<DashboardGraphsResponse> findDashboardGraphsData(@Valid @RequestBody DashboardParametersRequest request) throws JSONException {	
		logger.info("Enter into findDashboardGraphs function with below request parameters ");
		logger.info("Request Parameters = "+request.toString());
		List<DashboardGraphsResponse> response = null;
		try {			
			logger.info("Calling service with request parameters.");
			response = service.findDashboardGraphsData(request);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While fetching data. "+npe.getMessage());
		}
		catch (Exception e) {
			logger.error("ERROR >> While fetching data. "+e.getMessage());
		}
		return response;
	}
	
}
