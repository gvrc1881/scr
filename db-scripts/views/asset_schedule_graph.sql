 --29 function: DROP FUNCTION public.asset_schedule_graph(text);

﻿CREATE OR REPLACE FUNCTION public.asset_schedule_graph(IN depotid text)
  RETURNS TABLE(sno bigint, des_cription text, t_population integer, ty_target integer, tt_month integer, t_progress integer, os_countt integer, f_quency text, a_type character varying, s_code character varying, progress integer, percentage_op numeric, facilityid text, t_y_targer_facid character varying, at_st text, fac_id character varying) AS
$BODY$

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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.asset_schedule_graph(text)
  OWNER TO postgres;