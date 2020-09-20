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

}