package com.scr.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scr.message.request.CopyWPAndWPA;
import com.scr.message.response.ResponseStatus;
import com.scr.message.response.WPADailyProgressResponse;
import com.scr.message.response.WPASectionTargetsResponse;
import com.scr.model.ContentManagement;
import com.scr.model.TractionEnergyTariff;
import com.scr.model.WPADailyProgress;
import com.scr.model.WPASectionPopulation;
import com.scr.model.WPASectionTargets;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhases;
import com.scr.model.Works;
import com.scr.services.ContentManagementService;
import com.scr.services.WorksServices;
import com.scr.util.Constants;
import com.scr.util.Helper;

@RestController
@RequestMapping("/scr/api")
public class WorksController {
	
	private Logger log = Logger.getLogger(WorksController.class);
	
	@Autowired
	private WorksServices worksServices;
	
	@Autowired
	private ContentManagementService contentManagementService;
	
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
	
	
	@RequestMapping(value = "/existsWorkNameAndId/{id}/{workName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsWorkNameAndId(@PathVariable("id") Integer workId,@PathVariable("workName") String workName){
		log.info("Entered into existsWorkNameAndId function");
		Boolean result;
		try {
			Optional<Works> worksDetails = worksServices.findByWorkName(workName);
			
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(worksDetails.isPresent()) {
				Works works = worksDetails.get();
				log.info("comparing with id's");
				if (workId.equals(works.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and makecode..."+e.getMessage());
			return false;
		}
		
	}
	
	@PostMapping(value="/copyWPAndWPA")
	@ResponseBody
	public ResponseStatus copyWPAndWPA(@RequestBody CopyWPAndWPA copyWPAndWPA) {
		ResponseStatus responseStatus = new ResponseStatus();
		log.info("*** Enter into copyWPAndWPA function ***");
		log.info("*** standard phases size**"+copyWPAndWPA.getStandardPhases().size()+"*** standard phase activity**"+copyWPAndWPA.getStandardPhaseActivities().size());
		try {			
			worksServices.saveWPAndWPA(copyWPAndWPA);
			log.info("Preparing the return response and copyWPAndWPA function end ");
			return Helper.findResponseStatus("WP Data And WPA Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding WP Data And WPA Data. "+npe.getMessage());
			return Helper.findResponseStatus("WP  And WPA  Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding WP Data And WPA Data. "+e.getMessage());
			return Helper.findResponseStatus("WP  And WPA  Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/getWorkGroupsBasedOnWork/{workId}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<WorkGroup> getWorkGroupsBasedOnWork(@PathVariable("workId") Integer workId){
		log.info("Enter into getWorkGroupsBasedOnWork function ");
		List<WorkGroup> workGroups = null;
		try {
			Optional<Works> work = worksServices.findById(workId);
			if (work.isPresent()) {
				workGroups = worksServices.getWorkGroupsBasedOnWork(work.get());
			}
			return workGroups;
		} catch (Exception e) {
			log.error("Error >>  while find work Group Details by work id, "+e.getMessage());
		}
		return workGroups;
	}
	
	@RequestMapping(value = "/getWorkPhasesBasedOnWork/{workId}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<WorkPhases> getWorkPhasesBasedOnWork(@PathVariable("workId") Integer workId){
		log.info("Enter into getWorkPhasesBasedOnWork function ");
		List<WorkPhases> workGroups = null;
		try {
			Optional<Works> work = worksServices.findById(workId);
			if (work.isPresent()) {
				workGroups = worksServices.getWorkPhasesBasedOnWork(work.get());
			}
			return workGroups;
		} catch (Exception e) {
			log.error("Error >>  while find work phase Details by work id, "+e.getMessage());
		}
		return workGroups;
	}
	
	@RequestMapping(value = "/getWPADailyProgressBasedOnGroupActivity/{workId}/{workGroupId}/{workPhaseId}/{date}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<WPADailyProgressResponse> getWPADailyProgressBasedOnGroupActivity(@PathVariable("workId") Integer workId,@PathVariable("workGroupId") Long workGroupId,@PathVariable("workPhaseId") Integer workPhaseId,@PathVariable("date") Date date){
		log.info("Enter into getWorkPhasesBasedOnWork function Id***"+workId+"groupId"+workGroupId+"** phase Id ***"+workPhaseId+"**date***"+date);
		List<WPADailyProgressResponse> wpaDailyProgresses = null;
		
		try {
			wpaDailyProgresses = worksServices.getWPADailyProgressBasedOnGroupActivity(workId,workGroupId,workPhaseId,date);
			return wpaDailyProgresses;
		} catch (Exception e) {
			log.error("Error >>  while find WPA daily progess data by group and activity, "+e.getMessage());
		}
		return wpaDailyProgresses;
	}
	
	@PostMapping(value="/saveWPADailyProgress")
	@ResponseBody
	public ResponseStatus saveWPADailyProgress(@RequestBody List<WPADailyProgress> wpaDailyProgressRequest) {
		log.info("*** Enter into saveWPADailyProgress function ***");
		try {			
			worksServices.saveWPADailyProgress(wpaDailyProgressRequest);
			log.info("Preparing the return response and saveWPADailyProgress function end ");
			return Helper.findResponseStatus("WPA daily progress Data Added Successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding WPA daily progress Data. "+npe.getMessage());
			return Helper.findResponseStatus("WPA daily progress Addition is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding WPA daily progress Data. "+e.getMessage());
			return Helper.findResponseStatus("WPA daily progress   Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/getWPASectionPopulationBasedOnGroupActivity/{workId}/{workGroupId}/{workPhaseId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<WPASectionPopulation> getWPASectionPopulationBasedOnGroupActivity(
			@PathVariable("workId") Integer workId, @PathVariable("workGroupId") Long workGroupId,
			@PathVariable("workPhaseId") Integer workPhaseId) {
		log.info("Enter into getWPASectionPopulationBasedOnGroupActivity function Id***" + workId + "groupId"
				+ workGroupId + "** phase Id ***" + workPhaseId);
		List<WPASectionPopulation> wpaSectionPopulations = null;

		try {
			wpaSectionPopulations = worksServices.getWPASectionPopulationBasedOnGroupActivity(workId, workGroupId,
					workPhaseId);
			return wpaSectionPopulations;
		} catch (Exception e) {
			log.error("Error >>  while find WPA sectionn population data by group and activity, " + e.getMessage());
		}
		return wpaSectionPopulations;
	}

	@PostMapping(value = "/saveWPASectionPopulation")
	@ResponseBody
	public ResponseStatus saveWPASectionPopulation(@RequestBody List<WPASectionPopulation> wpaSectionPopulations) {
		log.info("*** Enter into saveWPASectionPopulation function ***");
		try {
			worksServices.saveWPASectionPopulation(wpaSectionPopulations);
			log.info("Preparing the return response and saveWPASectionPopulation function end ");
			return Helper.findResponseStatus("WPA section population Data Added Successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While adding WPA section population Data. " + npe.getMessage());
			return Helper.findResponseStatus("WPA section population Addition is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While adding WPA section population Data. " + e.getMessage());
			return Helper.findResponseStatus("WPA section population   Addition is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/getWPASectionPopulationBasedOnGroupActivityYear/{workId}/{workGroupId}/{workPhaseId}/{year}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<WPASectionTargetsResponse> getWPASectionPopulationBasedOnGroupActivityYear(
			@PathVariable("workId") Integer workId, @PathVariable("workGroupId") Long workGroupId,
			@PathVariable("workPhaseId") Integer workPhaseId, @PathVariable("year") Integer year) {
		log.info("Enter into getWPASectionPopulationBasedOnGroupActivityYear function Id***" + workId + "groupId"
				+ workGroupId + "** phase Id ***" + workPhaseId);
		List<WPASectionTargetsResponse> wpaSectionTargetsResponses = null;

		try {
			wpaSectionTargetsResponses = worksServices.getWPASectionPopulationBasedOnGroupActivityYear(workId, workGroupId,workPhaseId,year);
			return wpaSectionTargetsResponses;
		} catch (Exception e) {
			log.error("Error >>  while find WPA sectionn population data by group and activity, " + e.getMessage());
		}
		return wpaSectionTargetsResponses;
	}
	
	@PostMapping(value = "/saveWPASectionTargets")
	@ResponseBody
	public ResponseStatus saveWPASectionTargets(@RequestBody List<WPASectionTargets> wpaSectionTargets) {
		log.info("*** Enter into saveWPASectionTargets function ***");
		try {
			worksServices.saveWPASectionTargets(wpaSectionTargets);
			log.info("Preparing the return response and saveWPASectionTargets function end ");
			return Helper.findResponseStatus("WPA section targets Data Added Successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While adding WPA section targets Data. " + npe.getMessage());
			return Helper.findResponseStatus("WPA section targets Addition is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While adding WPA section targets Data. " + e.getMessage());
			return Helper.findResponseStatus("WPA section targets   Addition is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}
	
	@PostMapping("/projectUploadFiles")
	@ResponseBody
	public ResponseStatus uploadAttachedFiles(
			@RequestParam("file") List<MultipartFile> file,
			@RequestParam("workId") Integer workId,
			@RequestParam("contentCategory") String contentCategory,
			@RequestParam("description") String description,
			@RequestParam("divisionCode") String divisionCode,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("zonal") String zonal,
			@RequestParam("FU") String FU,
			@RequestParam("contentTopic") String contentTopic) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			log.info("File Name: "+contentCategory);
			log.info("fun_unit=="+FU);
			responseStatus = worksServices.storeUploadedFiles(file, contentCategory, description, divisionCode, createdBy, zonal,FU, contentTopic,workId);
			log.info("File Saved Successfully!");
		} catch (NullPointerException e) {
			log.error(e);
			return Helper.findResponseStatus("File saving is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error(e);
			return Helper.findResponseStatus("File saving is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
		return responseStatus;
	}
	
	@RequestMapping(value = "/projectsAttachedDocumentList/{workId}", method = RequestMethod.GET ,headers = "Accept=application/json")	
	public ResponseEntity<List<ContentManagement>> getDocumentList( @PathVariable("workId") Integer workId){
		List<ContentManagement> contentManagementList = new ArrayList<>();
		try {
			log.info("Getting Project  Details  = "+workId);	
			Optional<Works> projObj =worksServices.findById(workId);
			if (projObj.isPresent()) {
				Works project = projObj.get();
				if(project.getContentLink() != null) {
					contentManagementList = contentManagementService.findByCommonFileId(Long.parseLong(project.getContentLink()));
				}
				
				log.info("content size:::"+contentManagementList.size());
			}
			return new ResponseEntity<List<ContentManagement>>(contentManagementList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while getting DivisionHistory Details"+e.getMessage());
			return new ResponseEntity<List<ContentManagement>>(contentManagementList, HttpStatus.CONFLICT);
		}	
	}

}
