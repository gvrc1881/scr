package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.StatusItem;
import com.scr.repository.StatusItemRepository;

@Service
public class CommonUtilService {

	@Autowired
	private StatusItemRepository statusItemRepository;
	public List<StatusItem> findStatusItem(String statusTypeId) {
		return statusItemRepository.findByStatusTypeId(statusTypeId);
	}
	
	
}
