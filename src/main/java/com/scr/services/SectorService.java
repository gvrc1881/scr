package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.FacilityCommonMapper;
import com.scr.model.GantryMasterData;
import com.scr.model.Line;
import com.scr.model.Sector;
import com.scr.repository.LineRepository;
import com.scr.repository.SectorRepository;

@Service
public class SectorService {
	static Logger logger = LogManager.getLogger(SectorService.class);


	@Autowired
	private SectorRepository sectorRepository;
	@Autowired
	private LineRepository lineRepository;
	@Autowired
	private FacilityCommonMapper facilityCommonMapper;


	
	public List<Sector> findAll() {
		logger.info("Calling mapper for preparing to get Sector Data Allmodel object");
		List<Sector> sec = new ArrayList<>();
		List<Sector> sectorData = sectorRepository.findAll();
		for (Sector sector : sectorData) {
			sector = facilityCommonMapper.prepareSectorData(sector);
			sec.add(sector);
		}
		 return sec;
	}
	
	public List<Line> findAllOrderByLineCodeAsc() {
		// TODO Auto-generated method stub
		return lineRepository.findAllOrderByLineCodeAsc();
	}
	public void save(Sector sector) {
		sectorRepository.save(sector);
	}
	public Optional<Sector> findSectorById(Long id) {
		// TODO Auto-generated method stub
		return sectorRepository.findById(id);

	}

	public void deleteSectorById(Long id) {
		// TODO Auto-generated method stub
		sectorRepository.deleteById(id);
	}
	
	public Boolean existsBySectorCode(String sectorCode) {
		// TODO Auto-generated method stub
		return sectorRepository.existsBySectorCode(sectorCode);
	}
	
	public Optional<Sector> findBySectorCode(String sectorCode) {
		return sectorRepository.findBySectorCode(sectorCode);
	}
}
