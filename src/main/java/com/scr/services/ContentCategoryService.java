package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.ContentCategory;
import com.scr.repository.ContentCategoryRepository;

@Service
public class ContentCategoryService {
	
	@Autowired
	private ContentCategoryRepository contentCategoryRepository;

	public List<ContentCategory> findAll() {
		// TODO Auto-generated method stub
		return contentCategoryRepository.findAll();
	}

}
