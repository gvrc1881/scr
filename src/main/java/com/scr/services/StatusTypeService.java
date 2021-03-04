package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.StatusType;
import com.scr.repository.StatusTypeRepository;

@Service
public class StatusTypeService {
	
	@Autowired
	private StatusTypeRepository statusTypeRepository;
	
	public List<StatusType> findAll() {
		// TODO Auto-generated method stub
		return statusTypeRepository.findAll();
	}
	public void save(StatusType statusType) {
		statusTypeRepository.save(statusType);
}
	public Optional<StatusType> findStatusTypeById(Long id) {
		// TODO Auto-generated method stub
		return statusTypeRepository.findById(id);
	}

	public void deleteStatusTypeById(Long id) {
		// TODO Auto-generated method stub
		statusTypeRepository.deleteById(id);
	}
	public Boolean existsByStatusTypeId(String statusTypeId) {
		return statusTypeRepository.existsByStatusTypeId(statusTypeId);
	}
	//exist condition
	public Boolean existsByDescription(String description) {
		return statusTypeRepository.existsByDescription(description);
	}
	public Optional<StatusType> findByStatusTypeId(String statusTypeId) {
		// TODO Auto-generated method stub
		return statusTypeRepository.findByStatusTypeId(statusTypeId);
	}
	public Optional<StatusType> findByDescription(String description) {
		// TODO Auto-generated method stub
		return statusTypeRepository.findByDescription(description);
	}
}
