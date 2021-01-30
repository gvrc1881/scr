---1
-- FUNCTION: public.asset_schedule_graph(text)

-- DROP FUNCTION public.asset_schedule_graph(text);

CREATE OR REPLACE FUNCTION public.asset_schedule_graph(
	depotid text)
    RETURNS TABLE(sno bigint, des_cription text, t_population integer, ty_target integer, tt_month integer, t_progress integer, os_countt integer, f_quency text, a_type character varying, s_code character varying, progress integer, percentage_op numeric, facilityid text, t_y_targer_facid character varying, at_st text, fac_id character varying) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

BEGIN
Return query 
Select S_No,   description , Total_Population, total_year_target::INTEGER, target_till_month::INTEGER , Total_progress::INTEGER,  os_count::INTEGER, 
 frequency, a.Asset_type , Schedule_code, 
case when progres is null then 0 else progress end progress,
 case when PErcentage_of_progress is null then 0 else PErcentage_of_progress end PErcentage_of_progress,a.facility_id , ty_tgt.facility_id t_y_target_facility_id, 
 osc.at_st, osc.facility_id
 --  
From
(
select row_number() OVER (ORDER BY (Asset_type ,Schedule_code) ) S_No, AT_ST_description  description , Total_Population::INTEGER, 
--apr_cnt::INTEGER, may_cnt::INTEGER, juN_cnt::INTEGER, jul_cnt::INTEGER, aug_cnt::INTEGER, sep_cnt::INTEGER,
--oct_cnt::INTEGER, nov_cnt::INTEGER, dec_cnt::INTEGER, jan_cnt::INTEGER, feb_cnt::INTEGER, mar_cnt::INTEGER,
(apr_cnt+may_cnt+jun_cnt+jul_cnt+aug_cnt+sep_cnt+oct_cnt+ nov_cnt+dec_cnt+ jan_cnt +feb_cnt+mar_cnt)::INTEGER as Total_progress,

avg_cum_month_target_till_month::INTEGER target_till_month ,
frequency, Asset_type , Schedule_code,
tprog::integer  progres, depotid::text facility_id,

CASE when (avg_cum_month_target_till_month is null or avg_cum_month_target_till_month = 0 ) 
then 0 else ROUND((tprog/avg_cum_month_target_till_month*100)::numeric,2) end as PErcentage_of_progress

from
(

Select  AT_ST_description, Total_Population, frequency, Asset_type , Schedule_code, month_target, avg(cum_month_target_till_month) avg_cum_month_target_till_month,

case when sum(apr_cnt)::INTEGER is null THEN 0 else sum(apr_cnt)/12::INTEGER  end as  apr_cnt ,
case when sum(may_cnt)::INTEGER is null THEN 0 else sum(may_cnt)/12::INTEGER  end as  may_cnt ,
case when sum(jun_cnt)::INTEGER is null THEN 0 else sum(jun_cnt)/12::INTEGER  end as  jun_cnt ,
case when sum(jul_cnt)::INTEGER is null THEN 0 else sum(jul_cnt)/12::INTEGER  end as  jul_cnt ,
case when sum(aug_cnt)::INTEGER is null THEN 0 else sum(aug_cnt)/12::INTEGER  end as  aug_cnt ,
case when sum(sep_cnt)::INTEGER is null THEN 0 else sum(sep_cnt)/12::INTEGER  end as  sep_cnt ,
case when sum(oct_cnt)::INTEGER is null THEN 0 else sum(oct_cnt)/12::INTEGER  end as  oct_cnt ,
case when sum(nov_cnt)::INTEGER is null THEN 0 else sum(nov_cnt)/12::INTEGER  end as  nov_cnt ,
case when sum(dec_cnt)::INTEGER is null THEN 0 else sum(dec_cnt)/12::INTEGER  end as  dec_cnt ,
case when sum(jan_cnt)::INTEGER is null THEN 0 else sum(jan_cnt)/12::INTEGER  end as  jan_cnt ,
case when sum(feb_cnt)::INTEGER is null THEN 0 else sum(feb_cnt)/12::INTEGER end as  feb_cnt ,
case when sum(mar_cnt)::INTEGER is null THEN 0 else sum(mar_cnt)/12::INTEGER end as  mar_cnt ,

(sum(apr_cnt)+sum(may_cnt)+sum(jun_cnt)+sum(jul_cnt)+sum(aug_cnt)+sum(sep_cnt)+sum(oct_cnt)+sum(nov_cnt)+sum(dec_cnt)+sum(jan_cnt)+sum(feb_cnt)+sum(mar_cnt))/12::integer tprog
From
(
               -- To get the details of 
               select row_number() OVER (ORDER BY (as12m.Asset_type , as12m.Schedule_code) ),   as12m.Asset_type||'-'|| as12m.Schedule_code AT_ST_description, 
               Case when Total_Population is null THEN 0 else Total_Population end as Total_Population,
               as12m.Asset_type , as12m.Schedule_code,
               case when month_target is null then 0 else month_target end month_target,
               case when cum_month_target_till_month is null then 0 else cum_month_target_till_month end cum_month_target_till_month,
               case when Mon_cnt is null then 0 else Mon_cnt end Mon_cnt, frequency ,

 prog_of_year, prog_of_month, prog_of_year||'-'||prog_of_month,
                    case when prog_of_month != 4  then  0 else   mon_cnt end as apr_cnt  ,
                    case when prog_of_month != 5  then  0 else   mon_cnt end as may_cnt  ,
                    case when prog_of_month != 6  then  0 else   mon_cnt end as jun_cnt  ,
                    case when prog_of_month != 7  then  0 else   mon_cnt end as jul_cnt  ,
                    case when prog_of_month != 8  then  0 else   mon_cnt end as aug_cnt  ,
                    case when prog_of_month != 8  then  0 else   mon_cnt end as sep_cnt  ,
                    case when prog_of_month != 10 then  0 else   mon_cnt end as oct_cnt  ,
                    case when prog_of_month != 11 then  0 else   mon_cnt end as nov_cnt  ,
                    case when prog_of_month != 12 then  0 else   mon_cnt end as dec_cnt  ,
                    case when prog_of_month != 1  then  0 else   mon_cnt end as jan_cnt  ,
                    case when prog_of_month != 2  then  0 else   mon_cnt end as feb_cnt  ,
                    case when prog_of_month != 3  then  0 else   mon_cnt end as mar_cnt  
                    
               from (
                              -- to get all asset types and scehdule types (other than UNSCHED and COMM for month wise 
                              select extract(year from dd2) y1, extract(month from dd2) m1 , fy, asa1.Asset_type||' - '|| asa1.Schedule_code AT_ST_description , 
                              asa1.Asset_type , asa1.schedule_code, 
                              asa1.duration,
                              case when Schedule_code = 'AOH' then 'Yearly' 
                              when Schedule_code = 'QTR' then 'Quarterly' 
                              when Schedule_code = 'HY' then 'Half Yearly ' 
                              when Schedule_code = 'MON' then 'Monthly' 
                              when Schedule_code = 'Monthly' then 'Monthly' 
                              when Schedule_code = 'WEEK' then 'Weekly' 
                              when Schedule_code = 'POH' then  case when duration is null then 'Duration not defined' else duration::text||' Years' end  end frequency
                              from
                              (
                                             -- to get the 1st date of 12 months of a current FY from  now()
                                             SELECT generate_series(  (dd1+ interval '3 months') ,(dd1+ interval '14 months'), interval '1 month') dd2 , fy
                                             from
                                             (
                                             select extract(MONTH from now())::integer month1 , extract(year from now())::integer yy1,
                                             case when extract(MONTH from now())::integer  < 4 then (extract(year from now())::integer -1)::text||'-'||mod(extract(year from now())::integer ,100)::text
                                              else extract(year from now())::integer ||'-'|| mod((extract(year from now() )::integer+1), 100)::text  end FY,
                                             case when extract(MONTH from now())::integer  < 4 then to_date( (extract(year from now())::integer-1)::text||'04-01', 'YYYY-MM-DD') else
                                                  to_date (( (extract(year from now())::integer)::text), 'YYYY-MM-DD') end dd1
                                             )a
                                             --End to get the 1st date of 12 months of a current FY from  now()
                              ) b , asset_schedule_assoc asa1, product_category_member pcm1 
                              where asa1.asset_type = pcm1.Product_id
                              and pcm1.product_category_id = 'OHE_FIXED_ASSET'
                              and asa1.Schedule_code not in ('UNSCHD' , 'COMM' ) 
			      and upper(asa1.is_dpr) ='Y'
                              order by asset_type, Schedule_code, y1, m1
               ) as12m 
               -- end to get all asset types and scehdule types (other than UNSCHED and COMM for month wise 

               left Outer join 

               -- get all total count or population of each aset type in a depot and to link AT_ST for corresponding month 
               (
               select count(*) Total_Population , Asset_type
               from Asset_master_data
               -- facility 
               where Facility_id =depotid
               Group by Asset_type
               ) AP on AP.asset_type = as12m.Asset_type  
               -- End to get all total count or population of each aset type in a depot and to link AT_ST for corresponding month  

               left Outer join 

               -- get all monthly target and cumulative target upto end of month from start of FY of each aset type & Schedule of a depot
               (
               Select asset_type, schedule_type, fy,
                  CASE
                  -- WHEN ash.month1 = 1::integer case when vmct.target_jan is null then 0 else vmct.target_jan end as
                   WHEN ash.month1 = 1::integer THEN vmct.target_jan
                   WHEN ash.month1 = 2::integer THEN vmct.target_feb
                   WHEN ash.month1 = 3::integer THEN vmct.target_mar
                   WHEN ash.month1 = 4::integer THEN vmct.target_apr
                   WHEN ash.month1 = 5::integer THEN vmct.target_may
                   WHEN ash.month1 = 6::integer THEN vmct.target_jun
                   WHEN ash.month1 = 7::integer THEN vmct.target_jul
                   WHEN ash.month1 = 8::integer THEN vmct.target_aug
                   WHEN ash.month1 = 9::integer THEN vmct.target_sep
                   WHEN ash.month1 = 10::integer THEN vmct.target_oct
                   WHEN ash.month1 = 11::integer THEN vmct.target_nov
                   WHEN ash.month1 = 12::integer THEN vmct.target_dec
                   ELSE NULL::integer
               END AS month_target, 
               CASE
                   WHEN ash.month1 = 1::integer THEN vmct.cum_target_jan
                   WHEN ash.month1 = 2::integer THEN vmct.cum_target_feb
                   WHEN ash.month1 = 3::integer THEN vmct.cum_target_mar
                   WHEN ash.month1 = 4::integer THEN vmct.cum_target_apr
                   WHEN ash.month1 = 5::integer THEN vmct.cum_target_may
                   WHEN ash.month1 = 6::integer THEN vmct.cum_target_jun
                   WHEN ash.month1 = 7::integer THEN vmct.cum_target_jul
                   WHEN ash.month1 = 8::integer THEN vmct.cum_target_aug
                   WHEN ash.month1 = 9::integer THEN vmct.cum_target_sep
                   WHEN ash.month1 = 10::integer THEN vmct.cum_target_oct
                   WHEN ash.month1 = 11::integer THEN vmct.cum_target_nov
                   WHEN ash.month1 = 12::integer THEN vmct.cum_target_dec
                   ELSE NULL::integer
               END AS cum_month_target_till_month
           
               from v_monthly_cum_targets vmct, 
                              (select extract(MONTH from now()) month1 , extract(year from now()) yy1 )ash
               -- facility 
               where facility_id=depotid
               ) tar on as12m.Asset_type = tar.Asset_type  and  as12m.Schedule_code = tar.schedule_type and as12m.fy= tar.fy
               -- End get all monthly target and cumulative target upto end of month from start of FY of each aset type & Schedule of a depot
               left outer join
               -- get Schedules done count till this month from start of FY of each aset type & Schedule of a depot
               (
               Select count(*) Mon_cnt , Asset_type  , Schedule_code , extract(MONTH from schedule_date) prog_of_month, extract(year from schedule_date) prog_of_year, fy
               from v_assets_schedule_history
               -- facility 
               where facility_id=depotid
               --and FY =  as12m.fy
               group by Asset_type  , Schedule_code , extract(MONTH from schedule_date) , extract(year from schedule_date) , fy
               order by prog_of_year, prog_of_month
               ) mp -- mp monthly progress of at and st for each month and year
               on as12m.Asset_type = mp.Asset_type  and  as12m.Schedule_code = mp.schedule_code and as12m.fy= mp.fy
               -- End of get Schedules done count till this month from start of FY of each aset type & Schedule of a depot
               
               order by 1 , 2
               ) mgrp

group by AT_ST_description, Total_Population, Asset_type , Schedule_code, month_target , frequency

order by AT_ST_description,   Asset_type , Schedule_code
) final
) a
---
left outer join 

( 
select month1 , yy1, a1.fy, dd1, total_year_target,asset_type, schedule_type, facility_id
from
(
select extract(MONTH from now())::integer month1 , extract(year from now())::integer yy1,
case when extract(MONTH from now())::integer  < 4 then (extract(year from now())::integer -1)::text||'-'||mod(extract(year from now())::integer ,100)::text
else extract(year from now())::integer ||'-'|| mod((extract(year from now() )::integer+1), 100)::text  end FY,
case when extract(MONTH from now())::integer  < 4 then to_date( (extract(year from now())::integer-1)::text||'04-01', 'YYYY-MM-DD') else
to_date (( (extract(year from now())::integer)::text), 'YYYY-MM-DD') end dd1
)a1
left outer join ( select total_year_target, fy , asset_type, schedule_type, facility_id from v_monthly_cum_targets where facility_id = depotid ) b on (a1.fy =b.fy)
) ty_tgt on (ty_tgt.facility_id = a.facility_id and ty_tgt.Asset_type=a.asset_type and ty_tgt.Schedule_type =a.schedule_code)

left outer join 
-- out standing 

( 
 -- out staind count by facility and at-st
--- outstanding sch at-st count as on date
select count(*) os_count, asset_type||'-'||schedule_code at_st, facility_id, depot_type
from
(
--- all outstanding schedules even there is no scedule done etc
--- outstanding Schedules
select ast.asset_type||'-'||ast.schedule_code at_st,
ast.facility_id , ast.facility_name, depot_type, ast.asset_id, ast.asset_type ,ast.schedule_code, date_of_commision , recent_schedule_date::date, duration , frequency, uom_of_duration, month_duration ,
case when recent_schedule_date is null  then (date_of_commision + INTERVAL '1 month'  * ast.month_duration )::date
                              else (recent_schedule_date + INTERVAL '1 month' * month_duration)::date --"MONTHS"
end as Schedule_due_date,
(now()::date - case when recent_schedule_date is null  then (date_of_commision + INTERVAL '1 month'  * ast.month_duration )::date
                              else (recent_schedule_date + INTERVAL '1 month' * month_duration)::date --"MONTHS"
               end) outstanding_due_days,
case when (recent_schedule_date is null and date_of_commision is null) then 'Sch not yet done or data missing' end as remark
From
(
select amd.facility_id, f.facility_name, depot_type,
asset_id, amd.asset_type ,schedule_code,  AT_ST_description, oem_serial ,rly_assigned_serial , date_of_commision , amd.make ,amd.model ,section ,kilometer , duration , frequency, uom_of_duration,
month_duration
from facility f, asset_master_data amd --where facility_id=depotId, 
left outer join 
 (
   -- to get all asset types and scehdule types (other than UNSCHED and COMM for month wisedepotId
   select -- extract(year from dd2) y1, extract(month from dd2) m1 , fy, 
   asa1.Asset_type||' - '|| asa1.Schedule_code AT_ST_description ,
   asa1.Asset_type , asa1.schedule_code, uom_of_duration,
   asa1.duration,
   case when Schedule_code = 'AOH' then 'Yearly'
   when Schedule_code = 'QTR' then 'Quarterly'
   when Schedule_code = 'HY' then 'Half Yearly '
   when Schedule_code = 'MON' then 'Monthly'
   when Schedule_code = 'Monthly' then 'Monthly'
   when Schedule_code = 'WEEK' then 'Weekly'
   when Schedule_code = 'POH' then  case when duration is null then 'Duration not defined' else duration::text||' Years' end  end frequency,
   case when uom_of_duration ='Time in Years'  then duration::integer *12 
        when uom_of_duration ='Time in Months'  then duration::integer *1  
   end as month_duration --::integer
   from
   asset_schedule_assoc asa1, product_category_member pcm1
   where asa1.asset_type = pcm1.Product_id
   and pcm1.product_category_id = 'OHE_FIXED_ASSET'
   and asa1.Schedule_code not in ('UNSCHD' , 'COMM' )
   order by asset_type, Schedule_code 
               ) as12m on (amd.asset_type = as12m.asset_type)
               where amd.facility_id = f.facility_id
)AST
Left outer join 
 (
               Select  facility_id, asset_id , Asset_type  , Schedule_code , Max(schedule_date) recent_schedule_date
               from v_assets_schedule_history
               where facility_id=depotid
               
               group by  facility_id, asset_id ,Asset_type  , Schedule_code 
)  vash on (ast.asset_id = vash.asset_id and ast.asset_type = vash.asset_type and ast.schedule_code = vash.schedule_code)
               -- end to get all asset types and scehdule types (other than UNSCHED and COMM for month wise
where ast.facility_id=depotid
order by asset_id, asset_type, schedule_code
--- outstanding Schedules
--- all outstanding schedules even there is no scedule done etc

) osi  -- oust standing assets items
where outstanding_due_days > 0
group by asset_type||'-'||schedule_code, facility_id, depot_type
) osc on (osc.at_st = a.description and osc.facility_id = a.facility_id)
where ty_tgt.facility_id=depotid 
and Total_Population > 0
order by 2; 
END;
$BODY$;

ALTER FUNCTION public.asset_schedule_graph(text)
    OWNER TO postgres;


--2

CREATE OR REPLACE FUNCTION public.day_div_tss_energy_consumption_v(
	record_date date,
	req_division character varying)
    RETURNS TABLE(div character varying, period_from text, period_to text, recording_date text, feeder_id character varying, feeder_name character varying, multiplication_fac numeric, requested_reading_date text, no_of_days_lapsed_reading double precision, curseq_id character varying, curid bigint, cur_kwh numeric, prev_kwh numeric, consumption double precision, mavg_reading_date date, no_of_days_rec_to_avg_reading integer, mavg_kwh_value numeric, mavg_kvah_value numeric, mavg_rkvah_lag_value numeric, mavg_rkvah_lead_value numeric, avg_kwh numeric, avg_kvah numeric, avg_rkvah_lag numeric, avg_rkvah_lead numeric, first_reading_after_meter_fix text, meter_start_date text, reading_gap_days text, recent_reading_date text, prev_kvah numeric, cur_kvah numeric, prev_rkvah_lag numeric, cur_rkvah_lag numeric, prev_rkvah_lead numeric, cur_rkvah_lead numeric, cur_cmd numeric, cur_rmd numeric, cur_vol_max numeric, cur_vol_min numeric, cur_max_load numeric, joint_meter character varying, joint_reading_date text, no_of_days_lapsed_j_reading integer, jr_kwh numeric, jr_kvah numeric, jr_rkvah_lag numeric, jr_rkvah_lead numeric, max_load_time_hhmm text, cur_max_load_time_date timestamp without time zone, remarks character varying) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$
BEGIN
   RETURN QUERY

-- start of query

---  select * from v_energy_consumption 
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
a.cur_max_load_time_date::timestamp without time zone,
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
  f.max_load_time_hhmm , f.cur_max_load_time_date::timestamp ,f.remarks
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
    cur.max_load_time_date cur_max_load_time_date, cur.remarks,
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
                em.feeder_name from v_energy_meter em where upper(em_data_div) = upper(req_division)
                 and em.em_start_date < record_date::date and ( em.em_end_date is NULL or em.em_end_date > record_date::date )
                ) a
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
 

END; $BODY$;

ALTER FUNCTION public.day_div_tss_energy_consumption_v(date, character varying)
    OWNER TO postgres;
	
--3



-- FUNCTION: public.period_tss_energy_consumption_v(character varying, date, date)

-- DROP FUNCTION public.period_tss_energy_consumption_v(character varying, date, date);

CREATE OR REPLACE FUNCTION public.period_tss_energy_consumption_v(
	tss_id character varying,
	from_date date,
	to_date date)
    RETURNS TABLE(requested_tss_id character varying, period_from text, period_to text, req_date date, feeder_id character varying, feeder_name character varying, multiplication_fac numeric, requested_reading_date text, no_of_days_lapsed_reading integer, curseq_id character varying, curid bigint, cur_kwh numeric, prev_kwh numeric, consumption numeric, mavg_reading_date date, no_of_days_rec_to_avg_reading integer, mavg_kwh_value numeric, mavg_kvah_value numeric, mavg_rkvah_lag_value numeric, mavg_rkvah_lead_value numeric, avg_kwh numeric, avg_kvah numeric, avg_rkvah_lag numeric, avg_rkvah_lead numeric, first_reading_after_meter_fix text, meter_start_date date, reading_gap_days text, recent_reading_date text, prev_kvah numeric, cur_kvah numeric, prev_rkvah_lag numeric, cur_rkvah_lag numeric, prev_rkvah_lead numeric, cur_rkvah_lead numeric, cur_cmd numeric, cur_rmd numeric, cur_vol_max numeric, cur_vol_min numeric, cur_max_load numeric, joint_meter character varying, joint_reading_date date, no_of_days_lapsed_j_reading integer, jr_kwh numeric, jr_kvah numeric, jr_rkvah_lag numeric, jr_rkvah_lead numeric, max_load_time_hhmm text, cur_max_load_time_date timestamp without time zone, remarks character varying) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$
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
 a.cur_max_load_time_date::timestamp without time zone,
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
 f.avg_kvah, f.avg_rkvah_lag, f.avg_rkvah_lead , f.max_load_time_hhmm, f.cur_max_load_time_date::timestamp ,
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
	cur.max_load_time_date cur_max_load_time_date, 
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
   (   select energy_consume_date, em.feeder_id , em.id, em.seq_id ,  em.em_start_date , em.em_end_date , em.multiplication_fac , em.remarks ,   
	em_m_start_reading , em_m_end_reading , em_start_kwh , em_start_kvah , em_start_rkvah_lag , em_start_rkvah_lead ,  
	 em.feeder_name from ( select * from v_energy_meter em1
						  where (  
							  -- cases meter fixed before start of given period and end before end of period
							  (em1.em_start_date <= from_date::date  and em1.em_end_date >= from_date::date)
						  		or
							  -- cases meter fixed before start of given period and not ended before end of period
							  (em1.em_start_date <= from_date::date and em1.em_end_date is NULL)
							    or
							  -- cases meter fixed after start of given period and end before end of period
						   	  (em1.em_start_date <= from_date::date and em1.em_end_date < to_date::date ) 
							  	or
							  -- cases meter fixed after start of given period and not yet ended 
							   (em1.em_start_date <= from_date::date and em1.em_end_date is NULL ) 
							   )
						and em1.feeder_id = tss_id
						  )em ,  
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

END; $BODY$;

ALTER FUNCTION public.period_tss_energy_consumption_v(character varying, date, date)
    OWNER TO postgres;


----4
-- FUNCTION: public.user_func_location(text)

-- DROP FUNCTION public.user_func_location(text);

CREATE OR REPLACE FUNCTION public.user_func_location(
	userid text)
    RETURNS text
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$

DECLARE

               FIRST_LEVEL_RM_OF_UNIT TEXT;

               --NEXT_LEVEL_RM_OF_UNIT TEXT;

        UNIT_NAME TEXT;

               NO_OF_REPORTEES BIGINT;

               user_id text;

               N BIGINT;

               UNITNAMES TEXT;

               CUR_UNIT_NAMES CURSOR FOR SELECT UNIT_CODE FROM FUNCTIONAL_LOCATION_HIERARCHY flh_l where flh_l.head_login_id not in (select distinct flh_r.rm_login_id from FUNCTIONAL_LOCATION_HIERARCHY flh_r) order BY seq_id;

               UNIT_LIST  VARCHAR[] ;

BEGIN

 

               --UNIT_LIST='';

               RAISE NOTICE 'intitial -------UNIT_LIST-%', UNIT_LIST;

               SELECT COUNT(*) INTO NO_OF_REPORTEES FROM FUNCTIONAL_LOCATION_HIERARCHY WHERE RM_LOGIN_ID = USERID;

               RAISE NOTICE 'intitial -------UNIT_LIST-%', UNIT_LIST;

               IF  NO_OF_REPORTEES = 0 THEN  --USER HAS NO REPORTEES THEN HIS DEFAULT DEPOT/WH IS ONLY ONE UNIT

                              RAISE NOTICE 'IN LOOP UNIT NAME------- if no RM % -UNIT_NAME-%', N,UNITNAMES;

                              UNIT_LIST := array(

                       SELECT FACILITY_NAME 

                              FROM USER_DEFUALT_FAC_CONS_IND_ETC

                              WHERE FACILITY_TYPE_ID in ('WAREHOUSE', 'PLANT','SP', 'SSP', 'TSS')

                              AND USER_LOGIN_ID = USERID);

                              --UNIT_LIST= UNIT_LIST||UNIT_NAME||',';

                              RETURN UNIT_LIST;

               ELSE  -- GET THE LIST OF DEPOTS/WH COVERED TO USER DIRECTLY OR THROUGH THE REPORTEES

 

               RAISE NOTICE ' UNIT NAME------- For RM   % -',USERID ;

                              SELECT seq_id INTO user_id FROM FUNCTIONAL_LOCATION_HIERARCHY WHERE head_LOGIN_ID = USERID;

                              RAISE NOTICE 'user name id is ------- -% --', user_id;

                              UNIT_LIST := array(

                              WITH RECURSIVE nodes(seq_id,unit_code, unit_type, head_login_id, rm_seq_id ) AS

                              (

                              SELECT f1.seq_id, f1.unit_code,  f1.unit_type, f1.head_login_id, f1.rm_seq_id

                              FROM functional_location_hierarchy f1 WHERE f1.rm_seq_id::integer =  user_id::integer

                              UNION

                              SELECT f2.seq_id, f2.unit_code,  f2.unit_type, f2.head_login_id, f2.rm_seq_id

                              FROM functional_location_hierarchy f2, nodes f1 WHERE f2.rm_seq_id::integer = f1.seq_id::integer

                              )

                              SELECT unit_code FROM nodes  

                              order by rm_seq_id desc

                              );

                              RAISE NOTICE 'in loop-------UNIT_LIST-%', UNIT_LIST;

                             

                              RAISE NOTICE 'final --------NO OF LOCATIONS %  -- LIST OF LOCATIONS %', N, UNIT_LIST;

                              RETURN UNIT_LIST;

               END IF; 

               END;
$BODY$;

ALTER FUNCTION public.user_func_location(text)
    OWNER TO postgres;

-----5

-- select * from date_dd_mm_fy_details('2020-04-15')
--select * from information_schema.columns where table_name ='v_now_dd_mm_fy_data'
--drop function date_dd_mm_fy_details(given_date date);
CREATE OR REPLACE FUNCTION public.date_dd_mm_fy_details(
                given_date date)
    RETURNS TABLE(
                                yyyy       double precision, mm     double precision,
                dd           double precision, yy integer, fy_yyyyyy  text,
                fy_yyyy text, fy_start_yyyy          double precision,
                fy_start_date    date, fy_end_date          date
                )
               
    LANGUAGE 'plpgsql'
 

    COST 100
    VOLATILE
    ROWS 1000
AS $BODY$
BEGIN
   RETURN QUERY
--select given_date::date as ret_date;
 

SELECT date_part('year'::text, a.date) AS yyyy,
    date_part('month'::text, a.date) AS mm,
    date_part('day'::text, a.date) AS dd,
    mod(date_part('year'::text, a.date)::integer, 100) AS yy,
        CASE
            WHEN date_part('month'::text, a.date) > 3::double precision THEN (date_part('year'::text, a.date) || '-'::text) || (mod(date_part('year'::text, a.date)::integer, 100) + 1)
            ELSE ((date_part('year'::text, a.date)::integer - 1) || '-'::text) || mod(date_part('year'::text, a.date)::integer, 100)
        END AS fy_yyyyyy,
        CASE
            WHEN date_part('month'::text, a.date) > 3::double precision THEN (mod(date_part('year'::text, a.date)::integer, 100) || '-'::text) || (mod(date_part('year'::text, a.date)::integer, 100) + 1)
            ELSE ((mod(date_part('year'::text, a.date)::integer, 100) - 1) || '-'::text) || mod(date_part('year'::text, a.date)::integer, 100)
        END AS fy_yyyy,
        CASE
            WHEN date_part('month'::text, a.date) > 3::double precision THEN date_part('year'::text, a.date)
            ELSE (date_part('year'::text, a.date)::integer - 1)::double precision
        END AS fy_start_yyyy,
        CASE
            WHEN date_part('month'::text, a.date) > 3::double precision THEN to_date(date_part('year'::text, a.date) || '-04-01'::text, 'yyyy-mm-dd'::text)
            ELSE to_date((date_part('year'::text, a.date) - 1::double precision) || '-04-01'::text, 'yyyy-mm-dd'::text)
        END AS fy_start_date,
        CASE
            WHEN date_part('month'::text, a.date) > 3::double precision THEN to_date((date_part('year'::text, a.date) + 1::double precision) || '-03-31'::text, 'yyyy-mm-dd'::text)
            ELSE to_date(date_part('year'::text, a.date) || '-03-31'::text, 'yyyy-mm-dd'::text)
        END AS fy_end_date
   FROM ( SELECT given_date::date AS date) a;
 

-- end of query
 

 

END; $BODY$;

----------------

------6
-- FUNCTION: public.tcp_measure_v_func(integer, date)

-- DROP FUNCTION public.tcp_measure_v_func(integer, date);

CREATE OR REPLACE FUNCTION public.tcp_measure_v_func(
	requested_station integer,
	schedule_date date)
    RETURNS TABLE(rs_id bigint, rs_facility_id character varying, rs_facility_name character varying, 
	tcp_check_point_part character varying, tcp_check_point1_description character varying , tcp_check_point2_description character varying,
	tcp_display_group character varying, tcp_display_order character varying, tcp_active character varying, 
	tcpm_id bigint, tcpm_measure_point1 double precision ,  tcpm_measure_point2 double precision,
    tcpm_image_id character varying, tcpm_remark character varying, tcpm_criticality character varying,
 	f_diff double precision, vtm_tcp_display_order character varying ,
	pre1_m_tcps_date timestamp without time zone, pre1_m_tcpm_measure_point1 double precision, pre1_m_tcpm_measure_point2 double precision , 
	pre2_m_tcps_date timestamp without time zone, pre2_m_tcpm_measure_point1 double precision, pre2_m_tcpm_measure_point2 double precision ,  
	pre3_m_tcps_date timestamp without time zone, pre3_m_tcpm_measure_point1 double precision, pre3_m_tcpm_measure_point2 double precision ,  
	tcps_facility_id bigint, tcps_facility_name character varying, tcps_date timestamp without time zone, tcps_date_time timestamp without time zone,
	tcps_time text, tcps_by character varying, tcps_general_remark character varying) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$
BEGIN
   RETURN QUERY 

select 

rs.id rs_id,
rs.facility_id rs_facility_id,
rs.facility_name rs_facility_name,

rs.tcp_check_point_part,
rs.tcp_check_point1_description,
rs.tcp_check_point2_description,
rs.tcp_display_group,
rs.tcp_display_order,
rs.tcp_active,


-- current measures v_thermovision_measures cur_m
cur_m.tcpm_id ,
cur_m.tcpm_measure_point1,
cur_m.tcpm_measure_point2 ,
cur_m.tcpm_image_id,
cur_m.tcpm_remark,
cur_m.tcpm_criticality,

cur_m.f_diff,
cur_m.vtm_tcp_display_order  ,

-- previous readings
	pre1_m.tcps_date as pre1_m_tcps_date, 
	pre1_m.tcpm_measure_point1 as pre1_m_tcpm_measure_point1,
	pre1_m.tcpm_measure_point2 as pre1_m_tcpm_measure_point2,
	pre2_m.tcps_date as pre2_m_tcps_date, 
	pre2_m.tcpm_measure_point1 as pre1_m_tcpm_measure_point1,
	pre2_m.tcpm_measure_point2 as pre1_m_tcpm_measure_point2,
	pre3_m.tcps_date as pre3_m_tcps_date, 
	pre3_m.tcpm_measure_point1 as pre1_m_tcpm_measure_point1,
	pre3_m.tcpm_measure_point2 as pre1_m_tcpm_measure_point2,
-- cur schedule
cur.tcps_facility_id,
cur.tcps_facility_name ,
cur.tcps_date, cur.tcps_date_time, cur.tcps_time,
cur.tcps_by,
cur.tcps_general_remark
-- facility/station tss /sp/ssp etc
--
/*
select * 
*/
from (select id ,facility_id , facility_name , vtpc.*
	  from v_thermovision_check_points vtpc,
	  facility f where f.id = requested_station -- 18413--- 
	  and f.id =  vtpc.tcp_facility_id
	  and upper(vtpc.tcp_active) = 'YES'
	  ) rs  -- required station 
left outer join v_tcp_schedule cur
on (cur.tcps_date::date = Schedule_date -- '2021-01-30'-- 	
	and rs.id = cur.tcps_facility_id)
left outer join ( select tcp_sch_id , 
split_part(tcp_sch_id, ',',  1 )  as  Pre_tcp_sch_1,
split_part(tcp_sch_id, ',',  2 )  as  Pre_tcp_sch_2,
split_part(tcp_sch_id, ',',  3 )  as  Pre_tcp_sch_3,
split_part(tcp_sch_id, ',',  4 )  as  Pre_tcp_sch_4
from (
select	string_agg(tcps_id::character varying,',' order by a.tcps_date desc) tcp_sch_id
from (select ROW_NUMBER() OVER() seq ,cur_h.tcps_date ,cur_h.tcps_id
from v_tcp_schedule	cur_h  	-- schedule history before current schedule
where cur_h.tcps_date <  Schedule_date  --'2021-01-30'--
	  and cur_h.tcps_facility_id = requested_station -- 18413---::integer
order by tcps_date desc limit  4
) a	 )b
) p on (1=1)

left outer join 
	(	select * from
			(select 
				case when (vtm.tcpm_measure_point1 is not null and vtm.tcpm_measure_point2 is not null) then
					abs(vtm.tcpm_measure_point1 - vtm.tcpm_measure_point2)  end as f_diff, 
					 vtm.tcp_id as vtm_tcp_id , vtm.tcp_display_order vtm_tcp_display_order, tcpm_tcp_schedule_id,
			 		 vtm.tcpm_measure_point1 ,  vtm.tcpm_measure_point2 , vtm.tcps_facility_id ,vtm.tcpm_tcp_id ,vtm.tcpm_id,
			 		vtm.tcpm_image_id , vtm.tcpm_criticality, vtm.tcpm_remark
					from v_thermovision_measures vtm 
			) tm ,
			facility f 
		where f.id =  18413 -- requested_station
		and f.id =  tm.tcps_facility_id 
	) cur_m
on (cur.tcps_id = cur_m.tcpm_tcp_schedule_id and  rs.tcp_id = cur_m.tcpm_tcp_id ) 
left outer join v_thermovision_measures pre1_m
on (Pre_tcp_sch_1::bigint = pre1_m.tcpm_tcp_schedule_id and  vtm_tcp_id = pre1_m.tcpm_tcp_id )
left outer join v_thermovision_measures pre2_m
on (Pre_tcp_sch_2::bigint = pre2_m.tcpm_tcp_schedule_id and  vtm_tcp_id = pre2_m.tcpm_tcp_id )
left outer join v_thermovision_measures pre3_m
on (Pre_tcp_sch_3::bigint = pre3_m.tcpm_tcp_schedule_id and  vtm_tcp_id = pre3_m.tcpm_tcp_id )
order by rs.tcp_display_order::integer
;

END; 
$BODY$;

ALTER FUNCTION public.tcp_measure_v_func(integer, date)
   OWNER TO postgres;