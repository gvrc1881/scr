package com.scr.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.scr.message.request.AssistanceRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Assistance;
import com.scr.model.ContentManagement;
import com.scr.model.WorkGroup;
import com.scr.model.Works;
import com.scr.services.AssistanceService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class AssistanceController {
	
	static Logger logger = LogManager.getLogger(AssistanceController.class);
	

	@Autowired
	private AssistanceService assistanceService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllAssistance", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Assistance> findAllAssistance() throws JSONException {
		logger.info("Enter into findAll Assistance function");
		List<Assistance> assistanceList = null;
		try {
			logger.info("Calling service for Assistance data");
			assistanceList = assistanceService.findAll();
			logger.info("Fetched Assistance data = " + assistanceList.size());
			return assistanceList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the Assistance data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the Assistance data = " + e.getMessage());
		}
		logger.info("Exit from findAll Assistance function");
		return assistanceList;
	}
	
	//Add Assistance
		@PostMapping(value="/addAssistance",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		@ResponseBody
		public ResponseStatus addObservation(
				@RequestParam("file") List<MultipartFile> file, 
				@RequestParam("workId") Works workId,
				@RequestParam("workGroupId") WorkGroup workGroupId,
				@RequestParam("typeOfAssistance") String typeOfAssistance,
				@RequestParam("assistance") String assistance,
				@RequestParam("requestedBy") String requestedBy,
				@RequestParam("requestedDate") Date requestedDate,
				@RequestParam("requestTo") String requestTo,
				@RequestParam("responseBy") String responseBy,
				@RequestParam("responseDate") Date responseDate,
				@RequestParam("response") String response,
				@RequestParam("remark") String remark,
				@RequestParam("status") String status

				) {
			try {
				logger.info("add Assistance");
				AssistanceRequest assistanceRequest = new AssistanceRequest();
				assistanceRequest.setWorkId(workId);
				assistanceRequest.setWorkGroupId(workGroupId);
				assistanceRequest.setTypeOfAssistance(typeOfAssistance);
				assistanceRequest.setAssistance(assistance);
				assistanceRequest.setRequestedBy(requestedBy);
				assistanceRequest.setRequestedDate(requestedDate);
				assistanceRequest.setRequestTo(requestTo);
				assistanceRequest.setResponseBy(responseBy);
				assistanceRequest.setResponseDate(responseDate);
				assistanceRequest.setResponse(response);
				assistanceRequest.setRemark(remark);
				assistanceRequest.setStatus(status);
				

				assistanceService.saveAssistance(assistanceRequest, file);
				return Helper.findResponseStatus("Assistance Data Added Successfully", Constants.SUCCESS_CODE);
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("ERROR >> While adding Assistance data. "+e.getMessage());
				return Helper.findResponseStatus("Assistance Addition is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
		}
		//Update Assistance
		@PutMapping("/updateAssistance")
		@ResponseBody
		public ResponseStatus updateAssistanceItem(
				@RequestParam("file") List<MultipartFile> file, 
				@RequestParam("id") Long id,
				@RequestParam("workId") Works workId,
				@RequestParam("workGroupId") WorkGroup workGroupId,
				@RequestParam("typeOfAssistance") String typeOfAssistance,
				@RequestParam("assistance") String assistance,
				@RequestParam("requestedBy") String requestedBy,
				@RequestParam("requestedDate") Date requestedDate,
				@RequestParam("requestTo") String requestTo,
				@RequestParam("responseBy") String responseBy,
				@RequestParam("responseDate") Date responseDate,
				@RequestParam("response") String response,
				@RequestParam("remark") String remark,
				@RequestParam("status") String status,
				@RequestParam("attachment") String attachment) {
			try {
				logger.info("Update Assistance");
				AssistanceRequest assistanceRequest = new AssistanceRequest();
				assistanceRequest.setId(id);
				assistanceRequest.setWorkId(workId);
				assistanceRequest.setWorkGroupId(workGroupId);
				assistanceRequest.setTypeOfAssistance(typeOfAssistance);
				assistanceRequest.setAssistance(assistance);
				assistanceRequest.setRequestedBy(requestedBy);
				assistanceRequest.setRequestedDate(requestedDate);
				assistanceRequest.setRequestTo(requestTo);
				assistanceRequest.setResponseBy(responseBy);
				assistanceRequest.setResponseDate(responseDate);
				assistanceRequest.setResponse(response);
				assistanceRequest.setRemark(remark);
				assistanceRequest.setStatus(status);
				assistanceRequest.setAttachment(attachment);
				logger.info("calling update assistance");
				String assistanceStatus = assistanceService.updateAssistanceData(assistanceRequest, file);
				if(assistanceStatus.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
					return Helper.findResponseStatus("assistance Data Updated Successfully", Constants.SUCCESS_CODE);
				else
					return Helper.findResponseStatus(assistanceStatus, Constants.FAILURE_CODE);
			}catch (Exception e) {
				logger.error("ERROR >> While updating assistance data. "+e.getMessage());
				return Helper.findResponseStatus("assistance Updation is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
			}
		}
	@RequestMapping(value = "/findAssistanceById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Assistance> findAssistanceById(@PathVariable Long id){
		Optional<Assistance> assistance = null;
		try {
			logger.info("Selected Assistance Id = "+id);
			assistance = assistanceService.findAssistanceById(id);
			if(assistance.isPresent()) {
				logger.info("assistance Data = "+assistance.get());
				return new ResponseEntity<Assistance>(assistance.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<Assistance>(assistance.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find assistance Details by id, "+e.getMessage());
			return new ResponseEntity<Assistance>(assistance.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/deleteAssistance/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteAssistanceById(@PathVariable Long id) {
		logger.info("Enter into deleteAssistanceById function");
		logger.info("Selected Assistance Id = "+id);
		try {
			assistanceService.deleteAssistanceById(id);
			return Helper.findResponseStatus("Assistance deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting Assistance data"+npe.getMessage());
			return Helper.findResponseStatus("Assistance Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting Assistance data"+e.getMessage());
			return Helper.findResponseStatus("Assistance Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
}
