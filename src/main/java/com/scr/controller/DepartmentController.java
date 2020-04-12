/**
 * 
 */
package com.scr.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
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
import com.scr.model.Department;
import com.scr.services.DepartmentServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class DepartmentController {
	
	static Logger logger = LogManager.getLogger(DepartmentController.class);
	
	@Autowired
	private DepartmentServices departmentServices;
		
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllDepartments", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Department> departmentList() throws JSONException {
		List<Department> departmentUserList = null;
		try {
		logger.info("Fetch DepartmentUserList Started");	
		 departmentUserList = departmentServices.findByStatusId(Constants.ACTIVE_STATUS_ID);
		logger.info("Fetch DepartmentUserList Ended");
		return departmentUserList;
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return departmentUserList;	
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus addDepartment(@Valid @RequestBody Department department) throws JSONException {
		try {
			department.setStatusId(Constants.ACTIVE_STATUS_ID);
			department.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
			department.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));			
			departmentServices.saveDepartment(department);
			department = departmentServices.findByDepartmentIdAndStatusId(department.getDepartmentId(), Constants.ACTIVE_STATUS_ID);
			if (department != null)
				return Helper.findResponseStatus("Department Added Successfully", Constants.SUCCESS_CODE);
			else
				return Helper.findResponseStatus("Department Addition is Fail", Constants.FAILURE_CODE);
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Department Addition is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Department Addition is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existsDepartmentName/{departmentName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsDepartmentName(@PathVariable("departmentName") String departmentName){		
		try {
			return departmentServices.existsByDepartmentNameAndStatusId(departmentName, Constants.ACTIVE_STATUS_ID);
		} catch (Exception e) {
			logger.error("Error while checking exists department name.");
			return false;
		}
	}

	@RequestMapping(value = "/findRepartmentById/{departmentId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Department> findRepartmentById(@PathVariable("departmentId") Integer departmentId){
		Optional<Department> depOptional= null;
		try {
			depOptional = departmentServices.findByDepartmentId(departmentId);
			if(depOptional.isPresent())
				return new ResponseEntity<Department>(depOptional.get(), HttpStatus.OK);
			else
				return new ResponseEntity<Department>(depOptional.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error while find JobType Details by id");
			return new ResponseEntity<Department>(depOptional.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/deleteDepartment/{departmentId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteDepartment(@PathVariable("departmentId") Integer departmentId) throws JSONException {
		try {
			Optional<Department> depOptional = departmentServices.findByDepartmentId(departmentId);
			if (depOptional.isPresent()) {
				Department departmentToUpdate = depOptional.get();
				departmentToUpdate.setDepartmentId(departmentId);
				departmentToUpdate.setStatusId(Constants.UNACTIVE_STATUS_ID);
				departmentServices.saveDepartment(departmentToUpdate);
				return Helper.findResponseStatus("Department Deleted Successfully", Constants.SUCCESS_CODE);
			}
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Department Deletion is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Department Deletion is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
		return Helper.findResponseStatus("Department Deleted Successfully", Constants.SUCCESS_CODE);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/updateDepartment", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateDepartment(@Valid @RequestBody Department department) throws JSONException {
		try {
			Optional<Department> depOptional = departmentServices.findByDepartmentId(department.getDepartmentId());
			if (depOptional.isPresent()) {
				Department departmentToUpdate = depOptional.get();
				departmentToUpdate.setStatusId(Constants.ACTIVE_STATUS_ID);				
				departmentToUpdate.setModifiedDate(new Date(Calendar.getInstance().getTime().getTime()));
				departmentToUpdate.setDepartmentName(department.getDepartmentName());
				departmentServices.saveDepartment(departmentToUpdate);
			return Helper.findResponseStatus("Department Updated Successfully", Constants.SUCCESS_CODE);
			}else {
				return Helper.findResponseStatus("Department Updation is Failed", Constants.FAILURE_CODE);
			}
		}  catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("Department Updation is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("Department Updation is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

	
}
