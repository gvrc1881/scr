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
				callableStatement = con.prepareCall("{call tcp_measure_v_func_retest(?,?)}");
				logger.info("*** id *** = "+Integer.parseInt(facilityId));
				logger.info("*** date *** = "+Helper.convertStringToDate(date));
				callableStatement.setInt(1, Integer.parseInt(facilityId));
				callableStatement.setDate(2, Helper.convertStringToDate(date));
				resultSet = callableStatement.executeQuery();
				ThermovisionMeasureResponse response = null;
				while(resultSet != null && resultSet.next()) {
					response = new ThermovisionMeasureResponse();
					response.setTcpmCriticality(resultSet.getString("tcpm_criticality"));
					response.setTcpmMeasurePoint1(resultSet.getDouble("tcpm_measure_point1"));
					response.setTcpmMeasurePoint2(resultSet.getDouble("tcpm_measure_point2"));
					response.setPre1MTcpmMeasurePoint1(resultSet.getDouble("pre1_m_tcpm_measure_point1"));
					response.setPre1MTcpmMeasurePoint2(resultSet.getDouble("pre1_m_tcpm_measure_point2"));
					response.setPre1MTcpsDate(resultSet.getTimestamp("pre1_m_tcps_date"));
					response.setPre2MTcpmMeasurePoint1(resultSet.getDouble("pre2_m_tcpm_measure_point1"));
					response.setPre2MTcpmMeasurePoint2(resultSet.getDouble("pre2_m_tcpm_measure_point2"));
					response.setPre2MTcpsDate(resultSet.getTimestamp("pre2_m_tcps_date"));
					response.setPre3MTcpmMeasurePoint1(resultSet.getDouble("pre3_m_tcpm_measure_point1"));
					response.setPre3MTcpmMeasurePoint2(resultSet.getDouble("pre3_m_tcpm_measure_point2"));
					response.setPre3MTcpsDate(resultSet.getTimestamp("pre3_m_tcps_date"));
					response.setRsFacilityId(resultSet.getString("rs_facility_id"));
					response.setRsFacilityName(resultSet.getString("rs_facility_name"));
					response.setRsId(resultSet.getString("rs_id"));
					response.setTcpCheckPoint1Description(resultSet.getString("tcp_check_point1_description"));
					response.setTcpCheckPoint2Description(resultSet.getString("tcp_check_point2_description"));
					response.setTcpCheckPointPart(resultSet.getString("tcp_check_point_part"));
					response.setTcpDisplayGroup(resultSet.getString("tcp_display_group"));
					response.setTcpDisplayOrder(resultSet.getString("tcp_display_order"));
					response.setTcpmId(resultSet.getLong("tcpm_id"));
					response.setTcpmImageId(resultSet.getString("tcpm_image_id"));
					//response.setTcpmMeasure(resultSet.getString("tcpm_measure"));
					response.setTcpmRemark(resultSet.getString("tcpm_remark"));
					response.setTcpsBy(resultSet.getString("tcps_by"));
					response.setTcpsDate(resultSet.getTimestamp("tcps_date"));
					response.setTcpsDateTime(resultSet.getTimestamp("tcps_date_time"));
					response.setTcpsFacilityId(resultSet.getLong("tcps_facility_id"));
					response.setTcpsFacilityName(resultSet.getString("tcps_facility_name"));
					response.setTcpsGeneralRemark(resultSet.getString("tcps_general_remark"));
					response.setTcpsTime(resultSet.getString("tcps_time"));
					response.setfDiff(resultSet.getDouble("f_diff"));
					response.setTcpmDateOfRetest(resultSet.getDate("tcpm_date_of_retest"));
					response.setTcpmThermovisionMeasureId(resultSet.getLong("tcpm_thermovision_measure_id"));
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
