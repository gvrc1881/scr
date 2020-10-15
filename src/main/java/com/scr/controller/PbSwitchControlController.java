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
import com.scr.util.Constants;
import com.scr.util.Helper;
import com.scr.message.response.ResponseStatus;
import com.scr.model.PbSwitchControl;
import com.scr.services.PbSwitchControlService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class PbSwitchControlController {
	
	static Logger logger = LogManager.getLogger(PowerBlockController.class);
	
	@Autowired
	private PbSwitchControlService PbSwitchControlService;
	
	@RequestMapping(value = "/findByExtentTypeAndExtentCode/{extentType}/{extentCode}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<PbSwitchControl> findByExtentTypeAndExtentCode(@PathVariable("extentType") String extentType, @PathVariable("extentCode") List<String> extentCode) throws JSONException {
		logger.info("Enter into findByExtendTypeAndExtendCode function");
		List<PbSwitchControl> PBSwitchControlList = null;
		try {
			logger.info("Calling service for pb switch control data");
			PBSwitchControlList = PbSwitchControlService.findByPbExtentTypeAndPbExtentCodeIn(extentType,extentCode);
			logger.info("Fetched pb switch control data = "+PBSwitchControlList.size());
			return PBSwitchControlList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the pb switch control data = "+npe.getMessage());
		}catch (Exception e) {
			logger.error("ERROR >>> while fetching the pb switch control data = "+e.getMessage());
		}
		logger.info("Exit from findByExtentTypeAndExtentCode function");
		return PBSwitchControlList;	
	}
	@RequestMapping(value = "/findAllPbSwitchControl", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<PbSwitchControl> findAllPbSwitchControl() throws JSONException {
		logger.info("Enter into findAll PbSwitchControl function");
		List<PbSwitchControl> switchList = null;
		try {
			logger.info("Calling service for pb switch data");
			switchList = PbSwitchControlService.findAll();
			logger.info("Fetched pb switch data = " + switchList.size());
			return switchList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the pb switch data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the pb switch data = " + e.getMessage());
		}
		logger.info("Exit from findAll pb switch function");
		return switchList;
	}
	@RequestMapping(value = "/addPbSwitchItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addPbSwitchItem(@RequestBody PbSwitchControl pbSwitchControl) {
		logger.info("Enter into addPbSwitchItem function with below request parameters ");
		logger.info("Request Parameters = "+pbSwitchControl.toString());
		try {
			logger.info("Calling service with request parameters.");
			PbSwitchControlService.save(pbSwitchControl);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Pb switch control added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Pb switch control data. "+npe.getMessage());
			return Helper.findResponseStatus("Pb switch control save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Pb switch control. "+e.getMessage());
			return Helper.findResponseStatus("Pb switch control save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findPbSwitchById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<PbSwitchControl> findPbSwitchItemById(@PathVariable Long id){
		Optional<PbSwitchControl> pbSwitchData = null;
		try {
			logger.info("Selected pb switch control Data  Id = "+id);
			pbSwitchData = PbSwitchControlService.findPbSwitchItemById(id);
			if(pbSwitchData.isPresent()) {
				logger.info("pb switch control = "+pbSwitchData.get());
				return new ResponseEntity<PbSwitchControl>(pbSwitchData.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<PbSwitchControl>(pbSwitchData.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find pb switch control  Details by id, "+e.getMessage());
			return new ResponseEntity<PbSwitchControl>(pbSwitchData.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updatePbSwitchItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updatePbSwitchItem(@RequestBody PbSwitchControl pbSwitchControl) {
		logger.info("Enter into updatePbSwitchItem function with below request parameters ");
		logger.info("Request Parameters = "+pbSwitchControl.toString());
		try {
			logger.info("Calling service with request parameters.");
			PbSwitchControlService.save(pbSwitchControl);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("pb SwitchControl updated successfully", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating pbSwitch Control  data. "+npe.getMessage());
			return Helper.findResponseStatus("pbSwitch Control  update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating pbSwitch Control  data. "+e.getMessage());
			return Helper.findResponseStatus("pbSwitch Control update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deletePbSwitchItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deletePbSwitchItemById(@PathVariable Long id) {
		logger.info("Enter into deletePbSwitchItemById function");
		logger.info("Selected Pb switch control Id = "+id);
		try {
			PbSwitchControlService.deletePbSwitchItemById(id);
			return Helper.findResponseStatus("Pb switch control Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting Pb switch control data"+npe.getMessage());
			return Helper.findResponseStatus("Pb switch control Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting Pb switch control data"+e.getMessage());
			return Helper.findResponseStatus("Pb switch control Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

}
