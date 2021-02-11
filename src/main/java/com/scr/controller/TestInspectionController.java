package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
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
import com.scr.model.TestInspection;
import com.scr.services.MakeService;
import com.scr.services.ModelService;
import com.scr.services.TestInspectionService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TestInspectionController {
	
	
	static Logger logger = LogManager.getLogger(TestInspectionController.class);
	
	@Autowired
	private TestInspectionService testInspectionService;
	
	@Autowired 
	private ModelService modelService;
	
	@Autowired 
	private MakeService makeService;
	
	@RequestMapping(value = "/findAllTestInspection", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TestInspection> findAllTestInspection() throws JSONException {
		logger.info("Enter into findAll TestInspection function");
		List<TestInspection> testInspectionList = null;
		try {
			logger.info("Calling service for TestInspection data");
			testInspectionList = testInspectionService.findAll();
			logger.info("Fetched TestInspection data = " + testInspectionList.size());
			return testInspectionList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the TestInspection data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the TestInspection data = " + e.getMessage());
		}
		logger.info("Exit from findAll TestInspection function");
		return testInspectionList;
	}
	
	@RequestMapping(value="/addTestInspection",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addTestInspection(@RequestBody TestInspection testInspection) {
		logger.info("Enter into addTestInspection function with below request parameters ");
		logger.info("Request Parameters = "+testInspection.toString());
		try {
			logger.info("Calling service with request parameters.");
			testInspectionService.save(testInspection);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("testInspection Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding testInspection data. "+npe.getMessage());
			return Helper.findResponseStatus("testInspection save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding testInspection data. "+e.getMessage());
			return Helper.findResponseStatus("testInspection save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findTestInspectionById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<TestInspection> findTestInspectionById(@PathVariable Long id){
		Optional<TestInspection> testInspection = null;
		try {
			logger.info("Selected testInspection Id = "+id);
			testInspection = testInspectionService.findTestInspectionById(id);
			if(testInspection.isPresent()) {
				logger.info("TestInspection Data = "+testInspection.get());
				return new ResponseEntity<TestInspection>(testInspection.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<TestInspection>(testInspection.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find TestInspection Details by id, "+e.getMessage());
			return new ResponseEntity<TestInspection>(testInspection.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateTestInspection" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTestInspection(@RequestBody TestInspection testInspection) {
		logger.info("Enter into updateTestInspection function with below request parameters ");
		logger.info("Request Parameters = "+testInspection.toString());
		try {
			logger.info("Calling service with request parameters.");
			testInspectionService.save(testInspection);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("testInspection Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating testInspection data. "+npe.getMessage());
			return Helper.findResponseStatus("testInspection update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating testInspection data. "+e.getMessage());
			return Helper.findResponseStatus("testInspection update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteTestInspection/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteTestInspectionById(@PathVariable Long id) {
		logger.info("Enter into deleteTestInspectionById function");
		logger.info("Selected testInspection Id = "+id);
		try {
			testInspectionService.deleteTestInspectionById(id);
			return Helper.findResponseStatus("testInspection deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting testInspection data"+npe.getMessage());
			return Helper.findResponseStatus("testInspection Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting testInspection data"+e.getMessage());
			return Helper.findResponseStatus("testInspection Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	@RequestMapping(value = "/existNameMakeCodeAndModelCodeAndId/{id}/{name}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existNameMakeCodeAndModelCodeAndId(@PathVariable("id") Long id,@PathVariable("name") String name,@PathVariable("makeCode") Long makeCode,@PathVariable("modelCode") Long modelCode){
		
		logger.info("id=="+id+"makeCode=="+makeCode);
		Boolean result;
		try {
			Optional<TestInspection> testInspectionData = testInspectionService.findByNameAndMakeCodeAndModelCode (name,makeService.findMakeById(makeCode).get(),modelService.findModelById(modelCode).get());
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(testInspectionData.isPresent()) {
				TestInspection testInpe = testInspectionData.get();
				logger.info("***id ***"+testInpe.getId());
				if (id.equals(testInpe.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and name..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/findByNameMakeCodeAndModelCode/{name}/{makeCode}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsNameMakeCodeAndModelCode(@PathVariable("name") String name ,@PathVariable("makeCode") Long makeCode,@PathVariable("modelCode") Long modelCode){
		
		try {
			logger.info("Request for checking exists makeCode  modelCode and description...");
			return testInspectionService.existsByNameAndMakeCodeAndModelCode(name,makeService.findMakeById(makeCode).get(),modelService.findModelById(modelCode).get());
		} catch (Exception e) {
			logger.error("Error while checking exists name "+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/findByMakeCodeModelCodeAndDescription/{makeCode}/{modelCode}/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsMakeCodeModelCodeAndDescription(@PathVariable("makeCode") Long makeCode ,@PathVariable("modelCode") Long modelCode,@PathVariable("description") String description){
		
		try {
			logger.info("Request for checking exists makeCode  modelCode and description...");
			return testInspectionService.existsByMakeCodeAndModelCodeAndDescription(makeService.findMakeById(makeCode).get(),modelService.findModelById(modelCode).get(),description);
		} catch (Exception e) {
			logger.error("Error while checking exists description "+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existMakeCodeModelCodeAndDescriptionAndId/{id}/{makeCode}/{modelCode}/{description}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existMakeCodeModelCodeAndDescriptionAndId(@PathVariable("id") Long id,@PathVariable("makeCode") Long makeCode,@PathVariable("modelCode") Long modelCode,@PathVariable("description") String description){
		
		logger.info("id=="+id+"makeCode=="+makeCode);
		Boolean result;
		try {
			Optional<TestInspection> testInspectionData = testInspectionService.findByMakeCodeAndModelCodeAndDescription (makeService.findMakeById(makeCode).get(),modelService.findModelById(modelCode).get(),description);
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(testInspectionData.isPresent()) {
				TestInspection testInpe = testInspectionData.get();
				logger.info("***id ***"+testInpe.getId());
				if (id.equals(testInpe.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and description..."+e.getMessage());
			return false;
		}
	}
}
