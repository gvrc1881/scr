package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.FootPatrollingSection;
import com.scr.repository.FootPatrollingSectionRepository;

@Service
public class FootPatrollingSectionsService {
	
	@Autowired
	private FootPatrollingSectionRepository footPatrollingSectionRepository;
	
	public List<FootPatrollingSection> findAll() {
		// TODO Auto-generated method stub
		return footPatrollingSectionRepository.findAll();
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

}
