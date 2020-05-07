package com.scr.controller;

import java.sql.Timestamp;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.TractionEnergyTariff;
import com.scr.model.Works;
import com.scr.services.WorksServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@RestController
@RequestMapping("/scr/api")
public class WorksController {
	
	private Logger logger = Logger.getLogger(WorksController.class);
	
	@Autowired
	private WorksServices worksServices;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllWorks", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Works> workList() throws JSONException {
		List<Works> workList = null;
		try {
			logger.info("Fetch Work List Started");	
			workList = worksServices.findAll();
			logger.info("Fetch Work List Ended"+workList);
		return workList;
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return workList;	
	}
	
	@RequestMapping(value = "/addWork", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus save(@RequestBody Works work){
		work.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		work.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		Works saveWork = worksServices.save(work);
		if (saveWork != null) {
			return Helper.findResponseStatus("Work added successfully", Constants.SUCCESS_CODE);
		}
		return null;
	}
	
	
	@RequestMapping(value = "/findWork/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Works> findById(@PathVariable("id") Integer id){
		Optional<Works> ele = worksServices.findById(id);
		return new ResponseEntity<>(ele.get(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateWork" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus update (@RequestBody Works work) {
		work.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		worksServices.save(work);
		return Helper.findResponseStatus("Work updated successfully", Constants.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/deleteWork/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteById(@PathVariable Integer id) {
		logger.info("*** id****"+id);
		worksServices.deleteById(id);
		return Helper.findResponseStatus("Work Deleted successfully", Constants.SUCCESS_CODE);
	}
	

}
