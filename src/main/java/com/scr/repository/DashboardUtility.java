package com.scr.repository;

import java.sql.Connection;
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
		case Constants.DIVISION:
			return fetchDivisionWise(request);
		case Constants.SUB_DIVISION:
			return fetchSubDivisionWise(request);
		case Constants.DEPOT:
			return fetchDepotWise(request);
		case Constants.DIVISION_PERIOD:
			return fetchDivisionWisePeriod(request);
		case Constants.SUB_DIVISION_PERIOD:
			return fetchSubDivisionWisePeriod(request);
		case Constants.DEPOT_PERIOD:
			return fetchDepotWisePeriod(request);
		default:
			break;
		}
		return null;
	}

	private List<DashboardGraphsResponse> fetchDepotWisePeriod(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DashboardQueries.DEPOT_WISE_WITH_PERIOD);			
			preparedStatement.setString(1, request.getZone());
			preparedStatement.setString(2, request.getDivision());
			preparedStatement.setString(3, request.getSubDivision());
			preparedStatement.setString(4, request.getProduct());
			preparedStatement.setString(5, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(6, Helper.convertDateToString(request.getToDate()));
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				return prepareListFromResultSet(resultSet, request.getQueryType());
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

	private List<DashboardGraphsResponse> fetchSubDivisionWisePeriod(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DashboardQueries.SUB_DIVISION_WITH_PERIOD);			
			preparedStatement.setString(1, request.getZone());
			preparedStatement.setString(2, request.getDivision());
			preparedStatement.setString(3, request.getProduct());
			preparedStatement.setString(4, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(5, Helper.convertDateToString(request.getToDate()));
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				return prepareListFromResultSet(resultSet, request.getQueryType());
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

	private List<DashboardGraphsResponse> fetchDivisionWisePeriod(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DashboardQueries.DIVISION_WISE_WITH_PERIOD);			
			preparedStatement.setString(1, request.getZone());
			preparedStatement.setString(2, request.getProduct());
			preparedStatement.setString(3, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(4, Helper.convertDateToString(request.getToDate()));
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				return prepareListFromResultSet(resultSet, request.getQueryType());
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

	private List<DashboardGraphsResponse> fetchDepotWise(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DashboardQueries.DEPOT_WISE);			
			preparedStatement.setString(1, request.getZone());
			preparedStatement.setString(2, request.getDivision());
			preparedStatement.setString(3, request.getSubDivision());
			preparedStatement.setString(4, request.getProduct());
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				return prepareListFromResultSet(resultSet, request.getQueryType());
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

	private List<DashboardGraphsResponse> fetchSubDivisionWise(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DashboardQueries.SUB_DIVISION_WISE_BY_PRODUCT);
			preparedStatement.setString(1, request.getDivision());
			preparedStatement.setString(2, request.getZone());
			preparedStatement.setString(3, request.getProduct());
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				return prepareListFromResultSet(resultSet, request.getQueryType());
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

	private List<DashboardGraphsResponse> fetchDivisionWise(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(DashboardQueries.DIVISION_WISE_BY_PRODUCT);
			preparedStatement.setString(1, request.getProduct());
			preparedStatement.setString(2, request.getZone());
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				return prepareListFromResultSet(resultSet, request.getQueryType());
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
				return prepareListFromResultSet(resultSet, "");
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
				return prepareListFromResultSet(resultSet, "");
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

	private List<DashboardGraphsResponse> prepareListFromResultSet(ResultSet resultSet, String queryType) throws SQLException {
		List<DashboardGraphsResponse> responseList = new ArrayList<DashboardGraphsResponse>();
		DashboardGraphsResponse response = null; 
		while(resultSet != null && resultSet.next()) {
			response = new DashboardGraphsResponse();
			
			response.setZone(resultSet.getString("zone"));
			response.setDivision(resultSet.getString("div"));
			response.setMaterialDesc(resultSet.getString("material_desc"));
			if(queryType.equalsIgnoreCase(Constants.DIVISION) ||
				queryType.equalsIgnoreCase(Constants.SUB_DIVISION) ||
				queryType.equalsIgnoreCase(Constants.DEPOT)) {
				response.setQoh(resultSet.getDouble("qoh"));
			}
			if (!queryType.equalsIgnoreCase(Constants.DIVISION)) {
				if (queryType.equalsIgnoreCase(Constants.SUB_DIVISION)) {
					response.setSubDivision(resultSet.getString("subdiv"));
				} else if(queryType.equalsIgnoreCase(Constants.DEPOT)) {
					response.setSubDivision(resultSet.getString("subdiv"));
					response.setFacilityId(resultSet.getLong("facility_id"));
					response.setDepotName(resultSet.getString("depot_name"));					
				}else if(queryType.equalsIgnoreCase(Constants.DIVISION_PERIOD) ||
						queryType.equalsIgnoreCase(Constants.SUB_DIVISION_PERIOD) ||
						queryType.equalsIgnoreCase(Constants.DEPOT_PERIOD)) {
					if(queryType.equalsIgnoreCase(Constants.SUB_DIVISION_PERIOD)) {
						response.setSubDivision(resultSet.getString("sub_division"));
					}else if(queryType.equalsIgnoreCase(Constants.DEPOT_PERIOD)) {
						response.setSubDivision(resultSet.getString("subdiv"));
						response.setDepotName(resultSet.getString("depot_name"));
						response.setFacilityId(resultSet.getLong("facility_id"));
					}
					response.setReceivedQty(resultSet.getLong("received_qty"));
					response.setConsumedQty(resultSet.getLong("consumed_qty"));
					response.setQtyNetPeriod(resultSet.getLong("qty_net_period_net_qty"));	
				}
				response.setUom(resultSet.getString("uom"));
			}
			responseList.add(response);
		}
		return responseList;
	}

}
