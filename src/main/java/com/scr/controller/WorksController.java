package com.scr.controller;

import java.sql.Timestamp;
import java.util.Calendar;
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
import com.scr.model.Works;
import com.scr.services.WorksServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@RestController
@RequestMapping("/scr/api")
public class WorksController {
	
	private Logger log = Logger.getLogger(WorksController.class);
	
	@Autowired
	private WorksServices worksServices;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllWorks", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Works> workList() throws JSONException {
		log.info("Enter into workList function");
		List<Works> workList = null;
		try {
			log.info("Calling service for works data");	
			workList = worksServices.findAll();
			log.info("Fetched works data***"+workList.size());
			return workList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the works data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the works data = "+e.getMessage());
		}
		log.info("Exit from workList function");
		return workList;	
	}
	
	@RequestMapping(value = "/addWork", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus save(@RequestBody Works work){
		log.info("Enter into save function with below request parameters ");
		log.info("Request Parameters = "+work.toString());
		work.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		work.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		try {
			log.info("Calling service with request parameters.");
				 worksServices.save(work);
				log.info("Preparing the return response");
				return Helper.findResponseStatus("work added successfully", Constants.SUCCESS_CODE);
			}catch(NullPointerException npe) {
				log.error("ERROR >> While adding work data. "+npe.getMessage());
				return Helper.findResponseStatus("work save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
			}
			catch (Exception e) {
				log.error("ERROR >> While adding work data. "+e.getMessage());
				return Helper.findResponseStatus("work save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
	}
	
	
	@RequestMapping(value = "/findWork/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Works> findById(@PathVariable("id") Integer id){
		Optional<Works> work = null;
		try {
			log.info("Selected work Id = "+id);
			work = worksServices.findById(id);
			if(work.isPresent()) {
				log.info("work Data = "+work.get());
				return new ResponseEntity<Works>(work.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<Works>(work.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find work Details by id, "+e.getMessage());
			return new ResponseEntity<Works>(work.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updateWork" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus update (@RequestBody Works work) {
		log.info("Enter into update function with below request parameters ");
		log.info("Request Parameters = "+work.toString());
		try {
			log.info("Calling service with request parameters.");
			work.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			worksServices.save(work);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Work updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating work data. "+npe.getMessage());
			return Helper.findResponseStatus("Work update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating work data. "+e.getMessage());
			return Helper.findResponseStatus("work update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteWork/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteById(@PathVariable Integer id) {
		log.info("Enter into deleteById function");
		log.info("Selected Work Id = "+id);
		try {
			worksServices.deleteById(id);
			return Helper.findResponseStatus("Work Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting work data"+npe.getMessage());
			return Helper.findResponseStatus("Work Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting work data"+e.getMessage());
			return Helper.findResponseStatus("Work Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	
	@RequestMapping(value = "/existsWorkName/{workName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsWorkName(@PathVariable("workName") String workName){
		try {
			return worksServices.existsByWorkName(workName);
		} catch (Exception e) {
			log.error("Error while checking exists work name.");
			return false;
		}
	}

}
