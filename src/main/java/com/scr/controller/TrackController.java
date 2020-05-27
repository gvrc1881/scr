package com.scr.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Track;
import com.scr.services.TrackService;
import com.scr.util.Constants;
import com.scr.util.Helper;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TrackController {
	
	private Logger log = Logger.getLogger(TrackController.class);
	
	@Autowired
	private TrackService trackService;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllTrack", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Track> trackList() throws JSONException {
		log.info("Enter into trackList function");
		List<Track> trackList = null;
		try {
			log.info("Calling service for track data");	
			trackList = trackService.findAll();
			log.info("Fetch track List Ended***"+trackList.size());
			return trackList;
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the track data = "+npe.getMessage());
		}catch (Exception e) {
			log.error("ERROR >>> while fetching the track data = "+e.getMessage());
		}
		log.info("Exit from trackList function");
		return trackList;	
	}
	
	@RequestMapping(value = "/addTrack", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveTrack(@RequestBody Track track) {
		log.info("Enter into saveTrack function with below request parameters ");
		log.info("Request Parameters = "+track.toString());
		try {
			log.info("Calling service with request parameters.");
			track.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			trackService.saveTrack(track);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Track added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding track data. "+npe.getMessage());
			return Helper.findResponseStatus("track save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding track data. "+e.getMessage());
			return Helper.findResponseStatus("track save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findTrack/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Track> findById(@PathVariable("id") Integer id){
		Optional<Track> track =  null;
		try {
			log.info("Selected Track Id = "+id);
			track = trackService.findById(id);
			if(track.isPresent()) {
				log.info("Track Data = "+track.get());
				return new ResponseEntity<Track>(track.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<Track>(track.get(), HttpStatus.CONFLICT);
				
		} catch (Exception e) {
			log.error("Error >>  while find Track Details by id, "+e.getMessage());
			return new ResponseEntity<Track>(track.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updateTrack" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTrack (@RequestBody Track track) {
		log.info("Enter into updateTrack function with below request parameters ");
		log.info("Request Parameters = "+track.toString());
		try {
			log.info("Calling service with request parameters.");
			track.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			trackService.saveTrack(track);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Track updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating track data. "+npe.getMessage());
			return Helper.findResponseStatus("Track update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating track data. "+e.getMessage());
			return Helper.findResponseStatus("Track update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteTrack/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteTrack(@PathVariable Integer id) {
		log.info("Enter into deleteTrack function");
		log.info("Selected Track Id = "+id);
		try {
			trackService.deleteTrackById(id);
			return Helper.findResponseStatus("Track Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting track data"+npe.getMessage());
			return Helper.findResponseStatus("Track Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting track data"+e.getMessage());
			return Helper.findResponseStatus("Track Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}

}
