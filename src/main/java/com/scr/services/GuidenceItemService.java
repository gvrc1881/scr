package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.GuidenceItem;
import com.scr.repository.GuidenceItemRepository;

@Service
public class GuidenceItemService {
	
	@Autowired
	private GuidenceItemRepository guidenceItemRepository;
	
	public List<GuidenceItem> findAll() {
		// TODO Auto-generated method stub
		return guidenceItemRepository.findAll();
	}

	public void save(GuidenceItem guidenceItem) {
		// TODO Auto-generated method stub
		guidenceItemRepository.save(guidenceItem);
	}

	public Optional<GuidenceItem> findGuidenceItemById(Integer id) {
		// TODO Auto-generated method stub
		return guidenceItemRepository.findById(id);
	}

	public void deleteGuidenceItemById(Integer id) {
		// TODO Auto-generated method stub
		guidenceItemRepository.deleteById(id);
	}

}
