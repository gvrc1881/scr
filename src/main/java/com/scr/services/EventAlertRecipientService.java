package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.AlertGroup;
import com.scr.model.AlertGroupMember;
import com.scr.model.EventAlertRecipient;
import com.scr.model.EventType;
import com.scr.repository.EventAlertRecipientRepository;
import com.scr.repository.EventTypeRepository;

@Service
public class EventAlertRecipientService {
	
	@Autowired
	private EventAlertRecipientRepository eventAlertRecipientRepository;
	 
	@Autowired
	private EventTypeRepository eventTypeRepository;
	
	public List<EventAlertRecipient> findAll() {
		// TODO Auto-generated method stub
		return eventAlertRecipientRepository.findAll();
	}
	public void save(EventAlertRecipient eventAlertRecipient) {
		eventAlertRecipientRepository.save(eventAlertRecipient);
}
	public Optional<EventAlertRecipient> findEventAlertRecipientItemById(Long id) {
		// TODO Auto-generated method stub
		return eventAlertRecipientRepository.findById(id);
	}
	public Optional<EventType> findByEventTypeId(Long id) {
		// TODO Auto-generated method stub
		return eventTypeRepository.findById(id);
	}
	public List<EventType> findAllEventType() {
		// TODO Auto-generated method stub
		return eventTypeRepository.findAll();
	}
	public void deleteEventAlertRecipientById(Long id) {
		// TODO Auto-generated method stub
		eventAlertRecipientRepository.deleteById(id);
	}
	public Boolean existsByName(String name) {
		return eventAlertRecipientRepository.existsByName(name);
	}
	//exist condition
	public Boolean existsByDescription(String description) {
		return eventAlertRecipientRepository.existsByDescription(description);
	}
	public Optional<EventAlertRecipient> findByName(String name) {
		// TODO Auto-generated method stub
		return eventAlertRecipientRepository.findByName(name);
	}
	public Optional<EventAlertRecipient> findByDescription(String description) {
		// TODO Auto-generated method stub
		return eventAlertRecipientRepository.findByDescription(description);
	}
	
	public Boolean existsByEventTypeIdAndAlertGroupIdAndAlertGroupMemberId(EventType eventTypeId,AlertGroup alertGroupId, AlertGroupMember alertGroupMemberId) {
		// TODO Auto-generated method stub
		return eventAlertRecipientRepository.existsByEventTypeIdAndAlertGroupIdAndAlertGroupMemberId(eventTypeId,alertGroupId,alertGroupMemberId);
	}
	public Optional<EventAlertRecipient> findByEventTypeIdAndAlertGroupIdAndAlertGroupMemberId(EventType eventTypeId,AlertGroup alertGroupId,AlertGroupMember alertGroupMemberId) {
		// TODO Auto-generated method stub
		return eventAlertRecipientRepository.findByEventTypeIdAndAlertGroupIdAndAlertGroupMemberId(eventTypeId,alertGroupId,alertGroupMemberId);
	}
}
