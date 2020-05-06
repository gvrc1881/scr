package com.scr.controller;

import java.util.List;
import java.util.Optional;
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
import com.scr.model.TpcBoardReportingFacility;
import com.scr.services.TPCBoardDepotAssocService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TPCBoardDepotAssocController {
	@Autowired
	private TPCBoardDepotAssocService tpcBoardDepotAssocService;
	
	
	@RequestMapping(value = "/findAllTPCBoardDepotAssoc" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<TpcBoardReportingFacility> findAllTPCBoardDepotAssoc(){
		List<TpcBoardReportingFacility> tpcBoardDepotAssoc = tpcBoardDepotAssocService.findAll();
		return tpcBoardDepotAssoc;
	}
	
	@RequestMapping(value = "/addTPCBoardDeotAssoc" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addTPCBoardDeotAssoc(@RequestBody TpcBoardReportingFacility tpcBoardReportingFacility) {
		tpcBoardDepotAssocService.save(tpcBoardReportingFacility);
		return Helper.findResponseStatus("TPC Board Depot Assoc added successfully", Constants.SUCCESS_CODE);

	}
	
	
	@RequestMapping(value = "/findTPCBoardDepotAssocById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<TpcBoardReportingFacility> findTPCBoardDepotAssocById(@PathVariable Long id){
		Optional<TpcBoardReportingFacility> tpcBoardDepotAssoc = tpcBoardDepotAssocService.findTPCBoardDepotAssocById(id);
		return new ResponseEntity<TpcBoardReportingFacility>(tpcBoardDepotAssoc.get(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/updateTPCBoardDepotAssoc" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTPCBoard(@RequestBody TpcBoardReportingFacility tpcBoardReportingFacility) {
		tpcBoardDepotAssocService.save(tpcBoardReportingFacility);
		return Helper.findResponseStatus("TPCBoard Depot Assoc updated successfully", Constants.SUCCESS_CODE);

	}
	
	@RequestMapping(value = "/deleteTPCBoardDepotAssoc/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteTPCBoardDepotAssocById(@PathVariable Long id) {
		tpcBoardDepotAssocService.deleteTPCBoardDepotAssocById(id);
		return Helper.findResponseStatus("TPCBoard Depot Assoc Deleted successfully", Constants.SUCCESS_CODE);
	}
}