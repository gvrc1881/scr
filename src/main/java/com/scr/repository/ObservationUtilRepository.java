
package com.scr.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scr.message.response.ObservationResponse;
import com.scr.util.CloseJDBCObjects;
@Component
public class ObservationUtilRepository{

	private static final Logger logger = Logger.getLogger(EnergyConsumptionRepository.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private CloseJDBCObjects closeJDBCObjects;
	
	
	
	String obsQ = "select ins.from_date_time , ins.section , ins.name_of_staff , ins.facility_id , obs.location , ins.inspection_seq_id , obs.seq_id \r\n" + 
			"from observations obs , inspection ins \r\n" + 
			"where obs.inspection_seq_id = ins.inspection_seq_id and type_of_work ='FOOT_PATROLLING' ";
	public List<ObservationResponse> findObservation(String section, String facilityId, String nameOfStaff, String fromDateTime) {
		List<ObservationResponse> list = new ArrayList<ObservationResponse>();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = dataSource.getConnection();
			psPreparedStatement = con.prepareStatement(obsQ);
			resultSet = psPreparedStatement.executeQuery();
			ObservationResponse response = null;
			while(resultSet != null && resultSet.next()) {
				logger.info("facility_id = "+resultSet.getString("facility_id"));
				response = new ObservationResponse();
				//response.setReq_date(resultSet.getDate("req_date"));
				response.setCurrentStatus(resultSet.getString("current_status"));
				response.setCreatedBy(resultSet.getString("created_by"));
				response.setCreatedOn(resultSet.getTimestamp("created_on"));
				response.setCreatedStamp(resultSet.getTimestamp("created_stamp"));
				response.setCreatedTxStamp(resultSet.getTimestamp("created_tx_stamp"));
				response.setDataDiv(resultSet.getString("data_div"));
				response.setInspectionSeqId(resultSet.getString("inspection_seq_id"));
				response.setDeviceId(resultSet.getString("device_id"));
				response.setDeviceSeqId(resultSet.getString("device_seq_id"));
				response.setSection(resultSet.getString("section"));
				response.setFacilityId(resultSet.getString("facility_id"));
				response.setId(resultSet.getLong("id"));
				response.setNameOfStaff(resultSet.getString("name_of_staff"));
				response.setFromDateTime(resultSet.getTimestamp("from_date_time"));
				response.setLocation(resultSet.getString("location"));
				response.setObservationCategory(resultSet.getString("observation_category"));
				response.setObservationItem(resultSet.getString("observation_item"));
				response.setActionRequired(resultSet.getString("action_required"));
				response.setDescription(resultSet.getString("description"));
			
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