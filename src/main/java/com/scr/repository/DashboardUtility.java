package com.scr.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.request.DashboardParametersRequest;
import com.scr.message.response.DashboardGraphsResponse;
import com.scr.util.CloseJDBCObjects;
import com.scr.util.Constants;
import com.scr.util.DashboardQueries;
import com.scr.util.Helper;

@Component
public class DashboardUtility {
	
	static Logger logger = LogManager.getLogger(DashboardUtility.class);
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private CloseJDBCObjects closeJDBCObjects;
	
	public List<DashboardGraphsResponse> findDashboardGraphsData(@Valid DashboardParametersRequest request) {
		switch (request.getQueryType()) {
		case Constants.MATERIAL_QTY_RECEIVED_AND_CONSUMED_IN_GIVEN_PERIOD_BY_DEPOT:
			return fetchByGivenPeriod(request);
		case Constants.MATERIAL_QTY_ONH_AND_BY_DEPOT:
			return fetchMaterialDataByDepot(request);
		default:
			break;
		}
		return null;
	}

	private List<DashboardGraphsResponse> fetchMaterialDataByDepot(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DashboardQueries.MATERIAL_QTY_ONH_AND_BY_DEPOT);
			preparedStatement.setString(1, request.getDepot());
			preparedStatement.setString(2, request.getDivision());
			preparedStatement.setString(3, request.getFacility());
			preparedStatement.setString(4, request.getSubDivision());
			preparedStatement.setString(5, request.getZone());
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				return prepareListFromResultSet(resultSet);
			}
		}catch (SQLException e) {
			logger.error("ERROR >>> while fetching data "+e.getLocalizedMessage());
		}catch (Exception e) {
			logger.error("ERROR >>> while fetching data "+e.getLocalizedMessage());
		}finally {
			closeJDBCObjects.releaseResouces(connection, preparedStatement, resultSet);
		}
		return null;
	}

	private List<DashboardGraphsResponse> fetchByGivenPeriod(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DashboardQueries.MATERIAL_QTY_RECEIVED_AND_CONSUMED_IN_GIVEN_PERIOD_BY_DEPOT);
			preparedStatement.setString(1, request.getDepot());
			preparedStatement.setString(2, request.getDivision());
			preparedStatement.setString(3, request.getFacility());
			preparedStatement.setString(4, request.getSubDivision());
			preparedStatement.setString(5, request.getZone());
			preparedStatement.setString(6, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(7, Helper.convertDateToString(request.getToDate()));
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				return prepareListFromResultSet(resultSet);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			logger.error("ERROR >>> while fetching data "+e.getLocalizedMessage());
		}catch (Exception e) {
			logger.error("ERROR >>> while fetching data "+e.getLocalizedMessage());
		}finally {
			closeJDBCObjects.releaseResouces(connection, preparedStatement, resultSet);
		}
		return null;
	}

	private List<DashboardGraphsResponse> prepareListFromResultSet(ResultSet resultSet) throws SQLException {
		List<DashboardGraphsResponse> responseList = new ArrayList<DashboardGraphsResponse>();
		DashboardGraphsResponse response = null; 
		while(resultSet != null && resultSet.next()) {
			response = new DashboardGraphsResponse();
			
			response.setZone(resultSet.getString("zone"));
			response.setDivision(resultSet.getString("div"));
			response.setSubDivision(resultSet.getString("subdiv"));
			response.setMaterialDesc(resultSet.getString("material_desc"));
			response.setReceivedQty(resultSet.getLong("received_qty"));
			response.setConsumedQty(resultSet.getLong("consumed_qty"));
			response.setQtyNetPeriod(resultSet.getLong("qty_net_period_net_qty"));
			response.setUom(resultSet.getString("uom"));
			
			responseList.add(response);
		}
		return responseList;
	}

}
