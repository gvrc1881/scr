package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.SubSector;
import com.scr.repository.SubSectorRepository;

@Service
public class SubSectorService {
	
	@Autowired
	private SubSectorRepository subSectorRepository;

	public List<SubSector> findAll() {
		// TODO Auto-generated method stub
		return subSectorRepository.findAll();
	}
	

}
