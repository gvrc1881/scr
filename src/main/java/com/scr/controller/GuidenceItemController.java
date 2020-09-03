package com.scr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.scr.message.response.ResponseStatus;
import com.scr.model.ContentManagement;
import com.scr.model.GuidenceItem;
import com.scr.services.ContentManagementService;
import com.scr.services.GuidenceItemService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class GuidenceItemController {
	
	static Logger log = Logger.getLogger(GuidenceItemController.class);
	
	@Autowired
	private GuidenceItemService guidenceItemService;
	
	@Autowired
	private ContentManagementService contentManagementService;
	
	@RequestMapping(value = "/findAllGuidenceItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<GuidenceItem> findAllGuidenceItems(){
		log.info("Enter into findAllGuidenceItems function");
		List<GuidenceItem> guidenceItem = null;
		try {
			log.info("Calling service for guidence item data");
			guidenceItem = guidenceItemService.findAll();
			log.info("Fetched guidence item data ***"+guidenceItem.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the guidence item data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the guidence item data = "+e.getMessage());
		}
		log.info("Exit from findAllGuidenceItems function");
		return guidenceItem;
	}
	
	@RequestMapping(value = "/addGuidenceItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addGuidenceItem(@RequestBody GuidenceItem guidenceItem) {
		log.info("Enter into addGuidenceItem function with below request parameters ");
		log.info("Request Parameters = "+guidenceItem.toString());
		try {
			log.info("Calling service with request parameters.");
			guidenceItemService.save(guidenceItem);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Guidence item added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding guidence item data. "+npe.getMessage());
			return Helper.findResponseStatus("guidence item save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding guidence item data. "+e.getMessage());
			return Helper.findResponseStatus("guidence item save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findGuidenceItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<GuidenceItem> findGuidenceItemById(@PathVariable Integer id){
		Optional<GuidenceItem> guidenceItem = null;
		try {
			log.info("Selected Guidence Item Id = "+id);
			guidenceItem = guidenceItemService.findGuidenceItemById(id);
			if(guidenceItem.isPresent()) {
				log.info("Guidence Item Data = "+guidenceItem.get());
				return new ResponseEntity<GuidenceItem>(guidenceItem.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<GuidenceItem>(guidenceItem.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Guidence Item Details by id, "+e.getMessage());
			return new ResponseEntity<GuidenceItem>(guidenceItem.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateGuidenceItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateGuidenceItem(@RequestBody GuidenceItem guidenceItem) {
		log.info("Enter into updateGuidenceItem function with below request parameters ");
		log.info("Request Parameters = "+guidenceItem.toString());
		try {
			log.info("Calling service with request parameters.");
			guidenceItemService.save(guidenceItem);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Guidence Item updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating guidence item data. "+npe.getMessage());
			return Helper.findResponseStatus("Guidence item update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating guidence item data. "+e.getMessage());
			return Helper.findResponseStatus("Guidence item update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteGuidenceItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteGuidenceItemById(@PathVariable Integer id) {
		log.info("Enter into deleteGuidenceItemById function");
		log.info("Selected Guidence item Id = "+id);
		try {
			guidenceItemService.deleteGuidenceItemById(id);
			return Helper.findResponseStatus("Guidence Item updated successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Guidence Item data"+npe.getMessage());
			return Helper.findResponseStatus("Guidence Item Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Guidence Item data"+e.getMessage());
			return Helper.findResponseStatus("Guidence Item Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@PostMapping("/guidenceItemUploadFiles")
	@ResponseBody
	public ResponseStatus uploadAttachedFiles(
			@RequestParam("file") List<MultipartFile> file,
			@RequestParam("guidenceItemId") Integer guidenceItemId,
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
			responseStatus = guidenceItemService.storeUploadedFiles(file, contentCategory, description, divisionCode, createdBy, zonal, FU, contentTopic,guidenceItemId);
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
	
	@RequestMapping(value = "/guidenceItemAttachedDocumentList/{guidenceItemId}", method = RequestMethod.GET ,headers = "Accept=application/json")	
	public ResponseEntity<List<ContentManagement>> getDocumentList( @PathVariable("guidenceItemId") Integer guidenceItemId){
		List<ContentManagement> contentManagementList = new ArrayList<>();
		try {
			log.info("Getting guidence item  id  = "+guidenceItemId);	
			Optional<GuidenceItem> guidenceItem = guidenceItemService.findGuidenceItemById(guidenceItemId);
			if (guidenceItem.isPresent()) {
				GuidenceItem guidenceItemDetails = guidenceItem.get();
				if(guidenceItemDetails.getContentLink() != null) {
					contentManagementList = contentManagementService.findByCommonFileId(Long.parseLong(guidenceItemDetails.getContentLink()));
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
