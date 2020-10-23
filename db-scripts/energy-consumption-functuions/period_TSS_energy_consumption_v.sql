--
-- TOC entry 4061 (class 1255 OID 129284)
-- Name: period_tss_energy_consumption_v(character varying, date, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.period_tss_energy_consumption_v(tss_id character varying, from_date date, to_date date) RETURNS TABLE(requested_tss_id character varying, period_from text, period_to text, req_date date, feeder_id character varying, feeder_name character varying, multiplication_fac numeric, requested_reading_date text, no_of_days_lapsed_reading integer, curseq_id character varying, curid bigint, cur_kwh numeric, prev_kwh numeric, consumption numeric, mavg_reading_date date, no_of_days_rec_to_avg_reading integer, mavg_kwh_value numeric, mavg_kvah_value numeric, mavg_rkvah_lag_value numeric, mavg_rkvah_lead_value numeric, avg_kwh numeric, avg_kvah numeric, avg_rkvah_lag numeric, avg_rkvah_lead numeric, first_reading_after_meter_fix text, meter_start_date date, reading_gap_days text, recent_reading_date text, prev_kvah numeric, cur_kvah numeric, prev_rkvah_lag numeric, cur_rkvah_lag numeric, prev_rkvah_lead numeric, cur_rkvah_lead numeric, cur_cmd numeric, cur_rmd numeric, cur_vol_max numeric, cur_vol_min numeric, cur_max_load numeric, joint_meter character varying, joint_reading_date date, no_of_days_lapsed_j_reading integer, jr_kwh numeric, jr_kvah numeric, jr_rkvah_lag numeric, jr_rkvah_lead numeric, max_load_time_hhmm text, remarks character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
   RETURN QUERY 

-- start of query



-- query to get the data for screen -- for one tss for mutliple days 
-- from date and to date



-- query to get the data for screen -- for one tss for mutliple days 
-- from date and to date

select 
a.feeder_id requested_tss_id , 
to_char(from_date,'dd-mm-yyyy')  period_from  , 
to_char(to_date,'dd-mm-yyyy')  period_to , 
a.req_date, 
a.feeder_id , 
a.feeder_name , 
a.multiplication_fac ,  
a.requested_reading_date ,
a.no_of_days_lapsed_reading,
  a.curseq_id, 
  a.curId,
  a.cur_kwh , 
a.prev_kwh ,
 case when a.no_of_days_lapsed_reading > 1 then (a.cur_kwh - a.prev_kwh)*a.multiplication_fac/a.no_of_days_lapsed_reading  else (a.cur_kwh - a.prev_kwh)*a.multiplication_fac end as   consumption ,
    a.mavg_reading_date ,
     a.no_of_days_rec_to_avg_reading, 
     a.mavg_kwh_value, 
     a.mavg_kvah_value, 
     a.mavg_rkvah_lag_value, 
     a.mavg_rkvah_lead_value, 
 a.avg_kwh, 
 a.avg_kvah, 
 a.avg_rkvah_lag,
  a.avg_rkvah_lead, 
 a.first_reading_after_meter_fix,
 a.meter_start_date,
 a.reading_gap_days,
 a.recent_reading_date_string ,
 a.prev_kvah ,
 a.cur_kvah ,
 a.prev_rkvah_lag, 
 a.cur_rkvah_lag,
 a.prev_rkvah_lead, 
 a.cur_rkvah_lead,
  a.cur_cmd, 
 a.cur_rmd, 
 a.cur_vol_max,
 a.cur_vol_min  , 
 a.cur_max_load   ,
 a.joint_meter,
 a.joint_reading_date, 
 a.no_of_days_lapsed_j_reading, 
 a.jr_kwh, 
 a.jr_kvah,
 a.jr_rkvah_lag, 
 a.jr_rkvah_lead ,
 a.max_load_time_hhmm,
	  a.remarks -- added mvag
from
(

select energy_consume_date as req_date, f.feeder_id , f.feeder_name , f.multiplication_fac , f.joint_meter , f.requested_reading_date ,f.first_reading_after_meter_fix, 
f.meter_start_date ,f.recent_reading_date, 
f.no_of_days_lapsed_reading,  
f.curseq_id,  f.curId,   -- added line
f.recent_reading_date_string , 'Gap(' ||   f.no_of_days_lapsed_reading ||')' reading_gap_days,
 recent_kwh as prev_kwh, f.cur_kwh,  
recent_kvah as prev_kvah, f.cur_kvah,  
recent_rkvah_lag as prev_rkvah_lag, f.cur_rkvah_lag,  
recent_rkvah_lead as prev_rkvah_lead, f.cur_rkvah_lead,  

f.cur_cmd, f.cur_rmd, f.cur_vol_max,f. cur_vol_min  , f.cur_max_load   ,
 f.joint_reading_date, f.no_of_days_lapsed_j_reading, f.jr_kwh, f.jr_kvah, f.jr_rkvah_lag, f.jr_rkvah_lead ,
 f.mavg_reading_date , f.no_of_days_rec_to_avg_reading, f.mavg_kwh_value, f.mavg_kvah_value, f.mavg_rkvah_lag_value, f.mavg_rkvah_lead_value, f.avg_kwh, 
 f.avg_kvah, f.avg_rkvah_lag, f.avg_rkvah_lead , f.max_load_time_hhmm,
	  f.remarks-- added mvag
from
(  
SELECT   
energy_consume_date ,  
	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(energy_consume_date::date, 'dd-mm-yyyy')  as requested_reading_date, rec.em_start_kwh rec_em_start_kwh,  -- changed yy to yyyy
    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix,   
    a.em_start_date::date  as meter_start_date ,  to_char(a.em_start_date::date, 'dd-mm-yyyy')   as meter_start_date_string , -- changed yy to yyyy
   rec.energy_reading_date AS recent_reading_date,  to_char(rec.energy_reading_date::date, 'dd-mm-yyyy')    as   recent_reading_date_string,  -- changed yy to yyyy
    (energy_consume_date::date - case when rec.energy_reading_date is null then a.em_start_date::Date else rec.energy_reading_date::Date end)-1  AS no_of_days_lapsed_reading ,  
   cur.seq_id curseq_id, cur.id as curId,  -- added aliases curseq_id
    cur.location cur_location, cur.feeder_id cur_feeder_id,  
    cur.energy_reading_date AS cuurent_reading_date,  
    cur.joint_meter ,
    cur.kwh AS cur_kwh,  
    cur.kvah AS cur_kvah,  
    cur.rkvah_lag AS cur_rkvah_lag,  
    cur.rkvah_lead AS cur_rkvah_lead,  
    cur.cmd cur_cmd,  
    cur.rmd cur_rmd,  
    cur.vol_max  cur_vol_max,  
    cur.vol_min cur_vol_min,  
    cur.max_load cur_max_load,  
    cur.max_load_time_hhmm,  
    cur.max_load_time_date,  
    cur.remarks, -- added line
    rec.seq_id rec_seq_id, rec.id , rec.energy_reading_date recent_energy_reading_Date ,  -- added alis rec_seq_id
    rec.location, rec.feeder_id rec_feeder_id,  
    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,  
    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,  
    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,  
    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,  
    rec.cmd recent_cmd,  
    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,  
    rec.max_load_time_hhmm recent_max_load_time_hhmm,  
    rec.max_load_time_date recent_max_load_time_date ,  
   rec.multiplication_fac recent_multiplication_fac  ,
 ----- ---- added for jr started
       -- joint reading ---
 
    jr.energy_reading_date AS joint_reading_date,
    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading,
    jr.kwh AS jr_kwh,
    jr.kvah AS jr_kvah,
    jr.rkvah_lag AS jr_rkvah_lag,
    jr.rkvah_lead AS jr_rkvah_lead,
     ----- ---- added for jr ended
      -- monthly average readings ---
 
    mavg.energy_reading_date AS mavg_reading_date,
    rec.energy_reading_date - mavg.energy_reading_date AS no_of_days_rec_to_avg_reading,
    mavg.kwh AS mavg_kwh_value,
    mavg.kvah AS mavg_kvah_value,
    mavg.rkvah_lag AS mavg_rkvah_lag_value,
    mavg.rkvah_lead AS mavg_rkvah_lead_value,
    case when (rec.energy_reading_date - mavg.energy_reading_date) > 0 then (mavg.kwh - rec.kwh) /(rec.energy_reading_date - mavg.energy_reading_date) else 0 end avg_kwh,
    case when (rec.energy_reading_date - mavg.energy_reading_date) > 0 then (mavg.kvah - rec.kvah) /(rec.energy_reading_date - mavg.energy_reading_date) else 0 end avg_kvah,
    case when (rec.energy_reading_date - mavg.energy_reading_date) > 0 then (mavg.rkvah_lag - rec.rkvah_lag) /(rec.energy_reading_date - mavg.energy_reading_date) else 0 end avg_rkvah_lag,
    case when (rec.energy_reading_date - mavg.energy_reading_date) > 0 then (mavg.rkvah_lead - rec.rkvah_lead) /(rec.energy_reading_date - mavg.energy_reading_date) else 0 end avg_rkvah_lead
     ----- ---- added monthly average readings ended  
   FROM  
   (   
   select energy_consume_date, em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks ,   
	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,  
	 em.feeder_name from v_energy_meter em ,  
	-- (select generate_series(from_date::date,  to_date::date, interval '1 day')::date as energy_consume_date )dt   
	 (select generate_series( from_date::date,  to_date::date, interval '1 day')::date as energy_consume_date )dt   
	where em.feeder_id = tss_id    
	 )  a  
   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max  
		FROM v_energy_consumption cur1  
		WHERE cur1.energy_reading_date < a.energy_consume_date  
		AND cur1.feeder_id = a.feeder_id   
		)  
	AND rec.feeder_id = a.feeder_id   
    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = a.energy_consume_date  
			AND a.feeder_id = cur.feeder_id )  
			---- added for jr started
   LEFT JOIN v_energy_consumption jr ON  ( jr.energy_reading_date = ( SELECT max(jr1.energy_reading_date) AS max
									FROM v_energy_consumption jr1
									WHERE jr1.energy_reading_date < cur.energy_reading_date 
									AND (jr1.joint_meter = 'y'::bpchar OR jr1.joint_meter = 'Y'::bpchar) 
									AND jr1.feeder_id = cur.feeder_id 
									)
						AND jr.feeder_id = cur.feeder_id )
 ----- ---- added for jr ended
 -- added for monthly average started
  LEFT JOIN v_energy_consumption mavg ON  ( mavg.energy_reading_date = ( SELECT max(mvag1.energy_reading_date) AS max
									FROM v_energy_consumption mvag1
									WHERE mvag1.energy_reading_date < (rec.energy_reading_date - 30)
									AND mvag1.feeder_id = cur.feeder_id 
									)
						AND mavg.feeder_id = rec.feeder_id )
-- added for monthly average ended
 
			) f   
 order by requested_reading_date
) a			 

;

-- end of query

END; $$;


ALTER FUNCTION public.period_tss_energy_consumption_v(tss_id character varying, from_date date, to_date date) OWNER TO postgres;
