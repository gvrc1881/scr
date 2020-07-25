
package com.scr.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.response.EnergyConsumptionResponse;
@Component
public class EnergyConsumptionRepository{

	private static final Logger logger = Logger.getLogger(EnergyConsumptionRepository.class);

	@Autowired
	DataSource dataSource;
	
	String exactDateQuery = "select req_date, feeder_id , feeder_name , multiplication_fac ,requested_reading_date ,first_reading_after_meter_fix, meter_start_date ,recent_reading_date, no_of_days_lapsed_reading," + 
			"case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||" + 
			"case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_kwh as prev_kwh, cur_kwh," + 
			"case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||" + 
			"case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_kvah as prev_kvah, cur_kvah," + 
			"case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||" + 
			"case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag," + 
			"case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||" + 
			"case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead," + 
			"cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load " + 
			"from " + 
			"(" + 
			"SELECT req_date," + 
			"	a.feeder_id , a.feeder_name , a.multiplication_fac , req_date as requested_reading_date, rec.em_start_kwh rec_em_start_kwh," + 
			"    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix, " + 
			"    case when rec.energy_reading_date is null then a.em_start_date::date end as meter_start_date ," + 
			"	rec.energy_reading_date AS recent_reading_date," + 
			"    (req_date::date - case when rec.energy_reading_date is null then a.em_start_date::Date else rec.energy_reading_date::Date end)-1  AS no_of_days_lapsed_reading ," + 
			"    cur.seq_id, cur.id ," + 
			"    cur.location cur_location, cur.feeder_id cur_feeder_id," + 
			"    cur.energy_reading_date AS cuurent_reading_date," + 
			"    cur.kwh AS cur_kwh," + 
			"    cur.kvah AS cur_kvah," + 
			"    cur.rkvah_lag AS cur_rkvah_lag," + 
			"    cur.rkvah_lead AS cur_rkvah_lead," + 
			"    cur.cmd cur_cmd," + 
			"    cur.rmd cur_rmd," + 
			"    cur.vol_max  cur_vol_max," + 
			"    cur.vol_min cur_vol_min," + 
			"    cur.max_load cur_max_load," + 
			"    cur.max_load_time_hhmm," + 
			"    cur.max_load_time_date," + 
			"    rec.seq_id, rec.id ," + 
			"    rec.location, rec.feeder_id rec_feeder_id," + 
			"    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh," + 
			"    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah," + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag," + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead," + 
			"    rec.cmd recent_cmd," + 
			"    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load," + 
			"    rec.max_load_time_hhmm recent_max_load_time_hhmm," + 
			"    rec.max_load_time_date recent_max_load_time_date ," + 
			"   rec.multiplication_fac recent_multiplication_fac	   " + 
			"   FROM " + 
			"   (select req_date , em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , " + 
			"	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ," + 
			"	 feeder_name from v_energy_meter em , (select ?::date req_date ) dt )  a" + 
			"   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max" + 
			"					FROM v_energy_consumption cur1" + 
			"					WHERE cur1.energy_reading_date < a.req_date" + 
			"					AND cur1.feeder_id = a.feeder_id " + 
			"					)" + 
			"	AND rec.feeder_id = a.feeder_id " + 
			"    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = a.req_date" + 
			"						AND a.feeder_id = cur.feeder_id )" + 
			"" + 
			") final";
	
	String betweenDatesQuery = "select energy_consume_date as req_date, feeder_id , feeder_name , multiplication_fac ,requested_reading_date ,first_reading_after_meter_fix, meter_start_date ,recent_reading_date, no_of_days_lapsed_reading," + 
			"case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||" + 
			"case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_kwh as prev_kwh, cur_kwh," + 
			"case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||" + 
			"case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_kvah as prev_kvah, cur_kvah," + 
			"case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||" + 
			"case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag," + 
			"case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||" + 
			"case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead," + 
			"cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load " + 
			"from " + 
			"(" + 
			"SELECT " + 
			"energy_consume_date ," + 
			"	a.feeder_id , a.feeder_name , a.multiplication_fac , energy_consume_date as requested_reading_date, rec.em_start_kwh rec_em_start_kwh," + 
			"    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix, " + 
			"    case when rec.energy_reading_date is null then a.em_start_date::date end as meter_start_date ," + 
			"    rec.energy_reading_date AS recent_reading_date," + 
			"    (energy_consume_date::date - case when rec.energy_reading_date is null then a.em_start_date::Date else rec.energy_reading_date::Date end)-1  AS no_of_days_lapsed_reading ," + 
			"   cur.seq_id, cur.id ," + 
			"    cur.location cur_location, cur.feeder_id cur_feeder_id," + 
			"    cur.energy_reading_date AS cuurent_reading_date," + 
			"    cur.kwh AS cur_kwh," + 
			"    cur.kvah AS cur_kvah," + 
			"    cur.rkvah_lag AS cur_rkvah_lag," + 
			"    cur.rkvah_lead AS cur_rkvah_lead," + 
			"    cur.cmd cur_cmd," + 
			"    cur.rmd cur_rmd," + 
			"    cur.vol_max  cur_vol_max," + 
			"    cur.vol_min cur_vol_min," + 
			"    cur.max_load cur_max_load," + 
			"    cur.max_load_time_hhmm," + 
			"    cur.max_load_time_date," + 
			"    rec.seq_id, rec.id , rec.energy_reading_date recent_energy_reading_Date ," + 
			"    rec.location, rec.feeder_id rec_feeder_id," + 
			"    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh," + 
			"    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah," + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag," + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead," + 
			"    rec.cmd recent_cmd," + 
			"    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load," + 
			"    rec.max_load_time_hhmm recent_max_load_time_hhmm," + 
			"    rec.max_load_time_date recent_max_load_time_date ," + 
			"   rec.multiplication_fac recent_multiplication_fac" + 
			"   FROM" + 
			"   ( " + 
			"   select energy_consume_date, em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , " + 
			"	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ," + 
			"	 feeder_name from v_energy_meter em ," + 
			"	 (select generate_series(?::date,  ?::date, interval '1 day')::date as energy_consume_date )dt " + 
			"	where feeder_id = ? " + 
			"	 )  a" + 
			"   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max" + 
			"					FROM v_energy_consumption cur1" + 
			"					WHERE cur1.energy_reading_date < a.energy_consume_date" + 
			"					AND cur1.feeder_id = a.feeder_id " + 
			"					)" + 
			"	AND rec.feeder_id = a.feeder_id " + 
			"    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = a.energy_consume_date" + 
			"						AND a.feeder_id = cur.feeder_id )" + 
			"" + 
			") final" + 
			" order by requested_reading_date";
	
	public List<EnergyConsumptionResponse> findEnergyConsumption(String fromDate, String toDate, String feederId) {
		List<EnergyConsumptionResponse> list = new ArrayList<EnergyConsumptionResponse>();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		try {
			logger.info("ps");
			con = dataSource.getConnection();
			if(toDate.equalsIgnoreCase("exact")) {
				psPreparedStatement = con.prepareStatement(exactDateQuery);
				psPreparedStatement.setString(1, fromDate);
			}else {
				psPreparedStatement = con.prepareStatement(betweenDatesQuery);
				psPreparedStatement.setString(1, fromDate);
				psPreparedStatement.setString(2, toDate);
				psPreparedStatement.setString(3, feederId);
			}
			resultSet = psPreparedStatement.executeQuery();
			logger.info("hello");
			EnergyConsumptionResponse response = null;
			while(resultSet != null && resultSet.next()) {
				logger.info("feeder_id = "+resultSet.getString("feeder_id"));
				response = new EnergyConsumptionResponse();
				response.setReq_date(resultSet.getDate("req_date"));
				response.setFeeder_id(resultSet.getString("feeder_id"));
				response.setFeeder_name(resultSet.getString("feeder_name"));
				response.setMultiplication_fac(resultSet.getDouble("multiplication_fac"));
				response.setRequested_reading_date(resultSet.getDate("requested_reading_date"));
				response.setFirst_reading_after_meter_fix(resultSet.getString("first_reading_after_meter_fix"));
				response.setMeter_start_date(resultSet.getDate("meter_start_date"));
				response.setRecent_reading_date(resultSet.getDate("recent_reading_date"));
				response.setNo_of_days_lapsed_reading(resultSet.getInt("no_of_days_lapsed_reading"));
				response.setPrev_kwh(resultSet.getString("prev_kwh"));
				response.setCur_kwh(resultSet.getDouble("cur_kwh"));
				response.setPrev_kvah(resultSet.getString("prev_kvah"));
				response.setCur_kvah(resultSet.getDouble("cur_kvah"));
				response.setPrev_rkvah_lag(resultSet.getString("prev_rkvah_lag"));
				response.setCur_rkvah_lag(resultSet.getDouble("cur_rkvah_lag"));
				response.setPrev_rkvah_lead(resultSet.getString("prev_rkvah_lead"));
				response.setCur_rkvah_lead(resultSet.getDouble("cur_rkvah_lead"));
				response.setCur_cmd(resultSet.getDouble("cur_cmd"));
				response.setCur_rmd(resultSet.getDouble("cur_rmd"));
				response.setCur_vol_max(resultSet.getDouble("cur_vol_max"));
				response.setCur_vol_min(resultSet.getDouble("cur_vol_min"));
				response.setCur_max_load(resultSet.getDouble("cur_max_load"));
				
				list.add(response);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			 if (resultSet != null) {
        try {
            if (!resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    if (psPreparedStatement != null) {
        try {
            if (!psPreparedStatement.isClosed()) {
                psPreparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    if (con != null) {
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
		}
		return list;
	}
	
}