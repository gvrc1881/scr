BEGIN
   RETURN QUERY 


select 

-- station tss/sp/ssp
--'tss_1' as required_station ,
rs.facility_id rs_facility_id,
rs.facility_name rs_facility_name,

vtpc.tcp_check_point_part,
vtpc.tcp_check_point_description,
vtpc.tcp_type_of_check_point,
vtpc.tcp_display_group,
vtpc.tcp_display_order,
vtpc.tcp_active,
vtpc.tcp_commparison_points ,

-- current measures v_thermovision_measures cur_m
cur_m.tcpm_id ,
cur_m.tcpm_measure,
cur_m.tcpm_ambient_temp,
cur_m.tcpm_image_id,
cur_m.tcpm_remark,
cur_m.tcpm_criticality,
cur_m.tcpm_variance_with_other_point ,
-- previous readings
	pre1_m.tcps_date as pre1_m_tcps_date, 
	pre1_m.tcpm_measure as pre1_m_tcpm_measure, 
	pre2_m.tcps_date as pre2_m_tcps_date, 
	pre2_m.tcpm_measure as pre2_m_tcpm_measure,
	pre3_m.tcps_date as pre3_m_tcps_date, 
	pre3_m.tcpm_measure as pre3_m_tcpm_measure,
-- cur schedule
cur.tcps_facility_id,
cur.tcps_facility_name ,
cur.tcps_date, cur.tcps_date_time, cur.tcps_time,
cur.tcps_by,
cur.tcps_general_remark
-- facility/station tss /sp/ssp etc
from (select facility_id , facility_name from facility a where a.facility_id = requested_station ) rs
left outer join v_thermovision_check_points vtpc
on (rs.facility_id::integer =  vtpc.tcp_facility_id  ) --vtpcm.tcp_facility_id::integer)
left outer join v_tcp_schedule cur
on (cur.tcps_date = Schedule_date and rs.facility_id::integer = cur.tcps_facility_id::integer)
left outer join ( select tcp_sch_id , 
split_part(tcp_sch_id, ',',  1 )  as  Pre_tcp_sch_1,
split_part(tcp_sch_id, ',',  2 )  as  Pre_tcp_sch_2,
split_part(tcp_sch_id, ',',  3 )  as  Pre_tcp_sch_3,
split_part(tcp_sch_id, ',',  4 )  as  Pre_tcp_sch_4
from (
select	string_agg(tcps_id::character varying,',' order by a.tcps_date desc) tcp_sch_id
from (select ROW_NUMBER() OVER() seq ,cur_h.tcps_date ,cur_h.tcps_id
from v_tcp_schedule	cur_h  	
where cur_h.tcps_date < Schedule_date and cur_h.tcps_facility_id = requested_station::integer
order by tcps_date desc limit  4
) a	 )b
) p on (1=1)
left outer join v_thermovision_measures cur_m
on (cur.tcps_id = cur_m.tcpm_tcp_schedule_id and  tcp_id = cur_m.tcpm_tcp_id ) 
left outer join v_thermovision_measures pre1_m
on (Pre_tcp_sch_1::bigint = pre1_m.tcpm_tcp_schedule_id and  tcp_id = pre1_m.tcpm_tcp_id )
left outer join v_thermovision_measures pre2_m
on (Pre_tcp_sch_2::bigint = pre2_m.tcpm_tcp_schedule_id and  tcp_id = pre2_m.tcpm_tcp_id )
left outer join v_thermovision_measures pre3_m
on (Pre_tcp_sch_3::bigint = pre3_m.tcpm_tcp_schedule_id and  tcp_id = pre3_m.tcpm_tcp_id )

;

END; 
