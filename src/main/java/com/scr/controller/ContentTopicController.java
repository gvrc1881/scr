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
import com.scr.model.ContentTopic;
import com.scr.services.ContentCategoryService;
import com.scr.services.ContentTopicService;

@RestController
@RequestMapping("/scr/api")
public class ContentTopicController {
	
static Logger logger = LogManager.getLogger(ContentTopicController.class);
	
	@Autowired
	private ContentTopicService contentTopicService;
	
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/existsContentTopic", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<ContentTopic> contentTopicList() throws JSONException {
		List<ContentTopic> contentTopicList = null;
		try {
		logger.info("Fetch contentTopicList Started");	
		contentTopicList = contentTopicService.findAll();
		logger.info("Fetch contentTopicList Ended");
		return contentTopicList;
		} catch (NullPointerException e) {
			logger.error(e);
		}
		catch (Exception e) {
			logger.error(e);
		}
		return contentTopicList;	
	}

}
