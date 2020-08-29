package com.scr.controller;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Make;
import com.scr.model.Model;
import com.scr.services.ModelService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class ModelController {
	
	private static Logger logger = Logger.getLogger(ModelController.class);
	
	@Autowired 
	ModelService modelService;
	
	@RequestMapping(value = "/addModel" , method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseStatus addModel(@RequestBody Model model) {
		
		logger.info("Enter into save function with below request parameters ");
		
		logger.info("Request Parameters = "+model.toString());
		
		model.setModelCode(model.getModelCode().toUpperCase());
		model.setModelName(model.getModelCode().toUpperCase());
		
		try {
			logger.info("Calling service with request parameters.");
		modelService.save(model);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("Model added successfully", Constants.SUCCESS_CODE);
		}
		
		catch(NullPointerException npe) {
			logger.error("ERROR >> While adding model data. "+npe.getMessage());
			return Helper.findResponseStatus("model save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding model data. "+e.getMessage());
			return Helper.findResponseStatus("model save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updateModel" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateMake(@RequestBody Model model) {
		logger.info("Enter into update function with below request parameters ");
		logger.info("Request Parameters = "+model.toString());
		try {
			logger.info("Calling service with request parameters.");
		model.setModelCode(model.getModelCode().toUpperCase());
		//make.setMakeName(make.getMakeName().toUpperCase());
		modelService.save(model);
		logger.info("Preparing the return response");
		return Helper.findResponseStatus("Model updated successfully", Constants.SUCCESS_CODE);
	}catch(NullPointerException npe) {
		logger.error("ERROR >> While updating model data. "+npe.getMessage());
		return Helper.findResponseStatus("model update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
	}
	catch (Exception e) {
		logger.error("ERROR >> While updating model data. "+e.getMessage());
		return Helper.findResponseStatus("model update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
	}
		}
	

	@RequestMapping(value = "/deleteModel/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteModelById(@PathVariable Long id) {
		logger.info("Enter into deleteById function");
		logger.info("Selected model Id = "+id);
		try {
		modelService.deleteModelById(id);
		return Helper.findResponseStatus("Model deleted successfully", Constants.SUCCESS_CODE);
	} catch (NullPointerException npe) {
		logger.error("ERROR >> While deleting model data"+npe.getMessage());
		return Helper.findResponseStatus("model Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
	} catch (Exception e) {
		logger.error("ERROR >> While deleting model data"+e.getMessage());
		return Helper.findResponseStatus("model Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
	}
}
	 @RequestMapping(value = "/findAllModel" , method = RequestMethod.GET , headers = "Accept=application/json")
	public List<Model> findAllModel() throws JSONException {
		 List<Model> model = null;
		 try {
			   logger.info("Calling service for model data");	
		
		 model = modelService.findAll();
		 logger.info("Fetched model data***"+model.size());
		return model;
	}catch (NullPointerException npe) {
		logger.error("ERROR >>> while fetching the model data = "+npe.getMessage());
	}
	catch (Exception e) {
		logger.error("ERROR >>> while fetching the model data = "+e.getMessage());
	}
		 logger.info("Exit from model function");
	return model;	
}
	
	@RequestMapping(value = "/findModelById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Model> findModelById(@PathVariable("id") Long id){

		Optional<Model> model = null;
		try {
			logger.info("Selected model Id = "+id);
			
			model = modelService.findModelById(id);
			if(model.isPresent()) {
				logger.info("model Data = "+model.get());
				return new ResponseEntity<Model>(model.get(), HttpStatus.OK);
				
			}
			else
				return new ResponseEntity<Model>(model.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find model Details by id, "+e.getMessage());
			return new ResponseEntity<Model>(model.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	@RequestMapping(value = "/existsModelCode/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsModelCode(@PathVariable("modelCode") String modelCode){		
		try {
			return modelService.existsByModelCode(modelCode.toUpperCase());
		} catch (Exception e) {
			logger.error("Error while checking exists model code.");
			return false;
		}
	}
	@RequestMapping(value = "/existsModelCodeAndId/{id}/{modelCode}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsModelCodeAndId(@PathVariable("id") Long id,@PathVariable("modelCode") String modelCode){
		
		logger.info("id=="+id+"modelCode=="+modelCode);
		Boolean result;
		try {
			Optional<Model> modelData = modelService.findByModelCode(modelCode);
			
			if(modelData.isPresent()) {
				Model model = modelData.get();
				logger.info("***id ***"+model.getId());
				if (id.equals(model.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and modelcode..."+e.getMessage());
			return false;
		}
	}
}