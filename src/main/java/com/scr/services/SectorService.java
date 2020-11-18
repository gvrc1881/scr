package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.Line;
import com.scr.model.Sector;
import com.scr.repository.LineRepository;
import com.scr.repository.SectorRepository;

@Service
public class SectorService {

	@Autowired
	private SectorRepository sectorRepository;
	@Autowired
	private LineRepository lineRepository;

	public List<Sector> findAll() {
		// TODO Auto-generated method stub
		return sectorRepository.findAll();
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
