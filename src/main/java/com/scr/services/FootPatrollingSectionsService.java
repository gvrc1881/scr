package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.mapper.FacilityCommonMapper;
import com.scr.model.FootPatrollingSection;
import com.scr.repository.FootPatrollingSectionRepository;

@Service
public class FootPatrollingSectionsService {
	
	@Autowired
	private FootPatrollingSectionRepository footPatrollingSectionRepository;
	
	@Autowired
	private FacilityCommonMapper facilityCommonMapper;


	
	public List<FootPatrollingSection> findAll() {
		List<FootPatrollingSection> fs = new ArrayList<>();
		List<FootPatrollingSection> fpSection = footPatrollingSectionRepository.findAll();
		for (FootPatrollingSection footPatrollingSection : fpSection) {
			footPatrollingSection = facilityCommonMapper.prepareFpSectionsData(footPatrollingSection);
			fs.add(footPatrollingSection);
		}
		 return fs;
	}
	
	

	public void save(FootPatrollingSection footPatrollingSection) {
		// TODO Auto-generated method stub
		footPatrollingSectionRepository.save(footPatrollingSection);
	}

	public Optional<FootPatrollingSection> findFPSectionsItemById(Long id) {
		// TODO Auto-generated method stub
		return footPatrollingSectionRepository.findById(id);
	}

	public void deleteFPSectionsItemById(Long id) {
		// TODO Auto-generated method stub
		footPatrollingSectionRepository.deleteById(id);
	}
	public Boolean existsByFpSection(String fpSection) {
		// TODO Auto-generated method stub
		return footPatrollingSectionRepository.existsByFpSection(fpSection);
	}
	public Optional<FootPatrollingSection> findByFpSection(String fpSection) {
		// TODO Auto-generated method stub
		return footPatrollingSectionRepository.findByFpSection(fpSection);
	}
}
