package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Sector;
import com.scr.repository.SectorRepository;

@Service
public class SectorService {

	@Autowired
	private SectorRepository sectorRepository;

	public List<Sector> findAll() {
		// TODO Auto-generated method stub
		return sectorRepository.findAll();
	}

}
