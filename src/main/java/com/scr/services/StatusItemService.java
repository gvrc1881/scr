package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.StatusItem;
import com.scr.model.TpcBoardReportingFacility;
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
	public Boolean existsByStatusTypeIdAndStatusId(String statusTypeId, String statusId) {
		return statusItemRepository.existsByStatusTypeIdAndStatusId(statusTypeId,statusId);
	}
	public Optional<StatusItem> findByStatusTypeIdAndStatusId(String statusTypeId,String statusId) {
		// TODO Auto-generated method stub
		return statusItemRepository.findByStatusTypeIdAndStatusId(statusTypeId,statusId);
	}
	public Boolean existsByStatusTypeIdAndStatusCode(String statusTypeId, String statusCode) {
		return statusItemRepository.existsByStatusTypeIdAndStatusCode(statusTypeId,statusCode);
	}
	public Optional<StatusItem> findByStatusTypeIdAndStatusCode(String statusTypeId,String statusCode) {
		// TODO Auto-generated method stub
		return statusItemRepository.findByStatusTypeIdAndStatusCode(statusTypeId,statusCode);
	}
	public Boolean existsByStatusTypeIdAndDescription(String statusTypeId, String description) {
		return statusItemRepository.existsByStatusTypeIdAndDescription(statusTypeId,description);
	}
	public Optional<StatusItem> findByStatusTypeIdAndDescription(String statusTypeId,String description) {
		// TODO Auto-generated method stub
		return statusItemRepository.findByStatusTypeIdAndDescription(statusTypeId,description);
	}
}
