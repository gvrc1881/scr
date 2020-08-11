package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.scr.model.Measures;
import com.scr.repository.MeasuresRepository;

@Service
public class MeasuresService {
	
	private static Logger logger = LogManager.getLogger(MeasuresService.class);
	

	@Autowired
	 private MeasuresRepository measuresRepository;
	

	public List<Measures> findAll() {
		// TODO Auto-generated method stub
		return measuresRepository.findAll();
	}
	

}