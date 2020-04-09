package com.scr.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.model.ContentCategory;
import com.scr.services.ContentCategoryService;

@RestController
@RequestMapping("/scr/api")
public class ContentCategoryController {
	
	static Logger logger = LogManager.getLogger(ContentCategoryController.class);
	
	@Autowired
	private ContentCategoryService ContentCategoryService;
	
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/existsContentCategory", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<ContentCategory> contentCategoryList() throws JSONException {
		List<ContentCategory> contentCategoryList = null;
		try {
		logger.info("Fetch contentCategoryList Started");	
		contentCategoryList = ContentCategoryService.findAll();
		logger.info("Fetch contentCategoryList Ended");
		return contentCategoryList;
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return contentCategoryList;	
	}

}
