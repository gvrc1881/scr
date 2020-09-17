package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.scr.model.Model;
import com.scr.repository.ModelRepository;

@Service
public class ModelService {
	
	private static Logger logger = LogManager.getLogger(ModelService.class);
	

	@Autowired
	 private ModelRepository modelRepository;
	
	public void save(Model model) {
		// TODO Auto-generated method stub
		modelRepository.save(model);
	}
	
	public void deleteModelById(Long id) {
		// TODO Auto-generated method stub
		modelRepository.deleteById(id);
	}

	public List<Model> findAllOrderBymodelCodeAsc() {
		// TODO Auto-generated method stub
		return modelRepository.findAllOrderBymodelCodeAsc();
	}
	
	public Optional<Model> findModelById(Long id) {
		// TODO Auto-generated method stub
		return modelRepository.findById(id);
	}
	
	
	public Boolean existsByModelCode(String modelCode)
	{
		return modelRepository.existsByModelCode(modelCode);
	}
	public Optional<Model> findByModelCode(String modelCode) {
		// TODO Auto-generated method stub
		return modelRepository.findByModelCode(modelCode);
	}

}