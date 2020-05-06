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
import com.scr.model.TpcBoard;
import com.scr.services.TPCBoardService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TPCBoardController {
	@Autowired
	private TPCBoardService tpcBoardService;
	
	
	@RequestMapping(value = "/findAllTPCBoard" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<TpcBoard> findAllTPCBoard(){
		List<TpcBoard> tpcBoard = tpcBoardService.findAll();
		return tpcBoard;
	}
	
	@RequestMapping(value = "/addTPCBoard" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addTPCBoard(@RequestBody TpcBoard tpcBoard) {
		tpcBoardService.save(tpcBoard);
		return Helper.findResponseStatus("TPC Board added successfully", Constants.SUCCESS_CODE);

	}
	
	
	@RequestMapping(value = "/findTPCBoardById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<TpcBoard> findTPCBoardById(@PathVariable Long id){
		Optional<TpcBoard> tpcBoard = tpcBoardService.findTPCBoardById(id);
		return new ResponseEntity<TpcBoard>(tpcBoard.get(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/updateTPCBoard" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTPCBoard(@RequestBody TpcBoard tpcBoard) {
		tpcBoardService.save(tpcBoard);
		return Helper.findResponseStatus("TPCBoard updated successfully", Constants.SUCCESS_CODE);

	}
	
	@RequestMapping(value = "/deleteTPCBoard/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteTPCBoardById(@PathVariable Long id) {
		tpcBoardService.deleteTPCBoardById(id);
		return Helper.findResponseStatus("TPCBoard Deleted successfully", Constants.SUCCESS_CODE);
	}
}