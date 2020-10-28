package com.scr.controller;

import java.util.List;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.SwitchMaintenenceHistory;
import com.scr.services.SwitchMaintenenceHistoryService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SwitchMaintenenceHistoryController {
	
	static Logger logger = LogManager.getLogger(SwitchMaintenenceHistoryController.class);
	
	@Autowired
	private SwitchMaintenenceHistoryService switchMaintenenceHistoryService;
	
	/*@Autowired
	private SMHMapper smhMapper;*/
	
	@RequestMapping(value = "/addSwitchMaintenenceHistory" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addSwitchMaintenenceHistory(@RequestBody SwitchMaintenenceHistory switchMaintenenceHistory) {
		logger.info("Enter into addSwitchMaintenenceHistory function with below request parameters ");
		logger.info("Request Parameters = "+switchMaintenenceHistory.toString());
		try {
			logger.info("Calling service with request parameters.");
			Optional<SwitchMaintenenceHistory> SMHData = switchMaintenenceHistoryService.findByPbOperationSeqIdAndIoLocationAndIoType(switchMaintenenceHistory.getPbOperationSeqId(),switchMaintenenceHistory.getIoLocation(),switchMaintenenceHistory.getIoType());
			if (SMHData.isPresent()) {
				SwitchMaintenenceHistory SMHDetails = SMHData.get();
				SMHDetails.setCloseDoneTimeLapse(switchMaintenenceHistory.getCloseDoneTimeLapse());
				SMHDetails.setCloseTimeLapse(switchMaintenenceHistory.getCloseTimeLapse());
				SMHDetails.setFieldNoIoClose(switchMaintenenceHistory.getFieldNoIoClose());
				SMHDetails.setFieldNoIoCloseDone(switchMaintenenceHistory.getFieldNoIoCloseDone());
				SMHDetails.setFieldNoIoOpen(switchMaintenenceHistory.getFieldNoIoOpen());
				SMHDetails.setFieldNoIoOpenDone(switchMaintenenceHistory.getFieldNoIoOpenDone());
				SMHDetails.setIsFieldOperated(switchMaintenenceHistory.getIsFieldOperated());
				SMHDetails.setIoClosedBy(switchMaintenenceHistory.getIoClosedBy());
				SMHDetails.setIoClosedDateTime(switchMaintenenceHistory.getIoClosedDateTime());
				SMHDetails.setIoClosedDateTimeDone(switchMaintenenceHistory.getIoClosedDateTimeDone());
				SMHDetails.setIoOpenedBy(switchMaintenenceHistory.getIoOpenedBy());
				SMHDetails.setIoOpenedDateTime(switchMaintenenceHistory.getIoOpenedDateTime());
				SMHDetails.setIoOpenedDateTimeDone(switchMaintenenceHistory.getIoOpenedDateTimeDone());
				SMHDetails.setTpcNoIoClose(switchMaintenenceHistory.getTpcNoIoClose());
				SMHDetails.setTpcNoIoCloseDone(switchMaintenenceHistory.getTpcNoIoCloseDone());
				SMHDetails.setTpcNoIoOpen(switchMaintenenceHistory.getTpcNoIoOpen());
				SMHDetails.setTpcNoIoOpenDone(switchMaintenenceHistory.getTpcNoIoOpenDone());
				switchMaintenenceHistoryService.save(SMHDetails);
			} else {
				switchMaintenenceHistoryService.save(switchMaintenenceHistory);
			}
			
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Switch Maintenence History added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Switch Maintenence History data. "+npe.getMessage());
			return Helper.findResponseStatus("Switch Maintenence History save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Switch Maintenence History . "+e.getMessage());
			return Helper.findResponseStatus("Switch Maintenence History save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/getSMHDataBasedOnPbId/{pbId}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<SwitchMaintenenceHistory> getSMHDataBasedOnPbId(@PathVariable String pbId){
		logger.info("Enter into  getSMHDataBasedOnPbId function");
		List<SwitchMaintenenceHistory> SMHData = null;
		try {
			logger.info("Selected pbId  Id = "+pbId);
			SMHData = switchMaintenenceHistoryService.findByPbOperationSeqId(pbId);
			logger.info("Fetched SMH Data size= " + SMHData.size());
				return SMHData;
		} catch (Exception e) {
			logger.error("Error >>  while getting SMH  Details by id, "+e.getMessage());
		}
		logger.info("Exit from getSMHDataBasedOnPbId function");
		return SMHData;
	}
	
	@RequestMapping(value = "/getSMHDataForOperation/{pbId}/{section}/{elementarySectionCode}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<SwitchMaintenenceHistory> getSMHDataForOperation(@PathVariable String pbId,@PathVariable String section,@PathVariable List<String> elementarySectionCode){
		logger.info("Enter into  getSMHDataBasedOnPbId function");
		//List<SwitchMaintenenceHistory> SMHData = null;
		try {
			logger.info("Selected pbId  Id = "+pbId);
			List<SwitchMaintenenceHistory> SMHData = switchMaintenenceHistoryService.prepareSMHDataForSwitchOperation(pbId,section,elementarySectionCode);
			logger.info("Fetched SMH Data size= " + SMHData);
				return SMHData;
		} catch (Exception e) {
			logger.error("Error >>  while getting SMH  Details by id, "+e.getMessage());
		}
		logger.info("Exit from getSMHDataBasedOnPbId function");
		return null;
	}

}
