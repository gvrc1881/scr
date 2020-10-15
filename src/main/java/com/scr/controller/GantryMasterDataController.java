package com.scr.controller;

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
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.GantryMasterData;
import com.scr.services.GantryMasterDataService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class GantryMasterDataController {

	static Logger logger = LogManager.getLogger(GantryMasterDataController.class);

	@Autowired
	private GantryMasterDataService gantryMasterDataService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllGantrys", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<GantryMasterData> findAllGantrys() throws JSONException {
		logger.info("Enter into findAll Gantrys function");
		List<GantryMasterData> gantryList = null;
		try {
			logger.info("Calling service for gantrys data");
			gantryList = gantryMasterDataService.findAll();
			logger.info("Fetched sector data = " + gantryList.size());
			return gantryList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the gantrys data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the gantrys data = " + e.getMessage());
		}
		logger.info("Exit from findAll Gantrys function");
		return gantryList;
	}
	@RequestMapping(value = "/addGantryItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addGantryItem(@RequestBody GantryMasterData gantryMasterData) {
		logger.info("Enter into addGantryItem function with below request parameters ");
		logger.info("Request Parameters = "+gantryMasterData.toString());
		try {
			logger.info("Calling service with request parameters.");
			gantryMasterDataService.save(gantryMasterData);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Gantry MasterData added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Gantry MasterData data. "+npe.getMessage());
			return Helper.findResponseStatus("Gantry MasterData save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Gantry MasterData data. "+e.getMessage());
			return Helper.findResponseStatus("Gantry MasterData save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findGantryItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<GantryMasterData> findGantryItemById(@PathVariable Long id){
		Optional<GantryMasterData> gantryMasterData = null;
		try {
			logger.info("Selected Gantry MasterData  Id = "+id);
			gantryMasterData = gantryMasterDataService.findGantryItemById(id);
			if(gantryMasterData.isPresent()) {
				logger.info("Gantry MasterData  Data = "+gantryMasterData.get());
				return new ResponseEntity<GantryMasterData>(gantryMasterData.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<GantryMasterData>(gantryMasterData.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find gantryMasterData  Details by id, "+e.getMessage());
			return new ResponseEntity<GantryMasterData>(gantryMasterData.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateGantryItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateGantryItem(@RequestBody GantryMasterData gantryMasterData) {
		logger.info("Enter into updateGantryItem function with below request parameters ");
		logger.info("Request Parameters = "+gantryMasterData.toString());
		try {
			logger.info("Calling service with request parameters.");
			gantryMasterDataService.save(gantryMasterData);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Gantry MasterData updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating Gantry MasterData  data. "+npe.getMessage());
			return Helper.findResponseStatus("Gantry MasterData  update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating Gantry MasterData  data. "+e.getMessage());
			return Helper.findResponseStatus("Gantry MasterData update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteGantryItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteGantryItemById(@PathVariable Long id) {
		logger.info("Enter into deleteGantryItemById function");
		logger.info("Selected Gantry MasterData Id = "+id);
		try {
			gantryMasterDataService.deleteGantryItemById(id);
			return Helper.findResponseStatus("Gantry MasterData Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting Gantry MasterData data"+npe.getMessage());
			return Helper.findResponseStatus("Gantry MasterData Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting Gantry MasterData data"+e.getMessage());
			return Helper.findResponseStatus("Gantry MasterData Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

}
