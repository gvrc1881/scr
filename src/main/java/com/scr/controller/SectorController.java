package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
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
import com.scr.model.Line;
import com.scr.model.Sector;
import com.scr.services.SectorService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class SectorController {

	static Logger logger = LogManager.getLogger(SectorController.class);

	@Autowired
	private SectorService sectorService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllSector", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Sector> findAllSector() throws JSONException {
		logger.info("Enter into findAll Sector function");
		List<Sector> sectorList = null;
		try {
			logger.info("Calling service for sector data");
			sectorList = sectorService.findAll();
			logger.info("Fetched sector data = " + sectorList.size());
			return sectorList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the sector data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the sector data = " + e.getMessage());
		}
		logger.info("Exit from findAll Sector function");
		return sectorList;
	}
	@RequestMapping(value = "/findAllLineCodes", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Line> findAllLineCodes() throws JSONException {
		logger.info("Enter into findAll Line function");
		List<Line> lineList = null;
		try {
			logger.info("Calling service for Line data");
			lineList = sectorService.findAllOrderByLineCodeAsc();
			logger.info("Fetched sector data = " + lineList.size());
			return lineList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the lineList data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the lineList data = " + e.getMessage());
		}
		logger.info("Exit from findAll lineList function");
		return lineList;
	}
	@RequestMapping(value="/addSector",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addSector(@RequestBody Sector sector) {
		logger.info("Enter into addSector function with below request parameters ");
		logger.info("Request Parameters = "+sector.toString());
		try {
			logger.info("Calling service with request parameters.");
			sectorService.save(sector);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Sector Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding Sector data. "+npe.getMessage());
			return Helper.findResponseStatus("Sector save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding Sector data. "+e.getMessage());
			return Helper.findResponseStatus("Sector save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findSectorById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Sector> findSectorById(@PathVariable Long id){
		Optional<Sector> sector = null;
		try {
			logger.info("Selected Sector Id = "+id);
			sector = sectorService.findSectorById(id);
			if(sector.isPresent()) {
				logger.info("sector Data = "+sector.get());
				return new ResponseEntity<Sector>(sector.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<Sector>(sector.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find Sector Details by id, "+e.getMessage());
			return new ResponseEntity<Sector>(sector.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateSector" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateSector(@RequestBody Sector sector) {
		logger.info("Enter into updateSector function with below request parameters ");
		logger.info("Request Parameters = "+sector.toString());
		try {
			logger.info("Calling service with request parameters.");
			sectorService.save(sector);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("Sector Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating Sector data. "+npe.getMessage());
			return Helper.findResponseStatus("Sector update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating Sector data. "+e.getMessage());
			return Helper.findResponseStatus("Sector update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteSector/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteSectorById(@PathVariable Long id) {
		logger.info("Enter into deleteSectorById function");
		logger.info("Selected Sector Id = "+id);
		try {
			sectorService.deleteSectorById(id);
			return Helper.findResponseStatus("Sector deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting Sector data"+npe.getMessage());
			return Helper.findResponseStatus("Sector Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting Sector data"+e.getMessage());
			return Helper.findResponseStatus("Sector Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
}
