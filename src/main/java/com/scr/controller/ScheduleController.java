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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.GantryMasterData;
import com.scr.model.Schedule;
import com.scr.model.TssFeederMaster;
import com.scr.services.ScheduleService;

import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ScheduleController {
	
	static Logger log = Logger.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduleService scheduleService;
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllSchedule", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Schedule> findAllSchedule() throws JSONException {
		log.info("Enter into findAllSchedule function");
		List<Schedule> scheduleList = null;
		try {
			log.info("Calling service for Schedules data");
			scheduleList = scheduleService.findAllOrderByscheduleCodeAsc();
			log.info("Fetched Schedules data = "+scheduleList.size());
			return scheduleList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the schedules data = "+npe.getMessage());
		}catch (Exception e) {
			log.error("ERROR >>> while fetching the schedules data = "+e.getMessage());
		}
		log.info("Exit from findAllschedule function");
		return scheduleList;	
	}
	@RequestMapping(value = "/addAssetSchedule" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addAssetSchedule(@RequestBody Schedule scheduleData) {
		log.info("Enter into addAssetSchedule function with below request parameters ");
		log.info("Request Parameters = "+scheduleData.toString());
		try {
			log.info("Calling service with request parameters.");
			Schedule sche = scheduleService.save(scheduleData);
			scheduleData.setSeqId(sche.getId().toString());
			scheduleService.save(scheduleData);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Assets Schedule added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Assets Schedule data. "+npe.getMessage());
			return Helper.findResponseStatus("Gantry MasterData save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Assets Schedule data. "+e.getMessage());
			return Helper.findResponseStatus("Assets Schedule save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findAssetScheduleById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Schedule> findAssetScheduleById(@PathVariable Long id){
		Optional<Schedule> scheduleData = null;
		try {
			log.info("Selected Asset Schedule Id = "+id);
			scheduleData = scheduleService.findAssetScheduleById(id);
			if(scheduleData.isPresent()) {
				log.info("Asset Schedule  Data = "+scheduleData.get());
				return new ResponseEntity<Schedule>(scheduleData.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<Schedule>(scheduleData.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Asset Schedule  Details by id, "+e.getMessage());
			return new ResponseEntity<Schedule>(scheduleData.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateAssetSchedule" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateAssetSchedule(@RequestBody Schedule scheduleData) {
		log.info("Enter into updateAssetSchedule function with below request parameters ");
		log.info("Request Parameters = "+scheduleData.toString());
		try {
			log.info("Calling service with request parameters.");
			scheduleService.save(scheduleData);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Assets Schedule updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating Assets Schedule   data. "+npe.getMessage());
			return Helper.findResponseStatus("Assets Schedule   update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating Assets Schedule   data. "+e.getMessage());
			return Helper.findResponseStatus("Assets Schedule  update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteAssetSchedule/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteAssetSchedule(@PathVariable Long id) {
		log.info("Enter into delete AssetsSchedule function");
		log.info("Selected AssetsSchedule Id = "+id);
		try {
			scheduleService.deleteAssetSchedule(id);
			return Helper.findResponseStatus("AssetsSchedule Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting AssetsSchedule data"+npe.getMessage());
			return Helper.findResponseStatus("AssetsSchedule Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting AssetsSchedule data"+e.getMessage());
			return Helper.findResponseStatus("AssetsSchedule Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	

	@RequestMapping(value = "/existScheduleCode/{scheduleCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existScheduleCode(@PathVariable("scheduleCode") String scheduleCode ){
			
		try {
            log.info("Request for checking exists Schedule code...");
			return scheduleService.existsByScheduleCode(scheduleCode);
		} catch (Exception e) {
			log.error("Error while checking exists schedule code..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existScheduleCodeById/{id}/{scheduleCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existScheduleCodeById(@PathVariable("id") Long id,@PathVariable("scheduleCode") String scheduleCode){
		
		log.info("id=="+id+"scheduleCode=="+scheduleCode);
		Boolean result;
		try {
			Optional<Schedule> schData = scheduleService.findByScheduleCode(scheduleCode);	
			if(schData.isPresent()) {
				Schedule schedule = schData.get();
				log.info("***id ***"+schedule.getId());
				if (id.equals(schedule.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and schedulecode..."+e.getMessage());
			return false;
		}
	}


}
