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
import com.scr.model.TractionEnergyTariff;
import com.scr.services.TrackService;
import com.scr.util.Constants;
import com.scr.util.Helper;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class TrackController {
	
	private Logger logger = Logger.getLogger(TrackController.class);
	
	@Autowired
	private TrackService trackService;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllTrack", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Track> trackList() throws JSONException {
		List<Track> trackList = null;
		try {
			logger.info("Fetch track List Started");	
			trackList = trackService.findAll();
			logger.info("Fetch track List Ended"+trackList);
		return trackList;
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return trackList;	
	}
	
	@RequestMapping(value = "/addTrack", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveTrack(@RequestBody Track track){
		track.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		Track saveTrack = trackService.saveTrack(track);
		if (saveTrack != null) {
			return Helper.findResponseStatus("Track added successfully", Constants.SUCCESS_CODE);
		}
		return null;
	}
	
	
	@RequestMapping(value = "/findTrack/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Track> findById(@PathVariable("id") Integer id){
		Optional<Track> ele = trackService.findById(id);
		return new ResponseEntity<>(ele.get(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateTrack" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateTrack (@RequestBody Track track) {
		track.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
		trackService.saveTrack(track);
		return Helper.findResponseStatus("Track updated successfully", Constants.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/deleteTrack/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteTrack(@PathVariable Integer id) {
		logger.info("*** id****"+id);
		trackService.deleteTrackById(id);
		return Helper.findResponseStatus("Track Deleted successfully", Constants.SUCCESS_CODE);
	}

}
