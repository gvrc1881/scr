package com.scr.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.request.DashboardParametersRequest;
import com.scr.message.response.DashboardGraphsResponse;
import com.scr.repository.DashboardUtility;

@Service
public class DashboardService {

	@Autowired
	private DashboardUtility utility;
	
	public List<DashboardGraphsResponse> findDashboardGraphsData(@Valid DashboardParametersRequest request) {
		return utility.findDashboardGraphsData(request);
	}

}
