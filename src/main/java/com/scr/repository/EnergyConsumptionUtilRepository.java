
package com.scr.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.CollaboratingWorkbooksEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.response.EnergyConsumptionResponse;
import com.scr.util.CloseJDBCObjects;
import com.scr.util.Helper;
@Component
public class EnergyConsumptionUtilRepository{

	private static final Logger logger = Logger.getLogger(EnergyConsumptionRepository.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private CloseJDBCObjects closeJDBCObjects;
	
	/*
	 * String exactDateQuery =
	 * "select req_date, feeder_id , feeder_name , multiplication_fac ,requested_reading_date ,first_reading_after_meter_fix, meter_start_date ,recent_reading_date, no_of_days_lapsed_reading,"
	 * +
	 * "case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||"
	 * +
	 * "case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_kwh as prev_kwh, cur_kwh,"
	 * +
	 * "case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||"
	 * +
	 * "case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_kvah as prev_kvah, cur_kvah,"
	 * +
	 * "case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||"
	 * +
	 * "case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag,"
	 * +
	 * "case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||"
	 * +
	 * "case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead,"
	 * + "cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load " + "from " +
	 * "( " + " SELECT req_date," +
	 * "	a.feeder_id , a.feeder_name , a.multiplication_fac , req_date as requested_reading_date, rec.em_start_kwh rec_em_start_kwh,"
	 * +
	 * "    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix, "
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_date::date end as meter_start_date ,"
	 * + "	rec.energy_reading_date AS recent_reading_date," +
	 * "    (req_date::date - case when rec.energy_reading_date is null then a.em_start_date::Date else rec.energy_reading_date::Date end)-1  AS no_of_days_lapsed_reading ,"
	 * + "    cur.seq_id, cur.id ," +
	 * "    cur.location cur_location, cur.feeder_id cur_feeder_id," +
	 * "    cur.energy_reading_date AS cuurent_reading_date," +
	 * "    cur.kwh AS cur_kwh," + "    cur.kvah AS cur_kvah," +
	 * "    cur.rkvah_lag AS cur_rkvah_lag," +
	 * "    cur.rkvah_lead AS cur_rkvah_lead," + "    cur.cmd cur_cmd," +
	 * "    cur.rmd cur_rmd," + "    cur.vol_max  cur_vol_max," +
	 * "    cur.vol_min cur_vol_min," + "    cur.max_load cur_max_load," +
	 * "    cur.max_load_time_hhmm," + "    cur.max_load_time_date," +
	 * "    rec.seq_id, rec.id ," + "    rec.location, rec.feeder_id rec_feeder_id,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,"
	 * + "    rec.cmd recent_cmd," +
	 * "    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,"
	 * + "    rec.max_load_time_hhmm recent_max_load_time_hhmm," +
	 * "    rec.max_load_time_date recent_max_load_time_date ," +
	 * "   rec.multiplication_fac recent_multiplication_fac	   " + "   FROM " +
	 * "   (select req_date , em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , "
	 * +
	 * "	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,"
	 * +
	 * "	 feeder_name from v_energy_meter em , (select ?::date req_date ) dt )  a"
	 * +
	 * "   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max"
	 * + "					FROM v_energy_consumption cur1" +
	 * "					WHERE cur1.energy_reading_date < a.req_date" +
	 * "					AND cur1.feeder_id = a.feeder_id " +
	 * "					)" + "	AND rec.feeder_id = a.feeder_id " +
	 * "    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = a.req_date"
	 * + "						AND a.feeder_id = cur.feeder_id )" + "" + ") final";
	 * 
	 * String date =
	 * "select  feeder_id , feeder_name , multiplication_fac ,requested_reading_date ,first_reading_after_meter_fix, meter_start_date ,recent_reading_date, no_of_days_lapsed_reading,"
	 * +
	 * " recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || recent_kwh as prev_kwh, cur_kwh,"
	 * +
	 * " recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || recent_kvah as prev_kvah, cur_kvah,"
	 * +
	 * " recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag,"
	 * +
	 * " recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead,"
	 * + " cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load,joint_meter" +
	 * " from " + " ( " + " SELECT " +
	 * "	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(?::date-12, 'dd-mm-yy') as requested_reading_date, rec.em_start_kwh rec_em_start_kwh,"
	 * +
	 * "    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix, "
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_date::date end as meter_start_date ,    "
	 * +
	 * "    ?::date-12 - case when rec.energy_reading_date is null then a.em_start_date else rec.energy_reading_date end AS recent_reading_date,"
	 * +
	 * "    ?::date-12 - case when rec.energy_reading_date is null then a.em_start_date else rec.energy_reading_date end AS no_of_days_lapsed_reading,"
	 * + "    cur.seq_id, cur.id ," +
	 * "    cur.location cur_location, cur.feeder_id cur_feeder_id," +
	 * "    cur.energy_reading_date AS cuurent_reading_date," +
	 * "    cur.joint_meter ," + "    cur.kwh AS cur_kwh," +
	 * "    cur.kvah AS cur_kvah," + "    cur.rkvah_lag AS cur_rkvah_lag," +
	 * "    cur.rkvah_lead AS cur_rkvah_lead," + "    cur.cmd cur_cmd," +
	 * "    cur.rmd cur_rmd," + "    cur.vol_max  cur_vol_max," +
	 * "    cur.vol_min cur_vol_min," + "    cur.max_load cur_max_load," +
	 * "    cur.max_load_time_hhmm," + "    cur.max_load_time_date," +
	 * "    rec.seq_id, rec.id ," + "    rec.location, rec.feeder_id rec_feeder_id,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,"
	 * + "    rec.cmd recent_cmd," +
	 * "    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,"
	 * + "    rec.max_load_time_hhmm recent_max_load_time_hhmm," +
	 * "    rec.max_load_time_date recent_max_load_time_date ," +
	 * "   rec.multiplication_fac recent_multiplication_fac " + "   FROM ( " +
	 * "   select em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , "
	 * +
	 * "	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,"
	 * +
	 * "	 feeder_name from v_energy_meter em where upper(em_data_div) = upper(?)  )  a"
	 * +
	 * "   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max"
	 * + "					FROM v_energy_consumption cur1" +
	 * "					WHERE cur1.energy_reading_date < ?::date-12" +
	 * "					AND cur1.feeder_id = a.feeder_id " + " )" +
	 * "	AND rec.feeder_id = a.feeder_id " +
	 * "    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = ?::date -12"
	 * + "						AND a.feeder_id = cur.feeder_id )" + "" + ") final";
	 * 
	 * String dateQuery =
	 * " select  feeder_id , feeder_name , multiplication_fac ,requested_reading_date ,  joint_meter , "
	 * +
	 * "first_reading_after_meter_fix, meter_start_date ,recent_reading_date, no_of_days_lapsed_reading,"
	 * +
	 * "recent_reading_date as prev_reading_date , 'Gap(' ||   no_of_days_lapsed_reading ||')'  reading_gap_days,"
	 * + "recent_kwh as prev_kwh, cur_kwh," + "recent_kvah as prev_kvah, cur_kvah,"
	 * + "recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag," +
	 * "recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead," +
	 * "cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load ,joint_meter " +
	 * " from " + " ( " + " SELECT " +
	 * "	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(?::date, 'dd-mm-yy') as requested_reading_date, rec.em_start_kwh rec_em_start_kwh,"
	 * +
	 * "    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix, "
	 * + "  a.em_start_date::date  as meter_start_date , " +
	 * "    to_char(case when rec.energy_reading_date is null then a.em_start_date::date else rec.energy_reading_date::date end,'dd-mm-yy')  AS recent_reading_date,"
	 * +
	 * "    ?::date - case when rec.energy_reading_date is null then a.em_start_date else rec.energy_reading_date end AS no_of_days_lapsed_reading,"
	 * + "    cur.seq_id, cur.id ," +
	 * "    cur.location cur_location, cur.feeder_id cur_feeder_id," +
	 * "    cur.energy_reading_date AS cuurent_reading_date," +
	 * "    cur.joint_meter ," + "    cur.kwh AS cur_kwh," +
	 * "    cur.kvah AS cur_kvah," + "    cur.rkvah_lag AS cur_rkvah_lag," +
	 * "    cur.rkvah_lead AS cur_rkvah_lead," + "    cur.cmd cur_cmd," +
	 * "    cur.rmd cur_rmd," + "    cur.vol_max  cur_vol_max," +
	 * "    cur.vol_min cur_vol_min," + "    cur.max_load cur_max_load," +
	 * "    cur.max_load_time_hhmm," + "    cur.max_load_time_date," +
	 * "    rec.seq_id, rec.id ," + "    rec.location, rec.feeder_id rec_feeder_id,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,"
	 * + "    rec.cmd recent_cmd," +
	 * "    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,"
	 * + "    rec.max_load_time_hhmm recent_max_load_time_hhmm," +
	 * "    rec.max_load_time_date recent_max_load_time_date ," +
	 * "   rec.multiplication_fac recent_multiplication_fac " + "   FROM ( " +
	 * "   select em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , "
	 * +
	 * "	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,"
	 * +
	 * "	 feeder_name from v_energy_meter em where upper(em_data_div) = upper(?)  )  a"
	 * +
	 * "   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max"
	 * + "					FROM v_energy_consumption cur1" +
	 * "					WHERE cur1.energy_reading_date < ?::date" +
	 * "					AND cur1.feeder_id = a.feeder_id " +
	 * "					)" + "	AND rec.feeder_id = a.feeder_id " +
	 * "    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = ?::date "
	 * + "						AND a.feeder_id = cur.feeder_id )" + ") final";
	 * 
	 * 
	 * String betweenDatesQuery =
	 * "select energy_consume_date as req_date, feeder_id , feeder_name , multiplication_fac ,requested_reading_date ,first_reading_after_meter_fix, meter_start_date ,recent_reading_date, no_of_days_lapsed_reading,"
	 * +
	 * "case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||"
	 * +
	 * "case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_kwh as prev_kwh, cur_kwh,"
	 * +
	 * "case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||"
	 * +
	 * "case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_kvah as prev_kvah, cur_kvah,"
	 * +
	 * "case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||"
	 * +
	 * "case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag,"
	 * +
	 * "case when recent_reading_date is null then meter_start_date else recent_reading_date end || ' : '||"
	 * +
	 * "case when no_of_days_lapsed_reading > 0 then '[Missed (' ||   no_of_days_lapsed_reading ||')]' else ' ' end || recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead,"
	 * + "cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load " + "from " +
	 * " ( " + " SELECT " + " energy_consume_date ," +
	 * "	a.feeder_id , a.feeder_name , a.multiplication_fac , energy_consume_date as requested_reading_date, rec.em_start_kwh rec_em_start_kwh,"
	 * +
	 * "    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix, "
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_date::date end as meter_start_date ,"
	 * + "    rec.energy_reading_date AS recent_reading_date," +
	 * "    (energy_consume_date::date - case when rec.energy_reading_date is null then a.em_start_date::Date else rec.energy_reading_date::Date end)-1  AS no_of_days_lapsed_reading ,"
	 * + "   cur.seq_id, cur.id ," +
	 * "    cur.location cur_location, cur.feeder_id cur_feeder_id," +
	 * "    cur.energy_reading_date AS cuurent_reading_date," +
	 * "    cur.kwh AS cur_kwh," + "    cur.kvah AS cur_kvah," +
	 * "    cur.rkvah_lag AS cur_rkvah_lag," +
	 * "    cur.rkvah_lead AS cur_rkvah_lead," + "    cur.cmd cur_cmd," +
	 * "    cur.rmd cur_rmd," + "    cur.vol_max  cur_vol_max," +
	 * "    cur.vol_min cur_vol_min," + "    cur.max_load cur_max_load," +
	 * "    cur.max_load_time_hhmm," + "    cur.max_load_time_date," +
	 * "    rec.seq_id, rec.id , rec.energy_reading_date recent_energy_reading_Date ,"
	 * + "    rec.location, rec.feeder_id rec_feeder_id," +
	 * "    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,"
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,"
	 * + "    rec.cmd recent_cmd," +
	 * "    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,"
	 * + "    rec.max_load_time_hhmm recent_max_load_time_hhmm," +
	 * "    rec.max_load_time_date recent_max_load_time_date ," +
	 * "   rec.multiplication_fac recent_multiplication_fac" + "   FROM" + "   ( " +
	 * "   select energy_consume_date, em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , "
	 * +
	 * "	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,"
	 * + "	 feeder_name from v_energy_meter em ," +
	 * "	 (select generate_series(?::date,  ?::date, interval '1 day')::date as energy_consume_date )dt "
	 * + "	where feeder_id = ? " + "	 )  a" +
	 * "   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max"
	 * + "					FROM v_energy_consumption cur1" +
	 * "					WHERE cur1.energy_reading_date < a.energy_consume_date"
	 * + "					AND cur1.feeder_id = a.feeder_id " +
	 * "					)" + "	AND rec.feeder_id = a.feeder_id " +
	 * "    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = a.energy_consume_date"
	 * + "						AND a.feeder_id = cur.feeder_id )" + "" + ") final"
	 * + " order by requested_reading_date";
	 * 
	 * String stationQuery =
	 * "select energy_consume_date as req_date, feeder_id , feeder_name , multiplication_fac , joint_meter , requested_reading_date ,first_reading_after_meter_fix, meter_start_date ,recent_reading_date, "
	 * + "no_of_days_lapsed_reading,  " +
	 * "recent_reading_date_string , 'Gap(' ||   no_of_days_lapsed_reading ||')' reading_gap_days,"
	 * + " recent_kwh as prev_kwh, cur_kwh,  " +
	 * "recent_kvah as prev_kvah, cur_kvah,  " +
	 * "recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag,  " +
	 * "recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead,  " +
	 * "cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load   " + "from   " +
	 * "(  " + " SELECT   " + "energy_consume_date ,  " +
	 * "	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(energy_consume_date::date, 'dd-mm-yy')  as requested_reading_date, rec.em_start_kwh rec_em_start_kwh,  "
	 * +
	 * "    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix,   "
	 * +
	 * "    a.em_start_date::date  as meter_start_date ,  to_char(a.em_start_date::date, 'dd-mm-yy')   as meter_start_date_string , "
	 * +
	 * "   rec.energy_reading_date AS recent_reading_date,  to_char(rec.energy_reading_date::date, 'dd-mm-yy')    as   recent_reading_date_string,  "
	 * +
	 * "    (energy_consume_date::date - case when rec.energy_reading_date is null then a.em_start_date::Date else rec.energy_reading_date::Date end)-1  AS no_of_days_lapsed_reading ,  "
	 * + "   cur.seq_id, cur.id ,  " +
	 * "    cur.location cur_location, cur.feeder_id cur_feeder_id,  " +
	 * "    cur.energy_reading_date AS cuurent_reading_date,  " +
	 * "    cur.joint_meter ," + "    cur.kwh AS cur_kwh,  " +
	 * "    cur.kvah AS cur_kvah,  " + "    cur.rkvah_lag AS cur_rkvah_lag,  " +
	 * "    cur.rkvah_lead AS cur_rkvah_lead,  " + "    cur.cmd cur_cmd,  " +
	 * "    cur.rmd cur_rmd,  " + "    cur.vol_max  cur_vol_max,  " +
	 * "    cur.vol_min cur_vol_min,  " + "    cur.max_load cur_max_load,  " +
	 * "    cur.max_load_time_hhmm,  " + "    cur.max_load_time_date,  " +
	 * "    rec.seq_id, rec.id , rec.energy_reading_date recent_energy_reading_Date ,  "
	 * + "    rec.location, rec.feeder_id rec_feeder_id,  " +
	 * "    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,  "
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,  "
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,  "
	 * +
	 * "    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,  "
	 * + "    rec.cmd recent_cmd,  " +
	 * "    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,  "
	 * + "    rec.max_load_time_hhmm recent_max_load_time_hhmm,  " +
	 * "    rec.max_load_time_date recent_max_load_time_date ,  " +
	 * "   rec.multiplication_fac recent_multiplication_fac  " + "   FROM  " +
	 * "   (   " +
	 * "   select energy_consume_date, em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks ,   "
	 * +
	 * "	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,  "
	 * + "	 feeder_name from v_energy_meter em ,  " +
	 * "	 (select generate_series(?::date,  ?::date, interval '1 day')::date as energy_consume_date )dt   "
	 * + "	where feeder_id = ?    " + "	 )  a  " +
	 * "   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max  "
	 * + "		FROM v_energy_consumption cur1  " +
	 * "		WHERE cur1.energy_reading_date < a.energy_consume_date  " +
	 * "		AND cur1.feeder_id = a.feeder_id   " + "		)  " +
	 * "	AND rec.feeder_id = a.feeder_id   " +
	 * "    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = a.energy_consume_date  "
	 * + "			AND a.feeder_id = cur.feeder_id )  " + "  " +
	 * "			) final  " + " order by requested_reading_date";
	 */
	
	String datesQ = "select  feeder_id , feeder_name , multiplication_fac ,requested_reading_date ,  joint_meter , " + 
			" first_reading_after_meter_fix, meter_start_date ,recent_reading_date, no_of_days_lapsed_reading," +
			" curseq_id, curId,  "+
			" recent_reading_date as prev_reading_date , 'Gap(' ||   no_of_days_lapsed_reading ||')'  reading_gap_days," + 
			" recent_kwh as prev_kwh, cur_kwh," + 
			" recent_kvah as prev_kvah, cur_kvah," + 
			" recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag," + 
			" recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead," + 
			" cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load," + 
			" joint_reading_date, no_of_days_lapsed_j_reading, jr_kwh, jr_kvah, jr_rkvah_lag, jr_rkvah_lead, curId, max_load_time_hhmm, remarks" + 
			" from  " + 
			"( " + 
			" SELECT " + 
			"	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(?::date, 'dd-mm-yyyy') as requested_reading_date, rec.em_start_kwh rec_em_start_kwh," + 
			"    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix, " + 
			"  a.em_start_date::date  as meter_start_date ,     " + 
			"    to_char(case when rec.energy_reading_date is null then a.em_start_date::date else rec.energy_reading_date::date end,'dd-mm-yyyy')  AS recent_reading_date," + 
			"    ?::date - case when rec.energy_reading_date is null then a.em_start_date else rec.energy_reading_date end AS no_of_days_lapsed_reading," + 
			"    cur.seq_id curseq_id, cur.id as curId ," + 
			"    cur.location cur_location, cur.feeder_id cur_feeder_id," + 
			"    cur.energy_reading_date AS cuurent_reading_date," + 
			"    cur.joint_meter ," + 
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
			"    cur.max_load_time_date, cur.remarks, " + 
			"    rec.seq_id rec_seq_id, rec.id ," + 
			"    rec.location, rec.feeder_id rec_feeder_id," + 
			"    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh," + 
			"    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah," + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag," + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead," + 
			"    rec.cmd recent_cmd," + 
			"    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load," + 
			//"    rec.max_load_time_hhmm recent_max_load_time_hhmm," + 
			//"    rec.max_load_time_date recent_max_load_time_date ," + 
			"    rec.multiplication_fac recent_multiplication_fac , " + 
			"    jr.energy_reading_date AS joint_reading_date," + 
			"    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading," + 
			"    jr.kwh AS jr_kwh," + 
			"    jr.kvah AS jr_kvah," + 
			"    jr.rkvah_lag AS jr_rkvah_lag," + 
			"    jr.rkvah_lead AS jr_rkvah_lead   " + 
			"   FROM ( " + 
			"   select em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , " + 
			"	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ," + 
			"	 feeder_name from v_energy_meter em where upper(em_data_div) = upper(?)  )  a" + 
			"   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max" + 
			"					FROM v_energy_consumption cur1" + 
			"					WHERE cur1.energy_reading_date < ?::date" + 
			"					AND cur1.feeder_id = a.feeder_id " + 
			"					)" + 
			"	AND rec.feeder_id = a.feeder_id " + 
			"    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = ?::date " + 
			"						AND a.feeder_id = cur.feeder_id )" + 
			"   LEFT JOIN v_energy_consumption jr ON  ( jr.energy_reading_date = ( SELECT max(jr1.energy_reading_date) AS max" + 
			"									FROM v_energy_consumption jr1" + 
			"									WHERE jr1.energy_reading_date < cur.energy_reading_date " + 
			"									AND (jr1.joint_meter = 'y'::bpchar OR jr1.joint_meter = 'Y'::bpchar) " + 
			"									AND jr1.feeder_id = cur.feeder_id " + 
			"									)" + 
			"						AND jr.feeder_id = cur.feeder_id )" + 
			") final";
	String sQuery = "select energy_consume_date as req_date, feeder_id , feeder_name , multiplication_fac , joint_meter , requested_reading_date ,first_reading_after_meter_fix, meter_start_date ,recent_reading_date, " + 
			"no_of_days_lapsed_reading,  " +
			" curseq_id, curId, "+
			"recent_reading_date_string , 'Gap(' ||   no_of_days_lapsed_reading ||')' reading_gap_days," + 
			" recent_kwh as prev_kwh, cur_kwh,  " + 
			"recent_kvah as prev_kvah, cur_kvah,  " + 
			"recent_rkvah_lag as prev_rkvah_lag, cur_rkvah_lag,  " + 
			"recent_rkvah_lead as prev_rkvah_lead, cur_rkvah_lead,  " + 
			"cur_cmd, cur_rmd, cur_vol_max, cur_vol_min  , cur_max_load ," + 
			"joint_reading_date," + 
			"no_of_days_lapsed_j_reading," + 
			"jr_kwh," + 
			"jr_kvah," + 
			"jr_rkvah_lag," + 
			"jr_rkvah_lead ,curId," + 
			"max_load_time_hhmm," + 
			"remarks " + 
			" from   " + 
			"(  " + 
			" SELECT   " + 
			" energy_consume_date ,  " + 
			"	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(energy_consume_date::date, 'dd-mm-yyyy')  as requested_reading_date, rec.em_start_kwh rec_em_start_kwh,  " + 
			"    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix,   " + 
			"    a.em_start_date::date  as meter_start_date ,  to_char(a.em_start_date::date, 'dd-mm-yyyy')   as meter_start_date_string , " + 
			"   rec.energy_reading_date AS recent_reading_date,  to_char(rec.energy_reading_date::date, 'dd-mm-yyyy')    as   recent_reading_date_string,  " + 
			"    (energy_consume_date::date - case when rec.energy_reading_date is null then a.em_start_date::Date else rec.energy_reading_date::Date end)-1  AS no_of_days_lapsed_reading ,  " + 
			"   cur.seq_id curseq_id, cur.id as curId,  " + 
			"    cur.location cur_location, cur.feeder_id cur_feeder_id,  " + 
			"    cur.energy_reading_date AS cuurent_reading_date,  " + 
			"    cur.joint_meter ," + 
			"    cur.kwh AS cur_kwh,  " + 
			"    cur.kvah AS cur_kvah,  " + 
			"    cur.rkvah_lag AS cur_rkvah_lag,  " + 
			"    cur.rkvah_lead AS cur_rkvah_lead,  " + 
			"    cur.cmd cur_cmd,  " + 
			"    cur.rmd cur_rmd,  " + 
			"    cur.vol_max  cur_vol_max,  " + 
			"    cur.vol_min cur_vol_min,  " + 
			"    cur.max_load cur_max_load,  " + 
			"    cur.max_load_time_hhmm,  " + 
			"    cur.max_load_time_date,  cur.remarks, " + 
			"    rec.seq_id rec_seq_id, rec.id , rec.energy_reading_date recent_energy_reading_Date ,  " + 
			"    rec.location, rec.feeder_id rec_feeder_id,  " + 
			"    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,  " + 
			"    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,  " + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,  " + 
			"    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,  " + 
			"    rec.cmd recent_cmd,  " + 
			"    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,  " + 
		//	"    rec.max_load_time_hhmm recent_max_load_time_hhmm,  " + 
		//	"    rec.max_load_time_date recent_max_load_time_date ,  " + 
			"   rec.multiplication_fac recent_multiplication_fac  ," + 
			" " + 
			"    jr.energy_reading_date AS joint_reading_date," + 
			"    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading," + 
			"    jr.kwh AS jr_kwh," + 
			"    jr.kvah AS jr_kvah," + 
			"    jr.rkvah_lag AS jr_rkvah_lag," + 
			"    jr.rkvah_lead AS jr_rkvah_lead" +
			"   FROM  " + 
			"   (   " + 
			"   select energy_consume_date, em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks ,   " + 
			"	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,  " + 
			"	 feeder_name from v_energy_meter em ,  " + 
			"	 (select generate_series(?::date,  ?::date, interval '1 day')::date as energy_consume_date )dt   " + 
			"	where feeder_id = ?    " + 
			"	 )  a  " + 
			"   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max  " + 
			"		FROM v_energy_consumption cur1  " + 
			"		WHERE cur1.energy_reading_date < a.energy_consume_date  " + 
			"		AND cur1.feeder_id = a.feeder_id   " + 
			"		)  " + 
			"	AND rec.feeder_id = a.feeder_id   " + 
			"    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = a.energy_consume_date  " + 
			"			AND a.feeder_id = cur.feeder_id )  " + 
			"   LEFT JOIN v_energy_consumption jr ON  ( jr.energy_reading_date = ( SELECT max(jr1.energy_reading_date) AS max" + 
			"									FROM v_energy_consumption jr1" + 
			"									WHERE jr1.energy_reading_date < cur.energy_reading_date " + 
			"									AND (jr1.joint_meter = 'y'::bpchar OR jr1.joint_meter = 'Y'::bpchar) " + 
			"									AND jr1.feeder_id = cur.feeder_id " + 
			"									)" + 
			"						AND jr.feeder_id = cur.feeder_id )" +
			"			) final  " + 
			" order by requested_reading_date";
	public List<EnergyConsumptionResponse> findEnergyConsumption(String fromDate, String toDate, String feederId, String division) {
		List<EnergyConsumptionResponse> list = new ArrayList<EnergyConsumptionResponse>();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		CallableStatement callableStatement = null;
		try {
			con = dataSource.getConnection();
			if(toDate.equalsIgnoreCase("exact")) {
				//psPreparedStatement = con.prepareStatement(datesQ);
				callableStatement = con.prepareCall("{call day_div_tss_energy_consumption_v(?,?)}");
				callableStatement.setDate(1, Helper.convertStringToDate(fromDate));
				//callableStatement.setString(2, fromDate);
				callableStatement.setString(2, division);
				//callableStatement.setString(4, fromDate);
				//callableStatement.setString(5, fromDate);
				//psPreparedStatement.setString(6, fromDate);
			}else {
				//psPreparedStatement = con.prepareStatement(sQuery);
				callableStatement = con.prepareCall("{call period_tss_energy_consumption_v(?,?,?)}");
				callableStatement.setString(1, feederId);
				callableStatement.setDate(2, Helper.convertStringToDate(fromDate));
				callableStatement.setDate(3, Helper.convertStringToDate(toDate));
				
			}
			resultSet = callableStatement.executeQuery();
			EnergyConsumptionResponse response = null;
			while(resultSet != null && resultSet.next()) {
				logger.info("feeder_id = "+resultSet.getString("feeder_id"));
				response = new EnergyConsumptionResponse();
				response.setFeederId(resultSet.getString("feeder_id"));
				response.setFeederName(resultSet.getString("feeder_name"));
				response.setMultiplicationFac(resultSet.getDouble("multiplication_fac"));
				response.setRequestedReadingDate(resultSet.getString("requested_reading_date"));
				response.setFirstReadingAfterMeterFix(resultSet.getString("first_reading_after_meter_fix"));
				response.setMeterStartDate(resultSet.getDate("meter_start_date"));
				response.setReadingGapDays(resultSet.getString("reading_gap_days"));
				response.setRecentReadingDate(resultSet.getString("recent_reading_date"));
				response.setNoOfDaysLapsedReading(resultSet.getString("no_of_days_lapsed_reading"));
				response.setPrevKwh(resultSet.getString("prev_kwh"));
				response.setCurKwh(resultSet.getDouble("cur_kwh"));
				response.setPrevKvah(resultSet.getString("prev_kvah"));
				response.setCurKvah(resultSet.getDouble("cur_kvah"));
				response.setPrevRkvahLag(resultSet.getString("prev_rkvah_lag"));
				response.setCurRkvahLag(resultSet.getDouble("cur_rkvah_lag"));
				response.setPrevRkvahLead(resultSet.getString("prev_rkvah_lead"));
				response.setCurRkvahLead(resultSet.getDouble("cur_rkvah_lead"));
				response.setCurCmd(resultSet.getDouble("cur_cmd"));
				response.setCurRmd(resultSet.getDouble("cur_rmd"));
				response.setCurVolMax(resultSet.getDouble("cur_vol_max"));
				response.setCurVolMin(resultSet.getDouble("cur_vol_min"));
				response.setCurMaxLoad(resultSet.getDouble("cur_max_load"));
				
				response.setJointMeter(resultSet.getString("joint_meter"));
				
				response.setJointReadingDate(resultSet.getDate("joint_reading_date"));
				response.setNoOfDaysLapsedJReading(resultSet.getInt("no_of_days_lapsed_j_reading"));
				response.setJrKvah(resultSet.getDouble("jr_kvah"));
				response.setJrKwh(resultSet.getDouble("jr_kwh"));
				response.setJrRkvahLag(resultSet.getDouble("jr_rkvah_lag"));
				response.setJrRkvahLead(resultSet.getDouble("jr_rkvah_lead"));
				
				response.setId(resultSet.getLong("curid"));
				response.setMaxLoadTimeHhmm(resultSet.getString("max_load_time_hhmm"));
				response.setRemarks(resultSet.getString("remarks"));				
				response.setDataDiv(division);
				
				response.setJrKwh(resultSet.getDouble("jr_kwh"));
				response.setJrKvah(resultSet.getDouble("jr_kvah"));
				response.setJrRkvahLag(resultSet.getDouble("jr_rkvah_lag"));
				response.setJrRkvahLead(resultSet.getDouble("jr_rkvah_lead"));
				
				response.setMavgReadingDate(resultSet.getString("mavg_reading_date"));
				response.setMavgKwhValue(resultSet.getDouble("mavg_kwh_value"));
				response.setMavgKvahValue(resultSet.getDouble("mavg_kvah_value"));
				response.setMavgRkvahLagValue(resultSet.getDouble("mavg_rkvah_lag_value"));
				response.setMavgRkvahLeadValue(resultSet.getDouble("mavg_rkvah_lead_value"));
				
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