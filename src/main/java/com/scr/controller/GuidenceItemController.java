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
import com.scr.model.GuidenceItem;
import com.scr.services.GuidenceItemService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class GuidenceItemController {
	
	@Autowired
	private GuidenceItemService guidenceItemService;
	
	
	@RequestMapping(value = "/findAllGuidenceItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<GuidenceItem> findAllGuidenceItems(){
		List<GuidenceItem> guidenceItem = guidenceItemService.findAll();
		return guidenceItem;
	}
	
	@RequestMapping(value = "/addGuidenceItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addGuidenceItem(@RequestBody GuidenceItem guidenceItem) {
		guidenceItemService.save(guidenceItem);
		return Helper.findResponseStatus("Guidence Item added successfully", Constants.SUCCESS_CODE);
	}
	
	
	@RequestMapping(value = "/findGuidenceItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<GuidenceItem> findGuidenceItemById(@PathVariable Integer id){
		Optional<GuidenceItem> guidenceItem = guidenceItemService.findGuidenceItemById(id);
		return new ResponseEntity<GuidenceItem>(guidenceItem.get(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateGuidenceItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateGuidenceItem(@RequestBody GuidenceItem guidenceItem) {
		guidenceItemService.save(guidenceItem);
		return Helper.findResponseStatus("Guidence Item updated successfully", Constants.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/deleteGuidenceItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteGuidenceItemById(@PathVariable Integer id) {
		guidenceItemService.deleteGuidenceItemById(id);
		return Helper.findResponseStatus("Guidence Item updated successfully", Constants.SUCCESS_CODE);
	}

}
