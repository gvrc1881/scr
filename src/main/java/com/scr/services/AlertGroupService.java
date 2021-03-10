package com.scr.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.AlertGroup;
import com.scr.model.StatusType;
import com.scr.repository.AlertGroupRepository;

@Service
public class AlertGroupService {
	
	@Autowired
	private AlertGroupRepository alertGroupRepository;
	
	public List<AlertGroup> findAll() {
		// TODO Auto-generated method stub
		return alertGroupRepository.findAll();
	}
	public void save(AlertGroup alertGroup) {
		alertGroupRepository.save(alertGroup);
}
	public Optional<AlertGroup> findAlertGroupItemById(Long id) {
		// TODO Auto-generated method stub
		return alertGroupRepository.findById(id);
	}

	public void deleteAlertGroupById(Long id) {
		// TODO Auto-generated method stub
		alertGroupRepository.deleteById(id);
	}
	public Boolean existsByName(String name) {
		return alertGroupRepository.existsByName(name);
	}
	//exist condition
	public Boolean existsByDescription(String description) {
		return alertGroupRepository.existsByDescription(description);
	}
	public Optional<AlertGroup> findByName(String name) {
		// TODO Auto-generated method stub
		return alertGroupRepository.findByName(name);
	}
	public Optional<AlertGroup> findByDescription(String description) {
		// TODO Auto-generated method stub
		return alertGroupRepository.findByDescription(description);
	}
}
