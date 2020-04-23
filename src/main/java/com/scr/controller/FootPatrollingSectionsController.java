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
import com.scr.model.FootPatrollingSection;
import com.scr.services.FootPatrollingSectionsService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class FootPatrollingSectionsController {
	@Autowired
	private FootPatrollingSectionsService footPatrollingSectionsService;
	
	
	@RequestMapping(value = "/findAllFPSectionsItems" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<FootPatrollingSection> findAllFPSectionsItems(){
		List<FootPatrollingSection> fpSectionsItem = footPatrollingSectionsService.findAll();
		return fpSectionsItem;
	}
	
	@RequestMapping(value = "/addFPSectionsItem" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addFPSectionsItem(@RequestBody FootPatrollingSection footPatrollingSection) {
		footPatrollingSectionsService.save(footPatrollingSection);
		return Helper.findResponseStatus("FP Sections Item added successfully", Constants.SUCCESS_CODE);

	}
	
	
	@RequestMapping(value = "/findFPSectionsItemById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<FootPatrollingSection> findFPSectionsItemById(@PathVariable Long id){
		Optional<FootPatrollingSection> footPatrollingSections = footPatrollingSectionsService.findFPSectionsItemById(id);
		return new ResponseEntity<FootPatrollingSection>(footPatrollingSections.get(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/updateFPSectionsItem" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateFPSectionsItem(@RequestBody FootPatrollingSection footPatrollingSection) {
		footPatrollingSectionsService.save(footPatrollingSection);
		return Helper.findResponseStatus("FP Sections Item updated successfully", Constants.SUCCESS_CODE);

	}
	
	@RequestMapping(value = "/deleteFPSectionsItem/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteFPSectionsItemById(@PathVariable Long id) {
		footPatrollingSectionsService.deleteFPSectionsItemById(id);
		return Helper.findResponseStatus("FP Sections Item Deleted successfully", Constants.SUCCESS_CODE);
	}

}
