
--
-- TOC entry 4062 (class 1255 OID 129322)
-- Name: day_div_tss_energy_consumption_v(date, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.day_div_tss_energy_consumption_v(record_date date, req_division character varying) RETURNS TABLE(div character varying, period_from text, period_to text, recording_date text, feeder_id character varying, feeder_name character varying, multiplication_fac numeric, requested_reading_date text, no_of_days_lapsed_reading double precision, curseq_id character varying, curid bigint, cur_kwh numeric, prev_kwh numeric, consumption double precision, mavg_reading_date date, no_of_days_rec_to_avg_reading integer, mavg_kwh_value numeric, mavg_kvah_value numeric, mavg_rkvah_lag_value numeric, mavg_rkvah_lead_value numeric, avg_kwh numeric, avg_kvah numeric, avg_rkvah_lag numeric, avg_rkvah_lead numeric, first_reading_after_meter_fix text, meter_start_date text, reading_gap_days text, recent_reading_date text, prev_kvah numeric, cur_kvah numeric, prev_rkvah_lag numeric, cur_rkvah_lag numeric, prev_rkvah_lead numeric, cur_rkvah_lead numeric, cur_cmd numeric, cur_rmd numeric, cur_vol_max numeric, cur_vol_min numeric, cur_max_load numeric, joint_meter character varying, joint_reading_date text, no_of_days_lapsed_j_reading integer, jr_kwh numeric, jr_kvah numeric, jr_rkvah_lag numeric, jr_rkvah_lead numeric, max_load_time_hhmm text, remarks character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
   RETURN QUERY 

-- start of query


 ---  select * from v_energy_consumption  
-------

-- query for DB  -- to be tuned just use for trial to get the data for screen

-- division also a parameter - the date of which user chooses is to be passed in place of now()::date
-- this query brings the values if available from the data and show the previous values in rec data also recent 
-- date of entry for that feeder and days lapsed between given date and recent data date

select 
req_division as div ,
' '  period_from,
' '  period_to,
to_char(record_date,'dd-mm-yyyy') ,  
a.feeder_id ,
a.feeder_name ,
a.multiplication_fac ,
a.requested_reading_date ,
a.no_of_days_lapsed_reading ,  
a.curseq_id,
a.curId,
a.cur_kwh ,
a.prev_kwh ,
 case when a.no_of_days_lapsed_reading::integer > 1 then (a.cur_kwh - a.prev_kwh)*a.multiplication_fac/a.no_of_days_lapsed_reading  else (a.cur_kwh - a.prev_kwh)*a.multiplication_fac end as   consumption ,
--(a.cur_kwh - a.prev_kwh)*a.multiplication_fac/extract(day from a.no_of_days_lapsed_reading) consumption ,
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
to_char(a.meter_start_date,'dd-mm-yyyy') meter_start_date ,
a.reading_gap_days ,
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
a.cur_vol_min ,
a.cur_max_load ,
a.joint_meter ,
a.joint_reading_date,
a.no_of_days_lapsed_j_reading ,
a.jr_kwh ,
a.jr_kvah ,
a.jr_rkvah_lag ,
a.jr_rkvah_lead ,
a.max_load_time_hhmm,
a.remarks

 
from
(

select (f.cur_kwh - f.recent_kwh)*f.multiplication_fac/extract(day from f.no_of_days_lapsed_reading) consumption ,
f.feeder_id , f.feeder_name , f.multiplication_fac ,f.requested_reading_date ,  f.joint_meter , 
f.first_reading_after_meter_fix, f.meter_start_date ,f.recent_reading_date, extract(day from f.no_of_days_lapsed_reading) no_of_days_lapsed_reading,
 f.curseq_id, 
 f.curId,
f.recent_reading_date as recent_reading_date_string , 'Gap(' ||   f.no_of_days_lapsed_reading ||')'  reading_gap_days,
--|| 
f.recent_kwh as prev_kwh, f.cur_kwh,
-- recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || 
f.recent_kvah as prev_kvah, f.cur_kvah,
--recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || 
f.recent_rkvah_lag as prev_rkvah_lag, f.cur_rkvah_lag,
--recent_reading_date || ' : Gap(' ||   no_of_days_lapsed_reading ||')' || 
f.recent_rkvah_lead as prev_rkvah_lead, f.cur_rkvah_lead,

f.cur_cmd, f.cur_rmd, f.cur_vol_max, f.cur_vol_min  , f.cur_max_load ,
to_char(f.joint_reading_date,'dd-mm-yyyy')  joint_reading_date ,
 --f.joint_reading_date , 
 f.no_of_days_lapsed_j_reading, f.jr_kwh, f.jr_kvah, f.jr_rkvah_lag, f.jr_rkvah_lead ,
 f.mavg_reading_date , f.no_of_days_rec_to_avg_reading, f.mavg_kwh_value, f.mavg_kvah_value, f.mavg_rkvah_lag_value, f.mavg_rkvah_lead_value, f.avg_kwh, f.avg_kvah, f.avg_rkvah_lag, f.avg_rkvah_lead ,
  f.max_load_time_hhmm , f.remarks
from 
(
SELECT 
	a.feeder_id , a.feeder_name , a.multiplication_fac , to_char(record_date::date, 'dd-mm-yyyy') as requested_reading_date, rec.em_start_kwh rec_em_start_kwh, -- changed yy to yyyy
    case when rec.energy_reading_date is null then 'Yes' else 'No' end as first_reading_after_meter_fix, 
  a.em_start_date::date  as meter_start_date , 
    
    to_char(case when rec.energy_reading_date is null then a.em_start_date::date else rec.energy_reading_date::date end,'dd-mm-yyyy')  AS recent_reading_date,  --- changed yy to yyyy
    record_date::date - case when rec.energy_reading_date is null then a.em_start_date else rec.energy_reading_date end AS no_of_days_lapsed_reading,
--- current day details
       cur.seq_id curseq_id, cur.id as curId,  -- added aliases curseq_id   ,
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
    cur.max_load_time_date, cur.remarks,
--    cur.multiplication_fac ,
--- recent to required date values 
	
    rec.seq_id, rec.id ,
    rec.location, rec.feeder_id rec_feeder_id,
    case when rec.energy_reading_date is null then a.em_start_kwh else rec.kwh end AS recent_kwh,
    case when rec.energy_reading_date is null then a.em_start_kvah else rec.kvah end AS recent_kvah,
    case when rec.energy_reading_date is null then a.em_start_rkvah_lag else rec.rkvah_lag end AS recent_rkvah_lag,
    case when rec.energy_reading_date is null then a.em_start_rkvah_lead else rec.rkvah_lead end AS recent_rkvah_lead,
    rec.cmd recent_cmd,
    rec.rmd recent_rmd, rec.vol_max recent_vol_max, rec.vol_min recent_vol_min, rec.max_load recent_max_load,
    rec.max_load_time_hhmm recent_max_load_time_hhmm,
    rec.max_load_time_date recent_max_load_time_date ,
   rec.multiplication_fac recent_multiplication_fac ,
   ----- ---- added for jr started
       -- joint reading ---
 
    jr.energy_reading_date AS joint_reading_date,
    cur.energy_reading_date - jr.energy_reading_date AS no_of_days_lapsed_j_reading,
    jr.kwh AS jr_kwh,
    jr.kvah AS jr_kvah,
    jr.rkvah_lag AS jr_rkvah_lag,
    jr.rkvah_lead AS jr_rkvah_lead ,
     ----- ---- added for jr ended

       -- monthly average readings ---
 
    mavg.energy_reading_date AS mavg_reading_date,
    rec.energy_reading_date - mavg.energy_reading_date AS no_of_days_rec_to_avg_reading,
    mavg.kwh AS mavg_kwh_value,
    mavg.kvah AS mavg_kvah_value,
    mavg.rkvah_lag AS mavg_rkvah_lag_value,
    mavg.rkvah_lead AS mavg_rkvah_lead_value,
    case when (rec.energy_reading_date - mavg.energy_reading_date) > 0 then ( rec.kwh - mavg.kwh) /(rec.energy_reading_date - mavg.energy_reading_date) else 0 end avg_kwh,
    case when (rec.energy_reading_date - mavg.energy_reading_date) > 0 then (rec.kvah - mavg.kvah) /(rec.energy_reading_date - mavg.energy_reading_date) else 0 end avg_kvah,
    case when (rec.energy_reading_date - mavg.energy_reading_date) > 0 then (rec.rkvah_lag - mavg.rkvah_lag) /(rec.energy_reading_date - mavg.energy_reading_date) else 0 end avg_rkvah_lag,
    case when (rec.energy_reading_date - mavg.energy_reading_date) > 0 then (rec.rkvah_lead - mavg.rkvah_lead) /(rec.energy_reading_date - mavg.energy_reading_date) else 0 end avg_rkvah_lead
     ----- ---- added monthly average readings ended   
   FROM ( 
   select em.feeder_id , em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks , 
	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,
	 em.feeder_name from v_energy_meter em where upper(em_data_div) = upper(req_division) ) a --req_division  )  a
   left outer join v_energy_consumption rec on rec.energy_reading_date = ( SELECT max(cur1.energy_reading_date) AS max
					FROM v_energy_consumption cur1
					WHERE cur1.energy_reading_date < record_date::date
					AND cur1.feeder_id = a.feeder_id 
					)
	AND rec.feeder_id = a.feeder_id 
    LEFT JOIN v_energy_consumption cur ON  ( cur.energy_reading_date = record_date::date 
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
) a  
;
-- end of query

END; $$;


ALTER FUNCTION public.day_div_tss_energy_consumption_v(record_date date, req_division character varying) OWNER TO postgres;

