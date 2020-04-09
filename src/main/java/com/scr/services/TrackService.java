package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Track;
import com.scr.repository.TrackRepository;

@Service
public class TrackService {
	
	@Autowired
	private TrackRepository trackRepository;

	public List<Track> findAll() {
		// TODO Auto-generated method stub
		return trackRepository.findAll();
	}

	public Track saveTrack(Track track) {
		// TODO Auto-generated method stub
		return trackRepository.save(track);
	}

	public Optional<Track> findById(Integer id) {
		// TODO Auto-generated method stub
		return trackRepository.findById(id);
	}

	public void deleteTrackById(Integer id) {
		// TODO Auto-generated method stub
		trackRepository.deleteById(id);
	}

}
