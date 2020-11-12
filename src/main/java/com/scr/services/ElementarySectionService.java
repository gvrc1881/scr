package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.ElementarySections;
import com.scr.repository.ElementarySectionsRepository;

@Service
public class ElementarySectionService {
	
	@Autowired
	private ElementarySectionsRepository elementarySectionsRepository;

	public List<ElementarySections> findAll() {
		// TODO Auto-generated method stub
		return elementarySectionsRepository.findAll();
	}
	
	public void save(ElementarySections elementarySections) {
		elementarySectionsRepository.save(elementarySections);
	}
	public Optional<ElementarySections> findElementarySectionsById(Long id) {
		// TODO Auto-generated method stub
		return elementarySectionsRepository.findById(id);

	}

	public void deleteElementarySectionsById(Long id) {
		// TODO Auto-generated method stub
		elementarySectionsRepository.deleteById(id);
	}
}
