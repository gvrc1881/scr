package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.TpcBoard;
import com.scr.services.TPCBoardService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TPCBoardController {
	static Logger log = LogManager.getLogger(TPCBoardController.class);

	@Autowired
	private TPCBoardService tpcBoardService;
	
	@RequestMapping(value = "/findAllTPCBoard" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<TpcBoard> findAllTPCBoard(){
		log.info("Enter into findAllTPCBoard function");
		List<TpcBoard> tpcBoard = null;
		try {
			log.info("Calling service for tpc Board data");
			tpcBoard = tpcBoardService.findAll();
			log.info("Fetched Tpc Board data"+tpcBoard.size());
		}catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the Tpc Board data = "+npe.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the Tpc Board data = "+e.getMessage());
		}
		log.info("Exit from findAllTPCBoard function");
		return tpcBoard;
	}
	
	@RequestMapping(value = "/addTPCBoard" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addTPCBoard(@RequestBody TpcBoard tpcBoard) {
		log.info("Enter into addTPCBoard function with below request parameters ");
		log.info("Request Parameters = "+tpcBoard.toString());
		try {
			log.info("Calling service with request parameters.");
			tpcBoardService.save(tpcBoard);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Tpc Board added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Tpc Board data. "+npe.getMessage());
			return Helper.findResponseStatus("Tpc Board save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Tpc Board data. "+e.getMessage());
			return Helper.findResponseStatus("Tpc Board save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findTPCBoardById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<TpcBoard> findTPCBoardById(@PathVariable Long id){
		Optional<TpcBoard> tpcBoard = null;
		try {
			log.info("Selected Tpc Board Id = "+id);
			tpcBoard = tpcBoardService.findTPCBoardById(id);
			if(tpcBoard.isPresent()) {
				log.info("Tpc Board Data = "+tpcBoard.get());
				return new ResponseEntity<TpcBoard>(tpcBoard.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<TpcBoard>(tpcBoard.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Tpc Board Details by id, "+e.getMessage());
			return new ResponseEntity<TpcBoard>(tpcBoard.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateTPCBoard" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTPCBoard(@RequestBody TpcBoard tpcBoard) {
		log.info("Enter into updateTPCBoard function with below request parameters ");
		log.info("Request Parameters = "+tpcBoard.toString());
		try {
			log.info("Calling service with request parameters.");
			tpcBoardService.save(tpcBoard);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Tpc Board Deleted successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating tpc Board data. "+npe.getMessage());
			return Helper.findResponseStatus("tpc Board update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating Tpc Board data. "+e.getMessage());
			return Helper.findResponseStatus("Tpc Board update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteTPCBoard/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteTPCBoardById(@PathVariable Long id) {
		log.info("Enter into deleteTPCBoardById function");
		log.info("Selected Tpc Board Id = "+id);
		try {
			tpcBoardService.deleteTPCBoardById(id);
			return Helper.findResponseStatus("Tpc Board updated successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Tpc Board data"+npe.getMessage());
			return Helper.findResponseStatus("Tpc Board Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Tpc Board data"+e.getMessage());
			return Helper.findResponseStatus("Tpc Board Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	@RequestMapping(value = "/existsTpcBoardAndDataDiv/{tpcBoard}/{dataDiv}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsTpcBoardAndDataDiv(@PathVariable("tpcBoard") String tpcBoard ,@PathVariable("dataDiv") String dataDiv){
			
		try {
			log.info("Request for checking exists tpcBoard and dataDiv.");
			
			return tpcBoardService.existsByTpcBoardAndDataDiv(tpcBoard,dataDiv);	
		} catch (Exception e) {
			log.error("Error while checking exists tpcBoard and unitName..."+e.getMessage());
			return false;
		}
	}
}