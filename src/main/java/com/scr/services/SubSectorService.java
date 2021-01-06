package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.mapper.FacilityCommonMapper;
import com.scr.model.SubSector;
import com.scr.repository.SubSectorRepository;

@Service
public class SubSectorService {
	
	static Logger logger = LogManager.getLogger(SubSectorService.class);

	
	@Autowired
	private SubSectorRepository subSectorRepository;

	@Autowired
	private FacilityCommonMapper facilityCommonMapper;

	public List<SubSector> findAll() {
		logger.info("Calling mapper for preparing to get SubSector Data Allmodel object");
		List<SubSector> subSec = new ArrayList<>();
		List<SubSector> subSectorData = subSectorRepository.findAll();
		for (SubSector subSector : subSectorData) {
			subSector = facilityCommonMapper.prepareSubSectorData(subSector);
			subSec.add(subSector);
		}
		 return subSec;
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
