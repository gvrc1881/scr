package com.scr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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

import com.scr.jobs.CommonUtility;
import com.scr.message.request.AssetMasterDataSearchRequest;
import com.scr.message.response.ResponseStatus;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetMasterDataFormParameter;
import com.scr.model.Facility;
import com.scr.services.AssetMasterDataService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class AssetMasterDataController {
	static Logger log = LogManager.getLogger(AssetMasterDataController.class);

	@Autowired
	private AssetMasterDataService assetMasterDataService;
	
	@Autowired
	private CommonUtility  commonUtility;

	@RequestMapping(value = "/findAllAssetMasterItems/{from}/{to}/{loggedUserData}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<AssetMasterData> findAllAssetMasterItems(@PathVariable("from") int from, @PathVariable("to") int to,
			@PathVariable("loggedUserData") String loggedUserData) {
		log.info("Enter into findAllAssetMasterItems function");
		List<AssetMasterData> assetMasterItem = null;
		List<String> fac= new ArrayList<>();
		try {
			log.info("Calling service for  assetMasterItem data");
			log.info("Calling service for assetStatus data");
			List<Facility> facility = commonUtility.findUserHierarchy(loggedUserData);
			log.info("facilities=="+facility.size());
			for (Facility facility2 : facility) {
				
				fac.add(facility2.getFacilityId());
				
			}
			assetMasterItem = assetMasterDataService.findPaginated(from, to,fac);
			log.info("Fetched assetMasterItem data ***" + assetMasterItem.size());
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the assetMaster data = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the assetMaster data = " + e.getMessage());
		}
		log.info("Exit from findAllAssetMasterItems function");
		return assetMasterItem;
	}

	@RequestMapping(value = "/findAllAssetMasterDataItems", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<AssetMasterData> findAllAssetMasterDataItems() throws JSONException {
		List<AssetMasterData> assetMasterList = null;
		try {
			log.info("Calling service for assetMasterList data");

			assetMasterList = assetMasterDataService.findAll();
			log.info("Fetched AssetMaster data***" + assetMasterList.size());
			return assetMasterList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the  AssetMaster data = " + npe.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the AssetMaster data = " + e.getMessage());
		}
		log.info("Exit from AssetMaster function");
		return assetMasterList;
	}

	@RequestMapping(value = "/addAssetMasterItem", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseStatus saveAssetMasterItem(@Valid @RequestBody AssetMasterData assetMasterData)
			throws JSONException {
		log.info("Enter into saveAssetMasterItem function with below request parameters ");
		log.info("Request Parameters = " + assetMasterData.toString());
		try {
			log.info("Calling service with request parameters.");
			assetMasterDataService.save(assetMasterData);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("AssetMaster Data Added Successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While adding drive data. " + npe.getMessage());
			return Helper.findResponseStatus("AssetMaster Data Addition is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While adding AssetMaster  data. " + e.getMessage());
			return Helper.findResponseStatus("AssetMaster Data Addition is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/findAssetMasterItemById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<AssetMasterData> findAssetMasterItemById(@PathVariable Long id) {
		Optional<AssetMasterData> assetMasterData = null;
		try {
			log.info("Selected Asset Master Data Id = " + id);
			assetMasterData = assetMasterDataService.findAssetMasterItemById(id);
			if (assetMasterData.isPresent()) {
				log.info("Asset Master Data = " + assetMasterData.get());
				return new ResponseEntity<AssetMasterData>(assetMasterData.get(), HttpStatus.OK);
			} else
				return new ResponseEntity<AssetMasterData>(assetMasterData.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Asset Master Data Details by id, " + e.getMessage());
			return new ResponseEntity<AssetMasterData>(assetMasterData.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateAssetMasterData", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseStatus updateAssetMasterData(@RequestBody AssetMasterData assetMasterData) {
		log.info("Enter into updateAssetMasterData function with below request parameters ");
		log.info("Request Parameters = " + assetMasterData.toString());
		try {
			log.info("Calling service with request parameters.");
			assetMasterDataService.save(assetMasterData);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("assetMasterData Updated successful", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While updating assetMaster data. " + npe.getMessage());
			return Helper.findResponseStatus("assetMasterData update is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While updating Sector data. " + e.getMessage());
			return Helper.findResponseStatus("Sector update is Failed with " + e.getMessage(), Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/deleteAssetMasterData/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseStatus deleteAssetMasterDataById(@PathVariable Long id) {
		log.info("Enter into deleteAssetMasterDataById function");
		log.info("Selected  AssetMaster Data Id = " + id);
		try {
			assetMasterDataService.deleteAssetMasterDataById(id);
			return Helper.findResponseStatus("AssetMasterData Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Asset Master Data" + npe.getMessage());
			return Helper.findResponseStatus("Asset Master Data Deletion is Failed with " + npe.getMessage(),
					Constants.FAILURE_CODE);
		} catch (Exception e) {
			log.error("ERROR >> While deleting Asset Master Data" + e.getMessage());
			return Helper.findResponseStatus("Asset Master Data Deletion is Failed with " + e.getMessage(),
					Constants.FAILURE_CODE);
		}
	}

	@RequestMapping(value = "/getAssetIdBasedonAssetTypeAndFacilityId/{assetType}/{facilityId}", method = RequestMethod.GET, headers = "accept=application/json")
	public ResponseEntity<List<AssetMasterData>> findAssetIdAndFacilityId(@PathVariable("assetType") String assetType,
			@PathVariable("facilityId") String facilityId) {
		List<AssetMasterData> assetIdsList = assetMasterDataService.findByAssetTypeAndFacilityId(assetType, facilityId);
		return new ResponseEntity<List<AssetMasterData>>(assetIdsList, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAssetParameterNamesBasedOnAssetTypes/{assetType}/{active}", method = RequestMethod.GET, headers = "accept=application/json")
	public ResponseEntity<List<AssetMasterDataFormParameter>> findAllAssetTypes(
			@PathVariable("assetType") String assetType, @PathVariable("active") String active) {
		List<AssetMasterDataFormParameter> assetTypeList = assetMasterDataService.findByAssetTypeAndActive(assetType,
				active);
		log.info("assetTypeList" + assetTypeList);
		log.info("assetTypeListSize" + assetTypeList.size());

		return new ResponseEntity<List<AssetMasterDataFormParameter>>(assetTypeList, HttpStatus.OK);
	}

	@RequestMapping(value = "/assetIdsByAssetTypeAndFacilityId/{assetType}/{facilityId}/{fromKm}/{toKm}", method = RequestMethod.GET, headers = "accept=application/json")
	public ResponseEntity<List<AssetMasterData>> findAssetIds(@PathVariable("assetType") String assetType,
			@PathVariable("facilityId") String facilityId, @PathVariable("fromKm") String fromKm,
			@PathVariable("toKm") String toKm) {

		List<AssetMasterData> assetIdsList = assetMasterDataService.findByAssetTypeAndFacilityIdAndKM(assetType,
				facilityId, Double.valueOf(fromKm), Double.valueOf(toKm));
		return new ResponseEntity<List<AssetMasterData>>(assetIdsList, HttpStatus.OK);
	}

	@RequestMapping(value = "/assetIdsByFacilityId/{facilityId}/{fromKm}/{toKm}", method = RequestMethod.GET, headers = "accept=application/json")
	public ResponseEntity<List<AssetMasterData>> findAssetIdsByfacilityId(@PathVariable("facilityId") String facilityId,
			@PathVariable("fromKm") String fromKm, @PathVariable("toKm") String toKm) {

		List<AssetMasterData> assetIdsList = assetMasterDataService.findAssetIdsByFacilityId(facilityId,
				Double.valueOf(fromKm), Double.valueOf(toKm));
		return new ResponseEntity<List<AssetMasterData>>(assetIdsList, HttpStatus.OK);
	}

	@RequestMapping(value = "/findMakeModel/{assetId}/{assetType}/{facilityId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<AssetMasterData>> findMakeModel(@PathVariable("assetId") String assetId,
			@PathVariable("assetType") String assetType, @PathVariable("facilityId") String facilityId)
			throws JSONException {
		log.info("Enter into findAshWithFacilityName function");
		System.out.println("assetId::" + assetId + "assetType::" + assetType + "facilityId::" + facilityId);
		List<AssetMasterData> assetData = null;
		try {
			log.info("Calling service for ASH data");
			if (null != assetId && assetId.contains("@"))
				assetId = assetId.replace('@', '/');
			assetData = assetMasterDataService.findMakeModel(assetId, assetType, facilityId);
			log.info("Fetched assetData  = " + assetData);
		} catch (NullPointerException e) {
			log.error("ERROR >>> while fetching the ash data = " + e.getMessage());
		} catch (Exception e) {
			log.error("ERROR >>> while fetching the ash data = " + e.getMessage());
		}
		log.info("Exit from findAshWithFacilityName function");
		return ResponseEntity.ok((assetData));
	}

	@RequestMapping(value = "/findByFacilityAssetTypeId/{facilityId}/{assetType}/{assetId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean findByFacilityAssetTypeId(@PathVariable("facilityId") String facilityId,
			@PathVariable("assetType") String assetType, @PathVariable("assetId") String assetId) {
		log.info("Exist=====" + facilityId + assetType + assetId);
		try {
			log.info("Request for checking exists facilityId  and assetType and assetId...");
			return assetMasterDataService.existsByFacilityIdAndAssetTypeAndAssetId(facilityId, assetType, assetId);
		} catch (Exception e) {
			log.error("Error while checking exists facilityId and assetType and assetId..." + e.getMessage());
			return false;
		}
	}

	@RequestMapping(value = "/findByFacilityIdAssetTypeAndId/{id}/{facilityId}/{assetType}/{assetId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean findByFacilityIdAssetTypeAndId(@PathVariable("id") Long id,
			@PathVariable("facilityId") String facilityId, @PathVariable("assetType") String assetType,
			@PathVariable("assetId") String assetId) {

		log.info("id==" + id + "facilityId==" + facilityId + "assetType==" + assetType + "assetId" + assetId);
		Boolean result;
		try {
			Optional<AssetMasterData> amdData = assetMasterDataService
					.findByFacilityIdAndAssetTypeAndAssetId(facilityId, assetType, assetId);

			if (amdData.isPresent()) {
				AssetMasterData assetMasterData = amdData.get();
				log.info("***id ***" + assetMasterData.getId());
				if (id.equals(assetMasterData.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			} else
				return result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and assetType and assetId..." + e.getMessage());
			return false;
		}
	}
	
	@RequestMapping(value = "/assetMasterDataSearch", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<AssetMasterData> assetMasterDataSCO(@RequestBody AssetMasterDataSearchRequest assetMasterDataSearchRequest) {
		log.info("Enter into assetMasterDataSearch function with below request parameters ");
		log.info("Request Parameters = " + assetMasterDataSearchRequest.toString());
		List<AssetMasterData> results = null;
		try {
			log.info("Calling service with request parameters.");
			results = assetMasterDataService.findSearch(assetMasterDataSearchRequest);
			log.info("Preparing the return response");
			//return Helper.findResponseStatus("find assetMasterDataSearch results successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While finding assetMasterDataSearch data. " + npe.getMessage());
			
		} catch (Exception e) {
			log.error("ERROR >> While finding assetMasterDataSearch data. " + e.getMessage());			
		}
		return results;
	}
}
