package com.scr.services;

import java.util.List;
import java.util.Optional;
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
	public void save(SubSector subSector) {
		subSectorRepository.save(subSector);
	}
	public Optional<SubSector> findSubSectorById(Long id) {
		// TODO Auto-generated method stub
		return subSectorRepository.findById(id);

	}

	public void deleteSubSectorById(Long id) {
		// TODO Auto-generated method stub
		subSectorRepository.deleteById(id);
	}
	public Boolean existsBySubSectorCode(String subSectorCode) {
		// TODO Auto-generated method stub
		return subSectorRepository.existsBySubSectorCode(subSectorCode);
	}
	
	public Optional<SubSector> findBySubSectorCode(String subSectorCode) {
		return subSectorRepository.findBySubSectorCode(subSectorCode);
	}

}
