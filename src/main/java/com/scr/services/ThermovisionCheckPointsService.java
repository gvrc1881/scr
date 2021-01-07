package com.scr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.ThermovisionCheckPoints;
import com.scr.repository.ThermovisionCheckPointsRepository;

@Service
public class ThermovisionCheckPointsService {
	
	@Autowired
	private ThermovisionCheckPointsRepository thermovisionCheckPointsRepository;

	public Optional<ThermovisionCheckPoints> findByCheckPointDescription(String tcpCheckPointDescription) {
		return thermovisionCheckPointsRepository.findByCheckPointDescription(tcpCheckPointDescription);
	}
	

}
