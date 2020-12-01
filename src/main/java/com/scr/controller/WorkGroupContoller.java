package com.scr.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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


import com.scr.message.request.WorkGroupRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Make;
import com.scr.model.WorkGroup;
import com.scr.services.WorkGroupService;
import com.scr.services.WorksServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class WorkGroupContoller {
	
	private static Logger logger = Logger.getLogger(WorkGroupContoller.class);
	
	@Autowired 
	WorkGroupService groupsSectionsService;
	
	@Autowired 
	WorksServices worksService;
	
	@RequestMapping(value = "/addGroupsSections" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addGroupsSections(@RequestBody WorkGroup workGroup) {
		
		logger.info("Enter into save function with below request parameters ");
		
		logger.info("Request Parameters = "+workGroup.toString());
		
	
		try {
			logger.info("Calling service with request parameters.");
			groupsSectionsService.save(workGroup);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("GroupsSections added successfully", Constants.SUCCESS_CODE);
		}
		
		catch(NullPointerException npe) {
			logger.error("ERROR >> While adding GroupsSections data. "+npe.getMessage());
			return Helper.findResponseStatus("GroupsSections save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding GroupsSections data. "+e.getMessage());
			return Helper.findResponseStatus("GroupsSections save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	

	
	@RequestMapping(value = "/updateGroupsSections" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateMake(@RequestBody WorkGroup workGroupReq) {
		logger.info("Enter into update function with below request parameters ");
		logger.info("Request Parameters = "+workGroupReq.toString());
		try {
			logger.info("Calling service with request parameters.");
		
			groupsSectionsService.save(workGroupReq);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("GroupsSections updated successfully", Constants.SUCCESS_CODE);
	}catch(NullPointerException npe) {
		logger.error("ERROR >> While updating GroupsSections data. "+npe.getMessage());
		return Helper.findResponseStatus("GroupsSections update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
	}
	catch (Exception e) {
		logger.error("ERROR >> While updating GroupsSections data. "+e.getMessage());
		return Helper.findResponseStatus("GroupsSections update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
	}
		}

	@RequestMapping(value = "/deleteGroupsSections/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteGroupsSectionsById(@PathVariable Long id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected GroupsSections Id = "+id);
		try {
			groupsSectionsService.deleteGroupsSectionsById(id);
		return Helper.findResponseStatus("GroupsSections deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		logger.error("ERROR >> While deleting GroupsSections data"+npe.getMessage());
		return Helper.findResponseStatus("GroupsSections Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error("ERROR >> While deleting GroupsSections data"+e.getMessage());
		return Helper.findResponseStatus("GroupsSections Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	 @RequestMapping(value = "/findAllGroupsSections" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<WorkGroup> findAllGroupsSections() throws JSONException {
		 List<WorkGroup> groupsList = null;
		 try {
			   logger.info("Calling service for groups data");	
		
			   groupsList = groupsSectionsService.findAll();
		 logger.info("Fetched GroupsSections data***"+groupsList.size());
		
		return groupsList;
	}catch (NullPointerException npe) {
		logger.error("ERROR >>> while fetching the GroupsSections data = "+npe.getMessage());
	}
	catch (Exception e) {
		logger.error("ERROR >>> while fetching the GroupsSections data = "+e.getMessage());
	}
		 logger.info("Exit from GroupsSections function");
	return groupsList;	
}
	
	@RequestMapping(value = "/findGroupsSectionsById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<WorkGroup> findGroupsSectionsById(@PathVariable("id") Long id){

		Optional<WorkGroup> groups = null;
		try {
			logger.info("Selected groupsSections Id = "+id);
			
			groups = groupsSectionsService.findGroupsSectionsById(id);
			if(groups.isPresent()) {
				logger.info("groupsSections Data = "+groups.get());
				return new ResponseEntity<WorkGroup>(groups.get(), HttpStatus.OK);
				
			}
			else
				return new ResponseEntity<WorkGroup>(groups.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find groupsSections Details by id, "+e.getMessage());
			return new ResponseEntity<WorkGroup>(groups.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/existsByWorkIdAndGroupAndSection/{work}/{group}/{section}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsByWorkIdAndGroupAndSection(@PathVariable("work") Integer work ,@PathVariable("group") String group,
			@PathVariable("section") String section){
		logger.info("Exist====="+"work=="+work+"group==="+group+"section=="+section);
		try {
			logger.info("Request for checking exists work and group and section ...");
			return groupsSectionsService.existsByworkIdAndWorkGroupAndSection(worksService.findById(work).get(),group,section);
		} catch (Exception e) {
			logger.error("Error while checking exists  work and group and section..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existsByWorkIdAndGroupAndSectionAndId/{id}/{work}/{group}/{section}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsMakeCodeAndId(@PathVariable("id") Long id,@PathVariable("work") Integer work ,@PathVariable("group") String group,
			@PathVariable("section") String section){
		
		logger.info("id=="+id+"work=="+work+"group=="+group+"section=="+section);
		Boolean result;
		try {
			Optional<WorkGroup> workData = groupsSectionsService.findByworkIdAndWorkGroupAndSection(worksService.findById(work).get(),group,section);
			
			if(workData.isPresent()) {
				WorkGroup works = workData.get();
				logger.info("***id ***"+works.getId());
				if (id.equals(works.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and work and group and section..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/getSectionsBasedOnGroups/{groupIdList}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<WorkGroup> getSectionsBasedOnGroups(@PathVariable("groupIdList") Long[] groupIdList) {
		 logger.info("Enter into  getSectionsBasedOnGroups function ");
		// Optional<WorkGroup> groups = null;
		List<WorkGroup> finalWorkGroupList = new ArrayList<WorkGroup>();
		try {
			// logger.info("Selected groupsSections Id = " + groupIdList.size());
			for (Long groupId : groupIdList) {
				Optional<WorkGroup> workGroup = groupsSectionsService.findGroupsSectionsById(groupId);
				if (workGroup.isPresent()) {
					List<WorkGroup> workGroupList = groupsSectionsService.findByWorkGroup(workGroup.get().getWorkGroup());
					finalWorkGroupList.addAll(workGroupList);
				}
			}
			logger.info(" Sections size " + finalWorkGroupList.size());
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the Sections data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the Sections data = " + e.getMessage());
		}
		logger.info("Exit from getSectionsBasedOnGroups function");
		return finalWorkGroupList;
	}
	
}