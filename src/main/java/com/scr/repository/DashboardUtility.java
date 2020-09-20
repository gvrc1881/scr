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
		case Constants.DIVISION_WISE_ENERGY_CONSUMPTION:
			return fetchDivisonWiseEnergyConsumption(request);
		case Constants.FEEDER_WISE_ENERGY_CONSUMPTION:
			return fetchFeederWiseEnergyConsumption(request);	
		default:
			break;
		}
		return null;
	}
	
	private List<DashboardGraphsResponse> fetchFeederWiseEnergyConsumption(@Valid DashboardParametersRequest request) {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
			try {
				connection = dataSource.getConnection();
				preparedStatement = connection.prepareStatement(DashboardQueries.FEEDER_WISH_ENERGY_CONSUMPTION);
				preparedStatement.setString(1, Helper.convertDateToString(request.getFromDate()));
				preparedStatement.setString(2, Helper.convertDateToString(request.getToDate()));
				preparedStatement.setString(3, request.getFeederId());
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null) {
					return prepareListForEnergyConsumptionFromResultSet(resultSet, request.getQueryType());
				}
			} catch (SQLException e) {
				logger.error("ERROR >>> while fetching data "+e.getLocalizedMessage());
			}finally {
				closeJDBCObjects.releaseResouces(connection, preparedStatement, resultSet);
			}
		return null;
	}

	private List<DashboardGraphsResponse> fetchDivisonWiseEnergyConsumption(@Valid DashboardParametersRequest request) {
			Connection connection = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			try {
				connection = dataSource.getConnection();
				preparedStatement = connection.prepareStatement(DashboardQueries.DIVISION_WISE_ENERGY_CONSUMPTION);
				preparedStatement.setString(1, Helper.convertDateToString(request.getFromDate()));
				preparedStatement.setString(2, Helper.convertDateToString(request.getFromDate()));
				preparedStatement.setString(3, request.getDivision());
				preparedStatement.setString(4, Helper.convertDateToString(request.getFromDate()));
				preparedStatement.setString(5, Helper.convertDateToString(request.getFromDate()));
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null) {
					return prepareListForEnergyConsumptionFromResultSet(resultSet, request.getQueryType());
				}
			} catch (SQLException e) {
				logger.error("ERROR >>> while fetching data "+e.getLocalizedMessage());
			}finally {
				closeJDBCObjects.releaseResouces(connection, preparedStatement, resultSet);
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
			preparedStatement.setString(1, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(2, Helper.convertDateToString(request.getToDate()));
			preparedStatement.setString(3, request.getZone());
			preparedStatement.setString(4, request.getDivision());
			preparedStatement.setString(5, request.getSubDivision());
			preparedStatement.setString(6, request.getProduct());
			preparedStatement.setString(7, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(8, Helper.convertDateToString(request.getToDate()));
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
			preparedStatement.setString(1, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(2, Helper.convertDateToString(request.getToDate()));
			preparedStatement.setString(3, request.getZone());
			preparedStatement.setString(4, request.getDivision());
			preparedStatement.setString(5, request.getProduct());
			preparedStatement.setString(6, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(7, Helper.convertDateToString(request.getToDate()));
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
			preparedStatement.setString(1, Helper.convertDateToString(request.getFromDate()));
			preparedStatement.setString(2, Helper.convertDateToString(request.getToDate()));
			preparedStatement.setString(3, request.getZone());
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
			response.setHeader(resultSet.getString("header"));
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
	
	private List<DashboardGraphsResponse> prepareListForEnergyConsumptionFromResultSet(ResultSet resultSet,
			String queryType) throws SQLException {
		List<DashboardGraphsResponse> responseList = new ArrayList<DashboardGraphsResponse>();
		DashboardGraphsResponse response = null;
		while (resultSet != null && resultSet.next()) {
			response = new DashboardGraphsResponse();
			if (queryType.equalsIgnoreCase(Constants.FEEDER_WISE_ENERGY_CONSUMPTION)) {
				response.setRequestedReadingDate(resultSet.getString("requested_reading_date"));
			}
			response.setFeederName(resultSet.getString("feeder_name"));
			response.setCurKwh(resultSet.getDouble("cur_kwh"));
			response.setPrevKwh(resultSet.getDouble("prev_kwh"));
			response.setMultiplicationFac(resultSet.getDouble("multiplication_fac"));
			response.setConsumption(resultSet.getDouble("consumption"));
			responseList.add(response);
		}
		return responseList;
	}

}
