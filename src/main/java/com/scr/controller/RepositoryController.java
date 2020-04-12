/**
 * 
 */
package com.scr.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.mapper.RepositoryMapper;
import com.scr.message.request.RepositoryRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Repository;
import com.scr.model.User;
import com.scr.services.RepositoryService;
import com.scr.services.UserServices;
import com.scr.util.Constants;
import com.scr.util.Helper;



/**
 * @author vt1056
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class RepositoryController {
	static Logger logger = LogManager.getLogger(RepositoryController.class);
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RepositoryMapper repositoryMapper;
	
	@Autowired
	private UserServices userService;
	
	@RequestMapping(value = "/findAllRepositories", method = RequestMethod.GET , headers="accept=application/json")	
	public ResponseEntity<List<Repository>> getAllRepositryDetails(){
		List<Repository> repositorys = null;
		try {
			
			/*
			 * ObjectMapper mapper = new ObjectMapper();
			 * mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			 */
		    //convert java object to JSON		   
			logger.info("Getting repositorys Details");			
			repositorys = repositoryService.findAllByIsActiveOrderByModifiedDateDesc(Constants.ACTIVE_STATUS_ID);  
			/*
			 * String json=mapper.writeValueAsString(repositorys); System.out.println(json);
			 */
			return new ResponseEntity<List<Repository>>(repositorys, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while getting Repository Details");
			return new ResponseEntity<List<Repository>>(repositorys, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin("*")
	@RequestMapping(value = "/addRepository", method = RequestMethod.POST, headers="accept=application/json")
	public ResponseStatus addRepositryDetails(@Valid @RequestBody RepositoryRequest repositoryRequest) {
		try {
			logger.info("Adding repositorys Details");
			Optional<User> user = userService.findById(new Long(repositoryRequest.getCreatedBy()));
			if(user.isPresent()) {		
				Repository savedRepository = repositoryService.saveRepository(repositoryMapper.prepareAddRepositoryMapper(repositoryRequest, user.get()));
			if (savedRepository != null)
				return Helper.findResponseStatus("Repository Added Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("Repository Addition is Fail", Constants.FAILURE_CODE);
			}else {
				return Helper.findResponseStatus("Anauthenticated User", Constants.FAILURE_CODE);
			}
		} catch (Exception e) {			
			logger.error("Error while Adding repositorys Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/findRepositoryById/{repositoryId}", method = RequestMethod.GET , headers="accept=application/json")	
	public ResponseEntity<Repository> findRepositryById(@PathVariable("repositoryId") Integer repositoryId){
		Repository repository = null;
		try {
			repository = repositoryService.findByRepositoryId(repositoryId);
			if(repository != null)
				return new ResponseEntity<Repository>(repository, HttpStatus.OK);
			else
				return new ResponseEntity<Repository>(repository, HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while deleteing repositorys Details");
			return new ResponseEntity<Repository>(repository, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/existsRepositoryIp/{repositoryIp}", method = RequestMethod.GET , headers="accept=application/json")	
	public Boolean existsByRepositoryIp(@PathVariable("repositoryIp") String repositoryIp){		
		try {			
			if(!repositoryIp.isEmpty())
				return repositoryService.existsByRepositoryIpByIsActive(repositoryIp.replace("*", "."), Constants.ACTIVE_STATUS_ID);
			else
				return false;
		} catch (Exception e) {			
			logger.error("Error while checking exists repository id.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsRepositoryCode/{repositoryCode}", method = RequestMethod.GET , headers="accept=application/json")	
	public Boolean existsByRepositoryCode(@PathVariable("repositoryCode") String repositoryCode){		
		try {
			return repositoryService.existsByRepositoryCodeByIsActive(repositoryCode, Constants.ACTIVE_STATUS_ID);
		} catch (Exception e) {			
			logger.error("Error while checking exists repository code.");
			return false;
		}
	}
	
	@RequestMapping(value = "/existsRepositoryName/{repositoryName}", method = RequestMethod.GET , headers="accept=application/json")	
	public Boolean existsByRepositoryName(@PathVariable("repositoryName") String repositoryName){		
		try {
			return repositoryService.existsByRepositoryNameByIsActive(repositoryName, Constants.ACTIVE_STATUS_ID);
		} catch (Exception e) {
			logger.error("Error while checking exists repository name.");
			return false;
		}
	}
	
	
	
	
	
	@RequestMapping(value = "/deleteRepository/{repositoryId}", method = RequestMethod.DELETE ,  headers="accept=application/json")	
	public ResponseStatus deleteRepositryDetails(@PathVariable("repositoryId") Integer repositoryId){
		try {
			Repository repository = repositoryService.findByRepositoryId(repositoryId);
			repository.setIsActive(Constants.UNACTIVE_STATUS_ID);
			repository.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			logger.info("deleteing repositorys Details");			
			Repository flag = repositoryService.saveRepository(repository);    
			if (flag != null)
				return Helper.findResponseStatus("Repository Deleted Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("Repository Deletion is Fail", Constants.FAILURE_CODE);	
		} catch (Exception e) {
			logger.error("Error while deleteing repositorys Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateRepository", method = RequestMethod.PUT , headers="accept=application/json")	
	public ResponseStatus updateRepositoryDetails(@Valid @RequestBody RepositoryRequest repositoryRequest){
		try {
			logger.info("Upadating Repository  Details");
			Optional<User> user = userService.findById(new Long(repositoryRequest.getModifiedBy()));
			if(user.isPresent()) {
			Repository repositoryToUpdate = repositoryService.findByRepositoryId(repositoryRequest.getRepositoryId());			
			Repository repository = repositoryService.saveRepository(repositoryMapper.prepareUpdateRepository(repositoryToUpdate, repositoryRequest, user.get()));    
			if (repository != null)
				return Helper.findResponseStatus("Repository Updated Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("Repository Updation is Fail", Constants.FAILURE_CODE);	
			}
			else
				return Helper.findResponseStatus("Anauthenticated User", Constants.FAILURE_CODE);	
		} catch (Exception e) {
			logger.error("Error while Upadating Repository  Details");
			e.printStackTrace();
			return Helper.findResponseStatus(e.getLocalizedMessage(), Constants.FAILURE_CODE);
		}
	}
}
