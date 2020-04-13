package com.scr.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.json.JSONException;
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
import com.scr.model.TractionEnergyTariff;
import com.scr.services.ContentManagementService;
import com.scr.services.TractionEnergyTariffService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TractionEnergyTariffController {
	private Logger logger = Logger.getLogger(TractionEnergyTariffController.class);
	
	@Autowired
	private TractionEnergyTariffService tractionEnergyTariffService;
	
	@Autowired
	private ContentManagementService contentManagementService;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllTractionEnergyTariff", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TractionEnergyTariff> tractionEneTariffList() throws JSONException {
		List<TractionEnergyTariff> tractionEnergyTariffList = null;
		try {
			logger.info("Fetch tractionEnergyTariffList Started");	
		tractionEnergyTariffList = tractionEnergyTariffService.findAll();
			logger.info("Fetch tractionEnergyTariffList Ended"+tractionEnergyTariffList);
		return tractionEnergyTariffList;
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return tractionEnergyTariffList;	
	}
	
	@RequestMapping(value = "/addTractionEnergyTariff", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveTractionEneTariff(@RequestBody TractionEnergyTariff tractionEnergyTariff){
		tractionEnergyTariff.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		TractionEnergyTariff saveTractionEneTariff = tractionEnergyTariffService.saveTractionEneTariff(tractionEnergyTariff);
		if (saveTractionEneTariff != null) {
			return Helper.findResponseStatus("Traction Energy Tariff added successfully", Constants.SUCCESS_CODE);
		}
		return null;
	}
	
	
	@RequestMapping(value = "/findTractionEnergyTariff/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<TractionEnergyTariff> findById(@PathVariable("id") Integer id){
		Optional<TractionEnergyTariff> ele = tractionEnergyTariffService.findById(id);
		return new ResponseEntity<>(ele.get(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateTractionEnergyTariff" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTractionEneTariff (@RequestBody TractionEnergyTariff tractionEnergyTariff) {
		tractionEnergyTariff.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		tractionEnergyTariffService.saveTractionEneTariff(tractionEnergyTariff);
		return Helper.findResponseStatus("Traction Energy Tariff updated successfully", Constants.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/deleteTractionEnergyTariff/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteTractionEneTariff(@PathVariable Integer id) {
		logger.info("*** id****"+id);
		tractionEnergyTariffService.deleteTractionEneTariffById(id);
		return Helper.findResponseStatus("Traction Energy Tariff Deleted successfully", Constants.SUCCESS_CODE);
	}
	
	@PostMapping("/tariffUploadFiles")
	@ResponseBody
	public ResponseStatus uploadAttachedFiles(
			@RequestParam("file") List<MultipartFile> file,
			@RequestParam("tractionEnergyTariffId") Integer tractionEnergyTariffId,
			@RequestParam("contentCategory") String contentCategory,
			@RequestParam("description") String description,
			@RequestParam("divisionCode") String divisionCode,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("zonal") String zonal,
			@RequestParam("FU") String FU,
			@RequestParam("contentTopic") String contentTopic) {
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			logger.info("File Name: "+contentCategory);
			responseStatus = tractionEnergyTariffService.storeUploadedFiles(file, contentCategory, description, divisionCode, createdBy, zonal, FU, contentTopic,tractionEnergyTariffId);
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
	
	@RequestMapping(value = "/attachedDocumentList/{tractionEneTariffId}", method = RequestMethod.GET ,headers = "Accept=application/json")	
	public ResponseEntity<List<ContentManagement>> getDocumentList( @PathVariable("tractionEneTariffId") Integer tractionEneTariffId){
		List<ContentManagement> contentManagementList = new ArrayList<>();
		try {
			logger.info("Getting tariff  Details  = "+tractionEneTariffId);	
			Optional<TractionEnergyTariff> tractionEneTariffObj =tractionEnergyTariffService.findById(tractionEneTariffId);
			if (tractionEneTariffObj.isPresent()) {
				TractionEnergyTariff tractionEneTariff = tractionEneTariffObj.get();
				String contentManagementIds = tractionEneTariff.getContentLink();
				if(contentManagementIds.contains(",")) {
				String[] arrayIds = contentManagementIds.split(",");
				 for(int i=0; i<arrayIds.length; i++) {
					 Optional<ContentManagement> contentManagementObj = contentManagementService.findById(Long.parseLong(arrayIds[i]));
					 if(contentManagementObj.isPresent()) {
						 contentManagementList.add(contentManagementObj.get());
					 }
			      }
				}else {
					 Optional<ContentManagement> contentManagementObj = contentManagementService.findById(Long.parseLong(contentManagementIds));
					 if(contentManagementObj.isPresent()) {
						 contentManagementList.add(contentManagementObj.get());
					 }
				}
				/*logger.info("before calling value"+contentManagementIds);
				contentManagementList = contentManagementService.findByIdIn(contentManagementIds);*/	
				logger.info("content size:::"+contentManagementList.size());
			}
			return new ResponseEntity<List<ContentManagement>>(contentManagementList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting DivisionHistory Details"+e.getMessage());
			return new ResponseEntity<List<ContentManagement>>(contentManagementList, HttpStatus.CONFLICT);
		}	
	}
	
}
