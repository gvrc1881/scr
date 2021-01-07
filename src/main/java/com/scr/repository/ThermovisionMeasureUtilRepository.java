package com.scr.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.response.ThermovisionMeasureResponse;
import com.scr.util.CloseJDBCObjects;
import com.scr.util.Helper;

@Component
public class ThermovisionMeasureUtilRepository {
	
	private static final Logger logger = Logger.getLogger(ThermovisionMeasureUtilRepository.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private CloseJDBCObjects closeJDBCObjects;

	public List<ThermovisionMeasureResponse> findThermovisionMeasure(String date, String facilityId) {
		List<ThermovisionMeasureResponse> list = new ArrayList<ThermovisionMeasureResponse>();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		CallableStatement callableStatement = null;
		try {
			con = dataSource.getConnection();
				callableStatement = con.prepareCall("{call tcp_measure_v_func(?,?)}");
				logger.info("*** id *** = "+Integer.parseInt(facilityId));
				logger.info("*** date *** = "+Helper.convertStringToDate(date));
				callableStatement.setInt(1, Integer.parseInt(facilityId));
				callableStatement.setDate(2, Helper.convertStringToDate(date));
				resultSet = callableStatement.executeQuery();
				ThermovisionMeasureResponse response = null;
				while(resultSet != null && resultSet.next()) {
					response = new ThermovisionMeasureResponse();
					response.setTcpmCriticality(resultSet.getLong("tcpm_criticality"));
					response.setPre1MTcpmMeasure(resultSet.getString("pre1_m_tcpm_measure"));
					response.setPre1MTcpsDate(resultSet.getTimestamp("pre1_m_tcps_date"));
					response.setPre2MTcpmMeasure(resultSet.getString("pre2_m_tcpm_measure"));
					response.setPre2MTcpsDate(resultSet.getTimestamp("pre2_m_tcps_date"));
					response.setPre3MTcpmMeasure(resultSet.getString("pre3_m_tcpm_measure"));
					response.setPre3MTcpsDate(resultSet.getTimestamp("pre3_m_tcps_date"));
					response.setRsFacilityId(resultSet.getString("rs_facility_id"));
					response.setRsFacilityName(resultSet.getString("rs_facility_name"));
					response.setRsId(resultSet.getString("rs_id"));
					response.setTcpCheckPointDescription(resultSet.getString("tcp_check_point_description"));
					response.setTcpCheckPointPart(resultSet.getString("tcp_check_point_part"));
					response.setTcpCommparisonPoints(resultSet.getLong("tcp_commparison_points"));
					response.setTcpDisplayGroup(resultSet.getString("tcp_display_group"));
					response.setTcpDisplayOrder(resultSet.getString("tcp_display_order"));
					response.setTcpmAmbientTemp(resultSet.getDouble("tcpm_ambient_temp"));
					response.setTcpmId(resultSet.getLong("tcpm_id"));
					response.setTcpmImageId(resultSet.getString("tcpm_image_id"));
					response.setTcpmMeasure(resultSet.getString("tcpm_measure"));
					response.setTcpmRemark(resultSet.getString("tcpm_remark"));
					response.setTcpmVarianceWithOtherPoint(resultSet.getDouble("tcpm_variance_with_other_point"));
					response.setTcpsBy(resultSet.getString("tcps_by"));
					response.setTcpsDate(resultSet.getTimestamp("tcps_date"));
					response.setTcpsDateTime(resultSet.getTimestamp("tcps_date_time"));
					response.setTcpsFacilityId(resultSet.getLong("tcps_facility_id"));
					response.setTcpsFacilityName(resultSet.getString("tcps_facility_name"));
					response.setTcpsGeneralRemark(resultSet.getString("tcps_general_remark"));
					response.setTcpsTime(resultSet.getString("tcps_time"));
					response.setTcpTypeOfCheckPoint(resultSet.getString("tcp_type_of_check_point"));
					list.add(response);
				}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			closeJDBCObjects.releaseResouces(con, psPreparedStatement, resultSet);
		}
		
		return list;
	}

}
