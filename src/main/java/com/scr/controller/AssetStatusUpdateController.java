package com.scr.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.AssetStatusUpdateResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetStatusUpdate;
import com.scr.model.DriveCategory;
import com.scr.model.Drives;
import com.scr.model.Facility;
import com.scr.model.TssFeederMaster;
import com.scr.model.WorkPhases;
import com.scr.repository.FacilityRepository;
import com.scr.services.AssetMasterDataService;
import com.scr.services.AssetStatusUpdateService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")

public class AssetStatusUpdateController {

	static Logger logger = LogManager.getLogger(AssetStatusUpdateController.class);

	@Autowired
	private AssetStatusUpdateService assetStatusService;

	@Autowired
	private AssetMasterDataService assetMasterDataService;

	@Autowired
	private FacilityRepository facilityRepository;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllAssetStatus", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<AssetStatusUpdate> findAllAssetStatus() throws JSONException {
		logger.info("Enter into findAll AssetStatus function");
		List<AssetStatusUpdate> assetStatusList = null;
		try {
			logger.info("Calling service for assetStatus data");
			assetStatusList = assetStatusService.findAll();
			logger.info("Fetched assetStatus data = " + assetStatusList.size());
			return assetStatusList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the assetStatus data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the assetStatus data = " + e.getMessage());
		}
		logger.info("Exit from findAll AssetStatus function");
		return assetStatusList;
	}

	@RequestMapping(value = "/addAssetStatusChange", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus addAssetStatus(@RequestBody AssetStatusUpdate assetStatusUpdate) {
		logger.info("Enter into addAssetStatus function with below request parameters ");
		logger.info("Request Parameters = " + assetStatusUpdate.toString());
		try {
			logger.info("Calling service with request parameters.");
			AssetStatusUpdate asu = assetStatusService.save(assetStatusUpdate);	
			if(asu.getFacilityId() != null)
			{
				Optional<Facility> fac = facilityRepository.findByFacilityName(asu.getFacilityId());
				assetStatusUpdate.setFacilityId(fac.get().getFacilityId());
			}
			assetStatusUpdate.setSeqId(asu.getId().toString());			
			assetStatusService.save(assetStatusUpdate);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("AssetStatusUpdate Added successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While adding AssetStatus data. " + npe.getMessage());
			return Helper.findResponseStatus("AssetStatus save is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("ERROR >> While adding AssetStatus data. " + e.getMessage());
			return Helper.findResponseStatus("AssetStatus save is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}


	@RequestMapping(value = "/findAssetStatusById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<AssetStatusUpdate> findAssetStatusById(@PathVariable Long id) {
		Optional<AssetStatusUpdate> assetStatusUpdate = null;
		try {
			logger.info("Selected AssetStatus Id = " + id);
			assetStatusUpdate = assetStatusService.findAssetStatusById(id);
			if (assetStatusUpdate.isPresent()) {
				logger.info("AssetStatus Data = " + assetStatusUpdate.get());
				return new ResponseEntity<AssetStatusUpdate>(assetStatusUpdate.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<AssetStatusUpdate>(assetStatusUpdate.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find AssetStatus Details by id, " + e.getMessage());
			return new ResponseEntity<AssetStatusUpdate>(assetStatusUpdate.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateAssetStatus", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateAssetStatus(@RequestBody AssetStatusUpdate assetStatusUpdate) {
		logger.info("Enter into updateAssetStatus function with below request parameters ");
		logger.info("Request Parameters = " + assetStatusUpdate.toString());
		try {
			logger.info("Calling service with request parameters.");
			AssetStatusUpdate asu = assetStatusService.save(assetStatusUpdate);	
			if(asu.getFacilityId() != null)
			{
				Optional<Facility> fac = facilityRepository.findByFacilityName(asu.getFacilityId());
				assetStatusUpdate.setFacilityId(fac.get().getFacilityId());
			}			
			assetStatusService.save(assetStatusUpdate);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("AssetStatus Updated successful", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While updating AssetStatus data. " + npe.getMessage());
			return Helper.findResponseStatus("AssetStatus update is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("ERROR >> While updating AssetStatus data. " + e.getMessage());
			return Helper.findResponseStatus("AssetStatus update is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/deleteAssetStatus/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteAssetStatusById(@PathVariable Long id) {
		logger.info("Enter into deleteAssetStatusById function");
		logger.info("Selected AssetStatus Id = " + id);
		try {
			assetStatusService.deleteAssetStatusById(id);
			return Helper.findResponseStatus("AssetStatus deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting AssetStatus data" + npe.getMessage());
			return Helper.findResponseStatus("AssetStatus Deletion is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.error("ERROR >> While deleting AssetStatus data" + e.getMessage());
			return Helper.findResponseStatus("AssetStatus Deletion is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/getTowerCarBasedOnDivsion/{division}/{subDivision}/{facilityId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<AssetStatusUpdateResponse>> getTowerCarBasedOnDivsion(@PathVariable("division") String division,
			@PathVariable("subDivision") String subDivision, @PathVariable("facilityId") String facilityId) throws JSONException {
		logger.info("Enter into getTowerCarBasedOnDivsion function");
		logger.info("division===" + division);
		logger.info("subDivision===" + subDivision);
		logger.info("facilityId===" + facilityId);

		List<AssetStatusUpdateResponse> amd = new ArrayList<>();		
		try {

			if (division != null && subDivision.equals("null") && facilityId.equals("null")) {
				logger.info("in if condiction");
				// amd =
				// assetMasterDataService.getAssetTypeAndAssetIdAndDateOfManufactureBasedOnDataDiv(div);
				amd = assetMasterDataService.getByDataDiv(division);
			} else if (division != null && !subDivision.equals("null") && facilityId.equals("null")) {
				logger.info("** in else if condition");
				//List<AssetStatusUpdateResponse> asur = null;
				List<Facility> fac = facilityRepository.findByDivisionAndSubDivision(division, subDivision);
				
				for (Facility facility : fac) {
					logger.info("** in else if for loop "+facility.getFacilityId());
					List<AssetStatusUpdateResponse> asur = assetMasterDataService.getByFacilityId(facility.getFacilityId());
					for (AssetStatusUpdateResponse assetStatusUpdateResponse : asur) {
						amd.add(assetStatusUpdateResponse);
					}
				}

			} else if (division != null && !subDivision.equals("null")  && facilityId != null) {
				logger.info("*** in 2nd else if condtion ");
				amd = assetMasterDataService.getByFacilityId(facilityId);
			}
			//logger.info("*** size **8"+amd.size());

		} catch (NullPointerException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e);
		}
		logger.info("Exit from getTowerCarBasedOnDivsion function");
		return ResponseEntity.ok((amd));
	}
	
	@RequestMapping(value = "/ExistsByAssetTypeAndAssetIdAndFacilityId/{assetType}/{assetId}/{facilityId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean ExistsByAssetTypeAndAssetIdAndFacilityId(@PathVariable("assetType") String assetType ,@PathVariable("assetId") String assetId,@PathVariable("facilityId") String facilityId){
			logger.info("Exist====="+assetType+"assetId"+assetId+"facilityID=="+facilityId);
		try {
            logger.info("Request for checking exists assetType and assetId and facilityID...");
			return assetStatusService.existsByAssetTypeAndAssetIdAndFacilityId(assetType,assetId,facilityId);
		} catch (Exception e) {
			logger.error("Error while  checking exists assetType and assetId and facilityID...."+e.getMessage());
			return false;
		}
	}

}
