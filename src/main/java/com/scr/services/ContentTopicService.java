package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.ContentTopic;
import com.scr.repository.ContentTopicRepository;

@Service
public class ContentTopicService {
	
	@Autowired
	private ContentTopicRepository contentTopicRepository;

	public  List<ContentTopic> findAll() {
		// TODO Auto-generated method stub
		return contentTopicRepository.findAll();
	}

}
