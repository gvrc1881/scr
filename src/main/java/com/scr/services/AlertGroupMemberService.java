package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.AlertGroupMember;
import com.scr.model.Receipents;
import com.scr.repository.AlertGroupMemberRepository;
import com.scr.repository.ReceipentsRepository;

@Service
public class AlertGroupMemberService {
	
	@Autowired
	private AlertGroupMemberRepository alertGroupMemberRepository;
	
	@Autowired
	private ReceipentsRepository receipentsRepository;
	
	
	public List<Receipents> findAllReceipentsList() {
		// TODO Auto-generated method stub
		return receipentsRepository.findAll();
	}
	
	public Optional<Receipents> findReceipentsItemById(Long id) {
		// TODO Auto-generated method stub
		return receipentsRepository.findById(id);
	}
	
	public List<AlertGroupMember> findAll() {
		// TODO Auto-generated method stub
		return alertGroupMemberRepository.findAll();
	}
	public void save(AlertGroupMember alertGroup) {
		alertGroupMemberRepository.save(alertGroup);
}
	public Optional<AlertGroupMember> findAlertGroupMemberItemById(Long id) {
		// TODO Auto-generated method stub
		return alertGroupMemberRepository.findById(id);
	}

	public void deleteAlertGroupMemberById(Long id) {
		// TODO Auto-generated method stub
		alertGroupMemberRepository.deleteById(id);
	}
	public Boolean existsByName(String name) {
		return alertGroupMemberRepository.existsByName(name);
	}
	//exist condition
	public Boolean existsByDescription(String description) {
		return alertGroupMemberRepository.existsByDescription(description);
	}
	public Optional<AlertGroupMember> findByName(String name) {
		// TODO Auto-generated method stub
		return alertGroupMemberRepository.findByName(name);
	}
	public Optional<AlertGroupMember> findByDescription(String description) {
		// TODO Auto-generated method stub
		return alertGroupMemberRepository.findByDescription(description);
	}

}
