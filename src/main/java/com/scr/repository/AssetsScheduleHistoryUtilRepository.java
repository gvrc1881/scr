package com.scr.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.message.response.ASHMeasuresActvities;
import com.scr.message.response.AssetsScheduleHistoryResponse;
import com.scr.message.response.EnergyConsumptionResponse;
import com.scr.util.CloseJDBCObjects;



@Repository
public class AssetsScheduleHistoryUtilRepository{
	private static final Logger logger = Logger.getLogger(EnergyConsumptionRepository.class);
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	CloseJDBCObjects closeJDBCObjects;
	public static final String SQL_SELECT_ASH_FACLITY_NAME="select  ash.*,fac.facility_name\n" + 
			"from assets_schedule_history ash " + 
			"inner join facility fac " + 
			"on ash.facility_id=fac.facility_id "+
			"order by ash.asset_id,ash.schedule_date";
	public List<AssetsScheduleHistoryResponse> findAshWithFacilityName() {
		List<AssetsScheduleHistoryResponse> list = new ArrayList<AssetsScheduleHistoryResponse>();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = dataSource.getConnection();
				psPreparedStatement = con.prepareStatement(SQL_SELECT_ASH_FACLITY_NAME);
			resultSet = psPreparedStatement.executeQuery();
			AssetsScheduleHistoryResponse response = null;
			while(resultSet != null && resultSet.next()) {
				response = new AssetsScheduleHistoryResponse();
				//response.setReq_date(resultSet.getDate("req_date"));
				response.setAssetId(resultSet.getString("asset_id"));
				response.setAssetType(resultSet.getString("asset_type"));
				response.setCreatedBy(resultSet.getString("created_by"));
				response.setCreatedOn(resultSet.getTimestamp("created_on"));
				response.setCreatedStamp(resultSet.getTimestamp("created_stamp"));
				response.setCreatedTxStamp(resultSet.getTimestamp("created_tx_stamp"));
				response.setDataDiv(resultSet.getString("data_div"));
				response.setDepo(resultSet.getString("facility_name"));
				response.setDetailsOfMaint(resultSet.getString("details_of_maint"));
				response.setDeviceCreatedStamp(resultSet.getTimestamp("device_created_stamp"));
				response.setDeviceId(resultSet.getString("device_id"));
				response.setDeviceLastUpdatedStamp(resultSet.getTimestamp("device_last_updated_stamp"));
				response.setDeviceSeqId(resultSet.getString("device_seq_id"));
				response.setDoneBy(resultSet.getString("done_by"));
				response.setFacilityId(resultSet.getString("facility_id"));
				response.setId(resultSet.getLong("id"));
				response.setInitialOfIncharge(resultSet.getString("initial_of_incharge"));
				response.setLastUpdatedStamp(resultSet.getTimestamp("last_updated_stamp"));
				response.setLastUpdatedTxStamp(resultSet.getTimestamp("last_updated_tx_stamp"));
				response.setPbOperationSeqId(resultSet.getString("pb_operation_seq_id"));
				response.setRemarks(resultSet.getString("remarks"));
				response.setScheduleCode(resultSet.getString("schedule_code"));
				response.setScheduleDate(resultSet.getTimestamp("schedule_date"));
				response.setSeqId(resultSet.getString("seq_id"));
				response.setStatus(resultSet.getString("status"));
				
				
				
				
				list.add(response);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			closeJDBCObjects.releaseResouces(con, psPreparedStatement, resultSet);
		}
		return list;
	}
	
	public static final String SQL_SELECT_ASH_BY_ID="select  ash.*,fac.facility_name\n" + 
			"from assets_schedule_history ash " + 
			"inner join facility fac " + 
			"on ash.facility_id=fac.facility_id "+
			"where ash.id=? "+
			"order by ash.asset_id,ash.schedule_date";
	public AssetsScheduleHistoryResponse findAshWithFacilityNameById(String id) {
		//List<AssetsScheduleHistoryResponse> list = new ArrayList<AssetsScheduleHistoryResponse>();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		AssetsScheduleHistoryResponse response = null;
		try {
			con = dataSource.getConnection();
				psPreparedStatement = con.prepareStatement(SQL_SELECT_ASH_BY_ID);
				psPreparedStatement.setLong(1, Long.valueOf(id));
			resultSet = psPreparedStatement.executeQuery();
			
			while(resultSet != null && resultSet.next()) {
				response = new AssetsScheduleHistoryResponse();
				//response.setReq_date(resultSet.getDate("req_date"));
				response.setAssetId(resultSet.getString("asset_id"));
				response.setAssetType(resultSet.getString("asset_type"));
				response.setCreatedBy(resultSet.getString("created_by"));
				response.setCreatedOn(resultSet.getTimestamp("created_on"));
				response.setCreatedStamp(resultSet.getTimestamp("created_stamp"));
				response.setCreatedTxStamp(resultSet.getTimestamp("created_tx_stamp"));
				response.setDataDiv(resultSet.getString("data_div"));
				response.setDepo(resultSet.getString("facility_name"));
				response.setDetailsOfMaint(resultSet.getString("details_of_maint"));
				response.setDeviceCreatedStamp(resultSet.getTimestamp("device_created_stamp"));
				response.setDeviceId(resultSet.getString("device_id"));
				response.setDeviceLastUpdatedStamp(resultSet.getTimestamp("device_last_updated_stamp"));
				response.setDeviceSeqId(resultSet.getString("device_seq_id"));
				response.setDoneBy(resultSet.getString("done_by"));
				response.setFacilityId(resultSet.getString("facility_id"));
				response.setId(resultSet.getLong("id"));
				response.setInitialOfIncharge(resultSet.getString("initial_of_incharge"));
				response.setLastUpdatedStamp(resultSet.getTimestamp("last_updated_stamp"));
				response.setLastUpdatedTxStamp(resultSet.getTimestamp("last_updated_tx_stamp"));
				response.setPbOperationSeqId(resultSet.getString("pb_operation_seq_id"));
				response.setRemarks(resultSet.getString("remarks"));
				response.setScheduleCode(resultSet.getString("schedule_code"));
				response.setScheduleDate(resultSet.getTimestamp("schedule_date"));
				response.setSeqId(resultSet.getString("seq_id"));
				response.setStatus(resultSet.getString("status"));
				
				
				
				
				//list.add(response);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			closeJDBCObjects.releaseResouces(con, psPreparedStatement, resultSet);
		}
		return response;
	}
	public static final String SQL_SELECT_ASH_MEASURES_MAKE_MODEL=  " SELECT asa.asset_type, asa.schedule_code,asaa.activity_id,asaa.activity_position_id, mal.activity_name," +
            " asaa.make_code, asaa.model_code,  asaa.lower_limit, asaa.upper_limit, asaa.description,asaa.seq_id,asaa.sub_asset_type" +
            " FROM asset_schedule_activity_assoc asaa, asset_schedule_assoc asa, measure_or_activity_list mal" +
            " WHERE asaa.asa_seq_id = asa.asa_seq_id AND asaa.activity_id = mal.activity_id" +
            " AND mal.activity_type = ?" +
            " AND asa.asset_type = ? AND asa.schedule_code = ? AND asaa.make_code =?  AND asaa.model_code =? AND asaa.activity_flag = 'yes' ORDER BY asaa.display_order ASC";
	public static final String SQL_SELECT_ASH_MEASURES_MAKE=  " SELECT asa.asset_type, asa.schedule_code,asaa.activity_id,asaa.activity_position_id, mal.activity_name," +
            " asaa.make_code, asaa.model_code,  asaa.lower_limit, asaa.upper_limit, asaa.description,asaa.seq_id,asaa.sub_asset_type" +
            " FROM asset_schedule_activity_assoc asaa, asset_schedule_assoc asa, measure_or_activity_list mal" +
            " WHERE asaa.asa_seq_id = asa.asa_seq_id AND asaa.activity_id = mal.activity_id" +
            " AND mal.activity_type = ?" +
            " AND asa.asset_type = ? AND asa.schedule_code = ? AND asaa.make_code =?  AND asaa.activity_flag = 'yes' ORDER BY asaa.display_order ASC";
	public static final String SQL_SELECT_ASH_MEASURES=  " SELECT asa.asset_type, asa.schedule_code,asaa.activity_id,asaa.activity_position_id, mal.activity_name," +
            " asaa.make_code, asaa.model_code,  asaa.lower_limit, asaa.upper_limit, asaa.description,asaa.seq_id,asaa.sub_asset_type" +
            " FROM asset_schedule_activity_assoc asaa, asset_schedule_assoc asa, measure_or_activity_list mal" +
            " WHERE asaa.asa_seq_id = asa.asa_seq_id AND asaa.activity_id = mal.activity_id" +
            " AND mal.activity_type = ?" +
            " AND asa.asset_type = ? AND asa.schedule_code = ? AND asaa.activity_flag = 'yes' ORDER BY asaa.display_order ASC";
	public List<ASHMeasuresActvities> findMeasuresOrActivities(String type,String assetType, String scheduleCode, String make, String model) {
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		ASHMeasuresActvities response = null;
		List<ASHMeasuresActvities> measuresList=null;
		try {
			con = dataSource.getConnection();
				psPreparedStatement = con.prepareStatement(SQL_SELECT_ASH_MEASURES_MAKE_MODEL,ResultSet.TYPE_SCROLL_INSENSITIVE,
					    ResultSet.CONCUR_READ_ONLY);
				psPreparedStatement.setString(1, type);
				psPreparedStatement.setString(2, assetType);
				psPreparedStatement.setString(3, scheduleCode);
				psPreparedStatement.setString(4, make);
				psPreparedStatement.setString(5, model);
			resultSet = psPreparedStatement.executeQuery();
			
			int rowcount = 0;
			if (resultSet.last()) {
			  rowcount = resultSet.getRow();
			  logger.info("rowcount with make model:::"+rowcount);
			  resultSet.beforeFirst(); 
			}
			if(rowcount==0) {
				resultSet.close();
				psPreparedStatement = con.prepareStatement(SQL_SELECT_ASH_MEASURES_MAKE,ResultSet.TYPE_SCROLL_INSENSITIVE,
					    ResultSet.CONCUR_READ_ONLY);
				psPreparedStatement.setString(1, type);
				psPreparedStatement.setString(2, assetType);
				psPreparedStatement.setString(3, scheduleCode);
				psPreparedStatement.setString(4, make);
				resultSet = psPreparedStatement.executeQuery();
				logger.info("rowcount with make :::"+rowcount);
				if (resultSet.last()) {
					  rowcount = resultSet.getRow();
					  logger.info("rowcount with make :::"+rowcount);
					  resultSet.beforeFirst(); 
					}
				if(rowcount==0) {
					resultSet.close();
					psPreparedStatement = con.prepareStatement(SQL_SELECT_ASH_MEASURES,ResultSet.TYPE_SCROLL_INSENSITIVE,
						    ResultSet.CONCUR_READ_ONLY);
					psPreparedStatement.setString(1, type);
					psPreparedStatement.setString(2, assetType);
					psPreparedStatement.setString(3, scheduleCode);
					resultSet = psPreparedStatement.executeQuery();
					logger.info("rowcount  :::"+rowcount);
				}
				
			}
			measuresList=new ArrayList<>();
			
			
			while(resultSet != null && resultSet.next()) {
				response = new ASHMeasuresActvities();
				//response.setReq_date(resultSet.getDate("req_date"));
				//response.setAssetId(resultSet.getString("asset_id"));
				response.setAssetType(resultSet.getString("asset_type"));				
				//response.setFacilityId(resultSet.getString("facility_id"));
				//response.setId(resultSet.getLong("id"));
				response.setSeqId(resultSet.getString("seq_id"));
				
				response.setActivityId(resultSet.getString("activity_id"));
				response.setActivityPositionId(resultSet.getString("activity_position_id"));				
				response.setActivityName(resultSet.getString("activity_name"));
				response.setMake(resultSet.getString("make_code"));
				response.setModel(resultSet.getString("model_code"));				
				response.setLowerLimit(resultSet.getString("lower_limit"));
				response.setUpperLimit(resultSet.getString("upper_limit"));
				response.setDescription(resultSet.getString("description"));				
				response.setSubAssetType(resultSet.getString("sub_asset_type"));
			
				measuresList.add(response);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			closeJDBCObjects.releaseResouces(con, psPreparedStatement, resultSet);
		}
		return measuresList;
	}

}