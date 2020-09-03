package com.scr.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
			logger.info("Fetch tractionEnergyTariffList data count ::"+tractionEnergyTariffList.size());
		return tractionEnergyTariffList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the tariff data = "+npe.getMessage());
		}catch (Exception e) {
			logger.error("ERROR >>> while fetching the tariff data = "+e.getMessage());
		}
		logger.info("Exit from tractionEneTariffList function");
		return tractionEnergyTariffList;	
	}
	
	@RequestMapping(value = "/addTractionEnergyTariff", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveTractionEneTariff(@RequestBody TractionEnergyTariff tractionEnergyTariff){
		logger.info("Enter into saveTractionEneTariff function with below request parameters ");
		logger.info("Request Parameters = "+tractionEnergyTariff.toString());
		tractionEnergyTariff.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		try {
			logger.info("Calling service with request parameters.");
			tractionEnergyTariffService.saveTractionEneTariff(tractionEnergyTariff);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Traction energy tariff added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding tariff data. "+npe.getMessage());
			return Helper.findResponseStatus("Traction energy tariff save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding tariff data. "+e.getMessage());
			return Helper.findResponseStatus("Traction energy tariff save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findTractionEnergyTariff/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<TractionEnergyTariff> findById(@PathVariable("id") Integer id){
		Optional<TractionEnergyTariff> tariff = null;
		try {
			logger.info("Selected Tariff Id = "+id);
			tariff = tractionEnergyTariffService.findById(id);
			if(tariff.isPresent()) {
				logger.info("Tariff Data = "+tariff.get());
				return new ResponseEntity<TractionEnergyTariff>(tariff.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<TractionEnergyTariff>(tariff.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			logger.error("Error >>  while find Tariff Details by id, "+e.getMessage());
			return new ResponseEntity<TractionEnergyTariff>(tariff.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updateTractionEnergyTariff" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTractionEneTariff (@RequestBody TractionEnergyTariff tractionEnergyTariff) {
		logger.info("Enter into updateTractionEneTariff function with below request parameters ");
		logger.info("Request Parameters = "+tractionEnergyTariff.toString());
		try {
			logger.info("Calling service with request parameters.");
			tractionEnergyTariff.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			tractionEnergyTariffService.saveTractionEneTariff(tractionEnergyTariff);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Traction energy tariff updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating tariff data. "+npe.getMessage());
			return Helper.findResponseStatus("Traction energy tariff update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating tariff data. "+e.getMessage());
			return Helper.findResponseStatus("Traction energy tariff update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteTractionEnergyTariff/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteTractionEneTariff(@PathVariable Integer id) {
		logger.info("Enter into deleteTractionEneTariff function");
		logger.info("Selected Tariff Id = "+id);
		try {
			tractionEnergyTariffService.deleteTractionEneTariffById(id);
			return Helper.findResponseStatus("Traction energy tariff Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting tariff data"+npe.getMessage());
			return Helper.findResponseStatus("Traction energy tariff Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting tariff data"+e.getMessage());
			return Helper.findResponseStatus("Traction energy tariff Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
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
				if(tractionEneTariff.getContentLink() != null) {
					contentManagementList = contentManagementService.findByCommonFileId(Long.parseLong(tractionEneTariff.getContentLink()));
				}
				
				/*String contentManagementIds = tractionEneTariff.getContentLink();
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
				}*/
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
	
	@RequestMapping(value = "/existsFromDate/{supplier}/{fromDate}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsTariff(@PathVariable("supplier") String supplier ,@PathVariable("fromDate") String fromDate){
			
		try {
            logger.info("Request for checking exists supplier and from date...");
			return tractionEnergyTariffService.existsBySupplierAndFromDate(supplier,Helper.convertStringToTimestamp(fromDate));
		} catch (Exception e) {
			logger.error("Error while checking exists supplier and from date..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existsFromDateAndId/{supplier}/{fromDate}/{id}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsTariffFromDateAndId(@PathVariable("supplier") String supplier ,@PathVariable("fromDate") String fromDate, @PathVariable("id") Integer id){
		logger.info("Request for checking exists supplier and from date and id..."+supplier+"from date:"+fromDate+"id:"+id);
		Boolean result;
		try {
			Optional<TractionEnergyTariff> tractionEnergyTariff = tractionEnergyTariffService.findBySupplierAndFromDate(supplier,Helper.convertStringToTimestamp(fromDate));
			if(tractionEnergyTariff.isPresent()) {
				TractionEnergyTariff tractionEnergyTariffDetails = tractionEnergyTariff.get();
				logger.info("comparing with id's"+id+"other id"+tractionEnergyTariffDetails.getId());
				if (id.equals(tractionEnergyTariffDetails.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and Tariff..."+e.getMessage());
			return false;
		}
	}
	
}
