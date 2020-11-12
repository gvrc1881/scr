package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.SubSector;
import com.scr.services.SubSectorService;
import com.scr.util.Constants;
import com.scr.util.Helper;

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
	@RequestMapping(value="/addSubSector",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addSubSector(@RequestBody SubSector subSector) {
		logger.info("Enter into addSubSector function with below request parameters ");
		logger.info("Request Parameters = "+subSector.toString());
		try {
			logger.info("Calling service with request parameters.");
			subSectorService.save(subSector);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("subSector Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding subSector data. "+npe.getMessage());
			return Helper.findResponseStatus("subSector save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding subSector data. "+e.getMessage());
			return Helper.findResponseStatus("subSector save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findSubSectorById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<SubSector> findSubSectorById(@PathVariable Long id){
		Optional<SubSector> subSector = null;
		try {
			logger.info("Selected subSector Id = "+id);
			subSector = subSectorService.findSubSectorById(id);
			if(subSector.isPresent()) {
				logger.info("subSector Data = "+subSector.get());
				return new ResponseEntity<SubSector>(subSector.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<SubSector>(subSector.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find subSector Details by id, "+e.getMessage());
			return new ResponseEntity<SubSector>(subSector.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateSubSector" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateSubSector(@RequestBody SubSector subSector) {
		logger.info("Enter into updateSubSector function with below request parameters ");
		logger.info("Request Parameters = "+subSector.toString());
		try {
			logger.info("Calling service with request parameters.");
			subSectorService.save(subSector);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("subSector Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating subSector data. "+npe.getMessage());
			return Helper.findResponseStatus("subSector update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating subSector data. "+e.getMessage());
			return Helper.findResponseStatus("subSector update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteSubSector/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteSubSectorById(@PathVariable Long id) {
		logger.info("Enter into deleteSubSectorById function");
		logger.info("Selected SubSector Id = "+id);
		try {
			subSectorService.deleteSubSectorById(id);
			return Helper.findResponseStatus("SubSector deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting SubSector data"+npe.getMessage());
			return Helper.findResponseStatus("SubSector Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting SubSector data"+e.getMessage());
			return Helper.findResponseStatus("SubSector Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
}
