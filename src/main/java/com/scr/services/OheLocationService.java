package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.OheLocation;
import com.scr.repository.OheLocationRepository;

@Service
public class OheLocationService {
	
	@Autowired
	private OheLocationRepository oheLocationRepository;

	
	public List<OheLocation> findAll(){
		return oheLocationRepository.findAll();
	}
	
	public void save(OheLocation oheLocation) {
		oheLocationRepository.save(oheLocation);
	}
	public Optional<OheLocation> findOheLocationById(Long id) {
		// TODO Auto-generated method stub
		return oheLocationRepository.findById(id);

	}

	public void deleteOheLocationById(Long id) {
		// TODO Auto-generated method stub
		oheLocationRepository.deleteById(id);
	}

}
