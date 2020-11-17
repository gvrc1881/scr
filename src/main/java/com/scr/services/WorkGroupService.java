package com.scr.services;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import com.scr.message.request.WorkGroupRequest;
import com.scr.model.WorkGroup;
import com.scr.model.Works;
import com.scr.repository.WorkGroupRepository;
import com.scr.util.Constants;

@Service
public class WorkGroupService {
	
	static Logger logger = LogManager.getLogger(WorkGroupService.class);
	
	@Autowired
	private WorkGroupRepository workGroupRepository;


	

	public void save(WorkGroup workGroup) {
		// TODO Auto-generated method stub
		workGroupRepository.save(workGroup);
	}
	
		

	public Optional<WorkGroup> findGroupsSectionsById(Long id) {
		
		return workGroupRepository.findById(id);
	}

	public void deleteGroupsSectionsById(Long id) {
		
		workGroupRepository.deleteById(id);
	}


	public List<WorkGroup> findAll() {
		// TODO Auto-generated method stub
		return workGroupRepository.findAll();
	}



	public Boolean existsByworkIdAndWorkGroupAndSection(Works work, String group, String section) {
		// TODO Auto-generated method stub
		return  workGroupRepository.existsByworkIdAndWorkGroupAndSection(work,group,section);
	}





	public Optional<WorkGroup> findByworkIdAndWorkGroupAndSection(Works work, String group, String section) {
		// TODO Auto-generated method stub
		return workGroupRepository.findByworkIdAndWorkGroupAndSection(work,group,section);
	}




	
}


