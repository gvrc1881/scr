package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.ElementarySection;
import com.scr.repository.ElementarySectionsRepository;

@Service
public class ElementarySectionService {
	
	@Autowired
	private ElementarySectionsRepository elementarySectionsRepository;

	public List<ElementarySection> findAll() {
		// TODO Auto-generated method stub
		return elementarySectionsRepository.findAll();
	}
	

}
