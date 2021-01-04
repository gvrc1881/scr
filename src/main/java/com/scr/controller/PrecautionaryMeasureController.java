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
import com.scr.model.PrecautionaryMeasure;
import com.scr.model.PrecautionaryMeasuresMaster;
import com.scr.services.PrecautionaryMeasureService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class PrecautionaryMeasureController {
	
	static Logger logger = LogManager.getLogger(PrecautionaryMeasureController.class);
	
	
	@Autowired
	private PrecautionaryMeasureService precautionaryMeasureService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllPrecautionaryMeasures", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<PrecautionaryMeasure> findAllPrecautionaryMeasures() throws JSONException {
		logger.info("Enter into findAll PrecautionaryMeasure function");
		List<PrecautionaryMeasure> precautionaryMeasureList = null;
		try {
			logger.info("Calling service for PrecautionaryMeasure data");
			precautionaryMeasureList = precautionaryMeasureService.findAll();
			logger.info("Fetched precautionaryMeasure data = " + precautionaryMeasureList.size());
			return precautionaryMeasureList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the PrecautionaryMeasure data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the PrecautionaryMeasure data = " + e.getMessage());
		}
		logger.info("Exit from findAll PrecautionaryMeasure function");
		return precautionaryMeasureList;
	}
	@RequestMapping(value = "/findPreMeasMasId/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<PrecautionaryMeasuresMaster> findById(@PathVariable("id") Integer id){
		Optional<PrecautionaryMeasuresMaster> precautionaryMeasuresMaster = null;
		try {
			logger.info("Selected Pre Measu Master Id = "+id);
			precautionaryMeasuresMaster = precautionaryMeasureService.findById(id);
			if(precautionaryMeasuresMaster.isPresent()) {
				logger.info("Pre Measu Master Data = "+precautionaryMeasuresMaster.get());
				return new ResponseEntity<PrecautionaryMeasuresMaster>(precautionaryMeasuresMaster.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<PrecautionaryMeasuresMaster>(precautionaryMeasuresMaster.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find Pre Measu Master Details by id, "+e.getMessage());
			return new ResponseEntity<PrecautionaryMeasuresMaster>(precautionaryMeasuresMaster.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value="/addPrecautionaryMeasures",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addPrecautionaryMeasures(@RequestBody PrecautionaryMeasure precautionaryMeasure) {
		logger.info("Enter into addPrecautionaryMeasures function with below request parameters ");
		logger.info("Request Parameters = "+precautionaryMeasure.toString());
		try {
			logger.info("Calling service with request parameters.");
			precautionaryMeasureService.save(precautionaryMeasure);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("precautionaryMeasure Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding precautionaryMeasure data. "+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding precautionaryMeasure data. "+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findPrecautionaryMeasureById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<PrecautionaryMeasure> findPrecautionaryMeasureById(@PathVariable Long id){
		Optional<PrecautionaryMeasure> precautionaryMeasure = null;
		try {
			logger.info("Selected precautionaryMeasure Id = "+id);
			precautionaryMeasure = precautionaryMeasureService.findPrecautionaryMeasureById(id);
			if(precautionaryMeasure.isPresent()) {
				logger.info("PrecautionaryMeasure Data = "+precautionaryMeasure.get());
				return new ResponseEntity<PrecautionaryMeasure>(precautionaryMeasure.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<PrecautionaryMeasure>(precautionaryMeasure.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find PrecautionaryMeasure Details by id, "+e.getMessage());
			return new ResponseEntity<PrecautionaryMeasure>(precautionaryMeasure.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updatePrecautionaryMeasure" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updatePrecautionaryMeasure(@RequestBody PrecautionaryMeasure precautionaryMeasure) {
		logger.info("Enter into updatePrecautionaryMeasure function with below request parameters ");
		logger.info("Request Parameters = "+precautionaryMeasure.toString());
		try {
			logger.info("Calling service with request parameters.");
			precautionaryMeasureService.save(precautionaryMeasure);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("precautionaryMeasure Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating precautionaryMeasure data. "+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating precautionaryMeasure data. "+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deletePrecautionaryMeasure/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deletePrecautionaryMeasureById(@PathVariable Long id) {
		logger.info("Enter into deletePrecautionaryMeasureById function");
		logger.info("Selected precautionaryMeasure Id = "+id);
		try {
			precautionaryMeasureService.deletePrecautionaryMeasureById(id);
			return Helper.findResponseStatus("precautionaryMeasure deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting precautionaryMeasure data"+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting precautionaryMeasure data"+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	@RequestMapping(value = "/getprecautionaryMeasureBasedOnActive/{active}", method = RequestMethod.GET, headers = "accept=application/json")
	public ResponseEntity<PrecautionaryMeasuresMaster> getprecautionaryMeasureBasedOnActive(@PathVariable("active") String active) {
		logger.info("** Enter into getprecautionaryMeasureBasedOnActive function ***");
		Optional<PrecautionaryMeasuresMaster> precautionaryMeasuresMaster = null;
		try {
			logger.info("** active = " + active);
			precautionaryMeasuresMaster = precautionaryMeasureService.findByActive(active);
			if (precautionaryMeasuresMaster.isPresent()) {
				return new ResponseEntity<PrecautionaryMeasuresMaster>(precautionaryMeasuresMaster.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<PrecautionaryMeasuresMaster>(precautionaryMeasuresMaster.get(), HttpStatus.CONFLICT);

		} catch (Exception e) {
			logger.error("Error >>  while find precautionaryMeasure Details by active, " + e.getMessage());
			return new ResponseEntity<PrecautionaryMeasuresMaster>(precautionaryMeasuresMaster.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@RequestMapping(value = "/findByFacilityIdPrecautionaryMeasureAndCreatedDate/{facilityId}/{precautionaryMeasure}/{dateOfWork}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFacilityIdAndPrecautionaryMeasureAndDateOfWork(@PathVariable("facilityId") String facilityId ,@PathVariable("precautionaryMeasure") Integer precautionaryMeasure,@PathVariable("dateOfWork") String dateOfWork){
		
		try {
			logger.info("Request for checking exists facilityId,Precautionary Measure and createdDate...");
			return precautionaryMeasureService.existsByFacilityIdAndPrecautionaryMeasureAndDateOfWork(facilityId,precautionaryMeasureService.findById(precautionaryMeasure).get(),Helper.convertStringToTimestamp(dateOfWork));
		} catch (Exception e) {
			logger.error("Error while checking exists facilityId "+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existFacilityIdPrecautionaryMeasureAndDateOfWorkAndId/{id}/{facilityId}/{precautionaryMeasure}/{dateOfWork}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsFacilityIdCreatedDateAndId(@PathVariable("id") Long id,@PathVariable("facilityId") String facilityId,@PathVariable("precautionaryMeasure") Integer precautionaryMeasure,@PathVariable("dateOfWork") String dateOfWork){
		
		logger.info("id=="+id+"precautionaryMeasure=="+precautionaryMeasure);
		Boolean result;
		try {
			Optional<PrecautionaryMeasure> precautionaryMeasureData = precautionaryMeasureService.findByFacilityIdAndPrecautionaryMeasureAndDateOfWork (facilityId,precautionaryMeasureService.findById(precautionaryMeasure).get(),Helper.convertStringToTimestamp(dateOfWork));
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(precautionaryMeasureData.isPresent()) {
				PrecautionaryMeasure preMeasure = precautionaryMeasureData.get();
				logger.info("***id ***"+preMeasure.getId());
				if (id.equals(preMeasure.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and facilityId..."+e.getMessage());
			return false;
		}
	}
	
	//Precautionary Measure Master
	
	@RequestMapping(value = "/findAllPrecautionaryMeasureMaster", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<PrecautionaryMeasuresMaster> findAllPrecautionaryMeasureMaster() throws JSONException {
		logger.info("Enter into findAll PrecautionaryMeasure Master function");
		List<PrecautionaryMeasuresMaster> precautionaryMeasMasteList = null;
		try {
			logger.info("Calling service for PrecautionaryMeasure data");
			precautionaryMeasMasteList = precautionaryMeasureService.findAllPreMeaMaster();
			logger.info("Fetched precautionaryMeasure data = " + precautionaryMeasMasteList.size());
			return precautionaryMeasMasteList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the PrecautionaryMeasure Master data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the PrecautionaryMeasure Master data = " + e.getMessage());
		}
		logger.info("Exit from findAll PrecautionaryMeasure Master function");
		return precautionaryMeasMasteList;
	}
	
	@RequestMapping(value="/addPrecautionaryMeasureMaster",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addPrecautionaryMeasureMaster(@RequestBody PrecautionaryMeasuresMaster precautionaryMeasureMaster) {
		logger.info("Enter into addPrecautionaryMeasures function with below request parameters ");
		logger.info("Request Parameters = "+precautionaryMeasureMaster.toString());
		try {
			logger.info("Calling service with request parameters.");
			precautionaryMeasureService.save(precautionaryMeasureMaster);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("precautionaryMeasure Master Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding precautionaryMeasure Master data. "+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Master save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding precautionaryMeasure Master data. "+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Master save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/updatePrecautionaryMeasureMaster" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updatePrecautionaryMeasureMaster(@RequestBody PrecautionaryMeasuresMaster precautionaryMeasureMaster) {
		logger.info("Enter into updatePrecautionaryMeasure function with below request parameters ");
		logger.info("Request Parameters = "+precautionaryMeasureMaster.toString());
		try {
			logger.info("Calling service with request parameters.");
			precautionaryMeasureService.save(precautionaryMeasureMaster);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("precautionaryMeasure Master Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating precautionaryMeasure Master data. "+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Master update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating precautionaryMeasure Master data. "+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Master update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deletePrecautionaryMeasureMaster/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deletePrecautionaryMeasureMasterById(@PathVariable Integer id) {
		logger.info("Enter into deletePrecautionaryMeasure MasterById function");
		logger.info("Selected precautionaryMeasure Master Id = "+id);
		try {
			precautionaryMeasureService.deletePrecautionaryMeasureMasterById(id);
			return Helper.findResponseStatus("precautionaryMeasure Master deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting precautionaryMeasure Master data"+npe.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Master Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting precautionaryMeasure Master data"+e.getMessage());
			return Helper.findResponseStatus("precautionaryMeasure Master Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existPrecautionaryMeasure/{precautionaryMeasure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existPrecautionaryMeasure(@PathVariable("precautionaryMeasure") String precautionaryMeasure ){
			
		try {
            logger.info("Request for checking exists precautionary Measure...");
			return precautionaryMeasureService.existsByPrecautionaryMeasure(precautionaryMeasure);
		} catch (Exception e) {
			logger.error("Error while checking exists precautionary Measure..."+e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/existPrecautionaryMeasureById/{id}/{precautionaryMeasure}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existPrecautionaryMeasureById(@PathVariable("id") Integer id,@PathVariable("precautionaryMeasure") String precautionaryMeasure){
		
		logger.info("id=="+id+"precautionaryMeasure=="+precautionaryMeasure);
		Boolean result;
		try {
			Optional<PrecautionaryMeasuresMaster> precautionaryMeasureData = precautionaryMeasureService.findByPrecautionaryMeasure(precautionaryMeasure);	
			if(precautionaryMeasureData.isPresent()) {
				PrecautionaryMeasuresMaster precautionaryMeasuresMaster = precautionaryMeasureData.get();
				logger.info("***id ***"+precautionaryMeasuresMaster.getId());
				if (id.equals(precautionaryMeasuresMaster.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			logger.error("Error while checking exists id and precautionary Measures Master..."+e.getMessage());
			return false;
		}
	}
	
	
}
