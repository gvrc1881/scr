package com.scr.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.PingResponse;
import com.scr.util.WebUtils;


@RestController
@RequestMapping("/scr/api")
public class PingController {
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = "application/json")
	public PingResponse ping(HttpServletRequest request) {
		System.out.println(WebUtils.getClientIp(request));
		return new PingResponse();
	}

}