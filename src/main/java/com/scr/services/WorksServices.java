package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Works;
import com.scr.repository.WorksRepository;

@Service
public class WorksServices {

	private static Logger log = LogManager.getLogger(WorksServices.class);

	@Autowired
	private WorksRepository worksRepository;

	public List<Works> findAll() {
		// TODO Auto-generated method stub
		return worksRepository.findAll();
	}

	public Works save(Works work) {
		// TODO Auto-generated method stub
		return worksRepository.save(work);
	}

	public Optional<Works> findById(Integer id) {
		// TODO Auto-generated method stub
		return worksRepository.findById(id);
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		worksRepository.deleteById(id);
		
	}

}
