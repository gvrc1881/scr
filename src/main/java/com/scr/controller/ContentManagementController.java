/**
 * 
 */
package com.scr.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scr.message.request.ContentManagementRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.ContentManagement;
import com.scr.services.ContentManagementService;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@RestController
@RequestMapping("/scr/api")
public class ContentManagementController {
	static Logger logger = LogManager.getLogger(ContentManagementController.class);
	
	@Autowired
	private ContentManagementService service = null;
	
	@PostMapping("/uploadAttachedFiles")
	@ResponseBody
	public ResponseStatus uploadAttachedFiles(
			@RequestParam("file") List<MultipartFile> file, 
			@RequestParam("GenOps") String GenOps,
			@RequestParam("description") String description,
			@RequestParam("divisionCode") String divisionCode,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("zonal") String zonal,
			@RequestParam("FU") String FU,
			@RequestParam("topic") String topic,
			@RequestParam("assetTypeRlyId") String assetTypeRlyId,
			@RequestParam("make") String make,
			@RequestParam("model") String model,
			@RequestParam("docCategory") String docCategory) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			logger.info("File Name: "+GenOps);
			responseStatus = service.storeUploadedFiles(file, GenOps, description, divisionCode, createdBy, zonal, FU, topic, assetTypeRlyId, make, model, docCategory);
			logger.info("File Saved Successfully!");
		} catch (NullPointerException e) {
			logger.error(e);
			return Helper.findResponseStatus("File saving is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error(e);
			return Helper.findResponseStatus("File saving is Fail with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
		return responseStatus;
	}
	
	@RequestMapping(value = "/getUploadedFiles/{id}/{GenOps}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<ContentManagement> departmentList(@PathVariable("id") Integer createdBy, @PathVariable("GenOps") String GenOps) throws JSONException {
		List<ContentManagement> conList = new ArrayList<>();
		try {
			logger.info("Fetch Content Management Started");
			conList = service.findByCreatedByAndGenOps(createdBy, GenOps.replace("-", " "));
			logger.info("Fetch Content Management Ended");
			return conList;
		} catch (NullPointerException e) {
			logger.error(e);
			return conList;
		} catch (Exception e) {
			logger.error(e);
			return conList;
		}
	}
	
	@RequestMapping(value = "/deleteFile/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteFile(@PathVariable("id") Long id) throws JSONException {		
		try {
			logger.info("Deleting File"+id);
			service.deleteById(id);
			return Helper.findResponseStatus("File Deleted Successfully", Constants.SUCCESS_CODE);				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while deleteing File Details");
			return Helper.findResponseStatus(e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/updateDescription", method = RequestMethod.PUT ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public ResponseStatus updateDescription(@RequestBody ContentManagementRequest  contentManagement){
		try {
			logger.info("Upadating Content Management Description  Details");
			List<ContentManagement> updatedContentManagement = service.findByCommonFileId(contentManagement.getCommonFileId());
			if(updatedContentManagement != null && !updatedContentManagement.isEmpty()) {
				ResponseStatus response = service.updateDescription(updatedContentManagement, contentManagement);  
				if (response.getCode() == Constants.SUCCESS_CODE)
					return Helper.findResponseStatus("Content Management Description Updated Successfully", Constants.SUCCESS_CODE);
				else
					return Helper.findResponseStatus("Content Management Description Updation is Fail", Constants.FAILURE_CODE);
			}else
				return Helper.findResponseStatus("Content Management Description Updation is Fail", Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("Error while Upadating Content Management Description  Details");
			e.printStackTrace();
			return Helper.findResponseStatus(e.getLocalizedMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
}
