package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.scr.model.Make;
import com.scr.repository.MakeRepository;

@Service
public class MakeService {
	
	private static Logger logger = LogManager.getLogger(MakeService.class);
	

	@Autowired
	 private MakeRepository makeRepository;
	
	public void save(Make make) {
		// TODO Auto-generated method stub
		makeRepository.save(make);
	}
	
	public void deleteMakeById(Long id) {
		// TODO Auto-generated method stub
		makeRepository.deleteById(id);
	}

	public List<Make> findAll() {
		// TODO Auto-generated method stub
		return makeRepository.findAll();
	}
	
	public Optional<Make> findMakeById(Long id) {
		// TODO Auto-generated method stub
		return makeRepository.findById(id);
	}
	
	public Boolean existsByMakeName(String makeName)
	{
		return makeRepository.existsByMakeName(makeName);
	}
	
	public Boolean existsByMakeCode(String makeCode)
	{
		return makeRepository.existsByMakeCode(makeCode);
	}
	
	public Boolean existsByIdAndMakeCode(Long id,String makeCode)
	{
		return false;
	}

	public Optional<Make> findByMakeCode(String makeCode) {
		// TODO Auto-generated method stub
		return makeRepository.findByMakeCode(makeCode);
	}

}