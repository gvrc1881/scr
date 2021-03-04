package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.StatusItem;
import com.scr.repository.StatusItemRepository;

@Service
public class StatusItemService {
	
	@Autowired
	private StatusItemRepository statusItemRepository;
	
	public List<StatusItem> findAll() {
		// TODO Auto-generated method stub
		return statusItemRepository.findAll();
	}
	public void save(StatusItem statusItem) {
		statusItemRepository.save(statusItem);
}
	public Optional<StatusItem> findStatusItemById(Long id) {
		// TODO Auto-generated method stub
		return statusItemRepository.findById(id);
	}

	public void deleteStatusItemById(Long id) {
		// TODO Auto-generated method stub
		statusItemRepository.deleteById(id);
	}

}
